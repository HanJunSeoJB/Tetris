package kr.ac.jbnu.se.tetris;

public class  EventManager {
    //* 객체 초기화
    private final GameLogicManager gameLogicManager;
    public EventManager(GameLogicManager gameLogicManager) {
        this.gameLogicManager = gameLogicManager;
    }
    //*

    //* 각 키의 액션에 대한 동작처리 함수
    public void handleKeyAction(String action) {

        if (!gameLogicManager.isStarted() || gameLogicManager.getCurPiece().getShape() == Tetrominoes.NoShape) {
            return;
        }

        int currentX = gameLogicManager.getCurX();
        int currentY = gameLogicManager.getCurY();
        int lenth = gameLogicManager.getCurPiece().getWidth();

        switch (action) {
            case "pause":
                gameLogicManager.pause();
                break;

                //좌우 이동
            case "left":
                if (currentX >= 1) {
                    gameLogicManager.tryMove(gameLogicManager.getCurPiece(), currentX - 1, currentY);
                }
                else if (currentX < 1){
                    gameLogicManager.tryMove(gameLogicManager.getCurPiece(), gameLogicManager.BoardWidth - lenth, currentY);
                }
                break;

            case "right":
                if (currentX + lenth < gameLogicManager.BoardWidth) {
                    gameLogicManager.tryMove(gameLogicManager.getCurPiece(), currentX + 1, currentY);
                }
                else if (currentX + lenth >= gameLogicManager.BoardWidth){
                    gameLogicManager.tryMove(gameLogicManager.getCurPiece(), 0, currentY);
                }
                break;

            case "rotateRight":
                gameLogicManager.rotateRight();
                break;
            case "rotateLeft":
                gameLogicManager.rotateLeft();
                break;
            case "dropDown":
                gameLogicManager.dropDown();
                break;
            case "oneLineDown":
                gameLogicManager.oneLineDown();
                break;
            case "hold":
                gameLogicManager.hold();
                break;
            case "re":
                gameLogicManager.reStart();
                break;
            default:
                throw new IllegalArgumentException("Unknown action: " + action);
        }
    }
    //*
}
