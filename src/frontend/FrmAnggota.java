package frontend;

import backend.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrmAnggota extends JFrame {

    private JTextField txtIdAnggota, txtNama, txtAlamat, txtTelepon, txtCari;
    private JButton btnSimpan, btnHapus, btnTambahBaru, btnCari;
    private JTable tblAnggota;
    private DefaultTableModel model;

    public FrmAnggota() {
        setTitle("Form Anggota");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        kosongkanForm();
        tampilkanData();
    }

    private void initComponents() {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. ID Anggota 
        txtIdAnggota = new JTextField();
        txtIdAnggota.setEnabled(false);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Anggota"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(txtIdAnggota, gbc);
        gbc.gridwidth = 1;

        // 2. Nama
        txtNama = new JTextField();

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nama"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(txtNama, gbc);
        gbc.gridwidth = 1;

        // 3. Alamat
        txtAlamat = new JTextField();

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Alamat"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(txtAlamat, gbc);
        gbc.gridwidth = 1;

        // Telepon
        txtTelepon = new JTextField();

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Telepon"), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(txtTelepon, gbc);
        gbc.gridwidth = 1;

        // Button
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        btnSimpan = new JButton("Simpan");
        btnHapus = new JButton("Hapus");
        btnTambahBaru = new JButton("Tambah Baru");

        panelTombol.add(btnSimpan);
        panelTombol.add(btnHapus);
        panelTombol.add(btnTambahBaru);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 3;
        panel.add(panelTombol, gbc);
        gbc.gridwidth = 1;

        // Search
        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        txtCari = new JTextField(15);
        btnCari = new JButton("Cari");
        
        panelCari.add(txtCari);
        panelCari.add(btnCari);

        gbc.gridx = 3; gbc.gridy = 4;
        panel.add(panelCari, gbc);

        // Tabel
        model = new DefaultTableModel(new Object[]{"ID", "Nama", "Alamat", "Telepon"}, 0);
        tblAnggota = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblAnggota);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        panel.add(scrollPane, gbc);

        add(panel);

        // ===== Event =====

        btnSimpan.addActionListener(e -> simpan());
        btnHapus.addActionListener(e -> hapus());
        btnTambahBaru.addActionListener(e -> kosongkanForm());
        btnCari.addActionListener(e -> cari());

        tblAnggota.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblAnggota.getSelectedRow();
                txtIdAnggota.setText(model.getValueAt(row, 0).toString());
                txtNama.setText(model.getValueAt(row, 1).toString());
                txtAlamat.setText(model.getValueAt(row, 2).toString());
                txtTelepon.setText(model.getValueAt(row, 3).toString());
            }
        });
    }

    // ===============================
    // FUNGSI CRUD
    // ===============================

    private void kosongkanForm() {
        txtIdAnggota.setText("0");
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelepon.setText("");
    }

    private void tampilkanData() {
        model.setRowCount(0);
        ArrayList<Anggota> list = new Anggota().getAll();

        for (Anggota a : list) {
            model.addRow(new Object[]{
                a.getIdAnggota(),
                a.getNama(),
                a.getAlamat(),
                a.getTelepon()
            });
        }
    }

    private void cari() {
        model.setRowCount(0);
        ArrayList<Anggota> list = new Anggota().search(txtCari.getText());

        for (Anggota a : list) {
            model.addRow(new Object[]{
                a.getIdAnggota(),
                a.getNama(),
                a.getAlamat(),
                a.getTelepon()
            });
        }
    }

    private void simpan() {
        Anggota a = new Anggota();
        a.setIdAnggota(Integer.parseInt(txtIdAnggota.getText()));
        a.setNama(txtNama.getText());
        a.setAlamat(txtAlamat.getText());
        a.setTelepon(txtTelepon.getText());

        a.save();
        tampilkanData();
        kosongkanForm();
    }

    private void hapus() {
        Anggota a = new Anggota();
        a.setIdAnggota(Integer.parseInt(txtIdAnggota.getText()));
        a.delete();

        tampilkanData();
        kosongkanForm();
    }

    // ===============================
    // MAIN
    // ===============================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmAnggota().setVisible(true));
    }
}
