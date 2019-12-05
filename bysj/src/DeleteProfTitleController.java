import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/proftitle1/delete.ctl")
public class DeleteProfTitleController extends HttpServlet {
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        try{
            ProfTitleDao.getInstance().delete(id);
            //抛出异常
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
