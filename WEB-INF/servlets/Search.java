import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Search extends HttpServlet{
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    // static final String DB_URL="jdbc:mysql://localhost:3306/evm";
	static final String DB_URL="jdbc:mysql://localhost:3306/eventmanagement";

    static final String USER = "root";
    static final String PASS = "ssn@123";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try{
            Class.forName(JDBC_DRIVER);
         
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			// Execute SQL query 
			PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE access = 0 AND name LIKE ? AND email NOT IN (SELECT u_id FROM admins WHERE e_id = ? AND s_id = ?) ORDER BY name LIMIT 0,5");
			st.setString(1, request.getParameter("key")+'%');
            st.setString(2, request.getParameter("id"));
            st.setString(3, request.getParameter("s_id"));
			ResultSet rs = st.executeQuery();

            out.write("<thead><tr><th>#</th><th>Name</th><th>E-mail</th><th>Institute</th><th>Actions</th></tr></thead><tbody>");
            int i = 1;

            while(rs.next()){
                out.write("<tr><td>"+i+"</td><td>"+rs.getString("name")+"</td><td>"+rs.getString("email")+"</td><td>"+rs.getString("institute")+
                "</td><td><a href=\"http:\\\\localhost:2525\\EVM\\AddAdmin?id="+request.getParameter("id")+"&s_id="+request.getParameter("s_id")+"&u_id="+rs.getString("email")
                +"\" class=\"view\" title=\"add\" data-toggle=\"tooltip\" onclick=\"return confirm('do you want to add "+rs.getString("name")+" as an admin?')\"><i class=\"material-icons\">person_add</i></a></td></tr>");
                i++;
            }
            out.write("</tbody>");

            rs.close();
            st.close();
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        out.close();
    }
}