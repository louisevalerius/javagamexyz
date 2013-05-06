package com.blogspot.javagamexyz.gamexyz.screens.control.overworld;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.blogspot.javagamexyz.gamexyz.components.Damage;
import com.blogspot.javagamexyz.gamexyz.custom.Pair;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;
import com.blogspot.javagamexyz.gamexyz.maps.MapTools;
import com.blogspot.javagamexyz.gamexyz.screens.OverworldScreen;

public class OverworldAttackController implements InputProcessor {
	
	private OrthographicCamera camera;
	private World world;
	private GameMap gameMap;
	private OverworldScreen screen;
	
	public OverworldAttackController(OrthographicCamera camera, World world, GameMap gameMap, OverworldScreen screen) {
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
		int entityId = gameMap.getEntityAt(coords.x, coords.y);
		
		// Did they click within the attackable range, and a real entity?
		if (screen.highlightedCells.contains(coords, false) && entityId > -1) {
			
			//Entity source = world.getEntity(screen.selectedEntity);
			Entity target = world.getEntity(entityId);
			
			//Stats stats = source.getComponent(Stats.class);
			Damage damage = new Damage(10);
			
			target.addComponent(damage);
			target.changedInWorld();
			
			// Tell the screen that this entity has attacked this turn
			screen.highlightAttackRange();
		}

		// Wherever they clicked, they are now done with the "attacking" aspect of things
		screen.highlightedCells.clear();
		screen.renderHighlighter = false;
		screen.selectedEntity = -1;
		screen.removeInputSystems(this);
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
