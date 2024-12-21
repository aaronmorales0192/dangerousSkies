import java.awt.*;
import javax.swing.*;
public class Runner {


    static class images { 
        public Image planeImage = new ImageIcon(getClass().getResource("./images/animated_plane2.gif")).getImage();
    }
    

    public static void main(String[] args)  {
        soundPlayer sPlay = new soundPlayer();
        JFrame frame = new JFrame("Dangerous Skies");
        CardLayout cardLayout = new CardLayout();
        frame.setSize(1000, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel mainPanel = new JPanel(cardLayout);
        images plane = new images();
        JPanel startPanel = new JPanel(){;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the plane image in the background
            if (plane.planeImage != null) {
                g.drawImage(plane.planeImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
        };
        startPanel.setPreferredSize(new Dimension(1000, 400));
        JButton start = new JButton("Start game!");
        startPanel.add(start);
        startPanel.setBackground(new Color(135, 206, 235));
        mainPanel.add(startPanel);

        frame.add(mainPanel);
        sPlay.playMusic("summerOfMonsters.wav");
        frame.pack();
        frame.setVisible(true);

       

        start.addActionListener(e -> {
            Plane planePanel = new Plane();
            sPlay.stopMusic();
            sPlay.playMusic(2500,"ding.wav");
            JLabel best = planePanel.bestLabel();
            planePanel.add(best,BorderLayout.SOUTH);
            mainPanel.add(planePanel,"PlaneView");
            planePanel.requestFocus();
            cardLayout.show(mainPanel, "PlaneView");
            planePanel.requestFocusInWindow(); // Ensure game panel gets focus
        });
    }
}
