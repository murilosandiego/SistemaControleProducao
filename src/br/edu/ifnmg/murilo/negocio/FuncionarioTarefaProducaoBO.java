/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.negocio;

import br.edu.ifnmg.murilo.entidade.FuncionarioTarefaProducao;
import br.edu.ifnmg.murilo.persistencia.FuncionarioTarefaProducaoDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author murilo
 */
public class FuncionarioTarefaProducaoBO {
    
    public List<FuncionarioTarefaProducao> buscarFuncionarioPorProducao() throws SQLException{
        FuncionarioTarefaProducaoDAO funcionarioTarefaProducaoDAO = new FuncionarioTarefaProducaoDAO();
            return funcionarioTarefaProducaoDAO.buscarFuncionarioPorProducao();
        
    }
    
    public List<FuncionarioTarefaProducao> buscarSalarioFuncionarioPorMes() throws SQLException{
        FuncionarioTarefaProducaoDAO funcionarioTarefaProducaoDAO = new FuncionarioTarefaProducaoDAO();
            return funcionarioTarefaProducaoDAO.buscarSalarioFuncionarioPorMes();
    }
}
