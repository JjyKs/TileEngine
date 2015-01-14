package tileengine;

import tileengine.GameHandler;
import tileengine.Tile;

public class TextEmitter extends Tile {

    int state = 0;

    public TextEmitter(int texture, boolean interactable, boolean walkable) {
        super(texture, interactable, walkable);
    }

    @Override
    public void onInteraction() {
        active = true;
    }

    @Override
    public void reset() {
        state = 0;
        active = false;
    }

    @Override
    public void update() {
        if (active) {
            if (GameHandler.ePressed) {
                state++;
            }
            if (state == 1) {
                GameHandler.showTextBox = true;
                GameHandler.textToDraw = ""
                        + "         Text Emitter Object         "
                        + "                                     "
                        + "                                     "
                        + "Hi, here we are printing with        "
                        + "drawText method from textemitter     "
                        + "object. it uses bitmap images as a   "
                        + "font.                                "
                        + "testtesttesttesttesttesttesttesttest "
                        + "test                                 ";
            }
            if (state == 2) {
                GameHandler.showTextBox = true;
                GameHandler.textToDraw = ""
                        + "         Text Emitter Object         "
                        + "                                     "
                        + "                                     "
                        + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa        "
                        + "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb    "
                        + "ccccccccccccccccccccccccccccccccccccc"
                        + "                                     "
                        + "tddddddddddddddddddddddddddddddddddd "
                        + "test                                 ";
            }
            if (state == 3) {
                GameHandler.showTextBox = false;
                GameHandler.textToDraw = ""
                        + "                                     "
                        + "                                     "
                        + "                                     "                        
                        + "                                     "
                        + "                                     "
                        + "                                     "
                        + "                                     "
                        + "                                     "
                        + "                                     ";
            }
            if (state == 4){
                state = 1;
            }
        }
    }

}
