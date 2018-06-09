package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectarBD {

    Connection connection=null;

    public Connection ConectarBD() {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "127.0.0.1";
            String mydatabase = "instricpcion";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return connection;
    }
}
