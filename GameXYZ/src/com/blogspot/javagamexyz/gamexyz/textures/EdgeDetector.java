package com.blogspot.javagamexyz.gamexyz.textures;


public class EdgeDetector {
	
	public static float truncate(float c) {
		if (c < 0) return 0f;
		if (c > 1) return 1f;
		return c;
	}
	
	public static float[][] getEdges(Float[][] picture) {
		float a = 0.5f;
		
		float[][] filterX = {	{ -a/4f, -a/2f, 0, a/2f, a/4f }, 	
								{ -a/2f,    -a, 0,    a, a/2f },
								{    -a,  -2*a, 0,  2*a,    a },
								{ -a/2f,    -a, 0,    a, a/2f },
								{ -a/4f, -a/2f, 0, a/2f, a/4f }};
		
		float[][] filterY = {	{  a/4f,  a/2f,    a,  a/2f,  a/4f },
								{  a/2f,     a,  2*a,     a,  a/2f },
								{     0,     0,    0,     0,     0 },
								{ -a/2f,    -a, -2*a,    -a, -a/2f },
								{ -a/4f, -a/2f,   -a, -a/2f, -a/4f }};
        
//		float[][] filterY = { { a,  2*a,  a },
//							  { 0,    0,  0 },
//							  {-a, -2*a, -a }};
		
		
		int w = picture.length;
		int h = picture[0].length;
		
		
		
		float[][] edges = new float[w][h];
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				
				float[][] local = new float[5][5];
				for (int i = 0; i < 5; i++) {
					for (int j = 0; j < 5; j++) {
						local[i][j] = picture[(x + i-1 + w) % w][(y + j-1 + h) % h];
					}
				}
				
				float edgeX = 0f;
				float edgeY = 0f;
				for (int i = 0; i < 5; i++) {
					for (int j = 0; j < 5; j++) {
						edgeX += filterX[i][j] * local[i][j];
						edgeY += filterY[i][j] * local[i][j];
					}
				}
				
				edges[x][y] = truncate((float)Math.sqrt(edgeX * edgeX + edgeY * edgeY));
			}
		}
		return edges;
	}
	
	public static void main(String[] args) {
		Float[][] test = new Float[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				test[i][j] = 0f;
			}
			test[i][5] = 0.5f;
			test[5][i] = 0.5f;
		}
		
		for (int i = 0; i < test.length; i++) {
			for (int j = 0; j < test[0].length; j++) {
				System.out.print(test[i][j] + ", ");
			}
			System.out.println("");
		}
		
		System.out.println("\n\n\n\n\n\n\n");
		
		float[][] result = getEdges(test);
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				System.out.print(result[i][j] + ", ");
			}
			System.out.println("");
		}
	}
}
