package kr.ac.jbnu.se.tetris;

public class EventManager {
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

        switch (action) {
            case "pause":
                gameLogicManager.pause();
                break;
            case "left":
                gameLogicManager.tryMove(gameLogicManager.getCurPiece(), currentX - 1, currentY);
                break;
            case "right":
                gameLogicManager.tryMove(gameLogicManager.getCurPiece(), currentX + 1, currentY);
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
            default:
                throw new IllegalArgumentException("Unknown action: " + action);
        }
    }
    //*
}