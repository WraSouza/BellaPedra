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
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	private JFormattedTextField formattedTextFieldCPF;
	private JTextField textFieldNomeCliente;
	private JPanel panelEntrada;

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
	
	private void formatarCPF(){
		try {
			MaskFormatter mask = new MaskFormatter("###.###.###-##");
			mask.install(formattedTextFieldCPF);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Create the frame.
	 */
	public TelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 925, 577);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(0, 24, 97, 25);
		contentPane.add(btnNewButton);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 907, 26);
		contentPane.add(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 62, 907, 468);
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
				
				if(opcaoDesejada=="Entrada"){
					panelEntrada.setEnabled(true);
					formattedTextFieldCPF.setEditable(true);
					textFieldNomeCliente.setEditable(true);
				}else{
					panelEntrada.setEnabled(false);
					formattedTextFieldCPF.setEditable(false);
					textFieldNomeCliente.setEditable(false);
				}
				
			}
		});
		comboBoxOpcaoDesejada.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBoxOpcaoDesejada.setModel(new DefaultComboBoxModel(new String[] {"Selecione Uma Op\u00E7\u00E3o", "Entrada", "Sa\u00EDda"}));
		comboBoxOpcaoDesejada.setBounds(31, 45, 211, 28);
		panel.add(comboBoxOpcaoDesejada);
		
		panelEntrada = new JPanel();
		panelEntrada.setBorder(new TitledBorder(null, "Entrada", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panelEntrada.setBounds(12, 95, 866, 163);
		panel.add(panelEntrada);
		panelEntrada.setLayout(null);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCpf.setBounds(12, 26, 74, 16);
		panelEntrada.add(lblCpf);
		
		formattedTextFieldCPF = new JFormattedTextField();
		formattedTextFieldCPF.setFont(new Font("Tahoma", Font.PLAIN, 18));
		formattedTextFieldCPF.setBounds(12, 46, 175, 30);
		panelEntrada.add(formattedTextFieldCPF);
		
		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCliente.setBounds(218, 26, 74, 16);
		panelEntrada.add(lblCliente);
		
		textFieldNomeCliente = new JTextField();
		textFieldNomeCliente.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldNomeCliente.setBounds(218, 46, 223, 28);
		panelEntrada.add(textFieldNomeCliente);
		textFieldNomeCliente.setColumns(10);
		
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
		
		formatarCPF();
	}
}
