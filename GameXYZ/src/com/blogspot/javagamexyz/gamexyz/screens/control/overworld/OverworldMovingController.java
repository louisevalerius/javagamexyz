package com.blogspot.javagamexyz.gamexyz.screens.control.overworld;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.blogspot.javagamexyz.gamexyz.components.MapPosition;
import com.blogspot.javagamexyz.gamexyz.components.Movable;
import com.blogspot.javagamexyz.gamexyz.components.Movement;
import com.blogspot.javagamexyz.gamexyz.components.Sprite;
import com.blogspot.javagamexyz.gamexyz.custom.Pair;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;
import com.blogspot.javagamexyz.gamexyz.maps.MapTools;
import com.blogspot.javagamexyz.gamexyz.screens.OverworldScreen;

public class OverworldMovingController implements InputProcessor {
	private OrthographicCamera camera;
	private World world;
	private GameMap gameMap;
	private OverworldScreen screen;
	private InputManager inputManager;

	public OverworldMovingController(OrthographicCamera camera, World world, GameMap gameMap, OverworldScreen screen, InputManager inputManager) {
		this.camera = camera;
		this.world = world;
		this.gameMap = gameMap;
		this.screen = screen;
		this.inputManager = inputManager;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		final Pair coords = MapTools.window2world(Gdx.input.getX(), Gdx.input.getY(), camera);
		
		// Did they click within the movable range?
		if (screen.highlightedCells.contains(coords, false)) {
			
			Entity e = world.getEntity(screen.activeEntity);
			Sprite sprite = e.getComponent(Sprite.class);
			// Put a "ghost" entity there
			final Entity ghost = world.createEntity();
			Sprite sprite_ghost = new Sprite(sprite.name);
			sprite_ghost.a = 0.5f;
			sprite_ghost.r = 1;
			sprite_ghost.g = 1;
			sprite_ghost.b = 1;
			ghost.addComponent(sprite_ghost);
			MapPosition pos = new MapPosition(coords.x, coords.y);
			ghost.addComponent(pos);
			ghost.addToWorld();
			
			// Build a confirm dialog box
			float w = 200f;
			float h = 100f;
			float x = (inputManager.stage.getWidth() - w)/2f;
			float y = (inputManager.stage.getHeight() - h)/2f;

			// YES button
			ChangeListener confirm = new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					
					// Add the Movement component to this entity
					Entity e = world.getEntity(screen.activeEntity);
					Movable movable = e.getComponent(Movable.class);
					Pair p = screen.activeEntityCell;
					e.addComponent(new Movement(gameMap.pathFinder.findPath(p.x, p.y, coords.x, coords.y, movable, false)));
					e.changedInWorld();
					
					// Update this entity's location for the screen
					screen.activeEntityCell = coords;
					
					// Tell the screen that the entity has already moved this turn
					screen.moved = true;
					
					// Clear the stage
					inputManager.stage.clear();
					
					// Clear the ghost
					ghost.deleteFromWorld();
				}
			};
			
			// NO button
			ChangeListener decline = new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					// Process moving
					inputManager.menuProcessor.move();
					// Clear the ghost
					ghost.deleteFromWorld();
				}
			};
			
			// Build the box
			inputManager.menuBuilder.buildDialog("", "Confirm move?", x, y, w, h,
					inputManager.menuBuilder.getTextButton("Yes", confirm),
					inputManager.menuBuilder.getTextButton("No", decline));
			
			
		}

		// Wherever they clicked, they are now done with the "moving" aspect of things
		inputManager.menuBuilder.setMenusVisible(true);
		screen.highlightedCells.clear();
		screen.renderHighlighter = false;
		inputManager.selectedEntity = -1;
		inputManager.removeInputSystems(this);
		
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
}
