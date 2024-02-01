package view.componentes;

import javax.swing.*;
import java.awt.*;

public class LabelPrincipal extends JLabel {
    public LabelPrincipal(String texto) {
        super(texto);
        setFont(new Font("Arial", Font.PLAIN, 24));
    }
}