import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class CountdownTest2 extends JFrame{


		private JLabel label;
		Thread t;
		class Counter extends Thread{
			public void run() {
				for (int i=0;i<=10;i++) {
					try {
					Thread.sleep(1000);
					
				}catch(InterruptedException e) {
					return; //return이 되어야 스레드가 중지된다
			}
			label.setText(i+ "");
				}
			
		}

	}
	public CountdownTest2() {
			setTitle("카운트다운");
			setSize(400,150);
			getContentPane().setLayout(null);
			label = new JLabel("0");
			label.setBounds(0,0,384,111);
			label.setFont(new Font("Serif", Font.BOLD,100));
			getContentPane().add(label);
			setVisible(true);
			
			JButton b1 = new JButton("카운터 중지");
			b1.setBounds(247,25,125,23);
			b1.addActionListener(e->t.interrupt());
			getContentPane().add(b1); 
			setVisible(true);
			t = new Counter();
			t.start();}
			
	

		
		
		
	public static void main(String[] args) {
		CountdownTest2 t = new CountdownTest2();}
	
}