package com.blogspot.javagamexyz.gamexyz.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.blogspot.javagamexyz.gamexyz.EntityFactory;
import com.blogspot.javagamexyz.gamexyz.components.Damage;
import com.blogspot.javagamexyz.gamexyz.components.MapPosition;
import com.blogspot.javagamexyz.gamexyz.components.Stats;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;
import com.blogspot.javagamexyz.gamexyz.utils.MyMath;

public class DamageSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Damage> dm;
	@Mapper ComponentMapper<Stats> sm;
	@Mapper ComponentMapper<MapPosition> mpm;
	
	private GameMap gameMap;
	
	@SuppressWarnings("unchecked")
	public DamageSystem(GameMap gameMap) {
		super(Aspect.getAspectForAll(Damage.class, Stats.class));
		this.gameMap = gameMap;
	}

	@Override
	protected void process(Entity e) {
		Damage damage = dm.get(e);
		Stats stats = sm.get(e);
		MapPosition position = mpm.getSafe(e); // Useful for displaying damage on screen 
		
		if (damage.damage > 0) { // Successful attack
		
			// Update the target's health
			stats.health -= damage.damage;
		
			// Display a message
			if (position != null) {
				EntityFactory.createDamageLabel(world, ""+damage.damage, position.x, position.y).addToWorld();
			}
		}
		
		else if (damage.damage < 0) { // cure
			int cureAmt = MyMath.min(-1*damage.damage,stats.maxHealth-stats.health);
			stats.health += cureAmt;
			if (position != null) {
				EntityFactory.createDamageLabel(world, "+"+cureAmt, position.x, position.y).addToWorld();
			}
		}
	
		else {	// Otherwise they missed			
			// Create a damage label of "MISS"
			if (position != null) {
				EntityFactory.createDamageLabel(world, "MISS", position.x, position.y).addToWorld();
			}
		}
		
		// We've processed the damage, now it's done
		e.removeComponent(damage);
		e.changedInWorld();
	}
	
	@Override
	protected void removed(Entity e) {
		// This is called after the damage gets removed
		// We want to see if the target died in the process
		Stats stats = sm.get(e);
		if (stats.health <= 0) {
			// If so, it's toast!
			gameMap.removeEntity(e.getId());
			world.deleteEntity(e); 
		}
	}
	
	@Override
	protected boolean checkProcessing() {
		return true;
	}

}
