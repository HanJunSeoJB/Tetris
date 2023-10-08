package kr.ac.jbnu.se.tetris;

// 상수 관리 클래스
public class ConfigurationManager {

    private int executionWidth;
    private int excutionHeight;

    private int MiniBoardWidth;
    private int MiniBoarHeight;

    private int delay;

    public ConfigurationManager() {
        // 기본 설정 값으로 초기화
        this.executionWidth = 10;
        this.excutionHeight = 20;
        this.delay = 400;
        this.MiniBoardWidth = 8;
        this.MiniBoarHeight = 4;
    }

    // 게임 설정을 로드하는 메소드 (예: 파일에서 불러오기)
    public void loadConfiguration() {
        // TODO: 설정 파일에서 설정 값을 불러오는 로직을 구현
    }

    // 게임 설정을 저장하는 메소드 (예: 파일에 저장하기)
    public void saveConfiguration() {
        // TODO: 설정 값을 설정 파일에 저장하는 로직을 구현
    }

    //* 각 설정에 대한 "getter" 메소드
    public int getBoardWidth() {
        return executionWidth;
    }

    public int getBoardHeight() {
        return excutionHeight;
    }

    public int getDelay() {
        return delay;
    }
    public int getMiniBoardWidth() {
        return MiniBoardWidth;
    }

    public int getMiniBoardHeight() {
        return MiniBoarHeight;
    }
    //*
}
