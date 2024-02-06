package model.simulacao;

import model.ParametrosPrograma;
import model.ResultadoDia;
import view.JanelaPrincipal;
import model.ResultadoSimulacao;

/**
 * Classe simulacao base, herdar dela para criar novos metodos de simulacao;
 */
public abstract class Simulacao {
    protected double capitalAtual = 0.0;
    protected int qntContratosAtual = 0;
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
    public ResultadoSimulacao simular() {
        ParametrosPrograma parametrosPrograma = new ParametrosPrograma(janelaPrincipal);

        Integer diasUteis = parametrosPrograma.adquirirDiasUteis();
        if (diasUteis == null) return null;

        ResultadoSimulacao resultadoSimulacao = new ResultadoSimulacao();
        for (int i = 0; i < diasUteis; i++) {
            int dia = i + 1;
            resultadoSimulacao.getResultadosDias().add(simularDia(parametrosPrograma, dia));
        }
        resetarVariaveisCompartilhadas();
        resultadoSimulacao.calcularResultados();
        return resultadoSimulacao;
    }

    private void resetarVariaveisCompartilhadas() {
        qntContratosAtual = 0;
        capitalAtual = 0.0;
    }

    ;

    public abstract ResultadoDia simularDia(ParametrosPrograma parametros, int dia);
}
