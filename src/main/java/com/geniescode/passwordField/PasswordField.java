package com.geniescode.passwordField;

import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

public class PasswordField extends JPasswordField {
    private final Color bottomColor;
    public PasswordField(Color bottomColor) {
        this.bottomColor = bottomColor;
        initComponents();
    }

    private void initComponents() {
        setOpaque(false);
        setBorder(new EmptyBorder(10, 10, 10, 50));
        setFont(new Font("sanserif", Font.PLAIN, 14));
    }

    private void addHint(Graphics graphics) {
        Insets insets = getInsets();
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int c0 = getBackground().getRGB();
        int c1 = getForeground().getRGB();
        int m = 0xfefefefe;
        int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
        if (String.valueOf(getPassword()).length() == 0) {
            graphics.setColor(new Color(c2, true));
            graphics.drawString("Password", insets.left, getHeight() / 2 + fontMetrics.getAscent() / 2 - 2);
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setColor(bottomColor);
        graphics2D.fillRect(2, getHeight() - 2, getWidth() - 4, 3);
        addHint(graphics);
        super.paintComponent(graphics);
    }
}
