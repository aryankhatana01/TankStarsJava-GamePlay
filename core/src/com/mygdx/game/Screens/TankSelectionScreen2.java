package com.mygdx.game.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGdxGame;

public class TankSelectionScreen2 implements Screen {
    MyGdxGame game;
    Texture img;

    public TankSelectionScreen2 (MyGdxGame game){
        this.game = game;
    }

    @Override
    public void show() {
        img = new Texture("five.jpg");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(img, 0, 0);
        if(Gdx.input.justTouched()){
//            if (Gdx.input.isTouched()) {
//                System.out.println("X: " + Gdx.input.getX());
//                System.out.println("Y: " + Gdx.input.getY());
//            }
            // Back button
            if (Gdx.input.getX()>68 && Gdx.input.getX()<195 && Gdx.input.getY()<158 && Gdx.input.getY()>74) {
                if (Gdx.input.isTouched()) {
                    img.dispose();
                    game.setScreen(new TankSelectionScreen1(game));
                }
            }
            // select tank
            else if (Gdx.input.getY()<476 && Gdx.input.getY()>306) {
                if (Gdx.input.isTouched()) {
                    game.setScreen(new PlayScreen(game));
                }
            }
        }

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