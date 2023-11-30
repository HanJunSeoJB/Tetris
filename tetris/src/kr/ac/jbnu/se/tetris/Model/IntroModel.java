package kr.ac.jbnu.se.tetris.Model;

import kr.ac.jbnu.se.tetris.MenuState;

public class IntroModel {
    // 변수 초기화
    public static final int PLAY_WIDTH = 400;
    public static final int PLAY_HEIGHT = 400;
    public static final int MENU_WIDTH = 450;
    public static final int MENU_HEIGHT = 70;
    public static final int MENU_X = 154;
    public static final int MENU_Y = 200;
    public static final int NUM_WIDTH = 45;
    public static final int NUM_HEIGHT = 45;
    public static final int NUM_X = 246;
    public static final int NUM_Y = 193;

    public static final int NUM_INTERVAL = 72;
    public static final int MENU_INTERVAL = 100;
    public static final int SELECT_GAME_X = 240;
    public static final int SELECT_GAME_Y = 210;
    public static final int SELECT_GAME_WIDTH = 320;
    public static final int SELECT_GAME_HEIGHT = 80;
    public static final int SELECT_GAME_INTERVAL = 30;
    private static int checkEntered = 0;
    private static int checkClicked = 0;
    public static final float VOLUME = 1.0f;
    private static MenuState menuState = MenuState.NONE;
    private static int checkMulti = 0;

    /***************************************************************/
    // getter & setter
    public static int getCheckEntered() { return checkEntered; }
    public void setCheckEntered(int checkEntered) {
        this.checkEntered = checkEntered;
    }
    public int getCheckClicked() { return checkClicked; }
    public void setCheckClicked(int checkClicked) {
        this.checkClicked = checkClicked;
    }

    public static MenuState getMenuState() {
        return menuState;
    }

    public void setMenuState(MenuState menuState) {
        this.menuState = menuState;
    }

    public static int getCheckMulti() {
        return checkMulti;
    }
}