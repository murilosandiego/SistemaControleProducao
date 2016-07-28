/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.persistencia;

import br.edu.ifnmg.murilo.entidade.FuncionarioTarefaProducao;
import br.edu.ifnmg.murilo.entidade.Producao;
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
public class FuncionarioTarefaProducaoDAO {

    private static final String SQL_SELECT_FUNCIONARIO_POR_PRODUCAO = "SELECT  F.NOME, EXTRACT(YEAR FROM P.DATA) AS ANO,\n"
            + "SUM(QUANTIDADE) AS QUANTIDADE_PRODUZIDA FROM \n"
            + "      FUNCIONARIO F \n"
            + "   	JOIN TAREFAFUNCIONARIO TF\n"
            + "		ON F.ID = TF.ID_FUNCIONARIO\n"
            + "   	JOIN TAREFA T\n"
            + "		ON T.ID = TF.ID_TAREFA\n"
            + "   	JOIN PRODUCAO P\n"
            + "		ON P.ID = TF.ID_PRODUCAO\n"
            + "GROUP BY F.ID, ANO;";
    private static final String SQL_BUSCAR_SALARIO_FUCIONARIO_POR_MES = "  SELECT F.NOME, MONTH(P.DATA), YEAR(P.DATA), SUM(VALOR_DIARIA) AS SALARIO FROM \n"
            + "   FUNCIONARIO F JOIN TAREFAFUNCIONARIO TF\n"
            + "        ON F.ID = TF.ID_FUNCIONARIO\n"
            + "              JOIN PRODUCAO P\n"
            + "         ON P.ID = TF.ID_PRODUCAO\n"
            + "GROUP BY F.NOME, MONTH(P.DATA),YEAR(P.DATA);";

    public List<FuncionarioTarefaProducao> buscarFuncionarioPorProducao() throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        List<FuncionarioTarefaProducao> listafuTarefaProducoes = new ArrayList<>();

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de consulta dos dados
            comando = conexao.prepareStatement(SQL_SELECT_FUNCIONARIO_POR_PRODUCAO);
            //Executa o comando e obtém o resultado da consulta
            resultado = comando.executeQuery();
            //O método next retornar boolean informando se existe um próximo
            //elemento para iterar
            while (resultado.next()) {
                FuncionarioTarefaProducao funcionarioTarefaProducao = new FuncionarioTarefaProducao();

                funcionarioTarefaProducao.setNomeFuncionario(resultado.getString(1));
                //     funcionarioTarefaProducao.setMes(resultado.getInt(2));
                funcionarioTarefaProducao.setAno(resultado.getInt(2));
                funcionarioTarefaProducao.setQuantidade(resultado.getInt(3));
                //Adiciona um item à lista que será retornada
                listafuTarefaProducoes.add(funcionarioTarefaProducao);
            }
        } catch (Exception e) {
            //Caso aconteça alguma exeção é feito um rollback para o banco de
            //dados retornar ao seu estado anterior.
            if (conexao != null) {
                conexao.rollback();
            }
            throw e;
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return listafuTarefaProducoes;
    }

    public List<FuncionarioTarefaProducao> buscarSalarioFuncionarioPorMes() throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        List<FuncionarioTarefaProducao> listafuTarefaProducoes = new ArrayList<>();

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de consulta dos dados
            comando = conexao.prepareStatement(SQL_BUSCAR_SALARIO_FUCIONARIO_POR_MES);
            //Executa o comando e obtém o resultado da consulta
            resultado = comando.executeQuery();
            //O método next retornar boolean informando se existe um próximo
            //elemento para iterar
            while (resultado.next()) {
                FuncionarioTarefaProducao funcionarioTarefaProducao = new FuncionarioTarefaProducao();

                funcionarioTarefaProducao.setNomeFuncionario(resultado.getString(1));
                funcionarioTarefaProducao.setMes(resultado.getInt(2));
                funcionarioTarefaProducao.setAno(resultado.getInt(3));
                funcionarioTarefaProducao.setSalario(resultado.getDouble(4));
                //Adiciona um item à lista que será retornada
                listafuTarefaProducoes.add(funcionarioTarefaProducao);
            }
        } catch (Exception e) {
            //Caso aconteça alguma exeção é feito um rollback para o banco de
            //dados retornar ao seu estado anterior.
            if (conexao != null) {
                conexao.rollback();
            }
            throw e;
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return listafuTarefaProducoes;
    }
}
