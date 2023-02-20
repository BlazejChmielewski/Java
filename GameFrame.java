package Monkas;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GameFrame(){
        add(new GamePanel());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setTitle("SNAKE");
    }
}

