package de.hglabor.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class BingoManager {


    private static ArrayList<Material> needed = new ArrayList<>();
    private static HashMap<Player, ArrayList<Material>> checked = new HashMap<Player, ArrayList<Material>>();

    public BingoManager() {
        needed.add(ItemGenerator.getRandomMaterial());
        needed.add(ItemGenerator.getRandomMaterial());
        needed.add(ItemGenerator.getRandomMaterial());
        needed.add(ItemGenerator.getRandomMaterial());
        needed.add(ItemGenerator.getRandomMaterial());
        needed.add(ItemGenerator.getRandomMaterial());
        needed.add(ItemGenerator.getRandomMaterial());
        needed.add(ItemGenerator.getRandomMaterial());
        needed.add(ItemGenerator.getRandomMaterial());
        needed.add(ItemGenerator.getRandomMaterial());
        needed.add(ItemGenerator.getRandomMaterial());
    }

    public static String getItemName(Material material) {
        String itemName = material.name().toLowerCase();
        String[] itemNameSplit = itemName.split("_");
        itemName = "";
        for (String s : itemNameSplit) {
            itemName = itemName + s.replaceFirst(s.charAt(0) + "", (s.charAt(0) + "").toUpperCase()) + " ";
        }
        return itemName;
    }

    public static ArrayList<Material> getNeededMaterials() {
        return needed;
    }

    public static Boolean hasChecked(Player player, Material material) {
        if(needed.contains(material)) {
            try {
                if(checked.get(player).contains(material)) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static void setChecked(Player player, Material material, Boolean bool) {
        if(needed.contains(material)) {
            if(bool) {
                if (!checked.containsKey(player)) {
                    checked.put(player, new ArrayList<Material>());
                }
                checked.get(player).add(material);
            } else {
                if (!checked.containsKey(player)) {
                    checked.put(player, new ArrayList<Material>());
                }
                checked.get(player).remove(material);
            }
        }
    }

    public static ArrayList<Material> getCheckedItems(Player player) {
        return checked.get(player);
    }

}
