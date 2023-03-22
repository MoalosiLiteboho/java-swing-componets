package com.geniescode.tittleBar;

import com.geniescode.panel.Panel;
import net.miginfocom.swing.MigLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.function.Consumer;

import static java.awt.Color.green;
import static java.awt.Color.orange;
import static java.awt.Color.red;

public class ThreeButtons extends Panel {
    private final JFrame frame;
    public ThreeButtons(JFrame jFrame) {
        this.frame = jFrame;
        initComponents();
        Consumer<JFrame> setFrameResize = frame -> {
            final int[] x = new int[1];
            final int[] y = new int[1];
            final boolean[] register = {true};
            FrameResizer resizer = new FrameResizer();
            JPanel panel = new JPanel();

            resizer.setSnapSize(new Dimension(10, 10));
            resizer.setMinimumSize(new Dimension(800, 600));
            resizer.registerComponent(frame);
            frame.addWindowStateListener(event -> {
                if (event.getNewState() == JFrame.MAXIMIZED_BOTH) {
                    resizer.deregisterComponent(frame);
                    register[0] = false;
                } else if (event.getNewState() == JFrame.NORMAL) {
                    if (!register[0]) {
                        resizer.registerComponent(frame);
                        register[0] = true;
                    }
                }
            });
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent event) {
                    if (frame.getExtendedState() == JFrame.NORMAL && SwingUtilities.isLeftMouseButton(event)) {
                        x[0] = event.getX() + 3;
                        y[0] = event.getY() + 3;
                    }
                }
            });
            panel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent event) {
                    if (SwingUtilities.isLeftMouseButton(event))
                        if (frame.getExtendedState() == JFrame.NORMAL)
                            frame.setLocation(event.getXOnScreen() - x[0], event.getYOnScreen() - y[0]);
                }
            });
        };
        setFrameResize.accept(this.frame);
    }
    private void initComponents() {
        JPanel panel = new JPanel();
        Button closeButton = new Button();
        Button minimizeButton = new Button();
        Button resizeButton = new Button();

        resizeButton.setBackground(green);
        resizeButton.addActionListener(event -> {
            if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH) frame.setExtendedState(JFrame.NORMAL);
            else frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        });

        minimizeButton.setBackground(orange);
        minimizeButton.addActionListener(event -> frame.setState(JFrame.ICONIFIED));

        closeButton.setBackground(red);
        closeButton.addActionListener(action -> System.exit(0));

        panel.setLayout(new MigLayout("inset 3"));
        panel.add(closeButton);
        panel.add(minimizeButton);
        panel.add(resizeButton);
        panel.setOpaque(false);

        setLayout(new MigLayout("inset 3, fill", "[fill]", "[fill]"));
        add(panel);
    }
}
