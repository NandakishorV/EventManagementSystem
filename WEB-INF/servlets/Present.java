import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Present extends HttpServlet {
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
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute("user");
        String email = (String)session.getAttribute("email");
        String institute = (String)session.getAttribute("institute");  
           
		try {
			// Register JDBC driver
			//Class.forName("com.mysql.cj.jdbc.Driver");`
			Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String e_id=request.getParameter("e_id");
            String s_id=request.getParameter("s_id");
            String u_id=request.getParameter("u_id");
            String status="PRESENT";
            PreparedStatement stmt = conn.prepareStatement("UPDATE participants SET p_status = 'PRESENT' WHERE s_id=? AND e_id=? AND u_id=?;");
			stmt.setString(1,s_id);
            stmt.setString(2,e_id);
            stmt.setString(3,u_id);
            stmt.executeUpdate();
			stmt.close();
			conn.close();
            response.sendRedirect("./participants.html?e_id="+e_id+"&s_id="+s_id);
			// request.getRequestDispatcher("index.html").include(request, response);
        }
        catch (Exception e) {
            // out.println("Invalid username or password");
			e.printStackTrace();
        }
		out.close();
    }
}