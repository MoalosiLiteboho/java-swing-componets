package com.geniescode.comboBox;


import com.geniescode.scroll.ScrollBar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.List;

import static java.awt.Color.*;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

public class ComboBox <Type> extends JComboBox <Type> {
    private final String hintText;
    private final Color borderColor;

    public ComboBox (String hintText, Color borderColor) {
        this.hintText = hintText;
        this.borderColor = borderColor;
        initComponents();
    }

    private void initComponents() {
        setBackground(white);
        setBorder(new EmptyBorder(15, 3, 5, 3));
        setUI(new ComboUI(this));
        setFont(new Font("sanserif", Font.PLAIN, 15));
    }

    public void addModels(List<Type> list) {
        list.forEach(this::addItem);
    }

    private class ComboUI extends BasicComboBoxUI {
        private final ComboBox <Type> comboBox;

        public ComboUI (ComboBox <Type> comboBox) {
            this.comboBox = comboBox;
        }

        private void addHint(Graphics graphics) {
            FontMetrics fontMetrics = graphics.getFontMetrics();
            Insets insets = getInsets();
            int c0 = getBackground().getRGB();
            int c1 = getForeground().getRGB();
            int m = 0xfefefefe;
            int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);

            if (getSelectedIndex() == -1) {
                graphics.setColor(new Color(c2, true));
                graphics.drawString(hintText, insets.left, getHeight() / 2 + fontMetrics.getAscent() / 2 - 2);
            }
        }

        @Override
        public void paint(Graphics graphics, JComponent jComponent) {
            super.paint(graphics, jComponent);
            Graphics2D graphics2D = (Graphics2D) graphics;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            graphics2D.setColor(borderColor);

            graphics2D.setColor(borderColor);
            graphics2D.fillRect(2, getHeight() - 2, getWidth() - 4, 3);
            addHint(graphics2D);
            graphics2D.dispose();
        }

        @Override
        protected JButton createArrowButton() {
            return new ArrowButton();
        }

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup comboPopup = new BasicComboPopup((JComboBox<Object>) comboBox) {
                @Override
                protected JScrollPane createScroller() {
                    ScrollBar scrollBar = new ScrollBar();
                    JScrollPane scroll = new JScrollPane(list);

                    list.setFixedCellHeight(20);

                    scrollBar.setUnitIncrement(30);
                    scrollBar.setForeground(gray);

                    scroll.setBackground(white);
                    scroll.setVerticalScrollBar(scrollBar);
                    return scroll;
                }
            };
            comboPopup.setBorder(new LineBorder(lightGray, 1));
            return comboPopup;
        }

        private final class ArrowButton extends JButton {
            public ArrowButton () {
                initComponents();
            }

            private void initComponents() {
                setContentAreaFilled(false);
                setBorder(new EmptyBorder(5, 5, 5, 5));
                setBackground(gray);
            }

            @Override
            public void paint(Graphics graphics) {
                super.paint(graphics);
                Graphics2D graphics2D = (Graphics2D) graphics;
                graphics2D.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
                int size = 10;
                int x = (getWidth() - size) / 2, y = (getHeight() - size) / 2 + 5;
                int[] px = {x, x + size, x + size / 2};
                int[] py = {y, y, y + size};
                graphics2D.setColor(getBackground());
                graphics2D.fillPolygon(px, py, px.length);
                graphics2D.dispose();
            }
        }
    }
}
