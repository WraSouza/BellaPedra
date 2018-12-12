package br.com.mlsolucoes.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Produto {
	
	private String nomeProduto;

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	
	public boolean verificaProduto(String produto){
		
		ResultSet rs = null;
		boolean verifica = false;
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			String buscaProduto = "select * from produto";
			rs = conecta.stm.executeQuery(buscaProduto);
			
			while(rs.next()){
				if(produto.equals(rs.getString("produtoNome"))){
					verifica=true;
					break;
				}
			}			
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}
		
		return verifica;		
		
	}//Fim do método de verificar placa.
	
	public void insereProduto(){
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			String insereProduto = "insert into produto(produtoNome) values(?)";
			PreparedStatement stm = conecta.con.prepareStatement(insereProduto);
			stm.setString(1, this.nomeProduto);
			stm.executeUpdate();
			stm.close();
			conecta.con.close();
			
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}
		
	}//Fim do Método de inserir produto

}
