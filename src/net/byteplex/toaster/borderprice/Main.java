package net.byteplex.toaster.borderprice;

import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.Config;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Melted on 6/25/2015.
 */
public class Main extends JavaPlugin {
    int points = 0;
    int goal = 500;
    @Override
    public void onEnable() {
        // TODO Insert code to load points saved from previous instances of server
        getLogger().info("BorderPrice is enabled!");
    }

    @Override
    public void onDisable(){
        // TODO Insert code to save points for future instances of server
        getLogger().info("BorderPrice is disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("donate")){
            boolean yes = false;
            // TODO Get item user is holding and add points
            Player player = (Player)sender;
            ItemStack donating = player.getItemInHand();
            getLogger().info(donating.toString());
            if(donating.getType().equals(Material.IRON_INGOT)){
                points = points + (donating.getAmount() * 2); // Add points to server points
                yes = true;
                player.setItemInHand(null);
            } else if(donating.getType().equals(Material.DIAMOND)){
                points = points + (donating.getAmount() * 4); // Add points to server points
                yes = true;
                player.setItemInHand(null);
            } else if(donating.getType().equals(Material.COAL)){
                points = points + (donating.getAmount() * 1);
                yes = true;
                player.setItemInHand(null);
            } else{
                player.sendMessage(ChatColor.RED + "You can't donate that item!");
            }

            if(points >= goal){
                points = points - goal;
                BorderData border = Config.Border("world");
                if (border != null){
                    border.setRadius((int) Math.round(border.getRadius() * 1.5));
                }
                getServer().broadcastMessage(ChatColor.GOLD + "[BP] " + ChatColor.GRAY + "The border is extended to " + ChatColor.AQUA + border.getRadius() + ChatColor.GRAY + " blocks!");
                goal = (int) Math.round(Math.pow((double) goal, 1.2));
            }

            if(yes) {
                getServer().broadcastMessage(ChatColor.GOLD + "[BP] " + ChatColor.AQUA + sender.getName() + ChatColor.GRAY + " has donated towards the WorldBorder cause!");
                getServer().broadcastMessage(ChatColor.GOLD + "[BP] " + ChatColor.GRAY + "Progress: " + ChatColor.AQUA + points + ChatColor.GRAY + "/" + ChatColor.AQUA + goal + "");
            }

            return true;
        }
        return false;
    }
}
