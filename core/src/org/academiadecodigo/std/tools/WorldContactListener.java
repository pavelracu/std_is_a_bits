package org.academiadecodigo.std.tools;

import com.badlogic.gdx.physics.box2d.*;
import org.academiadecodigo.std.STDIsABits;
import org.academiadecodigo.std.sprites.Cell;
import org.academiadecodigo.std.sprites.Player;

/**
 * Created by neves on 07/07/2016.
 */
public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case STDIsABits.BALL_BIT | STDIsABits.VIRUS1_BIT:
                if (fixA.getFilterData().categoryBits == STDIsABits.BALL_BIT) {
                    ((Cell) fixA.getUserData()).setPlayer((Player) fixB.getUserData());
                } else {
                    ((Cell) fixB.getUserData()).setPlayer((Player) fixA.getUserData());
                }
                break;

            case STDIsABits.BALL_BIT | STDIsABits.VIRUS2_BIT:
                if (fixA.getFilterData().categoryBits == STDIsABits.BALL_BIT) {
                    ((Cell) fixA.getUserData()).setPlayer((Player) fixB.getUserData());
                } else {
                    ((Cell) fixB.getUserData()).setPlayer((Player) fixA.getUserData());
                }
                break;

            case STDIsABits.BALL_BIT | STDIsABits.BALL_BIT:
                if (((Cell) fixA.getUserData()).getPlayer() != null) {
                    if (((Cell) fixB.getUserData()).getPlayer() != null) {
                        ((Cell) fixA.getUserData()).setPlayer(null);
                        ((Cell) fixA.getUserData()).setTexture(Cell.NOT_INFECTED);
                        ((Cell) fixB.getUserData()).setPlayer(null);
                        ((Cell) fixB.getUserData()).setTexture(Cell.NOT_INFECTED);
                    } else {
                        ((Cell) fixB.getUserData()).setPlayer((Player) fixA.getUserData());
                        ((Cell) fixB.getUserData()).setTexture((Player) fixA.getUserData().getInfectedTexture());
                    }
                } else {
                    ((Cell) fixA.getUserData()).setPlayer((Player) fixB.getUserData());
                    ((Cell) fixB.getUserData()).setTexture((Player) fixB.getUserData().getInfectedTexture());
                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
