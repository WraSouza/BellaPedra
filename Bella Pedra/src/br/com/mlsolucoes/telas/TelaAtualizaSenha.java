package br.com.mlsolucoes.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
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
	private JTextField textField;
	private JPasswordField passwordField;

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
		btnAlterar.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnAlterar.setIcon(new ImageIcon(TelaAtualizaSenha.class.getResource("/br/com/mlsolucoes/imagens/sign-check-icon.png")));
		btnAlterar.setBounds(334, 317, 119, 41);
		panel.add(btnAlterar);
		
		JButton btnCancelar = new JButton("Sair");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnCancelar.setIcon(new ImageIcon(TelaAtualizaSenha.class.getResource("/br/com/mlsolucoes/imagens/sign-delete-icon.png")));
		btnCancelar.setBounds(485, 317, 119, 41);
		panel.add(btnCancelar);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 20));
		passwordField.setBounds(320, 188, 185, 26);
		panel.add(passwordField);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField.setBounds(320, 124, 185, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblUsurio = new JLabel("Usu\u00E1rio");
		lblUsurio.setForeground(Color.WHITE);
		lblUsurio.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblUsurio.setBounds(131, 124, 110, 25);
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
