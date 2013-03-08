package com.blogspot.javagamexyz.gamexyz;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.blogspot.javagamexyz.gamexyz.components.GameMap;
import com.blogspot.javagamexyz.gamexyz.components.Player;
import com.blogspot.javagamexyz.gamexyz.components.Position;
import com.blogspot.javagamexyz.gamexyz.components.Sprite;
import com.blogspot.javagamexyz.gamexyz.components.SpriteAnimation;

public class EntityFactory {

	public static Entity createMap(World world) {
		Entity e = world.createEntity();
		
		e.addComponent(new GameMap());
		return e;
	}
	
	public static Entity createPlayer(World world, float x, float y) {
		Entity e = world.createEntity();

		e.addComponent(new Position(x,y));

		Sprite sprite = new Sprite("anim");
		sprite.r = 1f;
		sprite.g = 1f;
		sprite.b = 1f;
		sprite.a = 1f;
		sprite.rotation = 0f;
		sprite.scaleX = 1f;
		sprite.scaleY = 1f;
		e.addComponent(sprite);
		
		SpriteAnimation anim = new SpriteAnimation();
		anim.playMode = Animation.LOOP_PINGPONG;
		anim.frameDuration = 0.3f;
		anim.stateTime = 0f;
		e.addComponent(anim);
		
		e.addComponent(new Player());
		return e;
	}
	
	public static Entity createWarrior(World world, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent(new Position(x,y));
		
		Sprite sprite = new Sprite("anim");
		sprite.r = 1f;
		sprite.g = 1f;
		sprite.b = 1f;
		sprite.a = 1f;
		sprite.rotation = 0f;
		sprite.scaleX = 1f;
		sprite.scaleY = 1f;
		e.addComponent(sprite);
		
		SpriteAnimation anim = new SpriteAnimation();
		anim.playMode = Animation.LOOP_PINGPONG;
		anim.frameDuration = 0.3f;
		anim.stateTime = MathUtils.random();
		e.addComponent(anim);
		
		return e;
	}
}
