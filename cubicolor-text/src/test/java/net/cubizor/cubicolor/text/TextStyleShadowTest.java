package net.cubizor.cubicolor.text;

import net.cubizor.cubicolor.core.ColorFactoryImpl;
import net.cubizor.cubicolor.api.Color;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TextStyleShadowTest {

    private final ColorFactoryImpl colors = new ColorFactoryImpl();

    @Test
    void styleWithoutShadowLeavesItUnset() {
        TextStyle style = TextStyle.of(colors.hex("#FF2E5B"));
        assertFalse(style.hasShadow());
        assertNull(style.getShadow());
    }

    @Test
    void builderCarriesTransparentShadow() {
        Color transparent = colors.hex("#00000000");
        TextStyle style = TextStyle.builder(colors.hex("#00D9FF")).shadow(transparent).bold().build();

        assertTrue(style.hasShadow());
        assertEquals(0, style.getShadow().getAlpha());
        assertTrue(style.isBold());
    }

    @Test
    void ofColorShadowDecorationsFactory() {
        Color shadow = colors.hex("#000000"); // opaque black, alpha defaults to 255
        TextStyle style = TextStyle.of(colors.hex("#3DFF7A"), shadow, Set.of(TextDecoration.UNDERLINED));

        assertTrue(style.hasShadow());
        assertEquals(255, style.getShadow().getAlpha());
        assertTrue(style.isUnderlined());
    }

    @Test
    void shadowParticipatesInEquality() {
        Color base = colors.hex("#FFFFFF");
        TextStyle noShadow = TextStyle.of(base);
        TextStyle withShadow = TextStyle.of(base, colors.hex("#00000000"), Set.of());

        assertNotEquals(noShadow, withShadow);
        assertEquals(withShadow, TextStyle.of(base, colors.hex("#00000000"), Set.of()));
    }
}
