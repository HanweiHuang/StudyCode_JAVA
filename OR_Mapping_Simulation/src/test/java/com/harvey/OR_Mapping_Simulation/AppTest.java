package com.harvey.OR_Mapping_Simulation;

import org.junit.Test;

import com.harvey.OR_Mapping_Simulation.model.Session;
import com.harvey.OR_Mapping_Simulation.model.Student;

/**
 * Unit test for simple App.
 */
public class AppTest {
	@Test
	public void test(){
		Student stu = new Student();
		stu.setId(4);
		stu.setName("haha");
		stu.setUsername("18");
		
		Session sess = new Session();
		sess.save(stu);
		
	} 
}
