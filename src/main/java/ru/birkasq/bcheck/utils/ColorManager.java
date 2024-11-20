package ru.birkasq.bcheck.utils;

import org.bukkit.ChatColor;

public class ColorManager {
    public static String toString(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
