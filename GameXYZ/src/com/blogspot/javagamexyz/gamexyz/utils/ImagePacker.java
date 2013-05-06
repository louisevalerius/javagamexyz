package com.blogspot.javagamexyz.gamexyz.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class ImagePacker {

	public static void run() {
		Settings settings = new Settings();		
		settings.filterMin = Texture.TextureFilter.Linear;
		settings.filterMag = Texture.TextureFilter.Linear;
		settings.pot=false;
        //TexturePacker2.process(settings, "textures-original", "resources/textures", "pack");
        TexturePacker2.process(settings, "textures/characters", "resources/textures", "characters");
        TexturePacker2.process(settings, "textures/maptiles", "resources/textures", "maptiles");
	}

}
