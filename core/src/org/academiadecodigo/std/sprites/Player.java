package org.academiadecodigo.std.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import org.academiadecodigo.std.Tumor;
import org.academiadecodigo.std.screens.PlayScreen;

/**
 * Created by Helia Marcos, David Neves, Nuno Pereira, Nelson Oliveira, Pavel Racu and Luis Salvado on 07/07/2016.
 */
public class Player extends Sprite {

    private final int PLAYER_RADIUS = 40;
    public static final float PLAYER_SPEED = 1f;

    private Body b2Body;
    private World world;
    private Fixture fixture;
    private FixtureDef fixtureDef;
    private Texture infectedTexture;


    public Player(PlayScreen screen, float x, float y, Texture infectedTexture, short categoryBit) {

        super(infectedTexture);
        this.world = screen.getWorld();
        this.infectedTexture = infectedTexture;
        setSize(this.getWidth() / Tumor.PPM, this.getHeight() / Tumor.PPM);

        definePlayer(x, y, categoryBit);
    }

    public void update(float dt) {

        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    public void definePlayer(float x, float y, short categoryBit) {

        // Cria a representação do objecto e define as propriedades do mesmo
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / Tumor.PPM, y / Tumor.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bodyDef);

        // define a fisica do objecto
        fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(PLAYER_RADIUS / Tumor.PPM);

        fixtureDef.filter.categoryBits = categoryBit;
        fixtureDef.filter.maskBits = Tumor.BALL_BIT | Tumor.VIRUS1_BIT | Tumor.VIRUS2_BIT | Tumor.EDGE_BIT;

        fixtureDef.shape = shape;
        fixtureDef.restitution = 1.4f;
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
