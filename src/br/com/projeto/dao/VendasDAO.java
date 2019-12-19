/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.projeto.dao;

import br.com.projeto.jdbc.ConnectionFactory;
import br.com.projeto.model.Clientes;
import br.com.projeto.model.Fornecedores;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Arthur
 */
public class VendasDAO {

    private Connection con;

    // construtor da classe 
    public VendasDAO() {
        this.con = new ConnectionFactory().getConnection();

    }

    //Cadastrar venda
    public void cadastrarVenda(Vendas obj) {

        try {
            String sql = "insert into tb_vendas (cliente_id,data_venda,total_venda,observacoes) values (?,?,?,?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, obj.getCliente().getId());
            stmt.setString(2, obj.getData_venda());
            stmt.setDouble(3, obj.getTotalVenda());
            stmt.setString(4, obj.getObs());

            stmt.execute();

            stmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);

        }

    }

    //Retorn a ultima venda
    public int retornaUltimaVenda() {

        try {
            int idvenda = 0;

            String sql = "select max(id) id from tb_vendas";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Vendas p = new Vendas();

                p.setId(rs.getInt("id"));

                idvenda = p.getId();
            }

            return idvenda;
        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

    //metodo que filtra venda por datas
    public List<Vendas> listarVendasPorPeriodo(LocalDate data_inicio, LocalDate data_fim) {

        try {
            //1 passo criar a lista
            List<Vendas> lista = new ArrayList<>();

            //2 passo criar o sql, organizar e executar
            //foi usado o "INNER JOIN", pois estou usando dados de duas tabelas diferentes
            //Então sera que criar dois objetos das classes específicas
            String sql = "select v.id , date_format(v.data_venda, '%d/%m/%Y') as data_formatada, c.nome, v.total_venda, v.observacoes from tb_vendas as v "
                    + "inner join tb_clientes as c on(v.cliente_id = c.id) where v.data_venda BETWEEN ? AND ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, data_inicio.toString());
            stmt.setString(2, data_fim.toString());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vendas obj = new Vendas();
                Clientes c = new Clientes();

                obj.setId(rs.getInt("v.id"));
                obj.setData_venda(rs.getString("data_formatada"));
                c.setNome(rs.getString("c.nome"));
                obj.setTotalVenda(rs.getDouble("v.total_venda"));
                obj.setObs(rs.getString("v.observacoes"));
                
                obj.setCliente(c);
                lista.add(obj);

            }

            return lista;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro: " + e);

        }
        return null;

    }

    
    public double retornaTotalVendaPorData(LocalDate data_venda){
        
        try {
            
            double totalvenda = 0;
            
            String sql = "select sum(total_venda) as total from tb_vendas where data_venda = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, data_venda.toString());
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                
               totalvenda = rs.getDouble("total");
                
            }
            
            return totalvenda;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       
        
        
    }
    
    
    
    
    
    
    
}
