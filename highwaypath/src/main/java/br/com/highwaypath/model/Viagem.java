package br.com.highwaypath.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class Viagem {
    
    @NotNull
    private String mapa;
    
    @NotNull
    private String origem;
    @NotNull
    private String destino;
    @NotNull
    private double autonomia;
    @NotNull
    private BigDecimal valorLitro;
    
    public String getOrigem() {
        return origem;
    }
    public void setOrigem(String origem) {
        this.origem = origem;
    }
    public String getDestino() {
        return destino;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }
    public double getAutonomia() {
        return autonomia;
    }
    public void setAutonomia(double autonomia) {
        this.autonomia = autonomia;
    }
    public BigDecimal getValorLitro() {
        return valorLitro;
    }
    public void setValorLitro(BigDecimal valorLitro) {
        this.valorLitro = valorLitro;
    }
    public String getMapa() {
        return mapa;
    }
    public void setMapa(String mapa) {
        this.mapa = mapa;
    }

}
