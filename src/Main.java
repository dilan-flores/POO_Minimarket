
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame=new JFrame("LOGIN");
        frame.setContentPane(new Login().PanelLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(0,0,600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}