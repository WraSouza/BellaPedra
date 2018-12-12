package br.com.mlsolucoes.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;

import br.com.mlsolucoes.classes.Funcionario;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;

public class TelaFuncionario extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNomeFuncionario;
	private JDateChooser dateChooserAdmissao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaFuncionario frame = new TelaFuncionario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaFuncionario() {
		setTitle("Inserir Dados do Funcion\u00E1rio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 481, 492);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 463, 445);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNomeDoFuncionrio = new JLabel("Nome do Funcion\u00E1rio");
		lblNomeDoFuncionrio.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNomeDoFuncionrio.setBounds(49, 64, 194, 16);
		panel.add(lblNomeDoFuncionrio);
		
		textFieldNomeFuncionario = new JTextField();
		textFieldNomeFuncionario.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldNomeFuncionario.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldNomeFuncionario.setBounds(49, 88, 352, 30);
		panel.add(textFieldNomeFuncionario);
		textFieldNomeFuncionario.setColumns(10);
		
		JLabel lblDataDeAdmisso = new JLabel("Data de Admiss\u00E3o");
		lblDataDeAdmisso.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDataDeAdmisso.setBounds(56, 166, 164, 16);
		panel.add(lblDataDeAdmisso);
		
		dateChooserAdmissao = new JDateChooser();
		dateChooserAdmissao.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateChooserAdmissao.setBounds(54, 195, 206, 30);
		panel.add(dateChooserAdmissao);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();				
			}
		});
		btnSair.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnSair.setIcon(new ImageIcon(TelaFuncionario.class.getResource("/br/com/mlsolucoes/imagens/sign-delete-icon.png")));
		btnSair.setBounds(312, 372, 111, 35);
		panel.add(btnSair);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nomeFuncionario = textFieldNomeFuncionario.getText();
				Date data =  (Date)dateChooserAdmissao.getDate();
				Funcionario novoFuncionario = new Funcionario();
				
				//Verifica se Funcionário Já está cadastrado no banco
				boolean verifica = novoFuncionario.verificaFuncionario(nomeFuncionario);
				
				//Se usuário já cadastrado, informa o usuário
				if(verifica){
					JOptionPane.showMessageDialog(null, "Usuário Já Cadastrado no Sistema");
				}				
				else{
				if((nomeFuncionario=="")||(data==null)){
					JOptionPane.showMessageDialog(null, "Insira Todos os Campos Solicitados");
				}else{
					//Enviar dados a serem inseridos					
					novoFuncionario.setFuncionarioNome(nomeFuncionario);
					novoFuncionario.setDataAdmissao(data);
					novoFuncionario.setFuncionarioStatus("Ativo");
					novoFuncionario.cadastraFuncionario();
					JOptionPane.showMessageDialog(null, "Usuário Cadastrado Com Sucesso");
					textFieldNomeFuncionario.setText("");
					dateChooserAdmissao.setDate(null);
				}
				}
				
			}
		});
		btnOk.setIcon(new ImageIcon(TelaFuncionario.class.getResource("/br/com/mlsolucoes/imagens/sign-check-icon.png")));
		btnOk.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnOk.setBounds(191, 373, 111, 34);
		panel.add(btnOk);
	}
}
