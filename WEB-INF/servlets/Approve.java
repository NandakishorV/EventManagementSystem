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
            String t_id=request.getParameter("id");
            PreparedStatement stmt = conn.prepareStatement("UPDATE tickets SET t_status = 'APPROVED' WHERE t_id = ?;");
			stmt.setString(1,t_id);
			stmt.executeUpdate();
            String status="PENDING";
			String t_id = "";
			String e_id = "";
			String e_name = "";
            String e_amt="";
			String t_status="";
            String txn_id="";
            String user="";
            String address="";
			e_id = rs.getString("e_id");
            e_name=rs.getString("e_name");
            e_amt=rs.getString("e_amt");
            PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM tickets WHERE e_id = ? AND t_status='PENDING'");
            stmt1.setString(1,e_id);
            stmt1.executeUpdate();
            while(rs1.next()){
                address=rs1.getString("address");
                txn_id=rs1.getString("txn_id");
                t_status=rs1.getString("t_status");
                t_id=rs1.getString("t_id");
                user=rs1.getString("u_id");
                out.println("<tr><td>"+e_name+"</td><td>"+e_amt+"</td><td>"+user+"</td><td>"+txn_id+"</td><td>"+address+"</td><td>"+"<button onclick=\"window.location.href = 'http://localhost:2525/EVM/Approve?id="+t_id+"';\">Approve</button>"+"</td><td>"+"<button onclick=\"window.location.href = 'http://localhost:2525/EVM/Deny?id="+t_id+"';\">Deny</button>");
            }
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