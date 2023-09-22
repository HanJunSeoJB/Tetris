package kr.ac.jbnu.se.tetris.Controller;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import kr.ac.jbnu.se.tetris.Model.IntroModel;
import kr.ac.jbnu.se.tetris.Model.SoundModel;
import kr.ac.jbnu.se.tetris.View.IntroView;

public class MainController {
    /***************************************************************/
    // �������
    IntroModel introModel;
    IntroView introView;
    JFrame frame;
    Container contentPane;
    IntroController introController;
    SoundModel soundModel;
    /***************************************************************/
    // MainController ������
    public MainController() {
        introModel = new IntroModel();
        introView = new IntroView(introModel);
        frame = new JFrame("TETRIS GAME");
        contentPane = frame.getContentPane();
        soundModel = new SoundModel();
        introController = new IntroController(introModel, introView, frame, contentPane, soundModel);
    }
    /***************************************************************/
    // run �޼ҵ� : ���� ����
    public void run() {
        contentPane.add(introView);
        soundModel.introBgmPlay();
        introView.addMouseListener(introController);
        introView.addMouseMotionListener(introController);
        frame.setPreferredSize(new Dimension(800, 800));
        frame.setLocation(200,100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}