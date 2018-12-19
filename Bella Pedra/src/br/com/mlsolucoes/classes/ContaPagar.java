package br.com.mlsolucoes.classes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;

public class ContaPagar {
	
	private double valorConta;
	private String motivo;
	private String descricaoMotivo;
	private Date dataPagamento;
	private String observacao;
	private Date dataLembrar;
	private String status;
	public double getValorConta() {
		return valorConta;
	}
	public void setValorConta(double valorConta) {
		this.valorConta = valorConta;
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
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Date getDataLembrar() {
		return dataLembrar;
	}
	public void setDataLembrar(Date dataLembrar) {
		this.dataLembrar = dataLembrar;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void insereNovoPagamento(){
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			String insereNovoPagamento = "insert into contaPagar(valorConta, motivoConta, descricaoConta, dataPagarConta, dataLembrarPagamento, observacao, statusConta) values(?,?,?,?,?,?,?)";
			PreparedStatement stm = conecta.con.prepareStatement(insereNovoPagamento);
			
			stm.setDouble(1,this.valorConta);
			stm.setString(2, this.motivo);
			stm.setString(3, this.descricaoMotivo);
			stm.setDate(4, new java.sql.Date(this.dataPagamento.getTime()));
			stm.setDate(5, new java.sql.Date(this.dataLembrar.getTime()));
			stm.setString(6, this.observacao);
			stm.setString(7, this.status);
			stm.executeUpdate();
			stm.close();
			conecta.con.close();
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}
		
		
	}//fim do método de inserir novo pagamento

}
