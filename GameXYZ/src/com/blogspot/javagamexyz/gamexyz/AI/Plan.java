package com.blogspot.javagamexyz.gamexyz.AI;

import com.artemis.Entity;
import com.badlogic.gdx.utils.Array;
import com.blogspot.javagamexyz.gamexyz.abilities.Action2;
import com.blogspot.javagamexyz.gamexyz.custom.Pair;


public class Plan implements Comparable<Plan> {
	
	public boolean moveFirst, moveDecided, moveDone, actionDone;
	public Action2 action;
	public Pair moveTarget, actionTarget;
	public float score;
	public Array<Entity> targetEntities;
	
	public Plan(boolean moveFirst, Pair moveTarget, Pair actionTarget, Action2 action, float score) {
		this.moveFirst = moveFirst;
		this.moveTarget = moveTarget;
		this.actionTarget = actionTarget;
		this.action = action;
		this.score = score;
		
		targetEntities = new Array<Entity>();
		
		moveDecided = moveDone = actionDone = false;
	}
	
	@Override
	public int compareTo(Plan p) {
		if (score < p.score) return 1; // p was better
		else if (score > p.score) return -1; // p was worse
		return 0; // they were equal
	}

}
