package Learndb;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;

public class new_employee extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField fname;
	private JTextField lname;
	private JTextField email;
	private JTextField salary;
	
	

	private app 
	app;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			new_employee dialog = new new_employee();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public new_employee() {
		setTitle("Add Employee");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			JLabel lblNewLabel = new JLabel("First Name:");
			contentPanel.add(lblNewLabel, "2, 2, right, default");
		}
		{
			fname = new JTextField();
			contentPanel.add(fname, "4, 2, fill, default");
			fname.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Last Name:");
			contentPanel.add(lblNewLabel_1, "2, 4, right, default");
		}
		{
			lname = new JTextField();
			contentPanel.add(lname, "4, 4, fill, default");
			lname.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Email:");
			contentPanel.add(lblNewLabel_2, "2, 6, right, default");
		}
		{
			email = new JTextField();
			contentPanel.add(email, "4, 6, fill, default");
			email.setColumns(10);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Salary:");
			contentPanel.add(lblNewLabel_3, "2, 8, right, default");
		}
		{
			salary = new JTextField();
			contentPanel.add(salary, "4, 8, fill, default");
			salary.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{SaveEmployee();
						}
						catch(Exception exe){
							exe.printStackTrace();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
						dispose();
						//System.exit(3);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
/*	protected BigDecimal convertStringToBigDecimal(String salaryStr) {

		BigDecimal result = null;

		try {
			double salaryDouble = Double.parseDouble(salaryStr);

			result = BigDecimal.valueOf(salaryDouble);
		} catch (Exception exc) {
			System.out.println("Invalid value. Defaulting to 0.0");
			result = BigDecimal.valueOf(0.0);
		}

		return result;
	} */
	private  void SaveEmployee() throws Exception {
		 EmployeesDAO employeeDAO = new EmployeesDAO();
		// TODO Auto-generated method stub
		// get the employee info from gui
		
		String firstName = fname.getText();
		String lastName = lname.getText();
		String emailstr = email.getText();
		String salaryStr = salary.getText();
		int salary = Integer.parseInt(salaryStr);

		Employee tempEmployee = new Employee(lastName,firstName, emailstr, salary);
		try {
			// save to the database
			employeeDAO.addEmployee(tempEmployee);
			// close dialog
			setVisible(false);
			dispose();

			// refresh gui list
			app.refreshEmployeesView();
			
			// show success message
			JOptionPane.showMessageDialog(app,
					"Employee added succesfully.",
					"Employee Added",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(
					app,
					"Error saving employee: "
							+ exc.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
		
	}

}
