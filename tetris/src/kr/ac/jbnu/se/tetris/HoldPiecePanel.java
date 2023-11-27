package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class HoldPiecePanel extends JPanel {
    Tetrominoes[] holdBoard;
    final int holdBoardWidth;
    final int holdBoardHeight;
    private final ConfigurationManager configurationManager;

    public HoldPiecePanel() {
        this.configurationManager = new ConfigurationManager();
        this.setPreferredSize(new Dimension(170, 85));
        this.setBackground(Color.DARK_GRAY);
        holdBoardWidth = configurationManager.getHoldBoardWidth();
        holdBoardHeight = configurationManager.getHoldBoarHeight();
        holdBoard = new Tetrominoes[holdBoardWidth * holdBoardHeight];
        clearHoldBoard();
    }

    public void clearHoldBoard() {
        Arrays.fill(holdBoard, Tetrominoes.NO_SHAPE);
    }

    public void updateHoldBoard(Shape holdPiece) {
        clearHoldBoard();

        for (int i = 0; i < 4; ++i) {
            int x = (holdPiece.x(i) + holdBoardWidth / 2);  // 중앙 정렬(x축)
            int y = holdBoardHeight - (holdPiece.y(i) + holdBoardHeight / 2) - 1;  // 중앙 정렬(y축), -1은 배열 인덱스 조정을 위해 추가

            // holdBoard의 범위 내에 있는지 확인합니다.
            if (x >= 0 && x < holdBoardWidth && y >= 0 && y < holdBoardHeight) {
                holdBoard[(y * holdBoardWidth) + x] = holdPiece.getShape();
            }
        }
    }

    int squareWidth() {
        return getWidth() / configurationManager.getHoldBoardWidth();
    }

    int squareHeight() {
        return getHeight() / configurationManager.getHoldBoarHeight();
    }
    public Tetrominoes holdShapeAt(int x, int y) {
        return holdBoard[(y * holdBoardWidth) + x];
    }


    public void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
        Color[] colors = {new Color(0, 0, 0), new Color(204, 102, 102), new Color(102, 204, 102),
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Dimension size = getSize();
        int holdBoardTop = (int) size.getHeight() - holdBoardHeight * squareHeight();

        for (int i = 0; i < holdBoardHeight; ++i) {
            for (int j = 0; j < holdBoardWidth; ++j) {
                Tetrominoes shape = holdShapeAt(j, holdBoardHeight - i - 1);

                if (shape != Tetrominoes.NO_SHAPE)
                    drawSquare(g, j * squareWidth(), holdBoardTop + i * squareHeight() , shape);
            }
        }
    }

}
