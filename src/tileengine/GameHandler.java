package tileengine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import org.lwjgl.input.Mouse;
import tileengine.quests.*;

public class GameHandler {

    //Pelin oliomuuttujat
    static Camera camera;
    static Player player;

    //Pelaajan muuttujat
    public static int playerX = 55 * 64, playerY = 64;
    public static String playerLookingAt = "up";
    public static boolean interacting = false;
    public static int playerAnimationTimer = 0;
    public static ItemList inventory = new ItemList();
    public static int realMouseX = (int) ((float) Mouse.getX() / GameHandler.xRes * GameHandler.scaleXRes);
    public static int realMouseY = (int) ((float) Mouse.getY() / GameHandler.xRes * GameHandler.scaleXRes);

    //Kameran muuttujat
    public static int cameraX = 0, cameraY = 0;

    //Kartta
    public static Tile[][][] map = new Tile[1024][1024][2];
    public static int spriteSheetX = 0;
    public static int spriteSheetY = 0;
    public static int spriteSheetScale = 32;

    //Pelin omia muuttujia
    public static int xRes = 1024, yRes = 768;
    public static float renderPercent = 1f;
    public static int scaleXRes = (int) (xRes * renderPercent), scaleYRes = (int) (yRes * renderPercent);
    public static int preRenderDistance = scaleXRes / spriteSheetScale / 2 + 1;
    public static int fps = 0;
    public static long lastFPS;

    //Tekstiboksin muuttujat
    public static boolean showTextBox = false;
    public static String talker = "";
    public static String textToDraw = ""
            + "                                     "
            + "                                     "
            + "                                     "
            + "                                     "
            + "                                     "
            + "                                     ";

    //Globaaleja näppäimistö/hiiri muuttujia
    public static boolean ePressed = false;

    //Animaatioiden näyttäjät listassa. Voimme näyttää kerralla 64 eri animaatiota
    public static AnimationPlayer[] animationPlayerArray = new AnimationPlayer[64];

    //Questit
    public static Quest introduction = new Introduction();

    //Pelitilanteen muuttujia
    public static boolean mainMenuActive = false;
    public static boolean inventoryActive = false;

    //Devausmuuttujat
    public static boolean developerMenuActive = false;
    public static boolean fpsLimit = true;
    public static boolean mapEditing = false;

    public static int overlayX = 0;
    public static int overlayY = 1;
    public static int overlayTexture = 2;

    public static boolean showTextureList;
    public static int textureSelectorXOffSet = 0;
    public static int textureSelectorYOffSet = -4;

    //Updatelooptimer (mapin animointi atm)
    public static int loopTime = 0;

    static void initAnimationPlayer() {
        for (int x = 0; x < animationPlayerArray.length; x++) {
            animationPlayerArray[x] = null;
        }
    }

    //Musiikin muuttujat
    public static float musicVolume = 0.025f;
    public static float effectVolume = 0.025f;

    static int addAnimation(int startX, int startY, int targetX, int targetY, int textureStart) {
        for (int x = 0; x < animationPlayerArray.length; x++) {
            if (animationPlayerArray[x] == null) {
                animationPlayerArray[x] = new AnimationPlayer(startX, startY, targetX, targetY, textureStart);
                return x;
            } else {
                if (animationPlayerArray[x].finished) {
                    animationPlayerArray[x] = null;
                    x--;
                }
            }
        }
        return -1;
    }

    static boolean animationRunning() {
        boolean isRunning = false;
        for (int x = 0; x < animationPlayerArray.length; x++) {
            if (animationPlayerArray[x] != null) {
                if (animationPlayerArray[x].active) {
                    isRunning = true;
                }
            }
        }
        return isRunning;
    }

    static void animateMap() {
        
        int xMin = Math.max(playerX/64-32, 0);
        int xMax = Math.min(playerX/64+32, map[1].length - 1);
        int yMin = Math.max(playerY/64-32, 0);
        int yMax = Math.min(playerY/64+32, map[1].length - 1);
        
        if (loopTime % 8 == 0) {
            for (int x = xMin; x < xMax; x++) {
                for (int y = yMin; y < yMax; y++) {
                    int textureNumber = map[x][y][0].getTexture();
                    if (textureNumber == 65 || textureNumber == 66 || textureNumber == 97 || textureNumber == 98) {
                        Random rand = new Random();
                        int randomNum = rand.nextInt((4 - 1) + 1) + 1;
                        if (randomNum == 1) {
                            map[x][y][0].setTexture(65);
                        } else if (randomNum == 2) {
                            map[x][y][0].setTexture(66);
                        } else if (randomNum == 3) {
                            map[x][y][0].setTexture(97);
                        } else if (randomNum == 4) {
                            map[x][y][0].setTexture(98);
                        }
                    }
                }
            }
        }
    }

    static void setTextureWall(int x, int y, int texture) {
        map[x][y][0].isWall();
        map[x][y][0].setTexture(texture);
    }

    static void fillMap(int texture) {
        
        
        Map mapToLoad = new Map();
        
       
        
        

        try {
            FileInputStream fileIn = new FileInputStream("map.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mapToLoad = (Map) in.readObject();
            in.close();
            fileIn.close();
            
        } catch (Exception i) {
            i.printStackTrace();
            return;
        }
        
        map = mapToLoad.map;

    }

    static void init() {
        camera = new Camera();
        player = new Player();

    }

    static void update() {
        loopTime++;
        if (loopTime == 63) {
            loopTime = 0;
        }
        realMouseX = (int) ((float) Mouse.getX() / GameHandler.xRes * GameHandler.scaleXRes);
        realMouseY = (int) ((float) Mouse.getY() / GameHandler.xRes * GameHandler.scaleXRes);
        animateMap();
    }
}
