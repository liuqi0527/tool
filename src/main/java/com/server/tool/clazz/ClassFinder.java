package com.server.tool.clazz;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载指定类的Class对象 
 */
public class ClassFinder {
	private static final Logger logger = LoggerFactory.getLogger(ClassFinder.class);
	private static final String SUFFFIX_CLASS = ".class";
	private static final String SUFFFIX_JAR = ".jar";
	public  static boolean fromJar = true; 

	public static void main(String[] args) throws IOException{
//		File f = new File("E:/workspace/tool/target/classes/game/tool/clazz");
//		File f = new File("./");//项目根目录
//		File f = new File("/");//盘符根目录
		
//		Set<Class<?>> set = find("game.tool.clazz");
//		for(Class<?> clzz : set){
//			System.out.println(clzz.getName());
//		}
	}
	
	/**
	 * @param pkgName 指定包名
	 * @param supperClass 指定父类
	 * @param subClass 指定子类
	 * @return subClass extends <b>T</b> extends supperClass，返回T
	 */
	public static Set<Class<?>> find(String pkgName, Class<?> supperClass, Class<?> subClass){
		Set<Class<?>> result = new HashSet<Class<?>>();
		for(Class<?> clazz : find(pkgName)){
			if((supperClass == null || supperClass.isAssignableFrom(clazz)) && 
					(subClass == null || clazz.isAssignableFrom(subClass))){
				result.add(clazz);
			}
		}
		return result;
	}
	
	/**
	 * @param pkgName 包名
	 * @return
	 */
	public static Set<Class<?>> find(String pkgName){
		Set<Class<?>> result = new HashSet<Class<?>>();
		
		for(String path : getClassPathList()){
			if(path.endsWith(SUFFFIX_JAR)){
				result.addAll(findFromJar(path, pkgName));
			}else{
				String pkgPath = path + "/" + pkgName.replace(".", "/");
				result.addAll(findFromDirectory(pkgPath, path));
			}
		}
		return result;
	}
	
	public static Set<Class<?>> findFromJar(String fileName, String pkgName) {
		if(!fromJar){
			return new HashSet<Class<?>>();
		}
		File file = new File(fileName);
		if(!file.exists()){
			return new HashSet<Class<?>>();
		}
		
		
		Set<Class<?>> result = new HashSet<Class<?>>();
		String classPrefix = pkgName.replace(".", "/");
		JarFile jarFile = null;
		try{
			jarFile = new JarFile(file);
			Enumeration<JarEntry> enumr = jarFile.entries();
			while(enumr.hasMoreElements()){
				JarEntry entry = enumr.nextElement();
				if(!checkJarEntry(entry, classPrefix)){
					continue;
				}
				String entryName = entry.getName();
				String className = entryName.substring(0, entryName.length() - SUFFFIX_CLASS.length()).replace("/", ".");
				addClassToSet(Class.forName(className), result);
			}
		}catch(Exception e){
			logger.error("", e);
		}finally{
			try{
				if(jarFile != null){
					jarFile.close();
				}
			}catch(Exception e){
				logger.error("", e);
			}
		}
		return result;
	}
	

	private static boolean checkJarEntry(JarEntry entry, String prefix){
		return !entry.isDirectory() && entry.getName().endsWith(SUFFFIX_CLASS)
				&& entry.getName().startsWith(prefix);
	}
	
	public static Set<Class<?>> findFromDirectory(String path, String pathPrefix){
		File file = new File(path);
		if(!file.exists()){
			return new HashSet<Class<?>>();
		}
		
		Set<Class<?>> result = new HashSet<Class<?>>();
		if(file.isFile()){
			fileToClazz(file, pathPrefix, result);
			return result;
		}
		
		
		for(File child : file.listFiles()){
			if(child.isFile()){
				fileToClazz(child, pathPrefix, result);
			}else{
				result.addAll(findFromDirectory(child.getPath(), pathPrefix));
			}
		}
		return result;
	}
	
	private static void fileToClazz(File file, String pathPrefix, Set<Class<?>> set){
		if(!file.getName().endsWith(SUFFFIX_CLASS)){
			return;
		}
		
		try{
			String path = file.getPath();
			int begin = pathPrefix.length() + 1;
			int end = path.indexOf(SUFFFIX_CLASS);
			String className = path.substring(begin, end).replace("\\", ".");
			addClassToSet(Class.forName(className), set);
		}catch(Exception e){
			logger.error("", e);
		}
	}
	
	private static void addClassToSet(Class<?> clazz, Set<Class<?>> set){
		if(!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())){
			set.add(clazz);
		}
	}
	
	public static String[] getClassPathList(){
		String classPath = System.getProperty("java.class.path");
		return classPath.split(System.getProperty("path.separator"));
	}
}
