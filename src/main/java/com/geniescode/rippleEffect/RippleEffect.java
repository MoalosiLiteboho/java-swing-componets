package com.geniescode.rippleEffect;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class RippleEffect {

    private final Component component;
    private Color rippleColor = Color.white;
    private List<Effect> effects;

    public RippleEffect(Component component) {
        this.component = component;
        init();
    }

    private void init() {
        effects = new ArrayList<>();
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (component.isEnabled())
                    if (SwingUtilities.isLeftMouseButton(event))
                        addEffect(event.getPoint());
            }
        });
    }

    public void addEffect(Point location) {
        effects.add(new Effect(component, location));
    }

    public void render(Graphics graphics, Shape contain) {
        Graphics2D graphics2D = (Graphics2D) graphics.create();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Effect effect : effects)
            if (effect != null)
                effect.render(graphics2D, contain);
        graphics2D.dispose();
    }

    private class Effect {
        private final Component component;
        private final Point location;
        private float animate;

        public Effect(Component component, Point location) {
            this.component = component;
            this.location = location;
            init();
        }

        private void init() {
            Animator animator = new Animator(500, new TimingTargetAdapter() {
                @Override
                public void timingEvent(float fraction) {
                    animate = fraction;
                    component.repaint();
                }

                @Override
                public void end() {
                    effects.remove(Effect.this);
                }
            });
            animator.setResolution(5);
            animator.start();
        }

        public void render(Graphics2D graphics2D, Shape contain) {
            Area area = new Area(contain);
            area.intersect(new Area(getShape(getSize(contain.getBounds2D()))));
            graphics2D.setColor(rippleColor);
            float alpha = 0.3f;
            if (animate >= 0.7f) {
                double t = animate - 0.7f;
                alpha = (float) (alpha - (alpha * (t / 0.3f)));
            }
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            graphics2D.fill(area);
        }

        private Shape getShape(double size) {
            double s = size * animate;
            double x = location.getX();
            double y = location.getY();
            return new Ellipse2D.Double(x - s, y - s, s * 2, s * 2);
        }

        private double getSize(Rectangle2D rectangle2D) {
            double size;
            if (rectangle2D.getWidth() > rectangle2D.getHeight())
                if (location.getX() < rectangle2D.getWidth() / 2) size = rectangle2D.getWidth() - location.getX();
                else size = location.getX();
            else
                if (location.getY() < rectangle2D.getHeight() / 2) size = rectangle2D.getHeight() - location.getY();
                else size = location.getY();
            return size + (size * 0.1f);
        }
    }

    public void setRippleColor(Color rippleColor) {
        this.rippleColor = rippleColor;
    }

    public Color getRippleColor() {
        return rippleColor;
    }
}
