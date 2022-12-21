package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;

public class MainMenuScreen implements Screen {
    MyGdxGame game;
    Texture img, newgamebutton, resumebutton, exitbutton, loading1, loading2, loading3, loading4;
    float x;
    float y;

    public MainMenuScreen (MyGdxGame game){
        this.game = game;
    }

    @Override
    public void show() {
        img = new Texture("two.jpg");
//        TextureRegion loading1 = new TextureRegion(new Texture("1.png"));
//        TextureRegion loading2 = new TextureRegion(new Texture("2.png"));
//        TextureRegion loading3 = new TextureRegion(new Texture("3.png"));
//        TextureRegion loading4 = new TextureRegion(new Texture("4.png"));
//        img = new Texture("Screenshot_20221116_134850-PhotoRoom.png");
//        newgamebutton = new Texture("newgame.png");
//        resumebutton = new Texture("resume.png");
//        exitbutton = new Texture("exit.png");
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(img, x, y);
        if(Gdx.input.justTouched()){
//            if (Gdx.input.isTouched()) {
//                System.out.println("X: " + Gdx.input.getX());
//                System.out.println("Y: " + Gdx.input.getY());
//            }
            // Exit
            if (Gdx.input.getX()>478 && Gdx.input.getX()<738 && Gdx.input.getY()<217 && Gdx.input.getY()>151) {
                if (Gdx.input.isTouched()) {
                    Gdx.app.exit();
                }
            }
            // Play
            else if (Gdx.input.getX()>338 && Gdx.input.getX()<597 && Gdx.input.getY()<113 && Gdx.input.getY()>45) {
                if (Gdx.input.isTouched()) {
                    game.setScreen(new GameTypeSelection(game));
                }
            }
//            // Resume
//            else if (Gdx.input.getX()>1020 && Gdx.input.getX()<1425 && Gdx.input.getY()<171 && Gdx.input.getY()>54) {
//                if (Gdx.input.isTouched()) {
//                    game.setScreen(new SavedStates(game));
//                }
//            }
        }

        Animation playerAnimation = new Animation(0.1f, loading1, loading2, loading3, loading4);
        playerAnimation.setPlayMode(Animation.PlayMode.LOOP);
//        game.batch.draw(newgamebutton, 260, y+530, 550, 250);
//        game.batch.draw(resumebutton, 660, y+530, 550, 250);
//        game.batch.draw(exitbutton, 430, y+480, 550, 250);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
        img.dispose();
    }
}