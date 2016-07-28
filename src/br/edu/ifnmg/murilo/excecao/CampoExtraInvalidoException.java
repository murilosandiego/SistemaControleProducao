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
public class CampoExtraInvalidoException extends SistemaControleProducaoException{

    public CampoExtraInvalidoException() {
        super("Favor verificar os campos de gasto extra");
    }
    
}
