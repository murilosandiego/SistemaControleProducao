/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.entidade;

/**
 *
 * @author murilo
 */
public class GastosExtra {

    private double tarifaAgua;
    private double tarifaLuz;
    private double outrasGastos;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getTarifaAgua() {
        return tarifaAgua;
    }

    public void setTarifaAgua(double tarifaAgua) {
        this.tarifaAgua = tarifaAgua;
    }

    public double getTarifaLuz() {
        return tarifaLuz;
    }

    public void setTarifaLuz(double tarifaLuz) {
        this.tarifaLuz = tarifaLuz;
    }

    public double getOutrasGastos() {
        return outrasGastos;
    }

    public void setOutrasGastos(double outrasGastos) {
        this.outrasGastos = outrasGastos;
    }

}
