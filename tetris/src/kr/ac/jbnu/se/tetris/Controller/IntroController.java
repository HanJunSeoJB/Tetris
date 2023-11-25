
package kr.ac.jbnu.se.tetris.Controller;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import kr.ac.jbnu.se.tetris.Model.SoundModel;
import kr.ac.jbnu.se.tetris.Model.IntroModel;
import kr.ac.jbnu.se.tetris.Tetris;
import kr.ac.jbnu.se.tetris.View.IntroView;
import kr.ac.jbnu.se.tetris.MenuState;

public class IntroController implements MouseListener, MouseMotionListener {
    /***************************************************************/
    // 멤버 변수
    private final IntroModel introModel;
    private final IntroView introView;
    private final JFrame frame;
    private final Container contentPane;

    private JSlider volumeSlider;
    private boolean isVolumeSliderVisible = false;
    private final SoundModel soundModel;
    /***************************************************************/
    // IntroController 생성자
    public IntroController(JFrame frame, Container contentPane, SoundModel soundModel) {
        this.introModel = new IntroModel();
        this.introView = new IntroView(introModel);
        contentPane.add(introView);
        introView.addMouseListener(this);
        introView.addMouseMotionListener(this);
        this.frame = frame;
        this.contentPane = contentPane;
        this.soundModel = soundModel;
    }

    private void createVolumeSlider() {
        volumeSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, (int)(IntroModel.VOLUME * 100));
        volumeSlider.setBounds(50, 80, 685, 30);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(1);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                float volume = source.getValue() / 100f;
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
                if(isWithinBounds(x, y, IntroModel.SELECT_GAME_X, IntroModel.SELECT_GAME_Y, IntroModel.SELECT_GAME_WIDTH, IntroModel.SELECT_GAME_HEIGHT)) {
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
            if(isWithinBounds(x, y, IntroModel.MENU_X, IntroModel.MENU_Y, IntroModel.MENU_WIDTH, IntroModel.MENU_HEIGHT)) {
                introModel.setCheckClicked(1);
                introModel.setMenuState(MenuState.GAME_START);
                return;
            }
// HOW TO PLAY
            else if(isWithinBounds(x, y, IntroModel.MENU_X, IntroModel.MENU_Y + IntroModel.MENU_HEIGHT, IntroModel.MENU_WIDTH, IntroModel.MENU_HEIGHT)) {
                introModel.setMenuState(MenuState.HOW_TO_PLAY);
                introView.repaint();
                return;
            }
            else if(isWithinBounds(x, y, IntroModel.MENU_X, IntroModel.MENU_Y + 2*IntroModel.MENU_HEIGHT+ IntroModel.MENU_INTERVAL, IntroModel.MENU_WIDTH, IntroModel.MENU_HEIGHT)) {
                introModel.setMenuState(MenuState.RANKING);
                introView.repaint();
                return;
            }
// GAME EXIT
            else if(isWithinBounds(x, y, IntroModel.MENU_X, IntroModel.MENU_Y + 3*IntroModel.MENU_HEIGHT + IntroModel.MENU_INTERVAL, IntroModel.MENU_WIDTH, IntroModel.MENU_HEIGHT)) {
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
    public void mouseEntered(MouseEvent e) { /* 사용 하지 않는 필수 구현 메소드 */ }
    @Override
    public void mouseExited(MouseEvent e) { /* 사용 하지 않는 필수 구현 메소드 */ }
    @Override
    public void mousePressed(MouseEvent e) { /* 사용 하지 않는 필수 구현 메소드 */ }
    @Override
    public void mouseReleased(MouseEvent e) { /* 사용 하지 않는 필수 구현 메소드 */ }
    @Override
    public void mouseDragged(MouseEvent e) { /* 사용 하지 않는 필수 구현 메소드 */ }
    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        // Menu에 마우스 갖다 댈 시 효과
        if((x >= IntroModel.MENU_X && x <= IntroModel.MENU_X + IntroModel.MENU_WIDTH) && (y >= IntroModel.MENU_Y && y <= IntroModel.MENU_Y + (4 * IntroModel.MENU_HEIGHT) + (3 * IntroModel.MENU_INTERVAL))) {
            if(y <= IntroModel.MENU_Y + IntroModel.MENU_HEIGHT)
                introModel.setCheckEntered(1);
            if(y >= IntroModel.MENU_Y + IntroModel.MENU_HEIGHT
                    && y <= IntroModel.MENU_Y + IntroModel.MENU_HEIGHT + IntroModel.MENU_INTERVAL)
                introModel.setCheckEntered(2);
            if(y >= IntroModel.MENU_Y + IntroModel.MENU_HEIGHT + IntroModel.MENU_INTERVAL
                    && y <= IntroModel.MENU_Y + IntroModel.MENU_HEIGHT + 2 * IntroModel.MENU_INTERVAL)
                introModel.setCheckEntered(3);
            if(y >= IntroModel.MENU_Y + IntroModel.MENU_HEIGHT + 2 * IntroModel.MENU_INTERVAL
                    && y <= IntroModel.MENU_Y + IntroModel.MENU_HEIGHT + 3 * IntroModel.MENU_INTERVAL)
                introModel.setCheckEntered(4);
        }
        else introModel.setCheckEntered(0);
        // SINGLE PLAY & MULTI PLAY
        if(introModel.getMenuState() == MenuState.GAME_START || introModel.getMenuState() == MenuState.SINGLE_PLAY || introModel.getMenuState() == MenuState.MULTI_PLAY) {
            if((x >= IntroModel.SELECT_GAME_X && x <= IntroModel.SELECT_GAME_X + IntroModel.SELECT_GAME_WIDTH) && (y >= IntroModel.SELECT_GAME_Y && y <= IntroModel.SELECT_GAME_Y + IntroModel.SELECT_GAME_HEIGHT)) {
                introModel.setMenuState(MenuState.SINGLE_PLAY);

            }

            else if((x >= IntroModel.SELECT_GAME_X && x <= IntroModel.SELECT_GAME_X + IntroModel.SELECT_GAME_WIDTH) && (y >= IntroModel.SELECT_GAME_Y + IntroModel.SELECT_GAME_HEIGHT + IntroModel.SELECT_GAME_INTERVAL && y <= IntroModel.SELECT_GAME_Y + (IntroModel.SELECT_GAME_HEIGHT * 2) + IntroModel.SELECT_GAME_INTERVAL))
            {
                introModel.setMenuState(MenuState.MULTI_PLAY);
            }
            else {
                introModel.setMenuState(MenuState.GAME_START);

            }

        }
        // repaint
        introView.repaint();
    }
}