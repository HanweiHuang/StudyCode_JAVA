import java.util.Random;


public class Tank implements Moveable{

	@Override
	public void move() {
		
		System.out.println("Tank moving ...");
		try {
			Thread.sleep(new Random().nextInt(10000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void stop(String a) {
		// TODO Auto-generated method stub
		System.out.println("this is a stop method "+ a);
	}

	
}
