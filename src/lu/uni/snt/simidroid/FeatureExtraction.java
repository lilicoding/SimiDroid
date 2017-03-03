package lu.uni.snt.simidroid;

public abstract class FeatureExtraction
{	
	protected String appPath;
	protected AppAbstract appAbstract = null;
	
	public FeatureExtraction(String appPath)
	{
		this.appPath = appPath;
		
		String appName = appPath;
        if (appName.contains("/"))
        {
        	appName = appName.substring(appName.lastIndexOf('/')+1);
        }
        
        appAbstract = new AppAbstract();
        appAbstract.appName = appName;
	}
	
	public AppAbstract getAppAbstract()
	{
		return appAbstract;
	}
	
	public abstract void extract();
}
