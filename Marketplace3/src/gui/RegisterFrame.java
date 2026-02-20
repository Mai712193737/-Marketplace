package gui;

import model.*;
import service.SystemManager;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class RegisterFrame extends JFrame {

    private final SystemManager system;

    public RegisterFrame(SystemManager system){

        this.system = system;

        setTitle("Marketplace - Register");
        setSize(520,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(new GradientBackground());
        setLayout(new GridBagLayout());

        JPanel card = new ShadowPanel();
        card.setPreferredSize(new Dimension(420,560));
        card.setLayout(new BoxLayout(card,BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(40,40,40,40));

        JLabel title = new JLabel("Create Account 🚀");
        title.setFont(new Font("Segoe UI",Font.BOLD,28));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JPasswordField pass = new JPasswordField();

        styleField(name);
        styleField(email);
        styleField(pass);

        addPlaceholder(name,"Enter your full name");
        addPlaceholder(email,"Enter your email");
        addPlaceholder(pass,"Create a strong password");

        String[] roles = {"SELLER","CUSTOMER"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        roleBox.setMaximumSize(new Dimension(Integer.MAX_VALUE,50));
        roleBox.setBackground(Color.WHITE);

        JButton register = createGradientButton("CREATE ACCOUNT");
        JButton back = createOutlineButton("Back to Login");

        register.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(40));
        card.add(name);
        card.add(Box.createVerticalStrut(20));
        card.add(email);
        card.add(Box.createVerticalStrut(20));
        card.add(pass);
        card.add(Box.createVerticalStrut(20));
        card.add(roleBox);
        card.add(Box.createVerticalStrut(40));
        card.add(register);
        card.add(Box.createVerticalStrut(15));
        card.add(back);

        add(card);

        // ================= Validation Logic =================

        register.addActionListener(e -> {

            String nameText = name.getText().trim();
            String emailText = email.getText().trim();
            String passText = new String(pass.getPassword()).trim();

            if(nameText.isEmpty() || nameText.equals("Enter your full name")){
                showError("Please enter your name first");
                name.requestFocus();
                return;
            }

            if(emailText.isEmpty() || emailText.equals("Enter your email")){
                showError("Email is required");
                email.requestFocus();
                return;
            }

            if(!isValidEmail(emailText)){
                showError("Please enter a valid email address");
                email.requestFocus();
                return;
            }

            if(passText.isEmpty() || passText.equals("Create a strong password")){
                showError("Password is required");
                pass.requestFocus();
                return;
            }

            if(passText.length() < 6){
                showError("Password must be at least 6 characters");
                pass.requestFocus();
                return;
            }

            try{

                User user;

                if(roleBox.getSelectedItem().equals("SELLER"))
                    user = new Seller(nameText,emailText,passText);
                else
                    user = new Customer(nameText,emailText,passText);

                system.register(user);

                showSuccess("Account Created Successfully 🎉");
                dispose();
                new LoginFrame(system);

            }catch(Exception ex){
                showError(ex.getMessage());
            }
        });

        back.addActionListener(e -> {
            dispose();
            new LoginFrame(system);
        });

        setVisible(true);
    }

    // ================= Email Validation =================

    private boolean isValidEmail(String email){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(regex,email);
    }

    // ================= Modern Field =================

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

                g2.setColor(new Color(0,0,0,40));
                g2.fillRoundRect(3,3,
                        field.getWidth()-6,
                        field.getHeight()-6,
                        35,35);

                g2.setColor(new Color(255,255,255,240));
                g2.fillRoundRect(0,0,
                        field.getWidth()-6,
                        field.getHeight()-6,
                        35,35);

                g2.dispose();
                super.paintSafely(g);
            }
        });
    }

    private void addPlaceholder(JTextField field,String text){
        field.setText(text);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter(){
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

    // ================= Dialogs =================

    private void showError(String message){
        showCustomDialog(message,new Color(255,80,80),"Error ⚠");
    }

    private void showSuccess(String message){
        showCustomDialog(message,new Color(0,200,120),"Success ✅");
    }

    private void showCustomDialog(String message,Color color,String titleText){

        JDialog dialog = new JDialog(this,true);
        dialog.setSize(420,220);
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

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Segoe UI",Font.BOLD,20));
        title.setForeground(color);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel msg = new JLabel("<html><center>"+message+"</center></html>");
        msg.setFont(new Font("Segoe UI",Font.PLAIN,14));
        msg.setForeground(Color.WHITE);
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton ok = createGradientButton("Understood");
        ok.addActionListener(e -> dialog.dispose());

        panel.add(title);
        panel.add(Box.createVerticalStrut(15));
        panel.add(msg);
        panel.add(Box.createVerticalStrut(25));
        panel.add(ok);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ================= Buttons =================

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
        btn.setFont(new Font("Segoe UI",Font.BOLD,15));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE,50));
        btn.setBorder(new EmptyBorder(12,20,12,20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createOutlineButton(String text){
        JButton btn = new JButton(text);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBorder(new LineBorder(Color.WHITE,1,true));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE,45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ================= Background =================

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
