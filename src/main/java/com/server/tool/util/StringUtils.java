package com.server.tool.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StringUtils {

	public static final String NEWLINE = System.getProperty("line.separator");

	private static final Logger log = LoggerFactory.getLogger(StringUtils.class);

    public static String alignLeft(CharSequence right, int width, char c) {
        return alignBoth("", right, width, c);
    }

    public static String alignBoth(CharSequence left, CharSequence right, int width, char c) {
        final int leftLength = left.length();
        final int rightLength = right.length();
        
        char[] chars = new char[width];
        Arrays.fill(chars, leftLength, width - rightLength, c);
        left.toString().getChars(0, leftLength, chars, 0);
        right.toString().getChars(0, rightLength, chars, width - rightLength);
        
        return new String(chars, 0, width);
    }
    
    /**
     * #capitalize is a more common word.
     * 
     * @deprecated
     * @see #capitalize(java.lang.String) 
     */
	public static String upperCaseFirstChar(String str){
        return capitalize(str);
	}

    /**
     * #decapitalize is a more common word.
     *
     * @deprecated
     * @see #decapitalize(java.lang.String) 
     */
	public static String lowerCaseFirstChar(String str){
		if(empty(str)) return str;
		if(str.length()==1) return str.toUpperCase();
		String first = String.valueOf(str.charAt(0)).toLowerCase();
		return first+str.substring(1);
	}

	public static boolean empty(String str){
		return str==null||str.trim().length()<=0;
	}

    public static String capitalize(String text) {
        if(text == null || text.length() == 0) {
            return text;
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }

    public static String decapitalize(String text) {
        return lowerCaseFirstChar(text);
    }

	public static String uuid(){
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
	
//    public static String md5(String str){
//        try {
//			MessageDigest md5=MessageDigest.getInstance("MD5");
//			return Base64Utils.encode(md5.digest(str.getBytes("utf-8")));
//		} catch (Exception e) {
//			throw new RuntimeException("md5 error", e);
//		}
//    }
    
	//remove last ln
	public static String removeLastLN(String str){
		if(!str.endsWith("\n")) return str;
		if(str.endsWith("\r\n")) return str.substring(0, str.length()-2);
		else return str.substring(0, str.length()-1);
	}
	
	public static String join(Collection<?> elements, String delimiter) {
        return join(elements, delimiter, true);
	}

	public static String join(Object[] elements, String delimiter) {
        return join(elements, delimiter, true);
	}

    public static String join(Collection<?> elements, String delimiter, boolean appendTail) {
        return join(elements, delimiter, null, appendTail);
    }

    /**
     * Returns the joint string from the given elements with a specified delimiter.
     *
     * @param elements the given elements
     * @param delimiter the given delimiter
     * @param formatter a tool used to customize the text of an element in elements
     * @param appendTail a flag indicates whether appending the last delimiter to the tail of the joint string
     *
     * @return the joint string from the given elements with a specified delimiter
     */
    public static <T> String join(Collection<T> elements, String delimiter, Formatter<T> formatter, boolean appendTail) {
        if(elements == null || elements.isEmpty()) {
            return "";
        }

		StringBuilder buffer = new StringBuilder();
		for (T a : elements) {
			buffer.append(formatter != null ? formatter.toString(a) : a).append(delimiter);
		}
        if(!appendTail) {
            buffer.setLength(buffer.length() - delimiter.length());
        }
		return buffer.toString();
    }

    /**
     * @see #join(java.util.Collection, java.lang.String, boolean) 
     */
    public static String join(Object[] elements, String delimiter, boolean appendTail) {
		return join(Arrays.asList(elements), delimiter, appendTail);
    }

    public static String fill(Object element, int count, String delimiter, boolean appendTail) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < count; i++) {
			buffer.append(element).append(delimiter);
		}
        if(!appendTail) {
            buffer.setLength(buffer.length() - delimiter.length());
        }
		return buffer.toString();
    }

    /**
	 * 判断一个字符串中是否含有中文字符
	 */
	public static boolean containChinese(String s) {
		String pt = "[\\w\\W]*[\u4e00-\u9fa5]+[\\w\\W]*";
		return (s.matches(pt));
	}

	/**
	 * 判断一个字符串是否全是ASCII字符
	 */
	public static boolean asciiString(String s) {
		String pt = "[\u0000-\u007f]*";
		return (s.matches(pt));
	}

	public static String encodeUTF8(String str){
		return encode("utf-8", str);
	}
	
	/**
	 * 编码指定的字符串
	 * @param charset
	 * @param str
	 * @return
	 */
	public static String encode(String charset, String str) {
		//log.debug("encode string : "+str);
		if (containChinese(str)) {
			//log.debug("this string has contain chinese already");
			return str;
		}
		if (asciiString(str)) {
			//log.debug("this string is ascii string");
			return str; 
		}
		String code[] = { charset, "utf-8", "gb2312", "gbk", "big5", "ISO8859-1" };
		try {
			// 取得在机器内表示的数据
			byte[] bytes = str.getBytes("ISO8859-1");
			for (int i = 0; i < code.length; i++) {
				String temp = new String(bytes, code[i]);
				if (containChinese(temp)) {
					//log.debug("success encode string using "+code[i]+" : "+temp);
					return temp;
				}
			}
		}
		catch (UnsupportedEncodingException e) {
			log.error("", e);
		}
		//log.error("cannot encode string : "+str);
		return str;
	}
	
	/**
	 * 将指定的字符串编码为unicode的形式
	 * @param str
	 * @return
	 */
	public static String toUnicode(String str){
		StringBuilder unicode = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c > 128) {
				unicode.append("&#x").append(Integer.toHexString(c))
						.append(";");
			} else
				unicode.append(c);
		}
		return unicode.toString();
	}
	
	public static String read(InputStream is) throws IOException{
		return read(new InputStreamReader(is));
	}
	
	public static String read(Reader reader) throws IOException{
		StringBuilder buffer = new StringBuilder(300); 
		BufferedReader br = new BufferedReader(reader);
		char[] chars = new char[300];
        int line;
		while ((line = br.read(chars)) >= 0){
			buffer.append(chars, 0, line);
		}
		br.close();
		return buffer.toString();
	}
	
	//key-value parse
	public static Map<String, String> toMap(String str){
		return toMap(str, ",");
	}

	public static Map<String, String> toMap(String str, String pairSeparator){
		return toMap(str, pairSeparator, "=");
	}

	public static Map<String, String> toMap(String str, String pairSeparator, String keyValueSeparator){
		Map<String, String> map = new HashMap<String, String>();
		String[] values = str.split(pairSeparator);
        Pattern pattern = Pattern.compile(keyValueSeparator);
		for(int i=0; i<values.length; i++){
			String[] pair = pattern.split(values[i]);
			if(pair.length==2) map.put(pair[0], pair[1]);
		}
		return map;
	}
	
	public static String toString(Map map){
		return toString(map, ",");
	}

	public static String toString(Map map, String pairConnector){
		return toString(map, pairConnector, "=");
	}
	
	public static String toString(Map map, String pairConnector, String keyValueConnector){
        return toString(map, pairConnector, keyValueConnector, true);
	}
	
	public static String toString(Map map, String pairConnector, String keyValueConnector, boolean appendTail){
		return toString(map, pairConnector, keyValueConnector, null, null, appendTail);
	}
	
	public static String toString(Map map, String pairConnector, String keyValueConnector,
            Formatter keyFormatter, Formatter valueFormatter,boolean appendTail){
		return toString(map, pairConnector, keyValueConnector, keyFormatter, valueFormatter, null, appendTail);
	}
	
	public static String toString(Map map, String pairConnector, String keyValueConnector,
            Formatter keyFormatter, Formatter valueFormatter, Comparator<Map.Entry> comparator, boolean appendTail){

        if(map.isEmpty()) {
            return "";
        }

        Iterator<Map.Entry> i;
        if(comparator == null) {
            i = map.entrySet().iterator();
        }
        else {
            List<Map.Entry> entries = new ArrayList(map.entrySet());
            Collections.sort(entries, comparator);
            i = entries.iterator();
        }

        StringBuilder sb = new StringBuilder();
        while(i.hasNext()){
			Map.Entry e = i.next();
            Object keyText = e.getKey();
            if(keyFormatter != null) {
                keyText = keyFormatter.toString(keyText);
            }
            Object valueText = e.getValue();
            if(valueFormatter != null) {
                valueText = valueFormatter.toString(valueText);
            }
			sb.append(keyText).append(keyValueConnector).append(valueText).append(pairConnector);
		}
        if(!appendTail) {
            sb.setLength(sb.length() - pairConnector.length());
        }
		return sb.toString();
	}

    public static String toString(byte[] bytes) {
        return toString(bytes, Character.MAX_RADIX);
    }

    public static String toString(byte[] bytes, int radix) {
        BigInteger i = new BigInteger(bytes);
        return i.toString(radix);
    }
	
    public static String toString(Exception e){
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	PrintStream ps = new PrintStream(baos);
    	e.printStackTrace(ps);
    	byte[] bytes = baos.toByteArray();
		return encodeUTF8(new String(bytes));
    }

    public static interface Formatter<T> {

        String toString(T o);

    }
    
	public static void main(String[] args){
		System.out.println(toString(new RuntimeException()));
	}
}
