package ru.geobot;

/**
 *
 * @author Alexey Andreev <konsoletyper@gmail.com>
 */
public abstract class GameObjectAdapter implements GameObjectListener {
    @Override
    public boolean click() {
        return false;
    }

    @Override
    public void mouseEnter() {
    }

    @Override
    public void mouseLeave() {
    }

    @Override
    public void keyDown(Key key) {
    }

    @Override
    public void keyUp(Key key) {
    }

    @Override
    public void time(long time) {
    }
}
