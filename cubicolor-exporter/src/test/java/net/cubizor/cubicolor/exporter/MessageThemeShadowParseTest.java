package net.cubizor.cubicolor.exporter;

import net.cubizor.cubicolor.text.MessageRole;
import net.cubizor.cubicolor.text.MessageTheme;
import net.cubizor.cubicolor.text.TextStyle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageThemeShadowParseTest {

    private final MessageThemeJsonParser parser = new MessageThemeJsonParser();

    @Test
    void parsesTransparentArgbShadow() {
        String json = """
            {
              "name": "neon",
              "messages": {
                "PRIMARY": { "color": "#00D9FF", "shadow": "#00000000", "decorations": [] }
              }
            }
            """;
        TextStyle style = parser.parse(json).getStyle(MessageRole.PRIMARY).orElseThrow();

        assertTrue(style.hasShadow());
        assertEquals(0, style.getShadow().getAlpha());
    }

    @Test
    void shadowIsOptional() {
        String json = """
            {
              "name": "plain",
              "messages": {
                "PRIMARY": { "color": "#00D9FF", "decorations": [] }
              }
            }
            """;
        TextStyle style = parser.parse(json).getStyle(MessageRole.PRIMARY).orElseThrow();

        assertFalse(style.hasShadow());
        assertNull(style.getShadow());
    }

    @Test
    void parsesShadowAlongsideDecorations() {
        String json = """
            {
              "name": "neon",
              "messages": {
                "HIGHLIGHT": { "color": "#FFE23D", "shadow": "#00000000", "decorations": ["BOLD"] }
              }
            }
            """;
        TextStyle style = parser.parse(json).getStyle(MessageRole.HIGHLIGHT).orElseThrow();

        assertTrue(style.hasShadow());
        assertTrue(style.isBold());
    }
}
