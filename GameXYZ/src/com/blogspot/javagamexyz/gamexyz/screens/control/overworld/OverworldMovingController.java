package com.blogspot.javagamexyz.gamexyz.screens.control.overworld;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.blogspot.javagamexyz.gamexyz.components.Movable;
import com.blogspot.javagamexyz.gamexyz.components.Movement;
import com.blogspot.javagamexyz.gamexyz.custom.Pair;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;
import com.blogspot.javagamexyz.gamexyz.maps.MapTools;
import com.blogspot.javagamexyz.gamexyz.screens.OverworldScreen;

public class OverworldMovingController implements InputProcessor {
	private OrthographicCamera camera;
	private World world;
	private GameMap gameMap;
	private OverworldScreen screen;

	public OverworldMovingController(OrthographicCamera camera, World world, GameMap gameMap, OverworldScreen screen) {
		this.camera = camera;
		this.world = world;
		this.gameMap = gameMap;
		this.screen = screen;
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
		Pair coords = MapTools.window2world(Gdx.input.getX(), Gdx.input.getY(), camera);
		
		// Did they click within the movable range?
		if (screen.highlightedCells.contains(coords, false)) {
			screen.activeEntityCell = coords;
			Entity e = world.getEntity(screen.selectedEntity);
			Movable movable = e.getComponent(Movable.class);
			Pair p = gameMap.getCoordinatesFor(screen.selectedEntity);
			e.addComponent(new Movement(gameMap.pathFinder.findPath(p.x, p.y, coords.x, coords.y, movable, false)));
			e.changedInWorld();
			
			// ** We no longer do this here, but rather directly in the MovementSystem
			// ** so that when the AI wants to move somewhere, the camera tracks it just
			// ** as well
			// // // Move the camera to the target cell
			// // // screen.cameraMovementSystem.move(coords.x,coords.y);
			
			// Tell the screen that the entity has already moved this turn
			screen.moved = true;
		}

		// Wherever they clicked, they are now done with the "moving" aspect of things
		screen.highlightedCells.clear();
		screen.renderHighlighter = false;
		screen.selectedEntity = -1;
		screen.removeInputSystems(this);
		
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
