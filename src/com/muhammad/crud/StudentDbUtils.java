package com.muhammad.crud;

import com.muhammad.crud.Student;
import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

public class StudentDbUtils {

	private DataSource datasource;

	public StudentDbUtils(DataSource datasource) {
		this.datasource = datasource;
	}

	public List<Student> getStudents() throws SQLException {
		List<Student> students = new ArrayList<>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = datasource.getConnection();
			st = con.createStatement();
			String sql = "select * from student";
			rs = st.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");

				Student s1 = new Student(id, firstName, lastName, email);
				students.add(s1);
			}
		} finally {
			close(con, st, rs);
		}

		return students;
	}

	private void close(Connection con, Statement st, ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				st.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public StudentDbUtils() {
	}

	//add sql script 
	public void addStudent(Student st) throws Exception {
		Connection con = null;
		PreparedStatement stat = null;

		try {
			con = datasource.getConnection();

			String sql = "insert into student(first_name,last_name,email)" + "values(?,?,?)";
			stat = con.prepareStatement(sql);

			stat.setString(1, st.getFirstName());
			stat.setString(2, st.getLastName());
			stat.setString(3, st.getEmail());

			stat.execute();

		}

		finally {
			close(con, stat, null);
		}

	}
// read specific student 
	public Student LoadStudent(String stuId) throws Exception {

		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Student s1 = null;

		try {
			int studentId = Integer.parseInt(stuId);

			con = datasource.getConnection();
			String sql = "select * from student where id=?";

			st = con.prepareStatement(sql);

			st.setInt(1, studentId);

			rs = st.executeQuery();

			if (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");

				s1 = new Student(studentId, firstName, lastName, email);
			}

		} finally {
			close(con, st, rs);
		}

		return s1;
	}

	public void updateStudent(Student s) throws Exception {

		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			con = datasource.getConnection();
			String sql = "update student set first_name=?, last_name=?, email=? where id=?";

			st = con.prepareStatement(sql);

			st.setString(1, s.getFirstName());
			st.setString(2, s.getLastName());
			st.setString(3, s.getEmail());
			st.setInt(4, s.getId());

			st.execute();

		} finally

		{
			close(con, st, rs);
		}

	}

	public void deleteStudent(String stuId) throws Exception {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			int sId = Integer.parseInt(stuId);
			con = datasource.getConnection();
			String sql = "delete  from student where id=?";
			st = con.prepareStatement(sql);
			st.setInt(1, sId);
			st.execute();
		} finally {
			close(con, st, rs);
		}

	}

}
