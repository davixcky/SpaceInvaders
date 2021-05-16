package com.uninorte.base.game.ui;

import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;
import com.uninorte.base.game.states.State;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UISlider extends UIObject {

    private ArrayList<BufferedImage> assets;
    private MouseListener mouseListener;

    private float percent;

    private Point pointerCoordinates;
    private final Point originalCoordinates;
    private final Point originalPointerCoordinates;

    private final BufferedImage sliderAsset, pointerAsset;
    private final Font customFont;

    public UISlider(State parent, float x, float y, int width, int height, MouseListener mouseListener) {
        super(parent, x, y, width,  height);

        bounds.x  = (int) (x + 50);
        bounds.y = (int) y;
        bounds.width = 22;
        bounds.height = 17;

        originalCoordinates = new Point((int) x, (int) y);
        pointerCoordinates = new Point(bounds.x, bounds.y);
        originalPointerCoordinates = new Point(pointerCoordinates);

        percent = 0;

        assets = Assets.getUiComponents(Assets.UI_ELEMENTS.SLIDER);
        sliderAsset = assets.get(0);
        pointerAsset = assets.get(1);
        customFont  = Assets.getFont(Assets.FontsName.SLKSCR, 13);

        this.mouseListener = mouseListener;
    }

    public void setValue(float value) {
        if (value > 100) {
            value = 100;
        } else if (value < 0) {
            value = 0;
        }

        pointerCoordinates.x = (int) (value * 175 / 100) + originalPointerCoordinates.x;
        bounds.x = pointerCoordinates.x;
        percent = value;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sliderAsset, originalCoordinates.x, originalCoordinates.y, null);
        g.drawImage(pointerAsset, pointerCoordinates.x, pointerCoordinates.y, null);

        String numberAsString = Integer.toString((int) percent);
        Text.drawString(g, numberAsString, originalCoordinates.x + 20, originalCoordinates.y + 9, true, Color.white, customFont);
    }

    @Override
    public void onClick() {
    }

    @Override
    public void onMouseChanged(MouseEvent e) {
    }

    @Override
    public void onObjectDragged(MouseEvent e) {
        float customX = e.getX() - distanceX;

        if (customX <= originalPointerCoordinates.x) {
            customX = originalPointerCoordinates.x;
        }

        int width = sliderAsset.getWidth() + originalCoordinates.x - pointerAsset.getWidth();
        if (customX >= width) {
            customX = width;
        }

        bounds.x = (int) customX;
        percent = (customX - originalPointerCoordinates.x) * 100 / 173;
        pointerCoordinates.x = (int) customX;
        mouseListener.onMouseChanged(percent);
    }
}
