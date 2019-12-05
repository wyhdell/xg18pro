
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/teacher/add.ctl")
public class AddTeacherController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String teacher_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Teacher对象
        Teacher teacherToAdd = JSON.parseObject(teacher_json, Teacher.class);
        //用大于4的随机数给teacherToAdd的id赋值
        teacherToAdd.setId(4 + (int)(1000*Math.random()));
        //创建JSON对象
        JSONObject resp = new JSONObject();
        //加入数据信息
        resp.put("MSG", "OK");
        try{
            TeacherService.getInstance().add(teacherToAdd);
            //抛出异常
        }catch (SQLException e){
            e.printStackTrace();
        }
        //响应
        response.getWriter().println(resp);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);

    }
}
