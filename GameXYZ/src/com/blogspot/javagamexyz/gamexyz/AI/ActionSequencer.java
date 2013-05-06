package com.blogspot.javagamexyz.gamexyz.AI;

import com.artemis.Entity;
import com.badlogic.gdx.utils.Array;
import com.blogspot.javagamexyz.gamexyz.components.Movable;
import com.blogspot.javagamexyz.gamexyz.components.Movement;
import com.blogspot.javagamexyz.gamexyz.custom.Pair;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;
import com.blogspot.javagamexyz.gamexyz.maps.MapTools;
import com.blogspot.javagamexyz.gamexyz.screens.OverworldScreen;

public class ActionSequencer {
	
	Plan plan;
	Entity e;
	
	private int step;
	private float m_t;
	private float a_t;
	
	private float[] moveTimer;
	private float[] actTimer;
	
	public ActionSequencer(Plan plan, Entity e) {
		this.plan = plan;
		this.e = e;
		
		float t1,t2,t3,t4;
		
		step = 0;
	
		// Move timing constants
		m_t = 0.75f;	// Time to wait upon focus, before showing movement range
		t1 = 1f;
		t2 = 0.5f;
		t3 = 0.05f;
		t4 = 0.25f;
		moveTimer = new float[]{t1, t2, t3, t4};
		
		// Act timing constants
		a_t = 0.75f;	// Time to wait upon focus, before showing action range
		t1 = 1f;		// Highlight attackable range
		t2 = 0.6f;		// Highlight target cell
		t3 = 0.75f;		// Linger to watch damage hit
		actTimer = new float[]{t1, t2, t3};
	}
	
	public void move(GameMap gameMap, OverworldScreen screen, float time) {
		
		if (time < m_t) return;
		
		// Step 1 - Highlight the reachable cells
		if (step == 0) {
			Movable movable = e.getComponent(Movable.class);
			
			Pair pos = gameMap.getCoordinatesFor(e.getId());
			
			Array<Pair> reachableCells = gameMap.pathFinder.getReachableCells(pos.x, pos.y, movable);
			screen.highlightedCells = reachableCells;
			screen.highlightMovementRange();
			
			m_t += moveTimer[step];
			step++;
			
			return;
		}
		
		// Step 2 - Highlight the cell we will move to
		else if (step == 1) {
			screen.highlightedCells.clear();
			screen.setHighlightColor(0.05f, 0.05f, 0.2f, 0.8f);
			screen.highlightedCells.add(plan.moveTarget);
			
			m_t += moveTimer[step];
			step++;
			
			return;
		}
		
		// Step 3 - Begin moving to target cell
		else if (step == 2) {
			Movable movable = e.getComponent(Movable.class);
			
			Pair pos = gameMap.getCoordinatesFor(e.getId());
			
			e.addComponent(new Movement(gameMap.pathFinder.findPath(pos.x, pos.y, plan.moveTarget.x, plan.moveTarget.y, movable, false)));
			e.changedInWorld();
			
			m_t += moveTimer[step];
			step++;
			
			return;
		}
		
		// Step 4 - We have arrived (plus a brief waiting period), unhighlight the cell
		else if (step == 3) {
			screen.renderHighlighter = false;
			
			m_t += moveTimer[step];
			step++;
			
			return;
		}
		
		// Finished: Tell the plan that the "move" is done 
		else {
			plan.moveDone = true;
			m_t = 0;
			step = 0;
		}
		
	}
	
	public void act(GameMap gameMap, OverworldScreen screen, float time) {
		if (plan.action == null) {
			plan.actionDone = true;
			return;
		}
		if (time < a_t) return;
		
		// Step 1 - Highlight attackable range
		if (step == 0) {
			Pair pos = gameMap.getCoordinatesFor(e.getId());
			screen.highlightedCells = MapTools.getNeighbors(pos.x, pos.y, plan.action.range);
			screen.highlightAttackRange();
			
			a_t += actTimer[step];
			step++;
		}
		
		// Step 2 - Highlight the target cell (the whole field)
		else if (step == 1) {
			screen.highlightedCells = MapTools.getNeighbors(plan.actionTarget.x, plan.actionTarget.y, plan.action.field-1);
			screen.highlightedCells.add(plan.actionTarget);
			screen.setHighlightColor(0.2f, 0.05f, 0.05f, 0.6f);
			
			a_t += actTimer[step];
			step++;
		}
		
		// Step 3 - Execute Action and unhighlight target cells
		else if (step == 2) {
			plan.action.actionProcessor.process(e, plan.targetEntities, plan.action);
			screen.renderHighlighter = false;
			
			a_t += actTimer[step];
			step++;
		}
		
		/*
		// Step 3 - Add damage and unhighlight target cells
		else if (step == 2) {
			int entityId;
			Array<Pair> field = MapTools.getNeighbors(plan.actionTarget.x, plan.actionTarget.y, plan.action.field-1);
			field.add(plan.actionTarget);
			for (Pair target : field) {
				entityId = gameMap.getEntityAt(target.x, target.y);
				screen.addComponent(damage, entityId);
			}
			
			screen.renderHighlighter = false;
			
			a_t += actTimer[step];
			step++;
		}
		*/
		
		// Finished
		else {
			plan.actionDone = true;
			a_t = 0;
			step = 0;
		}
	}
}
