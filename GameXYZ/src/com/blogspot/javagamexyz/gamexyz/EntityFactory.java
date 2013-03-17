package com.blogspot.javagamexyz.gamexyz;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.blogspot.javagamexyz.gamexyz.components.ColorAnimation;
import com.blogspot.javagamexyz.gamexyz.components.Expires;
import com.blogspot.javagamexyz.gamexyz.components.MapPosition;
import com.blogspot.javagamexyz.gamexyz.components.ScaleAnimation;
import com.blogspot.javagamexyz.gamexyz.components.Sprite;
import com.blogspot.javagamexyz.gamexyz.components.SpriteAnimation;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;

public class EntityFactory {
	
	public static Entity createNPC(World world, int x, int y, GameMap gameMap) {
		Entity e = world.createEntity();
		
		e.addComponent(new MapPosition(x,y));
		
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
		
		gameMap.entityLocations[x][y] = e.getId();
		
		return e;
	}
		
	public static Entity createClick(World world, int x, int y, float startScale, float speed) {
		Entity e = world.createEntity();
		
		e.addComponent(new MapPosition(x,y));
		
		Sprite sprite = new Sprite("click");
		sprite.r = 1f;
		sprite.g = 1f;
		sprite.b = 1f;
		sprite.a = 0.5f;
		sprite.rotation = 0f;
		sprite.scaleX = startScale;
		sprite.scaleY = startScale;
		e.addComponent(sprite);
		
		Expires expires = new Expires(1f);
		e.addComponent(expires);
		
		ScaleAnimation scaleAnimation = new ScaleAnimation(speed);
		e.addComponent(scaleAnimation);
		
		ColorAnimation colorAnimation = new ColorAnimation();
		colorAnimation.alphaAnimate = true;
		colorAnimation.alphaSpeed = -1f;
		e.addComponent(colorAnimation);
	
		
		return e;
	}
}
