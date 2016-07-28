/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.persistencia;

import br.edu.ifnmg.murilo.entidade.MateriaPrima;
import br.edu.ifnmg.murilo.entidade.Tarefa;
import br.edu.ifnmg.murilo.excecao.ConsultaSemResultadoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author murilo
 */
public class TarefaDAO {

    private static final String SQL_INSERT = "INSERT INTO TAREFA (DESCRICAO, NOME, OBSERVACAO, VALOR_DIARIA) VALUES (?, ?, ?,?)";
    private static final String SQL_BUSCAR_TODOS = "SELECT DESCRICAO, NOME, OBSERVACAO, VALOR_DIARIA, ID FROM TAREFA";
    private static final String SQL_UPDATE = "UPDATE TAREFA SET DESCRICAO =? , NOME = ?, OBSERVACAO = ?, VALOR_DIARIA = ? WHERE ID = ?";
    private static final String SQL_DELETE = "DELETE FROM TAREFA WHERE ID = ?";
    private static final String SQL_BUSCAR_POR_NOME = "SELECT DESCRICAO, NOME, OBSERVACAO, VALOR_DIARIA, ID FROM TAREFA WHERE UPPER(NOME) = UPPER(?)";
    
    
    public void inserir(Tarefa tarefa) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_INSERT);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)
            comando.setString(1, tarefa.getDescricao());
            comando.setString(2, tarefa.getNome());
            comando.setString(3, tarefa.getObservacao());
            comando.setDouble(4, tarefa.getValorDiaria());
          
            //Executa o comando
            comando.execute();
            //Persiste o comando no banco de dados
            conexao.commit();
        } catch (Exception e) {
            //Caso aconteça alguma exeção é feito um rollback para o banco de
            //dados retornar ao seu estado anterior.
            if (conexao != null) {
                conexao.rollback();
            }
            throw e;
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando);
        }
    }
    
    public void atualizar(Tarefa tarefa) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_UPDATE);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)
            comando.setString(1, tarefa.getDescricao());
            comando.setString(2, tarefa.getNome());
            comando.setString(3, tarefa.getObservacao());
            comando.setDouble(4, tarefa.getValorDiaria());
            comando.setInt(5, tarefa.getId());
          
            //Executa o comando
            comando.execute();
            //Persiste o comando no banco de dados
            conexao.commit();
        } catch (Exception e) {
            //Caso aconteça alguma exeção é feito um rollback para o banco de
            //dados retornar ao seu estado anterior.
            if (conexao != null) {
                conexao.rollback();
            }
            throw e;
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando);
        }
    }

    public List<Tarefa> buscarTodos() throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        List<Tarefa> listaTarefas = new ArrayList<>();

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de consulta dos dados
            comando = conexao.prepareStatement(SQL_BUSCAR_TODOS);
            //Executa o comando e obtém o resultado da consulta
            resultado = comando.executeQuery();
            //O método next retornar boolean informando se existe um próximo
            //elemento para iterar
            while (resultado.next()) {
                Tarefa tarefa = this.extrairLinhaResultadoBuscarTodos(resultado);
                //Adiciona um item à lista que será retornada
                listaTarefas.add(tarefa);
            }
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return listaTarefas;
    }

    private Tarefa extrairLinhaResultadoBuscarTodos(ResultSet resultado) throws SQLException {
        Tarefa tarefa = new Tarefa();
        tarefa.setDescricao(resultado.getString(1));
        tarefa.setNome(resultado.getString(2));
        tarefa.setObservacao(resultado.getString(3));
        tarefa.setValorDiaria(resultado.getDouble(4));
        tarefa.setId(resultado.getInt(5));
        
        return tarefa;
    }

    public void excluir(Integer id) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_DELETE);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)            
            comando.setInt(1, id);

            //Executa o comando
            comando.execute();
            //Persiste o comando no banco de dados
            conexao.commit();
        } catch (Exception e) {
            //Caso aconteça alguma exeção é feito um rollback para o banco de
            //dados retornar ao seu estado anterior.
            if (conexao != null) {
                conexao.rollback();
            }
            throw e;
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando);
        }
    }

    public Tarefa validarTarefaDuplicada(String nome) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        Tarefa tarefa = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de consulta dos dados
            comando = conexao.prepareStatement(SQL_BUSCAR_POR_NOME);
            comando.setString(1, nome);
            //Executa o comando e obtém o resultado da consulta
            resultado = comando.executeQuery();
            //O método next retornar boolean informando se existe um próximo
            //elemento para iterar
            if (resultado.next()) {
                tarefa = this.extrairLinhaResultadoBuscarTodos(resultado);
            }
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        if (tarefa == null) {
            throw new ConsultaSemResultadoException();
        }
        return tarefa;
    
    }

    
}
