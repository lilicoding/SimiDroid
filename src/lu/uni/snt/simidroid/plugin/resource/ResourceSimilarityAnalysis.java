package lu.uni.snt.simidroid.plugin.resource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lu.uni.snt.simidroid.SimilarityAnalysis;
import lu.uni.snt.simidroid.utils.CommonUtils;

public class ResourceSimilarityAnalysis extends SimilarityAnalysis 
{
	private Set<String> extensions = null;
	private boolean exclusive = false;
	
	public ResourceSimilarityAnalysis() {}
	
	public ResourceSimilarityAnalysis(String resExtensionSetPath, boolean exclusive)
	{
		extensions = CommonUtils.loadFile(resExtensionSetPath);
		this.exclusive = exclusive;
	}
	
	@Override
	protected void preAnalysis(Map<String, Object> features1, Map<String, Object> features2) 
	{
		super.preAnalysis(features1, features2);
		
		if (null != extensions)
		{
			Set<String> extensionFiles1 = new HashSet<String>();
			Set<String> extensionFiles2 = new HashSet<String>();
			
			for (String file : features1.keySet())
			{
				String fileExtension = file.substring(file.lastIndexOf('.')+1);
				
				if (extensions.contains(fileExtension))
				{
					extensionFiles1.add(file);
				}
			}
			
			for (String file : features2.keySet())
			{
				String fileExtension = file.substring(file.lastIndexOf('.')+1);
				
				if (extensions.contains(fileExtension))
				{
					extensionFiles2.add(file);
				}
			}
			
			if (exclusive)
			{
				Set<String> allFiles1 = CommonUtils.cloneSet(features1.keySet());
				allFiles1.removeAll(extensionFiles1);
				
				for (String file : allFiles1)
				{
					features1.remove(file);
				}
				
				Set<String> allFiles2 = CommonUtils.cloneSet(features2.keySet());
				allFiles2.removeAll(extensionFiles2);
				
				for (String file : allFiles2)
				{
					features2.remove(file);
				}
			}
			else
			{
				for (String file : extensionFiles1)
				{
					features1.remove(file);
				}
				
				for (String file : extensionFiles2)
				{
					features2.remove(file);
				}
			}
		}
		
	}

	@Override
	protected void postAnalysis() 
	{
		Map<String, String> aa1Hash2resources = new HashMap<String, String>();
		Map<String, String> aa2Hash2resources = new HashMap<String, String>();
		
		for (Map.Entry<String, Object> entry : aa1.features.entrySet())
		{
			aa1Hash2resources.put(entry.getValue().toString(), entry.getKey());
		}
		
		for (Map.Entry<String, Object> entry : aa2.features.entrySet())
		{
			aa2Hash2resources.put(entry.getValue().toString(), entry.getKey());
		}
		
		Set<String> tmpHashes = CommonUtils.cloneSet(aa1Hash2resources.keySet());
		tmpHashes.retainAll(aa2Hash2resources.keySet());
		
		for (String hash : tmpHashes)
		{
			if (! aa1Hash2resources.get(hash).equals(aa2Hash2resources.get(hash)))
			{
				System.out.println("[Explanation](Same Hash Different Resource File) " + aa1Hash2resources.get(hash) + " -> " + aa2Hash2resources.get(hash));
			}
		}
	}
}
