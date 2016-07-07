package org.academiadecodigo.std.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import org.academiadecodigo.std.STDIsABits;
import org.academiadecodigo.std.screens.PlayScreen;

/**
 * Created by neves on 07/07/2016.
 */
public class B2WorldCreator {

    public B2WorldCreator(PlayScreen screen) {

        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / STDIsABits.PPM, (rect.getY() + rect.getHeight() / 2) / STDIsABits.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / STDIsABits.PPM, rect.getHeight() / 2 / STDIsABits.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = STDIsABits.EDGE_BIT;
            body.createFixture(fdef);
        }
    }
}
