package view.componentes;

import javax.swing.*;
import java.awt.*;

public class TextFieldPrincipal extends JTextField {
    public TextFieldPrincipal() {
        setFont(new Font("Arial", Font.PLAIN, 24));
        setSize(0, 32);
    }

    public TextFieldPrincipal(String texto) {
        super();
        setText(texto);
    }

    public TextFieldPrincipal(int texto) {
        super();
        setText(String.valueOf(texto));
    }
}