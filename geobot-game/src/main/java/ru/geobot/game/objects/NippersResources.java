package ru.geobot.game.objects;

import ru.geobot.ResourceSet;
import ru.geobot.resources.Image;
import ru.geobot.resources.PolygonalBodyFactory;
import ru.geobot.resources.ResourcePath;

/**
 *
 * @author Alexey Andreev <konsoletyper@gmail.com>
 */
@ResourceSet
public interface NippersResources {
    @ResourcePath("nippers.png")
    Image image();

    @ResourcePath("nippers-shape.txt")
    PolygonalBodyFactory shape();
}
