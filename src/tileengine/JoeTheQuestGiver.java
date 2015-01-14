package tileengine;



public class JoeTheQuestGiver extends Tile {

    int state = 0;
    String lookingAt;
    int posX, posY;
    int animationPosition = -1;
    int playerPosY;
    int playerPosX;

    public JoeTheQuestGiver(boolean interactable, boolean walkable, int posX, int posY) {
        super(288, interactable, walkable);
        lookingAt = "up";
        this.posX = posX;
        this.posY = posY;
    }

    public JoeTheQuestGiver(boolean interactable, boolean walkable, int posX, int posY, String lookingAt, boolean drawable) {
        super(288, interactable, walkable);
        this.lookingAt = lookingAt;
        this.posX = posX;
        this.posY = posY;
        this.drawable = drawable;
    }

    @Override
    public void onInteraction() {
        active = true;
        if (GameHandler.playerX / GameHandler.spriteSheetScale * 2 > posX) {
            lookingAt = "right";
        }
        if (GameHandler.playerX / (GameHandler.spriteSheetScale * 2) < posX) {
            lookingAt = "left";
        }
        if (GameHandler.playerY / (GameHandler.spriteSheetScale * 2) > posY) {
            lookingAt = "up";
        }
        if (GameHandler.playerY / (GameHandler.spriteSheetScale * 2) < posY) {
            lookingAt = "down";
        }
    }

    @Override
    public void reset() {
        state = 0;
        active = false;
    }

    @Override
    public void update() {
        if (lookingAt.equals("right")) {
            texture = 352;
        }
        if (lookingAt.equals("left")) {
            texture = 320;
        }
        if (lookingAt.equals("up")) {
            texture = 384;
        }
        if (lookingAt.equals("down")) {
            texture = 288;
        }

        if (active) {
            super.playSound(1);
            if (GameHandler.introduction.isStartable() && !GameHandler.introduction.isStarted()) {
                if (GameHandler.ePressed && state != 4 && state != 5) {
                    state++;
                }
                if (state == 1) {
                    GameHandler.showTextBox = true;
                    GameHandler.textToDraw = ""
                            + "         Joe The Quest Giver         "
                            + "                                     "
                            + "                                     "
                            + "Hi,i will give you a quest someday.  "
                            + "You will need to wait for it though. "
                            + "                                     "
                            + "                                     "
                            + "                                     "
                            + "                          Continue...";
                }
                if (state == 2) {
                    GameHandler.showTextBox = true;
                    GameHandler.textToDraw = ""
                            + "         Joe The Quest Giver         "
                            + "                                     "
                            + "                                     "
                            + "Nevermind,i can give a one simple    "
                            + "quest now.Here you go.I will run away"
                            + "and you have to come and talk to me  "
                            + "again.                               "
                            + "                                     "
                            + "                          Continue...";
                }
                if (state == 3) {
                    state++;
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
                if (state == 4) {
                    playerPosY = GameHandler.playerY / (GameHandler.spriteSheetScale * 2);
                    playerPosX = GameHandler.playerX / (GameHandler.spriteSheetScale * 2);
                    state++;
                    if (GameHandler.map[posX + (posX - playerPosX)][posY + (posY - playerPosY)][1].walkable
                            && GameHandler.map[posX + (posX - playerPosX)][posY + (posY - playerPosY)][0].walkable) {
                        GameHandler.map[posX + (posX - playerPosX)][posY + (posY - playerPosY)][1] = new JoeTheQuestGiver(true, false, posX + (posX - playerPosX), posY + (posY - playerPosY), lookingAt, false);
                        GameHandler.map[posX][posY][1].hide();
                        animationPosition = GameHandler.addAnimation(posX * GameHandler.spriteSheetScale * 2, posY * GameHandler.spriteSheetScale * 2, (posX + (posX - playerPosX)) * GameHandler.spriteSheetScale * 2, (posY + (posY - playerPosY)) * GameHandler.spriteSheetScale * 2, texture);

                    } else {
                        //Aloita questi
                        GameHandler.introduction.start();
                        state = 0;
                    }
                }
                if (state == 5) {
                    if (!GameHandler.animationPlayerArray[animationPosition].active) {
                        GameHandler.map[posX][posY][1] = new Tile(128, false, true);
                        GameHandler.map[posX + (posX - playerPosX)][posY + (posY - playerPosY)][1].show();
                        GameHandler.animationPlayerArray[animationPosition].finish();
                        //Aloita questi
                        GameHandler.introduction.start();
                        reset();
                    }
                }
            } else if (GameHandler.introduction.isStarted() && !GameHandler.introduction.isFinished()) {
                if (GameHandler.ePressed) {
                    state++;
                }
                if (state == 1) {
                    GameHandler.showTextBox = true;
                    GameHandler.textToDraw = ""
                            + "         Joe The Quest Giver         "
                            + "                                     "
                            + "                                     "
                            + "Hi,it seems that you found me.Here is"
                            + "Your superduperhyperreward.a cat     "
                            + "                                     "
                            + "                                     "
                            + "                                     "
                            + "                          Continue...";
                }
                if (state == 2) {
                    state++;
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
                    state = 0;
                    GameHandler.introduction.finish();
                }

            } else if (GameHandler.introduction.isFinished()) {
                if (GameHandler.ePressed) {
                    state++;
                }
                if (state == 1) {
                    GameHandler.showTextBox = true;
                    GameHandler.textToDraw = ""
                            + "         Joe The Quest Giver         "
                            + "                                     "
                            + "                                     "
                            + "I do not have any quests for you     "
                            + "right now.Please come back later.    "
                            + "                                     "
                            + "                                     "
                            + "                                     "
                            + "                          Continue...";
                }
                if (state == 2) {
                    state++;
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
                    state = 0;
                }
            }
        }
    }
}
