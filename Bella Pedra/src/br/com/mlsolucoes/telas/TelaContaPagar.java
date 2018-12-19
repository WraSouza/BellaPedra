package br.com.mlsolucoes.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.mlsolucoes.classes.ConectaBanco;
import br.com.mlsolucoes.classes.ModeloTabela;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.JScrollPane;

public class TelaContaPagar extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaContaPagar frame = new TelaContaPagar();
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
		Date data = null;
		String dataFormatada = null;		
		
		try{
			ConectaBanco conecta = new ConectaBanco();
			conecta.conectaBanco();

			rs = conecta.stm.executeQuery(sql);
				
			while(rs.next()){
			data = rs.getDate("dataLembrarPagamento");
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			dataFormatada = formato.format(data);
			dados.add(new Object[]{rs.getString("motivoConta"),rs.getString("descricaoConta"),rs.getDouble("valorConta"),dataFormatada});
			}		
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, e);
		}
		
		ModeloTabela modelo = new ModeloTabela(dados, colunas);
		table.setModel(modelo);
		table.getColumnModel().getColumn(0).setPreferredWidth(248);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(248);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(248);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(248);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}//Fim do método de preencher a tabela com as contas a serem pagas

	/**
	 * Create the frame.
	 */
	public TelaContaPagar() {
		setTitle("Contas a Pagar");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1016, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 997, 393);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 17));
		scrollPane.setViewportView(table);
	}
}
