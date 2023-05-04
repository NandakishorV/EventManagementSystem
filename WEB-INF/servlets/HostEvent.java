import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class HostEvent extends HttpServlet {
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
           
		try {
			// Register JDBC driver
			//Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName(JDBC_DRIVER);
         
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute SQL query 
			PreparedStatement stmt = conn.prepareStatement("select * from events where h_id=? ORDER BY e_id");
            HttpSession session = request.getSession();
            String email = (String)session.getAttribute("email");
			stmt.setString(1,email);
			ResultSet rs = stmt.executeQuery();

            String name = "";
            String desc = "";
			int prize;
			int id;
			while(rs.next()){
				id = rs.getInt("e_id");
				name = rs.getString("e_name");
                desc = rs.getString("description");
				prize = rs.getInt("e_amt");
                out.write("<div class=\"row sub-event\"><div class=\"col\"><img src=\"./assets/1.jpeg\"></div>"+
                    "<div class=\"col\"><p style=\"color: #000; font-size: large;\"><strong>"+name+"</strong></p>"+
                    "<p style=\"color: #000;\">"+desc+"</p><div class=\"row\"><div class=\"cent\"><p class=\"fw-bold\" style=\"color: #000;\">Ticket Cost:  <span class=\"fas fa-rupee-sign\"></span><span>"+prize+"</span>"+
                    "</p><button class=\"fw-bold\" style=\"background-color:dodgerblue;color: #000;border-radius: 8px;border-color: dodgerblue;\" onclick=\"location.href='add_sub.html?id="+id+"';\">Add Sub Event</button></div></div></div></div>\n");
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