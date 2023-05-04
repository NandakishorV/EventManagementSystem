import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class History extends HttpServlet {
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
            HttpSession session = request.getSession();
            String user = (String)session.getAttribute("user");
            String email = (String)session.getAttribute("email");
            String institute = (String)session.getAttribute("institute"); 
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute SQL query 
			PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT(e_id) FROM participants WHERE u_id = ?");
            stmt.setString(1,email);
			ResultSet rs = stmt.executeQuery();
            int e_id;
			while(rs.next()){
				e_id = rs.getInt("e_id");
                PreparedStatement st = conn.prepareStatement("SELECT * FROM events WHERE e_id = ?");
                st.setInt(1,e_id);
                ResultSet rs1 = st.executeQuery();
                String e_name="";
                String e_start="";
                String e_end="";
                while(rs1.next()){
				    e_name = rs1.getString("e_name");
                    e_start = rs1.getString("e_start");
                    e_end = rs1.getString("e_end");
                    out.write("<tr><td>"+e_name+"</td><td>"+e_start+" - "+e_end+"</td><td>");
                    PreparedStatement st1 = conn.prepareStatement("SELECT * FROM participants WHERE e_id = ? AND u_id = ?");
                    st1.setInt(1,e_id);
                    st1.setString(2,email);
                    ResultSet rs2 = st1.executeQuery();
                    int s_id;
                    while(rs2.next()){
                        s_id = rs2.getInt("s_id");
                        PreparedStatement st2 = conn.prepareStatement("SELECT * FROM sub_events WHERE s_id = ? AND e_id = ?");
                        st2.setInt(1,s_id);
                        st2.setInt(2,e_id);
                        ResultSet rs3 = st2.executeQuery();
                        String s_name="";
                        while(rs3.next()){
                            s_name = rs3.getString("s_name");
                            out.write(s_name+", ");
                        }
                    }
                    out.write("</td></tr>");
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