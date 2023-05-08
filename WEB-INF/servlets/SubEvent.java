import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class SubEvent extends HttpServlet {
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
			PreparedStatement stmt = conn.prepareStatement("select * from sub_events where e_id=?");
			stmt.setString(1,request.getParameter("id"));
			ResultSet rs = stmt.executeQuery();

            String name = "";
            String desc = "";
			int rounds;
			int prize;
			while(rs.next()){
				name = rs.getString("s_name");
                desc = rs.getString("s_description");
				rounds = rs.getInt("s_rounds");
				prize = rs.getInt("s_prize");
                out.write("<div class=\"row sub-event\"><div class=\"col\"><img src=\"./assets/1.jpeg\"></div>"+
                    "<div class=\"col\"><p style=\"color: #000; font-size: large;\"><strong>"+name+"</strong></p>"+
                    "<p style=\"color: #000;\">"+desc+"</p><div class=\"row\"><div class=\"col\"><p class=\"fw-bold\" style=\"color: #000;\">No. of rounds:  <span>"+rounds+"</span></p>"+
                    "</div><div class=\"col\"><p class=\"fw-bold\" style=\"color: #000;\">Prize:  <span class=\"fas fa-rupee-sign\"></span><span>"+prize+"</span></p></div></div></div></div>\n");
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