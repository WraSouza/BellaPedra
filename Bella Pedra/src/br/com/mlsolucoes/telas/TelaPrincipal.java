package br.com.mlsolucoes.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JButton;
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

import br.com.mlsolucoes.classes.ConectaBanco;
import br.com.mlsolucoes.classes.EntradaValor;
import br.com.mlsolucoes.classes.MateriaPrima;
import br.com.mlsolucoes.classes.Movimentacao;
import br.com.mlsolucoes.classes.Produto;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import java.awt.event.ItemListener;
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
	private JDateChooser dateChooserSaida;
	private String user;
	private JPanel panel_1;
	private JButton btnSalvar;
	private JButton buttonPesquisar;
	private JTextField textFieldObsSaida;
	private JMenu mnAdicionar;
	private JLabel labelValorMensal;
	private JLabel labelValorTotal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);						
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	private void formatarCPF(){
		try {
			MaskFormatter mask = new MaskFormatter("###.###.###-##");
			mask.install(formattedTextFieldCPF);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}*/	
	
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
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();
			
			String buscaValores = "select * from movimentacao";
			rs = conecta.stm.executeQuery(buscaValores);			
							
				while(rs.next()){
					
					String valorMensal = String.valueOf(rs.getDouble("movimentacaoMensal"));
					String valorTotal = String.valueOf(rs.getDouble("movimentacaoTotal"));
					labelValorMensal.setText(String.valueOf(valorMensal));
					labelValorTotal.setText(String.valueOf(valorTotal));	
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
			mnAdicionar.setEnabled(false);
			labelValorTotal.setVisible(false);
		}
		
	}

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
				
				if(opcaoDesejada=="Selecione Uma Opção"){
					JOptionPane.showMessageDialog(null, "Selecione Uma Opção");
				}else if(opcaoDesejada=="Entrada"){
					
					EntradaValor novaEntrada = new EntradaValor();
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
					int dataCalendario = data.getDate();
					int dataDoSistema = dataSistema.getDate();
					
					//Pega o mês
					int mesCalendario = data.getMonth();
					int mesAtual = dataSistema.getMonth();
					
					
					//Verifica se todos os campos de "ENTRADA" estão preenchidos antes de salvar.Caso não estejam, mostra mensagem informando o usuário
					if((cpf_cnpj=="")||(nomeCliente=="")||(nomeProduto=="Selecione Produto")||(materiaPrima=="Selecione Um Item")||(valor=="")||(data==null)){
						JOptionPane.showMessageDialog(null, "Favor Preencher Todos os Campos");
					}else{
						//Manda dados para serem inseridos no banco de dados
						//Compara se a data do pagamento é maior que a data de hoje. Se for maior, não muda o valor do mês pois ainda não foi pago.
						if(dataCalendario<=dataDoSistema){
							
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
							cpf_cnpj = "";
							nomeCliente = "";
							
							//Insere dados no banco da movimentação mensal
							if(mesAtual==mesCalendario){
								
								Movimentacao movimentacao = new Movimentacao();	
								String valorNoBancoMensal = labelValorMensal.getText();
								String valorNoBancoTotal = labelValorTotal.getText();
								
								if(valorNoBancoMensal.isEmpty()){
									movimentacao.setValorMensal(Double.parseDouble(valor));
									movimentacao.setValorTotal(Double.parseDouble(valor));
									movimentacao.setMes(mesAtual);
									movimentacao.insereNovaMovimentacao();
									labelValorMensal.setText(valor);
									labelValorTotal.setText(valor);
									cpf_cnpj = "";
									nomeCliente = "";									
									
								}else{
									Double valorTotal = Double.parseDouble(valor) + Double.parseDouble(valorNoBancoMensal);
									movimentacao.setValorMensal(valorTotal);
									movimentacao.setValorTotal(valorTotal);
									movimentacao.setMes(mesAtual);
									movimentacao.atualizaMovimentacao();
									labelValorMensal.setText(String.valueOf(valorTotal));
									labelValorTotal.setText(String.valueOf(valorTotal));
									cpf_cnpj = "";
									nomeCliente = "";
								}
								
																
								
							}//atualiza valor mensal se os meses forem iguais.
							//Se os meses forem diferentes
							else{
								//criar código aqui caso os meses sejam diferentes
							}
							
							
						}else {
							
						}
					}					
					
				}//Fim do if para opção "Entrada"
				else if(opcaoDesejada=="Saída"){
					
					String valorSaida = formattedTextFieldValorSaida.getText();
					String motivo = (String)comboBoxMotivo.getSelectedItem();
					Date data = (Date)dateChooserSaida.getDate();
					
					//Verifica se todos os campos estão preenchidos antes de salvar.Caso não estejam, mostra mensagem informando o usuário
					if((valorSaida=="")||(motivo=="Selecione Uma Opção")||(data==null)){
						JOptionPane.showMessageDialog(null, "Favor Preencher Todos os Campos");
					}else{
						//Envia dados a serem gravados no banco de dados
					}
				}
				
				
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
				//UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 18));
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
			}
		});
		mnAdicionar.add(mntmAdicionarProduto);
		
		JMenuItem mntmAdicionarMatriaprima = new JMenuItem("Adicionar Mat\u00E9ria-Prima");
		mntmAdicionarMatriaprima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				MateriaPrima novaMateriaPrima = new MateriaPrima();
				boolean verifica = false;
				UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 18));
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
					dateChooserSaida.setEnabled(false);		
					textFieldObsSaida.setEditable(false);
					
				}else if(opcaoDesejada=="Saída"){
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
				}
				
			}
		});
		comboBoxOpcaoDesejada.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBoxOpcaoDesejada.setModel(new DefaultComboBoxModel(new String[] {"Selecione Uma Op\u00E7\u00E3o", "Entrada", "Sa\u00EDda"}));
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
		textFieldObsSaida.setBounds(34, 147, 624, 39);
		panelSaida.add(textFieldObsSaida);
		textFieldObsSaida.setColumns(10);
		
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
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 902, 433);
		panel_1.add(label);
		
		JLabel lblPesquisarPor = new JLabel("Pesquisar Por:");
		lblPesquisarPor.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPesquisarPor.setBounds(35, 32, 144, 16);
		panel_1.add(lblPesquisarPor);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(35, 54, 169, 22);
		panel_1.add(comboBox);
		
		buttonPesquisar = new JButton("");
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
		buttonSair.setBounds(148, 24, 51, 41);
		contentPane.add(buttonSair);
		
		JButton buttonAlerta = new JButton("");
		buttonAlerta.setToolTipText("Gerar Alerta");
		buttonAlerta.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/com/mlsolucoes/imagens/alarm.png")));
		buttonAlerta.setBounds(99, 24, 51, 41);
		contentPane.add(buttonAlerta);
		
		//formatarCPF();
		buscaMateriaPrima();
		buscaProduto();
		verificaValorNoBanco();
		
	}
}
