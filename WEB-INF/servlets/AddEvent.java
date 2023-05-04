import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class AddEvent extends HttpServlet{
		// JDBC driver name and database URL
      static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    //   static final String DB_URL="jdbc:mysql://localhost:3306/evm";
      static final String DB_URL="jdbc:mysql://localhost:3306/eventmanagement";

      //  Database credentials
      static final String USER = "root";
      static final String PASS = "ssn@123";

	  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   
		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
        String email = (String)session.getAttribute("email");
           
		try {
			// Register JDBC driver
			//Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName(JDBC_DRIVER);
         
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			// Execute SQL query
            PreparedStatement st = conn.prepareStatement("insert into events(`e_name`, `e_location`, `e_start`, `e_end`, `e_amt`, `e_status`, `max_events`, `description`, `h_id`) values(?,?,?,?,?,?,?,?,?)");
            st.setString(1, request.getParameter("eName"));
            st.setString(2, request.getParameter("loc"));
            st.setString(3, request.getParameter("stDate"));
            st.setString(4, request.getParameter("enDate"));
            st.setString(5, request.getParameter("amt"));
            st.setString(6, "UPCOMING");
            st.setString(7, request.getParameter("max"));
            st.setString(8, request.getParameter("desc"));
            st.setString(9, email);
            st.executeUpdate();
            response.sendRedirect("http://localhost:2525/EVM/events_host.html");
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