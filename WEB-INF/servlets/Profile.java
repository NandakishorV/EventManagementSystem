import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class Profile extends HttpServlet{

	  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   
		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter(); 
		HttpSession session = request.getSession();
        String user = (String)session.getAttribute("user");
        String email = (String)session.getAttribute("email");
        String institute = (String)session.getAttribute("institute");    
        response.sendRedirect("http://localhost:2525/EVM/profile.html?name="+user+"&email="+email+"&institute="+institute);           
        }
        
    }