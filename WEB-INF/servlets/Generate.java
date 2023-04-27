import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class Generate extends HttpServlet{
		// JDBC driver name and database URL
      static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    //   static final String DB_URL="jdbc:mysql://localhost:3306/evm";
      static final String DB_URL="jdbc:mysql://localhost:3306/eventmanagement";

      //  Database credentials
      static final String USER = "root";
    //   static final String PASS = "ssn@123";
      static final String PASS = "";

	  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   
		// Set response content type
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
           
		try {
			// Register JDBC driver
			//Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName(JDBC_DRIVER);
         
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			// Execute SQL query
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM events where e_id=?");
			stmt.setString(1, request.getParameter("id"));
			ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                out.write("<h1>"+rs.getString("e_name")+"</h1>");
                out.write("\n");
                out.write("<h2>"+rs.getString("e_name")+"</h2>");
                out.write("\n");
                out.write("<span>"+rs.getString("e_location")+"</span>");

                out.write("\n");
                out.write("<p>"+ rs.getDate("e_start").toString()+" <span>TO</span> "+rs.getDate("e_end").toString()+"</p>");
                out.write("\n");
                out.write("<span class=\"june-29\">"+rs.getString("e_start")+"</span>");
            }

            // Close all the connections
            rs.close();
            stmt.close();
            conn.close();
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // out.close();
    }
} 