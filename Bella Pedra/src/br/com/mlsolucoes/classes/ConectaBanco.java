package br.com.mlsolucoes.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ConectaBanco {
	
	public Connection con;
	public static Statement stm;		
	
	public void conectaBanco() throws SQLException {
		
		try{
		con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/baseconhecimento","root","root");
		stm = con.createStatement();
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "N�o Foi Poss�vel Conectar ao Banco");
		}
	}	

}
