import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;


public class Proxy {
	public static Object newProxyInstance(Class inter,InvocationHandler h) throws Exception{
	
	String rt = "\r\n";	
	String method = "";
	Method[] methods = inter.getMethods();
//	for(Method m:methods){
//		method +=
//				"@Override "+ rt +
//				" 	public void "+ m.getName() +"(){" + rt +
//				"		long start = System.currentTimeMillis();"+ rt +
//			    "		System.out.println(\"start time:\"+ start);"+ rt +
//			    "		t."+ m.getName()+"();"+ rt +
//			    "		long end = System.currentTimeMillis();"+ rt +
//			    "		System.out.println(\"time:\" + (end-start));"+ rt +
//			    "	}" + rt +"	";
//	}
		
	for(Method m:methods){
		method +=
				"@Override "+ rt +
				" 	public void "+ m.getName() +"(){" + rt +
				"	try{"+ rt +
				" 		Method md ="+inter.getName()+".class.getMethod(\""+m.getName()+"\");"+ rt + 
				"   		h.invoke(this, md);"+ rt + 
				"	}catch(Exception e){" + rt +
				"		e.printStackTrace();"+ rt +
				"	}" + rt +
			    "  }" + rt +"	";
	}
	
	String src =
			"import java.lang.reflect.Method;" + rt +
			"public class TankTime implements "+inter.getName() +"{" + rt +
			"	private Moveable t;"+ rt +
			"	private InvocationHandler h;" + rt +	
			"	public TankTime(InvocationHandler h){"+ rt +
			"		this.h = h;"+ rt +
			"	}"+ rt +
			"	"+method+ rt +
			"}";
	
	 //String path = System.getProperty("user.dir")+"/src/TankTime.java";
	String path = "c:/harvey/TankTime.java"; 
	//System.out.println(path);
	
	    //write file
	    File file = new File(path);
	    FileWriter fw = new FileWriter(file);
	    fw.write(src);
	    fw.flush();
	    fw.close();    
	
	    //compiler
	    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	    StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
	    Iterable units = fileMgr.getJavaFileObjects(path);
	    CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
	    t.call();
	    fileMgr.close();
	    
	    //load to memory and create an instance
	    URL[] urls = new URL[]{new URL("file:/"+"c:/harvey/")};
	    URLClassLoader url = new URLClassLoader(urls);
	    Class c = url.loadClass("TankTime");
	    //System.out.println(c);
	    
	    Constructor ctr = c.getConstructor(InvocationHandler.class);
	    Object m = ctr.newInstance(h);
	    //Moveable m = (Moveable)ctr.newInstance(new Tank());
	    //m.move();
	return m;
	}
}
