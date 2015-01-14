/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tileengine;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class MenuItem {

    boolean selected;
    boolean clicked;
    int posX, posY;
    int textOffSet = 0;
    String text = "";

    public MenuItem(int posX, int posY) {
        selected = false;
        this.posX = posX;
        this.posY = posY;
    }

    public MenuItem(boolean selected, int posX, int posY) {
        this.selected = selected;
        this.posX = posX;
        this.posY = posY;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public boolean clicked(){
        if (clicked && !Mouse.isButtonDown(0)){
            return true;
        }
        return false;
    }

    public void update() {
        if ((float) Mouse.getX() / GameHandler.xRes * GameHandler.scaleXRes > posX && (float) Mouse.getX() / GameHandler.xRes * GameHandler.scaleXRes < posX + 64 * 3
                && (float) Mouse.getY() / GameHandler.yRes * GameHandler.scaleYRes > posY && (float) Mouse.getY() / GameHandler.yRes * GameHandler.scaleYRes < posY + 64) {
            selected = true;
            textOffSet = 2;
        } else {
            selected = false;
            textOffSet = 0;
        }
        if (selected && Mouse.isButtonDown(0) && !clicked){
            clicked = true;
            MusicPlayer.menuEffect[1].playAsSoundEffect(1f, 1f, false);
            System.out.println("Klikkasit " + text);
        } else if (!Mouse.isButtonDown(0)){
            clicked = false;
        }
        drawBox();
        drawText();
    }

    public void drawBox() {
        for (int x = 0; x < 3; x++) {
            int selectedTexture = 197, selectedColumn = 0, selectedRow = 0;
            //Selvitetään milloin piirretään textboxin reunoja  
            if (x == 0) {
                selectedTexture = 196;
            }
            if (x == 2) {
                selectedTexture = 198;
            }

            if (selected) {
                selectedTexture += 3;
            }

            //pieni häksi for loopin avulla valitun texturen selvittämiseksi
            for (selectedColumn = selectedTexture; selectedColumn >= GameHandler.spriteSheetX / GameHandler.spriteSheetScale; selectedColumn -= 32) {
                selectedRow++;
            }

            float textureXOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedColumn;
            float textureYOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedRow;

            GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
            GL11.glVertex2f(posX + 64 + x * 64, posY);

            GL11.glTexCoord2f(textureXOffSet, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
            GL11.glVertex2f(posX + x * 64, posY);

            GL11.glTexCoord2f(textureXOffSet, textureYOffSet);
            GL11.glVertex2f(posX + x * 64, posY + 64);

            GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet);
            GL11.glVertex2f(posX + 64 + x * 64, posY + 64);

        }

    }

    public void drawText() {
        String sana = text.toLowerCase();
        String aakkoset = "abcdefghijklmnopqrstuvwxyz .-_,";
        int index = 0;

        for (int x = 0; x < 37; x++) {
            int selectedTexture = 0, selectedColumn = 0, selectedRow = 0;
            if (index < sana.length()) {
                selectedTexture = 160 + aakkoset.indexOf(sana.charAt(index));
            } else {
                selectedTexture = 128;
            }
            index++;

            //pieni häksi for loopin avulla valitun texturen selvittämiseksi
            for (selectedColumn = selectedTexture; selectedColumn >= GameHandler.spriteSheetX / GameHandler.spriteSheetScale; selectedColumn -= 32) {
                selectedRow++;
            }

            float textureXOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedColumn;
            float textureYOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedRow;

            GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
            GL11.glVertex2f(posX + 111 + x * 16 - sana.length() / 2 * 16, posY + 20 - textOffSet);

            GL11.glTexCoord2f(textureXOffSet, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
            GL11.glVertex2f(posX + 95 + x * 16 - sana.length() / 2 * 16, posY + 20 - textOffSet);

            GL11.glTexCoord2f(textureXOffSet, textureYOffSet);
            GL11.glVertex2f(posX + 95 + x * 16 - sana.length() / 2 * 16, posY + 36 - textOffSet);

            GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet);
            GL11.glVertex2f(posX + 111 + x * 16 - sana.length() / 2 * 16, posY + 36 - textOffSet);
        }
    }
}
