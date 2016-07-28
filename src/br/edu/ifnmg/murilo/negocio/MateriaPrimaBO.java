/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.murilo.negocio;

import br.edu.ifnmg.murilo.entidade.MateriaPrima;
import br.edu.ifnmg.murilo.persistencia.MateriaPrimaDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author murilo
 */
public class MateriaPrimaBO {
    
    public void inserir(MateriaPrima materiaPrima) throws SQLException{
        MateriaPrimaDAO materiaPrimaDAO = new MateriaPrimaDAO();
        materiaPrimaDAO.inserir(materiaPrima);
    }
    
    public List<MateriaPrima> buscarTodos() throws SQLException{
        MateriaPrimaDAO materiaPrimaDAO = new MateriaPrimaDAO();
        return materiaPrimaDAO.buscarTodos();
    }

    public void excluir(int id) throws SQLException {
        MateriaPrimaDAO materiaPrimaDAO = new MateriaPrimaDAO();
        materiaPrimaDAO.excluir(id);
    }

    public void atualizar(MateriaPrima materiaPrima) throws SQLException {
        MateriaPrimaDAO materiaPrimaDAO = new MateriaPrimaDAO();
        materiaPrimaDAO.atualizar(materiaPrima);
    }

    public MateriaPrima buscarByCodigo(int lerCodigoMateriaPrima) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
