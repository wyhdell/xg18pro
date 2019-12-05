import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/department1/add.ctl")
public class AddDepartmentController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String degree_json = JSONUtil.getJSON(request);

        //将JSON字串解析为School对象
        Department departmentToAdd = JSON.parseObject(degree_json,Department.class);
        //用大于4的随机数给degreeToAdd的id赋值
        departmentToAdd.setId(4 + (int)(1000*Math.random()));
        //增加Degree对象
        try{
            DepartmentService.getInstance().add(departmentToAdd);//将数据添加到后台
        }catch(SQLException e){
            e.printStackTrace();

        }

        //创建JSON对象
        JSONObject resp = new JSONObject();
        //加入数据信息
        resp.put("MSG", "OK");
        //响应
        response.getWriter().println(resp);

    }

}
