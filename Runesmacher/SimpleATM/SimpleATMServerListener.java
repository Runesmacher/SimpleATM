package Runesmacher.SimpleATM;

// Imports for MyPlugin
import Runesmacher.register.payment.Methods;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

public class SimpleATMServerListener extends ServerListener {

    private SimpleATM plugin;
    private Methods Methods = null;

    public SimpleATMServerListener(SimpleATM plugin) {
        this.plugin = plugin;
        this.Methods = new Methods();
    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        // Check to see if the plugin thats being disabled is the one we are using
        if (this.Methods != null && this.Methods.hasMethod()) {
            Boolean check = this.Methods.checkDisabled(event.getPlugin());

            if (check) {
                this.plugin.method = null;
                System.out.println(this.plugin.info.getName() + " Payment method was disabled. No longer accepting payments.");
            }
        }
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        // Check to see if we need a payment method
        if (!this.Methods.hasMethod()) {
            if (this.Methods.setMethod(event.getPlugin())) {
                this.plugin.method = this.Methods.getMethod();
                System.out.println(this.plugin.info.getName() + " hooked into " + this.plugin.method.getName() + " version: " + this.plugin.method.getVersion());
            }
        }
    }
}
