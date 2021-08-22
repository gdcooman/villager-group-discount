package be.glenndecooman.villagergroupdiscount.util;

import org.bukkit.plugin.Plugin;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

public class JPAUtil {
    private static EntityManagerFactory emf;

    public static void initialize(Plugin plugin) {
        Properties properties = new Properties();
        properties.put("hibernate.hikari.dataSource.url", "jdbc:h2:" + plugin.getDataFolder().toPath().toAbsolutePath().resolve("villagerGroupDiscounts"));
        // DO NOT DELETE! See https://www.spigotmc.org/threads/jpa-maven-shade-no-persistence-provider-for-entitymanager.374958/#post-3420503
        Thread.currentThread().setContextClassLoader(plugin.getClass().getClassLoader());

        emf = Persistence.createEntityManagerFactory("persistence-unit", properties);
    }

    public static EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public static void close(){
        emf.close();
    }
}
