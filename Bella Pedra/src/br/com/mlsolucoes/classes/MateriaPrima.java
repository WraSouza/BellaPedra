package br.com.mlsolucoes.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class MateriaPrima {
	
	private String nomeMateriaPrima;

	public String getNomeMateriaPrima() {
		return nomeMateriaPrima;
	}

	public void setNomeMateriaPrima(String nomeMateriaPrima) {
		this.nomeMateriaPrima = nomeMateriaPrima;
	}
	
public boolean verificaMateriaPrima(String produto){
		
		ResultSet rs = null;
		boolean verifica = false;
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			String buscaMateriaPrima = "select * from materiaprima";
			rs = conecta.stm.executeQuery(buscaMateriaPrima);
			
			while(rs.next()){
				if(produto.equals(rs.getString("nomeMateriaPrima"))){
					verifica=true;
					break;
				}
			}			
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}
		
		return verifica;		
		
	}//Fim do método de verificar matéria prima

public void insereMateriaPrima(){
	
	try{
		ConectaBanco conecta = new ConectaBanco();
		conecta.conectaBanco();
		
		String insereMateriaPrima = "insert into materiaprima(nomeMateriaPrima) values(?)";
		PreparedStatement stm = conecta.con.prepareStatement(insereMateriaPrima);
		stm.setString(1, this.nomeMateriaPrima);
		stm.executeUpdate();
		stm.close();
		conecta.con.close();		
		
	}catch(SQLException e){
		JOptionPane.showMessageDialog(null, e);
	}
	
}//Fim do Método de inserir produto


}
