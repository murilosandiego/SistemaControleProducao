/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.excecao;

/**
 *
 * @author murilo
 */
public class CampoDeVendaInvalidoException extends SistemaControleProducaoException{

    public CampoDeVendaInvalidoException() {
        super("Favor informar data, tipo de tijolo e quantidade produzida");
    }
    
}
