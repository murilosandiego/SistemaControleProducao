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
public class FornecedorCnpjDuplicadoException extends SistemaControleProducaoException{

    public FornecedorCnpjDuplicadoException(String cnpj) {
        super("Já existe um fornecedor cadastrado com o CNPJ: "+cnpj);
    }
    
}
