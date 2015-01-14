package tileengine.items;

import org.lwjgl.opengl.GL11;
import tileengine.GameHandler;

public class Item {

    public boolean equipable;
    public int equipSlot;
    public boolean usable;
    public int texture;
    public int prize;
    public boolean selected;
    public String text = ""
            + "Bronze helmet                 "
            + "This bronze helmet is crafted "
            + "by the greatest blacksmiths in"
            + "Aladuom.                      ";

    public Item() {
        this.texture = 1;
        usable = false;
        equipable = false;
        selected = false;
    }
    
    public Item(int texture) {
        this.texture = texture;
        usable = false;
        equipable = false;
        selected = false;
    }

    public Item(int texture, boolean usable) {
        this.texture = texture;
        this.usable = usable;
        selected = false;
    }

    public Item(int texture, boolean usable, boolean equipable) {
        this.texture = texture;
        this.usable = usable;
        this.equipable = equipable;
        selected = false;
    }

    public void draw(int posX, int posY, int size) {
        int selectedTexture = texture, selectedColumn = 0, selectedRow = 0;        
        //pieni häksi for loopin avulla valitun texturen selvittämiseksi
        for (selectedColumn = selectedTexture; selectedColumn >= GameHandler.spriteSheetX / GameHandler.spriteSheetScale; selectedColumn -= 32) {
            selectedRow++;
        }

        float textureXOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedColumn;
        float textureYOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedRow;

        GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
        GL11.glVertex2f(posX + size, posY);

        GL11.glTexCoord2f(textureXOffSet, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
        GL11.glVertex2f(posX, posY);

        GL11.glTexCoord2f(textureXOffSet, textureYOffSet);
        GL11.glVertex2f(posX, posY + size);

        GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet);
        GL11.glVertex2f(posX + size, posY + size);
    }    
}