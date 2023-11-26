package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class NextPiecePanel extends JPanel {

    int miniBoardSize = 8;
    Tetrominoes[] miniBoard;
    final int MiniBoardWidth;
    final int MiniBoardHeight;
    private final ConfigurationManager configurationManager;

    public NextPiecePanel() {
        this.configurationManager = new ConfigurationManager();
        this.setPreferredSize(new Dimension(170, 85));
        this.setBackground(Color.DARK_GRAY);
        MiniBoardWidth = configurationManager.getMiniBoardWidth();
        MiniBoardHeight = configurationManager.getMiniBoardHeight();
        miniBoard = new Tetrominoes[MiniBoardWidth * MiniBoardHeight];
        clearMiniBoard();
    }

    public void clearMiniBoard() {
        Arrays.fill(miniBoard, Tetrominoes.NoShape);
    }

    public void updateMiniBoard(Shape nextPiece) {
        int MiniBoardHeight = configurationManager.getBoardHeight();
        clearMiniBoard();

        for (int i = 0; i < 4; ++i) {
            int x = (nextPiece.x(i) + miniBoardSize / 2);  // 중앙 정렬(x축)
            int y = MiniBoardHeight - (nextPiece.y(i) + miniBoardSize/ 2) - 14;  // 중앙 정렬(y축)

            // miniBoard의 범위 내에 있는지 확인합니다.
            if (x >= 0 && x < miniBoardSize && y >= 0 && y < miniBoardSize) {
                miniBoard[(y * miniBoardSize) + x] = nextPiece.getShape();
            }
        }

    }
    int squareWidth() {
        return getWidth() / configurationManager.getMiniBoardWidth();
    }

    int squareHeight() {
        return getHeight() / configurationManager.getMiniBoardHeight();
    }
    public Tetrominoes miniShapeAt(int x, int y) {
        return miniBoard[(y * MiniBoardWidth) + x];
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
        int MiniboardTop = (int) size.getHeight() - MiniBoardHeight * squareHeight();

        for (int i = 0; i < MiniBoardHeight; ++i) {
            for (int j = 0; j < MiniBoardWidth; ++j) {
                Tetrominoes shape = miniShapeAt(j, MiniBoardHeight - i - 1);

                if (shape != Tetrominoes.NoShape)
                    drawSquare(g, j * squareWidth(), MiniboardTop + i * squareHeight() , shape);
            }
        }
    }


}
