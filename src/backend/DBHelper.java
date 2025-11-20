package backend;

import java.sql.*;

public class DBHelper {

    private static Connection koneksi;

    public static void bukaKoneksi() {
        if (koneksi == null) {
            try {

                // WAJIB ADA UNTUK POSTGRESQL
                Class.forName("org.postgresql.Driver");

                String url = "jdbc:postgresql://localhost:5432/dbperpus";
                String user = "postgres";
                String password = "Mfirman2806";

                koneksi = DriverManager.getConnection(url, user, password);

                System.out.println("Koneksi ke PostgreSQL BERHASIL!");

            } catch (Exception t) {
                System.out.println("Error koneksi PostgreSQL!");
                t.printStackTrace();
            }
        }
    }

    // KHUSUS POSTGRESQL: GUNAKAN RETURNING
    public static int insertQueryGetId(String query) {
        bukaKoneksi();
        int id = -1;

        try {
            PreparedStatement ps = koneksi.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public static boolean executeQuery(String query) {
        bukaKoneksi();
        try {
            Statement stmt = koneksi.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ResultSet selectQuery(String query) {
        bukaKoneksi();
        ResultSet rs = null;

        try {
            Statement stmt = koneksi.createStatement();
            rs = stmt.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }
}
