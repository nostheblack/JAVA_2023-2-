class Myclass implements Runnable{
	String myname;
	public Myclass(String name) {myname = name;}
	public void run() {
		for(int i=0;i<=10;i++) {
			System.out.print(myname+ i+ " ");
		}
	}
	
	
}
public class TestThread2two {

	public static void main(String[] args) {
		Thread t1 = new Thread(new Myclass("A"));
		Thread t2 = new Thread(new Myclass("B"));
		t1.start();
		t2.start();
		

	}

}
