package kr.ac.jbnu.se.tetris;

import kr.ac.jbnu.se.tetris.Model.SoundModel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

// 메인 함수 클래스
public class Tetris extends JFrame implements KeyListener {
	final int executionWidth;
	final int executionHeight;
	int playerNum;

	private final boolean isMultiplayer;

	private JLabel statusbar;

	private JPanel player1Panel;
	private HoldPiecePanel holdPiecePanel;
	private LevelPanel levelPanel;
	private BestScorePanel bestScorePanel;
	private Board board1;
	private Board board2;
	private UIManager uiManager;


	public Tetris(boolean isMultiPlayer, SoundModel soundModel) {
		addKeyListener(this);
		setFocusable(true);
		this.uiManager = new UIManager();
		this.isMultiplayer = isMultiPlayer;
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
		fillerPanelS.setBackground(Color.black);

		JPanel scorePanel = new JPanel();
		scorePanel.setPreferredSize(new Dimension(isMultiplayer ? (executionWidth - 400) / 4 : (executionWidth - 400) / 2, 40));
		scorePanel.setLayout(new BorderLayout());
		scorePanel.setBackground(Color.LIGHT_GRAY);

		statusbar.setFont(new Font("", Font.PLAIN, 20));
		statusbar.setForeground(Color.BLACK);
		statusbar.setVerticalAlignment(SwingConstants.CENTER);
		statusbar.setHorizontalAlignment(SwingConstants.CENTER);
		scorePanel.add(this.statusbar, BorderLayout.CENTER);

		fillerPanelS.add(scorePanel, BorderLayout.SOUTH);
		return fillerPanelS;
	}

	private JPanel createFillerPanelW(EtchedBorder border, HoldPiecePanel holdPiecePanel) {
		this.holdPiecePanel = new HoldPiecePanel();
		JPanel fillerPanelW = new JPanel();
		fillerPanelW.setPreferredSize(new Dimension(isMultiplayer ? (executionWidth - 200) / 4 : (executionWidth - 200) / 2, 400));
		fillerPanelW.setBackground(Color.BLACK);
		fillerPanelW.setLayout(new BorderLayout());

		//Hold 블럭 칸
		JPanel holdPanel = new JPanel();
		holdPanel.setBorder(border);
		holdPanel.setPreferredSize(new Dimension(200,175)); //아래의 jPanelW(여백) 패널과 함께 크기 조절
		holdPanel.setBackground(Color.DARK_GRAY);
		holdPanel.setLayout(new BorderLayout());

		JPanel titlePanelH = new JPanel();
		titlePanelH.setPreferredSize(new Dimension(200,30));
		titlePanelH.setBackground(Color.LIGHT_GRAY);
		titlePanelH.setLayout(new BorderLayout());
		titlePanelH.setBorder(border);

		// Hold 블럭 칸에 holdPiecePanel을 추가하는 JPanel 객체 생성
		JPanel holdBlockPanel = new JPanel();
		holdBlockPanel.setBorder(border);
		holdBlockPanel.setPreferredSize(new Dimension(200, 100));  // 너비와 높이는 필요에 따라 조정
		holdBlockPanel.setBackground(Color.DARK_GRAY);
		holdBlockPanel.add(holdPiecePanel);  // holdPiecePanel을 HoldBlock에 추가

		// HoldPanel의 중앙에 holdBlockPanel 추가
		holdPanel.add(holdBlockPanel, BorderLayout.EAST); //Hold 패널 위치
		holdPanel.add(titlePanelH, BorderLayout.NORTH);

		JLabel holdTitleLabel = new JLabel("Hold");
		holdTitleLabel.setFont(new Font("",Font.PLAIN,16));
		holdTitleLabel.setForeground(Color.BLACK);
		holdTitleLabel.setVerticalAlignment(SwingConstants.CENTER);
		holdTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

		titlePanelH.add(holdTitleLabel,BorderLayout.NORTH);
		fillerPanelW.add(holdPanel, BorderLayout.EAST);

		JPanel jPanelW = new JPanel();
		jPanelW.setPreferredSize(new Dimension(100, 225)); //위의 holdPanel 패널과 함께 크기 조절
		jPanelW.setBackground(Color.BLACK);

		fillerPanelW.add(jPanelW, BorderLayout.SOUTH);
		return fillerPanelW;
	}

	private JPanel createFillerPanelE(EtchedBorder border, NextPiecePanel nextPiecePanel) {
		JPanel fillerPanelE = new JPanel();
		fillerPanelE.setPreferredSize(new Dimension(isMultiplayer ? (executionWidth - 200) / 4 : (executionWidth - 200) / 2, 200));
		fillerPanelE.setBackground(Color.black);
		fillerPanelE.setLayout(new BorderLayout());

		JPanel nextPanel = new JPanel();

		nextPanel.setPreferredSize(new Dimension(200, 200));
		nextPanel.setBackground(Color.DARK_GRAY);
		nextPanel.setLayout(new BorderLayout());

		JPanel titlePanelN = new JPanel();
		titlePanelN.setPreferredSize(new Dimension(200, 30));
		titlePanelN.setBackground(Color.LIGHT_GRAY);
		titlePanelN.setLayout(new BorderLayout());
		titlePanelN.setBorder(border);

		JPanel nextBlockPanel = new JPanel();
		nextBlockPanel.setBorder(border);
		nextBlockPanel.setPreferredSize(new Dimension(200, 100));
		nextBlockPanel.setBackground(Color.DARK_GRAY);
		nextBlockPanel.add(nextPiecePanel);

		nextPanel.add(nextBlockPanel, BorderLayout.CENTER);
		nextPanel.add(titlePanelN, BorderLayout.NORTH);

		JLabel nextTitleLabel = new JLabel("Next");
		nextTitleLabel.setFont(new Font("", Font.PLAIN, 16));
		nextTitleLabel.setForeground(Color.BLACK);
		nextTitleLabel.setVerticalAlignment(SwingConstants.CENTER);
		nextTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titlePanelN.add(nextTitleLabel);

		fillerPanelE.add(nextPanel, BorderLayout.WEST);

		//Next 패널 아래(Level, TotalScore)
		JPanel underNextPanel = new JPanel();
		underNextPanel.setPreferredSize(new Dimension(100, 100));
		underNextPanel.setBackground(Color.BLACK);

		// Level 패널
		JPanel levelPanelLocal = new JPanel();
		levelPanelLocal.setPreferredSize(new Dimension(200, 50));
		levelPanelLocal.setBackground(Color.WHITE);
		levelPanelLocal.setLayout(new BorderLayout());
		underNextPanel.add(this.levelPanel, BorderLayout.SOUTH);

		// Best Score 패널
		JPanel bestScorePanelLocal = new JPanel();
		bestScorePanelLocal.setPreferredSize(new Dimension(200, 50));
		bestScorePanelLocal.setBackground(Color.DARK_GRAY);
		bestScorePanelLocal.setBorder(border);

		JLabel bestScoreLabel = new JLabel("Best Score");
		bestScoreLabel.setFont(new Font("", Font.BOLD, 24));
		bestScoreLabel.setForeground(Color.GRAY);

		bestScorePanel.add(bestScoreLabel, BorderLayout.WEST);
		underNextPanel.add(this.bestScorePanel, BorderLayout.CENTER);

		nextPanel.add(underNextPanel, BorderLayout.SOUTH);
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
		JPanel player2Panel = createPlayerPanel(2);


		JPanel multiplayerPanel = new JPanel(new GridLayout(1, 2));
		multiplayerPanel.add(player1Panel);
		multiplayerPanel.add(player2Panel);

		add(multiplayerPanel, BorderLayout.CENTER);
	}

	private JPanel createPlayerPanel(int playerNum) {
		JPanel playerPanel = new JPanel(new BorderLayout());

		// components initialization
		NextPiecePanel nextPiecePanel = new NextPiecePanel();
		this.holdPiecePanel = new HoldPiecePanel();
		this.levelPanel = new LevelPanel();
		this.bestScorePanel = new BestScorePanel();

		// Board
		Board board = new Board(nextPiecePanel, holdPiecePanel, levelPanel, bestScorePanel, uiManager, null);
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
		containerPanel.add(createFillerPanelE(border, nextPiecePanel), BorderLayout.EAST);
		containerPanel.add(createFillerPanelW(border, holdPiecePanel), BorderLayout.WEST);
		containerPanel.add(createFillerPanelS(uiManager.getStatusbar()), BorderLayout.SOUTH);

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
        return switch (keycode) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_J -> "left";
            case KeyEvent.VK_RIGHT, KeyEvent.VK_L -> "right";
            case KeyEvent.VK_DOWN, KeyEvent.VK_U -> "oneLineDown";
            case KeyEvent.VK_UP, KeyEvent.VK_K -> "rotateLeft";
            case KeyEvent.VK_SPACE, KeyEvent.VK_I -> "dropDown";
            case 'd', 'D' -> "hold";
            case KeyEvent.VK_R -> "re";
            default -> null;
        };
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