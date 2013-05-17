package com.blogspot.javagamexyz.gamexyz.screens;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;

public class BreakArtemis {

	private World world;
	
	public BreakArtemis() {
		world = new World();
		
		for (int i = 0; i < 100; i++) {
			Entity e = world.createEntity();
			if (i == 0) e.addComponent(new A());
			e.addToWorld();
		}
		
		world.initialize();
		world.process();
		
		for (int i = 0; i < 100; i++) {
			Entity e = world.getEntity(i);
			A component = e.getComponent(A.class);
			if (component == null) System.out.println(i);
		}
	}
	
	public static void main(String[] args) {
		new BreakArtemis();
	}
	
	private class A extends Component {}
}