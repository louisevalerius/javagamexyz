package com.blogspot.javagamexyz.gamexyz.screens.control.overworld;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.blogspot.javagamexyz.gamexyz.abilities.Action2;
import com.blogspot.javagamexyz.gamexyz.custom.Pair;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;
import com.blogspot.javagamexyz.gamexyz.maps.MapTools;
import com.blogspot.javagamexyz.gamexyz.screens.OverworldScreen;

public class OverworldAttackController implements InputProcessor {
	
	private OrthographicCamera camera;
	private World world;
	private GameMap gameMap;
	private OverworldScreen screen;
	private InputManager inputManager;
	
	public OverworldAttackController(OrthographicCamera camera, World world, GameMap gameMap, OverworldScreen screen, InputManager inputManager) {
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
		Pair coords = MapTools.window2world(Gdx.input.getX(), Gdx.input.getY(), camera);
		final Action2 action = inputManager.selectedAction;
		
		// If they actually clicked in range
		if (screen.highlightedCells.contains(coords, false)) {
			
			// Get the source and targets of the action
			final Entity source = world.getEntity(screen.activeEntity);
			final Array<Entity> targets = new Array<Entity>();
			for (Pair cell : action.fieldCalculator.getField(coords, action)) {
				int id = gameMap.getEntityAt(cell.x, cell.y);
				if (id > -1) targets.add(world.getEntity(id));
			}
			
			// If the action actually has some targets
			if (targets.size > 0){
				
				// Highlight the target field
				screen.highlightedCells = action.fieldCalculator.getField(coords, action);
				
				// Build a confirm dialog box
				float w = 200f;
				float h = 100f;
				float x = (screen.stage.getWidth() - w)/2f;
				float y = (screen.stage.getHeight() - h)/2f;
				ChangeListener confirm = new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						action.actionProcessor.process(source, targets, action);
						screen.attacked = true;
						inputManager.stage.clear();
						inputManager.selectedEntity = -1;
						screen.highlightedCells.clear();
						screen.renderHighlighter = false;
					}
				};
				ChangeListener decline = new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						inputManager.menuBuilder.setMenusVisible(true);
						screen.highlightedCells.clear();
						screen.renderHighlighter = false;
					}
				};
				inputManager.menuBuilder.buildDialog("","Confirm action?",x,y,w,h,
						inputManager.menuBuilder.getTextButton("Yes", confirm),
						inputManager.menuBuilder.getTextButton("No", decline));
			}
			
			// They clicked within range, but with no targets
			else {
				inputManager.menuBuilder.setMenusVisible(true);
				screen.highlightedCells.clear();
				screen.renderHighlighter = false;
			}

		}
		
		// If they clicked out of the range, bring the menu back up
		else {
			inputManager.menuBuilder.setMenusVisible(true);
			screen.highlightedCells.clear();
			screen.renderHighlighter = false;
		}
		
		// Wherever they clicked, they are now done with the "attacking" aspect of things
		inputManager.removeInputSystems(this);
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
