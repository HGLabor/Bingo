package de.hglabor.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Renderer extends MapRenderer {

    private BufferedImage img1 = null;
    private BufferedImage img2 = null;
    private BufferedImage img3 = null;
    private BufferedImage img4 = null;
    private BufferedImage img5 = null;
    private BufferedImage img6 = null;
    private BufferedImage img7 = null;
    private BufferedImage img8 = null;
    private BufferedImage img9 = null;
    private BufferedImage img10 = null;
    private BufferedImage back;

    {
        try {
            back = ImageIO.read(getClass().getResourceAsStream("/textures/" + "back" + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String s;
            InputStream input;
            s = BingoManager.getNeededMaterials().get(0).name().toLowerCase();
            try {
                input = getClass().getResourceAsStream("/textures/" + s + ".png");
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
            }
            try {
                img1 = ImageIO.read(input);
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
                img1 = ImageIO.read(input);
            }
            s = BingoManager.getNeededMaterials().get(1).name().toLowerCase();
            try {
                input = getClass().getResourceAsStream("/textures/" + s + ".png");
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
            }
            try {
                img2 = ImageIO.read(input);
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
                img2 = ImageIO.read(input);
            }
            s = BingoManager.getNeededMaterials().get(2).name().toLowerCase();
            try {
                input = getClass().getResourceAsStream("/textures/" + s + ".png");
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
            }
            try {
                img3 = ImageIO.read(input);
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
                img3 = ImageIO.read(input);
            }
            s = BingoManager.getNeededMaterials().get(3).name().toLowerCase();
            try {
                input = getClass().getResourceAsStream("/textures/" + s + ".png");
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
            }
            try {
                img4 = ImageIO.read(input);
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
                img4 = ImageIO.read(input);
            }
            s = BingoManager.getNeededMaterials().get(4).name().toLowerCase();
            try {
                input = getClass().getResourceAsStream("/textures/" + s + ".png");
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
            }
            try {
                img5 = ImageIO.read(input);
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
                img5 = ImageIO.read(input);
            }
            s = BingoManager.getNeededMaterials().get(5).name().toLowerCase();
            try {
                input = getClass().getResourceAsStream("/textures/" + s + ".png");
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
            }
            try {
                img6 = ImageIO.read(input);
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
                img6 = ImageIO.read(input);
            }
            s = BingoManager.getNeededMaterials().get(6).name().toLowerCase();
            try {
                input = getClass().getResourceAsStream("/textures/" + s + ".png");
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
            }
            try {
                img7 = ImageIO.read(input);
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
                img7 = ImageIO.read(input);
            }
            s = BingoManager.getNeededMaterials().get(7).name().toLowerCase();
            try {
                input = getClass().getResourceAsStream("/textures/" + s + ".png");
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
            }
            try {
                img8 = ImageIO.read(input);
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
                img8 = ImageIO.read(input);
            }
            s = BingoManager.getNeededMaterials().get(8).name().toLowerCase();
            try {
                input = getClass().getResourceAsStream("/textures/" + s + ".png");
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
            }
            try {
                img9 = ImageIO.read(input);
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
                img9 = ImageIO.read(input);
            }
            s = BingoManager.getNeededMaterials().get(9).name().toLowerCase();
            try {
                input = getClass().getResourceAsStream("/textures/" + s + ".png");
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
            }
            try {
                img10 = ImageIO.read(input);
            } catch (Exception e) {
                input = getClass().getResourceAsStream("/textures/" + "NOTEXTURE" + ".png");
                img10 = ImageIO.read(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        try {
            //canvas.drawImage(0, 0, back);
            canvas.drawText(40, 1, MinecraftFont.Font, "HGLABOR.DE");
            canvas.drawImage(5, 10, img1);
            canvas.drawImage(25, 10, img2);
            canvas.drawImage(45, 10, img3);
            canvas.drawImage(65, 10, img4);
            canvas.drawImage(85, 10, img5);
            canvas.drawImage(105, 30, img6);
            canvas.drawImage(5, 30, img7);
            canvas.drawImage(25, 30, img8);
            canvas.drawImage(45, 30, img9);
            canvas.drawImage(65, 30, img10);
            canvas.getMapView().setLocked(true);
            map.setWorld(Bukkit.getWorld("world_the_end"));
        } catch (Exception e) {

        }
    }
}
