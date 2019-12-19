/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.dao;

import br.com.projeto.jdbc.ConnectionFactory;
import br.com.projeto.model.Clientes;
import br.com.projeto.model.ItemVenda;
import br.com.projeto.model.Produtos;
import br.com.projeto.model.Vendas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Arthur
 */
public class ItensVendaDAO {
    
    private Connection con;
    
    public ItensVendaDAO() {
        this.con = new ConnectionFactory().getConnection();

    }
    
    //metodo que cadastra itens
    
    public void cadastraItem(ItemVenda obj){
        
        try {
         String  sql = "insert into tb_itensvendas (venda_id,produto_id,qtd,subtotal) values (?,?,?,?)";

            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, obj.getVenda().getId());
            stmt.setInt(2, obj.getProduto().getId());
            stmt.setInt(3, obj.getQtd());
            stmt.setDouble(4, obj.getSubtotal());
            
            stmt.execute();
            
            stmt.close();
           

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);

        }
        
        
    }
    
    
    //método que lista itens de uma venda por id
    
    public List<ItemVenda> listarItensPorVenda(int venda_id) {

        try {
            //1 passo criar a lista
            List<ItemVenda> lista = new ArrayList<>();

            //2 passo criar o sql, organizar e executar
            //foi usado o "INNER JOIN", pois estou usando dados de duas tabelas diferentes
            //Então sera que criar dois objetos das classes específicas
            String sql = "select i.id, p.descricao, i.qtd, p.preco, i.subtotal from tb_itensvendas as i"
                    + " inner join tb_produtos as p on(i.produto_id = p.id) where i.venda_id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, venda_id);
            

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ItemVenda item = new ItemVenda();
                Produtos prod = new Produtos();
                
                item.setId(rs.getInt("i.id"));
                prod.setDescricao(rs.getString("p.descricao"));
                item.setQtd(rs.getInt("i.qtd"));
                prod.setPreco(rs.getDouble("p.preco"));
                item.setSubtotal(rs.getDouble("i.subtotal"));
                
                item.setProduto(prod);
                lista.add(item);
            }
            
            return lista;
            

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro: " + e);

        }
        return null;

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
