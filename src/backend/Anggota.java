package backend;

import java.sql.*;
import java.util.ArrayList;
public class Anggota {

    private int idAnggota;
    private String nama;
    private String alamat;
    private String telepon;

    public Anggota() {
    }

    public Anggota(String nama, String alamat, String telepon) {
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
    }

    public int getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(int idAnggota) {
        this.idAnggota = idAnggota;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public Anggota getById(int id) {
        Anggota anggota = new Anggota();
        String sql = "SELECT * FROM anggota WHERE idanggota = " + id;

        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            if (rs != null && rs.next()) {
                anggota.setIdAnggota(rs.getInt("idanggota"));
                anggota.setNama(rs.getString("nama"));
                anggota.setAlamat(rs.getString("alamat"));
                anggota.setTelepon(rs.getString("telepon"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return anggota;
    }

    public ArrayList<Anggota> getAll() {
        ArrayList<Anggota> list = new ArrayList<>();
        String sql = "SELECT * FROM anggota";

        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs != null && rs.next()) {
                Anggota anggota = new Anggota();
                anggota.setIdAnggota(rs.getInt("idanggota"));
                anggota.setNama(rs.getString("nama"));
                anggota.setAlamat(rs.getString("alamat"));
                anggota.setTelepon(rs.getString("telepon"));

                list.add(anggota);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Anggota> search(String keyword) {
        ArrayList<Anggota> list = new ArrayList<>();
        String sql = "SELECT * FROM anggota WHERE "
                + "nama LIKE '%" + keyword + "%' "
                + "OR alamat LIKE '%" + keyword + "%' "
                + "OR telepon LIKE '%" + keyword + "%'";

        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs != null && rs.next()) {
                Anggota anggota = new Anggota();
                anggota.setIdAnggota(rs.getInt("idanggota"));
                anggota.setNama(rs.getString("nama"));
                anggota.setAlamat(rs.getString("alamat"));
                anggota.setTelepon(rs.getString("telepon"));

                list.add(anggota);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void save() {
        if (getById(idAnggota).getIdAnggota() == 0) {
            String sql = "INSERT INTO anggota (nama, alamat, telepon) "
                    + "VALUES ('" + this.nama + "', '"
                    + this.alamat + "', '"
                    + this.telepon + "')";

            this.idAnggota = DBHelper.insertQueryGetId(sql);
        } else {
            String sql = "UPDATE anggota SET "
                    + "nama = '" + this.nama + "', "
                    + "alamat = '" + this.alamat + "', "
                    + "telepon = '" + this.telepon + "' "
                    + "WHERE idanggota = " + this.idAnggota;

            DBHelper.executeQuery(sql);
        }
    }

    public void delete() {
        String sql = "DELETE FROM anggota WHERE idanggota = " + this.idAnggota;
        DBHelper.executeQuery(sql);
    }

}
