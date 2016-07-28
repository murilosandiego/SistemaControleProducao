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
public class CampoValorDiariaNegativoException extends SistemaControleProducaoException{

    public CampoValorDiariaNegativoException() {
        super("Informe um valor válido para a diaria do funcionário");
    }
    
}
