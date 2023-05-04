import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;
import java.util.Arrays;

public class Apply extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    // static final String DB_URL="jdbc:mysql://localhost:3306/evm";
	static final String DB_URL="jdbc:mysql://localhost:3306/eventmanagement";

    static final String USER = "root";
    static final String PASS = "ssn@123";

    public void doPost(HttpServletRequest request, HttpServletResponse response)
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
            String txn_id=request.getParameter("tid");
            String address=request.getParameter("email");
            String upi_id=request.getParameter("upi-id");
            String upi_name=request.getParameter("name");
            String[] sub = request.getParameterValues("s_evt");
            int t_id=0;
            String status="PENDING";
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// Execute SQL query

			PreparedStatement st = conn.prepareStatement("insert into tickets(e_id,u_id,t_status,txn_id,address,upi_id,upi_name) values(?,?,?,?,?,?,?)");
            st.setString(1,eid);
            st.setString(2,email);
            st.setString(3,status);
            st.setString(4,txn_id);
            st.setString(5,address);
            st.setString(6,upi_id);
            st.setString(7,upi_name);
            st.executeUpdate();
            PreparedStatement st1 = conn.prepareStatement("select t_id from tickets where e_id = ? AND u_id = ? AND txn_id = ?");
            st1.setString(1,eid);
            st1.setString(2,email);
            st1.setString(3,txn_id);
            ResultSet rs = st1.executeQuery();
            while(rs.next()){
                t_id=rs.getInt("t_id");
            }
            for (int i=0;i<sub.length;i++){
                PreparedStatement st2 = conn.prepareStatement("insert into ticket_logs values(?,?,?)");
                st2.setInt(1,t_id);
                st2.setString(2,sub[i]);
                st2.setString(3,eid);
                st2.executeUpdate();
                st2.close();
            }
            st1.close();
			st.close();
			conn.close();
            response.sendRedirect("http://localhost:2525/EVM/viewTicket.html");
			// request.getRequestDispatcher("index.html").include(request, response);
        }
        catch (Exception e) {
            // out.println("Invalid username or password");
			e.printStackTrace();
        }
		out.close();
    }
}