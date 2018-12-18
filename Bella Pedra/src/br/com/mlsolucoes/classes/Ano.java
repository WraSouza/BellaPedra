package br.com.mlsolucoes.classes;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Ano {
	
	private int anoAtual;

	public int getAnoAtual() {
		return anoAtual;
	}

	public void setAnoAtual(int anoAtual) {
		this.anoAtual = anoAtual;
	}
	
	
	public int retornaAno(){
		
		ResultSet rs = null;
		int ano = 0;
		
		try{
			ConectaBanco conecta = new  ConectaBanco();
			conecta.conectaBanco();
			
			String retornaAno = "select * from movimentacao";
			rs = conecta.stm.executeQuery(retornaAno);
			
			while(rs.next()){
				ano = rs.getInt("ano");
			}
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}
		
		return ano;
		
	}

}
