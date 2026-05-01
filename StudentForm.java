import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class StudentForm {

    static DefaultTableModel model;
    static JTable table;
    static JLabel totalLabel;

    public static void main(String[] args) {

        createTable(); // 🔥 create new table

        JFrame frame = new JFrame("Donation System");
        frame.setSize(700, 500);
        frame.setLayout(null);

        JLabel l1 = new JLabel("Name");
        l1.setBounds(50, 20, 100, 25);
        frame.add(l1);

        JTextField txtName = new JTextField();
        txtName.setBounds(150, 20, 200, 25);
        frame.add(txtName);

        JLabel l2 = new JLabel("Email");
        l2.setBounds(50, 60, 100, 25);
        frame.add(l2);

        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(150, 60, 200, 25);
        frame.add(txtEmail);

        JLabel l3 = new JLabel("Mobile");
        l3.setBounds(50, 100, 100, 25);
        frame.add(l3);

        JTextField txtMobile = new JTextField();
        txtMobile.setBounds(150, 100, 200, 25);
        frame.add(txtMobile);

        JLabel l4 = new JLabel("Donation");
        l4.setBounds(50, 140, 100, 25);
        frame.add(l4);

        JTextField txtDonation = new JTextField();
        txtDonation.setBounds(150, 140, 200, 25);
        frame.add(txtDonation);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(50, 180, 100, 30);
        frame.add(btnAdd);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(160, 180, 100, 30);
        frame.add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(270, 180, 100, 30);
        frame.add(btnDelete);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Email");
        model.addColumn("Mobile");
        model.addColumn("Donation");

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 230, 600, 150);
        frame.add(sp);

        totalLabel = new JLabel("Total Donation: 0");
        totalLabel.setBounds(50, 400, 300, 25);
        frame.add(totalLabel);

        loadData();

        // ADD
        btnAdd.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();

                String sql = "INSERT INTO donations(name,email,mobile,donation) VALUES(?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, txtName.getText());
                ps.setString(2, txtEmail.getText());
                ps.setString(3, txtMobile.getText());
                ps.setDouble(4, Double.parseDouble(txtDonation.getText()));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Inserted!");

                loadData();

                txtName.setText("");
                txtEmail.setText("");
                txtMobile.setText("");
                txtDonation.setText("");

                con.close();

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

                String sql = "UPDATE donations SET name=?,email=?,mobile=?,donation=? WHERE id=?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, txtName.getText());
                ps.setString(2, txtEmail.getText());
                ps.setString(3, txtMobile.getText());
                ps.setDouble(4, Double.parseDouble(txtDonation.getText()));
                ps.setInt(5, id);

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
                    "donation DOUBLE)");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
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
                        rs.getDouble("donation")
                });

                total += rs.getDouble("donation");
            }

            totalLabel.setText("Total Donation: " + total);

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}