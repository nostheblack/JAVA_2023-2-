public class JoinTest2 extends Thread{
	public void run() {
		for(int i =1; i<=3;i++) {
			System.out.println(getName()+ " "+ i);
		}
	}
	public static void main(String[] args) {
		JoinTest2 t1 = new JoinTest2();
		JoinTest2 t2 = new JoinTest2();
		t1.start();
		try {
			t1.join();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		t2.start();
	}

}
