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
public class GastoTotalInvalidoExcpetion extends SistemaControleProducaoException{

    public GastoTotalInvalidoExcpetion() {
        super("Gasto total inválido\nFavor verificar os campos de gastos");
    }
    
}
