package be.glenndecooman.villagergroupdiscount;

import be.glenndecooman.villagergroupdiscount.listener.OnPlayerPreLogin;
import be.glenndecooman.villagergroupdiscount.listener.OnVillagerCured;
import be.glenndecooman.villagergroupdiscount.listener.OnVillagerInfected;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

public final class VillagerGroupDiscount extends JavaPlugin {
    private static EntityManagerFactory emf;

    @Override
    public void onEnable() {
        connectToDatabase();
        registerListeners();
        getLogger().info("Loaded successfully");
    }

    @Override
    public void onDisable() {
        emf.close();
    }

    private void connectToDatabase() {
        Properties properties = new Properties();
        properties.put("hibernate.hikari.dataSource.url", "jdbc:h2:" + getDataFolder().toPath().toAbsolutePath().resolve("villagerGroupDiscounts"));
        // DO NOT DELETE! See https://www.spigotmc.org/threads/jpa-maven-shade-no-persistence-provider-for-entitymanager.374958/#post-3420503
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

        emf = Persistence.createEntityManagerFactory("persistence-unit", properties);
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new OnPlayerPreLogin(emf.createEntityManager()), this);
        pm.registerEvents(new OnVillagerCured(emf.createEntityManager()), this);
        pm.registerEvents(new OnVillagerInfected(emf.createEntityManager()), this);
    }
}
