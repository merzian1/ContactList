import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import net.proteanit.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Button;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;





public class MainFrame {

	private JFrame frmContactList;
	private JTable table;
	private JTextField id;
	private JTextField firstname;
	private JTextField lastname;
	private JTextField age;
	private JTextField phone;
	private JScrollPane scrollPane;
	public int newId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frmContactList.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection conn = null;
	private JTextField textField;
	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
		conn = SQLconnection.dbConnector();
		
		try{
			String query = "select idperson AS 'ID #', firstname AS 'FIRSTNAME', lastname 'LASTNAME', age AS 'AGE' FROM person";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			textField = new JTextField();
			textField.setBackground(SystemColor.inactiveCaption);
			textField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) 
				{
						try{
							
								String query = "SELECT idperson AS 'ID #', firstname AS 'FIRSTNAME', lastname 'LASTNAME', age AS 'AGE' FROM person WHERE idperson =? OR firstname = ? OR lastname = ? OR age = ? ";
								PreparedStatement pst = conn.prepareStatement(query);
								pst.setString(1,textField.getText());
								pst.setString(2,textField.getText());
								pst.setString(3,textField.getText());
								pst.setString(4,textField.getText());
								ResultSet rs = pst.executeQuery();
								table.setModel(DbUtils.resultSetToTableModel(rs));
								
									
									
							}catch(Exception ex)
							{
								JOptionPane.showMessageDialog(null, ex);
								
							}
						
											
				}
			});
			
			textField.setBounds(367, 65, 191, 20);
			frmContactList.getContentPane().add(textField);
			textField.setColumns(10);
			
			JLabel lblNewLabel_5 = new JLabel("Quick Search:");
			lblNewLabel_5.setBounds(272, 68, 121, 14);
			frmContactList.getContentPane().add(lblNewLabel_5);
			
			JLabel lbltitle = new JLabel("\"CONTACT LIST\"");
			lbltitle.setFont(new Font("Lucida Handwriting", Font.BOLD, 31));
			lbltitle.setBounds(216, 11, 384, 34);
			frmContactList.getContentPane().add(lbltitle);
			
			JButton btnRefresh = new JButton("REFRESH");
			btnRefresh.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						
											
						String query3 = "select idperson AS 'ID #', firstname AS 'FIRSTNAME', lastname 'LASTNAME', age AS 'AGE' FROM person";
						PreparedStatement pst3 = conn.prepareStatement(query3);
						ResultSet rs3 = pst3.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs3));	
						pst3.close();
						
						firstname.setText("");
						lastname.setText("");
						age.setText("");
						phone.setText("");
						id.setText("");
						textField.setText("");
						
						
						
						
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);
						
					}
					
					
				}
			});
			btnRefresh.setHorizontalAlignment(SwingConstants.LEFT);
			btnRefresh.setBounds(667, 64, 96, 23);
			frmContactList.getContentPane().add(btnRefresh);
			
			JButton btnRetrieve = new JButton("RETRIEVE");
			btnRetrieve.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						
						
						String query3 = "select * FROM person where idperson = '"+id.getText()+"'";
						PreparedStatement pst3 = conn.prepareStatement(query3);
						ResultSet rs3 = pst3.executeQuery();
						while(rs3.next()){
							id.setText(rs3.getString("idperson"));
							firstname.setText(rs3.getString("firstname"));
							lastname.setText(rs3.getString("lastname"));
							age.setText(rs3.getString("age"));
												
						}
						pst3.close();
						
						String query = "select number FROM phone where pid = '"+id.getText()+"'";
						PreparedStatement pst = conn.prepareStatement(query);
						ResultSet rs = pst.executeQuery();
						while(rs.next()){
							phone.setText(rs.getString("number"));
												
						}
						pst3.close();
						
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex);
						
					}
				}
			});
			btnRetrieve.setBounds(133, 95, 96, 23);
			frmContactList.getContentPane().add(btnRetrieve);
			
			JButton btnNewButton_1 = new JButton("CLEAR");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					firstname.setText("");
					lastname.setText("");
					age.setText("");
					phone.setText("");
					id.setText("");
					textField.setText("");
				}
			});
			btnNewButton_1.setBounds(568, 64, 89, 23);
			frmContactList.getContentPane().add(btnNewButton_1);
					
			pst.close();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex);
			
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	
	private void initialize() {
		frmContactList = new JFrame();
		frmContactList.setTitle("Contact List");
		frmContactList.getContentPane().setBackground(Color.WHITE);
		frmContactList.setBounds(100, 100, 789, 410);
		frmContactList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmContactList.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(236, 95, 527, 266);
		frmContactList.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				try {
					int row = table.getSelectedRow();
					String ids = (table.getModel().getValueAt(row,0)).toString();
					
					String query = "SELECT number FROM phone WHERE pid = '"+ids+"'";
					PreparedStatement pst = conn.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()){
						String nid=rs.getString("number"); 
						JOptionPane.showMessageDialog(null,"Phone No: "+nid+"", "PHONE NUMBER", JOptionPane.INFORMATION_MESSAGE);
					
					}
					pst.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		scrollPane.setViewportView(table);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(236, 95, 527, 266);
		frmContactList.getContentPane().add(scrollBar);
		
		JButton btnNewButton = new JButton("ADD CONTACT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try{
				
					
					String query = "INSERT into person (idperson,firstname, lastname, age) VALUES (?,?,?,?)";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1,id.getText());
					pst.setString(2,firstname.getText());
					pst.setString(3,lastname.getText());
					pst.setString(4,age.getText());
						
					
					pst.execute();
				/*	String q ="SELECT idperson FROM person ORDER BY 1 DESC";
					*PreparedStatement ps = conn.prepareStatement(q,PreparedStatement.RETURN_GENERATED_KEYS);
					*ps.executeQuery();
					*
					*ResultSet rs = ps.getGeneratedKeys();
					*if (rs.next()) {
					 *  newId = rs.getInt(1);
					*}
					*/
					
					
					String query1 = "INSERT into phone (number, pid) VALUES (?,?)";
					PreparedStatement pst1 = conn.prepareStatement(query1);
					//pst.setString(1,id.getText());
					//pst.setString(1,firstname.getText());
					pst1.setString(1,phone.getText());
					pst1.setString(2,id.getText());
					
					firstname.setText("");
					lastname.setText("");
					age.setText("");
					phone.setText("");
					id.setText("");
					
					pst1.execute();
					
					
					JOptionPane.showMessageDialog(null,"New Contact Added!");
					pst.close();
					pst1.close();
					
					
					
					
					String query2 = "select idperson AS 'ID #', firstname AS 'FIRSTNAME', lastname 'LASTNAME', age AS 'AGE' FROM person";
					PreparedStatement pst2 = conn.prepareStatement(query2);
					ResultSet rs2 = pst2.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs2));	
					pst2.close();
					
					String query3 = "select idperson AS 'ID #', firstname AS 'FIRSTNAME', lastname 'LASTNAME', age AS 'AGE' FROM person";
					PreparedStatement pst3 = conn.prepareStatement(query3);
					ResultSet rs3 = pst3.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs3));	
					pst3.close();
					
					
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
					
				}
				
				
				
			}
		});
		btnNewButton.setBounds(10, 280, 218, 23);
		frmContactList.getContentPane().add(btnNewButton);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(null, "Do you want to Delete?","Delete",JOptionPane.YES_NO_OPTION);
			if(action == 0)
			{
				try{
				
					
					String query = "DELETE FROM person WHERE idperson ='"+id.getText()+"'";
					PreparedStatement pst = conn.prepareStatement(query);
					
					String query1 = "DELETE FROM phone WHERE pid ='"+id.getText()+"'";
					PreparedStatement pst1 = conn.prepareStatement(query1);
					
					firstname.setText("");
					lastname.setText("");
					age.setText("");
					phone.setText("");
					id.setText("");
					textField.setText("");
					
					pst.execute();
					pst1.execute();
					JOptionPane.showMessageDialog(null,"Contact Deleted!");
					pst.close();
					pst1.execute();
					
					String query2 = "select idperson AS 'ID #', firstname AS 'FIRSTNAME', lastname 'LASTNAME', age AS 'AGE' FROM person";
					PreparedStatement pst2 = conn.prepareStatement(query2);
					ResultSet rs2 = pst2.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs2));	
					pst2.close();
					
					
					
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
					
				}
			}
			}
		});
		btnDelete.setBounds(12, 309, 218, 23);
		frmContactList.getContentPane().add(btnDelete);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					
					
					String query = "UPDATE person SET idperson = '"+id.getText()+"' , firstname= '"+firstname.getText()+"', lastname = '"+lastname.getText()+"', age= '"+age.getText()+"' WHERE idperson = '"+id.getText()+"'";
					PreparedStatement pst = conn.prepareStatement(query);
				
					
					pst.execute();
					JOptionPane.showMessageDialog(null,"Contact Updated!");
					pst.close();
					
					String query2 = "select idperson AS 'ID #', firstname AS 'FIRSTNAME', lastname 'LASTNAME', age AS 'AGE' FROM person";
					PreparedStatement pst2 = conn.prepareStatement(query2);
					ResultSet rs2 = pst2.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs2));	
					pst2.close();
					
					
					
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
					
				}
				
			}
		});
		btnUpdate.setBounds(10, 338, 218, 23);
		frmContactList.getContentPane().add(btnUpdate);
		
		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setBounds(10, 99, 61, 14);
		frmContactList.getContentPane().add(lblNewLabel);
		
		id = new JTextField();
		id.setBackground(SystemColor.inactiveCaption);
		id.setBounds(81, 95, 46, 20);
		frmContactList.getContentPane().add(id);
		id.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Firstname:");
		lblNewLabel_1.setBounds(10, 144, 81, 23);
		frmContactList.getContentPane().add(lblNewLabel_1);
		
		firstname = new JTextField();
		firstname.setBackground(SystemColor.inactiveCaption);
		firstname.setBounds(81, 144, 106, 20);
		frmContactList.getContentPane().add(firstname);
		firstname.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Lastname:");
		lblNewLabel_2.setBounds(10, 181, 61, 14);
		frmContactList.getContentPane().add(lblNewLabel_2);
		
		lastname = new JTextField();
		lastname.setBackground(SystemColor.inactiveCaption);
		lastname.setBounds(81, 177, 106, 20);
		frmContactList.getContentPane().add(lastname);
		lastname.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Age:");
		lblNewLabel_3.setBounds(10, 209, 46, 14);
		frmContactList.getContentPane().add(lblNewLabel_3);
		
		age = new JTextField();
		age.setBackground(SystemColor.inactiveCaption);
		age.setBounds(81, 205, 46, 20);
		frmContactList.getContentPane().add(age);
		age.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Phone :");
		lblNewLabel_4.setBounds(10, 237, 46, 14);
		frmContactList.getContentPane().add(lblNewLabel_4);
		
		phone = new JTextField();
		phone.setBackground(SystemColor.inactiveCaption);
		phone.setBounds(81, 233, 106, 20);
		frmContactList.getContentPane().add(phone);
		phone.setColumns(10);
	}
}
