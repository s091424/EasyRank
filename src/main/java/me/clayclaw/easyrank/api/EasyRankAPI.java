package me.clayclaw.easyrank.api;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class EasyRankAPI {

    public static void addRank(Player player, String rankId){
        if(player.hasPermission("rank." + rankId)) return;
        // Objects.requireNonNull(Bukkit.getServer().getServicesManager().getRegistration(Permission.class).getProvider(), "Permission provider not found");
        // Bukkit.getServer().getServicesManager().getRegistration(Permission.class).getProvider().playerAdd(player, "rank." + rankId);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " add rank." + rankId);
        System.out.println("CMD: permission added " + rankId);
    }

}
