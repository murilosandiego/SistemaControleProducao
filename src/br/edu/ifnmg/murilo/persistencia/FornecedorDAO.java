/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.persistencia;

import br.edu.ifnmg.murilo.entidade.Fornecedor;
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
public class FornecedorDAO {

    private static final String SQL_INSERT = "INSERT INTO FORNECEDOR (FORNECEDOR, LOGRADOURO, COMPLEMENTO, NUMERO, BAIRRO, CIDADE, UF, CEP, EMAIL, CNPJ, TELEFONE, CELULAR, OBSERVACAO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)";
    private static final String SQL_BUSCAR_TODOS = "SELECT FORNECEDOR, LOGRADOURO, COMPLEMENTO, NUMERO, BAIRRO, CIDADE, UF, CEP, EMAIL, CNPJ, TELEFONE, CELULAR, OBSERVACAO FROM FORNECEDOR";
    private static final String SQL_DELETE = "DELETE FROM FORNECEDOR WHERE CNPJ = ? ";
    private static final String SQL_UPDATE = "UPDATE FORNECEDOR SET FORNECEDOR = ?, LOGRADOURO = ?, COMPLEMENTO = ?, NUMERO = ?, BAIRRO = ?, CIDADE = ?, UF = ?, CEP = ?, EMAIL = ?, TELEFONE = ?, CELULAR = ?, OBSERVACAO = ? WHERE CNPJ = ?";
    private static final String SQL_BUSCAR_POR_CNPJ = "SELECT FORNECEDOR, LOGRADOURO, COMPLEMENTO, NUMERO, BAIRRO, CIDADE, UF, CEP, EMAIL, CNPJ, TELEFONE, CELULAR, OBSERVACAO FROM FORNECEDOR WHERE CNPJ = ?";
    
    public void inserir(Fornecedor fornecedor) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_INSERT);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)
            comando.setString(1, fornecedor.getFornecedor());
            comando.setString(2, fornecedor.getLogradouro());
            comando.setString(3, fornecedor.getComplemento());
            comando.setString(4, fornecedor.getNumero());
            comando.setString(5, fornecedor.getBairro());
            comando.setString(6, fornecedor.getCidade());
            comando.setString(7, fornecedor.getUf());
            comando.setString(8, fornecedor.getCep());
            comando.setString(9, fornecedor.getEmail());
            comando.setString(10, fornecedor.getCnpj());
            comando.setString(11, fornecedor.getTelefone());
            comando.setString(12, fornecedor.getCelular());
            comando.setString(13, fornecedor.getObservacao());
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

    public List<Fornecedor> buscarTodos() throws SQLException {

        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        List<Fornecedor> listaFornecedores = new ArrayList<>();

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
                Fornecedor fornecedor = this.extrairLinhaResultadoBuscarTodos(resultado);
                //Adiciona um item à lista que será retornada
                listaFornecedores.add(fornecedor);
            }
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return listaFornecedores;
    }

    private Fornecedor extrairLinhaResultadoBuscarTodos(ResultSet resultado) throws SQLException {
        
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setFornecedor(resultado.getString(1));
        fornecedor.setLogradouro(resultado.getString(2));
        fornecedor.setComplemento(resultado.getString(3));
        fornecedor.setNumero(resultado.getString(4));
        fornecedor.setBairro(resultado.getString(5));
        fornecedor.setCidade(resultado.getString(6));
        fornecedor.setUf(resultado.getString(7));
        fornecedor.setCep(resultado.getString(8));
        fornecedor.setEmail(resultado.getString(9));
        fornecedor.setCnpj(resultado.getString(10));
        fornecedor.setTelefone(resultado.getString(11));
        fornecedor.setCelular(resultado.getString(12));
        fornecedor.setObservacao(resultado.getString(13));
        
        return fornecedor;
    }

    public void excluir(String cnpj) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_DELETE);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)            
            comando.setString(1, cnpj);
            
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

    public void atualizar(Fornecedor fornecedor) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_UPDATE);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)
            comando.setString(1, fornecedor.getFornecedor());
            comando.setString(2, fornecedor.getLogradouro());
            comando.setString(3, fornecedor.getComplemento());
            comando.setString(4, fornecedor.getNumero());
            comando.setString(5, fornecedor.getBairro());
            comando.setString(6, fornecedor.getCidade());
            comando.setString(7, fornecedor.getUf());
            comando.setString(8, fornecedor.getCep());
            comando.setString(9, fornecedor.getEmail());           
            comando.setString(10, fornecedor.getTelefone());
            comando.setString(11, fornecedor.getCelular());
            comando.setString(12, fornecedor.getObservacao());
            comando.setString(13, fornecedor.getCnpj());
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
    
    public Fornecedor validarCnpjDuplicado(String cnpj) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        Fornecedor fornecedor = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de consulta dos dados
            comando = conexao.prepareStatement(SQL_BUSCAR_POR_CNPJ);
            comando.setString(1, cnpj);
            //Executa o comando e obtém o resultado da consulta
            resultado = comando.executeQuery();
            //O método next retornar boolean informando se existe um próximo
            //elemento para iterar
            if (resultado.next()) {
                fornecedor = this.extrairLinhaResultadoBuscarTodos(resultado);
            }
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        if (fornecedor == null) {
            throw new ConsultaSemResultadoException();
        }
        return fornecedor;
    }
}
