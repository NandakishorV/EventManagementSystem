import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class ViewTicket extends HttpServlet {
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
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tickets WHERE u_id = ? ORDER BY t_id DESC");
			stmt.setString(1,email);
			ResultSet rs = stmt.executeQuery();
            String status="APPROVED";
			String t_id = "";
			String e_id = "";
			String e_name = "";
			String t_status="";
            String txn_id="";
			while(rs.next()){
				t_id = rs.getString("t_id");
				e_id = rs.getString("e_id");
				t_status = rs.getString("t_status");
                PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM events WHERE e_id = ?");
                stmt1.setString(1,e_id);
                ResultSet rs1 = stmt1.executeQuery();
                while(rs1.next()){
                    e_name=rs1.getString("e_name");
                }
                out.println("<tr><td>"+t_id+"</td><td>"+e_name+"</td><td>");
                if(t_status.equals(status)){
                    out.println("<button onclick=\"window.location.href = 'http://localhost:2525/EVM/ticket.html?id="+e_id+"';\">View Ticket</button>"+"</td></tr>");
                }
                else{
                    out.println("<button disabled onclick=\"window.location.href = 'http://localhost:2525/EVM/ticket.html';\">View Ticket</button>"+"</td></tr>");                }
			}
			stmt.close();
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