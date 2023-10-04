package kr.ac.jbnu.se.tetris;

import java.awt.*;

import javax.swing.*;

// 메인 함수 클래스
public class Tetris extends JFrame {
	final int executionWidth = 800;
	final int executionHeight = 800;

	ImageIcon hold_img = new ImageIcon("src/image/hold.png");
	JLabel hold = new JLabel(hold_img);

	JLabel statusbar;

	public Tetris() {
		statusbar = new JLabel(" 0");
		add(statusbar, BorderLayout.SOUTH);
		hold.setBounds(0, 0, 220, 230);

		Board board = new Board(this);
		board.setPreferredSize(new Dimension(300, 600)); // 게임 보드의 크기 설정

		JPanel containerPanel = new JPanel(new BorderLayout());
		containerPanel.setPreferredSize(new Dimension(executionWidth, executionHeight));
		containerPanel.add(board, BorderLayout.CENTER);

		// 나머지 여백 패널 생성
		JPanel fillerPanelN = new JPanel();
		fillerPanelN.setPreferredSize(new Dimension(executionWidth, (executionHeight - 400) / 2));
		fillerPanelN.setBackground(Color.black);
		JPanel fillerPanelE = new JPanel();
		fillerPanelE.setPreferredSize(new Dimension((executionWidth - 200) / 2, 400));
		fillerPanelE.setBackground(Color.black);
		JPanel fillerPanelW = new JPanel();
		fillerPanelW.add(hold);
		fillerPanelW.setPreferredSize(new Dimension((executionWidth - 200) / 2, 400));
		fillerPanelW.setBackground(Color.black);
		JPanel fillerPanelS = new JPanel();
		fillerPanelS.setPreferredSize(new Dimension(executionWidth, (executionHeight - 400) / 2));
		fillerPanelS.setBackground(Color.black );

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