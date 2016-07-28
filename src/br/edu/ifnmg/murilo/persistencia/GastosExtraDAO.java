/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.persistencia;

import br.edu.ifnmg.murilo.entidade.GastosExtra;
import br.edu.ifnmg.murilo.entidade.Tijolo;
import br.edu.ifnmg.murilo.excecao.GastosExtrasNaoCadastradoException;
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
public class GastosExtraDAO {

    private static final String SQL_INSERT = "INSERT INTO GASTOSEXTRA (OUTROS, AGUA, LUZ) VALUES (?,?,?)";
    private static final String SQL_BUSCAR_TODOS = "SELECT OUTROS, AGUA, LUZ, ID FROM GASTOSEXTRA";
    private static final String SQL_UPDATE = "UPDATE GASTOSEXTRA SET OUTROS = ?, AGUA = ?, LUZ = ? WHERE ID = ?";

    public void inserir(GastosExtra gastosExtra) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_INSERT);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)
            comando.setDouble(1, gastosExtra.getOutrasGastos());
            comando.setDouble(2, gastosExtra.getTarifaAgua());
            comando.setDouble(3, gastosExtra.getTarifaLuz());

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

    public void atualiar(GastosExtra gastosExtra) throws SQLException {

        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_UPDATE);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)
            comando.setDouble(1, gastosExtra.getOutrasGastos());
            comando.setDouble(2, gastosExtra.getTarifaAgua());
            comando.setDouble(3, gastosExtra.getTarifaLuz());
            comando.setInt(4, gastosExtra.getId());

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

    public GastosExtra buscarTodos() throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        GastosExtra gastosExtra = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de consulta dos dados
            comando = conexao.prepareStatement(SQL_BUSCAR_TODOS);
            //Executa o comando e obtém o resultado da consulta
            resultado = comando.executeQuery();
            //O método next retornar boolean informando se existe um próximo
            //elemento para iterar
            if (resultado.next()) {
                gastosExtra = new GastosExtra();
                gastosExtra.setOutrasGastos(resultado.getDouble(1));
                gastosExtra.setTarifaAgua(resultado.getDouble(2));
                gastosExtra.setTarifaLuz(resultado.getDouble(3));
                gastosExtra.setId(resultado.getInt(4));
            }
        } catch (Exception e) {

            throw  new GastosExtrasNaoCadastradoException("Nenhum gasto cadastrado");
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return gastosExtra;
    }

}
