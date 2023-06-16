package org.lessons.java.nations;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/java-db-nations";
        String user = "root";
        String password = "root";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println(con.getCatalog());
            String sql = "SELECT c.name as country, c.country_id, r.name as region, cont.name as continent FROM countries c JOIN regions r ON c.region_id = r.region_id JOIN continents cont ON r.continent_id = cont.continent_id ORDER BY c.name;";
            System.out.println("");
            System.out.println("Query: " + sql);
            System.out.println("");
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        String country = rs.getString(1);
                        int countryId = rs.getInt(2);
                        String region = rs.getString(3);
                        String continent = rs.getString(4);
                        System.out.println("Country: " + country + " | " + "Country-id: " + countryId + " | " + "Region: " + region + " | " + "Continent: " + continent);
                        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Sorry, unable to connect");
        }
    }
}
