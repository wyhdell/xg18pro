import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/degree1/delete.ctl")
public class DeleteDegreeControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        //删除学位
        try{
            DegreeService.getInstance().delete(id);
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("delete doGet");
        //将请求重定向查询学院0l，以刷新学位信息
        response.sendRedirect("/bysj_war_exploded/html/basic/school.html");
    }
}

