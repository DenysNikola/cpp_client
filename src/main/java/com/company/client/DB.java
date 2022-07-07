package com.company.client;
import java.sql.*;
import java.util.ArrayList;

public class DB {
    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "cpp_db";
    private final String LOGIN = "root";
    private final String PASS = "";

    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public void insertName(String name) throws ClassNotFoundException, SQLException{
        String sql = "INSERT INTO `names` (name) VALUES (?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, name);

        prSt.executeUpdate();
    }

    public ArrayList<String> getNames() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM names";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<String> names = new ArrayList<>();
        while(res.next())
            names.add(res.getString("name"));

        return names;
    }



}
