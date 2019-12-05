
import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class DegreeDao {
    private static DegreeDao degreeDao=
            new DegreeDao();
    private DegreeDao(){}
    public static DegreeDao getInstance(){
        return degreeDao;
    }
    private static Collection<Degree> degrees;
    public Collection<Degree> findAll()throws SQLException {
        degrees = new TreeSet<Degree>();
        Connection connection = JdbcHelper.getConn();
        String x="select * from degree";
        PreparedStatement preparedStatement=connection.prepareStatement(x);
        //执行SQL查询语句并获得结果集对象
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while(resultSet.next()){
            Degree degree=new Degree(resultSet.getInt("id"),
                    resultSet.getString("description"),
                    resultSet.getString("no"),
                    resultSet.getString("remarks"));
            degrees.add(degree);
        }
        return degrees;
    }
    public boolean add(Degree degree) throws SQLException {
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句
        String addDegree_sql = "INSERT INTO degree(no,description,remarks) VALUES" +
                " (?,?,?)";
        //在该连接上创建预编译语句对象。preparestatement借口对象包装的是编译后的目标代码，
        // 当多次执行的时候，节约时间，并防止sql注入攻击。connection.prepareStatement()括号中有sql语句，
        // 而createStatement()中没有。其pstmt.executeUpdate()中写入sql语句。
        PreparedStatement pstmt = connection.prepareStatement(addDegree_sql);
        //为预编译参数（sql语句里的问号）赋值
        pstmt.setString(1, degree.getNo());
        pstmt.setString(2, degree.getDescription());
        pstmt.setString(3, degree.getRemarks());
        //没必要加入语句了。因为prepastatement()中已经含有sql语句了
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("增加了 " + affectedRowNum + " 条记录");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        //如果影响的行数大于1，则返回true，否则返回false
        return affectedRowNum > 0;
    }

    public boolean delete(Degree degree) throws SQLException{
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句
        String deleteDegree_sql="DELETE FROM degree WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement pstmt = connection.prepareStatement(deleteDegree_sql);
        //为预编译参数赋值
        pstmt.setInt(1,degree.getId());
        System.out.println("id"+degree.getId());
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("删除了 "+affectedRowNum+" 条记录");
        //关闭资源
        JdbcHelper.close(pstmt, connection);
        //如果影响的行数大于1，则返回true，否则返回false
        return degrees.remove(degree);
    }


    public Degree find(Integer id)throws SQLException{
        Degree degrees=null;
        Connection connection=JdbcHelper.getConn();
        String findString="select * from degree where id=?";
        PreparedStatement preparedStatement=connection.prepareStatement(findString);
        preparedStatement.setInt(1,id);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
            degrees=new Degree(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no")
                    ,resultSet.getString("remarks"));
        }JdbcHelper.close(preparedStatement, connection);
        return degrees;

    }

    public boolean update(Degree degree)throws SQLException{
        Connection connection = JdbcHelper.getConn();
        String updateDegree_sql="update degree set description=? where id=? ";
        PreparedStatement preparedStatement=connection.prepareStatement(updateDegree_sql);

        preparedStatement.setString(1, degree.getDescription());
        preparedStatement.setInt(2,degree.getId());
        int affectedRowNum = preparedStatement.executeUpdate();
        System.out.println("更新 " + affectedRowNum + " 条记录");
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRowNum > 0;
    }
    public static void main(String[] args)throws SQLException{
        Degree degree=DegreeDao.getInstance().find(11);
        degree.setDescription("7");
        System.out.println(degree);
        DegreeDao.getInstance().update(degree);
        Degree degree1=DegreeDao.getInstance().find(11);
        System.out.println(degree1);

    }
   // public boolean add(Degree degree){
        //return degrees.add(degree);


   // public boolean delete(Integer id){
       // Degree degree = this.find(id);
       // return this.delete(degree);
   // }

   // public boolean delete(Degree degree){
    //    return DegreeDao.degrees.remove(degree);
    //}
}

