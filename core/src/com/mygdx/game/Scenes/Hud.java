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

    private Integer health1;
    private Integer health2;

    Label p1Score;
    Label p2Score;
    Label p1Text;
    Label p2Text;

    public Hud(SpriteBatch batch){
        health1 = 100;
        health2 = 100;

        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        p1Score = new Label(String.format("%03d", health1), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        p2Score = new Label(String.format("%03d", health2), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        p1Text = new Label("PLAYER 1", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        p2Text = new Label("PLAYER 2", new Label.LabelStyle(new BitmapFont(), Color.GREEN));

        table.setDebug(true);

        table.add(p1Text).expandX().padLeft(10);
        table.add(p2Text).expandX().padLeft(10);
        table.row();
        table.add(p1Score).expandX().padLeft(10);
        table.add(p2Score).expandX().padLeft(10);

        stage.addActor(table);
    }


}
