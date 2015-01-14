package tileengine;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Player {

    private final int tileSize = 64;
    private int targetX = 55 * 64, targetY = 64;
    public boolean moving = false, canInterract = false, wantInterract = false;
    int movementSpeed = 2, sprintSpeed = 4;
    boolean allowMovementLeft = true, allowMovementRight = true, allowMovementUp = true, allowMovementDown = true;
    String playerLookingAt = GameHandler.playerLookingAt;
    boolean mapEditing = false;

    public void update() {

        movePlayer();

        //Palautetaan movementSpeed normaaliksi, jos spacebar pohjassa, se palautetaan takaisin sprint nopeudeksi ennen seuraavaa updatelooppia.
        movementSpeed = 2;
        //Pelaajan katsomissuunta
        playerLookingAt = GameHandler.playerLookingAt;
        if (!mapEditing) {
            //Katsotaan minnepäin pelaaja pystyy liikkumaan
            checkWherePlayerCanMove();

            canInterract = false;
            checkIfPlayerCanInterract();
            wantInterract = false;

            for (Tile map[][] : GameHandler.map) {
                for (Tile map2[] : map) {
                    map2[1].update();
                }
            }
        } else {
            mapEditing();
        }
    }

    private void mapEditing() {
        int offSetX = -32;
        int offSetY = -32;
        //Korjataan - arvoisille kameran sijainneille..
        if (GameHandler.cameraY > 0) {
            offSetY = 32;
        }        
        if (GameHandler.cameraX > 0) {
            offSetX = 32;
        }
        GameHandler.overlayX = (Mouse.getX() + offSetX) / 64 + (GameHandler.cameraX) / 64;
        GameHandler.overlayY = (Mouse.getY() + offSetY) / 64 + (GameHandler.cameraY) / 64;        
   }

    private void animatePlayer() {
        GameHandler.playerAnimationTimer++;
    }

    private void movePlayer() {
        if (GameHandler.playerX != targetX || GameHandler.playerY != targetY) {
            if (GameHandler.playerX < targetX) {
                GameHandler.playerX += movementSpeed;
                GameHandler.playerAnimationTimer++;
                if (GameHandler.playerX > targetX) {
                    GameHandler.playerX = targetX;
                }
            } else if (GameHandler.playerX > targetX) {
                GameHandler.playerX -= movementSpeed;
                GameHandler.playerAnimationTimer++;
                if (GameHandler.playerX < targetX) {
                    GameHandler.playerX = targetX;
                }
            } else if (GameHandler.playerY > targetY) {
                GameHandler.playerY -= movementSpeed;
                GameHandler.playerAnimationTimer++;
                if (GameHandler.playerY < targetY) {
                    GameHandler.playerY = targetY;
                }
            } else if (GameHandler.playerY < targetY) {
                GameHandler.playerY += movementSpeed;
                GameHandler.playerAnimationTimer++;
                if (GameHandler.playerY > targetY) {
                    GameHandler.playerY = targetY;
                }
            }
        } else {
            moving = false;
            GameHandler.playerAnimationTimer = 0;
        }
    }

    private void stopInteracting() {
        GameHandler.interacting = false;
        GameHandler.showTextBox = false;
        for (Tile map[][] : GameHandler.map) {
            for (Tile map2[] : map) {
                map2[1].reset();
            }
        }
    }

    private void checkIfPlayerCanInterract() {
        if (playerLookingAt.equals("up")) {
            if (GameHandler.map[GameHandler.playerX / GameHandler.spriteSheetScale / 2][GameHandler.playerY / GameHandler.spriteSheetScale / 2 + 1][1].interactable) {
                canInterract = true;
                if (wantInterract && !GameHandler.interacting) {
                    GameHandler.interacting = true;
                    GameHandler.map[GameHandler.playerX / GameHandler.spriteSheetScale / 2][GameHandler.playerY / GameHandler.spriteSheetScale / 2 + 1][1].onInteraction();
                }

            } else {
                stopInteracting();
            }
        }

        if (playerLookingAt.equals("down")) {
            if (GameHandler.map[GameHandler.playerX / GameHandler.spriteSheetScale / 2][GameHandler.playerY / GameHandler.spriteSheetScale / 2 - 1][1].interactable) {
                canInterract = true;
                if (wantInterract && !GameHandler.interacting) {
                    GameHandler.interacting = true;
                    GameHandler.map[GameHandler.playerX / GameHandler.spriteSheetScale / 2][GameHandler.playerY / GameHandler.spriteSheetScale / 2 - 1][1].onInteraction();
                }

            } else {
                stopInteracting();
            }
        }

        if (playerLookingAt.equals("left")) {
            if (GameHandler.map[GameHandler.playerX / GameHandler.spriteSheetScale / 2 - 1][GameHandler.playerY / GameHandler.spriteSheetScale / 2][1].interactable) {
                canInterract = true;
                if (wantInterract && !GameHandler.interacting) {
                    GameHandler.interacting = true;
                    GameHandler.map[GameHandler.playerX / GameHandler.spriteSheetScale / 2 - 1][GameHandler.playerY / GameHandler.spriteSheetScale / 2][1].onInteraction();
                }

            } else {
                stopInteracting();
            }
        }

        if (playerLookingAt.equals("right")) {
            if (GameHandler.map[GameHandler.playerX / GameHandler.spriteSheetScale / 2 + 1][GameHandler.playerY / GameHandler.spriteSheetScale / 2][1].interactable) {
                canInterract = true;
                if (wantInterract && !GameHandler.interacting) {
                    GameHandler.interacting = true;
                    GameHandler.map[GameHandler.playerX / GameHandler.spriteSheetScale / 2 + 1][GameHandler.playerY / GameHandler.spriteSheetScale / 2][1].onInteraction();
                }

            } else {
                stopInteracting();
            }
        }
    }

    private void checkWherePlayerCanMove() {
        for (int z = 0; z <= 1; z++) {

            if (GameHandler.map[GameHandler.playerX / GameHandler.spriteSheetScale / 2][GameHandler.playerY / GameHandler.spriteSheetScale / 2 + 1][z].isWalkable()) {
                //Ainoastaan ensimmäinen kerros voi sallia liikkumisen
                if (z == 0) {
                    allowMovementUp = true;
                }
            } else {
                allowMovementUp = false;
            }
            if (GameHandler.map[GameHandler.playerX / GameHandler.spriteSheetScale / 2][GameHandler.playerY / GameHandler.spriteSheetScale / 2 - 1][z].isWalkable()) {
                if (z == 0) {
                    allowMovementDown = true;
                }
            } else {
                allowMovementDown = false;
            }

            if (GameHandler.map[GameHandler.playerX / GameHandler.spriteSheetScale / 2 - 1][GameHandler.playerY / GameHandler.spriteSheetScale / 2][z].isWalkable()) {
                if (z == 0) {
                    allowMovementLeft = true;
                }
            } else {
                allowMovementLeft = false;
            }
            if (GameHandler.map[GameHandler.playerX / GameHandler.spriteSheetScale / 2 + 1][GameHandler.playerY / GameHandler.spriteSheetScale / 2][z].isWalkable()) {
                if (z == 0) {
                    allowMovementRight = true;
                }
            } else {
                allowMovementRight = false;
            }

        }
    }

    public void sprint() {
        movementSpeed = sprintSpeed;
        animatePlayer();
    }

    public void moveUp() {
        if (!moving) {
            GameHandler.playerLookingAt = "up";
            if (allowMovementUp) {
                stopInteracting();
                moving = true;
                targetY = GameHandler.playerY + tileSize;
            }
        }
    }

    public void moveDown() {
        if (!moving) {
            GameHandler.playerLookingAt = "down";
            if (allowMovementDown) {
                stopInteracting();
                moving = true;
                targetY = GameHandler.playerY - tileSize;
            }
        }
    }

    public void moveLeft() {
        if (!moving) {
            GameHandler.playerLookingAt = "left";
            if (allowMovementLeft) {
                stopInteracting();
                moving = true;
                targetX = GameHandler.playerX - tileSize;
            }
        }
    }

    public void moveRight() {
        if (!moving) {
            GameHandler.playerLookingAt = "right";
            if (allowMovementRight) {
                stopInteracting();
                moving = true;
                targetX = GameHandler.playerX + tileSize;
            }
        }
    }

    private void move() {
        GameHandler.playerX++;
        GameHandler.playerY++;
        System.out.println("Pelaaja liikkuu");
    }

    private void print() {
        System.out.println("Pelaaja");
        System.out.println(GameHandler.playerX);
        System.out.println(GameHandler.playerY);
    }
}
