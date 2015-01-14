package tileengine;

import org.lwjgl.input.Mouse;

public class MainMenu {

    public static MenuItem[] menuItems;

    public static void init() {
        menuItems = new MenuItem[5];
        for (int i = 0; i < 5; i++) {
            menuItems[i] = new MenuItem(i * 208, GameHandler.scaleYRes - 64);
            if (i == 0) {
                menuItems[i].setText("status");
            }
            if (i == 1) {
                menuItems[i].setText("inventory");
            }
            if (i == 2) {
                menuItems[i].setText("quests");
            }
            if (i == 3) {
                menuItems[i].setText("save");
            }
            if (i == 4) {
                menuItems[i].setText("dev menu");
            }
        }
    }

    public static void update() {
        boolean deactivate = false;
        if (menuItems[0].clicked()) {
            deactivate = true;
        }
        if (menuItems[1].clicked()) {
            GameHandler.inventoryActive = !GameHandler.inventoryActive ;            
        }
        if (menuItems[2].clicked()) {
            deactivate = true;
        }
        if (menuItems[3].clicked()) {
            deactivate = true;
        }
        if (menuItems[4].clicked()) {
            GameHandler.developerMenuActive = !GameHandler.developerMenuActive;
            deactivate = true;
        }
        if (GameHandler.mainMenuActive) {
            if ((float) Mouse.getY() / GameHandler.yRes * GameHandler.scaleYRes < GameHandler.scaleYRes - 128 && Mouse.isButtonDown(0) || deactivate) {
                GameHandler.mainMenuActive = false;
            }
            boolean buttonClicked = false;
            for (int i = 0; i < 5; i++) {
                menuItems[i].update();
            }
        }

    }
}
