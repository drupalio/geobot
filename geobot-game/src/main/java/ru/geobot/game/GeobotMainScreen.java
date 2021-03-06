package ru.geobot.game;

import ru.geobot.EntryPoint;
import ru.geobot.EntryPointCallback;
import ru.geobot.Key;
import ru.geobot.game.ui.GameOverMenu;
import ru.geobot.game.ui.MainMenu;
import ru.geobot.graphics.Color;
import ru.geobot.graphics.Graphics;
import ru.geobot.graphics.ImageUtil;
import ru.geobot.resources.ResourceReader;

/**
 *
 * @author Alexey Andreev <konsoletyper@gmail.com>
 */
public class GeobotMainScreen implements EntryPoint {
    private static int buttonWidth = 120;
    private static int buttonHeight = 111;
    private static int buttonPadding = 20;
    private EntryPoint inner;
    private EntryPoint menu;
    private MainMenu mainMenu;
    private GameOverMenu gameOverMenu;
    private boolean displayingMenu = true;
    private long timeOffset;
    private long currentTime;
    private long suspendTime;
    private int width;
    private int height;
    private ResourceReader resourceReader;
    private GameResources resources;
    private int mouseX;
    private int mouseY;

    public GeobotMainScreen() {
        mainMenu = new MainMenu(this);
        menu = mainMenu;
        gameOverMenu = new GameOverMenu(mainMenu, this);
    }

    @Override
    public void mouseMove(int x, int y) {
        mouseX = x;
        mouseY = y;
        if (!displayingMenu) {
            inner.mouseMove(x, y);
        } else {
            menu.mouseMove(x, y);
        }
    }

    @Override
    public void mouseDown() {
        if (!displayingMenu) {
            if (!menuButtonHover()) {
                inner.mouseDown();
            } else {
                showMenu();
            }
        } else {
            menu.mouseDown();
        }
    }

    @Override
    public void mouseUp() {
        if (!displayingMenu) {
            if (!menuButtonHover()) {
                inner.mouseUp();
            }
        } else {
            menu.mouseUp();
        }
    }

    @Override
    public void keyDown(Key key) {
        if (!displayingMenu) {
            inner.keyDown(key);
        } else {
            menu.keyDown(key);
        }
    }

    @Override
    public void keyUp(Key key) {
        if (!displayingMenu) {
            inner.keyUp(key);
        } else {
            menu.keyUp(key);
        }
    }

    @Override
    public boolean idle(long time) {
        currentTime = time;
        if (!displayingMenu) {
            return inner.idle(time - timeOffset);
        } else {
            return false;
        }
    }

    @Override
    public void resize(int width, int height) {
        if (!displayingMenu) {
            inner.resize(width, height);
        } else {
            menu.resize(width, height);
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics graphics) {
        if (!displayingMenu) {
            graphics.pushTransform();
            inner.paint(graphics);
            graphics.popTransform();
            ImageUtil menuButton = new ImageUtil(menuButtonHover() ? resources.menuButtonHover() :
                    resources.menuButton());
            menuButton.draw(graphics, width - buttonWidth - buttonPadding, height - buttonHeight - buttonPadding,
                    buttonWidth, buttonHeight);
        } else {
            if (inner != null) {
                graphics.pushTransform();
                inner.paint(graphics);
                graphics.popTransform();
                Color hazeColor = Color.black();
                hazeColor.a = 196;
                graphics.setColor(hazeColor);
                graphics.fillRectangle(0, 0, width, height);
            }
            menu.paint(graphics);
        }
    }

    private boolean menuButtonHover() {
        int x = mouseX - width + buttonPadding;
        int y = mouseY - height + buttonPadding;
        return x <= 0 && x > -buttonWidth && y <= 0 && y >= -buttonHeight;
    }

    @Override
    public void start(EntryPointCallback callback) {
        menu.start(callback);
    }

    private void stopGame() {
        setMenu(gameOverMenu);
        showMenu();
    }

    @Override
    public void setResourceReader(ResourceReader resourceReader) {
        this.resourceReader = resourceReader;
        resources = resourceReader.getResourceSet(GameResources.class);
        if (inner != null) {
            inner.setResourceReader(resourceReader);
        }
        menu.setResourceReader(resourceReader);
        gameOverMenu.setResourceReader(resourceReader);
    }

    @Override
    public void interrupt() {
        inner.interrupt();
    }

    public void showGame() {
        if (!displayingMenu) {
            return;
        }
        displayingMenu = false;
        timeOffset += currentTime - suspendTime;
        inner.resize(width, height);
    }

    public void showMenu() {
        if (displayingMenu) {
            return;
        }
        displayingMenu = true;
        suspendTime = currentTime;
    }

    public void setMenu(EntryPoint menu) {
        this.menu = menu;
        menu.resize(width, height);
    }

    public void setInner(EntryPoint inner) {
        this.inner = inner;
        inner.setResourceReader(resourceReader);
        inner.resize(width, height);
        inner.start(new EntryPointCallback() {
            @Override public void stop() {
                stopGame();
            }
        });
    }
}
