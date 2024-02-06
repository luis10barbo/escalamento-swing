package model.simulacao;

import model.ParametrosPrograma;
import model.ResultadoDia;
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
    public ResultadoSimulacao simular() {
        ParametrosPrograma parametrosPrograma = new ParametrosPrograma(janelaPrincipal);

        Integer diasUteis = parametrosPrograma.adquirirDiasUteis();
        if (diasUteis == null) return null;

        ResultadoSimulacao resultadoSimulacao = new ResultadoSimulacao();
        ResultadoDia diaAnterior = null;
        for (int i = 0; i < diasUteis; i++) {
            int dia = i + 1;
            diaAnterior = simularDia(parametrosPrograma, diaAnterior, dia);
            resultadoSimulacao.getResultadosDias().add(diaAnterior);
        }
        resultadoSimulacao.calcularResultados();
        return resultadoSimulacao;
    };

    public abstract ResultadoDia simularDia(ParametrosPrograma parametros, ResultadoDia diaAnterior, int dia);
}
