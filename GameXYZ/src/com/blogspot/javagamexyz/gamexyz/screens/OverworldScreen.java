package com.blogspot.javagamexyz.gamexyz.screens;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.blogspot.javagamexyz.gamexyz.EntityFactory;
import com.blogspot.javagamexyz.gamexyz.GameXYZ;
import com.blogspot.javagamexyz.gamexyz.components.Movable;
import com.blogspot.javagamexyz.gamexyz.custom.Pair;
import com.blogspot.javagamexyz.gamexyz.maps.GameMap;
import com.blogspot.javagamexyz.gamexyz.maps.MapTools;
import com.blogspot.javagamexyz.gamexyz.renderers.MapHighlighter;
import com.blogspot.javagamexyz.gamexyz.renderers.MapRenderer;
import com.blogspot.javagamexyz.gamexyz.screens.control.overworld.OverworldAttackController;
import com.blogspot.javagamexyz.gamexyz.screens.control.overworld.OverworldDragController;
import com.blogspot.javagamexyz.gamexyz.screens.control.overworld.OverworldMovingController;
import com.blogspot.javagamexyz.gamexyz.screens.control.overworld.OverworldSelectorController;
import com.blogspot.javagamexyz.gamexyz.systems.CameraMovementSystem;
import com.blogspot.javagamexyz.gamexyz.systems.DamageSystem;
import com.blogspot.javagamexyz.gamexyz.systems.FadingMessageRenderSystem;
import com.blogspot.javagamexyz.gamexyz.systems.HudRenderSystem;
import com.blogspot.javagamexyz.gamexyz.systems.MovementSystem;
import com.blogspot.javagamexyz.gamexyz.systems.SpriteRenderSystem;
import com.blogspot.javagamexyz.gamexyz.systems.TurnManagementSystem;

public class OverworldScreen extends AbstractScreen {
	
	public static GameMap gameMap;
	private OrthographicCamera hudCam;
	
	private SpriteRenderSystem spriteRenderSystem;
	private HudRenderSystem hudRenderSystem;
	private FadingMessageRenderSystem fadingMessageRenderSystem;
	private TurnManagementSystem turnManagementSystem;
	
	private MapRenderer mapRenderer;
	private MapHighlighter mapHighlighter;
	
	public int selectedEntity;
	public int activeEntity;
	public Pair activeEntityCell;
	public Array<Pair> highlightedCells;
	public boolean renderMap;
	public boolean renderMovementRange;
	public boolean renderAttackRange;
	
	public InputMultiplexer inputSystem;

	public Stage stage;
	public boolean handleStage;
	
	public int cursor;
	
	public OverworldDragController controllerDrag;
	public OverworldSelectorController controllerSelector;
	public OverworldMovingController controllerMoving;
	public OverworldAttackController controllerAttack;
	
	public Array<Integer> unitOrder;
	public boolean moved = false;
	public boolean attacked = false;
	
	public CameraMovementSystem cameraMovementSystem; 
	private boolean firstShow = true;
	
	public OverworldScreen(GameXYZ game, SpriteBatch batch, World world) {
		super(game,world,batch);

		cameraMovementSystem = new CameraMovementSystem(camera);
		activeEntityCell = new Pair(0,0);
    	gameMap  = new GameMap();
    	
    	unitOrder = new Array<Integer>();
    	setupWorld();
    	setupInputSystems();
    	fillWorldWithEntities();
    	
    	
    	selectedEntity = -1;

    	renderMap = true;
    	renderMovementRange = false;
    	renderAttackRange = false;
    	
    	stage = new Stage();
    	handleStage = false;
    	
    	
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		if (firstShow) {
			cameraMovementSystem.move(activeEntityCell.x, activeEntityCell.y);
			firstShow = false;
		}
		
		if (renderMap) {
			mapRenderer.render();
			spriteRenderSystem.process();
		}
		
		if (renderMovementRange) {
			mapHighlighter.render(highlightedCells,0f,0f,0.2f,0.3f);
		}
		
		fadingMessageRenderSystem.process();
		
		if (renderAttackRange) {
			mapHighlighter.render(highlightedCells,0.5f,0f,0f,0.3f);
		}
		
		if (handleStage) {
			stage.act(delta);
			stage.draw();
		}
		
		hudRenderSystem.process();
		
		cameraMovementSystem.process(delta);
	}

	@Override
	public void show() {
		
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		hudCam.setToOrtho(false, width, height);
		stage.setViewport(width, height, true);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		world.deleteSystem(hudRenderSystem);
		world.deleteSystem(spriteRenderSystem);
		world.deleteSystem(world.getSystem(MovementSystem.class));
	}
	
	public void selectedMove() {
		if (moved) return;
		removeInputSystems(stage);
		appendInputSystems(controllerMoving);
		Entity e = world.getEntity(selectedEntity);
		Movable movable = e.getComponent(Movable.class);
		highlightedCells = gameMap.pathFinder.getReachableCells(activeEntityCell.x, activeEntityCell.y, movable);
		renderMovementRange = true;
		handleStage = false;
		stage.clear();
	}
	
	public void selectedAttack() {
		if (attacked) return;
		setInputSystems(controllerDrag,controllerAttack,controllerSelector);
		highlightedCells = MapTools.getNeighbors(activeEntityCell.x, activeEntityCell.y);
		renderAttackRange = true;
		handleStage = false;
		stage.clear();
	}
	
	public void selectedWait() {
		setInputSystems(controllerDrag, controllerSelector);
		processTurn();
		handleStage = false;
		stage.clear();
		selectedEntity = -1;
		
		moved = attacked = false;
	}
	
	public void appendInputSystems(InputProcessor... processors) {
		for (int i = 0; i < processors.length; i++) inputSystem.addProcessor(processors[i]);
	}
	
	public void setInputSystems(InputProcessor... processors) {
		inputSystem = new InputMultiplexer(processors);
		Gdx.input.setInputProcessor(inputSystem);
	}
	
	public void prependInputSystems(InputProcessor... processors) {
		InputMultiplexer newMultiplexer = new InputMultiplexer();
		
		for (int i = 0; i < processors.length; i++) {
			newMultiplexer.addProcessor(processors[i]);
		}
		
		for (InputProcessor p : inputSystem.getProcessors()) {
			newMultiplexer.addProcessor(p);
		}
		
		inputSystem = newMultiplexer;
		Gdx.input.setInputProcessor(inputSystem);
	}
	
	public void removeInputSystems(InputProcessor... processors) {
		for (int i = 0; i < processors.length; i++) {
			inputSystem.removeProcessor(processors[i]);
		}
	}
	
	private void setupWorld() {
		hudCam = new OrthographicCamera();
    	
    	mapRenderer = new MapRenderer(camera,batch,gameMap.map);
    	mapHighlighter = new MapHighlighter(camera, batch);
    	
    	world.setSystem(new MovementSystem(gameMap));
    	world.setSystem(new DamageSystem(gameMap));
    	spriteRenderSystem = world.setSystem(new SpriteRenderSystem(camera,batch), true);
    	hudRenderSystem = world.setSystem(new HudRenderSystem(hudCam, batch),true);
    	fadingMessageRenderSystem = world.setSystem(new FadingMessageRenderSystem(camera,batch),true);
    	turnManagementSystem = world.setSystem(new TurnManagementSystem(unitOrder), true);
    	
    	
    	world.initialize();
    	System.out.println("The world is initialized");
    	
	}
	
	private void setupInputSystems() {
		controllerSelector = new OverworldSelectorController(camera,world,gameMap,this);
    	controllerMoving = new OverworldMovingController(camera,world,gameMap,this);
    	controllerDrag = new OverworldDragController(camera);
    	controllerAttack = new OverworldAttackController(camera,world,gameMap,this);
    	
    	setInputSystems(controllerDrag, controllerSelector);
	}
	
	private void fillWorldWithEntities() {
		int x, y;
    	for (int i=0; i<5; i++) {
    		do {
    			x = MathUtils.random(MapTools.width()-1);
    			y = MathUtils.random(MapTools.height()-1);
    		} while (gameMap.cellOccupied(x, y));
    		EntityFactory.createNPC(world,x,y,gameMap).addToWorld();
    	}
    	
    	Entity e = EntityFactory.createCursor(world);
    	cursor = e.getId();
    	e.addToWorld();
    	
    	// You have to process the world once to get all the entities loaded up with
    	// the "Stats" component - I'm not sure why, but if you don't, the bag of entities
    	// that turnManagementSystem gets is empty?
    	world.process();
    	// Running processTurn() once here initializes the unit order, and selects the first
    	// entity to go
    	processTurn();
	}
	
	public void processTurn() {
		turnManagementSystem.process();
		activeEntity = unitOrder.get(0);
		activeEntityCell = gameMap.getCoordinatesFor(activeEntity);
		if (activeEntityCell != null) cameraMovementSystem.move(activeEntityCell.x, activeEntityCell.y);
	}
}
