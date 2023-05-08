import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class AddAdmin extends HttpServlet{
		// JDBC driver name and database URL
      static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    //   static final String DB_URL="jdbc:mysql://localhost:3306/evm";
      static final String DB_URL="jdbc:mysql://localhost:3306/eventmanagement";

      //  Database credentials
      static final String USER = "root";
      static final String PASS = "ssn@123";

	  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   
		// Set response content type
		response.setContentType("text/html");
		// PrintWriter out = response.getWriter();
           
		try {
			// Register JDBC driver
			//Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName(JDBC_DRIVER);
         
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute SQL query
            PreparedStatement st = conn.prepareStatement("insert into admins values(?,?,?)");
            st.setString(1, request.getParameter("u_id"));
            st.setString(2, request.getParameter("id"));
            st.setString(3, request.getParameter("s_id"));
            st.executeUpdate();
            response.sendRedirect("http://localhost:2525/EVM/add_admin.html?id="+request.getParameter("id")+"&s_id="+request.getParameter("s_id"));
            st.close();

            // Close all the connections
            st.close();
            conn.close();
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // out.close();
    }
} 