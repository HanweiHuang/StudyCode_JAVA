import org.junit.Test;


public class newTest {
	@Test
	public void pro() throws Exception{
		Tank t = new Tank();
		//InvocationHandler h = new TimeHandler(t);
		InvocationHandler h = new LogHandler(t);
		Moveable m = (Moveable)Proxy.newProxyInstance(Moveable.class,h);
		m.move();
		m.stop("haha");
		
	}
}
