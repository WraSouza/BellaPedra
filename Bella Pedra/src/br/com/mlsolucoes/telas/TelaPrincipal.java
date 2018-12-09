package br.com.mlsolucoes.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.text.ParseException;

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

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ItemEvent;
import javax.swing.SwingConstants;
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
	private JTextField textFieldProduto;
	private JComboBox comboBoxMateriaPrima;
	private JFormattedTextField formattedTextFieldValorEntrada;
	private JDateChooser dateChooserEntrada;
	private JDateChooser dateChooserSaida;
	private JTextField textFieldMotivo;

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
		
	}

	/**
	 * Create the frame.
	 */
	public TelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 925, 638);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(SystemColor.menu);
		btnNewButton.setToolTipText("Salvar");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\wladi\\Downloads\\save.png"));
		btnNewButton.setBounds(0, 24, 51, 41);
		contentPane.add(btnNewButton);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 907, 26);
		contentPane.add(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		JMenu mnSobre = new JMenu("Sobre");
		menuBar.add(mnSobre);
		
		JMenuItem mntmSobreAAplicao = new JMenuItem("Sobre a Aplica\u00E7\u00E3o");
		mntmSobreAAplicao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaSobre novaTelaSobre = new TelaSobre();
				novaTelaSobre.setVisible(true);
			}
		});
		mnSobre.add(mntmSobreAAplicao);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 82, 907, 550);
		tabbedPane.setFont(new Font("Dialog",Font.BOLD,16));
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Entrada/Sa\u00EDda", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblInserirUmaNova = new JLabel("Inserir Uma Nova:");
		lblInserirUmaNova.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblInserirUmaNova.setBounds(31, 23, 186, 16);
		panel.add(lblInserirUmaNova);
		
		JComboBox comboBoxOpcaoDesejada = new JComboBox();
		comboBoxOpcaoDesejada.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
				String opcaoDesejada = (String)comboBoxOpcaoDesejada.getSelectedItem();
				
				//Se a opção desejada for ENTRADA, irá habilitar as opções para a função de entada de valores da venda, como nome, valor, cliente, etc..
				if(opcaoDesejada=="Entrada"){
					//habilita componentes do painel de Entrada
					panelEntrada.setEnabled(true);
					formattedTextFieldCPF.setEditable(true);
					textFieldNomeCliente.setEditable(true);
					textFieldProduto.setEditable(true);
					comboBoxMateriaPrima.setEnabled(true);
					formattedTextFieldValorEntrada.setEditable(true);
					dateChooserEntrada.setEnabled(true);
					
					//desabilita componentes do painel de Saída
					panelSaida.setEnabled(false);
					formattedTextFieldValorSaida.setEditable(false);
					textFieldMotivo.setEditable(false);
					dateChooserSaida.setEnabled(false);					
					
				}else if(opcaoDesejada=="Saída"){
					//desabilita componentes do painel de Entrada
					panelEntrada.setEnabled(false);
					formattedTextFieldCPF.setEditable(false);
					textFieldNomeCliente.setEditable(false);
					textFieldProduto.setEditable(false);
					comboBoxMateriaPrima.setEnabled(false);;
					formattedTextFieldValorEntrada.setEditable(false);
					dateChooserEntrada.setEnabled(false);
					
					//habilita componentes do painel de Saída
					panelSaida.setEnabled(true);
					formattedTextFieldValorSaida.setEditable(true);
					textFieldMotivo.setEditable(true);
					dateChooserSaida.setEnabled(true);	
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
		lblProduto.setBounds(457, 26, 96, 16);
		panelEntrada.add(lblProduto);
		
		textFieldProduto = new JTextField();
		textFieldProduto.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldProduto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldProduto.setBounds(457, 46, 162, 28);
		panelEntrada.add(textFieldProduto);
		textFieldProduto.setColumns(10);
		
		JLabel lblMatriaprima = new JLabel("Mat\u00E9ria-Prima");
		lblMatriaprima.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMatriaprima.setBounds(649, 27, 127, 16);
		panelEntrada.add(lblMatriaprima);
		
		comboBoxMateriaPrima = new JComboBox();
		comboBoxMateriaPrima.setModel(new DefaultComboBoxModel(new String[] {"Selecione Um Item"}));
		comboBoxMateriaPrima.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBoxMateriaPrima.setBounds(649, 46, 175, 28);
		panelEntrada.add(comboBoxMateriaPrima);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblValor.setBounds(40, 102, 56, 16);
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
		
		panelSaida = new JPanel();
		panelSaida.setBorder(new TitledBorder(null, "Sa\u00EDda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSaida.setBounds(12, 285, 866, 186);
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
		lblMotivo.setBounds(207, 34, 76, 16);
		panelSaida.add(lblMotivo);
		
		textFieldMotivo = new JTextField();
		textFieldMotivo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldMotivo.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldMotivo.setBounds(207, 55, 198, 30);
		panelSaida.add(textFieldMotivo);
		textFieldMotivo.setColumns(10);
		
		dateChooserSaida = new JDateChooser();
		dateChooserSaida.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateChooserSaida.setBounds(446, 55, 173, 30);
		panelSaida.add(dateChooserSaida);
		
		JLabel lblDataDoPagamento = new JLabel("Data do Pagamento");
		lblDataDoPagamento.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDataDoPagamento.setBounds(446, 34, 173, 16);
		panelSaida.add(lblDataDoPagamento);
		
		JPanel panel_1 = new JPanel();
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
		
		//formatarCPF();
		buscaMateriaPrima();
	}
}
