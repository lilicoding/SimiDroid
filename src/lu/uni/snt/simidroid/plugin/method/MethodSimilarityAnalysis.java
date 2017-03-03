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
	
	public MethodSimilarityAnalysis() { }
	
	public MethodSimilarityAnalysis(String librarySetPath)
	{
		libraries = CommonUtils.loadFile(librarySetPath);
		
		System.out.println("Excluding Library is Enabled.");
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
		// for excluding libraries
		if (null != libraries)
		{
			Set<String> methodsToRemove1 = new HashSet<String>();
			Set<String> methodsToRemove2 = new HashSet<String>();
			
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
					methodsToRemove1.add(method);
				}
			}
			
			for (String method : methodsToRemove1)
			{
				features1.remove(method);
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
					methodsToRemove2.add(method);
				}
			}
			
			for (String method : methodsToRemove2)
			{
				features2.remove(method);
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
