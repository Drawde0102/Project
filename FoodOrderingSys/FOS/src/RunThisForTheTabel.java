import java.sql.*;

public class RunThisForTheTabel
{
    public static void main(String[] args) {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/FoodOrderingSys";//Fill in DatabaseURL
        Connection connection = null;
        PreparedStatement pstat = null;

        String[] itemName = {"Pizza","Chips","Burger","Fried Chicken Sandwich","Milkshake","Onion Rings","Veggie Burger","Coke"};
        double[] itemPrice = {10.00,5.00,7.50,8.20,4.50,5.00,5.50,3.00};
        int j = 0;

        for(int i = 0; i < itemName.length; i++)
        {
        
            String name = itemName[i];
            double price = itemPrice[i];
            j ++;

            try 
            {
                connection = DriverManager.getConnection(DATABASE_URL, "root", "password");
                pstat = connection.prepareStatement("INSERT INTO orderitem (ItemName, Price) VALUES (?, ?)");
                pstat.setString(1, name);
                pstat.setDouble(2, price);
                j = pstat.executeUpdate();
                System.out.println(j + " record successfully added to the table.");
            } 
            catch (SQLException sqlException) 
            {
                sqlException.printStackTrace();
            } 
            finally 
            {
                try 
                {
                    if (pstat != null) 
                    {
                        pstat.close();
                    }
                    if (connection != null) 
                    {
                        connection.close();
                    }
                } 
                catch (Exception exception) 
                {
                    exception.printStackTrace();
                }
            }
        }
        
    } 
} 