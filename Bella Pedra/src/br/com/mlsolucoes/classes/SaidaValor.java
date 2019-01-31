package br.com.mlsolucoes.classes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;

public class SaidaValor {
	
	private double valorSaida;
	private String motivo;
	private String descricaoMotivo;
	private Date dataSaida;
	private String observacaoSaida;
	private String status;
		
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getValorSaida() {
		return valorSaida;
	}
	public void setValorSaida(double valorSaida) {
		this.valorSaida = valorSaida;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getDescricaoMotivo() {
		return descricaoMotivo;
	}
	public void setDescricaoMotivo(String descricaoMotivo) {
		this.descricaoMotivo = descricaoMotivo;
	}
	public Date getDataSaida() {
		return dataSaida;
	}
	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}
	public String getObservacaoSaida() {
		return observacaoSaida;
	}
	public void setObservacaoSaida(String observacaoSaida) {
		this.observacaoSaida = observacaoSaida;
	}
	
	public boolean insereSaida(){
		
		boolean resultado = true;
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			String insereValor = "insert into valorSaida(valorSaida,motivoSaida,descricaoMotivo,dataSaida,observacao,status) values(?,?,?,?,?,?)";
			PreparedStatement stm = conecta.con.prepareStatement(insereValor);
			
			stm.setDouble(1, this.valorSaida);
			stm.setString(2, this.motivo);
			stm.setString(3, this.descricaoMotivo);
			stm.setDate(4, new java.sql.Date(this.dataSaida.getTime()));
			stm.setString(5, this.observacaoSaida);
			stm.setString(6, this.status);
			stm.executeUpdate();
			stm.close();
			conecta.con.close();			
			JOptionPane.showMessageDialog(null, "Saída de Valores Realizada Com Sucesso");
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
            resultado = false;
			
			return resultado;
		}
		
		return resultado;
		
	}

}
