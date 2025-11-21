package frontend;

import backend.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrmBuku extends JFrame {

    private JTextField txtIdBuku, txtJudul, txtPenerbit, txtPenulis, txtCari;
    private JComboBox<Kategori> cmbKategori;
    private JButton btnSimpan, btnHapus, btnTambahBaru, btnCari;
    private JTable tblBuku;
    private DefaultTableModel model;

    public FrmBuku() {
        setTitle("Form Buku");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        tampilkanCmbKategori();
        kosongkanForm();
        tampilkanData();
    }

    private void initComponents() {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ========== ID Buku ==========
        txtIdBuku = new JTextField();
        txtIdBuku.setEnabled(false);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Buku"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(txtIdBuku, gbc);
        gbc.gridwidth = 1;

        // ========== Kategori ==========
        cmbKategori = new JComboBox<>();

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Kategori"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(cmbKategori, gbc);
        gbc.gridwidth = 1;

        // ========== Judul ==========
        txtJudul = new JTextField();

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Judul"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(txtJudul, gbc);
        gbc.gridwidth = 1;

        // ========== Penerbit ==========
        txtPenerbit = new JTextField();

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Penerbit"), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(txtPenerbit, gbc);
        gbc.gridwidth = 1;

        // ========== Penulis ==========
        txtPenulis = new JTextField();

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Penulis"), gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(txtPenulis, gbc);
        gbc.gridwidth = 1;

        // ========== Panel Tombol ==========
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnSimpan = new JButton("Simpan");
        btnHapus = new JButton("Hapus");
        btnTambahBaru = new JButton("Tambah Baru");

        panelTombol.add(btnSimpan);
        panelTombol.add(btnHapus);
        panelTombol.add(btnTambahBaru);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 3;
        panel.add(panelTombol, gbc);
        gbc.gridwidth = 1;

        // ========== Cari ==========
        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        txtCari = new JTextField(15);
        btnCari = new JButton("Cari");

        panelCari.add(txtCari);
        panelCari.add(btnCari);

        gbc.gridx = 3; gbc.gridy = 5;
        panel.add(panelCari, gbc);

        // ========== Tabel Buku ==========
        model = new DefaultTableModel(
            new Object[]{"ID", "Kategori", "Judul", "Penerbit", "Penulis"}, 0
        );

        tblBuku = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblBuku);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

        add(panel);

        // ========== EVENT ==========
        btnSimpan.addActionListener(e -> simpan());
        btnHapus.addActionListener(e -> hapus());
        btnTambahBaru.addActionListener(e -> kosongkanForm());
        btnCari.addActionListener(e -> cari());

        tblBuku.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblBuku.getSelectedRow();

                txtIdBuku.setText(model.getValueAt(row, 0).toString());
                txtJudul.setText(model.getValueAt(row, 2).toString());
                txtPenerbit.setText(model.getValueAt(row, 3).toString());
                txtPenulis.setText(model.getValueAt(row, 4).toString());

                // ambil object kategori lengkap dari database
                Buku b = new Buku().getById(Integer.parseInt(txtIdBuku.getText()));
                cmbKategori.setSelectedItem(b.getKategori());
            }
        });
    }

    // ========== Isi ComboBox Kategori ==========
    private void tampilkanCmbKategori() {
        cmbKategori.removeAllItems();

        ArrayList<Kategori> list = new Kategori().getAll();

        for (Kategori k : list) {
            cmbKategori.addItem(k);
        }
    }

    // ========== Kosongkan Form ==========
    private void kosongkanForm() {
        txtIdBuku.setText("0");
        cmbKategori.setSelectedIndex(0);
        txtJudul.setText("");
        txtPenerbit.setText("");
        txtPenulis.setText("");
    }

    // ========== Tampilkan Data ke JTable ==========
    private void tampilkanData() {
        model.setRowCount(0);

        ArrayList<Buku> list = new Buku().getAll();

        for (Buku b : list) {
            model.addRow(new Object[]{
                b.getIdbuku(),
                b.getKategori().getNama(),
                b.getJudul(),
                b.getPenerbit(),
                b.getPenulis()
            });
        }
    }

    // ========== Cari ==========
    private void cari() {
        model.setRowCount(0);

        ArrayList<Buku> list = new Buku().search(txtCari.getText());

        for (Buku b : list) {
            model.addRow(new Object[]{
                b.getIdbuku(),
                b.getKategori().getNama(),
                b.getJudul(),
                b.getPenerbit(),
                b.getPenulis()
            });
        }
    }

    // ========== Simpan ==========
    private void simpan() {
        Buku b = new Buku();

        b.setIdbuku(Integer.parseInt(txtIdBuku.getText()));
        b.setKategori((Kategori) cmbKategori.getSelectedItem());
        b.setJudul(txtJudul.getText());
        b.setPenerbit(txtPenerbit.getText());
        b.setPenulis(txtPenulis.getText());

        b.save();
        tampilkanData();
        kosongkanForm();
    }

    // ========== Hapus ==========
    private void hapus() {
        Buku b = new Buku();

        b.setIdbuku(Integer.parseInt(txtIdBuku.getText()));
        b.delete();

        tampilkanData();
        kosongkanForm();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmBuku().setVisible(true));
    }
}
