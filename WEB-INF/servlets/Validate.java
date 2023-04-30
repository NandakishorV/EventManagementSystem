import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Validate extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    // static final String DB_URL="jdbc:mysql://localhost:3306/evm";
	static final String DB_URL="jdbc:mysql://localhost:3306/eventmanagement";

    static final String USER = "root";
    static final String PASS = "ssn@123";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   
		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
           
		try {
			// Register JDBC driver
			//Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName(JDBC_DRIVER);
         
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String email=request.getParameter("uname");
			// Execute SQL query 
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
			stmt.setString(1,email);
			ResultSet rs = stmt.executeQuery();

			boolean isEmpty = true;
			String password = "";
			String name = "";
			String institute = "";
			String pwd = request.getParameter("pass");

			while(rs.next()){
				isEmpty = false;
				password = rs.getString("password");
				name = rs.getString("name");
				institute = rs.getString("institute");
			}
			if(isEmpty){
				out.write("User does not exist");
			}
			else if(pwd.equals(password)){
				out.write("valid");	
				HttpSession session = request.getSession();
				session.setAttribute("user", name);
				session.setAttribute("email", email);
				session.setAttribute("institute", institute);
			}
			else{
				out.write("Invalid username or password");
			}
			stmt.close();
			rs.close();
			conn.close();

			// request.getRequestDispatcher("index.html").include(request, response);
        }
        catch (Exception e) {
            // out.println("Invalid username or password");
			e.printStackTrace();
        }
		out.close();
    }
}