package ps.projekat.podaci.baza;

import java.sql.*;

public class BazaPodatakaUtil {

    private static ConnectionPool connectionPool = ConnectionPool.getInstanca();

    public static PreparedStatement prepareStatement(Connection connection, String sql, boolean generatedKeys, Object... values) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, generatedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for (int i = 0; i < values.length; i++)
            preparedStatement.setObject(i + 1, values[i]);
        return preparedStatement;
    }

    public static Connection getKonekciju() {
        Connection connection = null;
        try {
            connection = connectionPool.provjeraKonekcije();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(Connection connection) {
        if (connection != null)
            connectionPool.prijavljivanjeKonekcije(connection);
    }

    public static void close(Statement statement) {
        if (statement != null)
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null)
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        close(resultSet);
        close(statement);
        close(connection);
    }

    public static void close(Statement statement, Connection connection) {
        close(statement);
        close(connection);
    }
}
