package me.consciences.bolt.builder;

import me.consciences.bolt.text.Color;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

public final class ComponentBuilder {

    private final TextComponent textComponent;

    /**
     * Main constructor of our component builder. Takes in a text prompt.
     *
     * @param text - The initial text provided.
     */

    public ComponentBuilder(final String text) {
        this.textComponent = new TextComponent(TextComponent.fromLegacyText(Color.parse(text)));
    }

    /**
     *
     * Adds text that can be hovered over!
     * @param text - String being displayed
     *
     * @return Instance of ComponentBuilder
     */

    public ComponentBuilder hoverText(final String text) {
        this.textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(Color.parse(text))));
        return this;
    }

    /**
     *
     * Adds text that can be hovered over!
     * @param text - Strings being displayed!
     *
     * @return Instance of ComponentBuilder
     */

    public ComponentBuilder hoverText(final String... text) {
        final StringBuilder builder = new StringBuilder();

        for (final String string : text) {
            if (!builder.toString().isEmpty()) {
                builder.append("\n").append(string);
                continue;
            }

            builder.append(string);
        }
        this.textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(Color.parse(builder.toString()))));
        return this;
    }

    /**
     *
     * Adds text that can be hovered over!
     * @param text - Strings being displayed!
     *
     * @return Instance of ComponentBuilder
     */

    public ComponentBuilder hoverText(final List<String> text) {
        final StringBuilder builder = new StringBuilder();

        for (final String string : text) {
            if (!builder.toString().isEmpty()) {
                builder.append("\n").append(string);
                continue;
            }

            builder.append(string);
        }

        this.textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(Color.parse(builder.toString()))));
        return this;
    }

    /**
     *
     * Inserts text into a player's text box.
     * @param suggestion - String inserted into the players text box.
     *
     * @return Instance of ComponentBuilder
     */

    public ComponentBuilder suggest(final String suggestion) {
        this.textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggestion));
        return this;
    }

    /**
     *
     * Forces the player to run a command.
     * @param text - Command ran by player
     *
     * @return Instance of ComponentBuilder
     */

    public ComponentBuilder chat(final String text) {
        this.textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, text));
        return this;
    }

    /**
     *
     * Copies text to a player's clipboard
     * @param text - String copied by the player.
     *
     * @return Instance of ComponentBuilder
     */

    public ComponentBuilder copyToClipboard(final String text) {
        this.textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text));
        return this;
    }

    /**
     *
     * Allows a player to open a URL.
     * @param url - String set as the URL.
     *
     * @return Instance of ComponentBuilder
     */

    public ComponentBuilder openUrl(final String url) {
        this.textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return this;
    }

    /**
     *
     * Builds a {@link TextComponent} class.
     *
     * @return A TextComponent class based off of the settings provided.
     */

    public TextComponent build() {
        return this.textComponent;
    }
}

