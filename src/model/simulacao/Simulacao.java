package model.simulacao;

import view.JanelaPrincipal;
import model.ResultadoSimulacao;

/**
 * Classe simulacao base, herdar dela para criar novos metodos de simulacao;
 */
public abstract class Simulacao {
    private final JanelaPrincipal janelaPrincipal;
    public Simulacao(JanelaPrincipal janelaPrincipal) {
        this.janelaPrincipal = janelaPrincipal;
    }
    public JanelaPrincipal getJanelaPrincipal() {
        return janelaPrincipal;
    }

    /**
     * Metodo utilizado para simular
     */
    public abstract ResultadoSimulacao simular();
}
