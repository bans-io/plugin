package io.bans.platform;

import dev.dejvokep.boostedyaml.YamlDocument;

public interface PlatformConfiguration {
    YamlDocument get();

    void load();
    void reload();
}