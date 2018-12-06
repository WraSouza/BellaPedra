package br.com.mlsolucoes.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.mlsolucoes.classes.Login;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaAtualizaSenha extends JDialog {

	private JPanel contentPane;
	private JTextField textFieldUser;
	private JPasswordField passwordFieldNova;
	private JPasswordField passwordFieldAntes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaAtualizaSenha frame = new TelaAtualizaSenha();
					frame.setVisible(true);
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
	public TelaAtualizaSenha() {
		setTitle("Atualiza\u00E7\u00E3o de Senha");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 643, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setModal(true);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 625, 408);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Login login = new Login();
				
				boolean verificaUsuario;
				
				String usuario = textFieldUser.getText();
				String senhaUsuario = passwordFieldAntes.getText();
				String senhaUsuarioNova = passwordFieldNova.getText();
				
				verificaUsuario = login.verificaUsuario(usuario, senhaUsuario);
				
				if(verificaUsuario){
					login.alteraSenha(usuario, senhaUsuarioNova);
					JOptionPane.showMessageDialog(null, "Senha Alterada Com Sucesso");
				}else{
					JOptionPane.showMessageDialog(null, "Senha Não Confere");
				}				
				
			}
		});
		
		passwordFieldAntes = new JPasswordField();
		passwordFieldAntes.setBounds(320, 122, 185, 26);
		panel.add(passwordFieldAntes);
		
		JLabel lblSenhaAtual = new JLabel("Senha Atual");
		lblSenhaAtual.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSenhaAtual.setForeground(Color.WHITE);
		lblSenhaAtual.setBounds(132, 126, 138, 16);
		panel.add(lblSenhaAtual);
		btnAlterar.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnAlterar.setIcon(new ImageIcon(TelaAtualizaSenha.class.getResource("/br/com/mlsolucoes/imagens/sign-check-icon.png")));
		btnAlterar.setBounds(334, 317, 119, 41);
		panel.add(btnAlterar);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSair.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnSair.setIcon(new ImageIcon(TelaAtualizaSenha.class.getResource("/br/com/mlsolucoes/imagens/sign-delete-icon.png")));
		btnSair.setBounds(485, 317, 119, 41);
		panel.add(btnSair);
		
		passwordFieldNova = new JPasswordField();
		passwordFieldNova.setFont(new Font("Tahoma", Font.BOLD, 20));
		passwordFieldNova.setBounds(320, 188, 185, 26);
		panel.add(passwordFieldNova);
		
		textFieldUser = new JTextField();
		textFieldUser.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textFieldUser.setBounds(320, 51, 185, 26);
		panel.add(textFieldUser);
		textFieldUser.setColumns(10);
		
		JLabel lblUsurio = new JLabel("Usu\u00E1rio");
		lblUsurio.setForeground(Color.WHITE);
		lblUsurio.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblUsurio.setBounds(131, 52, 110, 25);
		panel.add(lblUsurio);
		
		JLabel lblNovaSenha = new JLabel("Nova Senha");
		lblNovaSenha.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNovaSenha.setForeground(Color.WHITE);
		lblNovaSenha.setBounds(131, 188, 139, 25);
		panel.add(lblNovaSenha);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(TelaAtualizaSenha.class.getResource("/br/com/mlsolucoes/imagens/login.jpg")));
		label.setBounds(0, 0, 625, 408);
		panel.add(label);
	}
}
