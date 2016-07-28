/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.negocio;

import br.edu.ifnmg.murilo.entidade.GastosExtra;
import br.edu.ifnmg.murilo.persistencia.GastosExtraDAO;
import java.sql.SQLException;

/**
 *
 * @author murilo
 */
public class GastosExtraBO {
    
    
    public void inserir(GastosExtra gastosExtra) throws SQLException{
        GastosExtraDAO gastosExtraDAO = new GastosExtraDAO();
        gastosExtraDAO.inserir(gastosExtra);
    }
    
    public GastosExtra buscar() throws SQLException{
        GastosExtraDAO gastosExtraDAO = new GastosExtraDAO();
        return gastosExtraDAO.buscarTodos();
    }
    
    public void atualizar(GastosExtra gastosExtra) throws SQLException{
        GastosExtraDAO gastosExtraDAO = new GastosExtraDAO();
        gastosExtraDAO.atualiar(gastosExtra);
    }
}
