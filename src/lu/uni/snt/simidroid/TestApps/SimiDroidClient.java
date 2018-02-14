package lu.uni.snt.simidroid.TestApps;

import java.io.File;

public class SimiDroidClient {

	public static void main(String[] args) throws Exception 
	{
		if (args.length == 0)
		{
			throw new RuntimeException("Parameter Error!");
		}
		
		Config.init();
		
		if (args.length == 1)
		{
			//DIR
			String dirPath = args[0];
			
			File dir = new File(dirPath);
			
			File[] files = dir.listFiles();
			
			if (files.length < 2)
			{
				throw new RuntimeException("Stop analysis: Less than two apps available in the directory!");
			}
			
			Config.appPathes = new String[files.length];
			for (int i = 0; i < files.length; i++)
			{
				Config.appPathes[i] = files[i].getAbsolutePath();
			}
		}
		else
		{
			Config.appPathes = new String[args.length];
			for (int i = 0; i < args.length; i++)
			{
				Config.appPathes[i] = args[i];
			}
		}
		
		
		if (Config.appPathes.length == 2)
		{
			SimiDroidClient4PW.start();
		}
		else
		{
			SimiDroidClient4MI.start();
		}
	}
}
