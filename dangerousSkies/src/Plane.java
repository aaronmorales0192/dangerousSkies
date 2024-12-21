import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Random;
import java.util.TimerTask;


public class Plane extends JPanel implements ActionListener, KeyListener { 

    Random random = new Random();
    int best = 0;
    JLabel bestScore;
    soundPlayer sPlayer = new soundPlayer();
    soundPlayer auxillarySPlayer = new soundPlayer();
    boolean music = false;
    Image planeImage;
    Image fighterImage;
    Image birdImage;
    Image crashImage;
    Image spikeImage;
    Image buildingImage;
    Image fireballImage;
    boolean gameOver = false;
    ArrayList <Block> obstacles;
    ArrayList <Block> groundObstacles;
    ArrayList <Block> fireballs;
    Block plane;
    int nextLevel = 1000;
    int score = 0;

    //dimensions of obstacles
    int birdX = 900;
    int birdWidth = 60;
    int birdHeight = 20;
    int biggerBirdWidth = 100;
    int biggerBirdHeight = 40;
    int fighterX = 900;
    int fighterWidth = 130;
    int fighterHeight = 50;
    int spikeX = 900;
    int spikeY = 350;
    int spikeHeight = 20;
    int spikeWidth = 100;
    int buildingX = 900;
    int buildingY = 260;
    int buildingHeight = 115;
    int buildingWidth = 100;
    int fireballHeight = 27;
    int fireballWidth = 27;

     //physics
     int xMovement = -8; //obstacles moving left speed
     int yMovement = 0; //plane up/down increase
     int gravity = 1;
     int fireballXMovement = -4;
     int fireballYMovement = 2;

     
    int planeWidth = 58;
    int planeHeight = 64;
    int planeX = 50;
    int planeY = 400 - planeHeight;




    class Block {
        int x;
        int y;
        int width;
        int height;
        Image img;

        Block(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }


    Timer gameLoop;
    Timer obstacleLoop;
    Timer groundObstaclesLoop;
    Timer fireballLoop;
    TimerTask startTimer;
    
    public JLabel bestLabel(){
        return bestScore;
    }
    

    public Plane() {
        bestScore = new JLabel("Best Score: " + best);
        setPreferredSize(new Dimension(1000, 400));
        setBackground(Color.white);
        setFocusable(true);
        addKeyListener(this);

        planeImage = new ImageIcon(getClass().getResource("./images/flying-airplane.gif")).getImage();
        fighterImage = new ImageIcon(getClass().getResource("./images/fighter.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./images/bird.gif")).getImage();
        fireballImage = new ImageIcon(getClass().getResource("./images/fireball.png")).getImage();
        crashImage = new ImageIcon(getClass().getResource("./images/crashing.png")).getImage();
        spikeImage = new ImageIcon(getClass().getResource("./images/spikes.png")).getImage();
        buildingImage = new ImageIcon(getClass().getResource("./images/building.png")).getImage();
        fireballImage = new ImageIcon(getClass().getResource("./images/fireball.png")).getImage();

        plane = new Block(planeX, planeY, planeWidth, planeHeight, planeImage);
        obstacles = new ArrayList<Block>();
        groundObstacles = new ArrayList<Block>();
        fireballs = new ArrayList<Block>();

        gameLoop = new Timer(1000/60,this);
        gameLoop.start(); 


        obstacleLoop = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeObstacle();
            }
        });
        obstacleLoop.start();

        groundObstaclesLoop = new Timer(290, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeGroundObstacle();
            }
        });
        groundObstaclesLoop.start();


        fireballLoop = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeFireball();
            }
        });
        fireballLoop.start();
    }


    public void placeFireball(){
        if (gameOver){
            return;        
        }
        double chance = Math.random();
        if (score > 1680 && score < 1770 && chance > .00){
            Block fireball = new Block (random.nextInt(500)+30, 0 , fireballWidth, fireballHeight, fireballImage);
            fireballs.add(fireball);
        }
        if (score > 4000 && chance > .65){
            Block fireball = new Block (random.nextInt(500)+30, 0 , fireballWidth, fireballHeight, fireballImage);
            fireballs.add(fireball);
        }
        else if (score > 2000 && chance > .75){
            Block fireball = new Block (random.nextInt(500)+30, 0 , fireballWidth, fireballHeight, fireballImage);
            fireballs.add(fireball);
        }
        else if (score > 1000 && chance > .93){
            Block fireball = new Block (random.nextInt(500)+30 , 0 , fireballWidth, fireballHeight, fireballImage);
            fireballs.add(fireball);
        }
        if (fireballs.size() > 10) {
            fireballs.remove(0); //remove the first ground obstacle from ArrayList
        }

    }

    public void placeGroundObstacle() {
        if (gameOver){
            return;        
        }
        double chance = Math.random(); //0 - 0.999999
        if (score > 600 && chance > .90){
            Block building = new Block (buildingX, buildingY, buildingWidth, buildingHeight, buildingImage);
            groundObstacles.add(building);
        } else if (score > 100){
            Block spike = new Block (spikeX, spikeY, spikeWidth, spikeHeight, spikeImage);
            groundObstacles.add(spike);
        } 
        if (groundObstacles.size() > 14) {
            groundObstacles.remove(0); //remove the first ground obstacle from ArrayList
        }
    
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
        
    public void placeObstacle(){
        if (gameOver){
            return;        
        }
        double chance = Math.random(); //0 - 0.999999
        if(score > 700 && chance > .80){
            Block fighter = new Block (fighterX, (int)((Math.random())*300) - 5, fighterWidth, fighterHeight, fighterImage);
            obstacles.add(fighter);
        }
        else if(score > 500 && chance > .60){
            Block biggerBird = new Block (birdX, (int)((Math.random())*300) - 5, biggerBirdWidth, biggerBirdHeight, birdImage);
            obstacles.add(biggerBird);
        }
        else if(chance > .00){
            Block bird = new Block (birdX, (int)((Math.random())*300) - 5, birdWidth, birdHeight, birdImage);
            obstacles.add(bird);
        }

        if (obstacles.size() > 10) {
            obstacles.remove(0); //remove the first ground obstacle from ArrayList
        }

    }

    private void draw(Graphics g) {
        g.drawImage(plane.img, plane.x, plane.y, plane.width, plane.height, null);

        //flying obstacles
        for (int i = 0; i < obstacles.size(); i++) {
            Block obstacle = obstacles.get(i);
            g.drawImage(obstacle.img, obstacle.x, obstacle.y, obstacle.width, obstacle.height, null);
        }

        
        //ground obstacles
        for (int i = 0; i < groundObstacles.size(); i++) {
            Block obstacle = groundObstacles.get(i);
            g.drawImage(obstacle.img, obstacle.x, obstacle.y, obstacle.width, obstacle.height, null);
        }

        //fireball
        for (int i = 0; i < fireballs.size(); i++) {
            Block obstacle = fireballs.get(i);
            g.drawImage(obstacle.img, obstacle.x, obstacle.y, obstacle.width, obstacle.height, null);
        }


        g.setColor(Color.black);
        g.setFont(new Font("Courier", Font.PLAIN, 20));
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf(score), 10, 35);
        } 
        else if (score > 0 && score < 100){
            g.drawString(String.valueOf(score)  + " Welcome to the skies, Captain!", 10, 35);
        }
        else {
            g.drawString(String.valueOf(score) , 10, 35);
        }
    }
        
    public void move(){
        if (yMovement < 4) { 
            yMovement += gravity;
            plane.y += yMovement;
        } else {
            plane.y += yMovement;
        }
       if (plane.y > 320) { //stop the plane from falling past the ground
            plane.y = 320;
            planeY = plane.y;
            plane.img = planeImage;
       } else if (plane.y < -20){
            plane.y = -20;
            planeY = plane.y;
            plane.img = planeImage;
       }


        for (int i = 0; i < obstacles.size(); i++) {
            Block obstacle = obstacles.get(i);
            obstacle.x += xMovement;
            if (collision(plane, obstacle)) {
                gameOver = true;
                plane.img = crashImage;
            }
        }

        for (int i = 0; i < groundObstacles.size(); i++) {
            Block obstacle = groundObstacles.get(i);
            obstacle.x += xMovement;
            if (collision(plane, obstacle)) {
                gameOver = true;
                plane.img = crashImage;
            }
        }

        for (int i = 0; i < fireballs.size(); i++) {
            Block obstacle = fireballs.get(i);
            obstacle.x += fireballXMovement;
            obstacle.y += fireballYMovement;
            if (collision(plane, obstacle)) {
                gameOver = true;
                plane.img = crashImage;
            }
        }
        score++;
    }
    
    boolean collision(Block a, Block b) {
        boolean died = a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
        a.x + a.width - 20 > b.x &&   //a's top right corner passes b's top left corner
        a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
        a.y + a.height - 20 > b.y;    //a's bottom left corner passes b's top left corner
        if (died == true){
            sPlayer.stopMusic();
            auxillarySPlayer.playMusic(1300,"crash.wav");
            music = false;
            return true;
        }
        return false;
    }
    


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!music){
            sPlayer.playMusic("sleepWalking.wav");
            music = true;
        }
        if (score > best){
            best = score;
        }    
        bestScore.setText("Best Score: " + best);
        move();
        repaint();
        if (gameOver) {
            obstacleLoop.stop();
            gameLoop.stop();
            fireballLoop.stop();
        }
    } 
       
    @Override
    public void keyTyped(KeyEvent e) {} 

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            yMovement = -7;
        } 

        if (gameOver) {
            // restart game by resetting conditions
            plane.y = planeY;
            plane.img = planeImage;
            yMovement = 0;
            obstacles.clear();
            groundObstacles.clear();
            fireballs.clear();
            score = 0;
            gameOver = false;
            gameLoop.start();
            obstacleLoop.start();
            fireballLoop.start();
            groundObstaclesLoop.start();
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}