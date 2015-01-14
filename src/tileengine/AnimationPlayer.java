package tileengine;

import org.lwjgl.opengl.GL11;

public class AnimationPlayer {

    int startX;
    int startY;
    int targetX;
    int targetY;
    int textureStart;
    int animationTimer;
    boolean active;
    boolean finished;

    public AnimationPlayer(int startX, int startY, int targetX, int targetY, int textureStart) {
        this.startX = startX;
        this.startY = startY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.textureStart = textureStart;
        animationTimer = 0;
        active = true;
        finished = false;
    }

    public void update() {
        int movementSpeed = 2;
        if (active) {
            int x = startX;
            int y = startY;      
            
            int selectedColumn = 0, selectedRow = 0;
            
            //pieni häksi for loopin avulla valitun texturen selvittämiseksi
            for (selectedColumn = textureStart; selectedColumn >= GameHandler.spriteSheetX / GameHandler.spriteSheetScale; selectedColumn -= 32) {
                selectedRow++;
            }
            selectedColumn += animationTimer / 8;

            float textureXOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedColumn;
            float textureYOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedRow;

            GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
            GL11.glVertex2f(64 + x, 0 + y);

            GL11.glTexCoord2f(textureXOffSet, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
            GL11.glVertex2f(0 + x, 0 + y);

            GL11.glTexCoord2f(textureXOffSet, textureYOffSet);
            GL11.glVertex2f(0 + x, 64 + y);

            GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet);
            GL11.glVertex2f(64 + x, 64 + y);
            
            if (startX < targetX){
                startX += movementSpeed;
                animationTimer++;
            }
            if (startX > targetX){
                startX -= movementSpeed;
                animationTimer++;
            }
            if (startY < targetY){
                startY += movementSpeed;
                animationTimer++;
            }
            if (startY > targetY){
                startY -= movementSpeed;
                animationTimer++;
            }
        }
        
        if (startX == targetX && startY == targetY && active){            
            active = false;
        }
    }
    public void finish(){
        finished = true;
    }
}

