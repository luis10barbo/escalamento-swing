package model;

import java.util.ArrayList;
import java.util.List;

public class ResultadoSimulacao {
    private final List<ResultadoDia> resultadosDias = new ArrayList<>();
    private Double mediaCapital = null;
    private Integer maxContratos = null;
    private Integer minContratos = null;
    private Double minCapital = null;
    private Double maxCapital = null;
    private Double resultadoCapital = null;
    private boolean jaCalculou = false;
    public List<ResultadoDia> getResultadosDias() {
        return resultadosDias;
    }

    public void calcularResultados() {
        double mediaCapital = 0;
        for (ResultadoDia resultadoDia: resultadosDias) {
            mediaCapital += resultadoDia.getCapital();

            if (minCapital == null || minCapital > resultadoDia.getCapital()) {
                minCapital = resultadoDia.getCapital();
            }

            if (maxCapital == null || maxCapital < resultadoDia.getCapital()) {
                maxCapital = resultadoDia.getCapital();
            }

            if (minContratos == null || minContratos > resultadoDia.getContratos()) {
                minContratos = resultadoDia.getContratos();
            }

            if (maxContratos == null || maxContratos < resultadoDia.getContratos()) {
                maxContratos = resultadoDia.getContratos();
            }
        }
        mediaCapital /= resultadosDias.size();
        resultadoCapital = resultadosDias.get(resultadosDias.size() - 1).getCapital();
        this.mediaCapital = mediaCapital;
        this.jaCalculou = true;
    }

    public boolean jaCalculou() {
        return jaCalculou;
    }

    public Double getMediaCapital() {
        return mediaCapital;
    }

    public Integer getMaxContratos() {
        return maxContratos;
    }

    public Integer getMinContratos() {
        return minContratos;
    }

    public Double getMinCapital() {
        return minCapital;
    }

    public Double getMaxCapital() {
        return maxCapital;
    }

    public Double getResultadoCapital() {
        return resultadoCapital;
    }
}
