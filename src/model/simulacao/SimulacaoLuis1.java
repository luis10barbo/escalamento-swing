package model.simulacao;

import view.JanelaPrincipal;
import model.ResultadoDia;
import model.ResultadoSimulacao;

import java.util.Random;

public class SimulacaoLuis1 extends Simulacao{
    public SimulacaoLuis1(JanelaPrincipal janelaPrincipal) {
        super(janelaPrincipal);
    }

    @Override
    public ResultadoSimulacao simular() {
        // varios dos prints so sao feitos caso tenha a opcao Simular multiplas vezes desligada
        if (!getJanelaPrincipal().adquirirSimularMultiplasVezes()) {
            System.out.println("\n\n\n\n\n\n");
            System.out.println("Inicializando a simulacao");
        }

        double total = 0;
        double ultimoEscalamento = 0;
        int qntContratosAtual = 0;

        // Adquirir parametros do programa
        Integer diasUteis = getJanelaPrincipal().adquirirDiasUteis();
        if (diasUteis == null) return null;

        Integer alvoEscalamento = getJanelaPrincipal().adquirirAlvoEscalamento();
        if (alvoEscalamento == null) return null;

        Integer qntContratos = getJanelaPrincipal().adquirirQntContratos();
        if (qntContratos == null) return null;

        qntContratosAtual = qntContratos;

        Double capitalInicial = getJanelaPrincipal().adquirirCapitalInicial();
        if (capitalInicial != null) {
            total = capitalInicial;
        }

        Integer porcAcerto = getJanelaPrincipal().adquirirPorcAcerto();
        if (porcAcerto == null) return null;

        Double lucroContrato = getJanelaPrincipal().adquirirLucroContrato();
        if (lucroContrato == null) return null;

        Integer incrementoContratos = getJanelaPrincipal().adquirirIncrementoContratos();
        // finalizar adquirir parametros do programa

        if (!getJanelaPrincipal().adquirirIncrementarManualmenteContratos()) {
            // Setar qnt contrato inicial caso n va ser modificado manualmente
            qntContratosAtual = ((int) total / alvoEscalamento) + 1;
        }

        ResultadoSimulacao resultadoSimulacao = new ResultadoSimulacao();
        for (int i = 0; i < diasUteis; i++) {
            int dia = i + 1;
            int porc = new Random().nextInt(101);

            if (!getJanelaPrincipal().adquirirSimularMultiplasVezes()) {
                System.out.println(" -- Simulando dia " + dia + " -- ");
                System.out.println("Total atual: " + total + " Ultimo escalamento: " + ultimoEscalamento + " Qnt Contratos: " + qntContratosAtual);
            }

            if (porcAcerto >= porc) {
                // Ganhou
                if (!getJanelaPrincipal().adquirirSimularMultiplasVezes()) {
                    System.out.println("Ganhou (+" + lucroContrato * qntContratosAtual + ")");
                }
                total += lucroContrato * qntContratosAtual;
            } else {
                // Perdeu
                if (!getJanelaPrincipal().adquirirSimularMultiplasVezes()) {
                    System.out.println("Perdeu (-" + lucroContrato * qntContratosAtual + ")");
                }
                total -= lucroContrato * qntContratosAtual;
            }

            // Ajustar quantidade de contratos
            if (getJanelaPrincipal().adquirirIncrementarManualmenteContratos()) {
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
                qntContratosAtual = ((int) total / alvoEscalamento) + 1;
            }

            ResultadoDia resultadoDia = new ResultadoDia(total, qntContratosAtual, dia);
            resultadoSimulacao.getResultadosDias().add(resultadoDia);
        }
        resultadoSimulacao.calcularResultados();
        return resultadoSimulacao;
    }
}
