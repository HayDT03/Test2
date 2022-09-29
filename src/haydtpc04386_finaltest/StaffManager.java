/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package haydtpc04386_finaltest;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dutru
 */
public class StaffManager extends javax.swing.JFrame {

    /**
     * Creates new form ProductManager
     */
    String head[] = {"Mã nhân viên","Họ tên","Quê quán","CCCD", "Giới tính", "Tình trạng hôn nhân","Hình ảnh",  "Số điện thoại", "Lương"};
    DefaultTableModel model = new DefaultTableModel(head, 0);
    Scanner sc = new Scanner(System.in);
    int index = 0;
    static String name ="UserAdmin";
    static String pass = "123456";
    static String url = "jdbc:sqlserver://localhost:1433;databaseName=QLNV;encrypt=true;trustServerCertificate=true";
    static String srcImage = "src\\Images\\";
    public StaffManager() {
        initComponents();
        setResizable(false);
//        LoadDataToComboBox();
        initData();
        btnCapNhat.setEnabled(false);
        btnXoa.setEnabled(false);
        btnNext.setEnabled(false);
        btnPre.setEnabled(false);
        lblHinhAnhSP.setHorizontalAlignment(SwingConstants.CENTER);
        txtNameFileSP.setEnabled(false);
        txtNameFileSP.setHorizontalAlignment(SwingConstants.CENTER);
        rdoNam.setSelected(true);
    }
//    public void LoadDataToComboBox(){
//        try{
//            cboLoaiHang.removeAllItems();
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection con = DriverManager.getConnection(url, name, pass);
//            Statement st = con.createStatement();
//            String sql = "SELECT TENLOAI FROM LOAI";
//            ResultSet rs = st.executeQuery(sql);
//            String check = null;
//            while(rs.next()){
//                String s = rs.getString(1);
//                if (s.equals(check) == false){
//                    cboLoaiHang.addItem(s);
//                    check = s;
//                }
//                
//            }
//            cboThuongHieu.removeAllItems();
//            Statement st2 = con.createStatement();
//            String sql2 = "SELECT TENNHANHIEU FROM NHANHIEU";
//            ResultSet rs2 = st.executeQuery(sql2);
//            check = null;
//            while(rs2.next()){
//                String s = rs2.getString(1);
//                if (s.equals(check) == false){
//                    cboThuongHieu.addItem(s);
//                    check = s;
//                }
//                
//            }
//            con.close();
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//    }
    public void initData() {
        try {
            model.setRowCount(0);
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, name, pass);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM NHANVIEN";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(rs.getString(3));
                row.add(rs.getString(4));
                row.add(rs.getString(5));
                row.add(rs.getString(6));
                row.add(rs.getString(7));
                row.add(rs.getString(8));
                row.add(rs.getString(9));
                model.addRow(row);
            }
            tblNhanVien.setModel(model);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void fillForm(){
        int row = tblNhanVien.getSelectedRow();
        txtMaNhanVien.setText(tblNhanVien.getValueAt(row, 0).toString());
        txtHoTen.setText(tblNhanVien.getValueAt(row, 1).toString());
        txtQueQuan.setText(tblNhanVien.getValueAt(row, 2).toString());
        txtCCCD.setText(tblNhanVien.getValueAt(row, 3).toString());
        if (tblNhanVien.getValueAt(row, 4).toString().equals("Nam")){
            rdoNam.setSelected(true);
        }else if(tblNhanVien.getValueAt(row, 4).toString().equals("Nữ")){
            rdoNu.setSelected(true);
        }
        cboHonNhan.setSelectedItem(tblNhanVien.getValueAt(row, 5).toString());
        File file = new File(srcImage + tblNhanVien.getValueAt(row, 6).toString());
        ReadImages(file);
        txtSDT.setText(tblNhanVien.getValueAt(row, 7).toString());
        txtLuong.setText(tblNhanVien.getValueAt(row, 8).toString());

    }
    public void LamMoi(){
        txtMaNhanVien.setText("");
        txtHoTen.setText("");
        txtQueQuan.setText("");
        txtCCCD.setText("");
        rdoNam.setSelected(true);
        txtLuong.setText("");
        txtSDT.setText("");
        txtNameFileSP.setText("");
        lblHinhAnhSP.setIcon(null);
        lblHinhAnhSP.setText("Hình ảnh sản phẩm");
        cboHonNhan.setSelectedIndex(0);


    }
    public boolean CheckForm(){
        if (txtMaNhanVien.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên.");
            txtMaNhanVien.requestFocus();
            return false;
        }
        if (txtHoTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhân viên.");
            txtHoTen.requestFocus();
            return false;
        }

        if (txtQueQuan.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập quê quán.");
            txtQueQuan.requestFocus();
            return false;
        }  
        if (txtCCCD.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập căn cước công dân.");
            txtCCCD.requestFocus();
            return false;
        }
        if (txtSDT.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại.");
            txtSDT.requestFocus();
            return false;
        }
        if (txtLuong.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lương.");
            txtCCCD.requestFocus();
            return false;
        }
        try {
            Double.parseDouble(txtLuong.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lương là số !!!");
            txtLuong.requestFocus();
            return false;
        }

//        if (txtThuongHieu.getText().equals("")) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập thương hiệu.");
//            txtThuongHieu.requestFocus();
//            return false;
//        }
//        if (txtLoaiHang.getText().equals("")) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập loại hàng.");
//            txtLoaiHang.requestFocus();
//            return false;
//        }
//        if (txtXuatXu.getText().equals("")) {
//            JOptionPane.showMessageDialog(this, "Vui lòng nhập xuất xứ.");
//            txtXuatXu.requestFocus();
//            return false;
//        }

        return true;
    }
    public boolean CheckDuplicate(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, name, pass);
            Statement st = con.createStatement();
            String sql = "SELECT MSNV FROM NHANVIEN";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                if(txtMaNhanVien.getText().equals(rs.getString(1))){
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public void ThemSP() {
        if(CheckDuplicate() && CheckForm()){
            CapNhatSP();
        }
        else if (CheckForm()) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url, name, pass);
                String sql = "insert into NHANVIEN values(?,?,?,?,?,?,?,?,?)";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, txtMaNhanVien.getText());
                st.setString(2, txtHoTen.getText());
                st.setString(3, txtQueQuan.getText());
                st.setString(4, txtCCCD.getText());
                if (rdoNam.isSelected()){
                    st.setString(5, "Nam");
                }else if(rdoNu.isSelected()){
                    st.setString(5, "Nữ");
                }
                st.setString(6, cboHonNhan.getSelectedItem().toString());
                st.setString(7, txtNameFileSP.getText());
                st.setString(8, txtSDT.getText());
                st.setString(9, txtLuong.getText());
                st.executeUpdate();
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                con.close();
                initData();
                LamMoi();
            } catch (Exception ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(this, "Lỗi!!!");
                ex.printStackTrace();
            }
        }
    }
    public void CapNhatSP(){
        if (CheckForm()) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url, name, pass);   
                String sql = "update NHANVIEN SET HOTEN = ?,  QUEQUAN= ?, CCCD = ?, GIOITINH = ?, HONNHAN = ?, HINHANH = ?, SODT= ?, LUONG = ? WHERE MSNV = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, txtHoTen.getText());
                st.setString(2, txtQueQuan.getText());
                st.setString(3, txtCCCD.getText());
                if (rdoNam.isSelected()){
                    st.setString(4, "Nam");
                }else if(rdoNu.isSelected()){
                    st.setString(4, "Nữ");
                }
                st.setString(5, cboHonNhan.getSelectedItem().toString());
                st.setString(6, txtNameFileSP.getText());
                st.setString(7, txtSDT.getText());
                st.setString(8, txtLuong.getText());
                st.setString(9, txtMaNhanVien.getText());
                st.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cập nhật dữ liệu thành công");
                con.close();
                initData();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(this, "Lỗi");
            }
        }
    }
    public void XoaSP(){
        int choose = (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa chứ ?", "", JOptionPane.YES_NO_OPTION));
        if (choose == JOptionPane.YES_OPTION) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url, name, pass);
                String sql = "delete from NHANVIEN where MSNV = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, txtMaNhanVien.getText());
                st.execute();
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                con.close();
                initData();
                LamMoi();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(this, "Lỗi");
            }
        } else
            return;
    }
    public void TimKiemSP(){
            try {
            model.setRowCount(0);
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, name, pass);
            String sql = "select * from NHANVIEN where GIOITINH = ?";
            PreparedStatement st = con.prepareStatement(sql);
            if (rdoNam.isSelected() == true) {
                st.setString(1, "Nam");
            } else if (rdoNu.isSelected() == true) {
                st.setString(1, "Nữ");
            }
            st.execute();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(rs.getString(3));
                row.add(rs.getString(4));
                row.add(rs.getString(5));
                row.add(rs.getString(6));
                row.add(rs.getString(7));
                row.add(rs.getString(8));
                row.add(rs.getString(9));
                model.addRow(row);
            }
            tblNhanVien.setModel(model);
            con.close();

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Lỗi");
        }
    }
    public void ReadImages(File path){
        try {
            Image img = ImageIO.read(path);
            lblHinhAnhSP.setText("");
            int width = lblHinhAnhSP.getWidth();
            int height = lblHinhAnhSP.getHeight();
            lblHinhAnhSP.setIcon(new ImageIcon(img.getScaledInstance(width, height, 0)));
            txtNameFileSP.setText(path.getName());
        } catch (IOException ex) {
            Logger.getLogger(StaffManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void SapXepTheoLuongGiam(){
         try {
            model.setRowCount(0);
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, name, pass);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM NHANVIEN ORDER BY LUONG DESC";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(rs.getString(3));
                row.add(rs.getString(4));
                row.add(rs.getString(5));
                row.add(rs.getString(6));
                row.add(rs.getString(7));
                row.add(rs.getString(8));
                row.add(rs.getString(9));
                model.addRow(row);
            }
            tblNhanVien.setModel(model);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void SapXepTheoLuongTang(){
         try {
            model.setRowCount(0);
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, name, pass);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM NHANVIEN ORDER BY LUONG  ASC";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(rs.getString(3));
                row.add(rs.getString(4));
                row.add(rs.getString(5));
                row.add(rs.getString(6));
                row.add(rs.getString(7));
                row.add(rs.getString(8));
                row.add(rs.getString(9));
                model.addRow(row);
            }
            tblNhanVien.setModel(model);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public String viTriBanGhi() {
        int viTri = tblNhanVien.getSelectedRow();
        return (viTri + 1) + " Trên " + (tblNhanVien.getRowCount());
    }
    public void firstSP() {
        index = 0;
        tblNhanVien.setRowSelectionInterval(index, index);
        fillForm();
        lblBanGhi.setText(viTriBanGhi());
        btnXoa.setEnabled(true);
        btnNext.setEnabled(true);
        btnPre.setEnabled(true);
        btnCapNhat.setEnabled(true);
    }

    public void preSP() {
        if (tblNhanVien.getRowCount() != 0) {
            index = tblNhanVien.getSelectedRow();
            if (index == 0) {
                lastSP();
            } else {
                index--;
            }
            tblNhanVien.setRowSelectionInterval(index, index);
            fillForm();
            lblBanGhi.setText(viTriBanGhi());
        }
    }

    public void lastSP() {
        index = tblNhanVien.getRowCount() - 1;
        tblNhanVien.setRowSelectionInterval(index, index);
        fillForm();
        lblBanGhi.setText(viTriBanGhi());
        btnXoa.setEnabled(true);
        btnNext.setEnabled(true);
        btnPre.setEnabled(true);
        btnCapNhat.setEnabled(true);

    }

    public void nextSP() {
        if (tblNhanVien.getRowCount() != 0) {
            index = tblNhanVien.getSelectedRow();
            if (index == tblNhanVien.getRowCount() - 1) {
                firstSP();
            } else {
                index++;
            }
            tblNhanVien.setRowSelectionInterval(index, index);
            fillForm();
            lblBanGhi.setText(viTriBanGhi());
        }

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        txtQueQuan = new javax.swing.JTextField();
        txtCCCD = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnFirst1 = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        lblBanGhi = new javax.swing.JLabel();
        btnPre = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnSapXepGiam = new javax.swing.JButton();
        btnMacDinh = new javax.swing.JButton();
        btnSapXepTang = new javax.swing.JButton();
        btnQuayLai = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblHinhAnhSP = new javax.swing.JLabel();
        btnChonAnhSP = new javax.swing.JButton();
        txtNameFileSP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtLuong = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cboHonNhan = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Quản lý nhân viên");

        jLabel2.setText("Mã nhân viên:");

        txtMaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNhanVienActionPerformed(evt);
            }
        });

        jLabel3.setText("Họ tên:");

        jLabel4.setText("Quê quán:");

        jLabel5.setText("CCCD:");

        txtCCCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCCCDActionPerformed(evt);
            }
        });

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        jPanel2.setLayout(null);

        btnFirst1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnFirst1.setText("|<");
        btnFirst1.setPreferredSize(new java.awt.Dimension(55, 21));
        btnFirst1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirst1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnFirst1);
        btnFirst1.setBounds(0, 1, 47, 30);

        btnNext.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnNext.setText(">>");
        btnNext.setPreferredSize(new java.awt.Dimension(55, 21));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        jPanel2.add(btnNext);
        btnNext.setBounds(190, 0, 51, 30);

        lblBanGhi.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lblBanGhi.setForeground(new java.awt.Color(0, 0, 255));
        lblBanGhi.setText("0 Trên 0");
        jPanel2.add(lblBanGhi);
        lblBanGhi.setBounds(110, 10, 64, 17);

        btnPre.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnPre.setText("<<");
        btnPre.setPreferredSize(new java.awt.Dimension(55, 21));
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });
        jPanel2.add(btnPre);
        btnPre.setBounds(50, 0, 51, 30);

        btnLast.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnLast.setText(">|");
        btnLast.setPreferredSize(new java.awt.Dimension(55, 21));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        jPanel2.add(btnLast);
        btnLast.setBounds(250, 0, 47, 30);

        btnSapXepGiam.setText("Giá giảm dần");
        btnSapXepGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSapXepGiamActionPerformed(evt);
            }
        });
        jPanel2.add(btnSapXepGiam);
        btnSapXepGiam.setBounds(310, 0, 100, 30);

        btnMacDinh.setText("Mặc định");
        btnMacDinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMacDinhActionPerformed(evt);
            }
        });
        jPanel2.add(btnMacDinh);
        btnMacDinh.setBounds(530, 0, 100, 30);

        btnSapXepTang.setText("Giá tăng dần");
        btnSapXepTang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSapXepTangActionPerformed(evt);
            }
        });
        jPanel2.add(btnSapXepTang);
        btnSapXepTang.setBounds(420, 0, 100, 30);

        btnQuayLai.setText("Quay lại đăng nhập");
        btnQuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLaiActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblHinhAnhSP.setText("Hình ảnh sản phẩm");

        btnChonAnhSP.setText("Chọn hình ảnh");
        btnChonAnhSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonAnhSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHinhAnhSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnChonAnhSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNameFileSP))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHinhAnhSP, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(txtNameFileSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChonAnhSP)
                .addGap(10, 10, 10))
        );

        jLabel7.setText("Giới tính:");

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel8.setText("Số điện thoại:");

        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });

        jLabel9.setText("Lương:");

        txtLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLuongActionPerformed(evt);
            }
        });

        jLabel10.setText("Tình trạng hôn nhân:");

        cboHonNhan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Độc thân", "Đã kết hôn", "Đã ly hôn" }));

        jPanel1.setLayout(null);

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel1.add(btnThem);
        btnThem.setBounds(10, 0, 110, 33);

        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Accept.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        jPanel1.add(btnCapNhat);
        btnCapNhat.setBounds(10, 40, 110, 33);

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Search.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        jPanel1.add(btnTimKiem);
        btnTimKiem.setBounds(10, 80, 110, 33);

        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Refresh.png"))); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });
        jPanel1.add(btnLamMoi);
        btnLamMoi.setBounds(10, 160, 110, 33);

        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Exit.png"))); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });
        jPanel1.add(btnThoat);
        btnThoat.setBounds(10, 200, 110, 33);

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel1.add(btnXoa);
        btnXoa.setBounds(10, 120, 110, 33);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(310, 310, 310)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnQuayLai))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel9))
                                        .addGap(42, 42, 42))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rdoNam)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoNu))
                                    .addComponent(cboHonNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtQueQuan, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtQueQuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdoNam)
                                    .addComponent(rdoNu)
                                    .addComponent(jLabel7))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(cboHonNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQuayLai)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnQuayLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuayLaiActionPerformed
        setVisible(false);
        LoginFrame mf = new LoginFrame();
        mf.setVisible(true);
    }//GEN-LAST:event_btnQuayLaiActionPerformed

    private void btnFirst1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirst1ActionPerformed
                firstSP();
    }//GEN-LAST:event_btnFirst1ActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
                preSP();
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
                nextSP();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
                lastSP();
    }//GEN-LAST:event_btnLastActionPerformed

    private void txtMaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNhanVienActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        fillForm();
        lblBanGhi.setText(viTriBanGhi());
        btnCapNhat.setEnabled(true);
        btnXoa.setEnabled(true);
        btnNext.setEnabled(true);
        btnPre.setEnabled(true);
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnChonAnhSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonAnhSPActionPerformed
        try {
            JFileChooser jfc = new JFileChooser(srcImage);
            jfc.showOpenDialog(null);
            File file = jfc.getSelectedFile();
            ReadImages(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnChonAnhSPActionPerformed

    private void txtCCCDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCCCDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCCCDActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void txtLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLuongActionPerformed

    private void btnMacDinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMacDinhActionPerformed
        initData();
    }//GEN-LAST:event_btnMacDinhActionPerformed

    private void btnSapXepGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSapXepGiamActionPerformed
        SapXepTheoLuongGiam();
    }//GEN-LAST:event_btnSapXepGiamActionPerformed

    private void btnSapXepTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSapXepTangActionPerformed
       SapXepTheoLuongTang();
    }//GEN-LAST:event_btnSapXepTangActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        ThemSP();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        CapNhatSP();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        TimKiemSP();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        LamMoi();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        int comfirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn thoát chứ ?");
        if (comfirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Cảm ơn bạn đã sử dụng dịch vụ.");
            System.exit(0);
        }
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        XoaSP();
    }//GEN-LAST:event_btnXoaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StaffManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StaffManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StaffManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StaffManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StaffManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnChonAnhSP;
    private javax.swing.JButton btnFirst1;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMacDinh;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnQuayLai;
    private javax.swing.JButton btnSapXepGiam;
    private javax.swing.JButton btnSapXepTang;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboHonNhan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBanGhi;
    private javax.swing.JLabel lblHinhAnhSP;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtNameFileSP;
    private javax.swing.JTextField txtQueQuan;
    private javax.swing.JTextField txtSDT;
    // End of variables declaration//GEN-END:variables
}
