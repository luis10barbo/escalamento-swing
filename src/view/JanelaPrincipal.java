package view;

import model.ParametrosPrograma;
import view.componentes.LabelPrincipal;
import view.componentes.TextFieldPrincipal;
import model.ResultadoSimulacao;
import model.simulacao.Simulacao;
import model.simulacao.SimulacaoLuis1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JanelaPrincipal extends JFrame {
    public JTextField tfPorcAcerto;
    public JTextField tfQntContratos;
    public JTextField tfLucroContrato;
    public JTextField tfDiasUteis;
    public JTextField tfAlvoEscalamento;
    public JTextField tfCapitalInicial;
    public JCheckBox cbIncrementarManualmenteContratos;
    public JCheckBox cbSimularMultiplasVezes;
    public JTextField tfNumeroSimulacoes;
    public JTextField tfIncrementoCapital;
    public JPanel painelIncrementarManualmenteContratos;
    public JPanel painelSimularMultiplasVezes;



    public String TXT_PORCENTAGEM_ACERTO = "Porcentagem acerto (ex: 80 seria 80%)";
    public String TXT_QUANTIDADE_CONTRATOS = "Quantidade Contratos inicial";
    public String TXT_LUCRO_CONTRATO = "Lucro p/contrato por dia";
    public String TXT_DIAS_UTEIS = "Dias Uteis";
    public String TXT_ALVO_ESCALAMENTO = "Alvo para escalamento";
    public String TXT_CAPITAL_INICIAL = "Capital Inicial";
    public String TXT_INCREMENTAR_MANUALMENTE_CONTRATOS = "Incrementar manualmente os contratos";
    public String TXT_INCREMENTO_CONTRATO = "Incremento de contrato por alvos";
    public String TXT_NUMERO_SIMULACOES = "Numero de Simulacoes (maior -> mais precisao)";

    public JanelaPrincipal() {
        criarJanela();
    }

    private void criarJanela() {
        setTitle("Calculador escalamento");
        setSize(800, 640);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));

        painelPrincipal.add(new LabelPrincipal(TXT_PORCENTAGEM_ACERTO));
        tfPorcAcerto = new TextFieldPrincipal(60);
        painelPrincipal.add(tfPorcAcerto);

        painelPrincipal.add(new LabelPrincipal(TXT_CAPITAL_INICIAL));
        tfCapitalInicial = new TextFieldPrincipal(1500);
        painelPrincipal.add(tfCapitalInicial);

        painelPrincipal.add(new LabelPrincipal(TXT_LUCRO_CONTRATO));
        tfLucroContrato = new TextFieldPrincipal(75);
        painelPrincipal.add(tfLucroContrato);

        painelPrincipal.add(new LabelPrincipal(TXT_DIAS_UTEIS));
        tfDiasUteis = new TextFieldPrincipal(20);
        painelPrincipal.add(tfDiasUteis);

        painelPrincipal.add(new LabelPrincipal(TXT_ALVO_ESCALAMENTO));
        tfAlvoEscalamento = new TextFieldPrincipal(150);
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
        ParametrosPrograma parametros = new ParametrosPrograma(this);
        Simulacao simulacaoEscolhida = new SimulacaoLuis1(this);
        if (cbSimularMultiplasVezes.isSelected()) {
            Integer optNumSimulacoes = parametros.adquirirNumeroSimulacoes();
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