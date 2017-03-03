package lu.uni.snt.simidroid.plugin.resource;

import java.util.Map;

import lu.uni.snt.simidroid.FeatureExtraction;
import lu.uni.snt.simidroid.utils.ApkUtils;

public class ResourceFeatureExtraction extends FeatureExtraction
{	
	public ResourceFeatureExtraction(String appPath) 
	{
		super(appPath);
	}

	public void extract()
	{
		Map<String, String> pathHashes = ApkUtils.getPathHashes(this.appPath);
		appAbstract.features.putAll(pathHashes);
	}
}
