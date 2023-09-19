package kr.ac.jbnu.se.tetris;

public class GameLogicManager {
    private final Board board;


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



    private int curX = 0;
    private int curY = 0;
    private Shape curPiece;
    private boolean isFallingFinished = false;
    private boolean isStarted = false;
    private boolean isPaused = false;
    private int numLinesRemoved = 0;

    public GameLogicManager(Board board) {
        this.board = board;
        this.curPiece = new Shape();

    }

    public void dropDown() {
        int newY = curY;
        while (newY > 0) {
            if (!tryMove(curPiece, curX, newY - 1))
                break;
            --newY;
        }
        pieceDropped();
    }

    public void pieceDropped() {
        int BoardWidth = board.getBoardWidth();
        Tetrominoes[] boardArray = board.getBoardArray();
        for (int i = 0; i < 4; ++i) {
            int x = curX + curPiece.x(i);
            int y = curY - curPiece.y(i);
            boardArray[(y * BoardWidth) + x] = curPiece.getShape();
        }

        removeFullLines();

        if (!isFallingFinished)
            newPiece();
    }
    public void newPiece() {
        int BoardWidth = board.getBoardWidth();
        int BoardHeight = board.getBoardHeight();
        curPiece.setRandomShape();
        curX = BoardWidth / 2 + 1;
        curY = BoardHeight - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {
            curPiece.setShape(Tetrominoes.NoShape);
            board.stopTimer();
            isStarted = false;
           board.updateStatusbar("game over");
        }
    }

    public void removeFullLines() {
        int numFullLines = 0;
        int BoardWidth = board.getBoardWidth();
        int BoardHeight = board.getBoardHeight();
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
           board.updateStatusbar(String.valueOf(numLinesRemoved));
            isFallingFinished = true;
            curPiece.setShape(Tetrominoes.NoShape);
            board.repaint();
        }
    }

    public Tetrominoes shapeAt(int x, int y) {
        Tetrominoes[] boardArray = board.getBoardArray();
        int BoardWidth = board.getBoardWidth();
        return boardArray[(y * BoardWidth) + x];
    }


    public boolean tryMove(Shape newPiece, int newX, int newY) {
        int BoardWidth = board.getBoardWidth();
        int BoardHeight = board.getBoardHeight();
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

    public void oneLineDown() {
        if (!tryMove(curPiece, curX, curY - 1))
            pieceDropped();
    }

    public void pause() {
        if (!isStarted)
            return;

        isPaused = !isPaused;
        if (isPaused) {
            board.stopTimer();
            board.updateStatusbar("paused");
        } else {
            board.startTimer();
            board.updateStatusbar(String.valueOf(numLinesRemoved));
        }
        board.repaint();
    }

    public void handleKeyAction(String action) {

        if (!isStarted || curPiece.getShape() == Tetrominoes.NoShape) {
            return;
        }

        switch (action) {
            case "pause":
                pause();
                break;
            case "left":
                tryMove(curPiece, curX - 1, curY);
                break;
            case "right":
                tryMove(curPiece, curX + 1, curY);
                break;
            case "rotateRight":
                tryMove(curPiece.rotateRight(), curX, curY);
                break;
            case "rotateLeft":
                tryMove(curPiece.rotateLeft(), curX, curY);
                break;
            case "dropDown":
                dropDown();
                break;
            case "oneLineDown":
                oneLineDown();
                break;
            default:
                throw new IllegalArgumentException("Unknown action: " + action);
        }
    }
}
