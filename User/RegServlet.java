import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class RegServlet extends HttpServlet{
		// JDBC driver name and database URL
      static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
      static final String DB_URL="jdbc:mysql://localhost:3306/eventmanagement";

      //  Database credentials
      static final String USER = "root";
      static final String PASS = "";
      
      
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
			PreparedStatement st = conn.prepareStatement("delete from event where e_id = ?");
			st.setInt(1, Integer.valueOf(request.getParameter("id")));
			st.executeUpdate();
  
            // Close all the connections
            st.close();
            conn.close();
  
            // Get a writer pointer 
            // to display the successful result
            
        out.println("<!DOCTYPE html><head><title>View</title></head>"+
                        "<body style=\"margin:auto; margin-top: 30px; padding: 10px; border: 1px solid black; width: 50%; text-align: center;\">"+
                            "<b>Click to view the Database contents</b><br><br>"+
                            "</body></html>"
                       );   
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
} 