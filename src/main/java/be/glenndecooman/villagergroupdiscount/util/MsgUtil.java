package be.glenndecooman.villagergroupdiscount.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class MsgUtil {
    private static Component prefix = Component.text("[VGD] ").color(TextColor.color(0xF34EFB));

    public static void info(Player player, String message) {
        Component comp = Component.text(message);
        info(player, comp);
    }

    public static void info(Player player, Component component) {
        sendMessage(player, component.color(TextColor.color(0x44C7FF)));
    }

    public static void success(Player player, String message) {
        Component comp = Component.text(message);
        success(player, comp);
    }

    public static void success(Player player, Component component) {
        sendMessage(player, component.color(TextColor.color(0x5FD745)));
    }

    public static void error(Player player, String message) {
        Component comp = Component.text(message);
        error(player, comp);
    }

    public static void error(Player player, Component component) {
        sendMessage(player, component.color(TextColor.color(0xD71C1C)));
    }

    public static void warning(Player player, String message) {
        Component comp = Component.text(message);
        warning(player, comp);
    }

    public static void warning(Player player, Component component) {
        sendMessage(player, component.color(TextColor.color(0xFF9D40)));
    }

    private static void sendMessage(Player player, Component component) {
        Component msg = prefix.append(component);
        player.sendMessage(msg);
    }
}
