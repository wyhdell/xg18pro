import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout.ctl")
public class LogOutController extends HttpServlet {
    @Override//服务器为每一个请求创建一个session来识别不同用户，httpsession接口为客户服务器连接，以方便次浏览器给服务器发送多个请求中都被识别为一次对话，
    //创建
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession();
        if(session!=null){
        session.invalidate();}
    }
}
