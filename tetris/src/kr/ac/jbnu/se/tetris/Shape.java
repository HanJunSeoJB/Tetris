package kr.ac.jbnu.se.tetris;

import java.util.Random;

public class Shape {

	//* 객체 초기화
	private Tetrominoes pieceShape;
	private final int[][] coords;
	private final Random random = new Random();

	public Shape() {
		coords = new int[4][2];
		setShape(Tetrominoes.NO_SHAPE);
	}
	//*

	//* 도형 생성 함수
	public void setShape(Tetrominoes shape) {
		int[][][] coordsTable = new int[][][] { { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },
				{ { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } }, { { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },
				{ { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },
				{ { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } }, { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } },
				{ { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } } };
		for (int i = 0; i < 4; i++) {
			System.arraycopy(coordsTable[shape.ordinal()][i], 0, coords[i], 0, 2);
		}
		pieceShape = shape;
	}
	//*

	//* 좌표 설정 함수
	public void setX(int index, int x) {
		coords[index][0] = x;
	}

	public void setY(int index, int y) {
		coords[index][1] = y;
	}

	public int x(int index) {
		if (index < 0 || index >= coords.length) {
			throw new ArrayIndexOutOfBoundsException("Index out of bounds: " + index);
		}
		return coords[index][0];
	}

	public int y(int index) {
		return coords[index][1];
	}
	//*

	// 게터 함수
	public Tetrominoes getPieceShape() {
		return pieceShape;
	}

	//* 랜덤 블록 생성 함수
	public void setRandomShape() {
		int x = random.nextInt(7) + 1;
		Tetrominoes[] values = Tetrominoes.values();
		setShape(values[x]);
	}
	//*

	//* 좌표 관련 함수
	public int minX() {
		int m = coords[0][0];
		for (int i = 0; i < 4; i++) {
			m = Math.min(m, coords[i][0]);
		}
		return m;
	}

	public int maxX() {
		int maxCoordinateX = coords[0][0];
		for (int i = 0; i < 4; i++) {
			maxCoordinateX = Math.max(maxCoordinateX, coords[i][0]);
		}
		return maxCoordinateX;
	}

	public int getWidth() {
		return maxX() - minX() + 1;
	}

	public int minY() {
		int m = coords[0][1];
		for (int i = 0; i < 4; i++) {
			m = Math.min(m, coords[i][1]);
		}
		return m;
	}

}
