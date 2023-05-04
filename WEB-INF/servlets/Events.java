import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Events extends HttpServlet {
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

			// Execute SQL query 
			PreparedStatement stmt = conn.prepareStatement("SELECT e_name, description FROM events");
			ResultSet rs = stmt.executeQuery();

            String name = "";
            String desc = "";
			while(rs.next()){
				name = rs.getString("e_name");
                desc = rs.getString("description");
                out.write("<div class=\"card\" style=\"max-width: 37em;\"> <img class=\"card-img-top\" src=\"../assets/51775-gradient-pink-blue-simple_background-simple-abstract.jpg\" alt=\"Card image cap\"><div class=\"card-body\">"+
                "<h5 class=\"card-title\">"+name+"</h5>"+
                "<p class=\"card-text\">"+desc+"</p>"+
                "<a class=\"card-text\" href=\"#\">Know more....</a></div></div>\n");
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