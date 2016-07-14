package org.academiadecodigo.tumor.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.*;
import org.academiadecodigo.tumor.Tumor;
import org.academiadecodigo.tumor.sprites.Cell;
import org.academiadecodigo.tumor.sprites.Player;

/**
 * Created by Helia Marcos, David Neves, Nuno Pereira, Nelson Oliveira, Pavel Racu and Luis Salvado on 07/07/2016.
 */
public class WorldContactListener implements ContactListener {

    private AssetManager manager;

    public WorldContactListener(AssetManager manager) {
        this.manager = manager;
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case Tumor.BALL_BIT | Tumor.VIRUS1_BIT:
            case Tumor.BALL_BIT | Tumor.VIRUS2_BIT:
                if (fixA.getFilterData().categoryBits == Tumor.BALL_BIT) {
                    if (((Cell) fixA.getUserData()).getState() != ((Player) fixB.getUserData()).getPlayerNum()) {
                        ((Cell) fixA.getUserData()).setState(((Player) fixB.getUserData()).getPlayerNum());
                        ((Cell) fixA.getUserData()).setTexture(((Player) fixB.getUserData()).getInfectedTexture());

                        manager.get("infection.wav", Sound.class).play();
                        Gdx.input.vibrate(20);
                    }

                } else {
                    if (((Cell) fixB.getUserData()).getState() != ((Player) fixA.getUserData()).getPlayerNum()) {
                        ((Cell) fixB.getUserData()).setState(((Player) fixA.getUserData()).getPlayerNum());
                        ((Cell) fixB.getUserData()).setTexture(((Player) fixA.getUserData()).getInfectedTexture());

                        manager.get("infection.wav", Sound.class).play();
                        Gdx.input.vibrate(20);
                    }

                }
                break;

            case Tumor.BALL_BIT | Tumor.BALL_BIT:
                if (((Cell) fixA.getUserData()).getState() != 0) {
                    if (((Cell) fixB.getUserData()).getState() != 0) {
                        if (((Cell) fixB.getUserData()).getState() != ((Cell) fixA.getUserData()).getState()) {
                            ((Cell) fixA.getUserData()).setState(0);
                            ((Cell) fixA.getUserData()).setTexture(Cell.NOT_INFECTED);
                            ((Cell) fixB.getUserData()).setState(0);
                            ((Cell) fixB.getUserData()).setTexture(Cell.NOT_INFECTED);

                        }
                    } else {
                        ((Cell) fixB.getUserData()).setState(((Cell) fixA.getUserData()).getState());
                        ((Cell) fixB.getUserData()).setTexture(((Cell) fixA.getUserData()).getTexture());
                    }
                } else {
                    if (((Cell) fixB.getUserData()).getState() != 0) {
                        ((Cell) fixA.getUserData()).setState(((Cell) fixB.getUserData()).getState());
                        ((Cell) fixA.getUserData()).setTexture(((Cell) fixB.getUserData()).getTexture());
                    }
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
