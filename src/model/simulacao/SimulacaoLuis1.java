package model.simulacao;

import model.ParametrosPrograma;
import view.JanelaPrincipal;
import model.ResultadoDia;
import model.ResultadoSimulacao;

import java.util.Random;

public class SimulacaoLuis1 extends Simulacao{
    public SimulacaoLuis1(JanelaPrincipal janelaPrincipal) {
        super(janelaPrincipal);
    }

    @Override
    public ResultadoDia simularDia(ParametrosPrograma parametros, ResultadoDia diaAnterior, int dia) {
        double capitalAtual = 0;
        int qntContratosAtual = 0;

        if (diaAnterior != null) {
            capitalAtual = diaAnterior.getCapital();
            qntContratosAtual = diaAnterior.getContratos();
            Double capitalInicial = parametros.adquirirCapitalInicial();
            if (capitalInicial != null) {
                capitalAtual = capitalInicial;
            }
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

        int porc = new Random().nextInt(101);

        if (!parametros.adquirirSimularMultiplasVezes()) {
            System.out.println(" -- Simulando dia " + dia + " -- ");
            System.out.println("Total atual: " + capitalAtual + " Qnt Contratos: " + qntContratosAtual);
        }

        if (porcAcerto >= porc) {
            // Ganhou
            if (!parametros.adquirirSimularMultiplasVezes()) {
                System.out.println("Ganhou (+" + lucroContrato * qntContratosAtual + ")");
            }
            capitalAtual += lucroContrato * qntContratosAtual;
        } else {
            // Perdeu
            if (!parametros.adquirirSimularMultiplasVezes()) {
                System.out.println("Perdeu (-" + lucroContrato * qntContratosAtual + ")");
            }
            capitalAtual -= lucroContrato * qntContratosAtual;
        }

        // Ajustar quantidade de contratos
        if (parametros.adquirirIncrementarManualmenteContratos()) {
            if (incrementoContratos == null) return null;
            // Aumentar / Diminuir por numero fixo
            if (porcAcerto > porc) {
                // Ganhou
                qntContratosAtual += incrementoContratos;
            } else {
                // Perdeu
                qntContratosAtual -= Math.max(1, incrementoContratos); // nao permitir ir para o negativo
            }
        } else {
            // Ajustar automaticamente a quantidade de contratos
            qntContratosAtual = ((int) capitalAtual / alvoEscalamento) + 1;
        }

        return new ResultadoDia(capitalAtual, qntContratosAtual, dia);
    }
}
