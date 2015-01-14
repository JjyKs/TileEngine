package tileengine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jyri
 */
public class OpenGL {

    public boolean isCloseRequested = false;

    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(GameHandler.xRes, GameHandler.yRes));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        try {
            initGL(); // init OpenGL
        } catch (IOException ex) {
            Logger.getLogger(OpenGL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initGL() throws IOException {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, GameHandler.scaleXRes, 0, GameHandler.scaleYRes, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // enable alpha blending
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        InputStream in = null;
        try {
            in = new FileInputStream("res/water.png");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OpenGL.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);

            PNGDecoder decoder = new PNGDecoder(in);

            GameHandler.spriteSheetX = decoder.getWidth();
            GameHandler.spriteSheetY = decoder.getHeight();
            GameHandler.spriteSheetScale = GameHandler.spriteSheetX / 32;

            ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
            buf.flip();
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
        } finally {
            in.close();
        }

    }

    public void updateTitle() {
        Display.setTitle("FPS: " + GameHandler.fps + " Player X: " + GameHandler.playerX / 64 + " Player Y: " + GameHandler.playerY / 64);
    }

    private void updateGL() {
        Display.update();
        if (GameHandler.fpsLimit) {
            Display.sync(60);
        }
        isCloseRequested = Display.isCloseRequested();
    }

    public void destroyGL() {
        Display.destroy();
    }

    private void renderPlayer() {
        int x = GameHandler.playerX;
        int y = GameHandler.playerY;

        int selectedColumn = GameHandler.playerAnimationTimer / 8;
        //Jonkun takia animationtimerissä on 33 eri asentoa, korjataan tällä viidennen texturen vilahdus..
        if (selectedColumn == 4) {
            selectedColumn = 3;
        }
        int selectedRow = 0;

        if (GameHandler.playerLookingAt.equals("down")) {
            selectedRow = 9;
        }
        if (GameHandler.playerLookingAt.equals("left")) {
            selectedRow = 10;
        }

        if (GameHandler.playerLookingAt.equals("right")) {
            selectedRow = 11;
        }

        if (GameHandler.playerLookingAt.equals("up")) {
            selectedRow = 12;
        }

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
    }

    private void renderAnimations() {
        for (AnimationPlayer animPlayer : GameHandler.animationPlayerArray) {
            if (animPlayer != null) {
                animPlayer.update();
            }
        }
    }

    private void renderMap() {
        //Muuttujat piirrettävän kartan aluetta varten                                               
        int renderX = GameHandler.cameraX / (GameHandler.spriteSheetScale * 2) + GameHandler.preRenderDistance + 1;
        int renderY = GameHandler.cameraY / (GameHandler.spriteSheetScale * 2) + GameHandler.preRenderDistance + 1;

        //Loopataan mapin läpi sillä alueella, missä pelaaja on.
        //Ensimmäinen loop maptilekerroksen ja objektikerroksen erittelyyn
        for (int z = 0; z <= 1; z++) {
            //Tästä alkaa itse arrayn looppaaminen
            for (int x = Math.max(0, renderX - GameHandler.preRenderDistance - 1); x < Math.min(GameHandler.map.length, renderX); x++) {
                for (int y = Math.max(0, renderY - GameHandler.preRenderDistance - 1); y < Math.min(GameHandler.map[0].length, renderY); y++) {
                    //Objektikerros tarkistus
                    if (z == 0 || (z == 1 && GameHandler.map[x][y][z].drawable)) {
                        int selectedTexture = GameHandler.map[x][y][z].getTexture(), selectedColumn = 0, selectedRow = 0;

                        //pieni häksi for loopin avulla valitun texturen selvittämiseksi
                        for (selectedColumn = selectedTexture; selectedColumn >= GameHandler.spriteSheetX / GameHandler.spriteSheetScale; selectedColumn -= 32) {
                            selectedRow++;
                        }

                        float textureXOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedColumn;
                        float textureYOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedRow;

                        GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
                        GL11.glVertex2f(64 + x * 64, 0 + y * 64);

                        GL11.glTexCoord2f(textureXOffSet, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
                        GL11.glVertex2f(0 + x * 64, 0 + y * 64);

                        GL11.glTexCoord2f(textureXOffSet, textureYOffSet);
                        GL11.glVertex2f(0 + x * 64, 64 + y * 64);

                        GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet);
                        GL11.glVertex2f(64 + x * 64, 64 + y * 64);
                    }
                }
            }
        }

    }

    public void drawTextBox(int offSet) {
        if (offSet == 0) {
            drawTextBox(1);
        }
        for (int x = 0 + offSet; x < 10 - offSet; x++) {
            for (int y = 0 + offSet * 2; y < 3 + offSet; y++) {
                int selectedTexture = 0, selectedColumn = 0, selectedRow = 0;
                //Selvitetään milloin piirretään textboxin reunoja                
                if (y == 0 + offSet) {
                    selectedTexture = 258;
                    if (x == 0) {
                        selectedTexture = 257;
                    }
                    if (x == 9) {
                        selectedTexture = 259;
                    }
                }
                if (y == 1 + offSet) {
                    selectedTexture = 226;
                    if (x == 0) {
                        selectedTexture = 225;
                    }
                    if (x == 9) {
                        selectedTexture = 227;
                    }
                }
                if (y == 2 + offSet) {
                    selectedTexture = 194;
                    if (x == 0 + offSet) {
                        selectedTexture = 193;
                    }
                    if (x == 9 - offSet) {
                        selectedTexture = 195;
                    }
                }

                //pieni häksi for loopin avulla valitun texturen selvittämiseksi
                for (selectedColumn = selectedTexture; selectedColumn >= GameHandler.spriteSheetX / GameHandler.spriteSheetScale; selectedColumn -= 32) {
                    selectedRow++;
                }

                float textureXOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedColumn;
                float textureYOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedRow;

                GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
                GL11.glVertex2f(70 + x * 64, 10 + y * 64);

                GL11.glTexCoord2f(textureXOffSet, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
                GL11.glVertex2f(6 + x * 64, 10 + y * 64);

                GL11.glTexCoord2f(textureXOffSet, textureYOffSet);
                GL11.glVertex2f(6 + x * 64, 76 + y * 64);

                GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet);
                GL11.glVertex2f(70 + x * 64, 76 + y * 64);
            }

        }

    }

    public void drawText(String sana) {
        sana = sana.toLowerCase();
        String aakkoset = "abcdefghijklmnopqrstuvwxyz .-_,";
        int index = 0;
        for (int y = 8; y >= 0; y--) {
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
                GL11.glVertex2f(39 + x * 16, 30 + y * 24);

                GL11.glTexCoord2f(textureXOffSet, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
                GL11.glVertex2f(23 + x * 16, 30 + y * 24);

                GL11.glTexCoord2f(textureXOffSet, textureYOffSet);
                GL11.glVertex2f(23 + x * 16, 46 + y * 24);

                GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet);
                GL11.glVertex2f(39 + x * 16, 46 + y * 24);
            }
        }
    }

    public void renderOverlays() {
        int selectedTexture = GameHandler.overlayTexture, selectedColumn = 0, selectedRow = 0;
        int x = GameHandler.overlayX;
        int y = GameHandler.overlayY;

        //pieni häksi for loopin avulla valitun texturen selvittämiseksi
        for (selectedColumn = selectedTexture; selectedColumn >= GameHandler.spriteSheetX / GameHandler.spriteSheetScale; selectedColumn -= 32) {
            selectedRow++;
        }

        float textureXOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedColumn;
        float textureYOffSet = ((float) 1 / GameHandler.spriteSheetScale) * selectedRow;

        GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
        GL11.glVertex2f(64 + x * 64, 0 + y * 64);

        GL11.glTexCoord2f(textureXOffSet, textureYOffSet + (float) 1 / GameHandler.spriteSheetScale);
        GL11.glVertex2f(0 + x * 64, 0 + y * 64);

        GL11.glTexCoord2f(textureXOffSet, textureYOffSet);
        GL11.glVertex2f(0 + x * 64, 64 + y * 64);

        GL11.glTexCoord2f(textureXOffSet + (float) 1 / GameHandler.spriteSheetScale, textureYOffSet);
        GL11.glVertex2f(64 + x * 64, 64 + y * 64);
    }
   

    public void renderGL() {
        updateGL();

        // Clear The Screen And The Depth Buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // draw quad        
        GL11.glPushMatrix();
        GL11.glTranslatef(-GameHandler.cameraX, -GameHandler.cameraY, 0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL11.glBegin(GL11.GL_QUADS);

        renderMap();
        renderAnimations();
        renderPlayer();

        if (GameHandler.player.mapEditing) {
            renderOverlays();
        }

        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glBegin(GL11.GL_QUADS);
        if (GameHandler.showTextBox) {
            drawTextBox(0);
            drawText(GameHandler.textToDraw);
        }

        MainMenu.update();
        DevButton.update();
        GameHandler.inventory.drawItems(800, 128, 20, 10, 15);

      

        GL11.glEnd();
    }
}
