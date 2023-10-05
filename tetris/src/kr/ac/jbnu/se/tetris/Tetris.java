package kr.ac.jbnu.se.tetris;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

// 메인 함수 클래스
public class Tetris extends JFrame {
	final int executionWidth = 800;
	final int executionHeight = 800;

	ImageIcon hold_img = new ImageIcon("src/image/hold.png");
	JLabel hold = new JLabel(hold_img);

	JLabel statusbar;

	public Tetris() {
		statusbar = new JLabel(" 0");

		hold.setBounds(0, 0, 220, 230);

		Board board = new Board(this);
		board.setPreferredSize(new Dimension(300, 600)); // 게임 보드의 크기 설정

		EtchedBorder border = new EtchedBorder();

		JPanel containerPanel = new JPanel(new BorderLayout());
		containerPanel.setPreferredSize(new Dimension(executionWidth, executionHeight));
		containerPanel.add(board, BorderLayout.CENTER);

		//위쪽 패널 생성
		JPanel fillerPanelN = new JPanel();
		fillerPanelN.setPreferredSize(new Dimension(executionWidth, (executionHeight - 400) / 2));
		fillerPanelN.setBackground(Color.black);

		//오른쪽 패널 생성
		JPanel fillerPanelE = new JPanel();
		fillerPanelE.setPreferredSize(new Dimension((executionWidth - 200) / 2, 400));
		fillerPanelE.setBackground(Color.black);
		fillerPanelE.setLayout(new BorderLayout());

		JPanel NextPanel = new JPanel();
		NextPanel.setBorder(border);
		NextPanel.setPreferredSize(new Dimension(200,300));
		NextPanel.setBackground(Color.DARK_GRAY);
		NextPanel.setLayout(new BorderLayout());

		JPanel TitlePanelN = new JPanel();
		TitlePanelN.setPreferredSize(new Dimension(200, 30));
		TitlePanelN.setBackground(Color.LIGHT_GRAY);
		TitlePanelN.setLayout(new BorderLayout());
		TitlePanelN.setLayout(new BorderLayout());
		TitlePanelN.setBorder(border);
		NextPanel.add(TitlePanelN, BorderLayout.NORTH);

		JLabel NextTitle = new JLabel("Next");
		NextTitle.setFont(new Font("",Font.PLAIN, 16));
		NextTitle.setForeground(Color.BLACK);
		NextTitle.setVerticalAlignment(JLabel.CENTER);
		NextTitle.setHorizontalAlignment(JLabel.CENTER);
		TitlePanelN.add(NextTitle);

		fillerPanelE.add(NextPanel, BorderLayout.WEST);

		//Next 패널 밑 여백
		JPanel jPanelE = new JPanel();
		jPanelE.setPreferredSize(new Dimension(100,60));
		jPanelE.setBackground(Color.BLACK);

		fillerPanelE.add(jPanelE, BorderLayout.SOUTH);

		//왼쪽 패널 생성
		JPanel fillerPanelW = new JPanel();
		fillerPanelW.add(hold);
		fillerPanelW.setPreferredSize(new Dimension((executionWidth - 200) / 2, 400));
		fillerPanelW.setBackground(Color.black);

		//Hold 블럭 칸
		JPanel HoldPanel = new JPanel();
		HoldPanel.setBackground(Color.DARK_GRAY);
		HoldPanel.setPreferredSize(new Dimension((executionWidth - 200) / 4,130));
		HoldPanel.setLayout(new BorderLayout());
		HoldPanel.setBorder(border);

		JPanel TitlePanelH = new JPanel();
		TitlePanelH.setPreferredSize(new Dimension(100,30));
		TitlePanelH.setBackground(Color.LIGHT_GRAY);
		TitlePanelH.setLayout(new BorderLayout());
		TitlePanelH.setBorder(border);

		JLabel HoldTitle = new JLabel("Hold");
		HoldTitle.setFont(new Font("",Font.PLAIN,16));
		HoldTitle.setForeground(Color.BLACK);
		HoldTitle.setVerticalAlignment(JLabel.CENTER);
		HoldTitle.setHorizontalAlignment(JLabel.CENTER);

		TitlePanelH.add(HoldTitle,BorderLayout.NORTH);
		HoldPanel.add(TitlePanelH, BorderLayout.NORTH);
		fillerPanelW.add(HoldPanel, BorderLayout.EAST);

		//아래쪽 패널 생성
		JPanel fillerPanelS = new JPanel();
		fillerPanelS.setPreferredSize(new Dimension(executionWidth, (executionHeight - 400) / 2));
		fillerPanelS.setBackground(Color.black );

		JPanel Score = new JPanel();
		Score.setPreferredSize(new Dimension((executionWidth - 400) / 2, 40));
		Score.setLayout(new BorderLayout());
		Score.setBackground(Color.LIGHT_GRAY);

		statusbar.setFont(new Font("",Font.PLAIN,20));
		statusbar.setForeground(Color.BLACK);
		statusbar.setVerticalAlignment(JLabel.CENTER);
		statusbar.setHorizontalAlignment(JLabel.CENTER);
		Score.add(statusbar, BorderLayout.CENTER);

		fillerPanelS.add(Score, BorderLayout.SOUTH);

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