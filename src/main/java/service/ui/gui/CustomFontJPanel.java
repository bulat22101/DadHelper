package service.ui.gui;

import javax.swing.*;
import java.awt.*;

public abstract class CustomFontJPanel extends JPanel {
    @Override
    public Component add(Component comp) {
        comp.setFont(new Font("Arial", Font.PLAIN, 20));
        return super.add(comp);
    }
}
