package backend;

import java.sql.*;
import java.util.ArrayList;

public class Buku {
    private int idbuku;
    private Kategori kategori;
    private String judul;
    private String penerbit;
    private String penulis;

    public Buku() {
    }

    public Buku(Kategori kategori, String judul, String penerbit, String penulis) {
        this.kategori = kategori;
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
    }

    public int getIdbuku() {
        return idbuku;
    }

    public void setIdbuku(int idbuku) {
        this.idbuku = idbuku;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    // ================= GET BY ID =================
    public Buku getById(int id) {
        Buku buku = new Buku();
        String sql = "SELECT b.*, k.nama, k.keterangan FROM buku b "
                + " LEFT JOIN kategori k ON b.idkategori = k.idkategori "
                + " WHERE b.idbuku = " + id;

        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            if (rs.next()) {
                buku.setIdbuku(rs.getInt("idbuku"));

                Kategori kat = new Kategori();
                kat.setIdKategori(rs.getInt("idkategori"));
                kat.setNama(rs.getString("nama"));
                kat.setKeterangan(rs.getString("keterangan"));
                buku.setKategori(kat);

                buku.setJudul(rs.getString("judul"));
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setPenulis(rs.getString("penulis"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buku;
    }

    // ================= GET ALL =================
    public ArrayList<Buku> getAll() {
        ArrayList<Buku> ListBuku = new ArrayList<>();
        String sql = "SELECT b.*, k.nama, k.keterangan FROM buku b "
                + " LEFT JOIN kategori k ON b.idkategori = k.idkategori";

        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs.next()) {
                Buku buku = new Buku();

                buku.setIdbuku(rs.getInt("idbuku"));

                Kategori kat = new Kategori();
                kat.setIdKategori(rs.getInt("idkategori"));
                kat.setNama(rs.getString("nama"));
                kat.setKeterangan(rs.getString("keterangan"));
                buku.setKategori(kat);

                buku.setJudul(rs.getString("judul"));
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setPenulis(rs.getString("penulis"));

                ListBuku.add(buku);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListBuku;
    }

    // ================= SEARCH =================
    public ArrayList<Buku> search(String keyword) {
        ArrayList<Buku> ListBuku = new ArrayList<>();
        String sql = "SELECT b.*, k.nama, k.keterangan FROM buku b "
                + " LEFT JOIN kategori k ON b.idkategori = k.idkategori "
                + " WHERE b.judul LIKE '%" + keyword + "%' "
                + " OR b.penerbit LIKE '%" + keyword + "%' "
                + " OR b.penulis LIKE '%" + keyword + "%' ";

        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs.next()) {
                Buku buku = new Buku();

                buku.setIdbuku(rs.getInt("idbuku"));

                Kategori kat = new Kategori();
                kat.setIdKategori(rs.getInt("idkategori"));
                kat.setNama(rs.getString("nama"));
                kat.setKeterangan(rs.getString("keterangan"));
                buku.setKategori(kat);

                buku.setJudul(rs.getString("judul"));
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setPenulis(rs.getString("penulis"));

                ListBuku.add(buku);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ListBuku;
    }

    // ================= SAVE =================
    public void save() {
        if (getById(idbuku).getIdbuku() == 0) {
            // INSERT
            String sql = "INSERT INTO buku (idkategori, judul, penerbit, penulis) VALUES("
                    + this.kategori.getIdKategori() + ", "
                    + "'" + this.judul + "', "
                    + "'" + this.penerbit + "', "
                    + "'" + this.penulis + "')";
            this.idbuku = DBHelper.insertQueryGetId(sql);
        } else {
            // UPDATE
            String sql = "UPDATE buku SET "
                    + "idkategori = " + this.kategori.getIdKategori() + ", "
                    + "judul = '" + this.judul + "', "
                    + "penerbit = '" + this.penerbit + "', "
                    + "penulis = '" + this.penulis + "' "
                    + "WHERE idbuku = " + this.idbuku;
            DBHelper.executeQuery(sql);
        }
    }

    // ================= DELETE =================
    public void delete() {
        String sql = "DELETE FROM buku WHERE idbuku = " + this.idbuku;
        DBHelper.executeQuery(sql);
    }
}
