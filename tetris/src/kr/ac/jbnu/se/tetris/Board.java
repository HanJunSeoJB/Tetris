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

	public Tetrominoes[] getBoardArray() {
		return board;
	}

	public int getBoardWidth() {
		return BoardWidth;
	}

	public int getBoardHeight() {
		return BoardHeight;
	}

	final int BoardWidth = 10;
	final int BoardHeight = 22;

	Timer timer;
	boolean isStarted = false;
	boolean isPaused = false;
	JLabel statusbar;
	Tetrominoes[] board;

	private GameLogicManager gameLogicManager;


	public Board(Tetris parent) {

		this.gameLogicManager = new GameLogicManager(this);

		setFocusable(true);
		timer = new Timer(400, this);
		timer.start();

		statusbar = parent.getStatusBar();
		board = new Tetrominoes[BoardWidth * BoardHeight];
		addKeyListener(new TAdapter(this));
		clearBoard();
	}

	public void actionPerformed(ActionEvent e) {
		if (gameLogicManager.isFallingFinished()) {
			gameLogicManager.setFallingFinished(false);
			gameLogicManager.newPiece();
		} else {
			gameLogicManager.oneLineDown();
		}
	}

	public void stopTimer() {
		timer.stop();
	}

	int squareWidth() {
		return (int) getSize().getWidth() / BoardWidth;
	}

	int squareHeight() {
		return (int) getSize().getHeight() / BoardHeight;
	}

	Tetrominoes shapeAt(int x, int y) {
		return board[(y * BoardWidth) + x];
	}

	public void startTimer() {
		timer.start();
	}

	public void start() {
		if (gameLogicManager.isPaused())
			return;

		gameLogicManager.setStarted(true);
		gameLogicManager.setFallingFinished(false);
		gameLogicManager.getNumLinesRemoved();
		clearBoard();

		gameLogicManager.newPiece();
		timer.start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Shape curPiece = gameLogicManager.getCurPiece();
		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();

		for (int i = 0; i < BoardHeight; ++i) {
			for (int j = 0; j < BoardWidth; ++j) {
				Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
				if (shape != Tetrominoes.NoShape)
					drawSquare(g, 0 + j * squareWidth(), boardTop + i * squareHeight(), shape);
			}
		}

		if (curPiece.getShape() != Tetrominoes.NoShape) {
			for (int i = 0; i < 4; ++i) {
				int x = gameLogicManager.getCurX() + curPiece.x(i);
				int y = gameLogicManager.getCurY() - curPiece.y(i);
				drawSquare(g, 0 + x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(),
						curPiece.getShape());
			}
		}
	}

	private void clearBoard() {
		for (int i = 0; i < BoardHeight * BoardWidth; ++i)
			board[i] = Tetrominoes.NoShape;
	}

	private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
		Color colors[] = {new Color(0, 0, 0), new Color(204, 102, 102), new Color(102, 204, 102),
				new Color(102, 102, 204), new Color(204, 204, 102), new Color(204, 102, 204), new Color(102, 204, 204),
				new Color(218, 170, 0)};

		Color color = colors[shape.ordinal()];

		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight() - 1, x, y);
		g.drawLine(x, y, x + squareWidth() - 1, y);

		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
	}

	public boolean isPaused() {
		return gameLogicManager.isPaused();
	}

	public void updateStatusbar(String text) {
		statusbar.setText(text);
	}

	public void handleKeyAction(String action) {
		gameLogicManager.handleKeyAction(action);
	}
}