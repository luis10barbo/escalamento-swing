package view.model.simulacao;

import view.JanelaPrincipal;
import view.model.ResultadoSimulacao;

public abstract class Simulacao {
    private final JanelaPrincipal janelaPrincipal;
    public Simulacao(JanelaPrincipal janelaPrincipal) {
        this.janelaPrincipal = janelaPrincipal;
    }
    public JanelaPrincipal getJanelaPrincipal() {
        return janelaPrincipal;
    }

    public abstract ResultadoSimulacao simularPeriodo();
}
