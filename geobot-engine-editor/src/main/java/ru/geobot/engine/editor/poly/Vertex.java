package ru.geobot.engine.editor.poly;

/**
 *
 * @author Alexey Andreev <konsoletyper@gmail.com>
 */
public class Vertex {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public Vertex clone() {
        Vertex copy = new Vertex();
        copy.x = x;
        copy.y = y;
        return copy;
    }
}
