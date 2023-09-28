package kr.ac.jbnu.se.tetris.Controller;

import kr.ac.jbnu.se.tetris.Model.SingleModel;
import kr.ac.jbnu.se.tetris.Model.SoundModel;
import kr.ac.jbnu.se.tetris.View.SingleView;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class SingleController implements Runnable, KeyListener {
    /***************************************************************/
    // ����ʵ�
    SingleModel singleModel;
    SingleView singleView;
    JFrame frame;
    Container contentPane;
    SoundModel soundModel;
    /***************************************************************/
    // SingleController ������
    public SingleController(SingleModel singleModel, SingleView singleView, JFrame frame, Container contentPane, SoundModel soundModel) {
        this.singleModel = singleModel;
        this.singleView = singleView;
        this.frame = frame;
        this.contentPane = contentPane;
        this.soundModel = soundModel;
        singleModel.setRandomBlock();  // ����, ���� ���� �������� ����
    }
    /***************************************************************/
    // KeyListener �������̵�
    @Override
    public void keyPressed(KeyEvent e) {
        if(singleModel.isMove(singleModel.getNowCol(), singleModel.getNowRow(), singleModel.getNowBlock(), singleModel.getTurnNum(), e.getKeyCode())) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    singleModel.setTurnNum(singleModel.getTurnNum() + 1);
                    soundModel.moveBlockPlay();
                    break;
                case KeyEvent.VK_LEFT:
                    singleModel.setNowBlockX(singleModel.getNowBlockX() - singleModel.getBlockSize());
                    singleModel.setNowRow(singleModel.getNowRow() - 1);
                    break;
                case KeyEvent.VK_RIGHT:
                    singleModel.setNowBlockX(singleModel.getNowBlockX() + singleModel.getBlockSize());
                    singleModel.setNowRow(singleModel.getNowRow() + 1);
                    break;
                case KeyEvent.VK_DOWN:
                    singleModel.setNowBlockY(singleModel.getNowBlockY() + singleModel.getBlockSize());
                    singleModel.setNowCol(singleModel.getNowCol() + 1);
                    break;
                case KeyEvent.VK_SPACE:
                    singleModel.setCheckSpace(1);
                    soundModel.menuClickPlay();
                    break;
            }
            singleView.repaint();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void run() {
        while(!singleModel.getIsOver()) {
            try {
                if(singleModel.getCheckSpace() == 0)
                    Thread.sleep(1000 - (singleModel.getGameStage() * 100));
                else
                    Thread.sleep(10);
                System.out.println("nowBlock : " + singleModel.getNowBlock() + ", turnNum : " + singleModel.getTurnNum() + ", nowCol : " + singleModel.getNowCol() + ", nowRow : " + singleModel.getNowRow());
                singleModel.checkBlockLast(singleModel.getNowBlock(), singleModel.getTurnNum());
                singleView.repaint();
            } catch(InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        soundModel.losePlay();
        int yes = JOptionPane.showConfirmDialog(frame, "Game Over! ������ �����մϴ�.", "�й�!",
                JOptionPane.YES_OPTION);
        if (yes == JOptionPane.YES_OPTION)
            System.exit(1);
    }
}