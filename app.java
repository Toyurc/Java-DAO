package Learndb;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import Learndb.EmployeesDAO;
import Learndb.Employee;

public class app extends JFrame {

	private JPanel contentPane;
	private JTextField nametextField;
	private JTable table;
	private EmployeesDAO EmployeeDAO ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					app frame = new app();
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
	public app() {
		
		try {
			 EmployeeDAO = new EmployeesDAO();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
		}
		
		setTitle("Employee Search App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 532, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Search Employee");
		lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(lblNewLabel);
		
		nametextField = new JTextField();
		panel.add(nametextField);
		nametextField.setColumns(10);
		
		JButton btnSearch = new JButton("Search!");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String lastName = nametextField.getText();

					List<Employee> employees = null;

					if (lastName != null && lastName.trim().length() > 0) {
						employees = EmployeesDAO.searchEmployees(lastName);
					} else {
						employees = EmployeesDAO.getAllEmployees();
					}
					
					// create the model and update the "table"
					tableModel model = new tableModel(employees);
					
					table.setModel(model);
					
					/*
					for (Employee temp : employees) {
						System.out.println(temp);
					}*/
					
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(app.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
				}
				
			}
		});
		panel.add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton addbttn = new JButton("Add New Employee");
		addbttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new_employee dialog = new new_employee( );

				// show dialog
				dialog.setVisible(true);
			}
		});
		panel_1.add(addbttn);
	}
	public void refreshEmployeesView() {

		try {
			List<Employee> employees = EmployeesDAO.getAllEmployees();

			// create the model and update the "table"
			tableModel model = new tableModel(employees);

			table.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
