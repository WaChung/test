package com.example.demo;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.OverridingClassLoader;

import com.aia.cache.EnableMsaMemCache;
import com.example.annotation.SingletonVariable;
import com.example.demo.mbean.BasicThread;

import de.codecentric.boot.admin.server.config.EnableAdminServer;


@SpringBootApplication
@ComponentScan("com.*")
@EnableMsaMemCache
@EnableAdminServer
public class App {
	@SingletonVariable
	private static ApplicationContext applicationContext;

	@SingletonVariable
	private static int dynamicNumberOfThread = 5;
	public static void main(String[] args) throws MalformedObjectNameException, InstanceAlreadyExistsException,
			MBeanRegistrationException, NotCompliantMBeanException, ClassNotFoundException {
		applicationContext = SpringApplication.run(App.class, args);
		ClassLoader cl = App.class.getClassLoader();
		OverridingClassLoader overridingClassLoader = new OverridingClassLoader(cl);
		// RestrictedClassLoader rl = new
		// RestrictedClassLoader(((LaunchedURLClassLoader)cl).getURLs(), cl);
		// Thread.currentThread().setContextClassLoader(rl);
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName("framework:type=Thread");
		BasicThread mbean = new BasicThread();
		mbs.registerMBean(mbean, name);
		getInstantiatedSigletons(applicationContext);;
		printBeans();
		
		// Start threads to increment counters.
		/*
		 * for (int i = 0; i < dynamicNumberOfThread; i++) { Thread thread = new
		 * IncrementerThread("Printer-" + i); thread.start(); }
		 * 
		 * // Register dynamic MBean MBeanServer server =
		 * ManagementFactory.getPlatformMBeanServer(); ObjectName objectName1 = new
		 * ObjectName("com.itsallbinary.jmx:name=dynamicCounter");
		 * server.registerMBean(new DynamicApplicationInfo(), objectName1);
		 * 
		 */

	}

    private static String[] printBeans() {
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if (autowireCapableBeanFactory instanceof SingletonBeanRegistry) {
            String[] singletonNames = ((SingletonBeanRegistry) autowireCapableBeanFactory).getSingletonNames();
            for (String singleton : singletonNames) {
            	if (!singleton.startsWith("com"))
            		continue;
                System.out.println("Singleton: "+singleton);
            }
            return singletonNames;
        }
        return null;
    }
    
    
    public static void getInstantiatedSigletons(ApplicationContext ctx) throws ClassNotFoundException {
        List<Object> singletons = new ArrayList<Object>();

        String[] all = ctx.getBeanDefinitionNames();

        ConfigurableListableBeanFactory clbf = ((AbstractApplicationContext) ctx).getBeanFactory();

        System.out.println("Singleton: ");
        for (String name : all) {
            Object s = clbf.getSingleton(name);
            if (s != null && s.getClass().getName().startsWith("com.example")) {
            	String className = s.getClass().getName();
            	className = className.indexOf("$") > 0 ? className.substring(0, className.indexOf("$")) : className ;
            	System.out.println("Class Name: " + className);
            	Class<?> c = Class.forName(className);
             for (Field f : c.getDeclaredFields()) {
            	 if (f.isAnnotationPresent(SingletonVariable.class)) {
            		 System.out.println("Field Name with Annotation: "+f.getName());
            	 } else {
            		 System.out.println("Field Name without Annotation: "+f.getName());
            		 
            	 }
             }
          	 // System.out.println(s);
            }
        }


}
}
class IncrementerThread extends Thread {

	private String threadName;

	public IncrementerThread(String threadName) {
		this.threadName = threadName;
		DynamicApplicationInfo.incrementOfThread(threadName);
	}

	@Override
	public void run() {
		while (true) {
			DynamicApplicationInfo.incrementOfThread(threadName);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
