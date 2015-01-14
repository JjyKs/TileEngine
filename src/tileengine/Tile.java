package tileengine;

import java.io.Serializable;

public class Tile implements Serializable{

    int texture;
    boolean walkable = false;
    boolean drawable = true;
    boolean interactable = false;
    boolean active = false;

    public Tile() {
        texture = 0;
    }

    public Tile(int texture, boolean interactable, boolean walkable) {
        this.texture = texture;
        this.interactable = interactable;
        this.walkable = walkable;
        drawable = true;
    }

    public void setTexture(int texture) {
        this.texture = texture;
        if (texture == 1 || texture == 65) {
            walkable = true;
        }
        if (texture == 0) {
            walkable = false;
        }
    }

    public int getTexture() {
        return texture;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void onInteraction() {
        System.out.println("INTERAKTATAAN PELAAJAN KANSSA :-DDDDDDD");
    }

    public void reset() {

    }

    public void update() {

    }

    public void playSound(int id) {
        if (GameHandler.ePressed) {
            MusicPlayer.menuEffect[id].playAsSoundEffect(1f, 0.8f, false);
        }
    }

    public void hide() {
        drawable = false;
    }

    public void show() {
        drawable = true;
    }
    
    public void isWall() {
        walkable = false;
    }
}
