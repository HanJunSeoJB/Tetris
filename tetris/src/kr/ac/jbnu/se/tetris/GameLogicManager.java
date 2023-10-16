package kr.ac.jbnu.se.tetris;

import java.util.Arrays;
import java.util.Timer;
import java.util.logging.Level;

public class GameLogicManager {

    //* 변수 초기화
    private boolean isStarted = false;
    private boolean isPaused = false;
    private int numLinesRemoved = 0;
    private int curX = 0;
    private int curY = 0;

    private int level = 1;

    private int score = 0;

    private int bestScore = 0;

    private int BoardWidth;
    private int BoardHeight;
    private Shape curPiece;
    private Shape nextPiece;
    private Shape holdPiece;
    private boolean isFallingFinished = false;
    //*

    //* 객체 초기화
    private final Board board;
    private final TimerManager timerManager;
    private final UIManager uiManager;
    private final ShapeAndTetrominoesManager shapeAndTetrominoesManager;
    //private final EventManager eventManager;
    private final ConfigurationManager configurationManager;

    NextPiecePanel nextPiecePanel;
    HoldPiecePanel holdPiecePanel;
    LevelPanel levelPanel;
    ScoreManager scoreManager;

    //*

    //* 게터&세터 메소드
    public int getCurX() {
        return curX;
    }


    public int getCurY() {
        return curY;
    }


    public Shape getCurPiece() {
        return curPiece;
    }

    public Shape getNextPiece() {
        return nextPiece;
    }

    public boolean isFallingFinished() {
        return isFallingFinished;
    }

    public void setFallingFinished(boolean fallingFinished) {
        isFallingFinished = fallingFinished;
    }


    public void setStarted(boolean started) {
        isStarted = started;
    }

    public boolean isPaused() {
        return isPaused;
    }


    public int getNumLinesRemoved() {
        return numLinesRemoved;
    }

    public boolean isStarted() {
        return isStarted;
    }
    public int getBestScore() {
        return bestScore;
    }

    //*

    //* 클래스 초기화
    public GameLogicManager(Board board, NextPiecePanel nextPiecePanel, HoldPiecePanel holdPiecePanel, LevelPanel levelPanel, ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
        this.bestScore = scoreManager.loadBestScore();
        this.board = board;
        this.nextPiecePanel = nextPiecePanel;
        this.holdPiecePanel = holdPiecePanel;
        this.levelPanel = levelPanel;
        this.curPiece = new Shape();
        this.nextPiece = new Shape();
        this.uiManager = board.getUIManager();
        this.shapeAndTetrominoesManager = new ShapeAndTetrominoesManager();
        this.timerManager = board.getTimerManager();
        //this.eventManager = new EventManager(this);
        this.configurationManager = new ConfigurationManager();
        this.BoardWidth= configurationManager.getBoardWidth();
        this.BoardHeight = configurationManager.getBoardHeight();

    }
    //*

    //* 블럭이 떨어지게 하는 함수
    public void dropDown() {
        int newY = curY;
        while (newY > 0) {
            if (!tryMove(curPiece, curX, newY - 1))
                break;
            --newY;
        }
        pieceDropped();
    }
    //*

    // 레벨에 따른 점수 배수를 반환하는 메서드
    public int getScoreMultiplier(int level) {
        switch (level) {
            case 2:
                return 20;
            case 3:
                return 50;
            case 4:
                return 100;
            case 5:
                return 500;
            default:
                return 10;  // 기본 점수 배수는 10입니다.
        }
    }

    //* 레벨 관리 함수
    public void updateLevel(int score) {
        if (score >= 10000) {
            level = 6;
        } else if (score >= 5000) {
            level = 5;
        } else if (score >= 2500) {
            level = 4;
        } else if (score >= 500) {
            level = 3;
        } else if (score >= 100) {
            level = 2;
        } else {
            level = 1;
        }
        adjustSpeed(level);
        levelPanel.updateLevel(level);
    }
    //*

    public void adjustSpeed(int level) {
        int delay = 400;  // Default delay
        switch(level) {
            case 2:
                delay = 300;
                break;
            case 3:
                delay = 200;
                break;
            case 4:
                delay = 150;
                break;
            case 5:
                delay = 100;
                break;
            case 6:
                delay = 50;
                break;
        }
        timerManager.setDelay(delay);
    }


    // 블럭이 바닥에 닿았나 확인하는 함수
    public void pieceDropped() {
       Tetrominoes[] boardArray = board.getBoardArray();
        for (int i = 0; i < 4; ++i) {
            int x = curX + curPiece.x(i);
            int y =curY - curPiece.y(i);
            boardArray[(y * BoardWidth) + x] = curPiece.getShape();

        }

        removeFullLines();

        if (!isFallingFinished) {
            newPiece();
        }
    }
    //*

    //* 새 조각 생성 함수
    public void newPiece() {

        shapeAndTetrominoesManager.generateNewShape();
        curPiece = shapeAndTetrominoesManager.getCurrentShape();
        nextPiece = shapeAndTetrominoesManager.getNextShape();
        curX = BoardWidth / 2 + 1;
        curY = BoardHeight - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {
            curPiece.setShape(Tetrominoes.NoShape);
            timerManager.stopTimer();
            if(score > bestScore)
               scoreManager.saveBestScore(score);
            isStarted = false;
           uiManager.updateStatusbar("game over");
        }

        nextPiecePanel.updateMiniBoard(nextPiece);
        nextPiecePanel.repaint();
    }
    //*

    //* 줄 제거 함수
    public void removeFullLines() {
        int numFullLines = 0;

        Tetrominoes[] boardArray = board.getBoardArray();

        for (int i = BoardHeight - 1; i >= 0; --i) {
            boolean lineIsFull = true;

            for (int j = 0; j < BoardWidth; ++j) {
                if (shapeAt(j, i) == Tetrominoes.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                ++numFullLines;
                for (int k = i; k < BoardHeight - 1; ++k) {
                    for (int j = 0; j < BoardWidth; ++j) {
                        boardArray[(k * BoardWidth) + j] = shapeAt(j, k + 1);
                    }
                }
            }

        }

        if (numFullLines > 0) {
            int scoreMultiplier = getScoreMultiplier(level);  // 레벨에 따른 점수 배수를 가져옵니다.
            score += numFullLines * scoreMultiplier;  // 줄의 수와 점수 배수를 곱하여 점수를 업데이트합니다.
            updateLevel(score);
            uiManager.updateScore(score);
            isFallingFinished = true;
            curPiece.setShape(Tetrominoes.NoShape);
            board.repaint();
        }
    }
    //*


    //* ?
    public Tetrominoes shapeAt(int x, int y) {
        Tetrominoes[] boardArray = board.getBoardArray();
        return boardArray[(y * BoardWidth) + x];
    }
//*

    //* 블럭 좌,우 이동 함수
    public boolean tryMove(Shape newPiece, int newX, int newY) {

        for (int i = 0; i < 4; ++i) {
            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);
            if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight)
                return false;
            if (shapeAt(x, y) != Tetrominoes.NoShape)
                return false;
        }

        curPiece = newPiece;
        curX = newX;
        curY = newY;
        board.repaint();
        return true;
    }
    //*

    //* 블럭이 한 줄 떨어지게 하는 함수
    public void oneLineDown() {
        if (!tryMove(curPiece, curX, curY - 1))
            pieceDropped();
    }
    //*

    //* 오른쪽 회전 함수
    public void rotateRight() {
        tryMove(shapeAndTetrominoesManager.rotateRight(), curX, curY);
    }

    //* 왼쪽 회전 함수
    public void rotateLeft() {
        tryMove(shapeAndTetrominoesManager.rotateLeft(), curX, curY);
    }
    //*



    //* 게임 정지 기능
    public void pause() {
        if (!isStarted)
            return;

        isPaused = !isPaused;
        if (isPaused) {
            timerManager.stopTimer();
            uiManager.updateStatusbar("paused");
        } else {
            timerManager.startTimer();
            uiManager.updateStatusbar(String.valueOf(numLinesRemoved));
        }
        board.repaint();
    }
    //*

    public boolean ghostTryMove(Shape newPiece, int newX, int newY) {
        for (int i = 0; i < 4; ++i) {
            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);
            if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight)
                return false;
            if (shapeAt(x, y) != Tetrominoes.NoShape)
                return false;
        }
        return true;
    }

    public void hold() {
        // 현재 블록을 임시 변수에 저장합니다.
        Shape tempPiece = curPiece;

        // 현재 블록을 홀드 영역에 저장합니다.
        if (holdPiece == null) {
            holdPiece = tempPiece;
            newPiece();

        } else {
            curPiece = holdPiece;
            holdPiece = tempPiece;
            shapeAndTetrominoesManager.setCurrentShape(curPiece);
            // 현재 블록의 위치를 초기화합니다.
            curX = BoardWidth / 2 + 1;
            curY = BoardHeight - 1 + curPiece.minY();
        }
        holdPiecePanel.updateHoldBoard(holdPiece);

        // 현재 블록을 지웁니다.

        // 보드를 업데이트하여 현재 블록을 지웁니다.
        board.repaint();
        holdPiecePanel.repaint();
    }


}
