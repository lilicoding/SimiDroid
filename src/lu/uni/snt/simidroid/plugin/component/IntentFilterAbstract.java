package lu.uni.snt.simidroid.plugin.component;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author li.li
 *
 */
public class IntentFilterAbstract 
{
	public List<IntentFilterItem> intentFilters = new ArrayList<IntentFilterItem>();
	
	public boolean equalsTo(IntentFilterAbstract ifa)
	{
		if (this.intentFilters.size() != ifa.intentFilters.size())
		{
			return false;
		}
		
		for (IntentFilterItem ifi : this.intentFilters)
		{
			boolean matched = false;
			
			for (IntentFilterItem ifi2 : ifa.intentFilters)
			{
				if (ifi.toString().equals(ifi2.toString()))
				{
					matched = true;
					break;
				}
			}
			
			if (! matched)
			{
				return false;
			}
		}
		
		return true;
	}

	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		
		boolean first = true;
		
		for (IntentFilterItem ifi : this.intentFilters)
		{
			if (first)
			{
				sb.append(ifi.toString());
				first = false;
			}
			else
			{
				sb.append("," + ifi.toString());
			}
		}
		
		return sb.toString();
	}
	
	
}
