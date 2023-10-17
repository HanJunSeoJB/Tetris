package kr.ac.jbnu.se.tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//키보드 액션 처리 클래스
public class TAdapter extends KeyAdapter {
    private final Board board;
    private final int playerNum;

    public TAdapter(Board board, int playerNum) {
        this.board = board;
        this.playerNum = playerNum;
    }

    //* 키보드 액션 처리 함수
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();

        if (keycode == 'p' || keycode == 'P') {
            board.handleKeyAction("pause");
            return;
        }

        if (board.isPaused())
            return;

        if(playerNum == 1) {
            switch (keycode) {
                case KeyEvent.VK_LEFT:
                    board.handleKeyAction("left");
                    break;
                case KeyEvent.VK_RIGHT:
                    board.handleKeyAction("right");
                    break;
                case KeyEvent.VK_DOWN:
                    board.handleKeyAction("oneLineDown");
                    break;
                case KeyEvent.VK_UP:
                    board.handleKeyAction("rotateLeft");
                    break;
                case KeyEvent.VK_SPACE:
                    board.handleKeyAction("dropDown");
                    break;
                case 'd':
                case 'D':
                    board.handleKeyAction("hold");
                    break;
                case KeyEvent.VK_R:
                    board.handleKeyAction("re");
                    break;
            }
        }
        else if(playerNum==2){
            switch (keycode) {
                case 'j':
                    board.handleKeyAction("left");
                    break;
                case 'l':
                    board.handleKeyAction("right");
                    break;
                case 'k':
                    board.handleKeyAction("rotateLeft");  // 또는 "rotateRight"에 따라
                    break;
                case 'y':
                    board.handleKeyAction("oneLineDown");
                    break;
                case 'u':
                    board.handleKeyAction("dropDown");
                    break;
                // 추가적으로 필요한 키 액션들
            }
        }

    }
    //*
}