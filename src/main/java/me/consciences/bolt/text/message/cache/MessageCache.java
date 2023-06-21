package me.consciences.bolt.text.message.cache;

import lombok.Getter;
import me.consciences.bolt.placeholder.PlaceholderReplacer;
import me.consciences.bolt.text.message.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MessageCache {

    private final FileConfiguration config;
    private final Map<String, Message> messages;

    public MessageCache(final FileConfiguration config) {
        this.config = config;
        this.messages = new HashMap<>();
    }

    public MessageCache sendMessage(Player player, String path) {
        if (this.hasMessage(path)) {
            this.getMessage(path).send(player);
        }
        return this;
    }

    public MessageCache sendMessage(CommandSender sender, String path) {
        if (this.hasMessage(path)) {
            this.getMessage(path).send(sender);
        }
        return this;
    }

    public MessageCache sendMessage(Player player, PlaceholderReplacer placeholders, String path) {
        if (this.hasMessage(path)) {
            this.getMessage(path).send(player, placeholders);
        }
        return this;
    }

    public MessageCache sendMessage(CommandSender sender, PlaceholderReplacer placeholders, String path) {
        if (this.hasMessage(path)) {
            this.getMessage(path).send(sender, placeholders);
        }
        return this;
    }

    public MessageCache sendMessage(Player player, String path, PlaceholderReplacer placeholders) {
        if (this.hasMessage(path)) {
            this.getMessage(path).send(player, placeholders);
        }
        return this;
    }

    public MessageCache sendMessage(CommandSender sender, String path, PlaceholderReplacer placeholders) {
        if (this.hasMessage(path)) {
            this.getMessage(path).send(sender, placeholders);
        }
        return this;
    }

    public MessageCache broadcast(String path) {
        if (this.hasMessage(path)) {
            this.getMessage(path).broadcast();
        }
        return this;
    }

    public MessageCache broadcast(String path, PlaceholderReplacer replacer) {
        if (this.hasMessage(path)) {
            this.getMessage(path).broadcast(replacer);
        }
        return this;
    }

    public MessageCache loadMessage(String path) {
        messages.put(path.toLowerCase(), new Message(this.config, path));
        return this;
    }

    public boolean hasMessage(String path) {
        return messages.containsKey(path.toLowerCase());
    }

    public Message getMessage(String path) {
        return messages.get(path.toLowerCase());
    }

    public MessageCache cacheMessages(final String path) {
        for (String key : this.config.getConfigurationSection(path).getKeys(false)) {
            this.loadMessage(path + "." + key);
        }

        return this;
    }
}
