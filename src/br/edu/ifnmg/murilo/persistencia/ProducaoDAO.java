/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.persistencia;

import br.edu.ifnmg.murilo.entidade.Producao;
import br.edu.ifnmg.murilo.entidade.FuncionarioTarefaProducao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author murilo
 */
public class ProducaoDAO {

    private static final String SQL_INSERT = "INSERT INTO PRODUCAO (DATA, TIPOTIJOLO, UND, GASTO_AGUA, GASTO_BARRO, GASTO_ELETRICIDADE, GASTO_LENHA, GASTO_OUTROS, GASTO_TOTAL, QUANTIDADE, LUCRO_ESTIMADO, VALOR_VENDA) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_INSERT_TAREFA_FUNCIONARIO = "INSERT INTO TAREFAFUNCIONARIO (ID_PRODUCAO, ID_FUNCIONARIO, ID_TAREFA,VALOR_DIARIA,QUANTIDADE_PRODUZIDA) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_BUSCAR_TODOS = " SELECT DATA, TIPOTIJOLO, UND, GASTO_AGUA, GASTO_BARRO, GASTO_ELETRICIDADE, GASTO_LENHA, GASTO_OUTROS, GASTO_TOTAL, QUANTIDADE, ID, LUCRO_ESTIMADO, VALOR_VENDA FROM PRODUCAO ORDER BY ID";
    private static final String SQL_BUSCA_GASTOS_DETALHADOS = "SELECT P.ID, P.DATA, P.GASTO_LENHA, P.GASTO_BARRO, P.GASTO_ELETRICIDADE, P.GASTO_AGUA, SUM(VALOR_DIARIA) AS GASTO_FUNCIONARIO, P.GASTO_TOTAL, GASTO_OUTROS  FROM FUNCIONARIO F \n"
            + "   JOIN TAREFAFUNCIONARIO TF \n"
            + "	ON F.ID = TF.ID_FUNCIONARIO\n"
            + "   JOIN TAREFA T\n"
            + "	ON T.ID = TF.ID_TAREFA\n"
            + "   JOIN PRODUCAO P\n"
            + "	ON P.ID = TF.ID_PRODUCAO\n"
            + "   GROUP BY P.ID ORDER BY P.ID";

    public void inserir(Producao producao, List<FuncionarioTarefaProducao> listaTarefaFuncionarios) throws SQLException {

        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet result = null;
        //  MercadoriaDAO mercadoriadao = new MercadoriaDAO();

        try {
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            java.sql.Date sqldate = new java.sql.Date(producao.getData().getTime());

            comando.setDate(1, sqldate);
            comando.setString(2, producao.getTipoTijolo());
            comando.setString(3, producao.getUnd());
            comando.setDouble(4, producao.getGastoAgua());
            comando.setDouble(5, producao.getGastoBarro());
            comando.setDouble(6, producao.getGastoEletricidade());
            comando.setDouble(7, producao.getGastoLenha());
            comando.setDouble(8, producao.getGastoOutros());
            comando.setDouble(9, producao.getGastoTotal());
            comando.setInt(10, producao.getQuantidade());
            comando.setDouble(11, producao.getLucroEstimado());
            comando.setDouble(12, producao.getValorDeVenda());
            //Executa o comando
            comando.execute();
            // System.out.println(comando.getGeneratedKeys().getType());
            ResultSet resultSetChave = comando.getGeneratedKeys();

            if (resultSetChave.next()) {
                int chaveProducao = resultSetChave.getInt(1);

                for (FuncionarioTarefaProducao tarefaFuncionario : listaTarefaFuncionarios) {

                    PreparedStatement comandoTarefaFuncionario = conexao.prepareStatement(SQL_INSERT_TAREFA_FUNCIONARIO);

                    comandoTarefaFuncionario.setInt(1, chaveProducao);
                    comandoTarefaFuncionario.setInt(2, tarefaFuncionario.getFuncionario().getId());
                    comandoTarefaFuncionario.setInt(3, tarefaFuncionario.getTarefa().getId());
                    comandoTarefaFuncionario.setDouble(4, tarefaFuncionario.getValorDiaria());
                    comandoTarefaFuncionario.setInt(5, tarefaFuncionario.getQuantidadeProduzida());
                    comandoTarefaFuncionario.execute();
                }
            }

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

    public List<Producao> buscarTodos() throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        List<Producao> listaProducao = new ArrayList<>();

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
                Producao producao = this.extrairLinhaResultadoBuscarTodos(resultado);
                //Adiciona um item à lista que será retornada
                listaProducao.add(producao);
            }
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return listaProducao;

    }

    private Producao extrairLinhaResultadoBuscarTodos(ResultSet resultado) throws SQLException {
        Producao producao = new Producao();

        java.util.Date data = (resultado.getTimestamp(1));
        producao.setData(data);

        producao.setTipoTijolo(resultado.getString(2));
        producao.setUnd(resultado.getString(3));
        producao.setGastoAgua(resultado.getDouble(4));
        producao.setGastoBarro(resultado.getDouble(5));
        producao.setGastoEletricidade(resultado.getDouble(6));
        producao.setGastoLenha(resultado.getDouble(7));
        producao.setGastoOutros(resultado.getDouble(8));
        producao.setGastoTotal(resultado.getDouble(9));
        producao.setQuantidade(resultado.getInt(10));
        producao.setId(resultado.getInt(11));
        producao.setLucroEstimado(resultado.getDouble(12));
        producao.setValorDeVenda(resultado.getDouble(13));
                

        return producao;
    }

    public List<Producao> buscarGastosDetalhados() throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        List<Producao> listaValorFuncionarioPorProducao = new ArrayList<>();

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de consulta dos dados
            comando = conexao.prepareStatement(SQL_BUSCA_GASTOS_DETALHADOS);
            //Executa o comando e obtém o resultado da consulta
            resultado = comando.executeQuery();
            //O método next retornar boolean informando se existe um próximo
            //elemento para iterar
            while (resultado.next()) {
                Producao producao = new Producao();
                producao.setId(resultado.getInt(1));

                java.util.Date data = (resultado.getTimestamp(2));
                producao.setData(data);

                producao.setGastoLenha(resultado.getDouble(3));
                producao.setGastoBarro(resultado.getDouble(4));
                producao.setGastoEletricidade(resultado.getDouble(5));
                producao.setGastoAgua(resultado.getDouble(6));
                producao.setGastoFuncionarios(resultado.getDouble(7));
             //   producao.setGastoTotal(resultado.getDouble(8));
                producao.setGastoOutros(resultado.getDouble(9));
                //Adiciona um item à lista que será retornada
                listaValorFuncionarioPorProducao.add(producao);
            }
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return listaValorFuncionarioPorProducao;
    }
    
    
}
