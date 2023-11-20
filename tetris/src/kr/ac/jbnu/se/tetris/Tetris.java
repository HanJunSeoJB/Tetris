package kr.ac.jbnu.se.tetris;

import kr.ac.jbnu.se.tetris.Model.SoundModel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

// 메인 함수 클래스
public class Tetris extends JFrame implements KeyListener {
	final int executionWidth;
	final int executionHeight;
	int playerNum;

	private boolean isMultiplayer;


	JLabel statusbar;

	private JPanel player1Panel;
	private JPanel player2Panel;
	NextPiecePanel nextPiecePanel;
	HoldPiecePanel holdPiecePanel;
	LevelPanel levelPanel;
	BestScorePanel bestScorePanel;
	Board board1;
	Board board2;


	public Tetris(boolean isMultiPlayer, SoundModel soundModel) {
		addKeyListener(this);
		setFocusable(true);
		this.isMultiplayer = isMultiPlayer;
		// 멀티플레이 모드인 경우 화면의 너비를 두 배로 설정합니다.
		this.executionWidth = isMultiplayer ? 1600 : 800;
		this.executionHeight = 800;

		if (isMultiPlayer) {
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
		fillerPanelW.setBackground(Color.BLACK);
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

	private JPanel createFillerPanelE(EtchedBorder border, NextPiecePanel nextPiecePanel, LevelPanel levelPanel, BestScorePanel bestScorePanel) {
		this.nextPiecePanel = nextPiecePanel;
		this.levelPanel = levelPanel;
		this.bestScorePanel = bestScorePanel;
		JPanel fillerPanelE = new JPanel();
		fillerPanelE.setPreferredSize(new Dimension(isMultiplayer ? (executionWidth - 200) / 4 : (executionWidth - 200) / 2, 200));
		fillerPanelE.setBackground(Color.black);
		fillerPanelE.setLayout(new BorderLayout());

		JPanel NextPanel = new JPanel();
		//NextPanel.setBorder(border);
		NextPanel.setPreferredSize(new Dimension(200,200));
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

		//Next 패널 아래(Level, TotalScore)
		JPanel UnderNextPanel = new JPanel();
		UnderNextPanel.setPreferredSize(new Dimension(100, 100));
		UnderNextPanel.setBackground(Color.BLACK);

		//Level 패널
		JPanel LevelPanel = new JPanel();
		LevelPanel.setPreferredSize(new Dimension(200, 50));
		LevelPanel.setBackground(Color.WHITE);
		LevelPanel.setLayout(new BorderLayout());
		UnderNextPanel.add(levelPanel, BorderLayout.SOUTH);

		//Best Score 패널
		JPanel BestScorePanel = new JPanel();
		BestScorePanel.setPreferredSize(new Dimension(200,50));
		BestScorePanel.setBackground(Color.DARK_GRAY);
		BestScorePanel.setBorder(border);

		JLabel BestScore = new JLabel("Best Score");
		BestScore.setFont(new Font("",Font.BOLD,24));
		BestScore.setForeground(Color.GRAY);

		BestScorePanel.add(BestScore, BorderLayout.WEST);
		UnderNextPanel.add(bestScorePanel, BorderLayout.CENTER);

		NextPanel.add(UnderNextPanel, BorderLayout.SOUTH);
		return fillerPanelE;
	}

	private JPanel createFillerPanelN() {
		JPanel fillerPanelN = new JPanel();
		fillerPanelN.setPreferredSize(new Dimension(isMultiplayer ? executionWidth / 2 : executionWidth, (executionHeight - 400) / 2));
		fillerPanelN.setBackground(Color.black);
		return fillerPanelN;
	}

	public void singlePlay() {
		playerNum = 1;
		player1Panel = createPlayerPanel(1);
		add(player1Panel, BorderLayout.CENTER);
	}

	public void multiPlay() {
		playerNum = 2;
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
		LevelPanel levelPanel = new LevelPanel();
		BestScorePanel bestScorePanel = new BestScorePanel();

		// Board
		Board board = new Board(nextPiecePanel, holdPiecePanel, levelPanel, bestScorePanel, statusbar, playerNum);
		if (playerNum == 1)
			board1 = board;
		else
			board2 = board;
		board.setPreferredSize(new Dimension(300, 600)); // 게임 보드의 크기 설정

		// EtchedBorder
		EtchedBorder border = new EtchedBorder();

		// Panels
		JPanel containerPanel = new JPanel(new BorderLayout());
		containerPanel.setPreferredSize(new Dimension(executionWidth, executionHeight));
		containerPanel.add(board, BorderLayout.CENTER);

		containerPanel.add(createFillerPanelN(), BorderLayout.NORTH);
		containerPanel.add(createFillerPanelE(border, nextPiecePanel, levelPanel, bestScorePanel), BorderLayout.EAST);
		containerPanel.add(createFillerPanelW(border, holdPiecePanel), BorderLayout.WEST);
		containerPanel.add(createFillerPanelS(statusbar), BorderLayout.SOUTH);

		playerPanel.add(containerPanel, BorderLayout.CENTER);

		board.start();



		return playerPanel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		Board board = null;

		// Check if the key event is from player 1 or player 2 based on the keycode
		if (keycode == KeyEvent.VK_LEFT || keycode == KeyEvent.VK_RIGHT ||
				keycode == KeyEvent.VK_DOWN || keycode == KeyEvent.VK_UP ||
				keycode == KeyEvent.VK_SPACE || keycode == 'd' || keycode == 'D' || keycode == KeyEvent.VK_R) {
			// The key event is from player 1
			board = board1;
		} else if (keycode == KeyEvent.VK_J || keycode == KeyEvent.VK_L || keycode == KeyEvent.VK_U ||
				keycode == KeyEvent.VK_K || keycode == KeyEvent.VK_I) {
			// The key event is from player 2
			board = board2;
		}

		if (board != null) {
			String action = convertKeycodeToAction(keycode);
			if (action != null) {
				board.handleKeyAction(action);
			}
		}
	}

	private String convertKeycodeToAction(int keycode) {
		switch (keycode) {
			case KeyEvent.VK_LEFT: return "left";
			case KeyEvent.VK_RIGHT: return "right";
			case KeyEvent.VK_DOWN: return "oneLineDown";
			case KeyEvent.VK_UP: return "rotateLeft";
			case KeyEvent.VK_SPACE: return "dropDown";
			case 'd':
			case 'D':
				return "hold";
			case KeyEvent.VK_R: return "re";
			case KeyEvent.VK_J: return "left";
			case KeyEvent.VK_L: return "right";
			case KeyEvent.VK_U: return "oneLineDown";
			case KeyEvent.VK_K: return "rotateLeft";
			case KeyEvent.VK_I: return "dropDown";
			default:
				return null;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Handle key released event if needed
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Handle key typed event if needed
	}
}