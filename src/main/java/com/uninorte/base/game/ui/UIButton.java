package com.uninorte.base.game.ui;

import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;
import com.uninorte.base.game.states.State;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UIButton extends UIObject {

	private ArrayList<BufferedImage> images;
	private ClickListener clicker;

	private String text = null;
	private String hoverText = null;
	private boolean isCustomSize = false;
	private Dimension size;

	private static ArrayList<BufferedImage> buttonsAssets = Assets.getUiComponents(Assets.UI_ELEMENTS.BUTTONS);
	public  static BufferedImage btnImage = buttonsAssets.get(0);
	public static BufferedImage btnHoverImager = buttonsAssets.get(3);

	public UIButton(State parent, float x, float y, int width, int height, ArrayList<BufferedImage> images, ClickListener clicker) {
		super(parent, x, y, width, height);
		this.images = images;
		this.clicker = clicker;
		isCustomSize = true;
		size = new Dimension(width, height);
	}

	public UIButton(State parent, float x, float y, BufferedImage image, ClickListener clicker) {
		super(parent, x, y, image.getWidth(), image.getHeight());

		images = new ArrayList<>();
		images.add(image);
		images.add(image);

		this.width = image.getWidth();
		this.height = image.getHeight();

		this.clicker = clicker;
	}

	@Override
	public void update() {}

	@Override
	public void render(Graphics g) {
		int currentImageIndex = hovering ? 0 :  1;
		BufferedImage currentImage = images.get(currentImageIndex);
		String currentText = hovering ? hoverText : text;

		Dimension currentDimension = new Dimension(width, height);

		int textX = (int) (x + currentDimension.getWidth() / 2);
		int textY = (int) (y + currentDimension.getHeight() / 2);

		g.drawImage(currentImage, (int) x, (int) y, (int) currentDimension.getWidth(), (int) currentDimension.getHeight(), null);

		if (text != null)
			Text.drawString(g, currentText, textX, textY, true, Color.white, Assets.getFont(Assets.FontsName.SLKSCR, 8));

	}

	@Override
	public void onClick() {
		clicker.onClick();
	}

	@Override
	public void onMouseChanged(MouseEvent e) {

	}

	@Override
	public void onObjectDragged(MouseEvent e) {

	}

	public void setHoverImage(BufferedImage hoverImage) {
		images.add(0, hoverImage);
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setHover(BufferedImage hoverImage, String hoverText) {
		setHoverImage(hoverImage);

		this.hoverText = hoverText;
	}

	public void setSize(Dimension size) {
		this.size = size;
		isCustomSize = true;

		bounds.width = size.width;
		bounds.height = size.height;

		this.width = size.width;
		this.height = size.height;
	}

	public static float getHeightRelative(UIButton uiButton) {
		return uiButton.getY() + uiButton.getHeight() + 10;
	}
}
