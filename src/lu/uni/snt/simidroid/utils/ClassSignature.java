package lu.uni.snt.simidroid.utils;

public class ClassSignature 
{
	public static final String DEFAULT = "DEFAULT";
	private String signature = "";
	
	public ClassSignature(String signature)
	{
		this.signature = signature;
	}
	
	public boolean isInnerClass()
	{
		return signature.contains("$");
	}
	
	public String getPackageName()
	{
		String pkg = signature;
		
		if (pkg.contains("$"))
		{
			pkg = pkg.substring(0, pkg.indexOf('$'));
		}
		
		if (! pkg.contains("."))
		{
			pkg = DEFAULT;
		}
		else
		{
			int pos = pkg.lastIndexOf('.');
			pkg = pkg.substring(0, pos);
		}
		
		return pkg;
	}
	
	/**
	 * if level equeals 2, then for lu.uni.snt.serval.Test, we return lu.uni
	 * 
	 * @param level
	 * @return
	 */
	public String getPackageName(int level)
	{
		return getPackageName(getPackageName(), level);
	}
	
	public static String getPackageName(String pkgName, int level)
	{
		String[] segments = pkgName.split("\\.");
		
		if (segments.length <= level)
		{
			return pkgName;
		}
		
		String pkg = segments[0];
		for (int i = 1; i < level; i++)
		{
			pkg = pkg + "." + segments[i];
		}
		
		return pkg;
	}
}
