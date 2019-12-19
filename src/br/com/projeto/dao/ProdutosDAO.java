/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.dao;

import br.com.projeto.jdbc.ConnectionFactory;
import br.com.projeto.model.Fornecedores;

import br.com.projeto.model.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Arthur
 */
public class ProdutosDAO {

    private Connection con;

    // construtor da classe 
    public ProdutosDAO() {
        this.con = new ConnectionFactory().getConnection();

    }

    //método cadastrar Produtos
    public void cadastrarProdutos(Produtos obj) {

        try {
            String sql = "insert into tb_produtos (descricao, preco, qtd_estoque, for_id) values (?,?,?,?)";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());

            stmt.setInt(4, obj.getFornecedor().getId());

            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Produto Casdastrado com Sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);

        }

    }

    public void excluirProduto(Produtos obj) {

        try {
            // 1 passo, criar o comando sql
            String sql = "delete from tb_produtos where id=?";

            // 2 passo,  conectar o banco de dados e organizar o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getId());
       

            // 3 passo, executar o comando sql
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Excluido com Sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
        

        
        
    }
    
    public void alterarProduto(Produtos obj) {
        
               try {
            // 1 passo, criar o comando sql
            String sql = "update tb_produtos set descricao=?,preco=?,qtd_estoque=?,for_id=? where id=?";
                   

            // 2 passo,  conectar o banco de dados e organizar o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());

            stmt.setInt(4, obj.getFornecedor().getId());
            stmt.setInt(5, obj.getId());
            
            // 3 passo, executar o comando sql
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Alterado com Sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
        
        
    }
    
    
    
    public List<Produtos> listarProdutos() {

        try {
            //1 passo criar a lista
            List<Produtos> lista = new ArrayList<>();

            //2 passo criar o sql, organizar e executar
            //foi usado o "INNER JOIN", pois estou usando dados de duas tabelas diferentes
            //Então sera que criar dois objetos das classes específicas
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on (p.for_id = f.id)";

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(); // Quando for usar um select, criar um ResultSet

            while (rs.next()) {
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();

                obj.setId(rs.getInt("p.id")); //Lembrando que é ID, não codigo;
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));
                
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);

                lista.add(obj);

            }

            return lista;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro: " + e);

        }
        return null;

    }

    public List<Produtos> buscaProdutoPorNome(String descricao) {

        try {
            //1 passo criar a lista
            List<Produtos> lista = new ArrayList<>();

            //2 passo criar o sql, organizar e executar
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on (p.for_id = f.id) where p.descricao like ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, descricao);
            ResultSet rs = stmt.executeQuery(); // Quando for usar um select, criar um ResultSet

            while (rs.next()) {
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();

                obj.setId(rs.getInt("p.id")); //Lembrando que é ID, não codigo;
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));
                
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);
              

                lista.add(obj);

            }

            return lista;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro: " + e);

        }
        return null;

    }
    
     public Produtos consultaPorNome(String descricao){
        
        try {
            
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on (p.for_id = f.id) where descricao = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, descricao);
            
            ResultSet rs = stmt.executeQuery();
            
            Produtos obj = new Produtos();
            Fornecedores f = new Fornecedores();
            if (rs.next()) {
                

                obj.setId(rs.getInt("id")); //Lembrando que é ID, não codigo;
                obj.setDescricao(rs.getString("descricao"));
                obj.setPreco(rs.getDouble("preco"));
                obj.setQtd_estoque(rs.getInt("qtd_estoque"));
                
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);


            }
            
            return obj;
        } catch (Exception e) {
            
           
            JOptionPane.showMessageDialog(null,"Produto não encontrado." );
            return null;
        }
        
        
        
    }
    
     public Produtos consultaPorCodigo(int codigo){
        
        try {
            
            String sql = "select p.id, p.descricao, p.preco, p.qtd_estoque, f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on (p.for_id = f.id) where p.id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, codigo);
            
            ResultSet rs = stmt.executeQuery();
            
            Produtos obj = new Produtos();
            Fornecedores f = new Fornecedores();
            if (rs.next()) {
                

                obj.setId(rs.getInt("id")); //Lembrando que é ID, não codigo;
                obj.setDescricao(rs.getString("descricao"));
                obj.setPreco(rs.getDouble("preco"));
                obj.setQtd_estoque(rs.getInt("qtd_estoque"));
                
                f.setNome(rs.getString("f.nome"));
                obj.setFornecedor(f);


            }
            
            return obj;
        } catch (Exception e) {
            
           
            JOptionPane.showMessageDialog(null,"Produto não encontrado." );
            return null;
        }
        
        
        
    }
    
    //metodo que dá baixa no estoque
     
     public void baixaEstoque(int id, int qtd_nova){
         try {
             String sql = "update tb_produtos set qtd_estoque = ? where id = ?";
             
             //2 passo - conectar o banco de dados e organizar o comando sql
             PreparedStatement stmt = con.prepareStatement(sql);
             
             stmt.setInt(1, qtd_nova);
             stmt.setInt(2, id);
             stmt.execute();
             stmt.close();
             
             
             
         } catch (Exception e) {
             
             JOptionPane.showMessageDialog(null, "Erro: "+e);
         }
         
     }
    
    
     public int retornaEstoqueAtual(int id){
         
         try {
             int qtd_estoque = 0;
             
             String sql = "select qtd_estoque from tb_produtos where id = ?";
             
             PreparedStatement stmt = con.prepareStatement(sql);
             stmt.setInt(1, id);
             
             ResultSet rs =  stmt.executeQuery();
             
             if(rs.next()){
     
                qtd_estoque = (rs.getInt("qtd_estoque"));
             }
             
             return qtd_estoque;
             
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
  
         
         
         
     }
     
     public void adicionarEstoque(int id, int qtd_nova){
         try {
             String sql = "update tb_produtos set qtd_estoque = ? where id = ?";
             
             //2 passo - conectar o banco de dados e organizar o comando sql
             PreparedStatement stmt = con.prepareStatement(sql);
             
             stmt.setInt(1, qtd_nova);
             stmt.setInt(2, id);
             stmt.execute();
             stmt.close();
             
             
             
         } catch (Exception e) {
             
             JOptionPane.showMessageDialog(null, "Erro: "+e);
         }
         
     }
     
    
    
    
    
    
    
}
