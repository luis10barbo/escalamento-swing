package model;

import view.JanelaPrincipal;

import javax.swing.*;

public class ParametrosPrograma {
    private final JanelaPrincipal janelaPrincipal;
    public ParametrosPrograma(JanelaPrincipal janelaPrincipal) {
        this.janelaPrincipal = janelaPrincipal;
    }

    public boolean adquirirSimularMultiplasVezes() {
        return janelaPrincipal.cbSimularMultiplasVezes.isSelected();
    }

    public boolean adquirirIncrementarManualmenteContratos() {
        return janelaPrincipal.cbIncrementarManualmenteContratos.isSelected();
    }

    // Abaixo tem varias funcoes para adquirir seguramente os parametros do programa
    public Integer adquirirIncrementoContratos() {
        int incrementoCapital;
        try {
            incrementoCapital = Integer.parseInt(janelaPrincipal.tfIncrementoCapital.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, janelaPrincipal.TXT_INCREMENTO_CONTRATO + " deve ser um numero!");
            return null;
        }
        return incrementoCapital;
    }

    public Double adquirirCapitalInicial() {
        double capitalInicial;
        try {
            capitalInicial = Double.parseDouble(janelaPrincipal.tfCapitalInicial.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, janelaPrincipal.TXT_CAPITAL_INICIAL + " deve ser um numero/real!");
            return null;
        }
        return capitalInicial;
    }

    public Integer adquirirDiasUteis() {
        int diasUteis;
        try {
            diasUteis = Integer.parseInt(janelaPrincipal.tfDiasUteis.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, janelaPrincipal.TXT_DIAS_UTEIS + " deve ser um numero!");
            return null;
        }
        return diasUteis;
    }

    public Integer adquirirQntContratos() {
        int qntContratos;
        try {
            qntContratos = Integer.parseInt(janelaPrincipal.tfQntContratos.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, janelaPrincipal.TXT_QUANTIDADE_CONTRATOS + " deve ser um numero!");
            return null;
        }
        return qntContratos;
    }

    public Double adquirirLucroContrato() {
        double lucroContrato;
        try {
            lucroContrato = Double.parseDouble(janelaPrincipal.tfLucroContrato.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, janelaPrincipal.TXT_LUCRO_CONTRATO + " deve ser um numero/real!");
            return null;
        }
        return lucroContrato;
    }

    public Integer adquirirPorcAcerto() {
        int porcAcerto;
        try {
            porcAcerto = Integer.parseInt(janelaPrincipal.tfPorcAcerto.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, janelaPrincipal.TXT_PORCENTAGEM_ACERTO + " deve ser um numero!");
            return null;
        }
        return porcAcerto;
    }

    public Integer adquirirAlvoEscalamento() {
        int alvoEscalamento;
        try {
            alvoEscalamento = Integer.parseInt(janelaPrincipal.tfAlvoEscalamento.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, janelaPrincipal.TXT_ALVO_ESCALAMENTO + " deve ser um numero!");
            return null;
        }
        return alvoEscalamento;
    }

    public Integer adquirirNumeroSimulacoes() {
        int numeroSimulacoes;
        try {
            numeroSimulacoes = Integer.parseInt(janelaPrincipal.tfNumeroSimulacoes.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, janelaPrincipal.TXT_NUMERO_SIMULACOES + " deve ser um numero!");
            return null;
        }
        return numeroSimulacoes;
    }
}
