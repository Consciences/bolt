package me.consciences.bolt.text.message;

import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import lombok.Getter;
import me.consciences.bolt.builder.ComponentBuilder;
import me.consciences.bolt.placeholder.PlaceholderReplacer;
import me.consciences.bolt.text.Color;
import me.consciences.bolt.text.enums.ComponentAction;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Getter
public class Message {

    private final boolean messageEnabled,
            hoverTextEnabled,
            clickActionEnabled,
            soundEnabled,
            actionBarEnabled,
            titleEnabled;

    private final int volume,
            pitch,
            fadeInTicks,
            stayTicks,
            fadeOutTicks;

    private final String clickAction,
            sound,
            actionBar,
            title,
            subTitle;

    private final List<String> messages, hoverText;

    private final ComponentAction action;

    public Message(final FileConfiguration config, final String path) {
        this.messageEnabled = config.getBoolean(path + ".chat-message.enabled", false);
        this.messages = Color.parse(config.getStringList(path + ".chat-message.value"));

        this.hoverTextEnabled = config.getBoolean(path + ".chat-message.hover-text.enabled", false);
        this.hoverText = Color.parse(config.getStringList(path + ".chat-message.hover-text.value"));

        this.clickActionEnabled = config.getBoolean(path + ".chat-message.click-action.enabled", false);
        this.action = ComponentAction.valueOf(config.getString(path + ".chat-message.click-action.type", "NULL"));
        this.clickAction = config.getString(path + ".chat-message.click-action.text", "");

        this.soundEnabled = config.getBoolean(path + ".sound.enabled", false);
        this.sound = config.getString(path + ".sound.value", "ENTITY_PLAYER_LEVELUP");
        this.volume = config.getInt(path + ".sound.volume", (int) XSound.DEFAULT_VOLUME);
        this.pitch = config.getInt(path + ".sound.pitch", (int) XSound.DEFAULT_PITCH);

        this.titleEnabled = config.getBoolean(path + ".title.enabled", false);
        this.title = config.getString(path + ".title.title", "");
        this.subTitle = config.getString(path + ".title.sub-title");

        this.fadeInTicks = config.getInt(path + ".title.advanced.fade-in-ticks", 20);
        this.stayTicks = config.getInt(path + ".title.advanced.stay-ticks", 20);
        this.fadeOutTicks = config.getInt(path + ".title.advanced.fade-out-ticks", 20);

        this.actionBarEnabled = config.getBoolean(path + ".action-bar.enabled", false);
        this.actionBar = config.getString(path + ".action-bar.value", "");
    }

    public void send(final Player player, final PlaceholderReplacer replacer) {

        if (this.messageEnabled) {

            if (this.hoverTextEnabled) {

                final List<TextComponent> components = new LinkedList<>();

                for (final String message : this.messages) {

                    final ComponentBuilder builder = new ComponentBuilder(replacer.parse(message));

                    builder.hoverText(replacer.parse(this.hoverText));

                    if (this.clickActionEnabled) {

                        switch (this.action) {
                            case COPY:
                                builder.copyToClipboard(replacer.parse(this.clickAction));
                                break;
                            case OPEN:
                                builder.openUrl(replacer.parse(this.clickAction));
                                break;
                            case COMMAND:
                                builder.chat(replacer.parse(this.clickAction));
                                break;
                            case SUGGEST:
                                builder.suggest(replacer.parse(this.clickAction));
                                break;
                            default:
                                break;
                        }
                    }

                    components.add(builder.build());
                }

                for (TextComponent component : components) {
                    player.spigot().sendMessage(component);
                }

                return;
            }

            for (String message : this.messages) {
                player.sendMessage(Color.parse(replacer.parse(message)));
            }
        }

        if (this.soundEnabled) {
            Optional<XSound> xSoundOptional = XSound.matchXSound(this.sound);
            xSoundOptional.ifPresent(xSound -> xSound.play(player, this.volume, this.pitch));
        }

        if (this.actionBarEnabled) {
            ActionBar.sendActionBar(player, replacer.parse(Color.parse(this.actionBar)));
        }

        if (this.titleEnabled) {
            Titles.sendTitle(player, this.fadeInTicks, this.stayTicks, this.fadeOutTicks,
                    replacer.parse(Color.parse(this.title)), replacer.parse(Color.parse(this.subTitle)));
        }
    }

    public void send(final Player player) {
        this.send(player, new PlaceholderReplacer());
    }

    public void send(final CommandSender player, final PlaceholderReplacer replacer) {

        if (this.messageEnabled) {

            if (this.hoverTextEnabled) {

                final List<TextComponent> components = new LinkedList<>();

                for (final String message : this.messages) {

                    final ComponentBuilder builder = new ComponentBuilder(replacer.parse(message));

                    builder.hoverText(replacer.parse(this.hoverText));

                    if (this.clickActionEnabled) {

                        switch (this.action) {
                            case COPY:
                                builder.copyToClipboard(replacer.parse(this.clickAction));
                                break;
                            case OPEN:
                                builder.openUrl(replacer.parse(this.clickAction));
                                break;
                            case COMMAND:
                                builder.chat(replacer.parse(this.clickAction));
                                break;
                            case SUGGEST:
                                builder.suggest(replacer.parse(this.clickAction));
                                break;
                            default:
                                break;
                        }
                    }

                    components.add(builder.build());
                }

                for (TextComponent component : components) {
                    player.spigot().sendMessage(component);
                }

                return;
            }

            for (String message : this.messages) {
                player.sendMessage(Color.parse(message));
            }
        }

        if (this.soundEnabled) {
            Optional<XSound> xSoundOptional = XSound.matchXSound(this.sound);
            xSoundOptional.ifPresent(xSound -> xSound.play((Player) player, this.volume, this.pitch));
        }

        if (this.actionBarEnabled) {
            ActionBar.sendActionBar((Player) player, replacer.parse(Color.parse(this.actionBar)));
        }

        if (this.titleEnabled) {
            Titles.sendTitle((Player) player, this.fadeInTicks, this.stayTicks, this.fadeOutTicks,
                    replacer.parse(Color.parse(this.title)), replacer.parse(Color.parse(this.subTitle)));
        }
    }

    public void send(final CommandSender player) {
        this.send(player, new PlaceholderReplacer());
    }

    public void broadcast() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            this.send(player);
        }
    }

    public void broadcast(final PlaceholderReplacer replacer) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.send(player, replacer);
        }
    }
}
