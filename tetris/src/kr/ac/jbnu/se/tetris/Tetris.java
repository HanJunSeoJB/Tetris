package kr.ac.jbnu.se.tetris;

import kr.ac.jbnu.se.tetris.Model.SoundModel;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

// 메인 함수 클래스
public class Tetris extends JFrame {
	final int executionWidth;
	final int executionHeight;

	private boolean isMultiplayer;


	JLabel statusbar;

	private JPanel player1Panel;
	private JPanel player2Panel;
	NextPiecePanel nextPiecePanel;
	HoldPiecePanel holdPiecePanel;

	SoundModel soundModel = new SoundModel();

	public Tetris(boolean isMultiPlayer) {
		this.isMultiplayer = isMultiPlayer;
		// 멀티플레이 모드인 경우 화면의 너비를 두 배로 설정합니다.
		this.executionWidth = isMultiplayer ? 1600 : 800;
		this.executionHeight = 800;

		if (isMultiplayer) {
			multiPlay();
		} else {
			singlePlay();
		}
		pack();
		setTitle("Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		soundModel.playBgm();
	}

	private JPanel createFillerPanelS(JLabel statusbar) {
		this.statusbar = statusbar;
		JPanel fillerPanelS = new JPanel();
		fillerPanelS.setPreferredSize(new Dimension(isMultiplayer ? executionWidth / 2 : executionWidth, (executionHeight - 400) / 2));
		fillerPanelS.setBackground(Color.black );

		JPanel Score = new JPanel();
		Score.setPreferredSize(new Dimension(isMultiplayer ? (executionWidth - 400) / 4 : (executionWidth - 400) / 2, 40));
		Score.setLayout(new BorderLayout());
		Score.setBackground(Color.LIGHT_GRAY);

		statusbar.setFont(new Font("",Font.PLAIN,20));
		statusbar.setForeground(Color.BLACK);
		statusbar.setVerticalAlignment(JLabel.CENTER);
		statusbar.setHorizontalAlignment(JLabel.CENTER);
		Score.add(statusbar, BorderLayout.CENTER);

		fillerPanelS.add(Score, BorderLayout.SOUTH);
		return fillerPanelS;
	}

	private JPanel createFillerPanelW(EtchedBorder border, HoldPiecePanel holdPiecePanel) {
		this.holdPiecePanel = holdPiecePanel;
		JPanel fillerPanelW = new JPanel();
		fillerPanelW.setPreferredSize(new Dimension(isMultiplayer ? (executionWidth - 200) / 4 : (executionWidth - 200) / 2, 400));
		fillerPanelW.setBackground(Color.black);
		fillerPanelW.setLayout(new BorderLayout());

		//Hold 블럭 칸
		JPanel HoldPanel = new JPanel();
		HoldPanel.setBorder(border);
		HoldPanel.setPreferredSize(new Dimension(200,175)); //아래의 jPanelW(여백) 패널과 함께 크기 조절
		HoldPanel.setBackground(Color.DARK_GRAY);
		HoldPanel.setLayout(new BorderLayout());

		JPanel TitlePanelH = new JPanel();
		TitlePanelH.setPreferredSize(new Dimension(200,30));
		TitlePanelH.setBackground(Color.LIGHT_GRAY);
		TitlePanelH.setLayout(new BorderLayout());
		TitlePanelH.setBorder(border);

		// Hold 블럭 칸에 holdPiecePanel을 추가하는 JPanel 객체 생성
		JPanel HoldBlock = new JPanel();
		HoldBlock.setBorder(border);
		HoldBlock.setPreferredSize(new Dimension(200, 100));  // 너비와 높이는 필요에 따라 조정
		HoldBlock.setBackground(Color.DARK_GRAY);
		HoldBlock.add(holdPiecePanel);  // holdPiecePanel을 HoldBlock에 추가

		// HoldPanel의 중앙에 HoldBlock 추가
		HoldPanel.add(HoldBlock, BorderLayout.EAST); //Hold 패널 위치
		HoldPanel.add(TitlePanelH, BorderLayout.NORTH);

		JLabel HoldTitle = new JLabel("Hold");
		HoldTitle.setFont(new Font("",Font.PLAIN,16));
		HoldTitle.setForeground(Color.BLACK);
		HoldTitle.setVerticalAlignment(JLabel.CENTER);
		HoldTitle.setHorizontalAlignment(JLabel.CENTER);

		TitlePanelH.add(HoldTitle,BorderLayout.NORTH);
		fillerPanelW.add(HoldPanel, BorderLayout.EAST);

		JPanel jPanelW = new JPanel();
		jPanelW.setPreferredSize(new Dimension(100, 225)); //위의 HoldPanel 패널과 함께 크기 조절
		jPanelW.setBackground(Color.BLACK);

		fillerPanelW.add(jPanelW, BorderLayout.SOUTH);
		return fillerPanelW;
	}

	private JPanel createFillerPanelE(EtchedBorder border, NextPiecePanel nextPiecePanel) {
		this.nextPiecePanel = nextPiecePanel;
		JPanel fillerPanelE = new JPanel();
		fillerPanelE.setPreferredSize(new Dimension(isMultiplayer ? (executionWidth - 200) / 4 : (executionWidth - 200) / 2, 400));
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
		TitlePanelN.setBorder(border);

		JPanel NextBlock = new JPanel();
		NextBlock.setBorder(border);
		NextBlock.setPreferredSize(new Dimension(200,100));
		NextBlock.setBackground(Color.DARK_GRAY);
		NextBlock.add(nextPiecePanel);

		NextPanel.add(NextBlock, BorderLayout.CENTER);
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
		return fillerPanelE;
	}

	private JPanel createFillerPanelN() {
		JPanel fillerPanelN = new JPanel();
		fillerPanelN.setPreferredSize(new Dimension(isMultiplayer ? executionWidth / 2 : executionWidth, (executionHeight - 400) / 2));
		fillerPanelN.setBackground(Color.black);
		return fillerPanelN;
	}

	public void singlePlay() {
		player1Panel = createPlayerPanel(1);
		add(player1Panel, BorderLayout.CENTER);
	}

	public void multiPlay() {
		player1Panel = createPlayerPanel(1);
		player2Panel = createPlayerPanel(2);


		JPanel multiplayerPanel = new JPanel(new GridLayout(1, 2));
		multiplayerPanel.add(player1Panel);
		multiplayerPanel.add(player2Panel);

		add(multiplayerPanel, BorderLayout.CENTER);


	}

	private JPanel createPlayerPanel(int playerNum) {
		JPanel playerPanel = new JPanel(new BorderLayout());


		// components initialization
		JLabel statusbar = new JLabel(" 0");
		NextPiecePanel nextPiecePanel = new NextPiecePanel();
		HoldPiecePanel holdPiecePanel = new HoldPiecePanel();

		// Board
		Board board = new Board(this, nextPiecePanel, holdPiecePanel, statusbar, playerNum);
		board.setPreferredSize(new Dimension(300, 600)); // 게임 보드의 크기 설정

		// EtchedBorder
		EtchedBorder border = new EtchedBorder();

		// Panels
		JPanel containerPanel = new JPanel(new BorderLayout());
		containerPanel.setPreferredSize(new Dimension(executionWidth, executionHeight));
		containerPanel.add(board, BorderLayout.CENTER);

		containerPanel.add(createFillerPanelN(), BorderLayout.NORTH);
		containerPanel.add(createFillerPanelE(border, nextPiecePanel), BorderLayout.EAST);
		containerPanel.add(createFillerPanelW(border, holdPiecePanel), BorderLayout.WEST);
		containerPanel.add(createFillerPanelS(statusbar), BorderLayout.SOUTH);

		playerPanel.add(containerPanel, BorderLayout.CENTER);

		board.start();



		return playerPanel;
	}


	public JLabel getStatusBar() {
		return statusbar;
	}

	public static void main(String[] args) {
		Tetris game = new Tetris(false);
		game.setVisible(true);
	}
}