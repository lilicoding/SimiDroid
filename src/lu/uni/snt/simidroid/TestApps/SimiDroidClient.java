package lu.uni.snt.simidroid.TestApps;

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
	}
}
