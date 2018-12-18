package br.com.mlsolucoes.classes;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Mes {
	
	private int mesBanco;

	public int getMesBanco() {
		return mesBanco;
	}

	public void setMesBanco(int mesBanco) {
		this.mesBanco = mesBanco;
	}
	
	//Retorna o m�s do banco a ser comparado com o m�s do valor a ser inserido no banco
	public int retornaMes(){
		
		ResultSet rs = null;
		int mes = 0;
		
		try{
			ConectaBanco conecta = new  ConectaBanco();
			conecta.conectaBanco();
			
			String retornaMes = "select * from movimentacao";
			rs = conecta.stm.executeQuery(retornaMes);
			
			while(rs.next()){
				mes = rs.getInt("mesAtual");
			}
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}
		
		return mes;
		
	}//Fim do m�todo de retornar o m�s do banco.

}
