package kr.ac.jbnu.se.tetris;

import java.util.Timer;

public class GameLogicManager {

    //* 변수 초기화
    private boolean isStarted = false;
    private boolean isPaused = false;
    private int numLinesRemoved = 0;
    private int curX = 0;
    private int curY = 0;
    private Shape curPiece;
    private boolean isFallingFinished = false;
    //*

    //* 객체 초기화
    private final Board board;
    private final TimerManager timerManager;
    private final UIManager uiManager;
    private final ShapeAndTetrominoesManager shapeAndTetrominoesManager;
    private final EventManager eventManager;
    private final ConfigurationManager configurationManager;

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
    //*

    //* 클래스 초기화
    public GameLogicManager(Board board) {
        this.board = board;
        this.curPiece = new Shape();
        this.uiManager = board.getUIManager();
        this.shapeAndTetrominoesManager = new ShapeAndTetrominoesManager();
        this.timerManager = board.getTimerManager();
        this.eventManager = new EventManager(this);
        this.configurationManager = new ConfigurationManager();

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

    // 블럭이 바닥에 닿았나 확인하는 함수
    public void pieceDropped() {
        int BoardWidth = board.getBoardWidth();
        Tetrominoes[] boardArray = board.getBoardArray();
        for (int i = 0; i < 4; ++i) {
            int x = curX + curPiece.x(i);
            int y =curY - curPiece.y(i);
            boardArray[(y * BoardWidth) + x] = curPiece.getShape();
        }

        removeFullLines();

        if (!isFallingFinished) {
            newPiece();
        } else {
            isFallingFinished = false;
        }
    }
    //*

    //* 새 조각 생성 함수
    public void newPiece() {
        int BoardWidth = configurationManager.getBoardWidth();
        int BoardHeight = configurationManager.getBoardHeight();
        shapeAndTetrominoesManager.generateNewShape();
        curPiece = shapeAndTetrominoesManager.getCurrentShape();
        curX = BoardWidth / 2 + 1;
        curY = BoardHeight - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {
            curPiece.setShape(Tetrominoes.NoShape);
            timerManager.stopTimer();
            isStarted = false;
           uiManager.updateStatusbar("game over");
        }
    }
    //*

    //* 줄 제거 함수
    public void removeFullLines() {
        int numFullLines = 0;
        int BoardWidth = configurationManager.getBoardWidth();
        int BoardHeight = configurationManager.getBoardHeight();
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
                    for (int j = 0; j < BoardWidth; ++j)
                        boardArray[(k * BoardWidth) + j] = shapeAt(j, k + 1);
                }
            }

        }

        if (numFullLines > 0) {
            numLinesRemoved += numFullLines;
            uiManager.updateScore(numLinesRemoved);
            isFallingFinished = true;
            curPiece.setShape(Tetrominoes.NoShape);
            board.repaint();
        }
    }
    //*


    //* ?
    public Tetrominoes shapeAt(int x, int y) {
        Tetrominoes[] boardArray = board.getBoardArray();
        int BoardWidth = configurationManager.getBoardWidth();
        return boardArray[(y * BoardWidth) + x];
    }
//*

    //* 블럭 좌,우 이동 함수
    public boolean tryMove(Shape newPiece, int newX, int newY) {
        int BoardWidth = configurationManager.getBoardWidth();
        int BoardHeight = configurationManager.getBoardHeight();
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
}
