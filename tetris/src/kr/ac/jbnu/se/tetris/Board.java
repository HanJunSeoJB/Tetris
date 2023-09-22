package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	boolean isStarted = false; // 게임시작 여부 변수
	boolean isPaused = false; // 마찬가지
	JLabel statusbar; // 점수바
	Tetrominoes[] board; // 보드 객체 초기화

	//* 필요한 클래스 초기화
	private final TimerManager timerManager;
	private final UIManager uiManager;
	private final GameLogicManager gameLogicManager;
	private final RenderingManager renderingManager;
	private final EventManager eventManager;
	ConfigurationManager configManager = new ConfigurationManager();
	//*
	final int BoardWidth = configManager.getBoardWidth(); // 보드 넓이 초기화
	final int BoardHeight = configManager.getBoardHeight(); // 보드 높이 초기화
	final int delay = configManager.getDelay(); // 게임 시작 딜레이 변수 초기화

	//* GameLogicManager class에서 사용하기 위해 getter 생성
	public TimerManager getTimerManager() {
		return timerManager;
	}

	public Tetrominoes[] getBoardArray() {
		return board;
	}

	public UIManager getUIManager() {
		return this.uiManager;
	}

	public int getBoardWidth() {
		return BoardWidth;
	}

	public int getBoardHeight() {
		return BoardHeight;
	}
	//*


	public Board(Tetris parent) {

		//* 객체 초기화
		this.timerManager = new TimerManager(this, delay);
		this.uiManager = new UIManager(parent.getStatusBar());
		this.gameLogicManager = new GameLogicManager(this);
		this.renderingManager = new RenderingManager(this);
		this.eventManager = new EventManager(this.gameLogicManager);
		//*

		//* 게임시작
		setFocusable(true);
		timerManager.startTimer();
		//*

		//상태바 초기화
		statusbar = parent.getStatusBar();
		//보드 초기화
		board = new Tetrominoes[BoardWidth * BoardHeight];
		//키 입력 이벤트 초기화
		addKeyListener(new TAdapter(this));
		//보드 클리어
		clearBoard();
	}

	// 블럭이 바닥에 닿았을 때 처리 함수
	public void actionPerformed(ActionEvent e) {
		if (gameLogicManager.isFallingFinished()) {
			gameLogicManager.setFallingFinished(false);
			gameLogicManager.newPiece();
		} else {
			gameLogicManager.oneLineDown();
		}
	}

	// 사각형 넓이
	int squareWidth() {
		return (int) getSize().getWidth() / BoardWidth;
	}

	//사각형 높이
	int squareHeight() {
		return (int) getSize().getHeight() / BoardHeight;
	}

	public void startTimer() {
		timerManager.startTimer();
	}

	// 게임 시작 함수 ( 정지 된 상태에서  p를 눌렀을 때 시작처리)
	public void start() {
		if (gameLogicManager.isPaused())
			return;

		gameLogicManager.setStarted(true);
		gameLogicManager.setFallingFinished(false);
		uiManager.updateStatusbar(String.valueOf(gameLogicManager.getNumLinesRemoved()));
		clearBoard();

		gameLogicManager.newPiece();
		timerManager.startTimer();
	}
	// 보드에 그리는 함수
	public void paint(Graphics g) {
		super.paint(g);
		Shape curPiece = gameLogicManager.getCurPiece();
		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();

		for (int i = 0; i < BoardHeight; ++i) {
			for (int j = 0; j < BoardWidth; ++j) {
				Tetrominoes shape = gameLogicManager.shapeAt(j, BoardHeight - i - 1);
				if (shape != Tetrominoes.NoShape)
					renderingManager.drawSquare(g, 0 + j * squareWidth(), boardTop + i * squareHeight(), shape);
			}
		}

		if (curPiece.getShape() != Tetrominoes.NoShape) {
			for (int i = 0; i < 4; ++i) {
				int x = gameLogicManager.getCurX() + curPiece.x(i);
				int y = gameLogicManager.getCurY() - curPiece.y(i);
				renderingManager.drawSquare(g, 0 + x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(),
						curPiece.getShape());
			}
		}
	}

	// 보드 초기화 함수
	private void clearBoard() {
		for (int i = 0; i < BoardHeight * BoardWidth; ++i)
			board[i] = Tetrominoes.NoShape;
	}

	// 게임 멈춤 여부 확인 함수
	public boolean isPaused() {
		return gameLogicManager.isPaused();
	}

	// 키 이벤트 처리함수
	public void handleKeyAction(String action) {
		eventManager.handleKeyAction(action);
	}
}