/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.negocio;

import br.edu.ifnmg.murilo.entidade.Usuario;
import br.edu.ifnmg.murilo.excecao.ArgumentInvalidExeception;
import br.edu.ifnmg.murilo.excecao.UsuarioLoginExistenteException;
import br.edu.ifnmg.murilo.persistencia.UsuarioDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author murilo
 */
public class UsuarioBO {
 
    
    public void verificaUsuarioLogin(Usuario usuario) throws SQLException {
        Usuario usuarioExistente = null;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioExistente = usuarioDAO.buscarByLogin(usuario.getLogin());
        if (usuarioExistente != null) {
            throw new UsuarioLoginExistenteException("Existe um usuario cadastrado com esse login.");
        }
    }

    public void inserir(Usuario usuario) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.inserir(usuario);
    }

   
    
    public Usuario login(String login, String senha) throws SQLException {
        Usuario usuarioExistente = null;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioExistente = usuarioDAO.buscarByLoginAndSenha(login, senha);
        if (usuarioExistente != null) {
            return usuarioExistente;
        }else{
            throw new ArgumentInvalidExeception("Login ou inválidos.\n Tente novamento efetuar o login.");
        }
    }

    public List<Usuario> buscarTodos() throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.buscarTodos();
    }

    public void excluir(Integer id) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.excluir(id);
    }

    public void atualizar(Usuario usuarioEmEdicao) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.atualizar(usuarioEmEdicao);
    }
    
}
