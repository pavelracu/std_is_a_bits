package org.academiadecodigo.std.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import org.academiadecodigo.std.Tumor;
import org.academiadecodigo.std.screens.PlayScreen;

/**
 * Created by Helia Marcos, David Neves, Nuno Pereira, Nelson Oliveira, Pavel Racu and Luis Salvado on 07/07/2016.
 */
public class Cell extends Sprite {

    public static final int CELL_RADIUS = 20;

    private Body b2Body;
    private World world;
    private Fixture fixture;
    private PlayScreen screen;
    private int state;
    private FixtureDef fdef;


    public static final Texture NOT_INFECTED = new Texture("cell.png");


    public Cell(PlayScreen screen, float x, float y) {

        super(NOT_INFECTED);
        this.screen = screen;
        this.world = screen.getWorld();

        setPosition(x, y);

        setSize(CELL_RADIUS * 2 / Tumor.PPM, CELL_RADIUS * 2 / Tumor.PPM);

        defineCell();

    }

    private void defineCell() {

        state = 0;
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bdef);

        fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(CELL_RADIUS / Tumor.PPM);
        fdef.filter.categoryBits = Tumor.BALL_BIT;
        fdef.filter.maskBits = Tumor.BALL_BIT | Tumor.EDGE_BIT | Tumor.VIRUS2_BIT | Tumor.VIRUS1_BIT;

        fdef.shape = shape;
        fdef.restitution = 1f;
        fdef.friction = 1f;


        fixture = b2Body.createFixture(fdef);
        fixture.setUserData(this);
    }

    public void update(float dt) {

        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Body getB2Body() {
        return b2Body;
    }

    public FixtureDef getFdef() {
        return fdef;
    }
}
