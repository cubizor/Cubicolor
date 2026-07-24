package net.cubizor.cubicolor.text;

import net.cubizor.cubicolor.api.Color;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a text style with color, decorations and an optional text shadow.
 * Platform-agnostic and immutable.
 *
 * <p>The shadow is the Minecraft 1.21.4+ text drop-shadow colour. It is ARGB-capable, so a fully
 * transparent shadow ({@code #00000000}) disables the vanilla shadow entirely — the trick used to
 * make bright / neon palettes read cleanly. A {@code null} shadow means "unset": the vanilla shadow
 * (or whatever the surrounding component inherits) is left untouched.
 */
public class TextStyle {

    private final Color color;
    private final Set<TextDecoration> decorations;
    private final Color shadow;

    private TextStyle(Color color, Set<TextDecoration> decorations, Color shadow) {
        this.color = Objects.requireNonNull(color, "Color cannot be null");
        this.decorations = Set.copyOf(decorations);
        this.shadow = shadow; // nullable — null = shadow unset
    }

    /**
     * Gets the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the decorations
     */
    public Set<TextDecoration> getDecorations() {
        return decorations;
    }

    /**
     * Gets the text shadow colour, or {@code null} if this style leaves the shadow unset.
     * The colour is ARGB-capable; an alpha of 0 (e.g. {@code #00000000}) disables the vanilla shadow.
     */
    public Color getShadow() {
        return shadow;
    }

    /**
     * Whether this style sets an explicit text shadow.
     */
    public boolean hasShadow() {
        return shadow != null;
    }

    /**
     * Checks if this style has a specific decoration
     */
    public boolean hasDecoration(TextDecoration decoration) {
        return decorations.contains(decoration);
    }

    /**
     * Checks if this style is bold
     */
    public boolean isBold() {
        return hasDecoration(TextDecoration.BOLD);
    }

    /**
     * Checks if this style is italic
     */
    public boolean isItalic() {
        return hasDecoration(TextDecoration.ITALIC);
    }

    /**
     * Checks if this style is underlined
     */
    public boolean isUnderlined() {
        return hasDecoration(TextDecoration.UNDERLINED);
    }

    /**
     * Checks if this style is strikethrough
     */
    public boolean isStrikethrough() {
        return hasDecoration(TextDecoration.STRIKETHROUGH);
    }

    /**
     * Checks if this style is obfuscated
     */
    public boolean isObfuscated() {
        return hasDecoration(TextDecoration.OBFUSCATED);
    }

    /**
     * Creates a new builder
     */
    public static Builder builder(Color color) {
        return new Builder(color);
    }

    /**
     * Creates a simple text style with just color
     */
    public static TextStyle of(Color color) {
        return new TextStyle(color, Set.of(), null);
    }

    /**
     * Creates a text style with color and decorations
     */
    public static TextStyle of(Color color, TextDecoration... decorations) {
        return new TextStyle(color, Set.of(decorations), null);
    }

    /**
     * Creates a text style with color, an optional shadow colour, and decorations.
     */
    public static TextStyle of(Color color, Color shadow, Set<TextDecoration> decorations) {
        return new TextStyle(color, decorations, shadow);
    }

    /**
     * Builder for TextStyle
     */
    public static class Builder {
        private final Color color;
        private final Set<TextDecoration> decorations = EnumSet.noneOf(TextDecoration.class);
        private Color shadow;

        private Builder(Color color) {
            this.color = Objects.requireNonNull(color, "Color cannot be null");
        }

        /**
         * Adds a decoration
         */
        public Builder decoration(TextDecoration decoration) {
            decorations.add(decoration);
            return this;
        }

        /**
         * Sets the text shadow colour (ARGB-capable; alpha 0 disables the vanilla shadow).
         * Pass {@code null} to leave the shadow unset.
         */
        public Builder shadow(Color shadow) {
            this.shadow = shadow;
            return this;
        }

        /**
         * Makes the text bold
         */
        public Builder bold() {
            return decoration(TextDecoration.BOLD);
        }

        /**
         * Makes the text italic
         */
        public Builder italic() {
            return decoration(TextDecoration.ITALIC);
        }

        /**
         * Makes the text underlined
         */
        public Builder underlined() {
            return decoration(TextDecoration.UNDERLINED);
        }

        /**
         * Makes the text strikethrough
         */
        public Builder strikethrough() {
            return decoration(TextDecoration.STRIKETHROUGH);
        }

        /**
         * Makes the text obfuscated
         */
        public Builder obfuscated() {
            return decoration(TextDecoration.OBFUSCATED);
        }

        /**
         * Builds the TextStyle
         */
        public TextStyle build() {
            return new TextStyle(color, decorations, shadow);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextStyle textStyle = (TextStyle) o;
        return color.equals(textStyle.color) &&
               decorations.equals(textStyle.decorations) &&
               Objects.equals(shadow, textStyle.shadow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, decorations, shadow);
    }

    @Override
    public String toString() {
        return "TextStyle{" +
               "color=" + color.toHex() +
               ", decorations=" + decorations +
               ", shadow=" + (shadow == null ? "unset" : shadow.toHex()) +
               '}';
    }
}
