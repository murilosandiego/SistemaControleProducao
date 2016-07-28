/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.negocio;

import br.edu.ifnmg.murilo.entidade.Tijolo;
import br.edu.ifnmg.murilo.persistencia.TijoloDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author murilo
 */
public class TijoloBO {
    
    public void inserir(Tijolo tijolo) throws SQLException{
        TijoloDAO tijoloDAO = new TijoloDAO();
        tijoloDAO.inserir(tijolo);
    }

    public List<Tijolo> buscarTodos() throws SQLException {
        TijoloDAO tijoloDAO = new TijoloDAO();
        return tijoloDAO.buscarTodos();
    }

    public void excluir(Integer id) throws SQLException {
        TijoloDAO tijoloDAO = new TijoloDAO();
        tijoloDAO.excluir(id);
    }

    public void atualizar(Tijolo tijolo) throws SQLException {
        TijoloDAO tijoloDAO = new TijoloDAO();
        tijoloDAO.atualizar(tijolo);
    }
}
