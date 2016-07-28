/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.negocio;

import br.edu.ifnmg.murilo.entidade.Fornecedor;
import br.edu.ifnmg.murilo.excecao.ConsultaSemResultadoException;
import br.edu.ifnmg.murilo.excecao.FornecedorCnpjDuplicadoException;
import br.edu.ifnmg.murilo.persistencia.FornecedorDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author murilo
 */
public class FornecedorBO {
    
    public void inserir(Fornecedor fornecedor) throws SQLException{
        
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        this.validarCnpjDuplicado(fornecedor);
        fornecedorDAO.inserir(fornecedor);
    }
    
    public List<Fornecedor> buscarTodos() throws SQLException{
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        return fornecedorDAO.buscarTodos();
    }

    public void excluir(String cnpj) throws SQLException {
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        fornecedorDAO.excluir(cnpj);
        
    }

    public void atualizar(Fornecedor fornecedor) throws SQLException {
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        fornecedorDAO.atualizar(fornecedor);
    }
    
    public void validarCnpjDuplicado(Fornecedor fornecedor) throws SQLException{
        FornecedorDAO fornecedorDAO =  new FornecedorDAO();
        try{
            Fornecedor fornecedorEncontrado = fornecedorDAO.validarCnpjDuplicado(fornecedor.getCnpj());
            throw new FornecedorCnpjDuplicadoException(fornecedor.getCnpj());
        }catch(ConsultaSemResultadoException e){
            //Está não é preciso fazer nada. Não existe 
        }        
    }
    
    
}
