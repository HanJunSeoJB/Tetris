package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;

public class LevelPanel extends JPanel {
    private JLabel levelLabel;  // 레벨을 표시하는 라벨

    public LevelPanel() {
        setPreferredSize(new Dimension(200, 50));
        setBackground(Color.DARK_GRAY);
        setLayout(new FlowLayout());  // 레이아웃 관리자를 FlowLayout으로 변경

        // 레벨 제목 라벨
        JLabel levelTitle = new JLabel("Level:");
        levelTitle.setFont(new Font("", Font.BOLD, 24));
        levelTitle.setForeground(Color.WHITE);
        add(levelTitle);  // levelTitle 라벨을 LevelPanel에 직접 추가

        // 레벨 라벨 초기화
        levelLabel = new JLabel("1");  // 게임이 레벨 1에서 시작한다고 가정
        levelLabel.setFont(new Font("", Font.BOLD, 24));
        levelLabel.setForeground(Color.WHITE);
        add(levelLabel);  // levelLabel 라벨을 LevelPanel에 직접 추가
    }

    // 레벨 업데이트 메소드
    public void updateLevel(int newLevel) {
        levelLabel.setText(String.valueOf(newLevel));
    }
}
