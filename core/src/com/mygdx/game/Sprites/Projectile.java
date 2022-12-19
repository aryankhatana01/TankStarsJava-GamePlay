package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;

public class Projectile extends Sprite {
    public World world;
    public Body b2body;
    private Texture projimg;

    public Projectile(World world){
        this.world = world;
        defineProj();
        projimg = new Texture("fireball.png");
        setBounds(0,0, 16/MyGdxGame.PPM, 16/MyGdxGame.PPM);
        setRegion(projimg);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
    }

    public void defineProj(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(80/MyGdxGame.PPM, 80/MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef  = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5/MyGdxGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

}