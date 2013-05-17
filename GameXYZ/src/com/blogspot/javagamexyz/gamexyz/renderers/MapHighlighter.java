package com.blogspot.javagamexyz.gamexyz.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.blogspot.javagamexyz.gamexyz.custom.FloatPair;
import com.blogspot.javagamexyz.gamexyz.custom.Pair;
import com.blogspot.javagamexyz.gamexyz.maps.MapTools;

public class MapHighlighter extends AbstractRenderer {
	
	private Texture highlight;
	private float t;
	private float r, g, b, a;
	
	public MapHighlighter(OrthographicCamera camera, SpriteBatch batch) {
		super(camera,batch);
		highlight = new Texture(Gdx.files.internal("textures/misc/hex_blank.png"));
		t = 0;
	}
		
	public void render(Array<Pair> cells) {
		if (cells == null || cells.size < 1) return;
		
		// Get bottom left and top right coordinates of camera viewport and convert
		// into grid coordinates for the map
		int x0 = MathUtils.floor(camera.frustum.planePoints[0].x / (float)MapTools.col_multiple) - 1;
		int y0 = MathUtils.floor(camera.frustum.planePoints[0].y / (float)MapTools.row_multiple);
		int x1 = MathUtils.floor(camera.frustum.planePoints[2].x / (float)MapTools.col_multiple);
		int y1 = MathUtils.floor(camera.frustum.planePoints[2].y / (float)MapTools.row_multiple);

		
		begin();
		batch.setColor(r,g,b,a/8*(7+MathUtils.cos(8*t)));
		
		for (Pair cell : cells) {
			if (cell.x < x0 || cell.x > x1 || cell.y < y0 || cell.y > y1) continue;
			FloatPair coords = MapTools.world2window(cell.x, cell.y);
			batch.draw(highlight, coords.x-highlight.getWidth()/2, coords.y-highlight.getHeight()/2);
		}
		
		t+=Gdx.graphics.getDeltaTime();
	
		end();
	}
	
	public void setColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
}
