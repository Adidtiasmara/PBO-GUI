package frontend;

import backend.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrmKategori extends JFrame {

    private JTextField txtIdKategori, txtNama, txtKeterangan, txtCari;
    private JButton btnSimpan, btnHapus, btnTambahBaru, btnCari;
    private JTable tblKategori;
    private DefaultTableModel model;

    public FrmKategori() {
        setTitle("Form Kategori");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        kosongkanForm();
        tampilkanData();
    }

    private void initComponents() {

        // PANEL UTAMA
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===============================
        // 1. TextField ID (disable)
        // ===============================
        txtIdKategori = new JTextField();
        txtIdKategori.setEnabled(false);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Kategori"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(txtIdKategori, gbc);
        gbc.gridwidth = 1;

        // ===============================
        // 2. TextField Nama
        // ===============================
        txtNama = new JTextField();

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nama Kategori"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(txtNama, gbc);
        gbc.gridwidth = 1;

        // ===============================
        // 3. TextField Keterangan
        // ===============================
        txtKeterangan = new JTextField();

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Keterangan"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(txtKeterangan, gbc);
        gbc.gridwidth = 1;

        // ===============================
        // 4-6. Tombol Samping Kiri
        // ===============================
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnSimpan = new JButton("Simpan");
        btnHapus = new JButton("Hapus");
        btnTambahBaru = new JButton("Tambah Baru");

        panelTombol.add(btnSimpan);
        panelTombol.add(btnHapus);
        panelTombol.add(btnTambahBaru);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 3;
        panel.add(panelTombol, gbc);
        gbc.gridwidth = 1;

        // ===============================
        // 7–8. Kolom pencarian + tombol Cari
        // ===============================
        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtCari = new JTextField(15);
        btnCari = new JButton("Cari");

        panelCari.add(txtCari);
        panelCari.add(btnCari);

        gbc.gridx = 3; gbc.gridy = 3;
        panel.add(panelCari, gbc);

        // ===============================
        // 9. Tabel
        // ===============================
        model = new DefaultTableModel(new Object[]{"ID", "Nama", "Keterangan"}, 0);
        tblKategori = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblKategori);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

        // Add panel to frame
        add(panel);

        // ============= EVENT HANDLER =============

        btnSimpan.addActionListener(e -> simpan());
        btnHapus.addActionListener(e -> hapus());
        btnTambahBaru.addActionListener(e -> kosongkanForm());
        btnCari.addActionListener(e -> cari());

        tblKategori.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblKategori.getSelectedRow();
                txtIdKategori.setText(model.getValueAt(row, 0).toString());
                txtNama.setText(model.getValueAt(row, 1).toString());
                txtKeterangan.setText(model.getValueAt(row, 2).toString());
            }
        });
    }

    // ======================= FUNGSI CRUD =======================

    private void kosongkanForm() {
        txtIdKategori.setText("0");
        txtNama.setText("");
        txtKeterangan.setText("");
    }

    private void tampilkanData() {
        model.setRowCount(0);
        ArrayList<Kategori> list = new Kategori().getAll();

        for (Kategori kat : list) {
            model.addRow(new Object[]{
                kat.getIdKategori(),
                kat.getNama(),
                kat.getKeterangan()
            });
        }
    }

    private void cari() {
        model.setRowCount(0);
        String keyword = txtCari.getText();
        ArrayList<Kategori> list = new Kategori().search(keyword);

        for (Kategori kat : list) {
            model.addRow(new Object[]{
                kat.getIdKategori(),
                kat.getNama(),
                kat.getKeterangan()
            });
        }
    }

    private void simpan() {
        Kategori kat = new Kategori();
        kat.setIdKategori(Integer.parseInt(txtIdKategori.getText()));
        kat.setNama(txtNama.getText());
        kat.setKeterangan(txtKeterangan.getText());

        kat.save();
        tampilkanData();
        kosongkanForm();
    }

    private void hapus() {
        Kategori kat = new Kategori();
        kat.setIdKategori(Integer.parseInt(txtIdKategori.getText()));

        kat.delete();
        tampilkanData();
        kosongkanForm();
    }

    // ======================= MAIN =======================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmKategori().setVisible(true));
    }
}
