package ru.geobot;

import java.util.ArrayList;
import java.util.List;
import org.jbox2d.dynamics.World;
import ru.geobot.graphics.Graphics;

/**
 *
 * @author Alexey Andreev <konsoletyper@gmail.com>
 */
public class GameObject {
    Game game;
    int zIndex;
    private List<GameObjectListener> listeners;

    public GameObject(Game game) {
        this.game = game;
        game.cleanRemovedObjects();
        game.objects.add(this);
    }

    public final void dispose() {
        if (game == null) {
            throw new IllegalStateException("Game object is already disposed");
        }
        destroy();
        game.hasRemovedObjects = true;
        game = null;
    }

    protected void destroy() {
    }

    protected void paint(@SuppressWarnings("unused") Graphics graphics) {
    }

    @SuppressWarnings("unused")
    protected boolean hasPoint(float x, float y) {
        return false;
    }

    protected void release() {
    }

    protected void mouseEnter() {
        if (listeners != null) {
            for (GameObjectListener listener : listeners.toArray(new GameObjectListener[0])) {
                listener.mouseEnter();
            }
        }
    }

    protected void mouseLeave() {
        if (listeners != null) {
            for (GameObjectListener listener : listeners.toArray(new GameObjectListener[0])) {
                listener.mouseLeave();
            }
        }
    }

    public final void addListener(GameObjectListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    public final void removeListener(GameObjectListener listener) {
        if (listeners == null) {
            return;
        }
        listeners.remove(listener);
    }

    protected boolean click() {
        boolean handled = false;
        if (listeners != null) {
            for (GameObjectListener listener : listeners.toArray(new GameObjectListener[0])) {
                if (listener.click()) {
                    handled = true;
                }
            }
        }
        return handled;
    }

    protected void time(long time) {
        if (listeners != null) {
            for (GameObjectListener listener : listeners.toArray(new GameObjectListener[0])) {
                listener.time(time);
            }
        }
    }

    protected final int getZIndex() {
        return zIndex;
    }

    protected final void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    protected final World getWorld() {
        return game != null ? game.getWorld() : null;
    }

    protected final Game getGame() {
        return game;
    }

    protected final boolean isUnderMouse() {
        return game != null && game.objectUnderMouse == this;
    }
}
