import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SchoolTest {
    public static void main(String[] args)throws SQLException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try{
            connection=JdbcHelper.getConn();
            //关闭自动提交
            connection.setAutoCommit(false);
            preparedStatement=connection.prepareStatement("insert into school(description,no) values (?,?)");
            preparedStatement.setString(1,"算理");
            preparedStatement.setString(2,"0222");
            preparedStatement.executeUpdate();
            preparedStatement=connection.prepareStatement("insert into school(description,no) values (?,?)");
            preparedStatement.setString(1,"算55");
            preparedStatement.setString(2,"022");
            preparedStatement.executeUpdate();//若抛出异常，执行roallback回滚，
            // 使第一条语句insert对数据库所做的更改失败，回归到最初状态
            //数据真正提交
            connection.commit();

        }
        catch(SQLException e){
            System.out.println(e.getErrorCode()+e.getMessage());
            try{
                if(connection==null) {
                    connection.rollback();
                }
            }catch (SQLException e1){
                e1.printStackTrace();
                //在命令行打印异常信息在程序中出错的位置及原因
            }
            finally {
                try {
                    //恢复自动提交
                    connection.setAutoCommit(true);
                }catch (SQLException e2){
                    e2.printStackTrace();
                }
                //关闭资源
                JdbcHelper.close(preparedStatement,connection);
            }
        }
    }
}
