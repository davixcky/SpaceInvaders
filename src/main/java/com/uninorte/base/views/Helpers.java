package com.uninorte.base.views;

import com.uninorte.base.game.gfx.ContentLoader;

import javax.swing.*;
import java.awt.*;

public class Helpers {
    public static JPanel createPanel(JPanel basePanel, Dimension preferredSize, String position, int axis, Component ...components) {
        JPanel tmp = createPanel(basePanel, position, axis, components);
        tmp.setPreferredSize(preferredSize);
        return tmp;
    }

    public static JPanel createPanel(JPanel basePanel, String position, Component ...components) {
        JPanel tmp = createPanel(basePanel, position, 0, components);
        return tmp;
    }

    public static JPanel createPanel(JPanel basePanel, String position, int axis, Component ...components) {
        JPanel tmp = new JPanel();
        tmp.setLayout(new BoxLayout(tmp, axis));
        tmp.setOpaque(false);
        basePanel.add(tmp, position);

        for (Component c: components) {
            tmp.add(c);
        }

        return tmp;
    }

    public static JPanel createPanelNonRoot(int axis, Component ...components) {
        JPanel tmp = new JPanel();
        tmp.setLayout(new BoxLayout(tmp, axis));
        tmp.setOpaque(false);

        for (Component c: components) {
            tmp.add(c);
        }

        return tmp;
    }

    public static JButton createButton(String path, String pathHover, Dimension preferredSize) {
        JButton tmpBtn = createButton(path, preferredSize);
        tmpBtn.setRolloverIcon(new ImageIcon(ContentLoader.loadImage(pathHover)));
        return tmpBtn;
    }

    public static JButton createButton(String path, Dimension preferredSize) {
        JButton tmpBtn = createButton(path);
        tmpBtn.setPreferredSize(preferredSize);
        tmpBtn.setMaximumSize(preferredSize);
        return tmpBtn;
    }

    public static JButton createButton(String path) {
        JButton tmpBtn = new JButton(new ImageIcon(ContentLoader.loadImage(path)));
        tmpBtn.setBorderPainted(false);
        tmpBtn.setOpaque(false);
        tmpBtn.setContentAreaFilled(false);
        return tmpBtn;
    }
}
