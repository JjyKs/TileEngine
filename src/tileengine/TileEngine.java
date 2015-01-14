package tileengine;

import org.lwjgl.Sys;

public class TileEngine {

    
    static OpenGL openGL;
    

    public static void main(String[] args) {
        init();
        update();
    }

    static void init() {
        GameHandler.init();
        GameHandler.fillMap(129);   
        openGL = new OpenGL();
        openGL.start();
        GameHandler.lastFPS = getTime();
        GameHandler.initAnimationPlayer();
        MusicPlayer.init();
        IconLoader.setIcon();
        MainMenu.init();
        DevButton.init();
    }

    static void update() {
        while (!openGL.isCloseRequested) {      
            KeyboardListener.listen();  
            //Piirtokomentoihin liittyvät updatet ovat tämän alla
            openGL.renderGL();
            updateFPS();
            GameHandler.player.update();
            GameHandler.camera.update();
            GameHandler.update();
            MusicPlayer.update();
        }
        MusicPlayer.destroy();
        openGL.destroyGL();
    }

    static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    static void updateFPS() {
        if (getTime() - GameHandler.lastFPS > 1000) {
            openGL.updateTitle();
            GameHandler.fps = 0; //reset the FPS counter
            GameHandler.lastFPS += 1000; //add one second
        }
        GameHandler.fps++;
    }
}
