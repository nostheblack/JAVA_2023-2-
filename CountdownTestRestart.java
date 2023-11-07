import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CountdownTestRestart extends JFrame {

    private JLabel label;
    Thread t;
    boolean counting = true;

    class Counter extends Thread {
        public void run() {
            for (int i = 0; i <= 10; i++) {
                if (!counting) {
                    return;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                label.setText(Integer.toString(i));
            }
        }
    }

    public CountdownTestRestart() {
        setTitle("카운트다운");
        setSize(400, 150);
        getContentPane().setLayout(null);

        label = new JLabel("0");
        label.setBounds(0, 0, 384, 111);
        label.setFont(new Font("Serif", Font.BOLD, 100));
        getContentPane().add(label);

        JButton b1 = new JButton("카운터 중지");
        b1.setBounds(247, 25, 125, 23);
        b1.addActionListener(e -> counting = false);
        getContentPane().add(b1);

        JButton b2 = new JButton("카운트 다시 시작");
        b2.setBounds(247, 50, 125, 23);
        b2.addActionListener(e -> {
            counting = true;
            t = new Counter();
            t.start();
        });
        getContentPane().add(b2);

        setVisible(true);
        t = new Counter();
        t.start();
    }

    public static void main(String[] args) {
        CountdownTestRestart t = new CountdownTestRestart();
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
