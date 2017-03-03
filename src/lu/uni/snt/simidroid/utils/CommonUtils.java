package lu.uni.snt.simidroid.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CommonUtils 
{
	public static boolean isStringEmpty(String str)
	{
		boolean isEmpty = false;
		
		if (null == str || str.isEmpty())
		{
			isEmpty = true;
		}
		
		return isEmpty;
	}
	
	public static Set<String> cloneSet(Set<String> src)
	{
		Set<String> dest = new HashSet<String>();
		for (String s : src)
		{
			dest.add(s);
		}
		return dest;
	}
	
	public static Map<String, Object> cloneMap(Map<String, Object> src)
	{
		Map<String, Object> dest = new HashMap<String, Object>();
		for (String srcKey : src.keySet())
		{
			dest.put(srcKey, src.get(srcKey));
		}
		
		return dest;
	}
	
	public static String getFileName(String path)
	{
		String fileName = path;
		if (fileName.contains("/"))
		{
			fileName = fileName.substring(fileName.lastIndexOf('/')+1);
		}
		
		return fileName;
	}
	
	public static void writeResultToFile(String path, String content)
	{
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
		    out.print(content);
		    out.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static int totalValue(Map<String, Integer> map)
	{
		int total = 0;
		
		for (Map.Entry<String, Integer> entry : map.entrySet())
		{
			total += entry.getValue();
		}
		
		return total;
	}
	
	public static Set<String> loadFile(String filePath)
	{
		Set<String> lines = new HashSet<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line = "";
			while ((line = br.readLine()) != null)
			{
				lines.add(line);
			}
			
			br.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return lines;
	}
	
	public static List<String> loadFileToList(String filePath)
	{
		List<String> lines = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line = "";
			while ((line = br.readLine()) != null)
			{
				lines.add(line);
			}
			
			br.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return lines;
	}
	
	public static List<String> loadFileToList(String filePath, String prefix)
	{
		if ("NULL".equals(prefix))
		{
			return loadFileToList(filePath);
		}
		
		List<String> lines = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line = "";
			while ((line = br.readLine()) != null)
			{
				if (null != prefix && line.startsWith(prefix))
				{
					lines.add(line.replace(prefix, ""));
				}
			}
			
			br.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return lines;
	}
	
	public static TreeMap<String,Integer> sort(Map<String, Integer> map)
	{
		ValueComparator bvc =  new ValueComparator(map);
		TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);
		sorted_map.putAll(map);
		
		return sorted_map;
	}
	
	static class ValueComparator implements Comparator<String> {

	    Map<String, Integer> base;
	    public ValueComparator(Map<String, Integer> base) {
	        this.base = base;
	    }

	    // Note: this comparator imposes orderings that are inconsistent with equals.    
	    public int compare(String a, String b) {
	        if (base.get(a) >= base.get(b)) {
	            return -1;
	        } else {
	            return 1;
	        } // returning 0 would merge keys
	    }
	}
}
