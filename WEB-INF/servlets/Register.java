import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class Register extends HttpServlet{
		// JDBC driver name and database URL
      static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    //   static final String DB_URL="jdbc:mysql://localhost:3306/evm";
      static final String DB_URL="jdbc:mysql://localhost:3306/eventmanagement";

      //  Database credentials
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
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
			stmt.setString(1, request.getParameter("uname"));
			ResultSet rs = stmt.executeQuery();

			boolean isEmpty = true;
            while(rs.next()){
                isEmpty = false;
                break;
            }

            if(!isEmpty){
                out.write("Email already registered");
            }
            else{
                PreparedStatement st = conn.prepareStatement("insert into users values(?,?,?,?)");
                st.setString(1, request.getParameter("uname"));
                st.setString(2, request.getParameter("name"));
                st.setString(3, request.getParameter("institute"));
                st.setString(4, request.getParameter("pass"));
                st.executeUpdate();
                out.write("Registered Successfully");
                st.close();

            }

            // Close all the connections
            stmt.close();
            conn.close();
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // out.close();
    }
} 