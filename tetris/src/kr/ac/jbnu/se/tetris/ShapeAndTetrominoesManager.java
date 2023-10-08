package kr.ac.jbnu.se.tetris;

public class ShapeAndTetrominoesManager {

    private Shape currentShape;
    private Shape nextShape;

    public ShapeAndTetrominoesManager() {
        generateNewShape();
        generateNextShape();
    }

    // 새로운 Shape를 랜덤하게 생성하는 메서드
    public void generateNewShape() {
        currentShape = nextShape != null ? nextShape : new Shape();
        generateNextShape();
    }

    private void generateNextShape() {
        nextShape = new Shape();
        nextShape.setRandomShape();
    }

    public Shape getNextShape() {
        return nextShape;
    }

    // 현재 Shape를 회전시키는 메서드
    public Shape rotateLeft() {
        return rotateLeft(currentShape);
    }

    public Shape rotateRight() {
        return rotateRight(currentShape);
    }

    // Shape 객체를 인자로 받아 회전시키는 메서드
    public Shape rotateLeft(Shape shape) {
        if (shape.getShape() == Tetrominoes.SquareShape)
            return shape;

        Shape result = new Shape();
        result.setShape(shape.getShape());

        for (int i = 0; i < 4; ++i) {
            result.setX(i, shape.y(i));
            result.setY(i, -shape.x(i));
        }
        currentShape = result;  // 현재 Shape를 업데이트
        return result;
    }

    public Shape rotateRight(Shape shape) {
        if (shape.getShape() == Tetrominoes.SquareShape)
            return shape;

        Shape result = new Shape();
        result.setShape(shape.getShape());

        for (int i = 0; i < 4; ++i) {
            result.setX(i, -shape.y(i));
            result.setY(i, shape.x(i));
        }
        currentShape = result;  // 현재 Shape를 업데이트
        return result;
    }


    // 현재 Shape의 정보를 제공하는 메서드
    public Shape getCurrentShape() {
        return currentShape;
    }

    // 현재 Shape를 설정하는 메서드
    public void setCurrentShape(Shape shape) {
        this.currentShape = shape;
    }
}
