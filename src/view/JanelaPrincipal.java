package view;

import view.componentes.LabelPrincipal;
import view.componentes.TextFieldPrincipal;
import view.model.ResultadoDia;
import view.model.ResultadoSimulacao;

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
    JCheckBox cbSimularMultiplasVezes;
    JTextField tfNumeroSimulacoes;
    JTextField tfIncrementoCapital;
    JPanel painelIncrementarFixoContratos;
    JPanel painelSimularMultiplasVezes;



    String TXT_PORCENTAGEM_ACERTO = "Porcentagem acerto (ex: 80 seria 80%)";
    String TXT_QUANTIDADE_CONTRATOS = "Quantidade Contratos inicial";
    String TXT_LUCRO_CONTRATO = "Lucro p/contrato por dia";
    String TXT_DIAS_UTEIS = "Dias Uteis";
    String TXT_ALVO_ESCALAMENTO = "Alvo para escalamento";
    String TXT_CAPITAL_INICIAL = "Capital Inicial";
    String TXT_INCREMENTAR_FIXO_CONTRATOS = "Incrementar por numero fixo os contratos ao atingir alvo";
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

        cbIncrementarFixoContratos = new JCheckBox(TXT_INCREMENTAR_FIXO_CONTRATOS);
        painelPrincipal.add(cbIncrementarFixoContratos);
        cbIncrementarFixoContratos.addActionListener(e -> {repaint();});

        painelIncrementarFixoContratos = new JPanel();
        painelIncrementarFixoContratos.setLayout(new BoxLayout(painelIncrementarFixoContratos, BoxLayout.Y_AXIS));

        painelIncrementarFixoContratos.add(new LabelPrincipal(TXT_INCREMENTO_CONTRATO));
        tfIncrementoCapital = new TextFieldPrincipal(1);
        painelIncrementarFixoContratos.add(tfIncrementoCapital);

        painelIncrementarFixoContratos.add(new LabelPrincipal(TXT_QUANTIDADE_CONTRATOS));
        tfQntContratos = new TextFieldPrincipal(1);
        painelIncrementarFixoContratos.add(tfQntContratos);

        painelPrincipal.add(painelIncrementarFixoContratos);

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
        painelIncrementarFixoContratos.setVisible(cbIncrementarFixoContratos.isSelected());
        painelSimularMultiplasVezes.setVisible(cbSimularMultiplasVezes.isSelected());
    }

    /**
     * Fazer a simulacao utilizando dos parametros definidos no programa
     */
    public void cliqueGerarEscalamento(ActionEvent e) {
        if (cbSimularMultiplasVezes.isSelected()) {
            Optional<Integer> optNumSimulacoes = adquirirNumeroSimulacoes();
            if (optNumSimulacoes.isEmpty()) {
                return;
            }

            double mediaCapitalTotal = 0;
            for (int i = 0; i < optNumSimulacoes.get(); i++) {
                // System.out.println("Simulacao x" + i);
                ResultadoSimulacao resultadoSimulacao = simularPeriodo();
                mediaCapitalTotal += resultadoSimulacao.getResultadoCapital();
            }
            mediaCapitalTotal /= optNumSimulacoes.get();
            System.out.println("Media capital total em " + optNumSimulacoes.get() + "x simulacoes: " + mediaCapitalTotal);
        } else {
            simularPeriodo();
        }
    }

    public ResultadoSimulacao simularPeriodo() {
        if (!cbSimularMultiplasVezes.isSelected()) {
            System.out.println("\n\n\n\n\n\n");
            System.out.println("Inicializando a simulacao");
        }

        double total = 0;
        double ultimoEscalamento = 0;
        int qntContratosAtual = 0;

        // Adquirir parametros do programa
        Optional<Integer> optDiasUteis = adquirirDiasUteis();
        if (optDiasUteis.isEmpty()) return null;

        Optional<Integer> optAlvoEscalamento = adquirirAlvoEscalamento();
        if (optAlvoEscalamento.isEmpty()) return null;

        Optional<Integer> optQntContratos = adquirirQntContratos();
        if (optQntContratos.isEmpty()) return null;

        qntContratosAtual = optQntContratos.get();

        Optional<Double> optCapitalInicial = adquirirCapitalInicial();
        if (optCapitalInicial.isPresent()) {
            total = optCapitalInicial.get();
        }

        Optional<Integer> optPorcAcerto = adquirirPorcAcerto();
        if (optPorcAcerto.isEmpty()) return null;

        Optional<Double> optLucroContrato = adquirirLucroContrato();
        if (optLucroContrato.isEmpty()) return null;

        Optional<Integer> optIncrementoContratos = adquirirIncrementoContratos();
        // finalizar adquirir parametros do programa

        if (!cbIncrementarFixoContratos.isSelected()) {
            // Setar qnt contrato inicial caso n va subir em numero fixo
            qntContratosAtual = ((int) total / optAlvoEscalamento.get()) + 1;
        }

        ResultadoSimulacao resultadoSimulacao = new ResultadoSimulacao();
        for (int i = 0; i < optDiasUteis.get(); i++) {
            int dia = i + 1;
            int porc = new Random().nextInt(101);

            if (!cbSimularMultiplasVezes.isSelected()) {
                System.out.println(" -- Simulando dia " + dia + " -- ");
                System.out.println("Total atual: " + total + " Ultimo escalamento: " + ultimoEscalamento + " Qnt Contratos: " + qntContratosAtual);
            }

            if (optPorcAcerto.get() >= porc) {
                // Ganhou
                if (!cbSimularMultiplasVezes.isSelected()) {
                    System.out.println("Ganhou (+" + optLucroContrato.get() * qntContratosAtual + ")");
                }
                total += optLucroContrato.get() * qntContratosAtual;
            } else {
                // Perdeu
                if (!cbSimularMultiplasVezes.isSelected()) {
                    System.out.println("Perdeu (-" + optLucroContrato.get() * qntContratosAtual + ")");
                }
                total -= optLucroContrato.get() * qntContratosAtual;
            }

            // Ajustar quantidade de contratos
            if (cbIncrementarFixoContratos.isSelected()) {
                if (optIncrementoContratos.isEmpty()) return null;
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

            ResultadoDia resultadoDia = new ResultadoDia(total, qntContratosAtual, dia);
            resultadoSimulacao.getResultadosDias().add(resultadoDia);
        }
        resultadoSimulacao.calcularResultados();
        return resultadoSimulacao;
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

    private Optional<Integer> adquirirNumeroSimulacoes() {
        int numeroSimulacoes;
        try {
            numeroSimulacoes = Integer.parseInt(tfNumeroSimulacoes.getText());
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, TXT_NUMERO_SIMULACOES + " deve ser um numero!");
            return Optional.empty();
        }
        return Optional.of(numeroSimulacoes);
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