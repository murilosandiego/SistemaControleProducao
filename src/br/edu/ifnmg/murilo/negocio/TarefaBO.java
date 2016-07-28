/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.negocio;

import br.edu.ifnmg.murilo.entidade.Tarefa;
import br.edu.ifnmg.murilo.excecao.ConsultaSemResultadoException;
import br.edu.ifnmg.murilo.excecao.TarefaDuplicadoException;
import br.edu.ifnmg.murilo.persistencia.TarefaDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author murilo
 */
public class TarefaBO {
    
    public void inserir(Tarefa tarefa) throws SQLException{
        TarefaDAO tarefaDAO = new TarefaDAO();
        this.validarNomeDuplicado(tarefa);
        tarefaDAO.inserir(tarefa);
    }

    public List<Tarefa> buscarTodos() throws SQLException{
        TarefaDAO tarefaDAO = new TarefaDAO();
        return tarefaDAO.buscarTodos();
    }

    public void atualizar(Tarefa tarefaEmEdicao) throws SQLException {
        TarefaDAO tarefaDAO = new TarefaDAO();
        tarefaDAO.atualizar(tarefaEmEdicao);
    }

    public void excluir(Integer id) throws SQLException {
        TarefaDAO tarefaDAO = new TarefaDAO();
        tarefaDAO.excluir(id);
    }
    
    public void validarNomeDuplicado(Tarefa tarefa) throws SQLException{
        TarefaDAO tarefaDAO =  new TarefaDAO();
        try{
            Tarefa tarefaEncontrada = tarefaDAO.validarTarefaDuplicada(tarefa.getNome());
            throw new TarefaDuplicadoException(tarefa.getNome());
        }catch(ConsultaSemResultadoException e){
            //Está não é preciso fazer nada. Não existe 
        }        
    }
}
