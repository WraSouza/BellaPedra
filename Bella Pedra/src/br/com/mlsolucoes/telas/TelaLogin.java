package br.com.mlsolucoes.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.mlsolucoes.classes.ConectaBanco;
import br.com.mlsolucoes.classes.Login;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUser;
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
		setTitle("Tela de Login");
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
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Login login = new Login();
				ResultSet rs = null;
				String status = "PENDENTE";
				
				//Pega a data do sistema
				LocalDate date = LocalDate.now();
				Date dataSistema = java.sql.Date.valueOf(date);
				//int diaDoSistema = dataSistema.getDate();
				
				String usuario = textFieldUser.getText();
				String senha = passwordField.getText();
				
				try{
				ConectaBanco conecta = new ConectaBanco();
				conecta.conectaBanco();
				
				boolean verificaUsuario = login.verificaUsuario(usuario, senha);
				
				if(verificaUsuario){
					TelaPrincipal novaTela = new TelaPrincipal();
					TelaContaPagar novaTelaConta = new TelaContaPagar();
					
					//Verifica se existe contas a pagar no banco
					String sql = "select * from contaPagar where statusConta = '"+status+"' and dataLembrarPagamento='"+dataSistema+"'";
					rs = conecta.stm.executeQuery(sql);
										
					if(!rs.isBeforeFirst()){

						novaTela.setVisible(true);
						
					}else{
						
					novaTela.setVisible(true);
					novaTela.recebeLogin(usuario);	
					JOptionPane.showMessageDialog(null, "Há Contas a Serem Pagas");
					novaTelaConta.setVisible(true);
					novaTelaConta.preencherTabela(sql);
					
					}
					
					dispose();
				}else{
					JOptionPane.showMessageDialog(null, "Dados Não Conferem");
				}
				
				}catch(SQLException e){
					
				}
			}
		});
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
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
               
				String status = "PENDENTE";
				ResultSet RS = null;
				
				//Pega a data do sistema
				LocalDate date = LocalDate.now();
				Date dataSistema = java.sql.Date.valueOf(date);
				//int diaDoSistema = dataSistema.getDate();
				
				if(evt.getKeyCode()==KeyEvent.VK_ENTER){
					try{
			            
				        ConectaBanco conecta = new ConectaBanco();			        
				        conecta.conectaBanco();				       
				        
				        String loginusuario = textFieldUser.getText();
				        String senhaUsuario = passwordField.getText();
				        
				        String fazerLogin = "select * from login where loginUser = '" +loginusuario+ "'";
				        
				        ResultSet rs = ConectaBanco.stm.executeQuery(fazerLogin);
				        rs.first();
				       
				        if((loginusuario.equals(rs.getString("loginUser")))&&(senhaUsuario.equals(rs.getString("loginSenha"))))
				        {				            				            
				                JOptionPane.showMessageDialog(null, "Acesso Autorizado");
				                
				                //Chamar Tela de Opções  
				                 textFieldUser.setText("");
				                    passwordField.setText("");                    
				                    TelaPrincipal telaopcoes = new TelaPrincipal();
				                    TelaContaPagar novaConta = new TelaContaPagar();
									
									//Verifica se existe contas a pagar no banco
									String sql = "select * from contaPagar where statusConta = '"+status+"' and dataLembrarPagamento='"+dataSistema+"'";
									rs = conecta.stm.executeQuery(sql);
														
									if(!rs.isBeforeFirst()){
										
										telaopcoes.setVisible(true);
										telaopcoes.recebeLogin(loginusuario);
										
									}else{
										
									telaopcoes.setVisible(true);
									telaopcoes.recebeLogin(loginusuario);	
									
									JOptionPane.showMessageDialog(null, "Há Contas a Serem Pagas");
									
									novaConta.setVisible(true);
									novaConta.preencherTabela(sql);
									
									telaopcoes.recebeLogin(loginusuario);
									
									}
				                   
				                    dispose();				                
				            
				        }else{
				            JOptionPane.showMessageDialog(null, "Senha Incorreta");
				            textFieldUser.setText("");
				            passwordField.setText("");
				        }
				        
				        }catch(SQLException ex){
				            JOptionPane.showMessageDialog(null, "Usuáro Não Cadastrado");
				            textFieldUser.setText("");
				            passwordField.setText("");
				        }
				}
			}
		});
		passwordField.setBounds(299, 210, 202, 31);
		panel.add(passwordField);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 674, 26);
		panel.add(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		mnArquivo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		menuBar.add(mnArquivo);
		
		JMenuItem mntmNovoUsurio = new JMenuItem("Atualizar Senha");
		mntmNovoUsurio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaAtualizaSenha novaTela = new TelaAtualizaSenha();
				novaTela.setVisible(true);
				novaTela.setLocationRelativeTo(null);
			}
		});
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
		mntmSobreAAplicao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaSobre novaTelaSobre = new TelaSobre();
				novaTelaSobre.setVisible(true);
				novaTelaSobre.setLocationRelativeTo(null);
			}
		});
		mntmSobreAAplicao.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		mnSobre.add(mntmSobreAAplicao);
		
		JLabel lblUsurio = new JLabel("Usu\u00E1rio");
		lblUsurio.setForeground(Color.WHITE);
		lblUsurio.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblUsurio.setBounds(123, 127, 112, 16);
		panel.add(lblUsurio);
		
		textFieldUser = new JTextField();
		textFieldUser.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textFieldUser.setBounds(299, 115, 202, 31);
		panel.add(textFieldUser);
		textFieldUser.setColumns(10);
		
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
