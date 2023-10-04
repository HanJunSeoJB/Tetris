package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import java.awt.Graphics;

public class RenderingManager {

    //* 객체 초기화
    private final Board board;

    public RenderingManager(Board board) {
        this.board = board;
    }
    //*

    //* ??
    public void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
        Color colors[] = {new Color(0, 0, 0), new Color(204, 102, 102), new Color(102, 204, 102),
                new Color(102, 102, 204), new Color(204, 204, 102), new Color(204, 102, 204), new Color(102, 204, 204),
                new Color(218, 170, 0)};

        Color color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, board.squareWidth() - 2, board.squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + board.squareHeight() - 1, x, y);
        g.drawLine(x, y, x + board.squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + board.squareHeight() - 1, x + board.squareWidth() - 1, y + board.squareHeight() - 1);
        g.drawLine(x + board.squareWidth() - 1, y + board.squareHeight() - 1, x + board.squareWidth() - 1, y + 1);
    }
    //*

    public void drawGhostSquare(Graphics g, int x, int y) {
        Color semiTransparentWhite = new Color(239, 237, 239, 40); // R=255, G=255, B=255, A=127

        g.setColor(semiTransparentWhite);
        g.fillRect(x + 1, y + 1, board.squareWidth() - 2, board.squareHeight() - 2);

        g.setColor(semiTransparentWhite.brighter());
        g.drawLine(x, y + board.squareHeight() - 1, x, y);
        g.drawLine(x, y, x + board.squareWidth() - 1, y);

        g.setColor(semiTransparentWhite.darker());
        g.drawLine(x + 1, y + board.squareHeight() - 1, x + board.squareWidth() - 1, y + board.squareHeight() - 1);
        g.drawLine(x + board.squareWidth() - 1, y + board.squareHeight() - 1, x + board.squareWidth() - 1, y + 1);
    }

}
