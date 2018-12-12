package br.com.mlsolucoes.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;

public class Funcionario {

	private String funcionarioNome;
	private Date dataAdmissao;
	private String funcionarioStatus;	
	
	public String getFuncionarioNome() {
		return funcionarioNome;
	}
	public void setFuncionarioNome(String funcionarioNome) {
		this.funcionarioNome = funcionarioNome;
	}
	public Date getDataAdmissao() {
		return dataAdmissao;
	}
	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	public String getFuncionarioStatus() {
		return funcionarioStatus;
	}
	public void setFuncionarioStatus(String funcionarioStatus) {
		this.funcionarioStatus = funcionarioStatus;
	}
	
	public void cadastraFuncionario(){
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			String insereFuncionario = "insert into funcionarios(funcionarioNome, dataAdmissao, funcionarioStatus) values(?,?,?)";
			PreparedStatement stm = conecta.con.prepareStatement(insereFuncionario);
			
			stm.setString(1, this.funcionarioNome);
			stm.setDate(2, new java.sql.Date(this.dataAdmissao.getTime()));
			stm.setString(3, this.funcionarioStatus);
			stm.executeUpdate();
			stm.close();
			conecta.con.close();			
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null,e);
		}
		
	}//Fim do método de cadastrar Funcionário
	
	
public boolean verificaFuncionario(String funcionario){
		
		ResultSet rs = null;
		boolean retorno = false;
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			String verificaFuncionario = "select * from funcionarios";
			
			rs = conecta.stm.executeQuery(verificaFuncionario);
			
			while(rs.next()){
				if(funcionario.equals(rs.getString("funcionarioNome"))){
					retorno = true;
					break;
				}
			}		
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}
		
		return retorno;
		
	}//Fim do método verifica Funcionário
	
	
	
}
