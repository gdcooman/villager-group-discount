package be.glenndecooman.villagergroupdiscount;

import be.glenndecooman.villagergroupdiscount.event.OnVillagerCured;
import be.glenndecooman.villagergroupdiscount.event.OnVillagerInfected;
import org.bukkit.plugin.java.JavaPlugin;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

public final class VillagerGroupDiscount extends JavaPlugin {

    @Override
    public void onEnable() {
        connectToDatabase();
        registerEvents();
        getLogger().info("Loaded successfully");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new OnVillagerCured(), this);
        getServer().getPluginManager().registerEvents(new OnVillagerInfected(), this);
    }

    private void connectToDatabase() {
        Properties properties = new Properties();
        properties.put("hibernate.hikari.dataSource.url", "jdbc:h2:" + getDataFolder().toPath().toAbsolutePath().resolve("villagerGroupDiscounts"));
        // DO NOT DELETE! See https://www.spigotmc.org/threads/jpa-maven-shade-no-persistence-provider-for-entitymanager.374958/#post-3420503
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit", properties);
    }
}
