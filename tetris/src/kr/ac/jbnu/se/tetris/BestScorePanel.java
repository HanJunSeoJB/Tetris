package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;

public class BestScorePanel extends JPanel {
    private JLabel bestScoreLabel;  // 최고 점수를 표시하는 라벨

    public BestScorePanel() {
        setPreferredSize(new Dimension(200, 50));
        setBackground(Color.DARK_GRAY);
        setLayout(new FlowLayout());  // 레이아웃 관리자를 FlowLayout으로 설정

        // 최고 점수 제목 라벨
        JLabel bestScoreTitle = new JLabel("Best Score:");
        bestScoreTitle.setFont(new Font("", Font.BOLD, 24));
        bestScoreTitle.setForeground(Color.WHITE);
        add(bestScoreTitle);  // bestScoreTitle 라벨을 BestScorePanel에 직접 추가

        // 최고 점수 라벨 초기화
        bestScoreLabel = new JLabel("0");  // 최고 점수가 0으로 시작한다고 가정
        bestScoreLabel.setFont(new Font("", Font.BOLD, 24));
        bestScoreLabel.setForeground(Color.WHITE);
        add(bestScoreLabel);  // bestScoreLabel 라벨을 BestScorePanel에 직접 추가
    }

    // 최고 점수 업데이트 메소드
    public void updateBestScore(int newBestScore) {
        bestScoreLabel.setText(String.valueOf(newBestScore));
    }
}
