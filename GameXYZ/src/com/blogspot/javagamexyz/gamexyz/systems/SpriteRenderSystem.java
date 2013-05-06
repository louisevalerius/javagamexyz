package com.blogspot.javagamexyz.gamexyz.systems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.blogspot.javagamexyz.gamexyz.components.MapPosition;
import com.blogspot.javagamexyz.gamexyz.components.Sprite;
import com.blogspot.javagamexyz.gamexyz.components.SpriteAnimation;
import com.blogspot.javagamexyz.gamexyz.custom.Animation;
import com.blogspot.javagamexyz.gamexyz.custom.FloatPair;
import com.blogspot.javagamexyz.gamexyz.maps.MapTools;

public class SpriteRenderSystem extends EntitySystem {
	@Mapper ComponentMapper<MapPosition> pm;
	@Mapper ComponentMapper<Sprite> sm;
	@Mapper ComponentMapper<SpriteAnimation> sam;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private TextureAtlas atlas;
	
	//private; 
	private List<Entity> sortedEntities;
	
	@SuppressWarnings("unchecked")
	public SpriteRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(MapPosition.class, Sprite.class));
		this.camera = camera;
	}
	
	@SuppressWarnings("unchecked")
	public SpriteRenderSystem(OrthographicCamera camera, SpriteBatch batch) {
		super(Aspect.getAspectForAll(MapPosition.class, Sprite.class));
		this.camera = camera;
		this.batch = batch;
	}
	
	@Override
	protected void initialize() {
		//batch = new SpriteBatch();
		
		atlas = new TextureAtlas(Gdx.files.internal("textures/characters.atlas"),Gdx.files.internal("textures"));
		sortedEntities = new ArrayList<Entity>();
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for (Entity e : sortedEntities) {
			process(e);
		}
	}
	
	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}
	
	protected void process(Entity e) {
		if (pm.has(e)) {
			MapPosition position = pm.getSafe(e);
			
			// Get bottom left and top right coordinates of camera viewport and convert
			// into grid coordinates for the map
			int x0 = MathUtils.floor(camera.frustum.planePoints[0].x / (float)MapTools.col_multiple) - 1;
			int y0 = MathUtils.floor(camera.frustum.planePoints[0].y / (float)MapTools.row_multiple) - 1;
			int x1 = MathUtils.floor(camera.frustum.planePoints[2].x / (float)MapTools.col_multiple) + 2;
			int y1 = MathUtils.floor(camera.frustum.planePoints[2].y / (float)MapTools.row_multiple) + 1;
			// If sprite is off-screen, don't bother drawing it!
			if (position.x < x0 || position.x > x1 || position.y < y0 || position.y > y1) return;
			
			Sprite sprite = sm.get(e);
			
			TextureRegion spriteRegion = sprite.region;
			batch.setColor(sprite.r, sprite.g, sprite.b, sprite.a);

			int width = spriteRegion.getRegionWidth();
			int height = spriteRegion.getRegionHeight();
			
			sprite.region.setRegion(sprite.x, sprite.y, width, height);

			FloatPair drawPosition = MapTools.world2window(position.x, position.y);
			float posX = drawPosition.x - (spriteRegion.getRegionWidth() / 2 * sprite.scaleX);
			float posY = drawPosition.y - (spriteRegion.getRegionHeight() / 2 * sprite.scaleY);
			
			batch.draw(spriteRegion, posX, posY, 0, 0, spriteRegion.getRegionWidth(), spriteRegion.getRegionHeight(), sprite.scaleX, sprite.scaleY, sprite.rotation);
		}
	}
	
	@Override
	protected void end() {
		batch.end();
	}
	
	@Override
	protected void inserted(Entity e) {
		Sprite sprite = sm.get(e);
		sortedEntities.add(e);
		TextureRegion reg = atlas.findRegion(sprite.name);
		sprite.region = reg;
		sprite.x = reg.getRegionX();
		sprite.y = reg.getRegionY();
		sprite.width = reg.getRegionWidth();
		sprite.height = reg.getRegionHeight();
		if (sam.has(e)) {
			SpriteAnimation anim = sam.getSafe(e);
			anim.animation = new Animation( anim.frameDuration, atlas.findRegions(sprite.name), anim.playMode);
		}
		
		Collections.sort(sortedEntities, new Comparator<Entity>() {
			@Override
			public int compare(Entity e1, Entity e2) {
				Sprite s1 = sm.get(e1);
				Sprite s2 = sm.get(e2);
				return s1.layer.compareTo(s2.layer);
			}
		});
	}
	
	@Override
	protected void removed(Entity e) {
		sortedEntities.remove(e);
	}
}
