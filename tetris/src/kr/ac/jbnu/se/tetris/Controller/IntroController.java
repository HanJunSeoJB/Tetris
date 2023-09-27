package kr.ac.jbnu.se.tetris.Controller;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextField;

import kr.ac.jbnu.se.tetris.Model.SoundModel;
import kr.ac.jbnu.se.tetris.Model.IntroModel;
import kr.ac.jbnu.se.tetris.View.IntroView;
import kr.ac.jbnu.se.tetris.MenuState;

public class IntroController implements MouseListener, MouseMotionListener {
    /***************************************************************/
    // 멤버 변수
    IntroModel introModel;
    IntroView introView;
    JFrame frame;
    JTextField nameField;
    Container contentPane;

    /*SingleModel singleModel;
    SingleView singleView;
    SingleController singleController;
    MultiModel multiModel;
    MultiView multiView;*/
    Socket socket;
    PrintWriter writer;
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
        /*singleModel = new SingleModel(soundModel);
        singleView = new SingleView(singleModel);
        singleController = new SingleController(singleModel, singleView, frame, contentPane, soundModel);*/
        name = "ClientA";
        this.soundModel = soundModel;
        /*multiModel = new MultiModel(soundModel);
        multiView = new MultiView(multiModel, name);*/
        /*try {
            socket = new Socket("192.168.0.9", 8050);
            writer = new PrintWriter(socket.getOutputStream());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }*/
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
            if(introModel.getCheckClicked() > 0) {
                if(isWithinBounds(x, y, introModel.getSelect_gameX(), introModel.getSelect_gameY(), introModel.getSelect_gameWidth(), introModel.getSelect_gameHeight())) {
                     /*Thread singleThread = new Thread(singleController);
                    singleThread.start();
                    soundModel.intoBgmStop();
                    soundModel.menuClickPlay();
                    contentPane.remove(introView);
                    contentPane.add(singleView);
                    frame.addKeyListener(singleController);*/
                    frame.setSize(1200 + 15, 901 + 35);
                    frame.setLocation(200, 0);
                    frame.setVisible(true);
                    return;
                } else if(isWithinBounds(x, y, introModel.getSelect_gameX(), introModel.getSelect_gameY() + introModel.getSelect_gameHeight() + introModel.getSelect_gameInterval(), introModel.getSelect_gameWidth(), introModel.getSelect_gameHeight())) {
                    introModel.setCheckMulti(1);
                    introView.repaint();
                    introView.add(nameField);
                    nameField.setLocation(240, 600);
                    nameField.setSize(330, 60);
                    nameField.setFont(new Font("Courier", Font.BOLD, 22));
                    introView.removeMouseMotionListener(this);
                    return;
                }
                /*else if((x >= 300 && x <= 500) && (y >= 680 && y <= 730) && (introModel.getCheckClicked() == 3)) {
                Thread senderThread = new SenderThread(socket, name, multiModel, writer, multiView);
                Thread receiverThread = new ReceiverThread(socket, writer, introView, multiModel, multiView, contentPane, frame, name, soundModel);
                senderThread.start();
                receiverThread.start();
            }*/
            }

            // GAME START
            if(isWithinBounds(x, y, introModel.getMenuX(), introModel.getMenuY(), introModel.getMenuWidth(), introModel.getMenuHeight())) {
                introModel.setMenuState(MenuState.GAME_START);
                return;
            }

            // HOW TO PLAY
            if(isWithinBounds(x, y, introModel.getMenuX(), introModel.getMenuY() + introModel.getMenuHeight(), introModel.getMenuWidth(), introModel.getMenuHeight())) {
                introModel.setMenuState(MenuState.HOW_TO_PLAY);
                introView.repaint();
                return;
            }

            // RANKING
            if(isWithinBounds(x, y, introModel.getMenuX(), introModel.getMenuY() + 2*introModel.getMenuHeight(), introModel.getMenuWidth(), introModel.getMenuHeight())) {
                // ... 이전 코드
                return;
            }

            // GAME EXIT
            if(isWithinBounds(x, y, introModel.getMenuX(), introModel.getMenuY() + 3*introModel.getMenuHeight(), introModel.getMenuWidth(), introModel.getMenuHeight())) {
                System.exit(1);
                return;
            }

            introModel.setMenuState(MenuState.NONE);
        }

        // SINGLE PLAY & MULTI PLAY
        /*if(introModel.getCheckClicked() > 0) {
            if((x >= introModel.getSelect_gameX() && x <= introModel.getSelect_gameX() + introModel.getSelect_gameWidth()) && (y >= introModel.getSelect_gameY() && y <= introModel.getSelect_gameY() + introModel.getSelect_gameHeight())) {
                *//*Thread singleThread = new Thread(singleController);
                singleThread.start();
                soundModel.intoBgmStop();
                soundModel.menuClickPlay();
                contentPane.remove(introView);
                contentPane.add(singleView);
                frame.addKeyListener(singleController);*//*
                frame.setSize(1200 + 15, 901 + 35);
                frame.setLocation(200, 0);
                frame.setVisible(true);
            }
            else if((x >= introModel.getSelect_gameX() && x <= introModel.getSelect_gameX() + introModel.getSelect_gameWidth()) && (y >= introModel.getSelect_gameY() + introModel.getSelect_gameHeight() + introModel.getSelect_gameInterval() && y <= introModel.getSelect_gameY() + (introModel.getSelect_gameHeight() * 2) + introModel.getSelect_gameInterval())) {
                introModel.setCheckMulti(1);
                introView.repaint();
                introView.add(nameField);
                nameField.setLocation(240, 600);
                nameField.setSize(330, 60);
                nameField.setFont(new Font("Courier", Font.BOLD, 22));
                introView.removeMouseMotionListener(this);
            }
            *//*else if((x >= 300 && x <= 500) && (y >= 680 && y <= 730) && (introModel.getCheckClicked() == 3)) {
                Thread senderThread = new SenderThread(socket, name, multiModel, writer, multiView);
                Thread receiverThread = new ReceiverThread(socket, writer, introView, multiModel, multiView, contentPane, frame, name, soundModel);
                senderThread.start();
                receiverThread.start();
            }*//*
        }*/

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
                System.out.println(introModel.getMenuState());

            }

            else if((x >= introModel.getSelect_gameX() && x <= introModel.getSelect_gameX() + introModel.getSelect_gameWidth()) && (y >= introModel.getSelect_gameY() + introModel.getSelect_gameHeight() + introModel.getSelect_gameInterval() && y <= introModel.getSelect_gameY() + (introModel.getSelect_gameHeight() * 2) + introModel.getSelect_gameInterval()))
                introModel.setMenuState(MenuState.MULTI_PLAY);
            else {
                introModel.setMenuState(MenuState.GAME_START);

            }

        }
        /***************************************************************/
        // repaint
        introView.repaint();
    }
}