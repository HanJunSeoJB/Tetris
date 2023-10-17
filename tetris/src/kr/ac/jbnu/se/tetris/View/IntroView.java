package kr.ac.jbnu.se.tetris.View;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

import kr.ac.jbnu.se.tetris.Board;
import kr.ac.jbnu.se.tetris.Model.IntroModel;
import kr.ac.jbnu.se.tetris.Model.SoundModel;
import kr.ac.jbnu.se.tetris.ScoreManager;

public class IntroView extends JPanel {
    /***************************************************************/
    // 멤버변수
    IntroModel introModel;
    ScoreManager scoreManager;

    ImageIcon intro, multi_start, how_to_start, ranking, sound;
    ImageIcon[] menu = new ImageIcon[4];
    ImageIcon[] menu_click = new ImageIcon[4];
    ImageIcon[] select_game = new ImageIcon[3];
    ImageIcon[] num = new ImageIcon[10];

    /***************************************************************/
    // IntroView 생성자
    public IntroView(IntroModel introModel) {
        this.introModel = introModel;
        this.scoreManager = new ScoreManager();
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
    }

    /***************************************************************/
    // drawMenu 메소드 : menu들을 그려줌
    public void drawMenu(Graphics g, IntroModel introModel) {
        for (int i = 0; i < menu.length; i++)
            g.drawImage(menu[i].getImage(), introModel.getMenuX(), introModel.getMenuY() + (i * introModel.getMenuInterval()), introModel.getMenuWidth(), introModel.getMenuHeight(), null);
        switch (introModel.getCheckEntered()) {
            case 1:
                g.drawImage(menu_click[0].getImage(), introModel.getMenuX(), introModel.getMenuY(), introModel.getMenuWidth(), introModel.getMenuHeight(), null);
                break;
            case 2:
                g.drawImage(menu_click[1].getImage(), introModel.getMenuX(), introModel.getMenuY() + introModel.getMenuInterval(), introModel.getMenuWidth(), introModel.getMenuHeight(), null);
                break;
            case 3:
                g.drawImage(menu_click[2].getImage(), introModel.getMenuX(), introModel.getMenuY() + (2*introModel.getMenuInterval()), introModel.getMenuWidth(), introModel.getMenuHeight(), null);
                break;
            case 4:
                g.drawImage(menu_click[3].getImage(), introModel.getMenuX(), introModel.getMenuY() + (3 * introModel.getMenuInterval()), introModel.getMenuWidth(), introModel.getMenuHeight(), null);
                break;
        }
        switch (introModel.getMenuState()) {
            case GAME_START:
                g.drawImage(select_game[0].getImage(), introModel.getMenuX(), introModel.getMenuY(), introModel.getMenuWidth(), introModel.getMenuHeight() * 3, null);
                break;
            case SINGLE_PLAY:
                g.drawImage(select_game[1].getImage(), introModel.getMenuX(), introModel.getMenuY(), introModel.getMenuWidth(), introModel.getMenuHeight() * 3, null);
                break;
            case MULTI_PLAY:
                g.drawImage(select_game[2].getImage(), introModel.getMenuX(), introModel.getMenuY(), introModel.getMenuWidth(), introModel.getMenuHeight() * 3, null);
                break;
            case HOW_TO_PLAY:
                g.drawImage(how_to_start.getImage(), introModel.getPlayX(), introModel.getPlayY(), introModel.getPlayWidth(), introModel.getPlayHeight(), null);
                break;
            case RANKING:
                g.drawImage(ranking.getImage(), 150, 150, 500, 500, null);
                drawNumber(scoreManager.loadScores(), g);

        }
        if (introModel.getCheckMulti() == 1)
            g.drawImage(multi_start.getImage(), 300, 680, 200, 50, null);
    }

    public void drawNumber(int[] scores, Graphics g) {
        int numX = introModel.getNumX();
        int numY = introModel.getNumY();
        int numWidth = introModel.getNumWidth();
        int numHeight = introModel.getNumHeight();
        int interval = introModel.getNumInterval();
        int[] numArray = scores.clone();  // scores 배열을 복제하여 numArray 배열 생성
        ImageIcon[] num = new ImageIcon[10];  // 숫자 이미지를 로드할 배열
        for(int i = 0; i < 10; i++) {
            num[i] = new ImageIcon("tetris/src/image/" + i + ".png");
        }
        int numInterval = 0;

        for (int i = 0; i < numArray.length; i++) {
            ArrayList<Integer> digitsList = new ArrayList<>();  // 각 자릿수를 저장할 리스트
            int tmpNum = numArray[i];  // numArray[i] 값을 일시적 변수에 저장

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
                g.drawImage(num[digit].getImage(), numX + Interval, numY + numInterval, numWidth, numHeight, null);
                Interval += 20;  // Interval 값 증가
            }

            numInterval += interval;  // numInterval 값 증가
        }
    }

    /***************************************************************/
    // paint 메소드 : 배경 이미지를 그려줌
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawImage(intro.getImage(), 0, 0, 800, 800, null);
        drawMenu(g, introModel);
        g.drawImage(sound.getImage(), 685, 29, 50, 50, null);
        setOpaque(false);
    }
}