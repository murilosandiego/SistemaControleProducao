/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.persistencia;

import br.edu.ifnmg.murilo.entidade.Funcionario;
import br.edu.ifnmg.murilo.excecao.ConsultaSemResultadoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author murilo
 */
public class FuncionarioDAO {

    private static final String SQL_INSERT = "INSERT INTO FUNCIONARIO (BAIRRO, CELULAR, CEP, CIDADE, COMPLEMENTO, CPF, "
            + "EMAIL, LOGRADOURO, NOME, NUMERO, OBSERVACAO, TELEFONE, UF, DATA_ADMISSAO, DATA_NASCIMENTO) VALUES"
            + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_BUSCAR_TODOS = "SELECT BAIRRO, CELULAR, CEP, CIDADE, COMPLEMENTO, CPF, "
            + "EMAIL, LOGRADOURO, NOME, NUMERO, OBSERVACAO, TELEFONE, UF, DATA_ADMISSAO, DATA_NASCIMENTO, ID FROM FUNCIONARIO";
    private static final String SQL_DELETE = "DELETE FROM FUNCIONARIO WHERE ID = ?";
    private static final String SQL_UPDATE = "UPDATE FUNCIONARIO SET BAIRRO = ?, CELULAR =?, CEP =?, CIDADE =?, COMPLEMENTO =?, CPF =?, "
            + "EMAIL =?, LOGRADOURO =?, NOME =?, NUMERO =?, OBSERVACAO =?, TELEFONE =?, UF =?, DATA_ADMISSAO =?, DATA_NASCIMENTO =? WHERE ID =?";
   
    private static final String SQL_BUSCAR_POR_CPF = "SELECT BAIRRO, CELULAR, CEP, CIDADE, COMPLEMENTO, CPF, "
            + "EMAIL, LOGRADOURO, NOME, NUMERO, OBSERVACAO, TELEFONE, UF, DATA_ADMISSAO, DATA_NASCIMENTO, ID FROM FUNCIONARIO WHERE CPF = ?";
    
    public void inserir(Funcionario funcionario) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_INSERT);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)
            comando.setString(1, funcionario.getBairro());
            comando.setString(2, funcionario.getCelular());
            comando.setString(3, funcionario.getCep());
            comando.setString(4, funcionario.getCidade());
            comando.setString(5, funcionario.getComplemento());
            comando.setString(6, funcionario.getCpf());
            comando.setString(7, funcionario.getEmail());
            comando.setString(8, funcionario.getLogradouro());
            comando.setString(9, funcionario.getNome());
            comando.setString(10, funcionario.getNumero());
            comando.setString(11, funcionario.getObservacao());
            comando.setString(12, funcionario.getTelefone());
            comando.setString(13, funcionario.getUf());

            java.sql.Date dataAdmissao = new java.sql.Date(funcionario.getDataAdmissao().getTime());
            java.sql.Date dataNascimento = new java.sql.Date(funcionario.getDataNascimento().getTime());

            comando.setDate(14, dataAdmissao);
            comando.setDate(15, dataNascimento);


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

    public List<Funcionario> buscarTodos() throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        List<Funcionario> listaFuncionarios = new ArrayList<>();

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
                Funcionario funcionario = this.extrairLinhaResultadoBuscarTodos(resultado);
                //Adiciona um item à lista que será retornada
                listaFuncionarios.add(funcionario);
            }
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return listaFuncionarios;
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

    public void atualizar(Funcionario funcionario) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_UPDATE);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)
            comando.setString(1, funcionario.getBairro());
            comando.setString(2, funcionario.getCelular());
            comando.setString(3, funcionario.getCep());
            comando.setString(4, funcionario.getCidade());
            comando.setString(5, funcionario.getComplemento());
            comando.setString(6, funcionario.getCpf());
            comando.setString(7, funcionario.getEmail());
            comando.setString(8, funcionario.getLogradouro());
            comando.setString(9, funcionario.getNome());
            comando.setString(10, funcionario.getNumero());
            comando.setString(11, funcionario.getObservacao());
            comando.setString(12, funcionario.getTelefone());
            comando.setString(13, funcionario.getUf());

            java.sql.Date dataAdmissao = new java.sql.Date(funcionario.getDataAdmissao().getTime());
            java.sql.Date dataNascimento = new java.sql.Date(funcionario.getDataNascimento().getTime());

            comando.setDate(14, dataAdmissao);
            comando.setDate(15, dataNascimento);

            
            comando.setInt(16, funcionario.getId());
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

    private Funcionario extrairLinhaResultadoBuscarTodos(ResultSet resultado) throws SQLException {
        Funcionario funcionario = new Funcionario();

        funcionario.setBairro(resultado.getString(1));
        funcionario.setCelular(resultado.getString(2));
        funcionario.setCep(resultado.getString(3));
        funcionario.setCidade(resultado.getString(4));
        funcionario.setComplemento(resultado.getString(5));
        funcionario.setCpf(resultado.getString(6));
        funcionario.setEmail(resultado.getString(7));
        funcionario.setLogradouro(resultado.getString(8));
        funcionario.setNome(resultado.getString(9));
        funcionario.setNumero(resultado.getString(10));
        funcionario.setObservacao(resultado.getString(11));
        funcionario.setTelefone(resultado.getString(12));
        funcionario.setUf(resultado.getString(13));

//        java.sql.Date dataAdmissao = resultado.getDate(15);
//        funcionario.setDataAdmissao(new Date(dataAdmissao.getTime()));

        java.util.Date data = (resultado.getTimestamp(14));
        funcionario.setDataAdmissao(data);
        
        java.sql.Date dataNascimento = resultado.getDate(15);
        funcionario.setDataNascimento(new Date(dataNascimento.getTime()));
        
        funcionario.setId(resultado.getInt(16));
        
        return funcionario;
    }

    public Funcionario buscarPorCpf(String cpf) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        Funcionario funcionario = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de consulta dos dados
            comando = conexao.prepareStatement(SQL_BUSCAR_POR_CPF);
            comando.setString(1, cpf);
            //Executa o comando e obtém o resultado da consulta
            resultado = comando.executeQuery();
            //O método next retornar boolean informando se existe um próximo
            //elemento para iterar
            if (resultado.next()) {
                funcionario = this.extrairLinhaResultadoBuscarTodos(resultado);
            }
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        if (funcionario == null) {
            throw new ConsultaSemResultadoException();
        }
        return funcionario;
    }

}
