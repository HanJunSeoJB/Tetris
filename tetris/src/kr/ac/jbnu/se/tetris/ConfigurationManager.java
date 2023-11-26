package kr.ac.jbnu.se.tetris;

import java.io.Serializable;

// 상수 관리 클래스
public class ConfigurationManager implements Serializable {

    private final int boardWidth;
    private final int boardHeight;

    private final int MiniBoardWidth;
    private final int MiniBoarHeight;
    private final int holdBoardWidth;
    private final int holdBoarHeight;

    public ConfigurationManager()  {
        // 기본 설정 값으로 초기화
        this.boardWidth = 10;
        this.boardHeight = 20;
        this.MiniBoardWidth = 8;
        this.MiniBoarHeight = 4;
        this.holdBoardWidth = 8;
        this.holdBoarHeight = 4;
    }

    //* 각 설정에 대한 "getter" 메소드
    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getMiniBoardWidth() {
        return MiniBoardWidth;
    }

    public int getMiniBoardHeight() {
        return MiniBoarHeight;
    }
    public int getHoldBoardWidth() {
        return holdBoardWidth;
    }

    public int getHoldBoarHeight() {
        return holdBoarHeight;
    }

    //*
}
