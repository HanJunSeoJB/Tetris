package kr.ac.jbnu.se.tetris;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

// 메인 함수 클래스
public class Tetris extends JFrame {

	final int gameWidth = 200;
	final int gameHeight = 400;

	JLabel statusbar;

	public Tetris() {

		statusbar = new JLabel(" 0");
		add(statusbar, BorderLayout.SOUTH);
		Board board = new Board(this);
		add(board);
		board.start();

		setSize(gameWidth, gameHeight);
		setTitle("Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public JLabel getStatusBar() {
		return statusbar;
	}

	public static void main(String[] args) {
		Tetris game = new Tetris();
		game.setLocationRelativeTo(null);
		game.setVisible(true);
	}
}