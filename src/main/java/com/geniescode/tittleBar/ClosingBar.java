package com.geniescode.tittleBar;

import net.miginfocom.swing.MigLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.Color;

public class ClosingBar extends JComponent {
    public ClosingBar() {
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        Button closingButton = new Button();

        closingButton.setBackground(Color.red);
        closingButton.addActionListener(action -> System.exit(0));

        panel.setLayout(new MigLayout("inset 3px"));
        panel.add(closingButton);
        panel.setOpaque(false);

        setLayout(new MigLayout("inset 3px, fill", "[fill]", "[fill]"));
        add(panel);
    }
}
