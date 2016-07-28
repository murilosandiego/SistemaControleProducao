/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.persistencia;

import br.edu.ifnmg.murilo.entidade.MateriaPrima;
import br.edu.ifnmg.murilo.entidade.Tijolo;
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
public class TijoloDAO {

    private static final String SQL_INSERT = "INSERT INTO TIJOLO (DESCRICAO, DIMENSOES, OBSERVACOES, TIPO, VALOR_VENDA) VALUES (?,?,?,?,?)";
    private static final String SQL_BUSCAR_TODOS = "SELECT DESCRICAO, DIMENSOES, OBSERVACOES, TIPO, ID, VALOR_VENDA FROM TIJOLO";
    private static final String SQL_DELETE = "DELETE FROM TIJOLO WHERE ID = ?";
    private static final String SQL_UPDATE = "UPDATE TIJOLO SET DESCRICAO = ?, DIMENSOES = ?, OBSERVACOES = ?, TIPO = ?, VALOR_VENDA = ? WHERE ID = ?";

    public void inserir(Tijolo tijolo) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_INSERT);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)
            comando.setString(1, tijolo.getDescricao());
            comando.setString(2, tijolo.getDimensoes());
            comando.setString(3, tijolo.getObservacoes());
            comando.setString(4, tijolo.getTipo());
            comando.setDouble(5, tijolo.getValorVenda());
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

    public List<Tijolo> buscarTodos() throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        List<Tijolo> listaTijolo = new ArrayList<>();

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
                Tijolo tijolo = this.extrairLinhaResultadoBuscarTodos(resultado);
                //Adiciona um item à lista que será retornada
                listaTijolo.add(tijolo);
            }
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return listaTijolo;
    }

    private Tijolo extrairLinhaResultadoBuscarTodos(ResultSet resultado) throws SQLException {
        Tijolo tijolo = new Tijolo();
        tijolo.setDescricao(resultado.getString(1));
        tijolo.setDimensoes(resultado.getString(2));
        tijolo.setObservacoes(resultado.getString(3));
        tijolo.setTipo(resultado.getString(4));
        tijolo.setId(resultado.getInt(5));
        tijolo.setValorVenda(resultado.getDouble(6));

        return tijolo;
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

    public void atualizar(Tijolo tijolo) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_UPDATE);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)
            comando.setString(1, tijolo.getDescricao());
            comando.setString(2, tijolo.getDimensoes());
            comando.setString(3, tijolo.getObservacoes());
            comando.setString(4, tijolo.getTipo());
            comando.setDouble(5, tijolo.getValorVenda());
            comando.setInt(6, tijolo.getId());
            
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

}
