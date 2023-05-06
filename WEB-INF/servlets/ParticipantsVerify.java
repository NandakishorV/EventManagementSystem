import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class ParticipantsVerify extends HttpServlet {
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
            String status="ABSENT";
			String t_id = "";
			String p_email = "";
			String p_name = "";
            String p_institute="";
            String p_status="";
            PreparedStatement stmt = conn.prepareStatement("SELECT u.email,t.t_id,u.name,u.institute,p.p_status FROM users AS u INNER JOIN participants AS p ON u.email=p.u_id INNER JOIN tickets AS t ON p.u_id=t.u_id WHERE t.t_status='APPROVED' AND p.s_id=? AND p.e_id=?;");
			stmt.setString(1,s_id);
            stmt.setString(2,e_id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
                p_status=rs.getString("p.p_status");
				p_email = rs.getString("u.email");
                p_name=rs.getString("u.name");
                p_institute=rs.getString("u.institute");
                t_id=rs.getString("t.t_id");
                out.write("<tr><td>"+t_id+"</td><td>"+p_name+"</td><td>"+p_institute+"</td><td>"+p_email+"</td>");
                if(p_status.equals(status)){
                    out.write("<td>"+"<button onclick=\"window.location.href = './Present?u_id="+p_email+"&s_id="+s_id+"&e_id="+e_id+"';\">Present</button>"+"</td></tr>");
                }
                else{
                    out.write("<td style=\"color:green\">Present</td></tr>");
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