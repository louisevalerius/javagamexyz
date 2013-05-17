package com.blogspot.javagamexyz.gamexyz;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.blogspot.javagamexyz.gamexyz.abilities.ActionFactory;
import com.blogspot.javagamexyz.gamexyz.components.AI;
import com.blogspot.javagamexyz.gamexyz.components.Abilities;
import com.blogspot.javagamexyz.gamexyz.components.ColorAnimation;
import com.blogspot.javagamexyz.gamexyz.components.Cursor;
import com.blogspot.javagamexyz.gamexyz.components.Expires;
import com.blogspot.javagamexyz.gamexyz.components.FadingMessage;
import com.blogspot.javagamexyz.gamexyz.components.MapPosition;
import com.blogspot.javagamexyz.gamexyz.components.Movable;
import com.blogspot.javagamexyz.gamexyz.components.ScaleAnimation;
import com.blogspot.javagamexyz.gamexyz.components.Sprite;
import com.blogspot.javagamexyz.gamexyz.components.SpriteAnimation;
import com.blogspot.javagamexyz.gamexyz.components.Stats;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;

public class EntityFactory {
	
	public static Entity createBlue(World world, int x, int y, GameMap gameMap) {
		Entity e = world.createEntity();
		
		e.addComponent(new MapPosition(x,y));
		gameMap.addEntity(e.getId(), x, y);
		
		Sprite sprite = new Sprite("cylinder");
		sprite.r = 0;
		sprite.g = 0;
		sprite.b = 0.4f;
		sprite.a = 1f;
		sprite.rotation = 0f;
		sprite.scaleX = 1f;
		sprite.scaleY = 1f;
		e.addComponent(sprite);
		
		e.addComponent(new Movable(10f, 0.14f));
		
		Abilities abilities = new Abilities();
		int abilityRoll = MathUtils.random(0,3) * 0;
		if (abilityRoll == 0) { 
			sprite.name = "fighter";
			abilities.actions.add(ActionFactory.physicalAttack("Sword Slash", 5, 85, 1));
			abilities.actions.add(ActionFactory.cure("First Aid",2, 1, 0, 1));
			abilities.actions.add(ActionFactory.spinAttack("Spin Attack", 4, 90));
		}
		else if (abilityRoll == 1) {
			sprite.name = "archer";
			abilities.actions.add(ActionFactory.physicalAttack("Shoot", 4, 70, 4));
			abilities.actions.add(ActionFactory.physicalAttack("Dagger Stab", 2, 80, 1));
		}
		else if (abilityRoll == 2) {
			sprite.name = "wizard";
			abilities.actions.add(ActionFactory.magicAttack("Fireball", 3, 4, 80, 3, 2));
			abilities.actions.add(ActionFactory.magicAttack("Ice Dagger", 4, 4, 90, 3, 1));
			abilities.actions.add(ActionFactory.physicalAttack("Club", 2, 70, 1));
		}
		else {//if (abilityRoll == 3) {
			sprite.name = "healer";
			abilities.actions.add(ActionFactory.cure("Heal", 4, 4, 3, 1));
			abilities.actions.add(ActionFactory.cure("Group Heal", 3, 6, 3, 2));
			abilities.actions.add(ActionFactory.physicalAttack("Club", 1, 65, 1));
		}
		e.addComponent(abilities);
		e.addComponent(new Stats());
		e.addComponent(new AI());
		world.getManager(PlayerManager2.class).setPlayer(e, Players.Blue);
		
		return e;
	}
	
	public static Entity createRed(World world, int x, int y, GameMap gameMap) {
		Entity e = world.createEntity();
		
		e.addComponent(new MapPosition(x,y));
		gameMap.addEntity(e.getId(), x, y);
		
		Sprite sprite = new Sprite("cylinder");
		sprite.r = 0.4f;
		sprite.g = 0;
		sprite.b = 0f;
		sprite.a = 1f;
		sprite.rotation = 0f;
		sprite.scaleX = 1f;
		sprite.scaleY = 1f;
		e.addComponent(sprite);
		
		e.addComponent(new Movable(10f, 0.14f));
		
		Abilities abilities = new Abilities();
		int abilityRoll = MathUtils.random(0,3) * 0;
		if (abilityRoll == 0) { 
			sprite.name = "fighter";
			abilities.actions.add(ActionFactory.physicalAttack("Sword Slash", 5, 85, 1));
			abilities.actions.add(ActionFactory.cure("First Aid",2, 1, 0, 1));
			abilities.actions.add(ActionFactory.spinAttack("Spin Attack", 4, 90));
		}
		else if (abilityRoll == 1) {
			sprite.name = "archer";
			abilities.actions.add(ActionFactory.physicalAttack("Shoot", 4, 70, 4));
			abilities.actions.add(ActionFactory.physicalAttack("Dagger Stab", 2, 80, 1));
		}
		else if (abilityRoll == 2) {
			sprite.name = "wizard";
			abilities.actions.add(ActionFactory.magicAttack("Fireball", 3, 4, 80, 3, 2));
			abilities.actions.add(ActionFactory.magicAttack("Ice Dagger", 4, 4, 90, 3, 1));
			abilities.actions.add(ActionFactory.physicalAttack("Club", 2, 70, 1));
		}
		else {//if (abilityRoll == 3) {
			sprite.name = "healer";
			abilities.actions.add(ActionFactory.cure("Heal", 4, 4, 3, 1));
			abilities.actions.add(ActionFactory.cure("Group Heal", 3, 6, 3, 2));
			abilities.actions.add(ActionFactory.physicalAttack("Club", 1, 65, 1));
		}
		e.addComponent(abilities);
		e.addComponent(new Stats());
		e.addComponent(new AI());
		world.getManager(PlayerManager2.class).setPlayer(e, Players.Red);
		
		return e;
	}
	
	public static Entity createNPC(World world, int x, int y, GameMap gameMap) {
		Entity e = world.createEntity();
		
		e.addComponent(new MapPosition(x,y));
		gameMap.addEntity(e.getId(), x, y);
		
		Sprite sprite = new Sprite("anim");
		sprite.r = 1f;
		sprite.g = 1f;
		sprite.b = 1f;
		sprite.a = 1f;
		sprite.rotation = 0f;
		sprite.scaleX = 1f;
		sprite.scaleY = 1f;
		e.addComponent(sprite);
		
		Abilities abilities = new Abilities();
		abilities.actions.add(ActionFactory.physicalAttack("Attack",4,90,1));
		abilities.actions.add(ActionFactory.cure("Heal",2, 1, 0, 1));
		
		SpriteAnimation anim = new SpriteAnimation();
		anim.playMode = Animation.LOOP_PINGPONG;
		anim.frameDuration = 0.3f;
		anim.stateTime = MathUtils.random(10f);
		e.addComponent(anim);
		
		e.addComponent(new Movable(10f, 0.14f));
		
		e.addComponent(new Abilities());
		if (e.getId() == 64) e.addComponent(new Stats(true));
		else e.addComponent(new Stats());
	
		world.getManager(PlayerManager2.class).setPlayer(e, Players.Human);
		
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
	
	public static Entity createCursor(World world) {
		Entity e = world.createEntity();
		
		e.addComponent(new MapPosition(0,0));
		
		Sprite sprite = new Sprite("anim");
		sprite.r = 1f;
		sprite.g = 1f;
		sprite.b = 1f;
		sprite.a = 1f;
		sprite.rotation = 0f;
		sprite.scaleX = 1f;
		sprite.scaleY = 1f;
		e.addComponent(sprite);
		
		e.addComponent(new Cursor());
		
		return e;
	}
	
	public static Entity createDamageLabel(World world, String label, float x, float y) {
		Entity e = world.createEntity();
		
		e.addComponent(new MapPosition(x,y));
		e.addComponent(new FadingMessage(label,1.2f,0f,1.3f));
		
		return e;
	}
	
	public static Entity createNPC2(World world, int x, int y, GameMap gameMap) {
		Entity e = createNPC(world, x, y, gameMap);
		world.getManager(PlayerManager2.class).setPlayer(e, Players.Computer);
		
		e.addComponent(new AI());
		
		return e;
	}
	
	public static class Players {
		public static final String Human = "HUMAN_TEAM";
		public static final String Computer = "COMPUTER_TEAM";
		public static final String Blue = "BLUE_TEAM";
		public static final String Red = "RED_TEAM";
		public static final String Green = "GREEN_TEAM";
		public static final String Yellow = "YELLOW_TEAM";
		public static final String Purple = "PURPLE_TEAM";
		public static final String Teal = "TEAL_TEAM";
	}
}
