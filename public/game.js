import SoundPlayer from './soundPlayer.js';  // Import the SoundPlayer class

// Create an instance of the SoundPlayer class
const soundPlayer = new SoundPlayer();

// Game variables
const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

let plane = { x: 50, y: 320, width: 58, height: 64 };
let obstacles = [];
let groundObstacles = [];
let fireballs = [];
let score = 0;
let bestScore = 0;
let gameOver = false;
let yMovement = 0;
const gravity = 1;

// Load images
const planeImg = new Image();
planeImg.src = './images/flying-airplane.gif';
const birdImg = new Image();
birdImg.src = './images/bird.gif';
const fireballImg = new Image();
fireballImg.src = './images/fireball.png';

// Play background music
soundPlayer.playMusic('./music/background.mp3');  // Adjust to your file path

// Main game loop
function gameLoop() {
  if (!gameOver) {
    update();
    draw();
    requestAnimationFrame(gameLoop);
  } else {
    ctx.fillStyle = 'black';
    ctx.font = '30px Arial';
    ctx.fillText(`Game Over! Score: ${score}`, 400, 200);
    ctx.fillText('Press Space to Restart!', 370, 240);
  }
}

// Update game state
function update() {
  yMovement += gravity;
  plane.y += yMovement;
  plane.y = Math.min(320, Math.max(0, plane.y)); // Keep plane within bounds

  // Move obstacles
  obstacles.forEach(obstacle => obstacle.x -= 8);
  groundObstacles.forEach(obstacle => obstacle.x -= 8);
  fireballs.forEach(fireball => {
    fireball.x -= 4;
    fireball.y += 2;
  });

  // Check collisions
  [...obstacles, ...groundObstacles, ...fireballs].forEach(obstacle => {
    if (collision(plane, obstacle)) {
      gameOver = true;
      soundPlayer.playSoundEffect('./sounds/collision.wav');  // Play collision sound
    }
  });

  // Remove off-screen obstacles
  obstacles = obstacles.filter(obstacle => obstacle.x + obstacle.width > 0);
  groundObstacles = groundObstacles.filter(obstacle => obstacle.x + obstacle.width > 0);
  fireballs = fireballs.filter(fireball => fireball.x + fireball.width > 0);

  // Increment score
  score++;
  if (score > bestScore) bestScore = score;
}

// Draw everything
function draw() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  ctx.drawImage(planeImg, plane.x, plane.y, plane.width, plane.height);

  obstacles.forEach(obstacle => {
    ctx.drawImage(birdImg, obstacle.x, obstacle.y, obstacle.width, obstacle.height);
  });

  fireballs.forEach(fireball => {
    ctx.drawImage(fireballImg, fireball.x, fireball.y, fireball.width, fireball.height);
  });

  // Draw score
  ctx.fillStyle = 'black';
  ctx.font = '20px Arial';
  ctx.fillText(`Score: ${score}`, 10, 20);
  ctx.fillText(`Best: ${bestScore}`, 10, 40);
}

// Check for collisions
function collision(a, b) {
  return (
    a.x < b.x + b.width &&
    a.x + a.width > b.x &&
    a.y < b.y + b.height &&
    a.y + a.height > b.y
  );
}

// Add obstacles
function addObstacle() {
  const bird = { x: canvas.width, y: Math.random() * 300, width: 60, height: 20 };
  obstacles.push(bird);
}

// Restart the game
function restartGame() {
  gameOver = false;
  plane.y = 320;
  yMovement = 0;
  obstacles = [];
  groundObstacles = [];
  fireballs = [];
  score = 0;
  gameLoop();
}

// Event listeners
document.addEventListener('keydown', e => {
  if (e.code === 'Space') {
    if (gameOver) {
      restartGame();
      soundPlayer.playSoundEffect('./sounds/restart.wav');  // Play restart sound
    } else {
      yMovement = -7;  // Make the plane fly up
      soundPlayer.playSoundEffect('./sounds/jump.wav');  // Play jump sound
    }
  }
});

// Spawn obstacles every 2 seconds
setInterval(addObstacle, 2000);

// Start the game
gameLoop();

