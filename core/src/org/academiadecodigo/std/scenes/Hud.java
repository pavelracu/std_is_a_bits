package org.academiadecodigo.std.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.academiadecodigo.std.STDIsABits;

/**
 * Created by Helia Marcos, David Neves, Nuno Pereira, Nelson Oliveira, Pavel Racu and Luis Salvado on 07/07/2016.
 */
public class Hud implements Disposable {

    private static Label scoreLabel1;
    private static Label scoreLabel2;
    private static Integer scorePlayer1;
    private static Integer scorePlayer2;
    public Stage stage;
    private Label countdownLabel;
    private Label timeLabel;
    private Label player1Label;
    private Label player2Label;
    private Viewport viewport;
    private Integer worldTimer;
    private float timeCount;
    private boolean timeUp;

    public Hud(SpriteBatch sb) {

        worldTimer = 180;
        timeCount = 0;
        scorePlayer1 = 0;
        scorePlayer2 = 0;

        viewport = new FitViewport(STDIsABits.WIDTH, STDIsABits.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.GRAY));
        countdownLabel.setFontScale(2f);
        scoreLabel1 = new Label(String.format("%06d", scorePlayer1), new Label.LabelStyle(new BitmapFont(), Color.RED));
        scoreLabel1.setFontScale(2f);
        scoreLabel2 = new Label(String.format("%06d", scorePlayer2), new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        scoreLabel2.setFontScale(2f);
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.GRAY));
        timeLabel.setFontScale(2f);
        player1Label = new Label("PLAYER 1", new Label.LabelStyle(new BitmapFont(), Color.RED));
        player1Label.setFontScale(2f);
        player2Label = new Label("PLAYER 2", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        player2Label.setFontScale(2f);

        table.add(player1Label).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.add(player2Label).expandX().padTop(10);
        table.row();
        table.add(scoreLabel1).expandX();
        table.add(countdownLabel).expandX();
        table.add(scoreLabel2).expandX();

        stage.addActor(table);
    }

    public static void addScore(int value, int player) {
        switch (player) {
            case 1:
                scorePlayer1 += value;
                scoreLabel1.setText(String.format("%04d", scorePlayer1));
                break;
            case 2:
                scorePlayer2 += value;
                scoreLabel2.setText(String.format("%04d", scorePlayer2));
                break;
            default:
                break;
        }
    }

    public static void removeScore(int value, int player) {
        switch (player) {
            case 1:
                scorePlayer1 -= value;
                scoreLabel1.setText(String.format("%04d", scorePlayer1));
                break;
            case 2:
                scorePlayer2 -= value;
                scoreLabel2.setText(String.format("%04d", scorePlayer2));
                break;
            default:
                break;
        }
    }


    public void update(float dt) {

        timeCount += dt;
        if (timeCount >= 1) {

            if (worldTimer > 0) {
                worldTimer--;

            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public boolean isTimeUp() {
        return timeUp;
    }

    @Override
    public void dispose() {

        stage.dispose();
    }

    public void clearScore() {
        scorePlayer1 = 0;
        scoreLabel1.setText(String.format("%04d", scorePlayer1));
        scorePlayer2 = 0;
        scoreLabel2.setText(String.format("%04d", scorePlayer2));
    }
}
