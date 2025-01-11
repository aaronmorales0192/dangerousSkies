class SoundPlayer {
    constructor() {
      this.audio = null;
    }
  
    playMusic(filePath, loop = true) {
      this.audio = new Audio(filePath);
      this.audio.loop = loop;
      this.audio.play().catch(err => console.error('Error playing music:', err));
    }
  
    playSound(filePath, duration = null) {
      const sound = new Audio(filePath);
      sound.play();
      if (duration) {
        setTimeout(() => sound.pause(), duration);
      }
    }
  
    stopMusic() {
      if (this.audio) {
        this.audio.pause();
        this.audio.currentTime = 0;
      }
    }
  }
  
  // Example usage:
  const soundPlayer = new SoundPlayer();
  soundPlayer.playMusic('./sounds/background-music.mp3');
  soundPlayer.playSound('./sounds/crash.mp3', 1500);
  