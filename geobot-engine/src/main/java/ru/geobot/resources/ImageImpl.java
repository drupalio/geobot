package ru.geobot.resources;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import ru.geobot.AWTGraphics;
import ru.geobot.graphics.AffineTransform;
import ru.geobot.graphics.Graphics;

/**
 *
 * @author Alexey Andreev <konsoletyper@gmail.com>
 */
class ImageImpl implements Image {
    private BufferedImage[] scaledImages;

    public ImageImpl(BufferedImage[] scaledImages) {
        this.scaledImages = scaledImages;
    }

    @Override
    public void draw(Graphics graphics) {
        AffineTransform transformation = graphics.getTransform();
        double factor = Math.sqrt(Math.abs(transformation.getDeterminant()));
        int index = 0;
        int intFactor = 1;
        while (index < scaledImages.length - 1 && factor < 0.5) {
            factor *= 2;
            intFactor *= 2;
            ++index;
        }
        BufferedImage original = scaledImages[0];
        BufferedImage scaled = scaledImages[index];
        graphics.scale(intFactor, intFactor);
        graphics.scale(original.getWidth() / (float)(scaled.getWidth() * intFactor),
                original.getHeight() / (float)(scaled.getHeight() * intFactor));
        ((AWTGraphics)graphics).getInnerGraphics().drawRenderedImage(scaled,
                new java.awt.geom.AffineTransform());
        graphics.setTransform(transformation);
    }

    @Override
    public void draw(Graphics graphics, float alpha) {
        AffineTransform transformation = graphics.getTransform();
        double factor = Math.sqrt(Math.abs(transformation.getDeterminant()));
        int index = 0;
        int intFactor = 1;
        while (index < scaledImages.length - 1 && factor < 0.5) {
            factor *= 2;
            intFactor *= 2;
            ++index;
        }
        BufferedImage original = scaledImages[0];
        BufferedImage scaled = scaledImages[index];
        graphics.scale(intFactor, intFactor);
        graphics.scale(original.getWidth() / (float)(scaled.getWidth() * intFactor),
                original.getHeight() / (float)(scaled.getHeight() * intFactor));
        Graphics2D innerGraphics = ((AWTGraphics)graphics).getInnerGraphics();
        Composite oldComposite = innerGraphics.getComposite();
        innerGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        innerGraphics.drawRenderedImage(scaled, new java.awt.geom.AffineTransform());
        innerGraphics.setComposite(oldComposite);
        graphics.setTransform(transformation);
    }

    @Override
    public int getHeight() {
        return scaledImages[0].getHeight();
    }

    @Override
    public int getWidth() {
        return scaledImages[0].getWidth();
    }
}
