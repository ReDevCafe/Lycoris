package org.momento.lycoris;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.momento.lycoris.agent.JavaAgent;

import javax.xml.transform.Transformer;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Lycoris extends JavaPlugin {

    public static Logger LOGGER;

    @Override
    public void onEnable() {
        LOGGER = getLogger();
        Instrumentation inst;
        try {
            inst = JavaAgent.load();
        } catch (Exception error) {
            LOGGER.log(Level.SEVERE, error.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
