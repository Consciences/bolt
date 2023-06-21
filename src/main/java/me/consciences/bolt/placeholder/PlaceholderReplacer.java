package me.consciences.bolt.placeholder;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Accessors(chain = true)
public class PlaceholderReplacer {

    private final Map<String, String> placeholders = new HashMap<>();

    public PlaceholderReplacer addPlaceholder(final String key, final String value) {
        this.placeholders.put(key, value);
        return this;
    }

    public String parse(String args) {
        for (final Map.Entry<String, String> entry : this.placeholders.entrySet()) {
            args = args.replace(entry.getKey(), entry.getValue());
        }

        return args;
    }

    public List<String> parse(final List<String> list) {

        final List<String> cloned = new ArrayList<>(list);

        for (int i = 0; i < cloned.size(); i++) {
            cloned.set(i, this.parse(cloned.get(i)));
        }

        return cloned;
    }
}

