package org.academiadecodigo.std.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.physics.box2d.*;
import org.academiadecodigo.std.STDIsABits;
import org.academiadecodigo.std.screens.PlayScreen;

/**
 * Created by neves on 07/07/2016.
 */
public class Cell extends Sprite {

    private final int CELL_RADIUS = 10;

    private Body b2Body;
    private World world;
    private Fixture fixture;
    private PlayScreen screen;
    private Player player;

    public static final Texture NOT_INFECTED = new Texture("ball.png");
    public static final Texture INFECTED_BY_1 = new Texture("blueball.png");
    public static final Texture INFECTED_BY_2 = new Texture("redball.png");


    public Cell(PlayScreen screen, float x, float y) {

        super(NOT_INFECTED);
        this.screen = screen;
        this.world = screen.getWorld();

        setPosition(x, y);

        setSize(CELL_RADIUS * 2 / STDIsABits.PPM, CELL_RADIUS * 2 / STDIsABits.PPM);

        defineCell();

    }

    private void defineCell() {

        player = null;
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(CELL_RADIUS / STDIsABits.PPM);
        fdef.filter.categoryBits = STDIsABits.BALL_BIT;
        fdef.filter.maskBits = STDIsABits.BALL_BIT | STDIsABits.EDGE_BIT | STDIsABits.VIRUS2_BIT | STDIsABits.VIRUS1_BIT;

        fdef.shape = shape;
        fdef.restitution = 0.5f;
        fdef.friction = 100f;


        fixture = b2Body.createFixture(fdef);
        b2Body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt) {

        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}
