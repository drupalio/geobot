package ru.geobot.game.caves;

import ru.geobot.ResourceSet;
import ru.geobot.resources.Image;
import ru.geobot.resources.Large;
import ru.geobot.resources.PolygonalBodyFactory;
import ru.geobot.resources.ResourcePath;

/**
 *
 * @author Alexey Andreev <konsoletyper@gmail.com>
 */
@ResourceSet
public interface Cave2Resources {
    @ResourcePath("cave2.png")
    @Large
    Image background();

    @ResourcePath("cave2-shape.txt")
    PolygonalBodyFactory shape();
}
