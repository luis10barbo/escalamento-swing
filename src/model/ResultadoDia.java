package model;

public class ResultadoDia {
    private final double capital;
    private final int contratos;
    int dia = 0;

    public ResultadoDia(double capital, int contratos, int dia) {
        this.capital = capital;
        this.contratos = contratos;
        this.dia = dia;
    }

    public double getCapital() {
        return capital;
    }

    public int getContratos() {
        return contratos;
    }
}
