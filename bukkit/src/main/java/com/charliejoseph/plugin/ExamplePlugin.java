package com.charliejoseph.plugin;

import com.charliejoseph.sdk.ExampleSDK;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExamplePlugin extends JavaPlugin {
    private ExampleSDK sdk;

    @Override
    public void onEnable() {
       saveDefaultConfig();
       sdk = new ExampleSDK();

       sdk.doSomething();
    }
}
