import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class SchoolDao {
	private static SchoolDao schoolDao = new SchoolDao();
	private static Collection<School> schools;
	static{
		schools = new TreeSet<School>();
		School school = new School(1,"土木工程","01","");
		schools.add(school);
		schools.add(new School(2,"管理工程","02","最好的学院"));
		schools.add(new School(3,"市政工程","03",""));
		schools.add(new School(4,"艺术","04",""));
	}
	private SchoolDao(){}
	public static SchoolDao getInstance(){
		return schoolDao;
	}

	public Collection<School> findAll(){
		return SchoolDao.schools;
	}
//将school对象已记录的形式存到数据库中
	public School addWthSP(School school)throws SQLException{
	    //存储过程为一组为了完成特定工程的SQL语句。存储过程的优点：无论存储过程中包含多少语句
        // ，客户端和服务器只完成一次信息交互
        //获得连接对象其中jdbc被看做一个接口
		Connection connection = JdbcHelper.getConn();
		//根据连接对象准备可调用语句对象，sp_addDepartment为存储过程名称，后面为4个参数
        //括号里的sql语句即为在powershell里执行语句，问号里写好要传递的参数.preparecall对应的是callableStatement对象
		CallableStatement callableStatement=connection.prepareCall("CALL sp_addSchool (?,?,?,?)");
		//得第4个参数设置为输出参数，类型为长整型（数据库的数据类型)
		callableStatement.registerOutParameter(4, Types.BIGINT);
		callableStatement.setString(1,school.getDescription());
		callableStatement.setString(2,school.getNo());
		callableStatement.setString(3,school.getRemarks());

		//执行可调用语句callableStatement
		callableStatement.execute();
		//获得第5个参数的值，数据库为该记录自动生成的id
		int id = callableStatement.getInt(4);
		//为参数department的id字段赋值
		school.setId(id);
		callableStatement.close();
		connection.close();
		return school;
	}
	public School find(Integer id)throws SQLException{
		School school=null;
		Connection connection=JdbcHelper.getConn();
		String sss="select * from school where id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sss);
		preparedStatement.setInt(1,id);
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next()){
			school=new School(resultSet.getInt("id"),
					resultSet.getString("description")
					,resultSet.getString("no")
					,resultSet.getString("remarks")
					);
		}
		JdbcHelper.close(preparedStatement, connection);
		return school;
	}
	public boolean update(School school){
		schools.remove(school);
		return schools.add(school);
	}
	public boolean add(School school){
		return schools.add(school);
	}

	public boolean delete(Integer id)throws SQLException{
		School school =this.find(id);
		return this.delete(school);
	}
	 public static void main(String[] args)throws SQLException {
		//创建一个被添加到数据库里的对象
		School addSchool=new School("3","2","");
		//schooldao是将数据进行存储的一个对象
		SchoolDao schoolDao=new SchoolDao();
		//执行添加方法
		School adddSchool=schoolDao.addWthSP(addSchool);
		System.out.println(adddSchool);
		System.out.println("添加school成功");

	}
	public boolean delete(School school){
		return SchoolDao.schools.remove(school);
	}
}
