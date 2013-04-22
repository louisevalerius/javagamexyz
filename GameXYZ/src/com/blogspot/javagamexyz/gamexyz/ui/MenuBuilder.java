package com.blogspot.javagamexyz.gamexyz.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.blogspot.javagamexyz.gamexyz.screens.OverworldScreen;

public class MenuBuilder {

	public static ScrollPane buildMenu(final OverworldScreen screen) {
		Texture fontTexture = new Texture(Gdx.files.internal("fonts/irisUPC.png"));
		fontTexture.setFilter(TextureFilter.Linear, TextureFilter.MipMapLinearLinear);
		TextureRegion fontRegion = new TextureRegion(fontTexture);
		BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/irisUPC.fnt"), fontRegion, false);
		font.setUseIntegerPositions(false);		
		
		ButtonStyle style = new ButtonStyle();
		style.up= new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/misc/button_down.png"))));
		style.unpressedOffsetX = 5f;
		style.pressedOffsetX = style.unpressedOffsetX + 1f;
		style.pressedOffsetY = -1f;
		
		
		LabelStyle lStyle = new LabelStyle();
		lStyle.font = font;
				
		Table mainTable = new Table();
		mainTable.defaults().width(80);
		
		ScrollPane scrollPane = new ScrollPane(mainTable);
		scrollPane.setFillParent(false);
		scrollPane.setX(-10);
		scrollPane.setY(screen.stage.getHeight()-250);
		
		Button b1 = new Button(style);
		b1.add(new Label("Move",lStyle));
		b1.left();
		b1.addListener(new ChangeListener() { 
			public void changed(ChangeEvent event, Actor actor) {
				screen.selectedMove();
			}
		});
		mainTable.add(b1);
		mainTable.row();
		
		Button b2 = new Button(style);
		b2.add(new Label("Attack",lStyle));
		b2.left();
		b2.addListener(new ChangeListener() { 
			public void changed(ChangeEvent event, Actor actor) {
				screen.selectedAttack();
			}
			});
		mainTable.add(b2);
		mainTable.row();
		
		// Make a bunch of filler buttons
		for (int i = 0; i < 10; i++) {
			Button b3 = new Button(style);
			b3.add(new Label("Wait",lStyle));
			b3.left();
			b3.addListener(new ChangeListener() { 
				public void changed(ChangeEvent event, Actor actor) {
					screen.selectedWait();
				}
				});
			mainTable.add(b3);
			mainTable.row();
		}
		
		return scrollPane;
	}	
}
