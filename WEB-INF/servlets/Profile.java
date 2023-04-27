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
        out.println("<!DOCTYPE html><head><title>View</title></head>"+
                        "<body style=\"margin:auto; margin-top: 30px; padding: 10px; border: 1px solid black; width: 50%; text-align: center;\">"+
                            "<b>Profile,"+user+"</b><br><br>"+
                            "<b>Name    -   "+user+"</b><br><br>"+
                            "<b>Email   -   "+email+"</b><br><br>"+
                            "<b>Institute   -   "+institute+"</b><br><br>"+
                            "</body></html>"
                       );   
            
        }
        
    }