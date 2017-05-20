package Learndb;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.math.*;
import Learndb.Employee;
public class EmployeesDAO {
	private static Connection connection;
	
	public EmployeesDAO ()throws Exception {
		
		//get db properties
		/*String dburl, user, password;
		dburl ="jdbc:mysql://localhost:3306/Employees";
		user ="root";
		password ="";
*/
		Properties properties = new Properties(); 
		properties.load(new FileInputStream("C:\\jctut\\Tailor_App\\sql\\root.properties"));
		
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		String dburl = properties.getProperty("dburl");
		
		//connect to database
		connection = DriverManager.getConnection(dburl,user,password);
		System.out.println("Database Connection Successsful to :" + dburl);
	}
	
	public  void addEmployee(Employee theEmployee) throws Exception {
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = connection.prepareStatement("insert into employees"
					+ " (first_name, last_name, email, salary)"
					+ " values (?, ?, ?, ?)");
			
			// set params
			myStmt.setString(1, theEmployee.getFirstName());
			myStmt.setString(2, theEmployee.getLastName());
			myStmt.setString(3, theEmployee.getEmail());
			myStmt.setInt(4, theEmployee.getSalary());
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}
		
	}
	
	
	public static  List<Employee> getAllEmployees() throws Exception {
		List<Employee> list = new ArrayList<>();
		
		Statement statement = null;
		ResultSet result = null;
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("select * from employees");
			
			while (result.next()) {
				Employee tempEmployee = convertRowToEmployee(result);
				list.add(tempEmployee);
			}

			return list;		
		}
		finally {
			close(statement, result);
		}
	}
		
		public static  List<Employee> searchEmployees(String lastName) throws Exception {
			List<Employee> list = new ArrayList<>();

			PreparedStatement myStmt = null;
			ResultSet myRs = null;

			try {
				lastName += "%";
				myStmt = connection.prepareStatement("select * from employees where last_name like ?");
				
				myStmt.setString(1, lastName);
				
				myRs = myStmt.executeQuery();
				
				while (myRs.next()) {
					Employee tempEmployee = convertRowToEmployee(myRs);
					list.add(tempEmployee);
				}
				
				return list;
			}
			finally {
				close(myStmt, myRs);
			}
		}
		
		private static Employee convertRowToEmployee(ResultSet myRs) throws SQLException {
			
			int id = myRs.getInt("id");
			String lastName = myRs.getString("last_name");
			String firstName = myRs.getString("first_name");
			String email = myRs.getString("email");
			int salary = myRs.getInt("salary");
			
			Employee tempEmployee = new Employee(id, lastName, firstName, email, salary);
			
			return tempEmployee;
		}

		
		private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
				throws SQLException {

			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				
			}
			
			if (myConn != null) {
				myConn.close();
			}
		}

		private static void close(Statement myStmt, ResultSet myRs) throws SQLException {
			close(null, myStmt, myRs);		
		}
		
	
		
		public static void main(String[] args) throws Exception {
			
			EmployeesDAO employeesdao = new EmployeesDAO();
			System.out.println(EmployeesDAO.searchEmployees("thom"));

			System.out.println(EmployeesDAO.getAllEmployees());
		}
	}
		
		
	

	


