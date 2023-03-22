package com.geniescode.buttons;

import com.geniescode.rippleEffect.RippleEffect;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

public class Button extends JButton {
    private final RippleEffect rippleEffect;
    private final String buttonText;

    public Button(String buttonText) {
        this.buttonText = buttonText;
        rippleEffect = new RippleEffect(this);
        initComponents();
    }

    private void initComponents() {
        setText(buttonText);
        setBackground(Color.green);
        setContentAreaFilled(false);
        setBorder(null);
        setOpaque(false);
        setForeground(Color.white);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent event) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics.create();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        Shape shape = new Area(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), getHeight(), getHeight()));
        graphics2D.setColor(getBackground());
        graphics2D.fill(shape);
        rippleEffect.render(graphics, shape);
        super.paintComponent(graphics);
    }
}
