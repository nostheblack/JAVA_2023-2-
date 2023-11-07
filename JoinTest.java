
public class JoinTest extends Thread{
	public void run() {
		for(int i =1; i<=3;i++) {
			System.out.println(getName()+ " "+ i);
		}
	}
	public static void main(String[] args) {
		JoinTest t1 = new JoinTest();
		JoinTest t2 = new JoinTest();
		t1.start();
		try {
			t1.join();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		t2.start();
	}

}
