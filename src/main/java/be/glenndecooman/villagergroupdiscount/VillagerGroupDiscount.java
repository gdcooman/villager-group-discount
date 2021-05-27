package be.glenndecooman.villagergroupdiscount;

import be.glenndecooman.villagergroupdiscount.command.CommandManager;
import be.glenndecooman.villagergroupdiscount.listener.OnPlayerPreLogin;
import be.glenndecooman.villagergroupdiscount.listener.OnVillagerCured;
import be.glenndecooman.villagergroupdiscount.listener.OnVillagerInfected;
import be.glenndecooman.villagergroupdiscount.persistence.CuredVillagerDAOImpl;
import be.glenndecooman.villagergroupdiscount.persistence.JPAUtil;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAOImpl;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

public final class VillagerGroupDiscount extends JavaPlugin {
    private static EntityManagerFactory emf;

    @Override
    public void onEnable() {
        JPAUtil.initialize(this);
        registerListeners();
        registerCommands();
        getLogger().info("Loaded successfully");
    }

    @Override
    public void onDisable() {
        emf.close();
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new OnPlayerPreLogin(new VGDPlayerDAOImpl()), this);
        pm.registerEvents(new OnVillagerCured(new VGDPlayerDAOImpl(), new CuredVillagerDAOImpl()), this);
        pm.registerEvents(new OnVillagerInfected(new CuredVillagerDAOImpl()), this);
    }

    private void registerCommands() {
        PluginCommand vgd = this.getCommand("vgd");
        if (vgd != null) {
            vgd.setExecutor(new CommandManager());
        }
    }
}
