package kr.ac.jbnu.se.tetris;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 타이머 관리 클래스
public class TimerManager {

    private Timer timer;
    private Board board;
    private int delay;

    public TimerManager(Board board, int delay) {
        this.board = board;
        this.delay = delay;
        initTimer();
    }

    private void initTimer() {
        timer = new Timer(delay, new TimerActionListener());
        timer.start();
    }

    public void startTimer() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public void stopTimer() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
        if (timer.isRunning()) {
            timer.stop();
        }
        timer.setDelay(delay);
        timer.start();
    }

    private class TimerActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            board.actionPerformed(e);
        }
    }
}
