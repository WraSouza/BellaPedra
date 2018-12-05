package br.com.mlsolucoes.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class TelaSobre extends JDialog {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaSobre frame = new TelaSobre();
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
	public TelaSobre() {
		setTitle("Sobre a Aplica\u00E7\u00E3o");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 479, 336);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setModal(true);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 461, 289);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblDesenvolvidoPor = new JLabel("Desenvolvido por: ML Solu\u00E7\u00F5es");
		lblDesenvolvidoPor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDesenvolvidoPor.setBounds(40, 120, 259, 54);
		panel.add(lblDesenvolvidoPor);
		
		JLabel lblContatoMlsolucoesoutlookcom = new JLabel("Contato:  ml-solucoes@outlook.com");
		lblContatoMlsolucoesoutlookcom.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblContatoMlsolucoesoutlookcom.setBounds(40, 210, 312, 25);
		panel.add(lblContatoMlsolucoesoutlookcom);
		
		JLabel lblVerso = new JLabel("Vers\u00E3o: 1.0");
		lblVerso.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblVerso.setBounds(40, 53, 103, 32);
		panel.add(lblVerso);
	}
}
