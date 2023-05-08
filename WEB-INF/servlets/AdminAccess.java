import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class AdminAccess extends HttpServlet {
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
			//Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName(JDBC_DRIVER);
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute SQL query 
			PreparedStatement stmt = conn.prepareStatement("select * from admins where u_id=?");
			stmt.setString(1,email);
			ResultSet rs = stmt.executeQuery();
            String e_id = "";
            String s_id = "";
			while(rs.next()){
				s_id = rs.getString("s_id");
                e_id = rs.getString("e_id");
                String name = "";
                String desc = "";
                int rounds=0;
                int prize=0;
                PreparedStatement stmt1 = conn.prepareStatement("select * from sub_events where e_id=? and s_id= ?");
                stmt1.setString(1,e_id);
                stmt1.setString(2,s_id);
                ResultSet rs1 = stmt1.executeQuery();
                while(rs1.next()){
                    name = rs1.getString("s_name");
                    desc = rs1.getString("s_description");
                    rounds = rs1.getInt("s_rounds");
                    prize = rs1.getInt("s_prize");
                     out.write("<div class=\"row sub-event\"><div class=\"col\"><img src=\"./assets/1.jpeg\"></div>"+
                    "<div class=\"col\"><p style=\"color: #000; font-size: large;\"><strong>"+name+"</strong></p>"+
                    "<p style=\"color: #000;\">"+desc+"</p><div class=\"row\"><div class=\"col\"><p class=\"fw-bold\" style=\"color: #000;\">No. of rounds:  <span>"+rounds+"</span></p>"+
                    "</div><div class=\"col\"><p class=\"fw-bold\" style=\"color: #000;\">Prize:  <span class=\"fas fa-rupee-sign\"></span><span>"+prize+"</span></p></div><div class=\"container text-center\"><button type=\"button\" class=\"btn btn-primary\" onclick=\"window.location.href = './participants.html"+
                    "?e_id="+e_id+"&s_id="+s_id+"';\">Participants</button></div></div></div></div>\n");
                } 
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