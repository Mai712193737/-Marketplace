package gui;

import model.*;
import service.SystemManager;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private final SystemManager system;

    public LoginFrame(SystemManager system) {

        this.system = system;

        setTitle("Marketplace - Login");
        setSize(520,620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(new GradientBackground());
        setLayout(new GridBagLayout());

        JPanel card = new ShadowPanel();
        card.setPreferredSize(new Dimension(400,500));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(50,40,50,40));

        JLabel welcome = new JLabel("Welcome Back 👋");
        welcome.setFont(new Font("Segoe UI",Font.BOLD,32));
        welcome.setForeground(Color.WHITE);
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Login to continue your journey");
        sub.setFont(new Font("Segoe UI",Font.PLAIN,15));
        sub.setForeground(new Color(230,230,255));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField email = new JTextField();
        JPasswordField pass = new JPasswordField();

        styleField(email);
        styleField(pass);

        addPlaceholder(email,"Enter your email");
        addPlaceholder(pass,"Enter your password");

        JButton login = createGradientButton("SIGN IN");
        JButton register = createOutlineButton("Create Account");

        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        register.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(welcome);
        card.add(Box.createVerticalStrut(10));
        card.add(sub);
        card.add(Box.createVerticalStrut(40));
        card.add(email);
        card.add(Box.createVerticalStrut(25));
        card.add(pass);
        card.add(Box.createVerticalStrut(45));
        card.add(login);
        card.add(Box.createVerticalStrut(20));
        card.add(register);

        add(card);

        // ================= LOGIC (لم يتغير) =================

        login.addActionListener(e -> {
            try {

                String emailText = email.getText().equals("Enter your email") ? "" : email.getText().trim();
                String passText = new String(pass.getPassword()).equals("Enter your password") ? "" : new String(pass.getPassword()).trim();

                User user = system.login(emailText, passText);

                if(user == null){
                    showErrorDialog("Wrong Email or Password");
                    return;
                }

                dispose();

                if(user instanceof Admin)
                    new AdminDashboard(system);
                else
                    new UserDashboard(system,user);

            } catch (Exception ex){
                showErrorDialog("Database Error:\n"+ex.getMessage());
            }
        });

        register.addActionListener(e -> {
            dispose();
            new RegisterFrame(system);
        });

        setVisible(true);
    }

    // ================= Modern Rounded Field =================

    private void styleField(JTextField field){

        field.setMaximumSize(new Dimension(Integer.MAX_VALUE,55));
        field.setOpaque(false);
        field.setFont(new Font("Segoe UI",Font.PLAIN,15));
        field.setForeground(Color.BLACK);
        field.setCaretColor(new Color(138,43,226));
        field.setBorder(new EmptyBorder(15,25,15,25));

        field.setUI(new javax.swing.plaf.basic.BasicTextFieldUI(){
            protected void paintSafely(Graphics g){
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow
                g2.setColor(new Color(0,0,0,40));
                g2.fillRoundRect(3,3,
                        field.getWidth()-6,
                        field.getHeight()-6,
                        35,35);

                // Glass background
                g2.setColor(new Color(255,255,255,240));
                g2.fillRoundRect(0,0,
                        field.getWidth()-6,
                        field.getHeight()-6,
                        35,35);

                g2.dispose();
                super.paintSafely(g);
            }
        });

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e){
                field.setCaretColor(new Color(0,191,255));
            }
            public void focusLost(FocusEvent e){
                field.setCaretColor(new Color(138,43,226));
            }
        });
    }

    // ================= Placeholder =================

    private void addPlaceholder(JTextField field, String text){
        field.setText(text);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e){
                if(field.getText().equals(text)){
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e){
                if(field.getText().isEmpty()){
                    field.setText(text);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    // ================= Ultra Modern Error Dialog =================

    private void showErrorDialog(String message){

        JDialog dialog = new JDialog(this,true);
        dialog.setSize(420,230);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0,0,0,0));

        JPanel panel = new JPanel(){
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0,0,0,100));
                g2.fillRoundRect(8,8,getWidth()-16,getHeight()-16,30,30);

                g2.setColor(new Color(35,35,55));
                g2.fillRoundRect(0,0,getWidth()-16,getHeight()-16,30,30);

                g2.dispose();
            }
        };

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30,30,30,30));
        panel.setOpaque(false);

        JLabel icon = new JLabel("⚠");
        icon.setFont(new Font("Segoe UI Emoji",Font.PLAIN,40));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        icon.setForeground(new Color(255,90,90));

        JLabel title = new JLabel("Login Failed");
        title.setFont(new Font("Segoe UI",Font.BOLD,20));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel msg = new JLabel("<html><center>"+message+"</center></html>");
        msg.setFont(new Font("Segoe UI",Font.PLAIN,14));
        msg.setForeground(new Color(220,220,255));
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton ok = new JButton("Understood"){
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0,0,new Color(255,90,90),
                        getWidth(),getHeight(),new Color(255,0,120)
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),25,25);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        ok.setContentAreaFilled(false);
        ok.setFocusPainted(false);
        ok.setForeground(Color.WHITE);
        ok.setFont(new Font("Segoe UI",Font.BOLD,14));
        ok.setBorder(new EmptyBorder(10,25,10,25));
        ok.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ok.setAlignmentX(Component.CENTER_ALIGNMENT);
        ok.addActionListener(e -> dialog.dispose());

        panel.add(icon);
        panel.add(Box.createVerticalStrut(10));
        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(msg);
        panel.add(Box.createVerticalStrut(20));
        panel.add(ok);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ================= Gradient Button =================

    private JButton createGradientButton(String text){
        JButton btn = new JButton(text){
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0,0,new Color(138,43,226),
                        getWidth(),getHeight(),new Color(0,191,255)
                );

                g2.setPaint(gp);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),30,30);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI",Font.BOLD,16));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE,50));
        btn.setBorder(new EmptyBorder(12,20,12,20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    // ================= Outline Button =================

    private JButton createOutlineButton(String text){
        JButton btn = new JButton(text){
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(255,255,255,30));
                g2.fillRoundRect(0,0,getWidth(),getHeight(),30,30);

                g2.setColor(Color.WHITE);
                g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,30,30);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI",Font.BOLD,14));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE,45));
        btn.setBorder(new EmptyBorder(10,20,10,20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    // ================= Gradient Background =================

    class GradientBackground extends JPanel {
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(
                    0,0,new Color(75,0,130),
                    getWidth(),getHeight(),new Color(0,102,204)
            );
            g2.setPaint(gp);
            g2.fillRect(0,0,getWidth(),getHeight());
        }
    }

    // ================= Shadow Panel =================

    class ShadowPanel extends JPanel {
        protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(0,0,0,120));
            g2.fillRoundRect(10,10,getWidth()-20,getHeight()-20,40,40);

            g2.setColor(new Color(255,255,255,40));
            g2.fillRoundRect(0,0,getWidth()-20,getHeight()-20,40,40);
        }
    }
}
