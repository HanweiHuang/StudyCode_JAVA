import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class LogHandler implements InvocationHandler {
	private Object target;
	public LogHandler(Object target){
		super();
		this.target = target;
	}
	
	@Override
	public void invoke(Object o, Method m) {
		System.out.println("log start");
		try {
			m.invoke(target);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("log finished");
	}

}
