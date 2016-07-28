/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.entidade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author murilo
 */
public class Producao {
    private Integer id;
    private Date data;
    private String tipoTijolo;
    private String und;
    private Integer quantidade;
    private double gastoLenha;
    private double gastoBarro;
    private double gastoEletricidade;
    private double gastoAgua;
    private double gastoOutros;
    private List<FuncionarioTarefaProducao> tarefaFuncionarios;
    private double gastoTotal;
    private double gastosMateriPrima;
    private double lucroEstimado;
    private double ValorDeVenda;

    public double getLucroEstimado() {
        return lucroEstimado;
    }

    public void setLucroEstimado(double lucroEstimado) {
        this.lucroEstimado = lucroEstimado;
    }

    public double getValorDeVenda() {
        return ValorDeVenda;
    }

    public void setValorDeVenda(double ValorDeVenda) {
        this.ValorDeVenda = ValorDeVenda;
    }


    public double getGastosMateriPrima() {
        return gastosMateriPrima;
    }

    public void setGastosMateriPrima(double gastosMateriPrima) {
        this.gastosMateriPrima = gastosMateriPrima;
    }
        
        
    //Atributo
    private double gastoFuncionarios;

    public double getGastoFuncionarios() {
        return gastoFuncionarios;
    }

    public void setGastoFuncionarios(double gastoFuncionarios) {
        this.gastoFuncionarios = gastoFuncionarios;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipoTijolo() {
        return tipoTijolo;
    }

    public void setTipoTijolo(String tipoTijolo) {
        this.tipoTijolo = tipoTijolo;
    }

    public String getUnd() {
        return und;
    }

    public void setUnd(String und) {
        this.und = und;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public double getGastoLenha() {
        return gastoLenha;
    }

    public void setGastoLenha(double gastoLenha) {
        this.gastoLenha = gastoLenha;
    }

    public double getGastoBarro() {
        return gastoBarro;
    }

    public void setGastoBarro(double gastoBarro) {
        this.gastoBarro = gastoBarro;
    }

    public double getGastoEletricidade() {
        return gastoEletricidade;
    }

    public void setGastoEletricidade(double gastoEletricidade) {
        this.gastoEletricidade = gastoEletricidade;
    }

    public double getGastoAgua() {
        return gastoAgua;
    }

    public void setGastoAgua(double gastoAgua) {
        this.gastoAgua = gastoAgua;
    }

    public double getGastoOutros() {
        return gastoOutros;
    }

    public void setGastoOutros(double gastoOutros) {
        this.gastoOutros = gastoOutros;
    }

    public List<FuncionarioTarefaProducao> getTarefaFuncionarios() {
        return tarefaFuncionarios;
    }

    public void setTarefaFuncionarios(List<FuncionarioTarefaProducao> tarefaFuncionarios) {
        this.tarefaFuncionarios = tarefaFuncionarios;
    }

    public double getGastoTotal() {
        return gastoTotal;
    }

    public void setGastoTotal(double gastoTotal) {
        this.gastoTotal = gastoTotal;
    }
    
    public String getDataFormatada() {
        String data;
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        data = dt.format(this.data);
        return data;
    }
    
    public double gastosMateriaPrima(){
        this.gastosMateriPrima = this.gastoBarro + this.gastoLenha;
        return gastosMateriPrima;
    }
    
}
