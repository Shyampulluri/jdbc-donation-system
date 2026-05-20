import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;

public class StudentForm {

    static DefaultTableModel model;
    static JTable table;
    static JScrollPane scrollPane;
    static JLabel totalLabel;

    public static void main(String[] args) {

        createTable(); // 🔥 create new table

        JFrame frame = new JFrame("Donation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(980, 760);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(12, 12));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 18);

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 12));
        topPanel.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Donation Details"));
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel l1 = new JLabel("Name");
        l1.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(l1, gbc);

        JTextField txtName = new JTextField();
        txtName.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtName, gbc);

        JLabel l2 = new JLabel("Email");
        l2.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(l2, gbc);

        JTextField txtEmail = new JTextField();
        txtEmail.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtEmail, gbc);

        JLabel l3 = new JLabel("Mobile");
        l3.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(l3, gbc);

        JTextField txtMobile = new JTextField();
        txtMobile.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtMobile, gbc);

        JLabel l4 = new JLabel("Donation Amount");
        l4.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        formPanel.add(l4, gbc);

        JTextField txtDonation = new JTextField();
        txtDonation.setFont(fieldFont);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtDonation, gbc);

        JLabel paymentHelp = new JLabel("Scan the QR code and pay the exact donation amount.");
        paymentHelp.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        paymentHelp.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(paymentHelp, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        buttonPanel.setBackground(Color.WHITE);
        JButton btnAdd = new JButton("Confirm Payment");
        btnAdd.setFont(fieldFont);
        JButton btnUpdate = new JButton("Update");
        btnUpdate.setFont(fieldFont);
        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(fieldFont);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        JPanel qrContainer = new JPanel(new BorderLayout());
        qrContainer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "QR Payment"));
        qrContainer.setBackground(Color.WHITE);
        QRPanel qrPanel = new QRPanel();
        qrPanel.setPreferredSize(new Dimension(260, 260));
        qrContainer.add(qrPanel, BorderLayout.CENTER);

        JLabel qrAmountLabel = new JLabel("Amount: ₹0.00");
        qrAmountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        qrAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        qrContainer.add(qrAmountLabel, BorderLayout.NORTH);

        JLabel qrHint = new JLabel("Place qr.png in the project folder to display it.");
        qrHint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        qrHint.setHorizontalAlignment(SwingConstants.CENTER);
        qrHint.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        qrContainer.add(qrHint, BorderLayout.SOUTH);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.65;
        gbc.fill = GridBagConstraints.BOTH;
        topPanel.add(formPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.35;
        topPanel.add(qrContainer, gbc);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Email");
        model.addColumn("Mobile");
        model.addColumn("Donation");
        model.addColumn("Payment Method");
        model.addColumn("Payment Status");

        table = new JTable(model);
        table.setFont(fieldFont);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        scrollPane = new JScrollPane(table);
        scrollPane.setVisible(false);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        totalLabel = new JLabel("Total Donation: 0");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalPanel.setBackground(Color.WHITE);
        totalPanel.add(totalLabel);
        bottomPanel.add(totalPanel, BorderLayout.SOUTH);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.CENTER);

        loadData();

        // ADD
        btnAdd.addActionListener(e -> {
            try {
                String name = txtName.getText().trim();
                String email = txtEmail.getText().trim();
                String mobile = txtMobile.getText().trim();
                String donationText = txtDonation.getText().trim();
                String paymentMethod = "QR Code";

                if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || donationText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields before payment.", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double donationAmount = Double.parseDouble(donationText);
                qrAmountLabel.setText("Amount: ₹" + String.format("%.2f", donationAmount));
                qrPanel.setAmount(donationAmount);
                qrPanel.repaint();

                if (!processPayment(frame, donationAmount)) {
                    return;
                }

                String paymentStatus = "Paid";

                Connection con = DBConnection.getConnection();
                String sql = "INSERT INTO donations(name,email,mobile,donation,payment_method,payment_status) VALUES(?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, mobile);
                ps.setDouble(4, donationAmount);
                ps.setString(5, paymentMethod);
                ps.setString(6, paymentStatus);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Payment successful and data saved!");

                loadData();
                scrollPane.setVisible(true);

                txtName.setText("");
                txtEmail.setText("");
                txtMobile.setText("");
                txtDonation.setText("");
                qrAmountLabel.setText("Amount: ₹0.00");
                qrPanel.setAmount(0);
                qrPanel.repaint();

                con.close();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Donation amount must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // TABLE CLICK
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                txtName.setText(model.getValueAt(row, 1).toString());
                txtEmail.setText(model.getValueAt(row, 2).toString());
                txtMobile.setText(model.getValueAt(row, 3).toString());
                txtDonation.setText(model.getValueAt(row, 4).toString());
            }
        });

        // UPDATE
        btnUpdate.addActionListener(e -> {
            try {
                int row = table.getSelectedRow();
                int id = Integer.parseInt(model.getValueAt(row, 0).toString());

                Connection con = DBConnection.getConnection();

                String sql = "UPDATE donations SET name=?,email=?,mobile=?,donation=?,payment_status=? WHERE id=?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, txtName.getText());
                ps.setString(2, txtEmail.getText());
                ps.setString(3, txtMobile.getText());
                ps.setDouble(4, Double.parseDouble(txtDonation.getText()));
                ps.setString(5, "Paid");
                ps.setInt(6, id);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Updated!");

                loadData();

                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // DELETE
        btnDelete.addActionListener(e -> {
            try {
                int row = table.getSelectedRow();
                int id = Integer.parseInt(model.getValueAt(row, 0).toString());

                Connection con = DBConnection.getConnection();

                String sql = "DELETE FROM donations WHERE id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Deleted!");

                loadData();

                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        frame.setVisible(true);
    }

    // CREATE TABLE
    public static void createTable() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            st.execute("CREATE TABLE IF NOT EXISTS donations (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(50)," +
                    "email VARCHAR(50)," +
                    "mobile VARCHAR(15)," +
                    "donation DOUBLE," +
                    "payment_method VARCHAR(20)," +
                    "payment_status VARCHAR(20))");

            DatabaseMetaData meta = con.getMetaData();
            addColumnIfMissing(meta, con, "donations", "payment_method", "VARCHAR(20)");
            addColumnIfMissing(meta, con, "donations", "payment_status", "VARCHAR(20)");
            dropColumnIfExists(meta, con, "donations", "card_last4");
            dropColumnIfExists(meta, con, "donations", "expiry");
            dropColumnIfExists(meta, con, "donations", "cvv");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addColumnIfMissing(DatabaseMetaData meta, Connection con, String tableName, String columnName, String columnDefinition) throws SQLException {
        try (ResultSet rs = meta.getColumns(null, null, tableName.toUpperCase(), columnName.toUpperCase())) {
            if (!rs.next()) {
                try (Statement st = con.createStatement()) {
                    st.execute("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + columnDefinition);
                }
            }
        }
    }

    private static void dropColumnIfExists(DatabaseMetaData meta, Connection con, String tableName, String columnName) throws SQLException {
        try (ResultSet rs = meta.getColumns(null, null, tableName.toUpperCase(), columnName.toUpperCase())) {
            if (rs.next()) {
                try (Statement st = con.createStatement()) {
                    st.execute("ALTER TABLE " + tableName + " DROP COLUMN " + columnName);
                }
            }
        }
    }

    // LOAD DATA
    public static void loadData() {
        try {
            model.setRowCount(0);

            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM donations");

            double total = 0;

            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("mobile"),
                        rs.getDouble("donation"),
                        rs.getString("payment_method"),
                        rs.getString("payment_status")
                });

                total += rs.getDouble("donation");
            }

            totalLabel.setText("Total Donation: " + total);

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean processPayment(JFrame parent, double amount) {
        String message = "Scan the QR code to pay ₹" + String.format("%.2f", amount) + " and click OK when payment is complete.";

        int result = JOptionPane.showConfirmDialog(
                parent,
                message,
                "Payment Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (result != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(parent, "Payment cancelled. Data was not saved.", "Payment Cancelled", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        JOptionPane.showMessageDialog(parent, "Payment successful! Saving your data now.", "Payment Success", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    private static class QRPanel extends JPanel {
        private BufferedImage qrImage;
        private double amount = 0;

        public QRPanel() {
            try {
                File file = new File("qr.png");
                if (file.exists()) {
                    qrImage = ImageIO.read(file);
                }
            } catch (Exception ignored) {
            }
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int w = getWidth();
            int h = getHeight();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, w, h);

            if (qrImage != null) {
                int imgWidth = Math.min(qrImage.getWidth(), w - 20);
                int imgHeight = Math.min(qrImage.getHeight(), h - 40);
                g.drawImage(qrImage, 10, 10, imgWidth, imgHeight, this);
                g.setColor(Color.BLACK);
                g.setFont(g.getFont().deriveFont(Font.BOLD, 12f));
                g.drawString("Scan the QR code", 10, imgHeight + 30);
            } else {
                g.setColor(Color.BLACK);
                int size = Math.min(w, h) - 40;
                int x0 = 20;
                int y0 = 20;
                for (int row = 0; row < 10; row++) {
                    for (int col = 0; col < 10; col++) {
                        if ((row + col) % 2 == 0 || (row == 0 && col < 3) || (row < 3 && col == 0)) {
                            g.fillRect(x0 + col * size / 10, y0 + row * size / 10, size / 10, size / 10);
                        }
                    }
                }
                g.drawRect(x0, y0, size, size);
                g.setFont(g.getFont().deriveFont(Font.BOLD, 12f));
                g.drawString("UPI QR", x0 + 10, y0 + size + 20);
                g.setFont(g.getFont().deriveFont(Font.PLAIN, 11f));
                if (amount > 0) {
                    g.drawString("Amount: ₹" + String.format("%.2f", amount), x0 + 10, y0 + size + 35);
                } else {
                    g.drawString("7416307668", x0 + 10, y0 + size + 35);
                }
            }
        }
    }
}
