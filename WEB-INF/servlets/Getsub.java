import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Getsub extends HttpServlet {
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
            PreparedStatement st = conn.prepareStatement("select * from events where e_id=?");
			st.setString(1,request.getParameter("id"));
			ResultSet res = st.executeQuery();
            while(res.next()){
                out.write(res.getString("e_name")+'\n'+res.getString("e_amt")+'\n'+res.getString("description"));
            }

			PreparedStatement stmt = conn.prepareStatement("select * from sub_events where e_id=?");
			stmt.setString(1,request.getParameter("id"));
			ResultSet rs = stmt.executeQuery();

            String name = "";
			int id;
			while(rs.next()){
				name = rs.getString("s_name");
				id = rs.getInt("s_id");
                out.write('\n'+name+';'+id);
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