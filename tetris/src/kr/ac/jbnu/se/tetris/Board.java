package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {
	Tetrominoes[] board; // 보드 객체 초기화

	//* 필요한 클래스 초기화
	private final transient GameLogicManager gameLogicManager;
	private final transient RenderingManager renderingManager;
	private final transient EventManager eventManager;

	ConfigurationManager configManager = new ConfigurationManager();
	BestScorePanel bestScorePanel;
	//*
	final int BoardWidth = configManager.getBoardWidth() * 12 / 10; // 보드 넓이 초기화
	final int BoardHeight = configManager.getBoardHeight() * 12 / 10; // 보드 높이 초기화

	//* GameLogicManager class에서 사용하기 위해 getter 생성

	public Tetrominoes[] getBoardArray() {
		return board;
	}

	public int getBoardWidth() {
		return BoardWidth;
	}

	public int getBoardHeight() {
		return BoardHeight;
	}
	//*


	public Board(NextPiecePanel nextPiecePanel, HoldPiecePanel holdPiecePanel, LevelPanel levelPanel, BestScorePanel bestScorePanel, UIManager uiManager, ScoreManager scoreManager) {

		//* 객체 초기화
		this.gameLogicManager = new GameLogicManager(this, nextPiecePanel, holdPiecePanel, levelPanel, scoreManager, uiManager);
		this.renderingManager = new RenderingManager(this);
		this.eventManager = new EventManager(this.gameLogicManager);
		this.bestScorePanel = bestScorePanel;
		//*

		//* 게임시작
		setFocusable(true);
		gameLogicManager.setStarted(true);
		//*
		//보드 초기화
		board = new Tetrominoes[BoardWidth * BoardHeight];
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

	// 게임 시작 함수 ( 정지 된 상태에서  p를 눌렀을 때 시작처리)
	public void start() {
		if (gameLogicManager.isPaused())
			return;

		gameLogicManager.setStarted(true);
		gameLogicManager.setFallingFinished(false);
		clearBoard();

		gameLogicManager.newPiece();
		bestScorePanel.updateBestScore(ScoreManager.loadBestScore());
		gameLogicManager.startTimer();
	}

	public void reStart() {
		clearBoard();
		start();
		repaint();
	}


	// 보드에 그리는 함수

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();

		//g.clearRect(0, 0, getWidth(), getHeight());

		// 보드판 색상 결정
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, size.width, size.height);
		Color lineColor = new Color(169, 169, 169, 50);
		g.setColor(lineColor);

		// 보드판 실선
		for(int i = 0; i < this.BoardHeight; ++i) {
			int Top = boardTop + i * this.squareHeight();
			g.fillRect(0, Top, size.width, 1);
		}

		for(int i = 0; i < this.BoardWidth + 1; ++i) {
			int x = i * this.squareWidth();
			g.fillRect(x, boardTop, 1, size.height - boardTop);
		}

		// 블록들을 그립니다.
		Shape curPiece = gameLogicManager.getCurPiece();
		for (int i = 0; i < BoardHeight; ++i) {
			for (int j = 0; j < BoardWidth; ++j) {
				Tetrominoes shape = gameLogicManager.shapeAt(j, BoardHeight - i - 1);
				if (shape != Tetrominoes.NO_SHAPE)
					renderingManager.drawSquare(g, j * squareWidth(), boardTop + i * squareHeight(), shape);
			}
		}

		if (curPiece.getPieceShape() != Tetrominoes.NO_SHAPE) {
			for (int i = 0; i < 4; ++i) {
				int x = gameLogicManager.getCurX() + curPiece.x(i);
				int y = gameLogicManager.getCurY() - curPiece.y(i);
				renderingManager.drawSquare(g, x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(),
						curPiece.getPieceShape());
				renderGhostPiece(g);
			}
		}

	}

	private void renderGhostPiece(Graphics g) {
		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();
		Shape ghostPiece = gameLogicManager.getCurPiece();  // 현재 조각을 복사하여 고스트 블록 생성
		int ghostY = gameLogicManager.getCurY();

		// 고스트 블록이 이동할 수 없을 때까지 아래로 이동
		while (gameLogicManager.ghostTryMove(ghostPiece, gameLogicManager.getCurX(), ghostY - 1)) {
			ghostY--;
		}

		// 고스트 블록 그리기 (반투명하게 그리는 로직이 필요합니다.)
		for (int i = 0; i < 4; ++i) {
			int x = gameLogicManager.getCurX() + ghostPiece.x(i);
			int y = ghostY - ghostPiece.y(i);
			renderingManager.drawGhostSquare(g, x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight());
		}
	}

	// 보드 초기화 함수
	public void clearBoard() {
		for (int i = 0; i < BoardHeight * BoardWidth; ++i)
			board[i] = Tetrominoes.NO_SHAPE;
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
