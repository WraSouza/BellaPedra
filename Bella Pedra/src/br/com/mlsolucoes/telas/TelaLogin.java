package br.com.mlsolucoes.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.SystemColor;

public class TelaLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 676, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 686, 488);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBackground(SystemColor.inactiveCaption);
		btnOk.setIcon(new ImageIcon(TelaLogin.class.getResource("/br/com/mlsolucoes/imagens/sign-check-icon.png")));
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnOk.setBounds(368, 401, 112, 41);
		panel.add(btnOk);
		
		JButton btnSair = new JButton("Sair");
		btnSair.setBackground(SystemColor.inactiveCaption);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSair.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnSair.setIcon(new ImageIcon(TelaLogin.class.getResource("/br/com/mlsolucoes/imagens/sign-delete-icon.png")));
		btnSair.setBounds(499, 401, 112, 41);
		panel.add(btnSair);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(299, 210, 202, 31);
		panel.add(passwordField);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 674, 26);
		panel.add(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		mnArquivo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnArquivo);
		
		JMenuItem mntmNovoUsurio = new JMenuItem("Novo Usu\u00E1rio");
		mntmNovoUsurio.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		mnArquivo.add(mntmNovoUsurio);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		mnArquivo.add(mntmSair);
		
		JMenu mnSobre = new JMenu("Sobre");
		mnSobre.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnSobre);
		
		JMenuItem mntmSobreAAplicao = new JMenuItem("Sobre a Aplica\u00E7\u00E3o");
		mntmSobreAAplicao.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		mnSobre.add(mntmSobreAAplicao);
		
		JLabel lblUsurio = new JLabel("Usu\u00E1rio");
		lblUsurio.setForeground(Color.WHITE);
		lblUsurio.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblUsurio.setBounds(123, 127, 112, 16);
		panel.add(lblUsurio);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField.setBounds(299, 115, 202, 31);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setForeground(Color.WHITE);
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSenha.setBounds(123, 222, 90, 16);
		panel.add(lblSenha);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(TelaLogin.class.getResource("/br/com/mlsolucoes/imagens/login.jpg")));
		label.setBounds(0, 26, 674, 462);
		panel.add(label);
	}
}
