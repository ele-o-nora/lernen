package graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class ElusiveButton extends JFrame {

    private JButton button;
    private int height;
    private int width;
    private int buttonWidth = 150;
    private int buttonHeight = 50;

    private Random random = new Random();

    public static void main(String[] args) {
        new ElusiveButton();
    }

    ElusiveButton() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Elusive Button");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        height = screenSize.height / 2;
        width = screenSize.width / 2;
        setBounds(width / 2, height / 2, width, height);

        setLayout(null);
        button = new JButton("Do not push");
        button.setBounds(width / 2 - buttonWidth / 2, height / 2 - buttonHeight / 2, buttonWidth, buttonHeight);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                int left = random.nextInt(width - buttonWidth);
                int top = random.nextInt(height - buttonHeight);
                button.setBounds(left, top, buttonWidth, buttonHeight);
            }
        });

        add(button);

        setVisible(true);
    }
}
