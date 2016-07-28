/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.persistencia;

import br.edu.ifnmg.murilo.entidade.Tarefa;
import br.edu.ifnmg.murilo.entidade.Usuario;
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
public class UsuarioDAO {

    private static final String SQL_SELECT_BY_LOGIN = "SELECT NOME, PERMISSAO, SENHA, ID, LOGIN FROM USUARIO WHERE LOGIN = ?";
    private static final String SQL_INSERT = "INSERT INTO USUARIO (LOGIN, NOME, SENHA) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_BY_LOGIN_AND_SENHA = "SELECT NOME, PERMISSAO, SENHA, ID, LOGIN FROM USUARIO WHERE LOGIN = ? AND SENHA = ?";
    private static final String SQL_BUSCAR_TODOS = "SELECT NOME, PERMISSAO, LOGIN, ID FROM USUARIO";
    private static final String SQL_UPDATE = "UPDATE USUARIO SET LOGIN = ?, NOME = ?, SENHA =? WHERE ID = ?";
    private static final String SQL_DELETE = "DELETE FROM USUARIO WHERE ID = ?";
    
    public Usuario buscarByLogin(String login) throws SQLException {
        Usuario usuario = null;
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        try {
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(SQL_SELECT_BY_LOGIN);
            comando.setString(1, login);
            resultado = comando.executeQuery();

            while (resultado.next()) {
                usuario = this.extrairLinhaResultado(resultado);
            }

        }  finally {
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return usuario;
    }

    public Usuario extrairLinhaResultado(ResultSet resultado) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setNome(resultado.getString(1));
        usuario.setPermissao(resultado.getInt(2));
        usuario.setSenha(resultado.getString(3));
        usuario.setId(resultado.getInt(4));
        usuario.setLogin(resultado.getString(1));

        return usuario;
    }

    public void inserir(Usuario usuario) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_INSERT);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)
            comando.setString(1, usuario.getLogin());
            comando.setString(2, usuario.getNome());
            comando.setString(3, usuario.getSenha());
            
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

    public Usuario buscarByLoginAndSenha(String login, String senha) throws SQLException {
        Usuario usuario = null;
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;
        try {
            conexao = BancoDadosUtil.getConnection();
            comando = conexao.prepareStatement(SQL_SELECT_BY_LOGIN_AND_SENHA);
            comando.setString(1, login);
            comando.setString(2, senha);
            resultado = comando.executeQuery();

            while (resultado.next()) {
                usuario = this.extrairLinhaResultado(resultado);
            }

        }  finally {
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return usuario;
    }

    public List<Usuario> buscarTodos() throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;
        ResultSet resultado = null;

        List<Usuario> listaUsuarios = new ArrayList<>();

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
                Usuario usuario = this.extrairLinhaResultadoBuscarTodos(resultado);
                //Adiciona um item à lista que será retornada
                listaUsuarios.add(usuario);
            }
        } finally {
            //Todo objeto que referencie o banco de dados deve ser fechado
            BancoDadosUtil.fecharChamadasBancoDados(conexao, comando, resultado);
        }
        return listaUsuarios;
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

    private Usuario extrairLinhaResultadoBuscarTodos(ResultSet resultado) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setNome(resultado.getString(1));
        usuario.setPermissao(resultado.getInt(2));
        usuario.setLogin(resultado.getString(3));
        usuario.setId(resultado.getInt(4));
        
        return usuario;
    }

    public void atualizar(Usuario usuario) throws SQLException {
        Connection conexao = null;
        PreparedStatement comando = null;

        try {
            //Recupera a conexão
            conexao = BancoDadosUtil.getConnection();
            //Cria o comando de inserir dados
            comando = conexao.prepareStatement(SQL_UPDATE);
            //Atribui os parâmetros (Note que no BD o index inicia por 1)
            comando.setString(1, usuario.getLogin());
            comando.setString(2, usuario.getNome());
            comando.setString(3, usuario.getSenha());
            comando.setInt(4, usuario.getId());
            
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
