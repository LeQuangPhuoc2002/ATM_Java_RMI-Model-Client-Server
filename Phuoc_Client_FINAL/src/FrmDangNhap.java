
import com.sun.glass.events.KeyEvent;
import java.util.ArrayList;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
public class FrmDangNhap extends javax.swing.JFrame {

    private JTextField selectedTextField;

    SharedVariables sharedVariables = new SharedVariables();

    public static int demChuoiTrongArrayList(ArrayList<String> list, String chuoi) {
        int count = 0;
        for (String phanTu : list) {
            if (phanTu.equals(chuoi)) {
                count++;
            }
        }
        return count;
    }

    long taikhoan = 0;

    public void run() {
        try {
            selectedTextField = null;
            String a = tk.getText();
            if (!a.isEmpty()) {
                taikhoan = Long.parseLong(a);
            }
            String b = mk.getText();
            ITinhToan server = ServerUtility.getServerInstance();
            if (mk.isEnabled() && (a.isEmpty() || b.isEmpty())) {
                txtloi.setText("Vui lòng nhập đầy đủ tài khoản và mật khẩu");
                tk.requestFocusInWindow();
            } else if (server.GetStatus(taikhoan) == -1) {
                txtloi.setText("Tài khoản " + taikhoan + " đã bị khóa");
                mk.setEnabled(false);
                mk.setText("");
            } else {
                if (!b.isEmpty()) {
                    long pin = Long.parseLong(b);
                    int login = server.KTDangNhap(taikhoan, pin);
                    switch (login) {
                        case 0:
                            SharedVariables.stk = taikhoan;
                            SharedVariables.tendn = server.timKiemNguoiNhan(taikhoan);
                            FrmDangNhap.this.setVisible(false);
                            FrmClient f = new FrmClient();
                            f.setVisible(true);
                            break;
                        case 1: // đang được sử dụng
                            txtloi.setText("Tài khoản " + taikhoan + " đang được sử dụng");

                            break;
                        case -1: // đã bị khóa
                            txtloi.setText("Tài khoản " + taikhoan + " đã bị khóa");
                            server.SetStatus(taikhoan, -1);
                            mk.setEnabled(false);
                            mk.setText("");
                            break;
                        case -2:
                            sharedVariables.saiMaPin.add(String.valueOf(taikhoan));
                            if (demChuoiTrongArrayList(sharedVariables.saiMaPin, String.valueOf(taikhoan)) >= 3) {
                                txtloi.setText("Nhập sai mã PIN 3 lần, tài khoản đã bị khóa");
                                mk.setText("");
                                mk.setEnabled(false);
                                server.SetStatus(taikhoan, -1);
                                break;
                            } else {
                                txtloi.setText("Sai mã PIN " + demChuoiTrongArrayList(sharedVariables.saiMaPin, String.valueOf(taikhoan)) + " lần");
                                mk.setText("");
                                mk.requestFocusInWindow();
                                break;
                            }
                        case -3:
                            txtloi.setText("Lỗi cơ sở dữ liệu");
                            break;
                        case -4:
                            txtloi.setText("Tài khoản không tồn tại");
                            break;
                    }
                } else {
                    if (server.timKiemNguoiNhan(taikhoan) == null) {
                        txtloi.setText("Tài khoản không tồn tại");
                        tk.requestFocusInWindow();
                    } else if (server.timKiemNguoiNhan(taikhoan) != null) {
                        txtloi.setText("");
                    }
                }
            }
        } catch (Exception tt) {
            tt.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtError = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        mk = new javax.swing.JPasswordField();
        tk = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtloi = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        dn1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        txtError.setFont(new java.awt.Font("Times New Roman", 2, 24)); // NOI18N
        txtError.setForeground(new java.awt.Color(255, 51, 51));

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 2, 36)); // NOI18N
        jLabel1.setText("Số tài khoản:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 2, 36)); // NOI18N
        jLabel2.setText("Mã PIN:");

        mk.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        mk.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mkFocusGained(evt);
            }
        });
        mk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mkActionPerformed(evt);
            }
        });
        mk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mkKeyPressed(evt);
            }
        });

        tk.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tk.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tkFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tkFocusLost(evt);
            }
        });
        tk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tkKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 102, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SP BANK");

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("ĐĂNG NHẬP >");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        txtloi.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        txtloi.setForeground(new java.awt.Color(255, 0, 0));
        txtloi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtloi.setText("txtloi");

        jLabel4.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        jLabel4.setText("Thông báo hệ thống:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(66, 66, 66)
                                        .addComponent(jLabel2))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tk)
                                    .addComponent(mk, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtloi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                        .addGap(42, 42, 42))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tk, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtloi)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("8");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setText("7");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setText("9");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton5.setText("5");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton6.setText("4");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton7.setText("2");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton8.setText("CANCEL");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton9.setText("1");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton10.setText("0");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton11.setText("3");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton12.setText("6");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton13.setText("CLEAR");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton14.setText("ENTER");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        dn1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        dn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dn1ActionPerformed(evt);
            }
        });

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dn1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtError)
                        .addGap(398, 398, 398)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(396, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168)
                .addComponent(txtError)
                .addGap(127, 127, 127))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(dn1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public FrmDangNhap() {
        initComponents();
    }
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        txtloi.setText("");
        tk.requestFocusInWindow();

    }//GEN-LAST:event_formWindowOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (selectedTextField != null && selectedTextField == tk) {
            // Thêm số 9 vào nội dung hiện tại của ô văn bản được chọn
            String currentText = selectedTextField.getText();
            if (currentText.length() < 8) {
                selectedTextField.setText(currentText + "8");
            }
        } else if (selectedTextField != null && selectedTextField == mk) {
            String currentText = selectedTextField.getText();
            if (currentText.length() < 6) {
                selectedTextField.setText(currentText + "8");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (selectedTextField != null && selectedTextField == tk) {
            // Thêm số 9 vào nội dung hiện tại của ô văn bản được chọn
            String currentText = selectedTextField.getText();
            if (currentText.length() < 8) {
                selectedTextField.setText(currentText + "7");
            }
        } else if (selectedTextField != null && selectedTextField == mk) {
            String currentText = selectedTextField.getText();
            if (currentText.length() < 6) {
                selectedTextField.setText(currentText + "7");
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (selectedTextField != null && selectedTextField == tk) {
            // Thêm số 9 vào nội dung hiện tại của ô văn bản được chọn
            String currentText = selectedTextField.getText();
            if (currentText.length() < 8) {
                selectedTextField.setText(currentText + "9");
            }
        } else if (selectedTextField != null && selectedTextField == mk) {
            String currentText = selectedTextField.getText();
            if (currentText.length() < 6) {
                selectedTextField.setText(currentText + "9");
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (selectedTextField != null && selectedTextField == tk) {
            // Thêm số 9 vào nội dung hiện tại của ô văn bản được chọn
            String currentText = selectedTextField.getText();
            if (currentText.length() < 8) {
                selectedTextField.setText(currentText + "5");
            }
        } else if (selectedTextField != null && selectedTextField == mk) {
            String currentText = selectedTextField.getText();
            if (currentText.length() < 6) {
                selectedTextField.setText(currentText + "5");
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (selectedTextField != null && selectedTextField == tk) {
            // Thêm số 9 vào nội dung hiện tại của ô văn bản được chọn
            String currentText = selectedTextField.getText();
            if (currentText.length() < 8) {
                selectedTextField.setText(currentText + "4");
            }
        } else if (selectedTextField != null && selectedTextField == mk) {
            String currentText = selectedTextField.getText();
            if (currentText.length() < 6) {
                selectedTextField.setText(currentText + "4");
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (selectedTextField != null && selectedTextField == tk) {
            // Thêm số 9 vào nội dung hiện tại của ô văn bản được chọn
            String currentText = selectedTextField.getText();
            if (currentText.length() < 8) {
                selectedTextField.setText(currentText + "2");
            }
        } else if (selectedTextField != null && selectedTextField == mk) {
            String currentText = selectedTextField.getText();
            if (currentText.length() < 6) {
                selectedTextField.setText(currentText + "2");
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        try {
            SharedVariables.tendn = "";
            ITinhToan server = ServerUtility.getServerInstance();
            server.DangXuat(SharedVariables.stk);
            SharedVariables.stk = 0;
            this.dispose();//hủy Frame: FrmDangNhap
            FrmDangNhap f = new FrmDangNhap();
            f.setVisible(true);//Mở form: drmClient
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (selectedTextField != null && selectedTextField == tk) {
            // Thêm số 9 vào nội dung hiện tại của ô văn bản được chọn
            String currentText = selectedTextField.getText();
            if (currentText.length() < 8) {
                selectedTextField.setText(currentText + "1");
            }
        } else if (selectedTextField != null && selectedTextField == mk) {
            String currentText = selectedTextField.getText();
            if (currentText.length() < 6) {
                selectedTextField.setText(currentText + "1");
            }
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (selectedTextField != null && selectedTextField == tk) {
            // Thêm số 9 vào nội dung hiện tại của ô văn bản được chọn
            String currentText = selectedTextField.getText();
            if (currentText.length() < 8) {
                selectedTextField.setText(currentText + "0");
            }
        } else if (selectedTextField != null && selectedTextField == mk) {
            String currentText = selectedTextField.getText();
            if (currentText.length() < 6) {
                selectedTextField.setText(currentText + "0");
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (selectedTextField != null && selectedTextField == tk) {
            // Thêm số 9 vào nội dung hiện tại của ô văn bản được chọn
            String currentText = selectedTextField.getText();
            if (currentText.length() < 8) {
                selectedTextField.setText(currentText + "3");
            }
        } else if (selectedTextField != null && selectedTextField == mk) {
            String currentText = selectedTextField.getText();
            if (currentText.length() < 6) {
                selectedTextField.setText(currentText + "3");
            }
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (selectedTextField != null && selectedTextField == tk) {
            // Thêm số 9 vào nội dung hiện tại của ô văn bản được chọn
            String currentText = selectedTextField.getText();
            if (currentText.length() < 8) {
                selectedTextField.setText(currentText + "6");
            }
        } else if (selectedTextField != null && selectedTextField == mk) {
            String currentText = selectedTextField.getText();
            if (currentText.length() < 6) {
                selectedTextField.setText(currentText + "6");
            }
        }

    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        if (selectedTextField != null) {
            // Lấy văn bản hiện tại từ JTextField
            String currentText = selectedTextField.getText();

            // Xóa một ký tự cuối cùng từ chuỗi
            if (currentText.length() > 0) {
                currentText = currentText.substring(0, currentText.length() - 1);
            }

            // Cập nhật JTextField với chuỗi mới
            selectedTextField.setText(currentText);
        }

    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        if (selectedTextField == tk && mk.isEnabled()) {
            mk.requestFocusInWindow();
            mk.setText("");
        } else {
            tk.requestFocusInWindow();
            tk.setText("");
            txtloi.setText("");
            mk.setEnabled(true);
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void dn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dn1ActionPerformed
        // TODO add your handling code here:
        try {
            if(mk.getText().length() < 6) {
                txtloi.setText("Mã PIN phải gồm 6 chữ số");
                mk.requestFocusInWindow();
                selectedTextField = mk;
            } else {
                run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_dn1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tkKeyPressed
        // TODO add your handling code here:
        String matkhau = String.valueOf(tk.getText());
        int lengthMatKhau = matkhau.length();
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            if (lengthMatKhau < 8) {
                tk.setEditable(true);
            } else {
                tk.setEditable(false);
            }
        } else {
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACKSPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                tk.setEditable(true);
            } else {
                tk.setEditable(false);
            }
        }
    }//GEN-LAST:event_tkKeyPressed

    private void tkFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tkFocusLost
        // TODO add your handling code here:
        //        try {
        //            run();
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
    }//GEN-LAST:event_tkFocusLost

    private void tkFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tkFocusGained
        // TODO add your handling code here:

        selectedTextField = tk;
        mk.setText("");
    }//GEN-LAST:event_tkFocusGained

    private void mkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mkKeyPressed
        // TODO add your handling code here:
        //        if (evt.getKeyCode() == 10) {
        //            try {
        //                run();
        //            } catch (Exception tt) {
        //                tt.printStackTrace();
        //            }
        //        }
        String PIN = String.valueOf(mk.getPassword());
        int lengthPIN = PIN.length();
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            if (lengthPIN < 6) {
                mk.setEditable(true);
            } else {
                mk.setEditable(false);
            }
        } else {
            if (evt.getExtendedKeyCode() == KeyEvent.VK_BACKSPACE || evt.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                mk.setEditable(true);
            } else {
                mk.setEditable(false);
            }
        }
    }//GEN-LAST:event_mkKeyPressed

    private void mkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mkActionPerformed

    private void mkFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mkFocusGained
        // TODO add your handling code here:
        //txtloi.setText("");
        selectedTextField = mk;
        mk.setText("");
    }//GEN-LAST:event_mkFocusGained

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
            java.util.logging.Logger.getLogger(FrmDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmDangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmDangNhap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dn1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField mk;
    private javax.swing.JTextField tk;
    private javax.swing.JLabel txtError;
    private javax.swing.JLabel txtloi;
    // End of variables declaration//GEN-END:variables
}
