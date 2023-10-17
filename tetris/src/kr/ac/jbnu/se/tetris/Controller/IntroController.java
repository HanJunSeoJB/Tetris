
package kr.ac.jbnu.se.tetris.Controller;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

import kr.ac.jbnu.se.tetris.Board;
import kr.ac.jbnu.se.tetris.Model.SingleModel;
import kr.ac.jbnu.se.tetris.Model.SoundModel;
import kr.ac.jbnu.se.tetris.Model.IntroModel;
import kr.ac.jbnu.se.tetris.TAdapter;
import kr.ac.jbnu.se.tetris.Tetris;
import kr.ac.jbnu.se.tetris.View.IntroView;
import kr.ac.jbnu.se.tetris.MenuState;
import kr.ac.jbnu.se.tetris.View.SingleView;

public class IntroController implements MouseListener, MouseMotionListener {
    /***************************************************************/
    // 멤버 변수
    IntroModel introModel;
    IntroView introView;
    JFrame frame;
    JTextField nameField;
    Container contentPane;

    SingleModel singleModel;
    SingleView singleView;
    SingleController singleController;
    private JSlider volumeSlider;
    private boolean isVolumeSliderVisible = false;
    SoundModel soundModel;
    String name;
    /***************************************************************/
    // IntroController 생성자
    public IntroController(IntroModel introModel, IntroView introView, JFrame frame, Container contentPane, SoundModel soundModel) {
        this.introModel = introModel;
        this.introView = introView;
        this.frame = frame;
        this.contentPane = contentPane;
        nameField = new JTextField(" 게임시작 대기중 ");
        singleModel = new SingleModel(soundModel);
        singleView = new SingleView(singleModel);
        singleController = new SingleController(singleModel, singleView, frame, contentPane, soundModel);
        name = "ClientA";
        this.soundModel = soundModel;
    }

    private void createVolumeSlider() {
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(introModel.getVolume() * 100));
        volumeSlider.setBounds(50, 80, 685, 30);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(1);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                float volume = source.getValue() / 100f;
                introModel.setVolume(volume);
                soundModel.setVolume(volume);
            }
        });
        introView.add(volumeSlider);
        isVolumeSliderVisible = true;
    }

    private void hideVolumeSlider() {
        introView.remove(volumeSlider);
        isVolumeSliderVisible = false;
    }

    private boolean isWithinBounds(int x, int y, int startX, int startY, int width, int height) {
        return (x >= startX && x <= startX + width) && (y >= startY && y <= startY + height);
    }
    /***************************************************************/
    // MouseListner, MouseMotionListener 오버라이딩

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            System.out.println("x : " + e.getX() + ", y : " + e.getY() + ", " + introModel.getCheckClicked());

            // SINGLE PLAY & MULTI PLAY
            if(introModel.getCheckClicked() == 1) {
                if(isWithinBounds(x, y, introModel.getSelect_gameX(), introModel.getSelect_gameY(), introModel.getSelect_gameWidth(), introModel.getSelect_gameHeight())) {
                    soundModel.intoBgmStop();
                    Tetris tetrisInstance = new Tetris(false, soundModel);
                    contentPane.remove(introView);
                    frame.dispose();
                    tetrisInstance.setVisible(true);
                    return;
                }
                else if(y >= 320 && y <= 383) {
                    soundModel.intoBgmStop();
                    Tetris tetrisInstance = new Tetris(true, soundModel);
                    contentPane.remove(introView);
                    frame.dispose();
                    tetrisInstance.setVisible(true);
                    return;
                }
                else {
                    introModel.setCheckClicked(0);
                }

            }

            // GAME START
            if(isWithinBounds(x, y, introModel.getMenuX(), introModel.getMenuY(), introModel.getMenuWidth(), introModel.getMenuHeight())) {
                introModel.setCheckClicked(1);
                introModel.setMenuState(MenuState.GAME_START);
                return;
            }
// HOW TO PLAY
            else if(isWithinBounds(x, y, introModel.getMenuX(), introModel.getMenuY() + introModel.getMenuHeight(), introModel.getMenuWidth(), introModel.getMenuHeight())) {
                introModel.setMenuState(MenuState.HOW_TO_PLAY);
                introView.repaint();
                return;
            }
            else if(isWithinBounds(x, y, introModel.getMenuX(), introModel.getMenuY() + 2*introModel.getMenuHeight()+ introModel.getMenuInterval(), introModel.getMenuWidth(), introModel.getMenuHeight())) {
                introModel.setMenuState(MenuState.RANKING);
                introView.repaint();
                return;
            }
// GAME EXIT
            else if(isWithinBounds(x, y, introModel.getMenuX(), introModel.getMenuY() + 3*introModel.getMenuHeight() + introModel.getMenuInterval(), introModel.getMenuWidth(), introModel.getMenuHeight())) {
                System.exit(1);
                return;
            }

            else if(isWithinBounds(x, y, 685, 29, 50, 50)) {
                createVolumeSlider();
                introView.repaint();  // refresh the view to show/hide the slider
                return;
            }
            if(isVolumeSliderVisible)
                hideVolumeSlider();
            introView.repaint();
            introModel.setMenuState(MenuState.NONE);

        }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        int x = e.getX();
        int y = e.getY();
        /***************************************************************/
        // Menu에 마우스 갖다 댈 시 효과
        if((x >= introModel.getMenuX() && x <= introModel.getMenuX() + introModel.getMenuWidth()) && (y >= introModel.getMenuY() && y <= introModel.getMenuY() + (4 * introModel.getMenuHeight()) + (3 * introModel.getMenuInterval()))) {
            if((x >= introModel.getMenuX() && x <= introModel.getMenuX() + introModel.getMenuWidth()) && (y >= introModel.getMenuY() && y <= introModel.getMenuY() + introModel.getMenuHeight()))
                introModel.setCheckEntered(1);
            if((x >= introModel.getMenuX() && x <= introModel.getMenuX() + introModel.getMenuWidth()) && (y >= introModel.getMenuY() + introModel.getMenuHeight() && y <= introModel.getMenuY() + introModel.getMenuHeight() + introModel.getMenuInterval()))
                introModel.setCheckEntered(2);
            if((x >= introModel.getMenuX() && x <= introModel.getMenuX() + introModel.getMenuWidth()) && (y >= introModel.getMenuY() + introModel.getMenuHeight() + introModel.getMenuInterval() && y <= introModel.getMenuY() + introModel.getMenuHeight() + (2 * introModel.getMenuInterval())))
                introModel.setCheckEntered(3);
            if((x >= introModel.getMenuX() && x <= introModel.getMenuX() + introModel.getMenuWidth()) && (y >= introModel.getMenuY() + introModel.getMenuHeight() + (2 * introModel.getMenuInterval()) && y <= introModel.getMenuY() + introModel.getMenuHeight() + (3 * introModel.getMenuInterval())))
                introModel.setCheckEntered(4);
        }
        if( (x >= introModel.getMenuX() && x <= introModel.getMenuX() + introModel.getMenuWidth()) && (y >= introModel.getMenuY() && y <= introModel.getMenuY() + introModel.getMenuHeight()) ||
                (x >= introModel.getMenuX() && x <= introModel.getMenuX() + introModel.getMenuWidth()) && (y >= introModel.getMenuY() + introModel.getMenuHeight() && y <= introModel.getMenuY() + introModel.getMenuHeight() + introModel.getMenuInterval()) ||
                (x >= introModel.getMenuX() && x <= introModel.getMenuX() + introModel.getMenuWidth()) && (y >= introModel.getMenuY() + introModel.getMenuHeight() + introModel.getMenuInterval() && y <= introModel.getMenuY() + introModel.getMenuHeight() + (2 * introModel.getMenuInterval())) ||
                (x >= introModel.getMenuX() && x <= introModel.getMenuX() + introModel.getMenuWidth()) && (y >= introModel.getMenuY() + introModel.getMenuHeight() + (2 * introModel.getMenuInterval()) && y <= introModel.getMenuY() + introModel.getMenuHeight() + (3 * introModel.getMenuInterval())))
            ;
        else introModel.setCheckEntered(0);
        /***************************************************************/
        // SINGLE PLAY & MULTI PLAY
        if(introModel.getMenuState() == MenuState.GAME_START || introModel.getMenuState() == MenuState.SINGLE_PLAY || introModel.getMenuState() == MenuState.MULTI_PLAY) {
            if((x >= introModel.getSelect_gameX() && x <= introModel.getSelect_gameX() + introModel.getSelect_gameWidth()) && (y >= introModel.getSelect_gameY() && y <= introModel.getSelect_gameY() + introModel.getSelect_gameHeight())) {
                introModel.setMenuState(MenuState.SINGLE_PLAY);

            }

            else if((x >= introModel.getSelect_gameX() && x <= introModel.getSelect_gameX() + introModel.getSelect_gameWidth()) && (y >= introModel.getSelect_gameY() + introModel.getSelect_gameHeight() + introModel.getSelect_gameInterval() && y <= introModel.getSelect_gameY() + (introModel.getSelect_gameHeight() * 2) + introModel.getSelect_gameInterval()))
            {
                introModel.setMenuState(MenuState.MULTI_PLAY);
            }
            else {
                introModel.setMenuState(MenuState.GAME_START);

            }

        }
        /***************************************************************/
        // repaint
        introView.repaint();
    }
}