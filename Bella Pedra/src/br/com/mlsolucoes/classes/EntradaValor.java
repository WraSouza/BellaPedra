package br.com.mlsolucoes.classes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;

public class EntradaValor {
	
	private String documentoCliente;
	private String nomeCliente;
	private String nomeProduto;
	private String materiaPrima;
	private double valorServico;
	private Date dataServico;
	private String status;
	public String getDocumentoCliente() {
		return documentoCliente;
	}
	public void setDocumentoCliente(String documentoCliente) {
		this.documentoCliente = documentoCliente;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public String getMateriaPrima() {
		return materiaPrima;
	}
	public void setMateriaPrima(String materiaPrima) {
		this.materiaPrima = materiaPrima;
	}
	public double getValorServico() {
		return valorServico;
	}
	public void setValorServico(double valorServico) {
		this.valorServico = valorServico;
	}
	public Date getDataServico() {
		return dataServico;
	}
	public void setDataServico(Date dataServico) {
		this.dataServico = dataServico;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void insereEntrada(){
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			String insereDados = "insert into servicoEntrada(?,?,?,?,?,?,?) values(?,?,?,?,?,?,?)";
			PreparedStatement stm = conecta.con.prepareStatement(insereDados);
			
			stm.setString(1, this.documentoCliente);
			stm.setString(2, this.nomeCliente);
			stm.setString(3, this.nomeProduto);
			stm.setString(4, this.materiaPrima);
			stm.setDouble(5, this.valorServico);
			stm.setDate(6, new java.sql.Date(this.dataServico.getTime()));
			stm.setString(7, this.status);
			stm.executeUpdate();
			stm.close();
			conecta.con.close();
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}
		
		
	}//Fim do método de inserir o valor de um serviço realizado

}
