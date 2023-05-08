import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Deny extends HttpServlet {
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
            String t_id=request.getParameter("id");
            PreparedStatement stmt = conn.prepareStatement("UPDATE tickets SET t_status = 'REJECTED' WHERE t_id = ?;");
			stmt.setString(1,t_id);
			stmt.executeUpdate();
            String u_id="";
            String e_id="";
            String s_id="";
            String t1_id="";
            PreparedStatement stmt1 = conn.prepareStatement("SELECT t.t_id,l.s_id,l.e_id,t.u_id FROM tickets AS t JOIN ticket_logs AS l ON t.t_id=l.t_id WHERE t.t_status='REJECTED';");
            ResultSet rs1=stmt1.executeQuery();
            while(rs1.next()){
                e_id = rs1.getString("l.e_id");
                u_id=rs1.getString("t.u_id");
                s_id=rs1.getString("l.s_id");
                t1_id=rs1.getString("t.t_id");
                PreparedStatement stmt3 = conn.prepareStatement("DELETE FROM ticket_logs WHERE t_id = ?");
                stmt3.setString(1,t1_id);
                stmt3.executeUpdate();
            }
			stmt.close();
			conn.close();
            response.sendRedirect("./tickets_host.html");
			// request.getRequestDispatcher("index.html").include(request, response);
        }
        catch (Exception e) {
            // out.println("Invalid username or password");
			e.printStackTrace();
        }
		out.close();
    }
}