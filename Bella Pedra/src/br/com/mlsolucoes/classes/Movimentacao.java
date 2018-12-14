package br.com.mlsolucoes.classes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Movimentacao {
	
	private double valorTotal;
	private double valorMensal;
	private int mes;
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
			
			String insereMovimentacao = "insert into movimentacao(?,?,?) values(?,?,?)";
			PreparedStatement stm = conecta.con.prepareStatement(insereMovimentacao);
			
			stm.setDouble(1, this.valorTotal);
			stm.setDouble(2, this.valorMensal);
			stm.setInt(3, this.mes);
			stm.executeUpdate();
			stm.close();
			conecta.con.close();
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}
		
	}//Fim do método que insere nova Movimentação. Isso é chamado sempre na primeira movimentação do mês.
	
	public void atualizaMovimentacao(){
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			String atualizaMovimentacao = "update movimentacao set movimentacaoTotal=?, movimentacaoMensal=?, mesAtual=?";
			PreparedStatement stm = conecta.con.prepareStatement(atualizaMovimentacao);
			
			stm.setDouble(1, this.valorTotal);
			stm.setDouble(2, this.valorMensal);
			stm.setInt(3, this.mes);
			stm.executeUpdate();
			stm.close();
			conecta.con.close();
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}
		
	}//Fim do método que atualiza movimentação. Isso é chamado em cada movimentação mensal.
	

}
