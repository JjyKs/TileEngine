package tileengine;

import tileengine.items.*;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public class ItemList {

    public ArrayList<Item> list = new ArrayList<Item>();
    int scrollOffSet = 0;

    public ItemList() {
        list.add(new Item(416));
        list.add(new MagicHelmet());
        list.add(new Item(416));
        list.add(new MagicHelmet());
        list.add(new Item(416));
        list.add(new MagicHelmet());
        list.add(new Item(416));
        list.add(new MagicHelmet());
        list.add(new Item(416));
        list.add(new MagicHelmet());
        list.add(new Item(416));
        list.add(new MagicHelmet());
        list.add(new Item(416));
        list.add(new MagicHelmet());
        list.add(new Item(416));
        list.add(new MagicHelmet());

    }

    public void drawItems(int menuX, int menuY, int size, int xSize, int ySize) {
        if (GameHandler.inventoryActive) //Piirretään taustalaatikko
        {
            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    int selectedTexture = 261, selectedColumn = 0, selectedRow = 0;;
                    //pieni häksi for loopin avulla valitun texturen selvittämiseksi
                    for (selectedColumn = selectedTexture; selectedColumn >= GameHandler.spriteSheetX / GameHandler.spriteSheetScale; selectedColumn -= 32) {
                        selectedRow++;
                    }

                    if (y == 0) {
                        selectedRow += 1;
                    }
                    if (y == ySize - 1) {
                        selectedRow -= 1;
                    }
                    if (x == 0) {
                        selectedColumn -= 1;
                    }
                    if (x == xSize - 1) {
                        selectedColumn += 1;
                    }

                    float textureXOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedColumn;
                    float textureYOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedRow;

                    GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
                    GL11.glVertex2f(menuX + x * size + size, menuY + y * size);

                    GL11.glTexCoord2f(textureXOffSet, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
                    GL11.glVertex2f(menuX + x * size, menuY + y * size);

                    GL11.glTexCoord2f(textureXOffSet, textureYOffSet);
                    GL11.glVertex2f(menuX + x * size, menuY + y * size + size);

                    GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet);
                    GL11.glVertex2f(menuX + x * size + size, menuY + y * size + size);
                }
            }

            //Piirretään itemit
            int i = 0;
            int x = 0;
            boolean showInfo = false;
            for (Item item : list) {
                if (i >= xSize / 2) {
                    x++;
                    i = 0;
                }
                if (GameHandler.realMouseX >= menuX + i * (size * 2) + 1 && GameHandler.realMouseX < menuX + i * (size * 2) + 1 + size * 2
                        && GameHandler.realMouseY >= menuY + x * (size * 2) + 1 && GameHandler.realMouseY < menuY + x * (size * 2) + 1 + size * 2) {
                    item.draw(menuX + i * (size * 2), x * (size * 2) + menuY + 8, (size + 1) * 2);
                    item.selected = true;
                    //Piirretään tavaran tiedot
                    drawText(item.text, 450, 0, size);
                } else {
                    item.draw(menuX + i * (size * 2), x * (size * 2) + menuY + 8, size * 2);
                    item.selected = false;
                }
                i++;
            }
        }
    }

    public void drawText(String sana, int xPos, int yPos, int size) {
        for (int x = 0; x < 18; x++) {
            for (int y = 0; y < 4; y++) {
                int selectedTexture = 261, selectedColumn = 0, selectedRow = 0;;
                //pieni häksi for loopin avulla valitun texturen selvittämiseksi
                for (selectedColumn = selectedTexture; selectedColumn >= GameHandler.spriteSheetX / GameHandler.spriteSheetScale; selectedColumn -= 32) {
                    selectedRow++;
                }

                if (y == 0) {
                    selectedRow += 1;
                }
                if (y == 4 - 1) {
                    selectedRow -= 1;
                }
                if (x == 0) {
                    selectedColumn -= 1;
                }
                if (x == 18 - 1) {
                    selectedColumn += 1;
                }

                float textureXOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedColumn;
                float textureYOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedRow;

                GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
                GL11.glVertex2f(xPos + x * 32 + 32, yPos + y * 32);

                GL11.glTexCoord2f(textureXOffSet, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
                GL11.glVertex2f(xPos + x * 32, yPos + y * 32);

                GL11.glTexCoord2f(textureXOffSet, textureYOffSet);
                GL11.glVertex2f(xPos + x * 32, yPos + y * 32 + 32);

                GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet);
                GL11.glVertex2f(xPos + x * 32 + 32, yPos + y * 32 + 32);
            }
        }
        xPos += 220;
        yPos = -70;
        sana = sana.toLowerCase();
        String aakkoset = "abcdefghijklmnopqrstuvwxyz .-_,";
        int index = 0;
        for (int y = 8; y >= 0; y--) {
            for (int x = 0; x < 30; x++) {
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
                GL11.glVertex2f(xPos + 32 / 2 + x * 32 / 2 - 32 * 6, yPos + y * (32 + 8) / 2);

                GL11.glTexCoord2f(textureXOffSet, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
                GL11.glVertex2f(xPos + x * 32 / 2 - 32 * 6, yPos + y * (32 + 8) / 2);

                GL11.glTexCoord2f(textureXOffSet, textureYOffSet);
                GL11.glVertex2f(xPos + x * 32 / 2 - 32 * 6, yPos + 32 / 2 + y * (32 + 8) / 2);

                GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet);
                GL11.glVertex2f(xPos + 32 / 2 + x * 32 / 2 - 32 * 6, yPos + 32 / 2 + y * (32 + 8) / 2);
            }
        }
    }
}
