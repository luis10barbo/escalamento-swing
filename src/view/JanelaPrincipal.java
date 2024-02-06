package view;

import view.componentes.LabelPrincipal;
import view.componentes.TextFieldPrincipal;
import model.ResultadoSimulacao;
import model.simulacao.Simulacao;
import model.simulacao.SimulacaoLuis1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JanelaPrincipal extends JFrame {
    JTextField tfPorcAcerto;
    JTextField tfQntContratos;
    JTextField tfLucroContrato;
    JTextField tfDiasUteis;
    JTextField tfAlvoEscalamento;
    JTextField tfCapitalInicial;
    JCheckBox cbIncrementarManualmenteContratos;
    JCheckBox cbSimularMultiplasVezes;
    JTextField tfNumeroSimulacoes;
    JTextField tfIncrementoCapital;
    JPanel painelIncrementarManualmenteContratos;
    JPanel painelSimularMultiplasVezes;



    String TXT_PORCENTAGEM_ACERTO = "Porcentagem acerto (ex: 80 seria 80%)";
    String TXT_QUANTIDADE_CONTRATOS = "Quantidade Contratos inicial";
    String TXT_LUCRO_CONTRATO = "Lucro p/contrato por dia";
    String TXT_DIAS_UTEIS = "Dias Uteis";
    String TXT_ALVO_ESCALAMENTO = "Alvo para escalamento";
    String TXT_CAPITAL_INICIAL = "Capital Inicial";
    String TXT_INCREMENTAR_MANUALMENTE_CONTRATOS = "Incrementar manualmente os contratos";
    String TXT_INCREMENTO_CONTRATO = "Incremento de contrato por alvos";
    String TXT_NUMERO_SIMULACOES = "Numero de Simulacoes (maior -> mais precisao)";

    public JanelaPrincipal() {
        criarJanela();
    }

    private void criarJanela() {
        setTitle("Calculador escalamento");
        setSize(800, 640);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        painelPrincipal.add(new LabelPrincipal(TXT_PORCENTAGEM_ACERTO));
        tfPorcAcerto = new TextFieldPrincipal(80);
        painelPrincipal.add(tfPorcAcerto);

        painelPrincipal.add(new LabelPrincipal(TXT_CAPITAL_INICIAL));
        tfCapitalInicial = new TextFieldPrincipal(0);
        painelPrincipal.add(tfCapitalInicial);

        painelPrincipal.add(new LabelPrincipal(TXT_LUCRO_CONTRATO));
        tfLucroContrato = new TextFieldPrincipal(100);
        painelPrincipal.add(tfLucroContrato);

        painelPrincipal.add(new LabelPrincipal(TXT_DIAS_UTEIS));
        tfDiasUteis = new TextFieldPrincipal(30);
        painelPrincipal.add(tfDiasUteis);

        painelPrincipal.add(new LabelPrincipal(TXT_ALVO_ESCALAMENTO));
        tfAlvoEscalamento = new TextFieldPrincipal(1000);
        painelPrincipal.add(tfAlvoEscalamento);

        cbIncrementarManualmenteContratos = new JCheckBox(TXT_INCREMENTAR_MANUALMENTE_CONTRATOS);
        painelPrincipal.add(cbIncrementarManualmenteContratos);
        cbIncrementarManualmenteContratos.addActionListener(e -> {repaint();});

        painelIncrementarManualmenteContratos = new JPanel();
        painelIncrementarManualmenteContratos.setLayout(new BoxLayout(painelIncrementarManualmenteContratos, BoxLayout.Y_AXIS));

        painelIncrementarManualmenteContratos.add(new LabelPrincipal(TXT_INCREMENTO_CONTRATO));
        tfIncrementoCapital = new TextFieldPrincipal(1);
        painelIncrementarManualmenteContratos.add(tfIncrementoCapital);

        painelIncrementarManualmenteContratos.add(new LabelPrincipal(TXT_QUANTIDADE_CONTRATOS));
        tfQntContratos = new TextFieldPrincipal(1);
        painelIncrementarManualmenteContratos.add(tfQntContratos);

        painelPrincipal.add(painelIncrementarManualmenteContratos);

        painelSimularMultiplasVezes = new JPanel();
        painelSimularMultiplasVezes.setLayout(new BoxLayout(painelSimularMultiplasVezes, BoxLayout.Y_AXIS));
        cbSimularMultiplasVezes = new JCheckBox("Simular Multiplas Vezes");
        painelPrincipal.add(cbSimularMultiplasVezes);
        cbSimularMultiplasVezes.addActionListener(e -> {repaint();});

        painelSimularMultiplasVezes.add(new LabelPrincipal(TXT_NUMERO_SIMULACOES));
        tfNumeroSimulacoes = new JTextField("10000");
        painelSimularMultiplasVezes.add(tfNumeroSimulacoes);

        painelPrincipal.add(painelSimularMultiplasVezes);

        JButton btnGerarEscalamento = new JButton("Gerar Escalamento");
        painelPrincipal.add(btnGerarEscalamento);
        btnGerarEscalamento.addActionListener(this::cliqueGerarEscalamento);


        add(painelPrincipal);
        setVisible(true);
    }

    /**
     * Atualizar elementos que devem ser escondidos ao redesenhar a pagina
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        painelIncrementarManualmenteContratos.setVisible(cbIncrementarManualmenteContratos.isSelected());
        painelSimularMultiplasVezes.setVisible(cbSimularMultiplasVezes.isSelected());
    }

    /**
     * Fazer a simulacao utilizando dos parametros definidos no programa
     */
    public void cliqueGerarEscalamento(ActionEvent e) {
        // TODO: Mudar para Simulacao que voce quer usar
        Simulacao simulacaoEscolhida = new SimulacaoLuis1(this);
        if (cbSimularMultiplasVezes.isSelected()) {
            Integer optNumSimulacoes = adquirirNumeroSimulacoes();
            if (optNumSimulacoes == null) {
                return;
            }

            double mediaCapitalTotal = 0;
            for (int i = 0; i < optNumSimulacoes; i++) {
                // System.out.println("Simulacao x" + i);
                ResultadoSimulacao resultadoSimulacao = simulacaoEscolhida.simular();
                mediaCapitalTotal += resultadoSimulacao.getResultadoCapital();
            }
            mediaCapitalTotal /= optNumSimulacoes;
            System.out.println("Media capital total em " + optNumSimulacoes + "x simulacoes: " + mediaCapitalTotal);
        } else {
            simulacaoEscolhida.simular();
        }
    }

    public boolean adquirirSimularMultiplasVezes() {
        return cbSimularMultiplasVezes.isSelected();
    }

    public boolean adquirirIncrementarManualmenteContratos() {
        return cbIncrementarManualmenteContratos.isSelected();
    }

    // Abaixo tem varias funcoes para adquirir seguramente os parametros do programa
    public Integer adquirirIncrementoContratos() {
        int incrementoCapital;
        try {
            incrementoCapital = Integer.parseInt(tfIncrementoCapital.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_INCREMENTO_CONTRATO + " deve ser um numero!");
            return null;
        }
        return incrementoCapital;
    }

    public Double adquirirCapitalInicial() {
        double capitalInicial;
        try {
            capitalInicial = Double.parseDouble(tfCapitalInicial.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_CAPITAL_INICIAL + " deve ser um numero/real!");
            return null;
        }
        return capitalInicial;
    }

    public Integer adquirirDiasUteis() {
        int diasUteis;
        try {
            diasUteis = Integer.parseInt(tfDiasUteis.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_DIAS_UTEIS + " deve ser um numero!");
            return null;
        }
        return diasUteis;
    }

    public Integer adquirirQntContratos() {
        int qntContratos;
        try {
            qntContratos = Integer.parseInt(tfQntContratos.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_QUANTIDADE_CONTRATOS + " deve ser um numero!");
            return null;
        }
        return qntContratos;
    }

    public Double adquirirLucroContrato() {
        double lucroContrato;
        try {
            lucroContrato = Double.parseDouble(tfLucroContrato.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_LUCRO_CONTRATO + " deve ser um numero/real!");
            return null;
        }
        return lucroContrato;
    }

    public Integer adquirirPorcAcerto() {
        int porcAcerto;
        try {
            porcAcerto = Integer.parseInt(tfPorcAcerto.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_PORCENTAGEM_ACERTO + " deve ser um numero!");
            return null;
        }
        return porcAcerto;
    }

    public Integer adquirirAlvoEscalamento() {
        int alvoEscalamento;
        try {
            alvoEscalamento = Integer.parseInt(tfAlvoEscalamento.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_ALVO_ESCALAMENTO + " deve ser um numero!");
            return null;
        }
        return alvoEscalamento;
    }

    public Integer adquirirNumeroSimulacoes() {
        int numeroSimulacoes;
        try {
            numeroSimulacoes = Integer.parseInt(tfNumeroSimulacoes.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_NUMERO_SIMULACOES + " deve ser um numero!");
            return null;
        }
        return numeroSimulacoes;
    }
}

/*
*
* Porcentagem de acerto -> tirar do total a porcentagem
* n contratos * capitalAtual => lucroAtual
* falha = lucroAtual * (porcentagem - 100)
* (tirar sempre 40% do lucro de todos dias)
*
*
* Definir um teto (ex: 7k parou o programa)
*
* se ele desceu
*
* */