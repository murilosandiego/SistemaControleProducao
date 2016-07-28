/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.negocio;

import br.edu.ifnmg.murilo.entidade.Producao;
import br.edu.ifnmg.murilo.entidade.FuncionarioTarefaProducao;
import br.edu.ifnmg.murilo.persistencia.ProducaoDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author murilo
 */
public class ProducaoBO {
    
    
    public void inserir(Producao producao, List<FuncionarioTarefaProducao> listaTarefaFuncionarios) throws SQLException{
        ProducaoDAO producaoDAO = new ProducaoDAO();
        producaoDAO.inserir(producao, listaTarefaFuncionarios);
    }
    
    public List<Producao> buscarTodos() throws SQLException{
        ProducaoDAO producaoDAO = new ProducaoDAO();
        return producaoDAO.buscarTodos();
    }
    
    public List<Producao> buscarValorFuncionarioPorProducao() throws SQLException{
        ProducaoDAO producaoDAO = new ProducaoDAO();
        return producaoDAO.buscarGastosDetalhados();
    }
}
