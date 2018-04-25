package lu.uni.snt.simidroid.TestApps;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class Config 
{
	public static String[] appPathes;
	
	public static String app1Path;
	public static String app2Path;
	
	public static String androidJars;
	public static String librarySetPath;
	public static boolean librarySetExclusive = false;
	
	public static String resExtensionSetPath;
	public static boolean resExtensionSetExclusive = false;
	
	public static double simiThreshold = 0d;
	
	public static PluginName pulginName = PluginName.METHOD;
	public static Set<PluginName> supportedPlugins = new HashSet<PluginName>();
	
	public static final String SIMIDROID_CONFIG_PATH = "simidroid_config.xml";
	
	public static final String ELE_AndroidJarsPath = "AndroidJarsPath";
	public static final String ELE_Plugin = "Plugin";
	public static final String ELE_SIMI_THRESHOLD = "SimiThreshold";
	
	public static final String ELE_LibrarySetPath = "LibrarySetPath";
	public static final String ELE_ResExtensionSetPath = "ResExtensionSetPath";
	
	public static final String ATTR_Exclusive = "exclusive";
	
	public static void init()
	{
		SAXBuilder builder = new SAXBuilder();
		
		try
		{
			Document doc = (Document) builder.build(SIMIDROID_CONFIG_PATH);
			Element simidroid = doc.getRootElement();

			androidJars = simidroid.getChild(ELE_AndroidJarsPath).getText();
			
			Element threshold = simidroid.getChild(ELE_SIMI_THRESHOLD);
			if (null != threshold)
			{
				simiThreshold = Double.parseDouble(threshold.getText());
			}
			
			List<Element> pluginEles = simidroid.getChildren(ELE_Plugin);
			for (Element pluginEle : pluginEles)
			{
				String pluginName = pluginEle.getAttributeValue("name");
				
				if (pluginName.equals(PluginName.METHOD.getPluginName()))
				{
					supportedPlugins.add(PluginName.METHOD);
					
					Element librarySetPathEle = pluginEle.getChild(ELE_LibrarySetPath);
					if (null != librarySetPathEle)
					{
						librarySetPath = librarySetPathEle.getText();
						
						String exclusiveValue = librarySetPathEle.getAttributeValue(ATTR_Exclusive);
						if (null != exclusiveValue)
						{
							if ("true".equals(exclusiveValue))
							{
								librarySetExclusive = true;
							}
						}
					}
				}
				else if (pluginName.equals(PluginName.COMPONENT.getPluginName()))
				{
					supportedPlugins.add(PluginName.COMPONENT);
				}
				else if (pluginName.equals(PluginName.RESOURCE.getPluginName()))
				{
					supportedPlugins.add(PluginName.RESOURCE);
					
					Element resExtensionSetPathEle = pluginEle.getChild(ELE_ResExtensionSetPath);
					if (null != resExtensionSetPathEle)
					{
						resExtensionSetPath = resExtensionSetPathEle.getText();
						
						String exclusiveValue = resExtensionSetPathEle.getAttributeValue(ATTR_Exclusive);
						if (null != exclusiveValue)
						{
							if ("true".equals(exclusiveValue))
							{
								resExtensionSetExclusive = true;
							}
						}
					}
				}
				else
				{
					throw new RuntimeException("[SimiDroid_Config] Unrecognized plugin name!");
				}
			}
		}
		catch (Exception ex)
		{
			throw new RuntimeException("[SimiDroid_Config] Parse failed!");
		}
	}
}

enum PluginName 
{
	METHOD ("METHOD"),
	COMPONENT ("COMPONENT"),
	RESOURCE ("RESOURCE");
	
	private String pluginName;
	
	PluginName(String pluginName)
	{
		this.pluginName = pluginName;
	}

	public String getPluginName() 
	{
		return pluginName;
	}
}
