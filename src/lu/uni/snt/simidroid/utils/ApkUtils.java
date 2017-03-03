package lu.uni.snt.simidroid.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class ApkUtils 
{
	public static Map<String, String> getPathHashes(String zipFile)
	{
		Map<String, String> pathHashes = new HashMap<String, String>();

		try 
		{
			InputStream inputStream = new FileInputStream(zipFile);

			ZipInputStream zipStream = new ZipInputStream(inputStream);
			
			ZipEntry entry = null;
			DigestInputStream dis = null;
	        while((entry = zipStream.getNextEntry()) != null)
	        {
	        	MessageDigest md = MessageDigest.getInstance("MD5");
	            dis = new DigestInputStream(zipStream, md);
	            
	            byte[] buffer = new byte[1024];
	            int read = dis.read(buffer);
	            while (read > -1) 
	            {
	                read = dis.read(buffer);
	            }
	            
	            byte[] digest = dis.getMessageDigest().digest();
	            
	            String hexHash = (new HexBinaryAdapter()).marshal(digest);
	            pathHashes.put(entry.getName(), hexHash);
	            
	            entry.getCreationTime();
	        }
	        
	        if (null != dis)
	        {
	        	dis.close();
	        }
	        zipStream.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return pathHashes;
	}
	
	/**
	 * The dex creation time is not accurate for repackaging detection, as it may not be changed by the repackaging process.
	 * 
	 * @param zipFile
	 * @return
	 */
	public static long getDexCreationTime(String zipFile)
	{
		long creationTime = -1l;
		
		try 
		{
			InputStream inputStream = new FileInputStream(zipFile);

			ZipInputStream zipStream = new ZipInputStream(inputStream);
			
			ZipEntry entry = null;
	        while((entry = zipStream.getNextEntry()) != null)
	        {
	        	if ("classes.dex".equals(entry.getName()))
	        	{
	        		creationTime = entry.getTime();
	        		break;
	        	}
	        }
	        
	        zipStream.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		if (creationTime == -1l)
		{
			throw new RuntimeException("No classes.dex found or no time set for classes.dex [" + zipFile + "]");
		}
		
		
		return creationTime;
	}
	
	public static long getApkCreationTime(String zipFile)
	{
		long creationTime = Long.MIN_VALUE;
		
		try 
		{
			InputStream inputStream = new FileInputStream(zipFile);

			ZipInputStream zipStream = new ZipInputStream(inputStream);
			
			ZipEntry entry = null;
	        while((entry = zipStream.getNextEntry()) != null)
	        {
	        	long time = entry.getTime();
	        	
        		if (creationTime < time)
        		{
        			creationTime = time;
        		}
	        }
	        
	        zipStream.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		if (creationTime == Long.MIN_VALUE)
		{
			throw new RuntimeException("No classes.dex found or no time set for classes.dex [" + zipFile + "]");
		}
		
		
		return creationTime;
	}
	
	public static long getCreationTime(String zipFile)
	{
		long creationTime = -1l;
		
		try 
		{
			File file = new File(zipFile);
	        
	        creationTime = file.lastModified();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return creationTime;
	}
	
	public static void main(String[] args) throws Exception
	{
		/*
		Set<String> ss = new HashSet<String>();
		
		String dir = "/Volumes/joey/workspace/piggyback/groundtruth/apps";
		for (File file : new File(dir).listFiles())
		{
			
			Path p = Paths.get(file.getAbsolutePath());
			
			BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);

			System.out.println("creationTime: " + attr.creationTime());
			System.out.println("lastAccessTime: " + attr.lastAccessTime());
			System.out.println("lastModifiedTime: " + attr.lastModifiedTime());
			
			
			
			long time2 = getApkCreationTime(file.getAbsolutePath());
			 
			 Date date = new Date(time2);
		    Format format = new SimpleDateFormat("yyyy MM");
		    
		    
		    ss.add(format.format(date));
		}
		
		for (String s : ss)
		{
			System.out.println(s);
		}*/
		
		
		 String zipFile = "/Users/li.li/Project/github/DroidBench/apk/InterComponentCommunication/ActivityCommunication5.apk";
		 Map<String, String> pathHashes = getPathHashes(zipFile);
		 for (Map.Entry<String, String> entry : pathHashes.entrySet())
		 {
			 System.out.println(entry.getKey() + ", " + entry.getValue());
		 }
		 
		 long time1 = getDexCreationTime(zipFile);
		 long time2 = getCreationTime(zipFile);
		 long time3 = getApkCreationTime(zipFile);
		 System.out.println(time1 + ", " + time2 + ", " + time3);
		 
	}
}
