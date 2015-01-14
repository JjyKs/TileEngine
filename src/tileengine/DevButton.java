package tileengine;

public class DevButton {

    static MenuItem devButton, mapEditorButton, textureSelector, plusSign1, plusSign2, minusSign1, minusSign2;

    public static void init() {
        devButton = new MenuItem(GameHandler.scaleXRes - 190, 0);
        devButton.setText("FPS LIMIT");
    }

    public static void update() {
        if (GameHandler.developerMenuActive) {
            if (devButton.clicked()) {
                GameHandler.fpsLimit = !GameHandler.fpsLimit;
            }
            devButton.update();

        }
    }
}
