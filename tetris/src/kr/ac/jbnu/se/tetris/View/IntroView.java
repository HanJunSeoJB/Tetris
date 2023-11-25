package kr.ac.jbnu.se.tetris.View;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import kr.ac.jbnu.se.tetris.Model.IntroModel;
import kr.ac.jbnu.se.tetris.ScoreManager;

public class IntroView extends JPanel {
    /***************************************************************/
    // 멤버변수
    private final IntroModel introModel;
    ImageIcon intro;
    ImageIcon multi_start;
    ImageIcon how_to_start;
    ImageIcon ranking;
    ImageIcon sound;
    ImageIcon[] menu = new ImageIcon[4];
    ImageIcon[] menu_click = new ImageIcon[4];
    ImageIcon[] select_game = new ImageIcon[3];
    ImageIcon[] num = new ImageIcon[10];

    /***************************************************************/
    // IntroView 생성자
    public IntroView(IntroModel introModel) {
        this.introModel = introModel;
        intro = new ImageIcon("tetris/src/image/intro.png");
        ranking =new ImageIcon("tetris/src/image/ranking.png");
        sound = new ImageIcon("tetris/src/image/sound.png");
        multi_start = new ImageIcon("tetris/src/image/multi_start.png");
        how_to_start = new ImageIcon("tetris/src/image/how_to_play.png");
        menu[0] = new ImageIcon("tetris/src/image/menu1.png");
        menu[1] = new ImageIcon("tetris/src/image/menu2.png");
        menu[2] = new ImageIcon("tetris/src/image/menu3.png");
        menu[3] = new ImageIcon("tetris/src/image/menu4.png");
        menu_click[0] = new ImageIcon("tetris/src/image/menu1_click.png");
        menu_click[1] = new ImageIcon("tetris/src/image/menu2_click.png");
        menu_click[2] = new ImageIcon("tetris/src/image/menu3_click.png");
        menu_click[3] = new ImageIcon("tetris/src/image/menu4_click.png");
        select_game[0] = new ImageIcon("tetris/src/image/select_game.png");
        select_game[1] = new ImageIcon("tetris/src/image/select_game_click1.png");
        select_game[2] = new ImageIcon("tetris/src/image/select_game_click2.png");
        for(int i = 0; i < 10; i++) {
            num[i] = new ImageIcon("tetris/src/image/"+ i +".png");
        }
        // 창 크기 입렫 받기

    }

    /***************************************************************/
    // drawMenu 메소드 : menu들을 그려줌
    public void drawMenu(Graphics g) {
        for (int i = 0; i < menu.length; i++)
            g.drawImage(menu[i].getImage(), IntroModel.MENU_X, IntroModel.MENU_Y + (i * IntroModel.MENU_INTERVAL), IntroModel.MENU_WIDTH, IntroModel.MENU_HEIGHT, null);
        switch (introModel.getCheckEntered()) {
            case 1 -> g.drawImage(menu_click[0].getImage(), IntroModel.MENU_X, IntroModel.MENU_Y, IntroModel.MENU_WIDTH,
                    IntroModel.MENU_HEIGHT, null);
            case 2 -> g.drawImage(menu_click[1].getImage(), IntroModel.MENU_X,
                    IntroModel.MENU_Y + IntroModel.MENU_INTERVAL, IntroModel.MENU_WIDTH, IntroModel.MENU_HEIGHT, null);
            case 3 -> g.drawImage(menu_click[2].getImage(), IntroModel.MENU_X,
                    IntroModel.MENU_Y + (2 * IntroModel.MENU_INTERVAL), IntroModel.MENU_WIDTH, IntroModel.MENU_HEIGHT,
                    null);
            case 4 -> g.drawImage(menu_click[3].getImage(), IntroModel.MENU_X,
                    IntroModel.MENU_Y + (3 * IntroModel.MENU_INTERVAL), IntroModel.MENU_WIDTH, IntroModel.MENU_HEIGHT,
                    null);
            default -> {}
        }
        switch (introModel.getMenuState()) {
            case GAME_START -> g.drawImage(select_game[0].getImage(), IntroModel.MENU_X, IntroModel.MENU_Y,
                    IntroModel.MENU_WIDTH, IntroModel.MENU_HEIGHT * 3, null);
            case SINGLE_PLAY -> g.drawImage(select_game[1].getImage(), IntroModel.MENU_X, IntroModel.MENU_Y,
                    IntroModel.MENU_WIDTH, IntroModel.MENU_HEIGHT * 3, null);
            case MULTI_PLAY -> g.drawImage(select_game[2].getImage(), IntroModel.MENU_X, IntroModel.MENU_Y,
                    IntroModel.MENU_WIDTH, IntroModel.MENU_HEIGHT * 3, null);
            case HOW_TO_PLAY ->
                    g.drawImage(how_to_start.getImage(), IntroModel.MENU_X, IntroModel.MENU_Y, IntroModel.PLAY_WIDTH,
                            IntroModel.PLAY_HEIGHT, null);
            case RANKING -> {
                g.drawImage(ranking.getImage(), 150, 150, 500, 500, null);
                drawNumber(ScoreManager.loadScores(), g);
            }
        }
        if (introModel.getCheckMulti() == 1)
            g.drawImage(multi_start.getImage(), 300, 680, 200, 50, null);
    }

    public void drawNumber(int[] scores, Graphics g) {
        int[] numArray = scores.clone();  // scores 배열을 복제하여 numArray 배열 생성
        ImageIcon[] num = new ImageIcon[10];  // 숫자 이미지를 로드할 배열
        for(int i = 0; i < 10; i++) {
            num[i] = new ImageIcon("tetris/src/image/" + i + ".png");
        }
        int numInterval = 0;

        for (int j : numArray) {
            ArrayList<Integer> digitsList = new ArrayList<>();  // 각 자릿수를 저장할 리스트
            int tmpNum = j;  // numArray[i] 값을 일시적 변수에 저장

            // tmpNum 값이 0이 될 때까지 반복
            while (tmpNum != 0) {
                int tmp = tmpNum % 10;  // 일의 자리 숫자 추출
                digitsList.add(tmp);  // 일의 자리 숫자를 리스트에 추가
                tmpNum /= 10;  // tmpNum 값을 10으로 나누어서 다음 자리 수로 이동
            }

            Collections.reverse(digitsList);  // 리스트를 반전시키기(올바른 순서로 만들기)

            int Interval = 0;
            // 올바른 순서로 숫자를 그리기
            for (int digit : digitsList) {
                g.drawImage(num[digit].getImage(), IntroModel.NUM_X + Interval, IntroModel.NUM_Y + numInterval,
                        IntroModel.NUM_WIDTH, IntroModel.NUM_HEIGHT, null);
                Interval += 20;  // Interval 값 증가
            }

            numInterval += IntroModel.NUM_INTERVAL;  // numInterval 값 증가
        }
    }

    /***************************************************************/
    // paint 메소드 : 배경 이미지를 그려줌
    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawImage(intro.getImage(), 0, 0, 800, 800, null);
        drawMenu(g);
        g.drawImage(sound.getImage(), 685, 29, 50, 50, null);
        setOpaque(false);
    }
}