import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.TreeSet;


public final class DepartmentDao {
	private static Collection<Department> departments;
	static {

	}

	private static DepartmentDao departmentDao=new DepartmentDao();
	private DepartmentDao(){}

	public static DepartmentDao getInstance(){
		return departmentDao;
	}


	public Collection<Department> findAll()throws SQLException {
		departments=new TreeSet<Department>();//????????????
		Connection connection = JdbcHelper.getConn();
		String x="select * from department";
		PreparedStatement preparedStatement=connection.prepareStatement(x);
		//执行SQL查询语句并获得结果集对象
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			Department department=new Department(resultSet.getInt("id"),
					resultSet.getString("description"),
					resultSet.getString("no"),
					resultSet.getString("remarks"),
					SchoolDao.getInstance().find(resultSet.getInt("id")));
			departments.add(department);
		}
		return DepartmentDao.departments;
	}
	public Department find(Integer id)throws SQLException{
		Department department=null;
		Connection connection=JdbcHelper.getConn();
		String findString="select * from department where id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(findString);
		preparedStatement.setInt(1,id);
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next()){
			department=new Department(
			        resultSet.getInt("id"),
			        resultSet.getString("description")
					,resultSet.getString("no"), resultSet.getString("remarks")
					,SchoolDao.getInstance().find(resultSet.getInt("school_id")));
		}JdbcHelper.close(preparedStatement, connection);
			return department;
	}

	public boolean update(Department department)throws SQLException{
        Connection connection = JdbcHelper.getConn();
        String updateDegree_sql="update department set description=? ,no=?,remarks=? where id=?";
        PreparedStatement preparedStatement=connection.prepareStatement(updateDegree_sql);
        preparedStatement.setString(1, department.getDescription());
        preparedStatement.setString(2, department.getNo());
        preparedStatement.setString(3,department.getRemarks());
        preparedStatement.setInt(4,department.getId());
        int affectedRowNum = preparedStatement.executeUpdate();
        System.out.println("更新 " + affectedRowNum + " 条记录");
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRowNum > 0;
	}
	public boolean add(Department department) throws SQLException {
		//获得连接对象
		Connection connection = JdbcHelper.getConn();
		String addDegree_sql =
				"INSERT INTO department(no,description,remarks,school_id) VALUES" +
						" (?,?,?,?)";
		//在该链接上创建预编译语句对象
		PreparedStatement pstmt = connection.prepareStatement(addDegree_sql);
		//为预编译参数赋值
		pstmt.setString(1,department.getNo());
		pstmt.setString(2,department.getDescription());
		pstmt.setString(3,department.getRemarks());
		pstmt.setInt(4,department.getSchool().getId());
		int affectedRowNm = pstmt.executeUpdate();
		//执行预编译对象的executeUpdate方法,获取添加的记录行数
		System.out.println("添加了" + pstmt.executeUpdate() + "条记录...");
		//关闭对象
		JdbcHelper.close(pstmt,connection);
		return affectedRowNm >0;
	}


	public static void main(String[] args)throws SQLException {
        Department department =DepartmentDao.getInstance().find(9);
        System.out.println(department);
        department.setDescription("xx");

        DepartmentDao.getInstance().update(department);
        Department department1=DepartmentDao.getInstance().find(9);
        System.out.println(department1);

        System.out.println("更新成功");
	}
//	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		School managementSchool = SchoolDao.getInstance().find(1);
//		Department department = new Department(2, "信息管理", "0202", "", managementSchool);
//		DepartmentDao.getInstance().update(department);
//		System.out.println("have finished");
//	}
public boolean delete(Integer id) throws SQLException{
	//获取数据库连接对象
	Connection connection = JdbcHelper.getConn();
	//创建sql语句
	String deleteDepartment_sql = "DELETE FROM department" + " WHERE id=?";
	//在该连接上创建预编译语句对象
	PreparedStatement pstmt = connection.prepareStatement(deleteDepartment_sql);
	//为预编译语句赋值
	pstmt.setInt(1,id);
	int affectedRowNum = pstmt.executeUpdate();
	System.out.println("删除"+affectedRowNum+"条记录");
	//关闭pstmt, connection对象（关闭资源）
	JdbcHelper.close(pstmt,connection);
	return affectedRowNum > 0;
}
	public boolean delete(Department department){
		return departments.remove(department);
	}
}

