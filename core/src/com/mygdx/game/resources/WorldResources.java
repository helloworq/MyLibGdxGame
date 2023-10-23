package com.mygdx.game.resources;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.main.contants.EntityEnum;



public class WorldResources {
    public static final float GRAVITY = 9.8f;
    public static final World WORLD = new World(new Vector2(0, -GRAVITY), true);

    static {
        WORLD.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (EntityEnum.BULLET.equals(contact.getFixtureA().getBody().getUserData())) {
                    contact.getFixtureA().getBody().setUserData(EntityEnum.TRASH);
                }
                if (EntityEnum.BULLET.equals(contact.getFixtureB().getBody().getUserData())) {
                    contact.getFixtureB().getBody().setUserData(EntityEnum.TRASH);
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
        });
    }
}
