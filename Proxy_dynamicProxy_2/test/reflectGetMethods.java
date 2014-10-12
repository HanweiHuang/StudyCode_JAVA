import java.lang.reflect.Method;

import org.junit.Test;


public class reflectGetMethods {
	@Test
	public void test(){
		Method[] methods = Moveable.class.getMethods();
		for(Method m:methods){
			//System.out.println(m.getParameterTypes());
			Class[] types = m.getParameterTypes();
			for(int i=0; i<m.getParameterTypes().length;i++){
				System.out.println(types[i].getName());
			}
		}
	}
}
