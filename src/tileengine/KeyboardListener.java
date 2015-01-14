package tileengine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeyboardListener {

    static long aPressed = -1, dPressed = -1, wPressed = -1, sPressed = -1;
    static boolean eKeyWasDown = false, qKeyWasDown = false;

    public static void listen() {
       
        //estetään liikkuminen animaation aikana
        if (!GameHandler.animationRunning()) {
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                if (aPressed == -1) {
                    aPressed = getTime();
                }
            } else {
                aPressed = -1;
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                if (dPressed == -1) {
                    dPressed = getTime();
                }
            } else {
                dPressed = -1;
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                if (wPressed == -1) {
                    wPressed = getTime();
                }
            } else {
                wPressed = -1;
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                if (sPressed == -1) {
                    sPressed = getTime();
                }
            } else {
                sPressed = -1;
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                GameHandler.player.sprint();
            }
        }
        GameHandler.ePressed = false;
        if (Keyboard.isKeyDown(Keyboard.KEY_E) && !eKeyWasDown) {
            GameHandler.player.wantInterract = true;
            GameHandler.ePressed = true;
            eKeyWasDown = true;
            MusicPlayer.menuEffect[0].playAsSoundEffect(0.8f, 1f, false);
        } else if (!Keyboard.isKeyDown(Keyboard.KEY_E)) {
            eKeyWasDown = false;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_Q) && !qKeyWasDown) {
            GameHandler.mainMenuActive = !GameHandler.mainMenuActive;
            qKeyWasDown = true;
            MusicPlayer.menuEffect[0].playAsSoundEffect(1f, 1f, false);
            MusicPlayer.menuEffect[1].playAsSoundEffect(1f, 1f, false);
        } else if (!Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            qKeyWasDown = false;
        }

        if (aPressed > dPressed && aPressed > wPressed && aPressed > sPressed) {
            GameHandler.player.moveLeft();
        }
        if (dPressed > aPressed && dPressed > wPressed && dPressed > sPressed) {
            GameHandler.player.moveRight();
        }
        if (wPressed > dPressed && wPressed > aPressed && wPressed > sPressed) {
            GameHandler.player.moveUp();
        }
        if (sPressed > dPressed && sPressed > aPressed && sPressed > wPressed) {
            GameHandler.player.moveDown();
        }
    }

    private static long getTime() {
        return System.nanoTime() / 1000000;
    }
}
