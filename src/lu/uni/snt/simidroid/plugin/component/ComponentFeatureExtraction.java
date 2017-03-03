package lu.uni.snt.simidroid.plugin.component;

import java.util.ArrayList;

import lu.uni.snt.simidroid.FeatureExtraction;
import lu.uni.snt.simidroid.plugin.component.manifest.AXmlNode;
import lu.uni.snt.simidroid.plugin.component.manifest.ProcessManifest;

public class ComponentFeatureExtraction extends FeatureExtraction
{	
	public ComponentFeatureExtraction(String appPath) 
	{
		super(appPath);
	}

	public void extract()
	{
		try 
		{
			ProcessManifest processManifest = new ProcessManifest(appPath);
			
			String pkgName = processManifest.getPackageName();
			
			processComponentAXmlNodeList(pkgName, "activity", processManifest.getActivities());
			processComponentAXmlNodeList(pkgName, "service", processManifest.getServices());
			processComponentAXmlNodeList(pkgName, "receiver", processManifest.getReceivers());
			processComponentAXmlNodeList(pkgName, "provider", processManifest.getProviders());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			// Change to Log
		}
	}
	
	private void processComponentAXmlNodeList(String pkgName, String componentType, ArrayList<AXmlNode> list)
	{
		for (AXmlNode node : list)
		{
			String componentName = node.getAttribute("name").getValue().toString();

			if (componentName.startsWith("."))
			{
				componentName = pkgName + componentName;
			}
			else 
			{
				if (! componentName.contains("."))
				{
					componentName = pkgName + "." + componentName;
				}
			}
			
			componentName = componentType + ":" + componentName;
			
			IntentFilterAbstract ifa = new IntentFilterAbstract();
			
			for (AXmlNode ifNode : node.getChildrenWithTag("intent-filter"))
			{
				IntentFilterItem ifi = new IntentFilterItem();
				
				for (AXmlNode actionNode : ifNode.getChildrenWithTag("action"))
				{
					String actionName = actionNode.getAttribute("name").getValue().toString();
					ifi.actions.add(actionName);
				}
				
				for (AXmlNode categoryNode : ifNode.getChildrenWithTag("category"))
				{
					String categoryName = categoryNode.getAttribute("name").getValue().toString();
					ifi.categories.add(categoryName);
				}
				
				ifa.intentFilters.add(ifi);
			}
			
			this.appAbstract.features.put(componentName, ifa);
		}
	}
}
