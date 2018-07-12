package com.muhammad.crud;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtils studentDbUtils;

	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			studentDbUtils = new StudentDbUtils(dataSource);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentControllerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {
			String theCommand = request.getParameter("command");

			if (theCommand == null) {
				theCommand = "LIST";
			}

			switch (theCommand) {
			case "LIST":
				listStudents(request, response);
				break;
			case "ADD":
				addStudent(request, response);
				break;
			case "LOAD":
				loadStudent(request, response);
				break;
			case "UPDATE":
				updateStudent(request, response);
				break;
			case "DELETE":
				deleteStudent(request, response);
				break;
			default:
				listStudents(request, response);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// list all Students (Read)
	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<Student> students = studentDbUtils.getStudents();

		request.setAttribute("listStudents", students);

		RequestDispatcher rd = request.getRequestDispatcher("listStudents.jsp");
		rd.forward(request, response);

	}

	// add student

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		Student st = new Student(firstName, lastName, email);
		studentDbUtils.addStudent(st);

		listStudents(request, response);

	}

	// load specific student
	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String stuId = request.getParameter("studentId");

		Student theStudent = studentDbUtils.LoadStudent(stuId);

		request.setAttribute("The_Student", theStudent);

		RequestDispatcher rd = request.getRequestDispatcher("update-student-form.jsp");
		rd.forward(request, response);

	}

	// update
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int stuId = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		Student st = new Student(stuId, firstName, lastName, email);

		studentDbUtils.updateStudent(st);

		listStudents(request, response);

	}

	// delete Student

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String stuId = request.getParameter("studentId");

		studentDbUtils.deleteStudent(stuId);

		listStudents(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
