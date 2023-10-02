package kr.ac.jbnu.se.tetris;

import java.awt.*;

import javax.swing.*;

// 메인 함수 클래스
public class Tetris extends JFrame {
	final int executionWidth = 800;
	final int executionHeight = 800;

	JLabel statusbar;

	public Tetris() {
		statusbar = new JLabel(" 0");
		add(statusbar, BorderLayout.SOUTH);

		Board board = new Board(this);
		board.setPreferredSize(new Dimension(200, 400)); // 게임 보드의 크기 설정

		JPanel containerPanel = new JPanel(new BorderLayout());
		containerPanel.setPreferredSize(new Dimension(executionWidth, executionHeight));
		containerPanel.add(board, BorderLayout.CENTER);

		// 나머지 여백 패널 생성
		JPanel fillerPanelN = new JPanel();
		fillerPanelN.setPreferredSize(new Dimension(executionWidth, (executionHeight - 400) / 2));
		JPanel fillerPanelE = new JPanel();
		fillerPanelE.setPreferredSize(new Dimension((executionWidth - 200) / 2, 400));
		JPanel fillerPanelW = new JPanel();
		fillerPanelW.setPreferredSize(new Dimension((executionWidth - 200) / 2, 400));
		JPanel fillerPanelS = new JPanel();
		fillerPanelS.setPreferredSize(new Dimension(executionWidth, (executionHeight - 400) / 2));

		containerPanel.add(fillerPanelN, BorderLayout.NORTH);
		containerPanel.add(fillerPanelE, BorderLayout.EAST);
		containerPanel.add(fillerPanelW, BorderLayout.WEST);
		containerPanel.add(fillerPanelS, BorderLayout.SOUTH);

		add(containerPanel);
		board.start();

		pack();
		setTitle("Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public JLabel getStatusBar() {
		return statusbar;
	}

	public static void main(String[] args) {
		Tetris game = new Tetris();
		game.setVisible(true);
	}
}