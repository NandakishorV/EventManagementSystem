import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Apply extends HttpServlet {
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
            String eid = request.getParameter("e_id");
            String status="PENDING";
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// Execute SQL query
            System.out.println(eid);
			PreparedStatement st = conn.prepareStatement("insert into tickets(e_id,u_id,t_status) values(?,?,?)");
                st.setString(1,eid);
                st.setString(2,email);
                st.setString(3,status);
                st.executeUpdate();
                out.println("<!DOCTYPE html><head><title>View</title></head><style></style>"+
                        "<body style=\"margin:auto; margin-top: 30px; padding: 10px; border: 1px solid black; width: 50%; text-align: center;\">"+
                            "<b>Registered Successfully</b><br><br>"+
                            "<b>Wait for the host to approve your request</b><br><br>"+
                            "<button onclick=\"window.location.href = 'http://localhost:2525/EVM/ViewTicket';\">Tickets Page</button>"+
                            "</body></html>"
                       );   
			st.close();
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