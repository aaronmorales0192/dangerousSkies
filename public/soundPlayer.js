class SoundPlayer {
    constructor() {
      this.bgMusic = null; // For background music
      this.isPlaying = false; // To track if music is playing
    }
  
    // Play background music in a loop
    playMusic(filePath) {
      if (this.isPlaying) return; // Prevent multiple music playing at the same time
  
      this.bgMusic = new Audio(filePath); // Create an audio element
      this.bgMusic.loop = true; // Loop the music
      this.bgMusic.play(); // Play the music
      this.isPlaying = true;
      console.log("Background music is playing.");
    }
  
    // Play a sound effect (one-time sound)
    playSoundEffect(filePath) {
      const sound = new Audio(filePath); // Create an audio element for the sound effect
      sound.play(); // Play the sound
      console.log("Sound effect is playing.");
    }
  
    // Stop background music
    stopMusic() {
      if (this.bgMusic && this.isPlaying) {
        this.bgMusic.pause(); // Pause the music
        this.bgMusic.currentTime = 0; // Reset the music to the beginning
        this.isPlaying = false;
        console.log("Background music stopped.");
      }
    }
  
    // Pause the current music without resetting it
    pauseMusic() {
      if (this.bgMusic && this.isPlaying) {
        this.bgMusic.pause(); // Pause the music
        console.log("Background music paused.");
      }
    }
  
    // Resume playing the paused music
    resumeMusic() {
      if (this.bgMusic && !this.isPlaying) {
        this.bgMusic.play(); // Resume the music
        this.isPlaying = true;
        console.log("Background music resumed.");
      }
    }
  
    // Change the volume of the background music (0.0 to 1.0 scale)
    setVolume(volume) {
      if (this.bgMusic) {
        this.bgMusic.volume = volume;
        console.log(`Background music volume set to ${volume}`);
      }
    }
  }
  
  // Export the SoundPlayer class so it can be used in other files
  export default SoundPlayer;
  