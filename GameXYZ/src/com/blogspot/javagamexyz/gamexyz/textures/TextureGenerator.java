package com.blogspot.javagamexyz.gamexyz.textures;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

public class TextureGenerator{

	int width, height;
	
	public TextureGenerator(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void generateTexture(int n, boolean weighted, boolean repeat) {
		width = 128;
		height = 128;
		
		Pixmap pixmap;
		if (repeat) pixmap = new Pixmap(2*width, 2*height, Pixmap.Format.RGBA8888);
		else pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		FileHandle file;
		Triplet[][] baseMap = new Triplet[width][height]; 
		Triplet baseColor = new Triplet(1f, 0.916f, 0.572f);
		
		float minDark = 0.6f;
		float md = minDark / (1 - minDark);
		
		Noise noiseGenerator = new Noise(width, height, weighted);
		
		
		// Get the base noise map - colored
		Float[][] noise = new Float[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				float gray = noiseGenerator.turbulence(x, y, 6);
				noise[x][y] = gray;
				gray = (md + gray) / (md + 1);
				baseMap[x][y] = new Triplet(gray*baseColor.r, gray*baseColor.g, gray*baseColor.b);
				fillPixmap(pixmap, x, y, baseMap[x][y].r, baseMap[x][y].g, baseMap[x][y].b, repeat);
			}
		}
		file = new FileHandle("../textures/"+n+"base.png");
		PixmapIO.writePNG(file, pixmap);
		
		// Get the edge map
		float[][] edges = EdgeDetector.getEdges(noise);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				fillPixmap(pixmap, x, y, edges[x][y], edges[x][y], edges[x][y], repeat);
			}
		}
		file = new FileHandle("../textures/"+n+"edge.png");
		PixmapIO.writePNG(file, pixmap);
		
		// Add the edges to the original
		float damper = 10f;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				float red = baseMap[x][y].r + edges[x][y]/damper;
				float green = baseMap[x][y].g + edges[x][y]/damper;
				float blue = baseMap[x][y].b + edges[x][y]/damper;
				
				if (red > 1) red = 1;
				if (green > 1) green = 1;
				if (blue > 1) blue = 1; 
				
				fillPixmap(pixmap, x, y, red, green, blue, repeat);
			}	
		}
		
		file = new FileHandle("../textures/" + n + ".png");
		PixmapIO.writePNG(file, pixmap);
	}
	
	private void fillPixmap(Pixmap pixmap, int x, int y, float r, float g, float b, boolean repeated) {
		int c = Color.rgba8888(new Color(r,g,b,1));
		pixmap.drawPixel(x, y, c);
		if (!repeated) return;
		pixmap.drawPixel(width+x, y, c);
		pixmap.drawPixel(x, height+y, c);
		pixmap.drawPixel(width+x, height+y, c);
	}
	
	private static class Triplet {
		public float r, g, b;
		public Triplet(float r, float g, float b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}
	}

}
