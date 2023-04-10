package com.charliejoseph.plugin;

import com.charliejoseph.sdk.ExampleSDK;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(
        id = "exampleplugin",
        name = "ExamplePlugin",
        version = "@VERSION@",
        description = "The example Velocity plugin.",
        url = "https://charliejoseph.com",
        authors = {"CharlieJoseph"}
)
public final class ExamplePlugin {
    private final ProxyServer proxy;
    private final Logger logger;
    private final Path dataDirectory;
    private final ExampleSDK exampleSDK;

    @Inject
    public ExamplePlugin(ProxyServer proxy, Logger logger, @DataDirectory Path dataDirectory) {
        this.proxy = proxy;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.exampleSDK = new ExampleSDK();
    }

    @Subscribe
    public void onEnable(ProxyInitializeEvent event) {
        exampleSDK.doSomething();
    }
}
