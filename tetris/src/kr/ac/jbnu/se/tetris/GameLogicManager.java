package kr.ac.jbnu.se.tetris;

import kr.ac.jbnu.se.tetris.Model.SoundModel;
import javax.swing.Timer;
import javax.swing.SwingWorker;

public class GameLogicManager {

    //* 변수 초기화
    private boolean isStarted = false;
    private boolean isPaused = false;
    private boolean inFeverMode = false;
    private int feverModeLinesCleared = 0;

    public static final int NUM_LINES_REMOVED = 0;
    private int curX = 0;
    private int curY = 0;

    private int level = 1;

    private int score = 0;

    private final int BoardWidth;
    private final int BoardHeight;
    private boolean isFallingFinished = false;
    //*

    //* 객체 초기화
    private final Board board;
    private final TimerManager timerManager;
    private final UIManager uiManager;
    private final ShapeAndTetrominoesManager shapeAndTetrominoesManager;

    NextPiecePanel nextPiecePanel;
    HoldPiecePanel holdPiecePanel;
    LevelPanel levelPanel;
    ScoreManager scoreManager;
    SoundModel soundModel;

    //*

    //* 게터&세터 메소드
    public int getCurX() {
        return curX;
    }


    public int getCurY() {
        return curY;
    }


    public Shape getCurPiece() {
        return shapeAndTetrominoesManager.getCurrentShape();
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
        return NUM_LINES_REMOVED;
    }

    public boolean isStarted() {
        return isStarted;
    }

    //*

    //* 클래스 초기화
    public GameLogicManager(Board board, NextPiecePanel nextPiecePanel, HoldPiecePanel holdPiecePanel, LevelPanel levelPanel, ScoreManager scoreManager, UIManager uiManager) {
        this.scoreManager = scoreManager;
        this.soundModel = new SoundModel();
        this.board = board;
        this.nextPiecePanel = nextPiecePanel;
        this.holdPiecePanel = holdPiecePanel;
        this.levelPanel = levelPanel;
        this.uiManager = uiManager;
        this.shapeAndTetrominoesManager = new ShapeAndTetrominoesManager();
        int delay = 400;
        this.timerManager = new TimerManager(board, delay);
        this.BoardWidth= board.getBoardWidth();
        this.BoardHeight = board.getBoardHeight();

    }
    //*

    //* 블럭이 떨어지게 하는 함수
    public void dropDown() {
        int newY = curY;
        Shape curPiece = shapeAndTetrominoesManager.getCurrentShape();
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
        return switch (level) {
            case 2 -> 20;
            case 3 -> 50;
            case 4 -> 100;
            case 5 -> 500;
            default -> 10;  // 기본 점수 배수는 10입니다.
        };
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
        int delay = switch (level) {
            case 2 -> 300;
            case 3 -> 200;
            case 4 -> 150;
            case 5 -> 100;
            case 6 -> 50;
            default -> 400;  // Default delay
        };
        timerManager.setDelay(delay);
    }


    // 블럭이 바닥에 닿았나 확인하는 함수
    public void pieceDropped() {
        Shape curPiece = shapeAndTetrominoesManager.getCurrentShape();
        Tetrominoes[] boardArray = board.getBoardArray();
        for (int i = 0; i < 4; ++i) {
            int x = curX + curPiece.x(i);
            int y =curY - curPiece.y(i);
            boardArray[(y * BoardWidth) + x] = curPiece.getPieceShape();

        }

        removeFullLines();

        if (!isFallingFinished) {
            soundModel.dropBlockPlay();
            newPiece();
        }
    }
    //*

    //* 새 조각 생성 함수
    public void newPiece() {
        shapeAndTetrominoesManager.generateNewShape();
        Shape curPiece = shapeAndTetrominoesManager.getCurrentShape();
        Shape nextPiece = shapeAndTetrominoesManager.getNextShape();
        curX = BoardWidth / 2 + 1;
        curY = BoardHeight - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {
            curPiece.setShape(Tetrominoes.NO_SHAPE);
            timerManager.stopTimer();
            ScoreManager.updateAndSaveScores(score);
            isStarted = false;
            uiManager.updateStatusbar("game over");
        }

        nextPiecePanel.updateMiniBoard(nextPiece);
        nextPiecePanel.repaint();
    }
    //*

    public void startTimer() {
        timerManager.startTimer();
    }

    //* 줄 제거 함수
    public void removeFullLines() {
        int numFullLines = 0;
        Shape curPiece = shapeAndTetrominoesManager.getCurrentShape();
        Tetrominoes[] boardArray = board.getBoardArray();

        boolean[] fullLines = new boolean[BoardHeight];

        for (int i = BoardHeight - 1; i >= 0; --i) {
            boolean lineIsFull = true;

            for (int j = 0; j < BoardWidth; ++j) {
                if (shapeAt(j, i) == Tetrominoes.NO_SHAPE) {
                    lineIsFull = false;
                    break;
                }
            }

            fullLines[i] = lineIsFull;

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
            soundModel.clearBlockPlay();
            int scoreMultiplier = getScoreMultiplier(level);
            if(!inFeverMode)// 레벨에 따른 점수 배수를 가져옵니다.
                score += numFullLines * scoreMultiplier;// 줄의 수와 점수 배수를 곱하여 점수를 업데이트합니다.
            else
                score += numFullLines * scoreMultiplier * (feverModeLinesCleared+1);
            updateLevel(score);
            uiManager.updateScore(score);
            isFallingFinished = true;
            curPiece.setShape(Tetrominoes.NO_SHAPE);
            feverMode(numFullLines);
            board.repaint();

            if (inFeverMode) {
                uiManager.changeColor(inFeverMode);

                feverModeLinesCleared += numFullLines;
                // Fever Mode 종료 조건 체크
                if (feverModeLinesCleared >= 10) {
                    inFeverMode = false;
                    System.out.println("Fever Mode Deactivated!");
                    uiManager.changeColor(inFeverMode);
                    feverModeLinesCleared = 0;
                }
            }
        }
    }

    private void feverMode(int numFullLines) {
        // 한 번에 5개 이상의 줄을 제거했는지 체크
        if (numFullLines >= 1) {
            if (inFeverMode) {
                return;
            }
            // Fever Mode 활성화
            inFeverMode = true;
            System.out.println("Fever Mode Activated!");
            feverModeLinesCleared = 0;
        }
    }



    public Tetrominoes shapeAt(int x, int y) {
        Tetrominoes[] boardArray = board.getBoardArray();
        return boardArray[(y * BoardWidth) + x];
    }

    //* 블럭 좌,우 이동 함수
    public boolean tryMove(Shape newPiece, int newX, int newY) {

        for (int i = 0; i < 4; ++i){
            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);

            // 왼쪽을 벗어나면 가장 오른쪽에 위치하도록 조정
            if (x < 0) {
                newX += (BoardWidth + x);
            }

            // 오른쪽을 벗어나면 가장 왼쪽에 위치하도록 조정
            if (x >= BoardWidth) {
                newX = (x - BoardWidth + 1);
            }

            if (y < 0 || y >= BoardHeight)
                return false;
            if (shapeAt(x, y) != Tetrominoes.NO_SHAPE)
                return false;
        }

        curX = newX;
        curY = newY;
        board.repaint();
        return true;
    }
    //*

    //* 블럭이 한 줄 떨어지게 하는 함수
    public void oneLineDown() {
        Shape curPiece = shapeAndTetrominoesManager.getCurrentShape();
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
            uiManager.updateStatusbar(String.valueOf(NUM_LINES_REMOVED));
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
            if (shapeAt(x, y) != Tetrominoes.NO_SHAPE)
                return false;
        }
        return true;
    }
    public void reStart() {
        board.reStart();
    }
    public void hold() {
        Shape curPiece = shapeAndTetrominoesManager.getCurrentShape();
        // 현재 블록을 임시 변수에 저장합니다.
        Shape tempPiece = curPiece;
        Shape holdPiece = shapeAndTetrominoesManager.getHoldShape();

        // 현재 블록을 홀드 영역에 저장합니다.
        if (holdPiece == null) {
            shapeAndTetrominoesManager.setHoldShape(tempPiece);
            newPiece();

        } else {
            curPiece = holdPiece;
            shapeAndTetrominoesManager.setHoldShape(tempPiece);
            shapeAndTetrominoesManager.setCurrentShape(curPiece);
            // 현재 블록의 위치를 초기화합니다.
            curX = BoardWidth / 2 + 1;
            curY = BoardHeight - 1 + curPiece.minY();
        }
        holdPiecePanel.updateHoldBoard(shapeAndTetrominoesManager.getHoldShape());

        // 보드를 업데이트하여 현재 블록을 지웁니다.
        board.repaint();
        holdPiecePanel.repaint();
    }
}
