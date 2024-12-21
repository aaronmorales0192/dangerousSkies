import javax.sound.sampled.*;
import java.io.File;
import java.util.*;
import java.io.IOException;

public class soundPlayer {

        private Clip clip;

    public soundPlayer(){
    }


    // Play music with looping
    public void playMusic(String filePath) {
        new Thread(() -> {
            try {
                File file = new File(filePath);

                if (!file.exists()) {
                    System.out.println("Audio file not found at: " + filePath);
                    return;
                }

                // Create AudioInputStream and open clip
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioStream);

                // Set the clip to loop continuously
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                System.out.println("Music is playing in a loop.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }



    public void playMusic(int lengthInMilliseconds, String filePath) {
        new Thread(() -> {
            try {
                // Relative path to the audio file (outside the src folder)
                File file = new File(filePath);

                if (!file.exists()) {
                    System.out.println("Audio file not found at: " + filePath);
                    return;
                }

                // Create AudioInputStream
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioStream);

                // Start the audio clip
                clip.start();
                System.out.println("Music is playing. It will stop after " + lengthInMilliseconds + " milliseconds.");

                // Play for the specified duration
                Thread.sleep(lengthInMilliseconds);

                clip.close();
                audioStream.close();
                System.out.println("Music playback finished.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start(); // Start the thread for playback
    }

    // Stop the music

    
    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            System.out.println("Music stopped.");
        }
    }
       
    
}
