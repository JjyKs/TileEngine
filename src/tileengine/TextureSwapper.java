package tileengine;

import tileengine.GameHandler;

public class TextureSwapper extends Tile {
    int startTexture;
    
    public TextureSwapper(int texture, boolean interactable, boolean walkable) {
        super(texture, interactable, walkable);
        startTexture = texture;
    }
    
    @Override
    public void onInteraction() {
        texture += 32;
        GameHandler.interacting = false;
    }
    
    @Override
    public void reset(){
        texture = startTexture;
    }

}
