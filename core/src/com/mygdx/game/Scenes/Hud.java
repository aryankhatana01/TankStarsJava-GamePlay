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

    private Integer score_P1;
    private Integer score_P2;

    Label p1Label;
    Label p2Label;
    Label p1ScoreLabel;
    Label p2ScoreLabel;

    public Hud(SpriteBatch sb){
        score_P1 = 100;
        score_P2 = 100;

        viewport = new FitViewport(MyGdxGame.V_WIDTH,
                MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        p1ScoreLabel = new Label(String.format("%03d", score_P1),
                new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        p2ScoreLabel = new Label(String.format("%03d", score_P2),
                new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        p1Label = new Label("PLAYER 1",
                new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        p2Label = new Label("PLAYER 2",
                new Label.LabelStyle(new BitmapFont(), Color.GREEN));

        table.setDebug(true);

        table.add(p1Label);
//        table.add(p2Label);
        table.row();
        table.add(p1ScoreLabel);
//        table.add(p2ScoreLabel);

//        stage.addActor(table);
    }


}
