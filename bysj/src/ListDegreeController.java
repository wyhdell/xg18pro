import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/degree/list.ctl")
public class ListDegreeController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charest=utf-8");
        //为顶级调用者，只能用try catch 处理异常，不能抛出异常
        try{
            Collection<Degree> degrees = DegreeService.getInstance().findAll();//找到后台刚添加的数据
            String degree_str = JSON.toJSONString(degrees, SerializerFeature.DisableCircularReferenceDetect);
            System.out.println(degree_str);
            response.getWriter().println(degree_str);//返回前段
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
