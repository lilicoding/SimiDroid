package lu.uni.snt.simidroid.plugin.method;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lu.uni.snt.simidroid.SimilarityAnalysis;
import lu.uni.snt.simidroid.utils.CommonUtils;
import lu.uni.snt.simidroid.utils.MethodSignature;

public class MethodSimilarityAnalysis extends SimilarityAnalysis 
{
	private Set<String> libraries = null;
	private boolean exclusive = false;
	
	public MethodSimilarityAnalysis() { }
	
	public MethodSimilarityAnalysis(String librarySetPath, boolean exclusive)
	{
		libraries = CommonUtils.loadFile(librarySetPath);
		this.exclusive = exclusive;
		
		System.out.println("Library Set is Enabled.");
	}
	
	@Override
	protected boolean identical(Object src, Object dest) 
	{
		MethodAbstract srcMA = (MethodAbstract) src;
		MethodAbstract destMA = (MethodAbstract) dest;
		
		return srcMA.equalsTo(destMA);
	}

	@Override
	protected void preAnalysis(Map<String, Object> features1, Map<String, Object> features2) 
	{
		if (null != libraries)
		{
			Set<String> libraryMethods1 = new HashSet<String>();
			Set<String> libraryMethods2 = new HashSet<String>();

			for (String method : features1.keySet())
			{
				boolean libraryMethod = false;
				
				String cls = new MethodSignature(method).getCls();
				
				for (String lib : libraries)
				{
					if (cls.startsWith(lib))
					{
						libraryMethod = true;
						break;
					}
				}
				
				if (libraryMethod)
				{
					libraryMethods1.add(method);
				}
			}
			
			
			
			for (String method : features2.keySet())
			{
				boolean libraryMethod = false;
				
				String cls = new MethodSignature(method).getCls();
				
				for (String lib : libraries)
				{
					if (cls.startsWith(lib))
					{
						libraryMethod = true;
						break;
					}
				}
				
				if (libraryMethod)
				{
					libraryMethods2.add(method);
				}
			}
			
			if (exclusive)
			{
				//considering only library-related code
				
				Set<String> allMethods1 = CommonUtils.cloneSet(features1.keySet());
				allMethods1.removeAll(libraryMethods1);
				
				for (String method : allMethods1)
				{
					features1.remove(method);
				}
				
				Set<String> allMethods2 = CommonUtils.cloneSet(features2.keySet());
				allMethods2.removeAll(libraryMethods2);
				
				for (String method : allMethods2)
				{
					features2.remove(method);
				}
			}
			else
			{
				// excluding libraries
				
				for (String method : libraryMethods1)
				{
					features1.remove(method);
				}
				
				for (String method : libraryMethods2)
				{
					features2.remove(method);
				}
			}
		}
		
		
		super.preAnalysis(features1, features2);
	}

	@Override
	protected void postAnalysis() 
	{
		super.postAnalysis();
	}
}
