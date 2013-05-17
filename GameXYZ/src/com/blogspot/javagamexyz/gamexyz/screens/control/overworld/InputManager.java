package com.blogspot.javagamexyz.gamexyz.screens.control.overworld;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.blogspot.javagamexyz.gamexyz.abilities.Action2;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;
import com.blogspot.javagamexyz.gamexyz.screens.OverworldScreen;
import com.blogspot.javagamexyz.gamexyz.ui.MenuBuilder;

public class InputManager {

	private OverworldScreen screen;
	public Stage stage;
	
	public OverworldSelectorController select;
	public OverworldDragController drag;
	public OverworldMovingController move;
	public OverworldAttackController attack;
	
	public MenuBuilder menuBuilder;
	public MenuProcessor menuProcessor;
	
	private InputMultiplexer manager, previousManager;
	
	public int selectedEntity;
	public Action2 selectedAction;
	
	public InputManager(OrthographicCamera camera, World world, OverworldScreen screen, Stage stage, GameMap gameMap) {
		this.screen = screen;
		this.stage = stage;
		
		selectedEntity = -1;
		
		select = new OverworldSelectorController(camera, world, gameMap, screen, this);
		move = new OverworldMovingController(camera, world, gameMap, screen, this);
		drag = new OverworldDragController(camera);
		attack = new OverworldAttackController(camera, world, gameMap, screen, this);
		
		manager = new InputMultiplexer(stage, drag, select);
		previousManager = new InputMultiplexer();
		
		menuProcessor = new MenuProcessor(screen, this, world, gameMap, stage);
		menuBuilder = new MenuBuilder(this, menuProcessor, stage);
		
		Gdx.input.setInputProcessor(manager);
	}
	
	
	public void setDefault() {
		previousManager = manager;
		manager = new InputMultiplexer(stage, drag, select);
	}
	
	public void setEnabled(boolean enabled) {
		if (enabled) Gdx.input.setInputProcessor(manager);
		else Gdx.input.setInputProcessor(null);
	}
	
	
	
	
	
	public void appendInputSystems(InputProcessor... processors) {
		previousManager.setProcessors(manager.getProcessors());
		for (int i = 0; i < processors.length; i++) manager.addProcessor(processors[i]);
	}

	public void setInputSystems(InputProcessor... processors) {
		previousManager.setProcessors(manager.getProcessors());
		manager = new InputMultiplexer(processors);
		Gdx.input.setInputProcessor(manager);
	}
	
	public void prependInputSystems(InputProcessor... processors) {
		previousManager.setProcessors(manager.getProcessors());
		InputMultiplexer newMultiplexer = new InputMultiplexer();
		
		for (int i = 0; i < processors.length; i++) {
			newMultiplexer.addProcessor(processors[i]);
		}
		
		for (InputProcessor p : manager.getProcessors()) {
			newMultiplexer.addProcessor(p);
		}
		
		manager = newMultiplexer;
		Gdx.input.setInputProcessor(manager);
	}
	
	public void removeInputSystems(InputProcessor... processors) {
		for (int i = 0; i < processors.length; i++) {
			manager.removeProcessor(processors[i]);
		}
	}
	
	public boolean canMove() {
		return !screen.moved;
	}
	
	public boolean canAct() {
		return !screen.attacked;
	}

	
}
