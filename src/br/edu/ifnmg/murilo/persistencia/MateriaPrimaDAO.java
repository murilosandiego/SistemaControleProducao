/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.persistencia;

import br.edu.ifnmg.murilo.entidade.Fornecedor;
import br.edu.ifnmg.murilo.entidade.MateriaPrima;
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
public class MateriaPrimaDAO {

    private static final String SQL_INSERT = "INSERT INTO MATERIAPRIMA (DESCRICAO, NOME, NOMEFORNECEDOR, UND, VALOR) VALUES (?, ?, ?, ?,?)";
    private static final String SQL_BUSCAR_TODOS = "SELECT DESCRICAO, NOME, NOMEFORNECEDOR, UND, ID, VALOR FROM MATERIAPRIMA";
    private static final String SQL_DELETE = "DELETE FROM MATERIAPRIMA WHERE ID = ?";
    private static final String SQL_UPDATE = "UPDATE MATERIAPRIMA SET DESCRICAO = ?, NOME = ?, NOMEFORNECEDOR = ?, UND = ? WHERE ID = ?";
    private static final String SQL_BUSCAR_POR_NOME = "SELECT DESCRICAO, NOME, NOMEFORNECEDOR, UND FROM MATERIAPRIMA WHERE NOME = ?";

    public void inserir(MateriaPrima materiaPrima) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_INSERT);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)
            comando.setString(1, materiaPrima.getDescricao());
            comando.setString(2, materiaPrima.getNome());
            comando.setString(3, materiaPrima.getNomeFornecedor());
            comando.setString(4, materiaPrima.getUnd());
            comando.setDouble(5,materiaPrima.getValor());
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

    public List<MateriaPrima> buscarTodos() throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        List<MateriaPrima> listaMateriaPrima = new ArrayList<>();

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
                MateriaPrima materiaPrima = this.extrairLinhaResultadoBuscarTodos(resultado);
                //Adiciona um item à lista que será retornada
                listaMateriaPrima.add(materiaPrima);
            }
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return listaMateriaPrima;
    }

    private MateriaPrima extrairLinhaResultadoBuscarTodos(ResultSet resultado) throws SQLException {
        MateriaPrima materiaPrima = new MateriaPrima();
        materiaPrima.setDescricao(resultado.getString(1));
        materiaPrima.setNome(resultado.getString(2));
        materiaPrima.setNomeFornecedor(resultado.getString(3));
        materiaPrima.setUnd(resultado.getString(4));
        materiaPrima.setId(resultado.getInt(5));
        materiaPrima.setValor(resultado.getDouble(6));

        return materiaPrima;
    }
    

    public void excluir(int id) throws SQLException {
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

    public void atualizar(MateriaPrima materiaPrima) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_UPDATE);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)
            comando.setString(1, materiaPrima.getDescricao());
            comando.setString(2, materiaPrima.getNome());
            comando.setString(3, materiaPrima.getNomeFornecedor());
            comando.setString(4, materiaPrima.getUnd());
            comando.setInt(5, materiaPrima.getId());
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
