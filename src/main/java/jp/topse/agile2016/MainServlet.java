package jp.topse.agile2016;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

    private static final long serialVersionUID = 6961400581681209885L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("arg1", null);
        request.setAttribute("arg2", null);
        request.setAttribute("result", null);
        request.setAttribute("history", loadHistory());

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String arg1 = request.getParameter("arg1");
        request.setAttribute("arg1", arg1);
        String arg2 = request.getParameter("arg2");
        request.setAttribute("arg2", arg2);

        try {
            Integer v1 = Integer.parseInt(arg1);
            Integer v2 = Integer.parseInt(arg2);
            int value = v1.intValue() + v2.intValue();
            
            record(v1, v2, value);
            
            request.setAttribute("result", String.valueOf(value));
        } catch (NumberFormatException e) {
            request.setAttribute("result", "N/A");
        }
        
        request.setAttribute("history", loadHistory());

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    
    private void record(int v1, int v2, int result) {
		Connection connection = null;
        Statement statement = null;
        try {
            String dbPath = getServletContext().getRealPath("/WEB-INF/logs.db");

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            String sql = "INSERT INTO logs (v1, v2, result) VALUES (" + v1 +", " + v2 + ", " + result + ")";
            statement.execute(sql);
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        } catch (SQLException e) {
          e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private List<String> loadHistory() {
        List<String> history = new LinkedList<String>();

        Connection connection = null;
        Statement statement = null;
        try {
            String dbPath = getServletContext().getRealPath("/WEB-INF/logs.db");

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            String sql = "SELECT * FROM logs ORDER BY id DESC";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
            	int id = resultSet.getInt(1);
            	int v1 = resultSet.getInt(2);
            	int v2 = resultSet.getInt(3);
            	int result = resultSet.getInt(4);
            	history.add("" + id + " : " + v1 + " + " + v2 + " = " + result);
            }
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        } catch (SQLException e) {
          e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }	
        
        return history;
    }

}
