package br.com.vinicius.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.vinicius.entities.Product;
import br.com.vinicius.model.ConnectionModule;

public class ProductDAO {
	
	private ConnectionModule connection;
	
	/* Método responsavel pelo Read, do nosso CRUD.*/
	public List<Product> findAll(){
		try {
			List<Product> listaProdutos = new ArrayList<>();
			PreparedStatement ps = ConnectionModule.conect().prepareStatement(
					"select * from product");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Product p = new Product();
				p.setDescricao(rs.getString("descrição"));
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setPreco(rs.getDouble("preço"));
				p.setQuantidade(rs.getInt("quantidade"));
			}
			return listaProdutos;
		} catch (Exception e) {
			return null;
		}
	}
	
	/*Método de busca no banco, com filtro para o id*/
	public Product find(int id){
		try {
			Product p = new Product();
			PreparedStatement ps = ConnectionModule.conect().prepareStatement(
					"select * from product where id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {				
				p.setDescricao(rs.getString("descrição"));
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setPreco(rs.getDouble("preço"));
				p.setQuantidade(rs.getInt("quantidade"));
			}
			return p;
		} catch (Exception e) {
			return null;
		}
	}
	
	/*método de criação de um novo produto no banco de dados.*/
	public boolean create(Product product) {
		try {
			PreparedStatement ps = ConnectionModule.conect().prepareStatement(
		"insert into product(nome, preco, quantidade, descricao)values(?,?,?,?,?)");
			ps.setString(1, product.getNome());
			ps.setDouble(2, product.getPreco());
			ps.setInt(3, product.getQuantidade());
			ps.setString(4, product.getDescricao());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			return false;
		}
	}
	
	/*método de edição de um produto no banco de dados.*/
	public boolean edit(Product product) {
		try {
			PreparedStatement ps = ConnectionModule.conect().prepareStatement(
					"update product set nome=?, preco=?, quantidade=?, descricao=? where id =?");
			ps.setString(1, product.getNome());
			ps.setDouble(2, product.getPreco());
			ps.setInt(3, product.getQuantidade());
			ps.setString(4, product.getDescricao());
			ps.setInt(5, product.getId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			return false;
		}
	}
	
	/*método de exclusão de um produto no banco de dados.*/
	public boolean delete(Product product) {
		try {
			PreparedStatement ps = ConnectionModule.conect().prepareStatement(
					"delete from product where id =?");
			ps.setInt(1, product.getId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			return false;
		}
	}
}
