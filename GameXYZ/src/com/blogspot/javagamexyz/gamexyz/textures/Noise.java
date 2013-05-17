package com.blogspot.javagamexyz.gamexyz.textures;

import com.badlogic.gdx.math.MathUtils;
import com.blogspot.javagamexyz.gamexyz.utils.MyMath;

public class Noise
{
    int height;
    int width;
    
    int theight, twidth;
    float noise[][]; 

    boolean weighted;
    
    public Noise(int w,int h, boolean weighted)
    {
    	this.weighted = weighted;
        height=h;
        width = w;
        if (width<height) width = height;
        noise = new float[width][height];
        for (int i=0; i < width; i++)
           for (int j=0; j < height; j++)
              noise[i][j] = MathUtils.random();
    }

    // linearly interpolate between points in noise array
    public float linear(float x, float y)
    {

    	if (twidth * theight == 0) return 0;
    	
        int x0 = (int) x;
        int y0 = (int) y;
        float dx = x - x0;
        float dy = y - y0;

        x0 = x0 % twidth;
        y0 = y0 % theight;
        
        int x1 = (x0 + 1) % twidth;
        int y1 = (y0 + 1) % theight;
        
        return 	  noise[x0][y0] * (1 - dx - dy + dx*dy)
        		+ noise[x1][y0] * (dx - dx*dy)
        		+ noise[x0][y1] * (dy - dx*dy)
        		+ noise[x1][y1] * (dx*dy);
    }
    
   

    // linearly interpolate between noise at different resolutions
    public float turbulence(int x, int y, int steps)
    {
        float t = 0;
        int count = 0;
        float sum = 0;
        float scale = MyMath.pow(2, steps-1);
        float pixel_size = 1;
        while ( scale >= pixel_size) {
        	theight = height/(int)scale + height%2;
        	twidth = width/(int)scale + width%2;
            
        	
            if (weighted) {
            	t += linear((x/scale), (y/scale)) * scale;
            	sum += scale;
            }
            
            else {
            	t += linear((x/scale), (y/scale));
            	count++;
            }
            
            scale /= 2;
        }
        if (weighted) return t/sum;
        else return t/count;
    }
}
