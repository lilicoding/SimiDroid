package lu.uni.snt.simidroid.TestApps;

<<<<<<< HEAD
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
=======
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import lu.uni.snt.simidroid.AppAbstract;
import lu.uni.snt.simidroid.FeatureExtraction;
import lu.uni.snt.simidroid.SimilarityAnalysis;
import lu.uni.snt.simidroid.plugin.component.ComponentFeatureExtraction;
import lu.uni.snt.simidroid.plugin.component.ComponentSimilarityAnalysis;
import lu.uni.snt.simidroid.plugin.method.MethodFeatureExtraction;
import lu.uni.snt.simidroid.plugin.method.MethodSimilarityAnalysis;
import lu.uni.snt.simidroid.plugin.resource.ResourceFeatureExtraction;
import lu.uni.snt.simidroid.plugin.resource.ResourceSimilarityAnalysis;

public class SimiDroidClient {

	public static void main(String[] args) throws FileNotFoundException 
	{
		Config.app1Path = args[0];
		Config.app2Path = args[1];
		Config.androidJars = args[2];
		String toPath = args[3];
		
		PrintStream nps = new PrintStream(new FileOutputStream("/dev/null"));
		System.setOut(nps);
//		System.setErr(nps);
		
		// Compare Methods
		compare(new MethodFeatureExtraction(Config.app1Path), 
				new MethodFeatureExtraction(Config.app2Path), 
//				new MethodSimilarityAnalysis("res/libs91.txt"),
				new MethodSimilarityAnalysis(),
				toPath + "_method");
		// Compare Resources
		compare(new ResourceFeatureExtraction(Config.app1Path), 
				new ResourceFeatureExtraction(Config.app2Path), 
				new ResourceSimilarityAnalysis(),
				toPath + "_rec");
		// Compare Components
		compare(new ComponentFeatureExtraction(Config.app1Path), 
				new ComponentFeatureExtraction(Config.app2Path), 
				new ComponentSimilarityAnalysis(),
				toPath + "_comp");
	}

	public static void compare(FeatureExtraction appA1, FeatureExtraction appA2, SimilarityAnalysis sa, String path)
	{
		appA1.extract();
		AppAbstract aa1 = appA1.getAppAbstract();
		
		appA2.extract();
		AppAbstract aa2 = appA2.getAppAbstract();
		
		sa.compare(aa1, aa2);

//		sa.output(true);
//		sa.output(false);
		sa.writeResultAsJSON(path);
>>>>>>> f563a8f5229d007dfa2be3e323e377aee32032f6
	}
}
