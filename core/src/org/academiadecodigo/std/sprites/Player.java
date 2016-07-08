package org.academiadecodigo.std.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import org.academiadecodigo.std.STDIsABits;
import org.academiadecodigo.std.screens.PlayScreen;

/**
 * Created by Helia Marcos, David Neves, Nuno Pereira, Nelson Oliveira, Pavel Racu and Luis Salvado on 07/07/2016.
 */
public class Player extends Sprite {

    private final int PLAYER_RADIUS = 20;
    public static final float PLAYER_SPEED = 1f;

    private Body b2Body;
    private World world;
    private Fixture fixture;
    private FixtureDef fixtureDef;
    private Texture infectedTexture;


    public Player(PlayScreen screen, float x, float y, Texture infectedTexture, short categoryBit) {

        super(new Texture("ball.png"));
        this.world = screen.getWorld();
        this.infectedTexture = infectedTexture;
        setSize(this.getWidth() / STDIsABits.PPM, this.getHeight() / STDIsABits.PPM);

        definePlayer(x, y, categoryBit);
    }

    public void update(float dt) {

        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    public void definePlayer(float x, float y, short categoryBit) {

        // Cria a representação do objecto e define as propriedades do mesmo
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / STDIsABits.PPM, y / STDIsABits.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bodyDef);

        // define a fisica do objecto
        fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(PLAYER_RADIUS / STDIsABits.PPM);

        fixtureDef.filter.categoryBits = categoryBit;
        fixtureDef.filter.maskBits = STDIsABits.BALL_BIT | STDIsABits.VIRUS1_BIT | STDIsABits.VIRUS2_BIT | STDIsABits.EDGE_BIT;

        fixtureDef.shape = shape;
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 10f;

        fixture = b2Body.createFixture(fixtureDef);
        fixture.setUserData(this);
    }

    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    public Texture getInfectedTexture() {
        return infectedTexture;
    }

    public Body getB2Body() {
        return b2Body;
    }
}
