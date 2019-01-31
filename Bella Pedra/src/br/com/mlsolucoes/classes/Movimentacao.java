package br.com.mlsolucoes.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Movimentacao {
	
	private double valorTotal;
	private double valorMensal;
	private int mes;
	private int ano;
	
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public double getValorMensal() {
		return valorMensal;
	}
	public void setValorMensal(double valorMensal) {
		this.valorMensal = valorMensal;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	
	public void insereNovaMovimentacao(){
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			String insereMovimentacao = "insert into movimentacao(movimentacaoTotal,movimentacaoMensal,mesAtual,ano) values(?,?,?,?)";
			PreparedStatement stm = conecta.con.prepareStatement(insereMovimentacao);
			
			stm.setDouble(1, this.valorTotal);
			stm.setDouble(2, this.valorMensal);
			stm.setInt(3, this.mes);
			stm.setInt(4, this.ano);
			stm.executeUpdate();
			stm.close();
			conecta.con.close();
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}
		
	}//Fim do m�todo que insere nova Movimenta��o. Isso � chamado sempre na primeira movimenta��o do m�s.
	
	public void atualizaMovimentacao(){
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			String atualizaMovimentacao = "update movimentacao set movimentacaoTotal=?, movimentacaoMensal=?, mesAtual=?,ano=? where mesAtual = '"+mes+"'";
			PreparedStatement stm = conecta.con.prepareStatement(atualizaMovimentacao);
			
			stm.setDouble(1, this.valorTotal);
			stm.setDouble(2, this.valorMensal);
			stm.setInt(3, this.mes);
			stm.setInt(4, this.ano);
			stm.executeUpdate();
			stm.close();
			conecta.con.close();
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}
		
	}//Fim do m�todo que atualiza movimenta��o. Isso � chamado em cada movimenta��o mensal.
	

}
