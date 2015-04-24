package io.github.dembeor.terrainsavior;

import java.io.File;
import java.io.IOException;

import io.github.dembeor.terrainmanager.TerrainManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.FilenameException;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;


public final class TerrainSavior {
	Player player;
	World world;
	Location l1; // Location representing one corner of the region
	Location l2; // Location representing the corner opposite to <l1>;
	// ensure WorldEdit is available
	Plugin plugin;
	WorldEditPlugin wep = (WorldEditPlugin)Bukkit.getPluginManager().getPlugin("WorldEdit");
	{	 }
	// create a terrain manager object
	TerrainManager tm_player = new TerrainManager(wep, player);
	// OR - without needing an associated Player
	TerrainManager tm_machine = new TerrainManager(wep, world);
		 
	// don't include an extension - TerrainManager will auto-add ".schematic"
	File saveFile = new File(plugin.getDataFolder(), "backup1");
			 
	boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ts")) { // If the player typed /basic then do the following...
			if (args[0].equalsIgnoreCase("save") && args.length > 2)
			{
			try 
			{
			tm_machine.saveTerrain(saveFile, l1, l2);
			} catch (FilenameException e) {
				// thrown by WorldEdit - it doesn't like the file name/location etc.
			} catch (DataException e) {
				// thrown by WorldEdit - problem with the data
			} catch (IOException e) {
				// problem with creating/writing to the file
			}
			return true;
			}
			// reload a schematic
			else if (args[0].equalsIgnoreCase("load") && args.length > 2)
			{
			try {
				double x=Double.valueOf(args[2]);
				double y=Double.valueOf(args[3]);
				double z=Double.valueOf(args[4]);
				
				// reload at the given location
				Location location1 = new Location(world, x, y, z);
				tm_machine.loadSchematic(saveFile, location1);
				// OR
				// reload at the same place it was saved
				tm_machine.loadSchematic(saveFile);
			} catch (FilenameException e) {
				// thrown by WorldEdit - it doesn't like the file name/location etc.
			} catch (DataException e) {
				// thrown by WorldEdit - problem with the data
			} catch (IOException e) {
				// problem with opening/reading the file
			} catch (MaxChangedBlocksException e) {
				// thrown by WorldEdit - the schematic is larger than the configured block limit for the player
			} catch (EmptyClipboardException e) {
				// thrown by WorldEdit - should be self-explanatory
			}
		return true;
		};
	}
		return false;
}
}