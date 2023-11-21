package kr.ac.jbnu.se.tetris.Controller;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import kr.ac.jbnu.se.tetris.Model.SoundModel;

public class MainController {
    /***************************************************************/
    // 객체 초기화
    private final JFrame frame;
    public final Container contentPane;
    public final IntroController introController;
    private final SoundModel soundModel;
    /***************************************************************/
    // MainController 생성자
    public MainController() {
        this.frame = new JFrame("TETRIS GAME");
        this.contentPane = frame.getContentPane();
        this.soundModel = new SoundModel();
        this.introController = new IntroController(frame, contentPane, soundModel);
    }
    /***************************************************************/
    // run 메소드: 게임 실행
    public void run() {
        soundModel.introBgmPlay();
        frame.setPreferredSize(new Dimension(800, 800));
        frame.setLocation(200,100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}