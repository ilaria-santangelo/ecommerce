package com.ecommerceapp.servlet;

import com.ecommerceapp.utility.DatabaseManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@WebServlet("chat")
public class ChatServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Chat</title></head>");
		out.println("<body>");
		out.println("<h1>Chat:</h1>");
		// TODO: Retrieve messages from database
//		String query = "";
//		try(Connection connection = DatabaseManager.getConnection(); Statement statement = connection.createStatement()) {
//			ResultSet resultSet = statement.executeQuery(query);
//			while(resultSet.next()) {
//				String message = resultSet.getString("message");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//		}
		List<String> messages = List.of(
				"This is a test message",
				"This is also a test message"
		);
		messages.forEach(message -> out.println("<p>" + message + "</p>"));
		out.println("<form action=\"/chat\" method=\"post\">");
		out.println("<input type=\"text\" id=\"message\" name=\"message\"><br>");
		out.println("<input type=\"submit\" name=\"send\" value=\"Send\" />");
		out.println("</body>");
		out.println("</html>");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String plainText = request.getParameter("message");
		String query = "INSERT INTO Chats(message) VALUES(" + plainText + ")"; // TODO: Add to database
		try(Connection connection = DatabaseManager.getConnection(); Statement statement = connection.createStatement()) {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		response.sendRedirect(request.getContextPath() + "/src/main/webapp/views/chat.html");
	}
}
