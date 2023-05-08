import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class AddSub extends HttpServlet{
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
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
        String email = (String)session.getAttribute("email");
           
		try {
			// Register JDBC driver
			//Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName(JDBC_DRIVER);
         
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            PreparedStatement stmt = conn.prepareStatement("select s_id FROM sub_events WHERE e_id=? ORDER BY s_id DESC LIMIT 1");
            stmt.setString(1, request.getParameter("id"));
            ResultSet rs = stmt.executeQuery();
            int sid = 1;
            while(rs.next()){
                sid += rs.getInt("s_id");
            }
            System.out.println(sid);
			// Execute SQL query
            PreparedStatement st = conn.prepareStatement("insert into sub_events(`s_id`, `e_id`, `s_name`, `s_description`, `s_location`, `s_type`, `s_rounds`, `s_participants`, `s_date`, `s_start`, `s_end`, `s_prize`, `s_status`) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            st.setInt(1, sid);
            st.setString(2, request.getParameter("id"));
            st.setString(3, request.getParameter("eName"));
            st.setString(4, request.getParameter("desc"));
            st.setString(5, request.getParameter("loc"));
            st.setString(6, request.getParameter("type"));
            st.setString(7, request.getParameter("rounds"));
            st.setString(8, request.getParameter("max"));
            st.setString(9, request.getParameter("stDate"));
            st.setString(10, request.getParameter("stTime"));
            st.setString(11, request.getParameter("enTime"));
            st.setString(12, request.getParameter("amt"));
            st.setString(13, "UPCOMING");
            st.executeUpdate();
            response.sendRedirect("http://localhost:2525/EVM/sub_host.html");
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