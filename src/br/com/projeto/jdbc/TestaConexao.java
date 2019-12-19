/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.jdbc;

import br.com.projeto.view.frmLogin;
import javax.swing.JOptionPane;

/**
 *
 * @author Arthur
 */
public class TestaConexao {
    
    
    public static void main(String[] args) {
        
        try {
            
            new ConnectionFactory().getConnection();
            JOptionPane.showMessageDialog(null, "Conectado com sucesso!");
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Ops aconteceu o erro: " + e);
        }
        
        
        
    }
    
}
