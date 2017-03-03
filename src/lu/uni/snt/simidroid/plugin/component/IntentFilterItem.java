package lu.uni.snt.simidroid.plugin.component;

import java.util.Set;
import java.util.TreeSet;

public class IntentFilterItem
{
	public Set<String> actions = new TreeSet<String>();
	public Set<String> categories = new TreeSet<String>();
	
	
	@Override
	public String toString() 
	{
		int counter = 0;
		
		StringBuilder sb = new StringBuilder();
		for (String action : actions)
		{
			if (counter == 0)
				sb.append(action);
			else
				sb.append(":" + action);
			counter++;
		}
		
		sb.append("/");
		counter = 0;
		for (String category : categories)
		{
			if (counter == 0)
				sb.append(category);
			else
				sb.append(":" + category);
			counter++;
		}
		
		return sb.toString();
	}
}
