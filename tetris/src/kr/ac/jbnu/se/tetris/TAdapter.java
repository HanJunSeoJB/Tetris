package kr.ac.jbnu.se.tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

//키보드 액션 처리 클래스
public class TAdapter extends KeyAdapter {
    private final Board board;
    private final int playerNum;
    private final Map<Integer, String> player1KeyMap;
    private final Map<Character, String> player2KeyMap;
    private static final Logger logger = Logger.getLogger(TAdapter.class.getName());

    public TAdapter(Board board, int playerNum) {
        this.board = board;
        this.playerNum = playerNum;
        this.player1KeyMap = createPlayer1KeyMap();
        this.player2KeyMap = createPlayer2KeyMap();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (isPauseKey(keyCode)) {
            board.handleKeyAction("pause");
            return;
        }

        if (board.isPaused()) {
            return;
        }

        String action = getPlayerAction(keyCode);
        if (action != null) {
            board.handleKeyAction(action);
        } else {
            handleUnexpectedKey(keyCode);
        }
    }

    private boolean isPauseKey(int keyCode) {
        return keyCode == KeyEvent.VK_P;
    }

    private String getPlayerAction(int keyCode) {
        return (playerNum == 1) ? player1KeyMap.get(keyCode) : player2KeyMap.get((char) keyCode);
    }

    private void handleUnexpectedKey(int keyCode) {
        if (logger.isLoggable(Level.WARNING)) {
            logger.log(Level.WARNING, () -> "Unhandled key: " + keyCode);
        }
    }

    private Map<Integer, String> createPlayer1KeyMap() {
        Map<Integer, String> keyMap = new HashMap<>();
        keyMap.put(KeyEvent.VK_LEFT, "left");
        keyMap.put(KeyEvent.VK_RIGHT, "right");
        keyMap.put(KeyEvent.VK_DOWN, "oneLineDown");
        keyMap.put(KeyEvent.VK_UP, "rotateLeft");
        keyMap.put(KeyEvent.VK_SPACE, "dropDown");
        keyMap.put((int) 'd', "hold");
        keyMap.put((int) 'D', "hold");
        keyMap.put(KeyEvent.VK_R, "re");
        return keyMap;
    }

    private Map<Character, String> createPlayer2KeyMap() {
        Map<Character, String> keyMap = new HashMap<>();
        keyMap.put('j', "left");
        keyMap.put('l', "right");
        keyMap.put('k', "rotateLeft");  // 또는 "rotateRight"
        keyMap.put('y', "oneLineDown");
        keyMap.put('u', "dropDown");
        return keyMap;
    }
}