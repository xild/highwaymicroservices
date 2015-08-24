package br.com.highwaypath.model;

import java.math.BigDecimal;
import java.util.List;

public class PathResult {
    
    private List<String> locais;
    private double distancia;
    private BigDecimal custo;
    public List<String> getLocais() {
        return locais;
    }
    public void setLocais(List<String> locais) {
        this.locais = locais;
    }
    public double getDistancia() {
        return distancia;
    }
    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
    public BigDecimal getCusto() {
        return custo;
    }
    public void setCusto(BigDecimal custo) {
        this.custo = custo;
    }
    @Override
    public String toString() {
        return "PathResult [locais=" + locais + ", distancia=" + distancia
                + ", custo=" + custo + "]";
    }

}
