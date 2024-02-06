package model.simulacao;

import model.ParametrosPrograma;
import model.ResultadoDia;
import view.JanelaPrincipal;


public class SimulacaoLuis2 extends Simulacao {
    public SimulacaoLuis2(JanelaPrincipal janelaPrincipal) {
        super(janelaPrincipal);
    }

    @Override
    public ResultadoDia simularDia(ParametrosPrograma parametros, int dia) {
        Double capitalInicial = parametros.adquirirCapitalInicial();
        if (capitalInicial != null && capitalAtual == 0) {
            capitalAtual = capitalInicial;
        }

        // Adquirir parametros do programa
        Integer diasUteis = parametros.adquirirDiasUteis();
        if (diasUteis == null) return null;

        Integer alvoEscalamento = parametros.adquirirAlvoEscalamento();
        if (alvoEscalamento == null) return null;

        Integer qntContratos = parametros.adquirirQntContratos();
        if (qntContratos == null) return null;

        Integer porcAcerto = parametros.adquirirPorcAcerto();
        if (porcAcerto == null) return null;

        Double lucroContrato = parametros.adquirirLucroContrato();
        if (lucroContrato == null) return null;

        Integer incrementoContratos = parametros.adquirirIncrementoContratos();
        // finalizar adquirir parametros do programa

        if (!parametros.adquirirIncrementarManualmenteContratos()) {
            // Setar qnt contrato inicial caso n va ser modificado manualmente
            qntContratosAtual = ((int) capitalAtual / alvoEscalamento) + 1;
        } else {
            qntContratosAtual = qntContratos;
        }

        capitalAtual += lucroContrato * qntContratosAtual * ((100.0 - porcAcerto) / 100);

        // Ajustar quantidade de contratos
        if (parametros.adquirirIncrementarManualmenteContratos()) {
            if (incrementoContratos == null) return null;
            // Aumentar / Diminuir por numero fixo

            qntContratosAtual = (int) (incrementoContratos * (capitalAtual / alvoEscalamento)) + alvoEscalamento;
        } else {
            // Ajustar automaticamente a quantidade de contratos
            qntContratosAtual = ((int) capitalAtual / alvoEscalamento) + 1;
        }

        if (!parametros.adquirirSimularMultiplasVezes()) {
            System.out.println(" -- Simulando dia " + dia + " -- ");
            System.out.println("Total atual: " + capitalAtual + " Qnt Contratos: " + qntContratosAtual);
        }
        return new ResultadoDia(capitalAtual, qntContratosAtual, dia);
    }
}
