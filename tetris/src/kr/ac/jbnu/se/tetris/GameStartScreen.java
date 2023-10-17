package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameStartScreen extends JFrame {

    public GameStartScreen() {
        initUI();
    }

    private void initUI() {
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Tetris tetris = new Tetris(false);
                //tetris.initUI();
                GameStartScreen.this.dispose();  // Add this line to close the start screen when the game starts
            }
        });

        createLayout(startButton);

        setTitle("Tetris");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createLayout(JComponent... arg) {

        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGap(100)
                .addComponent(arg[0])
                .addGap(100)
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(70)
                .addComponent(arg[0])
                .addGap(70)
        );

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            GameStartScreen ex = new GameStartScreen();
            ex.setVisible(true);
        });
    }
}
