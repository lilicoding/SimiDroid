package lu.uni.snt.simidroid.plugin.component;

import java.util.Map;
import java.util.Set;

import lu.uni.snt.simidroid.SimilarityAnalysis;
import lu.uni.snt.simidroid.utils.CommonUtils;

public class ComponentSimilarityAnalysis extends SimilarityAnalysis 
{
	@Override
	protected boolean identical(Object src, Object dest) 
	{
		IntentFilterAbstract srcIFA = (IntentFilterAbstract) src;
		IntentFilterAbstract destIFA = (IntentFilterAbstract) dest;
		
		return srcIFA.equalsTo(destIFA);
	}

	@Override
	protected void preAnalysis(Map<String, Object> features1, Map<String, Object> features2) 
	{
		// for excluding libraries
		
		super.preAnalysis(features1, features2);
	}

	@Override
	protected void postAnalysis() 
	{
		for (String comp1 : aa2.features.keySet())
		{
			IntentFilterAbstract ifa1 = (IntentFilterAbstract) aa2.features.get(comp1);
			
			for (String comp2 : aa2.features.keySet())
			{
				if (comp1.equals(comp2))
				{
					continue;
				}
				
				IntentFilterAbstract ifa2 = (IntentFilterAbstract) aa2.features.get(comp2);
				
				for (IntentFilterItem ifi1 : ifa1.intentFilters)
				{
					for (IntentFilterItem ifi2 : ifa2.intentFilters)
					{
						Set<String> tmp = CommonUtils.cloneSet(ifi1.actions);
						tmp.retainAll(ifi2.actions);
						
						if (! tmp.isEmpty())
						{
							System.out.println("[Explanation](Duplicated Capability) " + tmp + " for " + comp1 + " and " + comp2);
						}
					}
				}
			}
			
		}
	}
}
