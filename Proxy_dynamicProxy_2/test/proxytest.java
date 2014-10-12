import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.junit.Test;


public class proxytest {
	@Test
	public void runTest(Class inter) throws IOException{
		String rt = "\r\n";
String src =
		"public class TankTime implements "+inter.getName() +"{" + rt +
		"	private Moveable t;"+ rt +
			
		"	public TankTime(Moveable t){"+ rt +
		"		this.t = t;"+ rt +
		"	}"+ rt +
			
		"	@Override"+ rt +
		"	public void move() {"+ rt +
		"		long start = System.currentTimeMillis();"+ rt +
		"		System.out.println(\"start time:\"+ start);"+ rt +
		"		t.move();"+ rt +
		"		long end = System.currentTimeMillis();"+ rt +
		"		System.out.println(\"time:\" + (end-start));"+ rt +
		"	}"+ rt +
		"}";
    
    String path = System.getProperty("user.dir")+"/src/TankTime.java";
    System.out.println(path);

    File file = new File(path);
    FileWriter fw = new FileWriter(file);
    fw.write(src);
    fw.flush();
    fw.close();
    
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
    
//    System.out.println(compiler);
    
	}
}
