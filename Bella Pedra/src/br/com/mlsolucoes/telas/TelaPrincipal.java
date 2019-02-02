package br.com.mlsolucoes.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import br.com.mlsolucoes.classes.Ano;
import br.com.mlsolucoes.classes.ConectaBanco;
import br.com.mlsolucoes.classes.ContaPagar;
import br.com.mlsolucoes.classes.EntradaValor;
import br.com.mlsolucoes.classes.MateriaPrima;
import br.com.mlsolucoes.classes.Mes;
import br.com.mlsolucoes.classes.ModeloTabela;
import br.com.mlsolucoes.classes.Movimentacao;
import br.com.mlsolucoes.classes.Produto;
import br.com.mlsolucoes.classes.SaidaValor;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.awt.event.ItemEvent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JCalendar;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	private JFormattedTextField formattedTextFieldCPF;
	private JFormattedTextField formattedTextFieldValorSaida;
	private JTextField textFieldNomeCliente;
	private JPanel panelEntrada;
	private JPanel panelSaida;
	private JComboBox comboBoxDescricaoMotivo;
	private JComboBox comboBoxMotivo;
	private JComboBox comboBoxMateriaPrima;
	private JComboBox comboBoxOpcaoDesejada;
	private JComboBox comboBoxProduto;
	private JFormattedTextField formattedTextFieldValorEntrada;
	private JDateChooser dateChooserEntrada;
	private JDateChooser dateChooserAvisoPagar;
	private JDateChooser dateChooserSaida;
	private String user;
	private JPanel panel_1;
	private JButton btnSalvar;
	private JButton buttonPesquisar;
	private JTextField textFieldObsSaida;
	private JMenu mnAdicionar;
	private JLabel labelValorMensal;
	private JLabel labelValorTotal;
	private JLabel labelTotal;
	private JTable table;
	private JMonthChooser monthChooser;
	private JYearChooser yearChooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {					
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);		
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
public void preencherTabela(String sql){
		
		ResultSet rs = null;
		ArrayList dados = new ArrayList();
		String[] colunas = new String[]{"Motivo","Descricao","Valor","Data do Pagamento"};
		String dataFormatada = null;	
		int mesSelecionado = monthChooser.getMonth();
		int anoSelecionado = yearChooser.getYear();
		Date dataBanco = null;
		int mesBanco;
		int anoBanco;
		double valorTotal = 0;
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			rs = conecta.stm.executeQuery(sql);
				
			while(rs.next()){
				dataBanco = rs.getDate("dataSaida");
				mesBanco = dataBanco.getMonth();
				anoBanco = dataBanco.getYear()+1900;
				
				if((mesSelecionado==mesBanco)&&(anoSelecionado==anoBanco)){				
				valorTotal = valorTotal + rs.getDouble("valorSaida");
			dataBanco = rs.getDate("dataSaida");
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			dataFormatada = formato.format(dataBanco);
			dados.add(new Object[]{rs.getString("descricaoMotivo"),rs.getString("observacao"),rs.getDouble("valorSaida"),dataFormatada});
				}
			
			}//fim do while
			labelTotal.setText(String.valueOf(valorTotal));
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}
		
		ModeloTabela modelo = new ModeloTabela(dados, colunas);
		table.setModel(modelo);
		table.getColumnModel().getColumn(0).setPreferredWidth(210);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(210);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(210);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(210);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}//Fim do método de preencher a tabela com os valores pesquisados
	
	
	private void buscaMateriaPrima(){
		
		ResultSet rs = null;
		formattedTextFieldCPF.removeAll();
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			String buscaMateraPrima = "select * from materiaprima";
			rs = conecta.stm.executeQuery(buscaMateraPrima);
			
			while(rs.next()){
			comboBoxMateriaPrima.addItem(rs.getString("nomeMateriaPrima"));
			}
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}		
		
	}//Fim do método de buscar matéria-prima
	
	public void buscaProduto(){
		
		ResultSet rs = null;
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			String buscaProduto = "select * from produto";
			rs = conecta.stm.executeQuery(buscaProduto);
			
			while(rs.next()){
			comboBoxProduto.addItem(rs.getString("produtoNome"));
			}
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}	
		
	}//fim do método de buscar produto
	
	public void verificaValorNoBanco(){
		
		ResultSet rs = null;	
		ResultSet RS = null;
		String valorMensal = null;
		String valorTotal = null;
		String valorTotalAnterior;
		double valorMesAnterior = 0;
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			LocalDate date = LocalDate.now();
			Date dataSistema = java.sql.Date.valueOf(date);
			int mesAtual = dataSistema.getMonth();
			int anoAtual = dataSistema.getYear()+1900;
			double valorAnterior = 0;
						
			String buscaAno = "select * from movimentacao where mesAtual = '"+mesAtual+"' and ano = '"+anoAtual+"'";
			rs = conecta.stm.executeQuery(buscaAno);			
			
			if(!rs.isBeforeFirst()){				
				
				if(mesAtual==0){				
				labelValorMensal.setText("0.00");
				labelValorTotal.setText("0.00");
				}else{
					String buscaValorBanco = "select * from movimentacao where mesAtual = '"+(mesAtual-1)+"' and ano = '"+anoAtual+"'";
					rs = conecta.stm.executeQuery(buscaValorBanco);
					labelValorMensal.setText("0.00");
					while(rs.next()){
						valorAnterior = rs.getDouble("movimentacaoTotal");						
					}
					if(valorAnterior==0){
						labelValorTotal.setText("0.00");
					}else{
						if(valorAnterior<0){
							labelValorTotal.setForeground(Color.red);
					labelValorTotal.setText(String.valueOf(valorAnterior));
						}else{
							labelValorTotal.setForeground(Color.black);
							labelValorTotal.setText(String.valueOf(valorAnterior));
						}
					}
				}
				
			}else{
				
				while(rs.next()){
					
					if(mesAtual==0){					
					double valorDoMes = rs.getDouble("movimentacaoMensal");
					double totalValor = rs.getDouble("movimentacaoTotal");
					
					if(valorDoMes<0){
						labelValorMensal.setForeground(Color.red);
						labelValorMensal.setText(String.valueOf(valorDoMes));
					}else{
						labelValorMensal.setForeground(Color.black);
						labelValorMensal.setText(String.valueOf(valorDoMes));
					}
					if(totalValor<0){
						labelValorTotal.setForeground(Color.red);
						labelValorTotal.setText(String.valueOf(totalValor));
					}else{
						labelValorTotal.setForeground(Color.black);
						labelValorTotal.setText(String.valueOf(totalValor));
					}					
					}else{
						
						String buscaValorAnterior = "select * from movimentacao where mesAtual = '"+mesAtual+"' and ano = '"+anoAtual+"'";
						rs = conecta.stm.executeQuery(buscaValorAnterior);
						
						while(rs.next()){
							valorMensal = String.valueOf(rs.getDouble("movimentacaoMensal"));
							valorTotal = String.valueOf(rs.getDouble("movimentacaoTotal"));
							labelValorMensal.setText(valorMensal);
							labelValorTotal.setText(String.valueOf(rs.getDouble("movimentacaoTotal")));
							if(Double.parseDouble(valorMensal)<0){
							labelValorMensal.setText(String.valueOf(valorMensal));
							labelValorMensal.setForeground(Color.red);
							}else{
								labelValorMensal.setForeground(Color.black);
								labelValorMensal.setText(String.valueOf(valorMensal));
							}
							if(Double.parseDouble(valorTotal)<0){
								labelValorTotal.setText(String.valueOf(valorTotal));
								labelValorTotal.setForeground(Color.red);
								}else{
									labelValorTotal.setForeground(Color.black);
									labelValorTotal.setText(String.valueOf(valorTotal));
								}
							
							
						}
						
					}//fim do if_else caso o mês seja diferente de janeiro
				}//Fim do while
			}
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}		
		
	}//fim do método de verificar se existe valores no banco
	
	
	public void recebeLogin(String login){
		
		user = login;
		
		if(user.equals("lielson")){			
			btnSalvar.setEnabled(true);
			buttonPesquisar.setEnabled(true);
		}else {
			buttonPesquisar.setEnabled(false);
			labelValorTotal.setVisible(false);
		}
		
	}//método que verifica qual usuário se logou no sistema.

	/**
	 * Create the frame.
	 */
	public TelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 925, 717);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnSalvar = new JButton("");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String opcaoDesejada = (String)comboBoxOpcaoDesejada.getSelectedItem();
				int dataCalendario = 0;
				int mesCalendario = 0;
				int anoAtual = 0;
				
				if(opcaoDesejada=="Selecione Uma Opção"){
					JOptionPane.showMessageDialog(null, "Selecione Uma Opção");
				}else if(opcaoDesejada=="Entrada"){
					
					EntradaValor novaEntrada = new EntradaValor();
					Mes mes = new Mes();
					Ano ano = new Ano();
					ResultSet rs = null;
					LocalDate date = LocalDate.now();
					String cpf_cnpj = formattedTextFieldCPF.getText();
					String nomeCliente = textFieldNomeCliente.getText();
					String nomeProduto = (String)comboBoxProduto.getSelectedItem();
					String materiaPrima = (String)comboBoxMateriaPrima.getSelectedItem();
					String valor = formattedTextFieldValorEntrada.getText();
					Date data = (Date)dateChooserEntrada.getDate();
					Date dataSistema = java.sql.Date.valueOf(date);		
					
					//Pega o Dia do Sistema
					if(data==null){
						
					}else{
					 dataCalendario = data.getDate();
					 mesCalendario = data.getMonth();
					 anoAtual = data.getYear()+1900;
					}
					int dataDoSistema = dataSistema.getDate();
					
					//Pega o mês do banco de dados					
					int mesAtual = mes.retornaMes();
					
					//Pega o ano do banco
					int anoDoBanco = ano.retornaAno();

					//Verifica se todos os campos de "ENTRADA" estão preenchidos antes de salvar.Caso não estejam, mostra mensagem informando o usuário
					if((cpf_cnpj=="")||(nomeCliente=="")||(nomeProduto=="Selecione Produto")||(materiaPrima=="Selecione Um Item")||(valor=="")||(data==null)){
						JOptionPane.showMessageDialog(null, "Favor Preencher Todos os Campos");
						
					}else{							
							//Envia os dados a serem inseridos no banco
							novaEntrada.setDocumentoCliente(cpf_cnpj);
							novaEntrada.setNomeCliente(nomeCliente);
							novaEntrada.setNomeProduto(nomeProduto);
							novaEntrada.setMateriaPrima(materiaPrima);
							novaEntrada.setDataServico(data);
							novaEntrada.setValorServico(Double.parseDouble(valor));
							novaEntrada.setStatus("RECEBIDA");	
							novaEntrada.insereEntrada();
							JOptionPane.showMessageDialog(null, "Entrada de Valores Inserida com Sucesso");							
							formattedTextFieldCPF.setText("");
							textFieldNomeCliente.setText("");
							
							//Insere dados no banco da movimentação mensal
								
								Movimentacao movimentacao = new Movimentacao();	
								String valorNoBancoMensal = labelValorMensal.getText();
								String valorNoBancoTotal = labelValorTotal.getText();
											
								if(valorNoBancoMensal.equals("0.00")){
									movimentacao.setValorMensal(Double.parseDouble(valor));
									double valorTotal = Double.parseDouble(valorNoBancoTotal) + Double.parseDouble(valor);
									movimentacao.setValorTotal(valorTotal);
									movimentacao.setMes(mesCalendario);
									movimentacao.setAno(anoAtual);
									movimentacao.insereNovaMovimentacao();
									labelValorMensal.setText(valor);
									labelValorTotal.setText(String.valueOf(valorTotal));										
									
								}else{
									Double valorTotal = Double.parseDouble(valor) + Double.parseDouble(valorNoBancoTotal);
									Double valorMensal = Double.parseDouble(valor) + Double.parseDouble(valorNoBancoMensal);
									movimentacao.setValorMensal(valorMensal);
									movimentacao.setValorTotal(valorTotal);
									movimentacao.setMes(mesCalendario);
									movimentacao.setAno(anoAtual);
									movimentacao.atualizaMovimentacao();
									if(valorTotal<0){
										labelValorTotal.setText(String.valueOf(valorTotal));
										labelValorTotal.setForeground(Color.red);
									}else{
										
										labelValorTotal.setForeground(Color.black);										
										labelValorTotal.setText(String.valueOf(valorTotal));
									}	
									if(valorMensal<0){
										labelValorMensal.setText(String.valueOf(valorMensal));
										labelValorMensal.setForeground(Color.red);
										
									}else{
										labelValorMensal.setForeground(Color.black);										
										labelValorMensal.setText(String.valueOf(valorMensal));	
									}																
								}							
													
							}//Fim do else													
					
				}//Fim do if para opção "Entrada"
				else if(opcaoDesejada=="Saída"){
					
					double valorTotal;
					Mes mes = new Mes();
					Ano ano = new Ano();
					String valorSaida = formattedTextFieldValorSaida.getText();
					String motivo = (String)comboBoxMotivo.getSelectedItem();
					Date data = (Date)dateChooserSaida.getDate();
					String obs = textFieldObsSaida.getText();
					String descricaoMotivo = (String)comboBoxDescricaoMotivo.getSelectedItem();
					
					//Pega data do Calendário Selecionado
					Date dataDoCalendario = dateChooserSaida.getDate();
					
					if(dataDoCalendario==null){
						
					}else{
					 dataCalendario = dataDoCalendario.getDate();
					 mesCalendario = dataDoCalendario.getMonth();
					 anoAtual = dataDoCalendario.getYear()+1900;
					}			
					
					//Pega o mês do banco de dados					
					int mesAtual = mes.retornaMes();
					
					//Pega o ano do banco
					int anoDoBanco = ano.retornaAno();
					
					//Verifica se todos os campos estão preenchidos antes de salvar.Caso não estejam, mostra mensagem informando o usuário
					if((valorSaida=="")||(motivo=="Selecione Uma Opção")||(data==null)){
						JOptionPane.showMessageDialog(null, "Favor Preencher Todos os Campos");
					}else{
						
						SaidaValor novaSaida = new SaidaValor();						
						Movimentacao movimentacao = new Movimentacao();	
						String valorNoBancoMensal = labelValorMensal.getText();
						String valorNoBancoTotal = labelValorTotal.getText();
						
						//Envia dados a serem gravados no banco de dados
						novaSaida.setValorSaida(Double.parseDouble(valorSaida));
						novaSaida.setMotivo(motivo);
						novaSaida.setDescricaoMotivo(descricaoMotivo);
						novaSaida.setDataSaida(data);
						novaSaida.setObservacaoSaida(obs);
						novaSaida.setStatus("PAGA");
						novaSaida.insereSaida();
						JOptionPane.showMessageDialog(null, "Saída de Valores Realizada Com Sucesso");
						textFieldObsSaida.setText("");
						formattedTextFieldValorSaida.setText("");								
									
						//envia dados a serem inseridos ou atualizados em movimentação
						if(valorNoBancoMensal.equals("0.00")){
							
							double valorMensal = 0 - Double.parseDouble(valorSaida);
							movimentacao.setValorMensal(valorMensal);
							if(valorNoBancoTotal.equals("0.00")){
								valorTotal = 0 - Double.parseDouble(valorSaida);
								movimentacao.setValorTotal(valorTotal);
								labelValorTotal.setForeground(Color.red);
								labelValorTotal.setText(String.valueOf(valorTotal));
							}else{
							valorTotal = Double.parseDouble(valorNoBancoTotal) - Double.parseDouble(valorSaida);
							movimentacao.setValorTotal(valorTotal);
							if(valorTotal>=0){
							labelValorTotal.setForeground(Color.black);
							labelValorTotal.setText(String.valueOf(valorTotal));
							}else{
								labelValorTotal.setForeground(Color.red);
								labelValorTotal.setText(String.valueOf(valorTotal));
							}
							}
							movimentacao.setMes(mesCalendario);
							movimentacao.setAno(anoAtual);
							movimentacao.insereNovaMovimentacao();
							if(valorMensal<0){
								labelValorMensal.setText(String.valueOf(valorMensal));
								labelValorMensal.setForeground(Color.red);
								//labelValorTotal.setText(valorSaida);
								//labelValorTotal.setForeground(Color.red);
							}else{
								labelValorMensal.setForeground(Color.black);								
								labelValorMensal.setText(valorSaida);	
								//labelValorTotal.setText(String.valueOf(valorTotal));
								//labelValorTotal.setForeground(Color.red);
							}													
							
						}else{
							Double valorMensal = Double.parseDouble(valorNoBancoMensal) - Double.parseDouble(valorSaida);
							Double valorInteiro = Double.parseDouble(valorNoBancoTotal) - Double.parseDouble(valorSaida);
							movimentacao.setValorMensal(valorMensal);
							movimentacao.setValorTotal(valorInteiro);
							movimentacao.setMes(mesAtual);
							movimentacao.setAno(anoAtual);
							movimentacao.atualizaMovimentacao();
							if(valorInteiro<0){								
								labelValorTotal.setText(String.valueOf(valorInteiro));								
								labelValorTotal.setForeground(Color.red);
							}else{								
								labelValorTotal.setForeground(Color.black);								
								labelValorTotal.setText(String.valueOf(valorInteiro));
							}
							if(valorMensal<0){								
								labelValorMensal.setText(String.valueOf(valorMensal));								
								labelValorMensal.setForeground(Color.red);
							}else{								
								labelValorMensal.setForeground(Color.black);								
								labelValorMensal.setText(String.valueOf(valorMensal));
							}
						}				
						}
				}//Fim do método caso a opção selecionada seja Saída	
				else if(opcaoDesejada=="Contas a Pagar"){
					
					String valor = formattedTextFieldValorSaida.getText();
					String motivo = (String)comboBoxMotivo.getSelectedItem();
					String descricaoMotivo = (String)comboBoxDescricaoMotivo.getSelectedItem();
					Date data = (Date)dateChooserSaida.getDate();
					Date dataLembrarPagamento = (Date)dateChooserAvisoPagar.getDate();
					String observacao = textFieldObsSaida.getText();
					
					if((valor.isEmpty())||(motivo=="Selecione Uma Opção")||(data==null)){
						JOptionPane.showMessageDialog(null, "Informe os Campos Necessários");
					}else{
						ContaPagar novaContaPagar = new ContaPagar();
						novaContaPagar.setValorConta(Double.parseDouble(valor));
						novaContaPagar.setMotivo(motivo);
						novaContaPagar.setDescricaoMotivo(descricaoMotivo);
						novaContaPagar.setDataPagamento(data);
						novaContaPagar.setDataLembrar(dataLembrarPagamento);
						novaContaPagar.setObservacao(observacao);
						novaContaPagar.setStatus("PENDENTE");
						novaContaPagar.insereNovoPagamento();
						JOptionPane.showMessageDialog(null, "Conta Inserida com Sucesso");
						formattedTextFieldValorSaida.setText("");
						textFieldObsSaida.setText("");
						
					}//Caso os campos estejam todos preenchidos
					
					
				}//fim do if caso a opção seja contas a pagar
				
			}
		});
		btnSalvar.setBackground(SystemColor.menu);
		btnSalvar.setToolTipText("Salvar");
		btnSalvar.setIcon(new ImageIcon("C:\\Users\\wladi\\Downloads\\save.png"));
		btnSalvar.setBounds(0, 24, 51, 41);
		contentPane.add(btnSalvar);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 907, 26);
		contentPane.add(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		mnArquivo.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		menuBar.add(mnArquivo);
		
		mnAdicionar = new JMenu("Adicionar");
		mnAdicionar.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		mnArquivo.add(mnAdicionar);
		
		JMenuItem mntmAdicionarFuncionrio = new JMenuItem("Adicionar Funcion\u00E1rio");
		mntmAdicionarFuncionrio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaFuncionario novaTelaFuncionario = new TelaFuncionario();
				novaTelaFuncionario.setVisible(true);
			}
		});
		mntmAdicionarFuncionrio.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		mnAdicionar.add(mntmAdicionarFuncionrio);
		
		JMenuItem mntmAdicionarProduto = new JMenuItem("Adicionar Produto");
		mntmAdicionarProduto.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		mntmAdicionarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Produto novoProduto = new Produto();
				boolean verifica = false;
				ResultSet rs = null;
				comboBoxProduto.removeAllItems();
				UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.PLAIN, 18));
				String nomeProduto = JOptionPane.showInputDialog("Insira Novo Produto");
				
				if(nomeProduto==null){
					
				}else{
					verifica = novoProduto.verificaProduto(nomeProduto);
					
					if(verifica){
						JOptionPane.showMessageDialog(null, "Produto Já Cadastrado");
					}else{
						novoProduto.setNomeProduto(nomeProduto);
						novoProduto.insereProduto();
						JOptionPane.showMessageDialog(null, "Produto Cadastrado Com Sucesso");
					}
				}	
				
				try{
					ConectaBanco conecta = new ConectaBanco();
					conecta.conectaBanco();
					
					String buscaProduto = "select * from produto";
					rs = conecta.stm.executeQuery(buscaProduto);
					
					while(rs.next()){
						comboBoxProduto.addItem(rs.getString("produtoNome"));
					}
					
				}catch(SQLException e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		mnAdicionar.add(mntmAdicionarProduto);
		
		JMenuItem mntmAdicionarMatriaprima = new JMenuItem("Adicionar Mat\u00E9ria-Prima");
		mntmAdicionarMatriaprima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				MateriaPrima novaMateriaPrima = new MateriaPrima();
				boolean verifica = false;
				ResultSet rs = null;
				comboBoxMateriaPrima.removeAllItems();
				UIManager.put("OptionPane.messageFont", new Font("Tahoma", Font.PLAIN, 18));
				String nomeMateriaPrima = JOptionPane.showInputDialog("Insira Nova Matéria-Prima");
				
				if(nomeMateriaPrima==null){
					
				}else{
					verifica = novaMateriaPrima.verificaMateriaPrima(nomeMateriaPrima);
					
					if(verifica){
						JOptionPane.showMessageDialog(null, "Matéria-Prima Já Cadastrada");
					}else{
						novaMateriaPrima.setNomeMateriaPrima(nomeMateriaPrima);
						novaMateriaPrima.insereMateriaPrima();
						JOptionPane.showMessageDialog(null, "Matéria-Prima Cadastrada Com Sucesso");
					}
				}	
				
				try{
					ConectaBanco conecta = new ConectaBanco();
					conecta.conectaBanco();
					
					String buscaMateriaPrima = "select * from materiaprima";
					rs = conecta.stm.executeQuery(buscaMateriaPrima);
					
					while(rs.next()){
						comboBoxMateriaPrima.addItem(rs.getString("nomeMateriaPrima"));
					}
					
				}catch(SQLException e){
					JOptionPane.showMessageDialog(null, e);
				}
				
			}
		});
		mntmAdicionarMatriaprima.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		mnAdicionar.add(mntmAdicionarMatriaprima);
		
		JMenu mnSobre = new JMenu("Sobre");
		mnSobre.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		menuBar.add(mnSobre);
		
		JMenuItem mntmSobreAAplicao = new JMenuItem("Sobre a Aplica\u00E7\u00E3o");
		mntmSobreAAplicao.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		mntmSobreAAplicao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaSobre novaTelaSobre = new TelaSobre();
				novaTelaSobre.setVisible(true);
			}
		});
		mnSobre.add(mntmSobreAAplicao);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 82, 907, 588);
		tabbedPane.setFont(new Font("Dialog",Font.BOLD,16));
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Entrada/Sa\u00EDda", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblInserirUmaNova = new JLabel("Inserir Uma Nova:");
		lblInserirUmaNova.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblInserirUmaNova.setBounds(31, 23, 186, 16);
		panel.add(lblInserirUmaNova);
		
		comboBoxOpcaoDesejada = new JComboBox();
		comboBoxOpcaoDesejada.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
				String opcaoDesejada = (String)comboBoxOpcaoDesejada.getSelectedItem();
				
				//Se a opção desejada for ENTRADA, irá habilitar as opções para a função de entada de valores da venda, como nome, valor, cliente, etc..
				if(opcaoDesejada=="Entrada"){
					//habilita componentes do painel de Entrada
					panelEntrada.setEnabled(true);
					formattedTextFieldCPF.setEditable(true);
					textFieldNomeCliente.setEditable(true);
					comboBoxProduto.setEnabled(true);
					comboBoxMateriaPrima.setEnabled(true);
					formattedTextFieldValorEntrada.setEditable(true);
					dateChooserEntrada.setEnabled(true);
					
					//desabilita componentes do painel de Saída
					panelSaida.setEnabled(false);
					formattedTextFieldValorSaida.setEditable(false);
					comboBoxMotivo.setEnabled(false);
					comboBoxDescricaoMotivo.setEnabled(false);
					dateChooserSaida.setEnabled(false);		
					textFieldObsSaida.setEditable(false);
					dateChooserAvisoPagar.setEnabled(false);
					
				}else if(opcaoDesejada=="Saída"){
					//desabilita componentes do painel de Entrada
					panelEntrada.setEnabled(false);
					formattedTextFieldCPF.setEditable(false);
					textFieldNomeCliente.setEditable(false);
					comboBoxProduto.setEnabled(false);
					comboBoxMateriaPrima.setEnabled(false);;
					formattedTextFieldValorEntrada.setEditable(false);
					dateChooserEntrada.setEnabled(false);
					dateChooserAvisoPagar.setEnabled(false);
					
					//habilita componentes do painel de Saída
					panelSaida.setEnabled(true);
					formattedTextFieldValorSaida.setEditable(true);
					comboBoxMotivo.setEnabled(true);
					dateChooserSaida.setEnabled(true);	
					textFieldObsSaida.setEditable(true);
					comboBoxDescricaoMotivo.setEnabled(true);
				}else if(opcaoDesejada=="Contas a Pagar"){
					//desabilita componentes do painel de Entrada
					panelEntrada.setEnabled(false);
					formattedTextFieldCPF.setEditable(false);
					textFieldNomeCliente.setEditable(false);
					comboBoxProduto.setEnabled(false);
					comboBoxMateriaPrima.setEnabled(false);;
					formattedTextFieldValorEntrada.setEditable(false);
					dateChooserEntrada.setEnabled(false);
					
					//habilita componentes do painel de Saída
					panelSaida.setEnabled(true);
					formattedTextFieldValorSaida.setEditable(true);
					comboBoxMotivo.setEnabled(true);
					dateChooserSaida.setEnabled(true);	
					textFieldObsSaida.setEditable(true);
					dateChooserAvisoPagar.setEnabled(true);
				}
				
			}
		});
		comboBoxOpcaoDesejada.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBoxOpcaoDesejada.setModel(new DefaultComboBoxModel(new String[] {"Selecione Uma Op\u00E7\u00E3o", "Contas a Pagar", "Entrada", "Sa\u00EDda"}));
		comboBoxOpcaoDesejada.setBounds(31, 45, 211, 28);
		panel.add(comboBoxOpcaoDesejada);
		
		panelEntrada = new JPanel();
		panelEntrada.setBorder(new TitledBorder(null, "Entrada", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panelEntrada.setBounds(12, 95, 866, 177);
		panel.add(panelEntrada);
		panelEntrada.setLayout(null);
		
		JLabel lblCpf = new JLabel("CPF/CNPJ");
		lblCpf.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCpf.setBounds(12, 26, 127, 16);
		panelEntrada.add(lblCpf);
		
		formattedTextFieldCPF = new JFormattedTextField();
		formattedTextFieldCPF.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedTextFieldCPF.setFont(new Font("Tahoma", Font.PLAIN, 18));
		formattedTextFieldCPF.setBounds(12, 46, 175, 30);
		panelEntrada.add(formattedTextFieldCPF);
		
		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCliente.setBounds(218, 26, 74, 16);
		panelEntrada.add(lblCliente);
		
		textFieldNomeCliente = new JTextField();
		textFieldNomeCliente.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldNomeCliente.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldNomeCliente.setBounds(218, 46, 223, 28);
		panelEntrada.add(textFieldNomeCliente);
		textFieldNomeCliente.setColumns(10);
		
		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblProduto.setBounds(453, 26, 96, 16);
		panelEntrada.add(lblProduto);
		
		JLabel lblMatriaprima = new JLabel("Mat\u00E9ria-Prima");
		lblMatriaprima.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMatriaprima.setBounds(649, 27, 127, 16);
		panelEntrada.add(lblMatriaprima);
		
		comboBoxMateriaPrima = new JComboBox();
		comboBoxMateriaPrima.setModel(new DefaultComboBoxModel(new String[] {"Selecione Um Item"}));
		comboBoxMateriaPrima.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBoxMateriaPrima.setBounds(649, 46, 175, 28);
		panelEntrada.add(comboBoxMateriaPrima);
		
		JLabel lblValor = new JLabel("Valor(Ex: 120.35)");
		lblValor.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblValor.setBounds(40, 102, 147, 16);
		panelEntrada.add(lblValor);
		
		JLabel lblNewLabel = new JLabel("R$");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(12, 129, 40, 16);
		panelEntrada.add(lblNewLabel);
		
		formattedTextFieldValorEntrada = new JFormattedTextField();
		formattedTextFieldValorEntrada.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedTextFieldValorEntrada.setFont(new Font("Tahoma", Font.PLAIN, 18));
		formattedTextFieldValorEntrada.setBounds(40, 122, 147, 30);
		panelEntrada.add(formattedTextFieldValorEntrada);
		
		JLabel lblDataDaEntrada = new JLabel("Data da Entrada do Valor");
		lblDataDaEntrada.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDataDaEntrada.setBounds(218, 102, 228, 16);
		panelEntrada.add(lblDataDaEntrada);
		
		dateChooserEntrada = new JDateChooser();
		dateChooserEntrada.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateChooserEntrada.setBounds(218, 122, 182, 29);
		panelEntrada.add(dateChooserEntrada);
		
		comboBoxProduto = new JComboBox();
		comboBoxProduto.setModel(new DefaultComboBoxModel(new String[] {"Selecione Produto"}));
		comboBoxProduto.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBoxProduto.setBounds(453, 46, 162, 28);
		panelEntrada.add(comboBoxProduto);
		
		panelSaida = new JPanel();
		panelSaida.setBorder(new TitledBorder(null, "Sa\u00EDda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSaida.setBounds(12, 285, 866, 225);
		panel.add(panelSaida);
		panelSaida.setLayout(null);
		
		JLabel lblValor_1 = new JLabel("Valor");
		lblValor_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblValor_1.setBounds(37, 34, 56, 16);
		panelSaida.add(lblValor_1);
		
		JLabel lblR = new JLabel("R$");
		lblR.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblR.setBounds(12, 64, 27, 16);
		panelSaida.add(lblR);
		
		formattedTextFieldValorSaida = new JFormattedTextField();
		formattedTextFieldValorSaida.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedTextFieldValorSaida.setFont(new Font("Tahoma", Font.PLAIN, 18));
		formattedTextFieldValorSaida.setBounds(37, 55, 147, 30);
		panelSaida.add(formattedTextFieldValorSaida);
		
		JLabel lblMotivo = new JLabel("Motivo");
		lblMotivo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMotivo.setBounds(196, 34, 76, 16);
		panelSaida.add(lblMotivo);
		
		dateChooserSaida = new JDateChooser();
		dateChooserSaida.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateChooserSaida.setBounds(681, 55, 173, 30);
		panelSaida.add(dateChooserSaida);
		
		JLabel lblDataDoPagamento = new JLabel("Data do Pagamento");
		lblDataDoPagamento.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDataDoPagamento.setBounds(681, 34, 173, 16);
		panelSaida.add(lblDataDoPagamento);
		
		comboBoxMotivo = new JComboBox();
		comboBoxMotivo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
				String opcaoEscolhida = (String)comboBoxMotivo.getSelectedItem();
				ResultSet rs = null;
				comboBoxDescricaoMotivo.removeAllItems();
				String opcao = null;
				
				try{
					ConectaBanco conecta = new ConectaBanco();
					conecta.conectaBanco();
					
					if(opcaoEscolhida=="Fornecedor"){
						opcao = "Fornecedor";
						String buscaValores = "select * from empresas where empresaSetor= '"+opcao+"'";
						rs = conecta.stm.executeQuery(buscaValores);
						
						while(rs.next()){
							comboBoxDescricaoMotivo.addItem(rs.getString("empresaNome"));
						}
					}else if(opcaoEscolhida=="Impostos"){
						
						String buscaValores = "select * from impostos";
						rs = conecta.stm.executeQuery(buscaValores);
						
						while(rs.next()){
							comboBoxDescricaoMotivo.addItem(rs.getString("impostoNome"));
						}
						
					}else if(opcaoEscolhida=="Diversos"){
						
						opcao = "Diversos";						
						String buscaValores = "select * from empresas where empresaSetor = '"+opcao+"'";
						rs = conecta.stm.executeQuery(buscaValores);
						
						while(rs.next()){
							comboBoxDescricaoMotivo.addItem(rs.getString("empresaNome"));
						}
						
					}else if(opcaoEscolhida=="Funcionário"){
						
						String status = "Ativo";
						
						String buscaFuncionario = "select * from funcionarios where funcionarioStatus = '"+status+"'";
						rs = conecta.stm.executeQuery(buscaFuncionario);
						
						while(rs.next()){
							comboBoxDescricaoMotivo.addItem(rs.getString("funcionarioNome"));
						}
						
					}
					
				}catch(SQLException e){
					JOptionPane.showMessageDialog(null, e);
				}
				
			}
		});
		comboBoxMotivo.setModel(new DefaultComboBoxModel(new String[] {"Selecione Uma Op\u00E7\u00E3o", "Diversos", "Fornecedor", "Funcion\u00E1rio", "Impostos"}));
		comboBoxMotivo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBoxMotivo.setBounds(196, 55, 209, 28);
		panelSaida.add(comboBoxMotivo);
		
		comboBoxDescricaoMotivo = new JComboBox();
		comboBoxDescricaoMotivo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBoxDescricaoMotivo.setBounds(417, 55, 241, 28);
		panelSaida.add(comboBoxDescricaoMotivo);
		
		JLabel lblObservaes = new JLabel("Observa\u00E7\u00F5es");
		lblObservaes.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblObservaes.setBounds(37, 118, 147, 16);
		panelSaida.add(lblObservaes);
		
		textFieldObsSaida = new JTextField();
		textFieldObsSaida.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldObsSaida.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldObsSaida.setBounds(34, 147, 448, 39);
		panelSaida.add(textFieldObsSaida);
		textFieldObsSaida.setColumns(10);
		
		JLabel lblDataDoAviso = new JLabel("Data do Aviso ");
		lblDataDoAviso.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDataDoAviso.setBounds(688, 119, 166, 16);
		panelSaida.add(lblDataDoAviso);
		
		dateChooserAvisoPagar = new JDateChooser();
		dateChooserAvisoPagar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateChooserAvisoPagar.setBounds(686, 142, 168, 30);
		panelSaida.add(dateChooserAvisoPagar);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Valor Total R$", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(350, 13, 237, 81);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		labelValorTotal = new JLabel("");
		labelValorTotal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelValorTotal.setBounds(12, 23, 213, 45);
		panel_2.add(labelValorTotal);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Valor Mensal R$", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(612, 13, 266, 80);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		labelValorMensal = new JLabel("");
		labelValorMensal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelValorMensal.setBounds(12, 24, 242, 43);
		panel_3.add(labelValorMensal);
		
		panel_1 = new JPanel();
		tabbedPane.addTab("Consulta", null, panel_1, null);		
		panel_1.setLayout(null);
		
		JLabel lblPesquisarPor = new JLabel("Pesquisar Por:");
		lblPesquisarPor.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPesquisarPor.setBounds(35, 32, 144, 16);
		panel_1.add(lblPesquisarPor);
		
		JComboBox comboBoxpesquisaPor = new JComboBox();
		comboBoxpesquisaPor.setModel(new DefaultComboBoxModel(new String[] {"Selecione Uma Op\u00E7\u00E3o", "Diversos", "Fornecedor ", "Funcion\u00E1rio", "Impostos"}));
		comboBoxpesquisaPor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBoxpesquisaPor.setBounds(35, 54, 204, 28);
		panel_1.add(comboBoxpesquisaPor);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 119, 849, 279);
		panel_1.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Total R$", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(658, 426, 221, 99);
		panel_1.add(panel_4);
		panel_4.setLayout(null);
		
		labelTotal = new JLabel("");
		labelTotal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelTotal.setBounds(12, 13, 197, 73);
		panel_4.add(labelTotal);
		
		JLabel lblPerodo = new JLabel("Per\u00EDodo");
		lblPerodo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPerodo.setBounds(258, 32, 110, 16);
		panel_1.add(lblPerodo);
		
		monthChooser = new JMonthChooser();
		monthChooser.getComboBox().setFont(new Font("Tahoma", Font.PLAIN, 18));
		monthChooser.setBounds(258, 54, 149, 28);
		panel_1.add(monthChooser);
		
		yearChooser = new JYearChooser();
		yearChooser.getSpinner().setFont(new Font("Tahoma", Font.BOLD, 18));
		yearChooser.setBounds(419, 48, 100, 34);
		panel_1.add(yearChooser);
		
		buttonPesquisar = new JButton("");
		buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ResultSet rs = null;

				String opcaoDesejada = (String)comboBoxpesquisaPor.getSelectedItem();
				int mesSelecionado = monthChooser.getMonth();
				int anoSelecionado = yearChooser.getYear();
				Date dataDoBanco = null;
				int mesBanco;
				int anoBanco;
				String sql = null;
				
				try{
					ConectaBanco conecta = new ConectaBanco();
					conecta.conectaBanco();
					
					String buscaData = "select * from valorSaida where motivoSaida = '"+opcaoDesejada+"'";	
					preencherTabela(buscaData);
					
					
				}catch(SQLException e){
					JOptionPane.showMessageDialog(null, e);
				}
				
			}
		});
		buttonPesquisar.setToolTipText("Pesquisar");
		buttonPesquisar.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/com/mlsolucoes/imagens/search.png")));
		buttonPesquisar.setBounds(50, 24, 51, 41);
		contentPane.add(buttonPesquisar);
		
		JButton buttonSair = new JButton("");
		buttonSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		buttonSair.setToolTipText("Sair");
		buttonSair.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/com/mlsolucoes/imagens/exit.png")));
		buttonSair.setBounds(150, 24, 51, 41);
		contentPane.add(buttonSair);
		
		JButton buttonAtualizar = new JButton("");
		buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ResultSet rs = null;
				comboBoxDescricaoMotivo.removeAllItems();
				
				try{
					ConectaBanco conecta = new ConectaBanco();
					conecta.conectaBanco();
					
					String buscaFuncionario = "select * from funcionarios";
					rs = conecta.stm.executeQuery(buscaFuncionario);
					
					while(rs.next()){
						comboBoxDescricaoMotivo.addItem(rs.getString("funcionarioNome"));
					}
					
				}catch(SQLException e){
					JOptionPane.showMessageDialog(null, e);
				}
				
			}
		});
		buttonAtualizar.setToolTipText("Atualizar");
		buttonAtualizar.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/com/mlsolucoes/imagens/reload.png")));
		buttonAtualizar.setBounds(100, 24, 51, 41);
		contentPane.add(buttonAtualizar);
		
		buscaMateriaPrima();
		buscaProduto();
		verificaValorNoBanco();
		//verificaContasPagar();
		
	}
}
