package org.academiadecodigo.std.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import org.academiadecodigo.std.Tumor;
import org.academiadecodigo.std.screens.PlayScreen;
import org.academiadecodigo.std.sprites.Cell;

import java.util.Iterator;

/**
 * Created by Helia Marcos, David Neves, Nuno Pereira, Nelson Oliveira, Pavel Racu and Luis Salvado on 07/07/2016.
 */
public class B2WorldCreator {

    private static Array<Cell> cells;
    private static PlayScreen screen;

    public B2WorldCreator(PlayScreen screen) {
        this.screen = screen;
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Tumor.PPM, (rect.getY() + rect.getHeight() / 2) / Tumor.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Tumor.PPM, rect.getHeight() / 2 / Tumor.PPM);
            fdef.shape = shape;
            fdef.restitution = 1.1f;
            fdef.filter.categoryBits = Tumor.EDGE_BIT;
            body.createFixture(fdef);
        }

        cells = new Array<Cell>();
//        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
//
//            if (MathUtils.random(100) < 2) {
//                Rectangle rect = ((RectangleMapObject) object).getRectangle();
//                cells.add(new Cell(screen, rect.getX() / Tumor.PPM, rect.getY() / Tumor.PPM));
//            }
//
//        }

    }

    public static void setCells(String[] cellData) {


        Iterator<Cell> cellIterator = cells.iterator();
        int cellIndex = 0;

        while (cellIterator.hasNext()) {

            String[] parse = cellData[cellIndex].split(",");
            System.out.println("FLOAT 0: " + Float.valueOf(parse[0]));
            System.out.println("FLOAT 1: " + Float.valueOf(parse[1]));

            Cell c = cellIterator.next();
            c.setCenterX(Float.valueOf(parse[0]));
            c.setCenterY(Float.valueOf(parse[1]));
            c.setState(Integer.parseInt(parse[2]));


            cellIndex++;
        }
    }

    public static void clearCells() {
        cells.clear();
    }

    public static void removeCell(Cell cell) {
        cells.removeValue(cell, true);
    }

    public static Array<Cell> getCells() {
        return cells;
    }

    public static PlayScreen getScreen() {
        return screen;
    }

}
