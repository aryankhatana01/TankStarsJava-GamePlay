package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;

public class ExitButton extends Sprite {
    public World world;
    public Body b2body;
    private Texture projimg;

    public ExitButton(World world){
        this.world = world;
        defineProj();
        projimg = new Texture("EXIT.png");
        setBounds(0,0, 20/MyGdxGame.PPM, 10/MyGdxGame.PPM);
        setRegion(projimg);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
    }

    public void defineProj(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(380/MyGdxGame.PPM, 200/MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef  = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5/MyGdxGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        world.step(0 ,0 ,0);
        b2body.setActive(false);
    }

    public void dispose() {
        projimg.dispose();
        Vector2 newPos = new Vector2(40000, 40000);
        b2body.setTransform(newPos, 0);
//        world.destroyBody(b2body);
        world.step(0 ,0 ,0);
        b2body.setActive(false);
    }

}