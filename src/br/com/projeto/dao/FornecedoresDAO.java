/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.dao;

import br.com.projeto.jdbc.ConnectionFactory;
import br.com.projeto.model.Clientes;
import br.com.projeto.model.Fornecedores;
import br.com.projeto.model.WebServiceCep;

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
public class FornecedoresDAO {
    
    private Connection con;

    // construtor da classe 
    public FornecedoresDAO() {
        this.con = new ConnectionFactory().getConnection();

    }
    
    
    //Metedo casdastrarFornecedores
    public void cadastrarFornecedores(Fornecedores obj) {

        try {
            // 1 passo, criar o comando sql
            String sql = "insert into tb_fornecedores(nome,cnpj,email,telefone,celular,cep,endereco,numero,complemento,bairro,cidade,estado) values (?,?,?,?,?,?,?,?,?,?,?,?) ";

            // 2 passo,  conectar o banco de dados e organizar o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getCnpj());
            stmt.setString(3, obj.getEmail());
            stmt.setString(4, obj.getTelefone());
            stmt.setString(5, obj.getCelular());
            stmt.setString(6, obj.getCep());
            stmt.setString(7, obj.getEndereco());
            stmt.setInt(8, obj.getNumero());
            stmt.setString(9, obj.getComplemento());
            stmt.setString(10, obj.getBairro());
            stmt.setString(11, obj.getCidade());
            stmt.setString(12, obj.getEstado());

            // 3 passo, executar o comando sql
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }

    }

    //Metodo alterarCliente
    public void alterarFornecedores(Fornecedores obj) {
        
               try {
            // 1 passo, criar o comando sql
            String sql = "update tb_fornecedores set nome=?,cnpj=?,email=?,telefone=?,celular=?,cep=?,endereco=?,"
                    + "numero=?,complemento=?,bairro=?,cidade=?,estado=? where id=?";

            // 2 passo,  conectar o banco de dados e organizar o comando sql
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getCnpj());
            stmt.setString(3, obj.getEmail());
            stmt.setString(4, obj.getTelefone());
            stmt.setString(5, obj.getCelular());
            stmt.setString(6, obj.getCep());
            stmt.setString(7, obj.getEndereco());
            stmt.setInt(8, obj.getNumero());
            stmt.setString(9, obj.getComplemento());
            stmt.setString(10, obj.getBairro());
            stmt.setString(11, obj.getCidade());
            stmt.setString(12, obj.getEstado());
            stmt.setInt(13, obj.getId());
            // 3 passo, executar o comando sql
            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Alterado com Sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
        
        
    }

     //Metedo excluirFornecedores
    public void excluirFornecedores(Fornecedores obj) {

        try {
            // 1 passo, criar o comando sql
            String sql = "delete from tb_fornecedores where id=?";

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
    
    public List<Fornecedores> buscaFornecedorPorNome(String nome) {

        try {
            //1 passo criar a lista
            List<Fornecedores> lista = new ArrayList<>();

            //2 passo criar o sql, organizar e executar
            String sql = "select * from tb_fornecedores where nome like ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery(); // Quando for usar um select, criar um ResultSet

            while (rs.next()) {
                Fornecedores obj = new Fornecedores();

                obj.setId(rs.getInt("id")); //Lembrando que é ID, não codigo;
                obj.setNome(rs.getString("nome"));
                obj.setCnpj(rs.getString("cnpj"));
                obj.setEmail(rs.getString("email"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setEstado(rs.getString("estado"));

                lista.add(obj);

            }

            return lista;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro: " + e);

        }
        return null;

    }

    
    
    
 
    
    
    
     public List<Fornecedores> listarFornecedores() {

        try {
            //1 passo criar a lista
            List<Fornecedores> lista = new ArrayList<>();

            //2 passo criar o sql, organizar e executar
            String sql = "select * from tb_fornecedores";

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(); // Quando for usar um select, criar um ResultSet

            while (rs.next()) {
                Fornecedores obj = new Fornecedores();

                obj.setId(rs.getInt("id")); //Lembrando que é ID, não codigo;
                obj.setNome(rs.getString("nome"));
                obj.setCnpj(rs.getString("cnpj"));
                obj.setEmail(rs.getString("email"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setEstado(rs.getString("estado"));

                lista.add(obj);

            }

            return lista;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro: " + e);

        }
        return null;

    }
    
     
     
     //método consultaCliente por Nome
    public Fornecedores consultaPorNome(String nome){
        
        try {
            
            String sql = "select * from tb_fornecedores where nome = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            
            ResultSet rs = stmt.executeQuery();
            
            Fornecedores obj = new Fornecedores();
            if (rs.next()) {
                

                obj.setId(rs.getInt("id")); //Lembrando que é ID, não codigo;
                obj.setNome(rs.getString("nome"));
                obj.setCnpj(rs.getString("cnpj"));
                obj.setEmail(rs.getString("email"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setEstado(rs.getString("estado"));

                

            }
            
            return obj;
        } catch (Exception e) {
            
           
            JOptionPane.showMessageDialog(null,"Fornecedor não encontrado." );
            return null;
        }
        
        
        
    }
     
     
     //Busca Cep
    
    public Fornecedores buscaCep(String cep) {
       
        WebServiceCep webServiceCep = WebServiceCep.searchCep(cep);
       

        Fornecedores obj = new Fornecedores();

        if (webServiceCep.wasSuccessful()) {
            obj.setEndereco(webServiceCep.getLogradouroFull());
            obj.setCidade(webServiceCep.getCidade());
            obj.setBairro(webServiceCep.getBairro());
            obj.setEstado(webServiceCep.getUf());
            return obj;
        } else {
            JOptionPane.showMessageDialog(null, "Erro numero: " + webServiceCep.getResulCode());
            JOptionPane.showMessageDialog(null, "Descrição do erro: " + webServiceCep.getResultText());
            return null;
        }

    }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
    
}
