

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password= request.getParameter("password");
		
		//check if userId and password are valid
		boolean result = false;
		try {
			result = authenticate(username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result) {
			
			request.setAttribute("username",username);
			// where we dispatch the control to
			RequestDispatcher dispatcher = request.getRequestDispatcher("success.jsp") ; 
			
			dispatcher.forward(request, response); 
			// success.jsp will get the request and response now
			// since we dispatch the control and not redirect to view, the scope is request scope.
		}else {
			response.sendRedirect("login.jsp");}

	}

	private User getUserByUsername(String username) throws SQLException {
		User user = null;
		//get DB connection
		System.out.println("connecting to DB");
		Connection connection = DatabaseConnectionUtil.getDatabaseConnection();
		
		//we asume that username is unique and only one user matched with a particular username
		// get user by username
		System.out.println("connected to DB");
		//load query
		PreparedStatement ps = connection.prepareStatement("select * from users where users.username = ?;");
		ps.setString(1, username);
		//execute query
		ResultSet result = ps.executeQuery();
		System.out.println("executed query to get user from database ");
		if(result.next()) {
		int id = result.getInt("id");
		String password = result.getString("password");
		String gender = result.getString("gender");
		String hobbies = result.getString("hobbies");
		String city = result.getString("city");
		connection.close();
		user= new User(id,username,password,gender,hobbies,city);
		} else{
			System.out.println("user not found");
		}
		return user;
		
	}

	private boolean authenticate(String username, String password) throws SQLException {
		//retrieve username + password from the database
		// compare with the username and password provided
		User userDB = getUserByUsername(username);
		String usernameDB = userDB.getUserName();
		String passwordDB = userDB.getPassword();
		
		if(usernameDB.equalsIgnoreCase(username) && passwordDB.equalsIgnoreCase(password)) {
			return true;
		}
		return false;
		
	}

}
