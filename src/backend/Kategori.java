package backend;

import java.sql.*;
import java.util.ArrayList;

public class Kategori {

    private int idKategori;
    private String nama;
    private String keterangan;

    public Kategori() {
    }

    public Kategori(String nama, String keterangan) {
        this.nama = nama;
        this.keterangan = keterangan;
    }

    public int getIdKategori() {
        return idKategori;
    }

    public String getNama() {
        return nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setIdKategori(int idKategori) {
        this.idKategori = idKategori;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    // ===================== GET BY ID ======================
    public Kategori getById(int id) {
        Kategori kat = new Kategori();
        String sql = "SELECT * FROM kategori WHERE idkategori = " + id;

        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            if (rs != null && rs.next()) {
                kat.setIdKategori(rs.getInt("idkategori"));
                kat.setNama(rs.getString("nama"));
                kat.setKeterangan(rs.getString("keterangan"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kat;
    }

    // ===================== GET ALL ======================
    public ArrayList<Kategori> getAll() {
        ArrayList<Kategori> ListKategori = new ArrayList<>();
        String sql = "SELECT * FROM kategori";

        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs != null && rs.next()) {
                Kategori kat = new Kategori();
                kat.setIdKategori(rs.getInt("idkategori"));
                kat.setNama(rs.getString("nama"));
                kat.setKeterangan(rs.getString("keterangan"));

                ListKategori.add(kat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListKategori;
    }

    // ===================== SEARCH ======================
    public ArrayList<Kategori> search(String keyword) {
        ArrayList<Kategori> ListKategori = new ArrayList<>();
        String sql = "SELECT * FROM kategori WHERE nama ILIKE '%" + keyword
                + "%' OR keterangan ILIKE '%" + keyword + "%'";

        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs != null && rs.next()) {
                Kategori kat = new Kategori();
                kat.setIdKategori(rs.getInt("idkategori"));
                kat.setNama(rs.getString("nama"));
                kat.setKeterangan(rs.getString("keterangan"));

                ListKategori.add(kat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListKategori;
    }

    // ===================== SAVE ======================
    public void save() {

        if (this.idKategori == 0) {
            // INSERT (POSTGRESQL)
            String sql = "INSERT INTO kategori (nama, keterangan) VALUES ("
                    + "'" + this.nama + "', "
                    + "'" + this.keterangan + "'"
                    + ") RETURNING idkategori";

            this.idKategori = DBHelper.insertQueryGetId(sql);

        } else {
            // UPDATE
            String sql = "UPDATE kategori SET "
                    + "nama = '" + this.nama + "', "
                    + "keterangan = '" + this.keterangan + "' "
                    + "WHERE idkategori = " + this.idKategori;

            DBHelper.executeQuery(sql);
        }
    }

    // ===================== DELETE ======================
    public void delete() {
        String sql = "DELETE FROM kategori WHERE idkategori = " + this.idKategori;
        DBHelper.executeQuery(sql);
    }

    // Override toString() untuk JComboBox
    @Override
    public String toString() {
        return nama;
    }
}
