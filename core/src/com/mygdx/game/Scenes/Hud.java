package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private int health1;
    private int health2;

    private float angle1;
    private float angle2;

    private int power1;
    private int power2;

    Label p1Score;
    Label p2Score;
    Label p1Text;
    Label p2Text;

    Label p1Angle;
    Label p2Angle;

    Label p1Power;
    Label p2Power;

    public Hud(SpriteBatch batch){
        health1 = 100;
        health2 = 100;

        power1 = 180;
        power2 = 180;

        angle1 = 0;
        angle2 = 0;

        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        p1Score = new Label(String.format("%03d", health1), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        p2Score = new Label(String.format("%03d", health2), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        p1Score.setFontScale(0.5F);
        p2Score.setFontScale(0.5F);

        p1Angle = new Label(String.format("%03f", angle1), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        p2Angle = new Label(String.format("%03f", angle2), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        p1Angle.setFontScale(0.5F);
        p2Angle.setFontScale(0.5F);

        p1Power = new Label(String.format("%03d", power1), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        p2Power = new Label(String.format("%03d", power2), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        p1Power.setFontScale(0.5F);
        p2Power.setFontScale(0.5F);

        p1Text = new Label("PLAYER 1", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        p2Text = new Label("PLAYER 2", new Label.LabelStyle(new BitmapFont(), Color.GREEN));

        table.setDebug(true);

        table.add(p1Text).expandX().padLeft(10);
        table.add(p2Text).expandX().padLeft(10);
        table.row();
        table.add(p1Score).expandX().padLeft(10);
        table.add(p2Score).expandX().padLeft(10);
        table.row();
        table.add(p1Angle).expandX().padLeft(10);
        table.add(p2Angle).expandX().padLeft(10);
        table.row();
        table.add(p1Power).expandX().padLeft(10);
        table.add(p2Power).expandX().padLeft(10);


        stage.addActor(table);
    }

    public void update(float dt, int h1, int h2, float ang1, float ang2, int p1, int p2) {
        health1 = h1;
        health2 = h2;

        angle1 = ang1;
        angle2 = ang2;

        power1 = p1;
        power2 = p2;

        p1Score.setText(health1);
        p2Score.setText(health2);

        p1Angle.setText((int) angle1);
        p2Angle.setText((int) angle2);

        p1Power.setText(power1);
        p2Power.setText(power2);
    }


}
