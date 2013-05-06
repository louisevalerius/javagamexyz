package com.blogspot.javagamexyz.gamexyz.screens.control.overworld;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.blogspot.javagamexyz.gamexyz.EntityFactory;
import com.blogspot.javagamexyz.gamexyz.components.MapPosition;
import com.blogspot.javagamexyz.gamexyz.custom.Pair;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;
import com.blogspot.javagamexyz.gamexyz.maps.MapTools;
import com.blogspot.javagamexyz.gamexyz.screens.OverworldScreen;
import com.blogspot.javagamexyz.gamexyz.ui.MenuBuilder;

public class OverworldSelectorController implements InputProcessor {
	
	private OrthographicCamera camera;
	private World world;
	private GameMap gameMap;
	
	// We need a copy of the screen implementing this controller (which has a copy of
	// the Game delegating to it) so we can change screens based on users making selections
	private OverworldScreen screen;
	
	public OverworldSelectorController(OrthographicCamera camera, World world, GameMap gameMap, OverworldScreen screen) {
		this.camera = camera;
		this.world = world;
		this.gameMap = gameMap;
		this.screen = screen;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		Entity e = world.getEntity(screen.cursor);
		MapPosition mp = e.getComponent(MapPosition.class);
		if (keycode == 19) mp.y+=1; // UP
		else if (keycode == 20) mp.y-=1; // DOWN
		else if (keycode == 21) { // LEFT
			if (mp.x % 2 == 0) mp.y-=1;
			mp.x-=1;
		}
		else if (keycode == 22) { // RIGHT
			if (mp.x %2 == 1) mp.y+=1;
			mp.x+=1;
		}
		
		else if (keycode == 62) { // SPACE
			screen.cameraMovementSystem.move(screen.activeEntityCell.x, screen.activeEntityCell.y);
		}
		
		return true;
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
		
		// Get the coordinates they clicked on
		Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
		Pair coords = MapTools.window2world(mousePosition.x, mousePosition.y, camera);
			
		// Check the entityID of the cell they click on
		//if (coords.x < 0 || coords.y < 0 || coords.x > gameMap.width-1 || coords.y > gameMap.height-1) return false;
		int entityId = gameMap.getEntityAt(coords.x, coords.y);
		
		// If it's an actual entity (not empty) then "select" it (unless it's already selected)		
		if ((entityId > -1) && (entityId != screen.selectedEntity) && (entityId == screen.activeEntity)) {
			
			// Now select the current entity
			screen.selectedEntity = entityId;
			EntityFactory.createClick(world, coords.x, coords.y, 0.1f, 4f).addToWorld();
			
			// Put up a menu for the selected entity
			screen.handleStage = true; 
			screen.stage.clear();
			screen.stage.addActor(MenuBuilder.buildMenu(screen));
			screen.prependInputSystems(screen.stage);
			
			// Make sure we drop any of the highlighted cells
			screen.renderMovementRange = false;
			screen.renderAttackRange = false;
			screen.highlightedCells = null;
	
			return true;
		}
		
		// If they didn't click on someone, we didn't process it
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
