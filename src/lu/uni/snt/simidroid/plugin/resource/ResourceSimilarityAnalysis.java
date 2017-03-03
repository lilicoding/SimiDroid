package lu.uni.snt.simidroid.plugin.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lu.uni.snt.simidroid.SimilarityAnalysis;
import lu.uni.snt.simidroid.utils.CommonUtils;

public class ResourceSimilarityAnalysis extends SimilarityAnalysis 
{
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
