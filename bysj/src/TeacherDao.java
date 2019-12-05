
import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

public final class TeacherDao {
	private static TeacherDao teacherDao=new TeacherDao();
	private TeacherDao(){}
	public static TeacherDao getInstance(){
		return teacherDao;
	}
	private static Collection<Teacher> teachers=new TreeSet<Teacher>();;
	public Collection<Teacher> findAll() throws SQLException {
		teachers = new HashSet<Teacher>();
		//获取数据库连接对象
		Connection connection = JdbcHelper.getConn();
		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT * FROM teacher");
		//若结果集仍然有下一条记录，则执行循环体
		while (resultSet.next()){
 			teachers.add(new Teacher(resultSet.getString("name"),
					ProfTitleService.getInstance().find(resultSet.getInt("title_id")),
					DegreeService.getInstance().find(resultSet.getInt("degree_id")),
					DepartmentService.getInstance().find(resultSet.getInt("department_id"))
			));
		}
		//使用JdbcHelper关闭Connection对象
		JdbcHelper.close(stmt,connection);
		//返回degrees
		return teachers;
	}
	public Teacher find(Integer id)throws SQLException{
		Teacher teacher = null;
		Connection connection = JdbcHelper.getConn();
		String findTeacher_sql = "SELECT * FROM teacher WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(findTeacher_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		ResultSet resultSet = preparedStatement.executeQuery();
		//由于id不能取重复值，故结果集中最多有一条记录
		//若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建Degree对象
		//若结果集中没有记录，则本方法返回null
		if (resultSet.next()){
			teacher = new Teacher(resultSet.getInt("id"),
					resultSet.getString("name"),
					ProfTitleService.getInstance().find(resultSet.getInt("title_id")),
					DegreeService.getInstance().find(resultSet.getInt("degree_id")),
					DepartmentService.getInstance().find(resultSet.getInt("department_id")));
		}
		//关闭资源
		JdbcHelper.close(resultSet,preparedStatement,connection);
		return teacher;
	}
	
	public boolean update(Teacher teacher)throws SQLException {
		Connection connection = JdbcHelper.getConn();
		String updateTeacher_sql = "UPDATE teacher SET name=?, title_id=?,degree_id=?,department_id=? WHERE id=?";
		PreparedStatement preparedStatement = connection.prepareStatement(updateTeacher_sql);
		preparedStatement.setString(1,teacher.getName());
		preparedStatement.setInt(2,teacher.getTitle().getId());
		preparedStatement.setInt(3,teacher.getDegree().getId());
		preparedStatement.setInt(4,teacher.getDepartment().getId());
		preparedStatement.setInt(5,teacher.getId());
		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("更新"+affectedRows+"条记录");
		preparedStatement.close();
		connection.close();
		return affectedRows>0;
	}
	
	public boolean add(Teacher teacher)throws SQLException{
		Connection connection = JdbcHelper.getConn();
		String updateTeacher_sql = "insert into teacher"
				+ "(id,name,title_id,degree_id,department_id)"
				+ " values (?,?,?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(updateTeacher_sql);
		preparedStatement.setInt(1,teacher.getId());
		preparedStatement.setString(2,teacher.getName());
		preparedStatement.setInt(3,teacher.getTitle().getId());
		preparedStatement.setInt(4,teacher.getDegree().getId());
		preparedStatement.setInt(5,teacher.getDepartment().getId());
		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("添加"+affectedRows+"条记录");
		preparedStatement.close();
		connection.close();
		return affectedRows>0;
	}

	public boolean delete(Integer id)throws SQLException{
		//获取数据库连接对象
		Connection connection = JdbcHelper.getConn();
		//创建sql语句
		String deleteTeacher_sql = "DELETE FROM teacher" + " WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement pstmt = connection.prepareStatement(deleteTeacher_sql);
		//为预编译语句赋值
		pstmt.setInt(1,id);
		int affectedRowNum = pstmt.executeUpdate();
		System.out.println("删除"+affectedRowNum+"条记录");
		//关闭pstmt, connection对象（关闭资源）
		JdbcHelper.close(pstmt,connection);
		return affectedRowNum > 0;
	}
	
	public boolean delete(Teacher teacher)throws SQLException{
		return delete(teacher.getId());
	}
	//方便测试的mian方法
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		ProfTitle profTitle = ProfTitleDao.getInstance().find(3);
//		System.out.println(profTitle);
//		Degree phd = DegreeDao.getInstance().find(47);
//		System.out.println(phd);
//		Department misDept = DepartmentService.getInstance().find(2);
//		System.out.println(misDept);
//		Teacher teacher = new Teacher(1,"苏同",profTitle,phd,misDept);
//		TeacherDao.getInstance().add(teacher);
//		System.out.println("have finished");
		Teacher teacher = TeacherDao.getInstance().find(2);
		System.out.println(teacher);
		teacher.setName("赵彤");
		TeacherDao.getInstance().update(teacher);
		Teacher teacher1 = TeacherDao.getInstance().find(2);
		System.out.println(teacher1);
	}
}
