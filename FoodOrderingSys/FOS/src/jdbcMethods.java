import java.sql.*;

public class jdbcMethods
{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/foodorderingsys";
    private static final String USER = "root";
    private static final String PASSWORD = "2311954244";

    public static int CountRows(String table) 
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try 
        {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            String query = "SELECT COUNT(*) AS row_count FROM " + table;
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            if (resultSet.next()) 
            {
                int rowCount = resultSet.getInt("row_count");
                return rowCount;
            }
        } 
        catch(SQLException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                // Close ResultSet, PreparedStatement, and Connection
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
        
        // Return a default value if no count was obtained
        return -1; 
    }

    public static Object getColumnData(String tableName, String columnName, int rowNumber) 
    {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT " + columnName + " FROM " + tableName + " LIMIT ?, 1")) 
             {
            statement.setInt(1, rowNumber - 1); // Adjust row number since SQL indexing starts from 0
            try (ResultSet resultSet = statement.executeQuery()) 
            {
                if (resultSet.next()) 
                {
                    return resultSet.getObject(columnName); // Assuming column name is the same as the table column name
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        // 调用方法示例
        String tableName = "orderitem";
        String columnName = "ItemName";
        int rowNumber = 1;

        String name = "" + getColumnData(tableName, columnName, rowNumber);
        System.out.println("Data for column " + columnName + " in row " + rowNumber + ": " + name);
    }
}