package view;

import view.componentes.LabelPrincipal;
import view.componentes.TextFieldPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Optional;
import java.util.Random;

public class JanelaPrincipal extends JFrame {
    JTextField tfPorcAcerto;
    JTextField tfQntContratos;
    JTextField tfLucroContrato;
    JTextField tfDiasUteis;
    JTextField tfAlvoEscalamento;
    JTextField tfCapitalInicial;
    JCheckBox cbIncrementarFixoContratos;
    JTextField tfIncrementoCapital;
    JPanel painelIncrementarFixoContratos;


    String TXT_PORCENTAGEM_ACERTO = "Porcentagem acerto";
    String TXT_QUANTIDADE_CONTRATOS = "Quantidade Contratos inicial";
    String TXT_LUCRO_CONTRATO = "Lucro p/contrato por dia";
    String TXT_DIAS_UTEIS = "Dias Uteis";
    String TXT_ALVO_ESCALAMENTO = "Alvo para escalamento";
    String TXT_CAPITAL_INICIAL = "Capital Inicial";
    String TXT_INCREMENTAR_FIXO_CONTRATOS = "Incrementar por numero fixo contratos ao atingir alvo";
    String TXT_INCREMENTO_CONTRATO = "Incremento de contrato por alvos";

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

        painelPrincipal.add(new LabelPrincipal(TXT_QUANTIDADE_CONTRATOS));
        tfQntContratos = new TextFieldPrincipal(1);
        painelPrincipal.add(tfQntContratos);

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

        cbIncrementarFixoContratos = new JCheckBox(TXT_INCREMENTAR_FIXO_CONTRATOS);
        painelPrincipal.add(cbIncrementarFixoContratos);
        cbIncrementarFixoContratos.addActionListener(e -> {repaint();});

        painelIncrementarFixoContratos = new JPanel();
        painelIncrementarFixoContratos.setLayout(new BoxLayout(painelIncrementarFixoContratos, BoxLayout.Y_AXIS));

        painelIncrementarFixoContratos.add(new LabelPrincipal(TXT_INCREMENTO_CONTRATO));
        tfIncrementoCapital = new TextFieldPrincipal(1);
        painelIncrementarFixoContratos.add(tfIncrementoCapital);
        painelPrincipal.add(painelIncrementarFixoContratos);

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
        painelIncrementarFixoContratos.setVisible(cbIncrementarFixoContratos.isSelected());

    }

    /**
     * Fazer a simulacao utilizando dos parametros definidos no programa
     */
    public void cliqueGerarEscalamento(ActionEvent e) {
        System.out.println("\n\n\n\n\n\n");
        System.out.println("Inicializando a simulacao");

        double total = 0;
        double ultimoEscalamento = 0;
        int qntContratosAtual = 0;
        Optional<Integer> optDiasUteis = adquirirDiasUteis();
        if (optDiasUteis.isEmpty()) return;

        Optional<Integer> optAlvoEscalamento = adquirirAlvoEscalamento();
        if (optAlvoEscalamento.isEmpty()) return;

        Optional<Integer> optQntContratos = adquirirQntContratos();
        if (optQntContratos.isEmpty()) return;

        qntContratosAtual = optQntContratos.get();

        Optional<Double> optCapitalInicial = adquirirCapitalInicial();
        if (optCapitalInicial.isPresent()) {
            total = optCapitalInicial.get();
        }

        for (int i = 0; i < optDiasUteis.get(); i++) {
            int dia = i + 1;
            int porc = new Random().nextInt(100);
            System.out.println(" -- Simulando dia " + dia + " -- ");
            System.out.println("Total atual: " + total + " Ultimo escalamento: " + ultimoEscalamento + " Qnt Contratos: " + qntContratosAtual);


            Optional<Integer> optPorcAcerto = adquirirPorcAcerto();
            if (optPorcAcerto.isEmpty()) return;

            Optional<Double> optLucroContrato = adquirirLucroContrato();
            if (optLucroContrato.isEmpty()) return;

            if (optPorcAcerto.get() > porc) {
                // Ganhou
                System.out.println("Ganhou (+" + optLucroContrato.get() * qntContratosAtual + ")");
                total += optLucroContrato.get() * qntContratosAtual;
            } else {
                // Perdeu
                System.out.println("Perdeu (-" + optLucroContrato.get() * qntContratosAtual + ")");
                total -= optLucroContrato.get() * qntContratosAtual;
            }

            // Ajustar quantidade de contratos
            if (cbIncrementarFixoContratos.isSelected()) {
                Optional<Integer> optIncrementoContratos = adquirirIncrementoContratos();
                if (optIncrementoContratos.isEmpty()) return;
                // Aumentar / Diminuir por numero fixo
                if (optPorcAcerto.get() > porc) {
                    // Ganhou
                    qntContratosAtual += optIncrementoContratos.get();
                } else {
                    // Perdeu
                    qntContratosAtual -= Math.max(1, optIncrementoContratos.get()); // nao permitir ir para o negativo
                }
            } else {
                // Ajustar automaticamente a quantidade de contratos
                qntContratosAtual = ((int) total / optAlvoEscalamento.get()) + 1;
            }


        }
    }

    // Abaixo tem varias funcoes para adquirir seguramente os parametros do programa
    private Optional<Integer> adquirirIncrementoContratos() {
        int incrementoCapital;
        try {
            incrementoCapital = Integer.parseInt(tfIncrementoCapital.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_INCREMENTO_CONTRATO + " deve ser um numero!");
            return Optional.empty();
        }
        return Optional.of(incrementoCapital);
    }

    private Optional<Double> adquirirCapitalInicial() {
        double capitalInicial;
        try {
            capitalInicial = Double.parseDouble(tfCapitalInicial.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_CAPITAL_INICIAL + " deve ser um numero/real!");
            return Optional.empty();
        }
        return Optional.of(capitalInicial);
    }

    private Optional<Integer> adquirirDiasUteis() {
        int diasUteis;
        try {
            diasUteis = Integer.parseInt(tfDiasUteis.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_DIAS_UTEIS + " deve ser um numero!");
            return Optional.empty();
        }
        return Optional.of(diasUteis);
    }

    private Optional<Integer> adquirirQntContratos() {
        int qntContratos;
        try {
            qntContratos = Integer.parseInt(tfQntContratos.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_QUANTIDADE_CONTRATOS + " deve ser um numero!");
            return Optional.empty();
        }
        return Optional.of(qntContratos);
    }

    private Optional<Double> adquirirLucroContrato() {
        double lucroContrato;
        try {
            lucroContrato = Double.parseDouble(tfLucroContrato.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_LUCRO_CONTRATO + " deve ser um numero/real!");
            return Optional.empty();
        }
        return Optional.of(lucroContrato);
    }

    private Optional<Integer> adquirirPorcAcerto() {
        int porcAcerto;
        try {
            porcAcerto = Integer.parseInt(tfPorcAcerto.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_PORCENTAGEM_ACERTO + " deve ser um numero!");
            return Optional.empty();
        }
        return Optional.of(porcAcerto);
    }

    private Optional<Integer> adquirirAlvoEscalamento() {
        int alvoEscalamento;
        try {
            alvoEscalamento = Integer.parseInt(tfAlvoEscalamento.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_ALVO_ESCALAMENTO + " deve ser um numero!");
            return Optional.empty();
        }
        return Optional.of(alvoEscalamento);
    }
}