/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.negocio;


import br.edu.ifnmg.helder.validaCPF.ValidaCPF;
import br.edu.ifnmg.murilo.entidade.Funcionario;
import br.edu.ifnmg.murilo.excecao.ConsultaSemResultadoException;
import br.edu.ifnmg.murilo.excecao.FuncionarioCpfDuplicadoException;
import br.edu.ifnmg.murilo.persistencia.FuncionarioDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author murilo
 */
public class FuncionarioBO {
    
    public void inserir(Funcionario funcionario) throws SQLException{
        
        ValidaCPF.validarCPF(funcionario.getCpf());
        this.validarCpfDuplicado(funcionario);
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionarioDAO.inserir(funcionario);
    }
    
    public List<Funcionario> buscarTodos() throws SQLException{
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        return funcionarioDAO.buscarTodos();
    }

    public void excluir(int id) throws SQLException {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionarioDAO.excluir(id);
        
    }

    public void atualizar(Funcionario funcionario) throws SQLException {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionarioDAO.atualizar(funcionario);
    }
    
    public void validarCpfDuplicado(Funcionario funcionario) throws SQLException{
        FuncionarioDAO funcionarioDAO =  new FuncionarioDAO();
        try{
            Funcionario funcionarioEncontrado = funcionarioDAO.buscarPorCpf(funcionario.getCpf());
            throw new FuncionarioCpfDuplicadoException(funcionario.getCpf());
        }catch(ConsultaSemResultadoException e){
            //Está não é preciso fazer nada. Não existe 
        }        
    }
}
