package com.uninorte.base.game.gfx;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Text {
    public static Dimension drawString(Graphics g, String text, int xPos, int yPos, boolean center, Color c, Font font){
        Dimension size = new Dimension();

        g.setColor(c);
        g.setFont(font);
        int x = xPos;
        int y = yPos;
        if(center){
            FontMetrics fm = g.getFontMetrics(font);
            x = xPos - fm.stringWidth(text) / 2;
            y = (yPos - fm.getHeight() / 2) + fm.getAscent();
        }
        g.drawString(text, x, y);

        FontMetrics fm = g.getFontMetrics(font);
        final Rectangle2D stringBounds = fm.getStringBounds(text, g);
        return new Dimension((int) stringBounds.getWidth(), (int) stringBounds.getHeight());
    }
}
