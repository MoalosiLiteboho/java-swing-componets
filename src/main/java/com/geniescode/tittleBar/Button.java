package com.geniescode.tittleBar;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Button extends JButton {
    public Button() {
        initComponents();
    }

    private void initComponents() {
        setPreferredSize(new Dimension( 20, 20));
        setContentAreaFilled(false);
        setBorder(null);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics.create();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(getBackground());
        graphics2D.fillOval(0, 0, getWidth(), getHeight());
        super.paint(graphics);
    }
}
