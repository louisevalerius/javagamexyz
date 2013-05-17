package com.blogspot.javagamexyz.gamexyz.screens.control.overworld;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.blogspot.javagamexyz.gamexyz.abilities.Action2;
import com.blogspot.javagamexyz.gamexyz.components.Movable;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;
import com.blogspot.javagamexyz.gamexyz.screens.OverworldScreen;

public class MenuProcessor {
	
	private OverworldScreen screen;
	private InputManager inputManager;
	private World world;
	private GameMap gameMap;
	Stage stage;
	
	public MenuProcessor(OverworldScreen screen, InputManager inputManager, World world, GameMap gameMap, Stage stage) {
		this.screen = screen;
		this.inputManager = inputManager;
		this.world = world;
		this.gameMap = gameMap;
		this.stage = stage;
	}

	public void move() {
		if (screen.moved) return;
		
		Entity e = world.getEntity(screen.activeEntity);
		Movable movable = e.getComponent(Movable.class);
		if (movable == null) return;
		
		inputManager.appendInputSystems(inputManager.move);
		
		screen.highlightedCells = gameMap.pathFinder.getReachableCells(screen.activeEntityCell.x, screen.activeEntityCell.y, movable);
		screen.renderHighlighter = true;
		screen.highlightMovementRange();
		inputManager.menuBuilder.setMenusVisible(false);
	}
	
	public void action(Action2 action) {
		if (screen.attacked) return;
		inputManager.selectedAction = action;
		inputManager.setInputSystems(stage,inputManager.drag,inputManager.attack,inputManager.select);
		
		screen.highlightedCells = action.rangeCalculator.getRange(screen.activeEntityCell, action);
		screen.highlightAttackRange();
		inputManager.menuBuilder.setMenusVisible(false);
		
		// DONE
		// AttackController - Add stage.clear() to yes button / add setMenusVisible(true) to "no" button
	}
	
	// public void item(Item item)
	public void item() {
		
	}
	
	public void selectWait() {
		inputManager.setDefault();
		screen.processTurn();
		stage.clear();
		inputManager.selectedEntity = -1;
		
		screen.moved = screen.attacked = false;
	}
	
}
