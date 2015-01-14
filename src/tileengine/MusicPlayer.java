package tileengine;

import java.io.IOException;
import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;


public class MusicPlayer {
    private static Audio oggStream;
    private static Audio seaSound;
    public static Audio menuEffect[];
    
    public static void init(){
        menuEffect = new Audio[64];
        try {	   
	    oggStream = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("res/music/she.ogg"));
            menuEffect[0] =  AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/music/SoundEffects/AbstractPackSFX/Files/AbstractSfx/59.ogg"));
            menuEffect[1] =  AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/music/SoundEffects/AbstractPackSFX/Files/AbstractSfx/22.ogg"));
            seaSound =  AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/music/08Ocean.ogg"));
        } catch (IOException e) {
	    e.printStackTrace();
	}
        oggStream.playAsMusic(1f, 1f, true);
        
        //SoundStore.get().setMusicVolume(GameHandler.musicVolume);
        //SoundStore.get().setSoundVolume(1f);
        playSeaSound();
    }
    
    public static void destroy(){
        AL.destroy();
    }
    
    public static void update(){                
        SoundStore.get().poll(0);
    }
    
    public static void playSeaSound(){
        seaSound.playAsSoundEffect(1f, 0.8f, true);
    }
    
    public static void stopSeaSound(){
        seaSound.stop();
    }
}
