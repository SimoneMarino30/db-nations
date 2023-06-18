package org.lessons.java.nations;

import java.math.BigInteger;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/java-db-nations";
        String user = "root";
        String password = "root";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println(con.getCatalog());
            //String sql = "SELECT c.name as country, c.country_id, r.name as region, cont.name as continent FROM countries c JOIN regions r ON c.region_id = r.region_id JOIN continents cont ON r.continent_id = cont.continent_id ORDER BY c.name;";
            //System.out.println("");
            //System.out.println("Query: " + sql);
            //System.out.println("");
// ------------------------------------------------------------------------------------------
            Scanner scan = new Scanner(System.in);
            System.out.println("What country would you like to search for?");
            String wantedString = scan.nextLine();
            //scan.close();
            String safeSql = "SELECT c.name as country, c.country_id, r.name as region, cont.name as continent FROM countries c JOIN regions r ON c.region_id = r.region_id JOIN continents cont ON r.continent_id = cont.continent_id WHERE c.name LIKE ? ORDER BY c.name;";
            //System.out.println("Query: " + safeSql);
            try(PreparedStatement ps = con.prepareStatement(safeSql)) {

                ps.setString(1, "%" + wantedString +"%");


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
            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                // BONUS
                String sqlMilestoneBonus = "SELECT c.name, l.language, cs.year, cs.population, cs.gdp FROM countries c JOIN country_languages cl ON c.country_id = cl.country_id JOIN languages l ON cl.language_id = l.language_id JOIN country_stats cs ON c.country_id = cs.country_id WHERE c.country_id LIKE ?;";
                System.out.println("What country id would you like to see?");
                int wantedId = Integer.parseInt(scan.nextLine());
                scan.close();
                try (PreparedStatement ps = connection.prepareStatement(sqlMilestoneBonus)) {

                    ps.setInt(1, wantedId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String countryName = rs.getString(1);
                            String countryLang = rs.getString(2);
                            int countryYear = rs.getInt(3);
                            int countryPopulation = rs.getInt(4);
                            long countryGdp = rs.getLong(5);
                            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
                            System.out.println("Country: " + countryName + " | \n" + "Languages spoken: " + countryLang + " | \n" + "Year: " + countryYear + " | \n" + "Population: " + countryPopulation + " | \n" + "Gdp: " + countryGdp);
                            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Sorry, unable to connect");
            e.printStackTrace();
        }
    }
}


// QUERY NON FUNZIONANTE GROUP_CONCAT
// SELECT GROUP_CONCAT(l.language),c.name, cs.year, cs.population, cs.gdp FROM countries c
//JOIN country_languages cl ON c.country_id = cl.country_id
//JOIN languages l ON cl.language_id = l.language_id
//JOIN country_stats cs ON c.country_id = cs.country_id
//WHERE c.country_id;