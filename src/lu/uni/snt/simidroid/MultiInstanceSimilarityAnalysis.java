package lu.uni.snt.simidroid;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lu.uni.snt.simidroid.TestApps.Config;
import lu.uni.snt.simidroid.TestApps.SimiDroidOut;
import lu.uni.snt.simidroid.utils.CommonUtils;

public class MultiInstanceSimilarityAnalysis 
{
	protected AppAbstract[] aas;
	double[][] distances;

	public Set<String> identicalFeatures = new HashSet<String>();
	public Set<String> similarFeatures = new HashSet<String>();
	public Set<String> strictSimilarFeatures = new HashSet<String>();
	public Map<Integer, Set<String>> newFeatures = new HashMap<Integer, Set<String>>();
	
	public void compare(AppAbstract[] aas, Class<? extends SimilarityAnalysis> saClass) throws Exception
	{
		this.aas = aas;
		
		//To compute distances between every two apps
		System.out.println("To compute distances between every two apps");
		
		PrintStream defaultStream = System.out;
		
		SimilarityAnalysis[][] analyses = new SimilarityAnalysis[aas.length][aas.length];
		for (int i = 0; i < aas.length; i++)
		{
			for (int j = i+1; j < aas.length; j++)
			{
				SimiDroidOut out = new SimiDroidOut(System.out, aas[i].appName + "->" + aas[j].appName);
				System.setOut(out);
				
				SimilarityAnalysis sa = null;
				if (saClass.getName().equals("MethodSimilarityAnalysis") && null != Config.librarySetPath)
				{
					sa = saClass.getConstructor(String.class).newInstance(Config.librarySetPath);
				}
				else
				{
					sa = saClass.newInstance();
				}
				
				sa.compare(aas[i], aas[j]);
				sa.output(true);
				
				analyses[i][j] = analyses[j][i] = sa;
				
				System.setOut(defaultStream);
			}
		}
		
		distances = new double[aas.length][aas.length];
		
		for (int i = 0; i < aas.length; i++)
		{
			for (int j = i+1; j < aas.length; j++)
			{
				double score = analyses[i][j].computeSimilarityScore();
				distances[i][j] = distances[j][i] = score;
				System.out.println("The distance for app_{" + i + ", " + j + "} is " + distances[i][j]);
			}
		}
		
		//To compute identical and similar features
		System.out.println("To compute identical and similar features");
		for (String key : aas[0].features.keySet())
		{
			boolean sameContent = true;
			boolean differentContent = true;
			
			for (int i = 1; i < aas.length; i++)
			{
				if (aas[i].features.containsKey(key))
				{
					if (analyses[0][i].identical(aas[0].features.get(key), aas[i].features.get(key)))
					{
						differentContent = false;
					}
					else
					{
						sameContent = false;
					}
				}
			}
			
			//The compared two items are always the same.
			if (sameContent)
			{
				identicalFeatures.add(key);
			}
			else
			{
				similarFeatures.add(key);
				
				//The compared two items are always different.
				if (true == differentContent)
				{
					strictSimilarFeatures.add(key);
				}
			}
		}
		
		//To compute new features for each app
		System.out.println("To compute new features for each app");
		for (int i = 0; i < aas.length; i++)
		{
			Set<String> featureKeys = CommonUtils.cloneSet(aas[i].features.keySet());
			
			for (int j = 0; j < aas.length; j++)
			{
				if (i == j)
				{
					continue;
				}
				
				featureKeys.removeAll(aas[j].features.keySet());
			}
			
			newFeatures.put(i, featureKeys);
		}
	}
	
	public void output()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("==>" + this.getClass().getName() + "," + identicalFeatures.size() + "," + similarFeatures.size() + "," + strictSimilarFeatures.size() + "\n");
		sb.append("identical: " + identicalFeatures.size() + "\n");
		sb.append("similar: " + similarFeatures.size() + "\n");
		sb.append("strictSimilar: " + strictSimilarFeatures.size() + "\n");
		
		for (int i = 0; i < aas.length; i++)
		{
			sb.append("new (a" + i + "): " + newFeatures.get(i).size() + "\n");
		}
		
		sb.append("Similarity Scores: " + "\n");
		for (int i = 0; i < aas.length; i++)
		{
			sb.append(aas[i].appName + ": ");
			for (int j = 0; j < aas.length; j++)
			{
				sb.append("\t" + distances[i][j]);
			}
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	public void output(boolean verbose)
	{
		output();
		
		if (verbose)
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("\n");
			sb.append("-------------------------------------------------------------------------------------" + "\n");
			sb.append("identical:" + "\n");
			for (String feature : identicalFeatures)
			{
				sb.append("  " + feature + "\n");
			}
			
			sb.append("\n");
			sb.append("-------------------------------------------------------------------------------------" + "\n");
			sb.append("similar:" + "\n");
			for (String feature : similarFeatures)
			{
				sb.append("  " + feature + "\n");
			}
			
			sb.append("\n");
			sb.append("-------------------------------------------------------------------------------------" + "\n");
			sb.append("strictSimilar:" + "\n");
			for (String feature : strictSimilarFeatures)
			{
				sb.append("  " + feature + "\n");
			}
			
			sb.append("\n");
			sb.append("new:" + "\n");
			for (Map.Entry<Integer, Set<String>> entry : newFeatures.entrySet())
			{
				sb.append("a" + entry.getKey() + ": " + "\n");
				for (String feature : entry.getValue())
				{
					sb.append("  " + feature + "\n");
				}
			}
			
			System.out.println(sb.toString());
		}
	}
}
