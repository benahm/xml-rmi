package com.aladyn.server;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.aladyn.server.ObjectBuilder;
import com.aladyn.server.exception.BuildObjectException;

/**
 * 
 * @author Mario Matar
 * @author Ahmed Bensaad
 *
 */
public class ObjectBuilderTest {

	@Test
	public void testAddField() {
		ObjectBuilder build = new ObjectBuilder("TestField");
		try {
			build.addField("public int i=2012;");
			build.addField("public String text=\"champs\";");
			Class<?> cc = build.getMyClass();
			Field f1, f2;

			f1 = cc.getDeclaredField("i");
			int i = f1.getInt(cc.newInstance());
			f2 = cc.getDeclaredField("text");
			String text = (String) f2.get(cc.newInstance());
			assertTrue(i == 2012 && text.equals("champs"));
		} catch (BuildObjectException  e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testAddMethod() {
		ObjectBuilder build = new ObjectBuilder("TestMethod");

		try {
			build.addMethod("public int getYear(){return 2012;}");
			build.addMethod("public String getLang(){ return \"français\";}");
			Class<?> cc = build.getMyClass();
			Method m1, m2;

			m1 = cc.getMethod("getYear");
			int year = (Integer) m1.invoke(cc.newInstance());
			m2 = cc.getMethod("getLang");
			String lang = (String) m2.invoke(cc.newInstance());
			assertTrue(year == 2012 && lang.equals("français"));
		} catch (BuildObjectException  e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddConstructor() {
		ObjectBuilder build = new ObjectBuilder("TestConstruct");

		try {
			build.addField("public int i=2011;");
			build.addConstructor("public TestConstruct(){}");
			build.addConstructor("public TestConstruct(int i){ this.i=2012; }");
			Class<?> cc = build.getMyClass();
			Constructor<?> c1, c2;

			c1 = cc.getDeclaredConstructor();
			c2 = cc.getConstructor(int.class);
			Object obj1 = c1.newInstance();
			int i1 = obj1.getClass().getField("i").getInt(obj1);
			Object obj2 = c2.newInstance(0);
			int i2 = obj2.getClass().getField("i").getInt(obj2);
			assertTrue(i1 == 2011 && i2 == 2012);
		} catch (BuildObjectException  e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddInterface() {
		ObjectBuilder build = new ObjectBuilder("TestInterface");
		Class<?> cc = null;
		try {
			build.addInterface(Comparable.class);
			cc = build.getMyClass();
		} catch (BuildObjectException e) {
			e.printStackTrace();
		}
		
		Class<?>[] inters;

		inters = cc.getInterfaces();
		Set<Class<?>> setInters = new HashSet<Class<?>>(Arrays.asList(inters));
		assertTrue(setInters.contains(Comparable.class));

	}
}
