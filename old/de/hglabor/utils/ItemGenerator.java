package de.hglabor.utils;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ItemGenerator {

    private static ArrayList<Material> materials = new ArrayList<>();

    public ItemGenerator() {
        materials.addAll(Arrays.asList(Material.values()));
        for(Material material : Material.values()) {
            if(material.name().toLowerCase().contains("spawn")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("slab")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("stair")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("wall")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("fence")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("pane")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("command")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("potted")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("carpet")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("_wood")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("head")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("button")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("bed")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("writ")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("banner")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("ice")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("portal")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("air")) {
                materials.remove(material);
            } else if(material.name().toLowerCase().contains("wire")) {
                materials.remove(material);
            }
        }
    }

    public static ArrayList<Material> getMaterials() {
        return materials;
    }

    public static Material getRandomMaterial() {
        return getMaterials().get(new Random().nextInt(getMaterials().size()));
    }

}
