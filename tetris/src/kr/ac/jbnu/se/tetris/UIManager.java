package kr.ac.jbnu.se.tetris;

import javax.swing.JLabel;


//* UI 처리 클래스
public class UIManager {

    private final JLabel statusbar;


    public UIManager(JLabel statusbar) {
        this.statusbar = statusbar;
    }

    public void updateStatusbar(String text) {
        statusbar.setText(text);
    }

    public void updateScore(int score) {
        updateStatusbar("Score: " + score);
    }
}
