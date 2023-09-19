package kr.ac.jbnu.se.tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter extends KeyAdapter {
    private final Board board;

    public TAdapter(Board board) {
        this.board = board;
    }

    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();

        if (keycode == 'p' || keycode == 'P') {
            board.handleKeyAction("pause");
            return;
        }

        if (board.isPaused())
            return;

        switch (keycode) {
            case KeyEvent.VK_LEFT:
                board.handleKeyAction("left");
                break;
            case KeyEvent.VK_RIGHT:
                board.handleKeyAction("right");
                break;
            case KeyEvent.VK_DOWN:
                board.handleKeyAction("rotateRight");
                break;
            case KeyEvent.VK_UP:
                board.handleKeyAction("rotateLeft");
                break;
            case KeyEvent.VK_SPACE:
                board.handleKeyAction("dropDown");
                break;
            case 'd':
            case 'D':
                board.handleKeyAction("oneLineDown");
                break;
        }
    }
}