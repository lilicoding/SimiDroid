package lu.uni.snt.simidroid;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lu.uni.snt.simidroid.utils.CommonUtils;

public class SimilarityAnalysis 
{
	protected AppAbstract aa1, aa2;
	
	public Set<String> identicalFeatures = new HashSet<String>();
	public Set<String> similarFeatures = new HashSet<String>();
	public Set<String> newFeatures = new HashSet<String>();
	public Set<String> deletedFeatures = new HashSet<String>();

	public void compare(AppAbstract aa1, AppAbstract aa2)
	{
		this.aa1 = aa1;
		this.aa2 = aa2;
		
		Map<String, Object> features1 = CommonUtils.cloneMap(aa1.features);
		Map<String, Object> features2 = CommonUtils.cloneMap(aa2.features);
		
		preAnalysis(features1, features2);
		
		for (String feature : features1.keySet())
		{
			if (features2.containsKey(feature))
			{
				Object src = features1.get(feature);
				Object dest = features2.get(feature);
				
				if (identical(src, dest))
				{
					if (feature.contains("myapplication"))
						System.out.println("-->" + feature);
					identicalFeatures.add(feature);
				}
				else
				{
					similarFeatures.add(feature);
				}
			}
			else
			{
				deletedFeatures.add(feature);
			}
		}
		
		newFeatures = CommonUtils.cloneSet(features2.keySet());
		newFeatures.removeAll(features1.keySet());
		
		postAnalysis();
	}
	
	protected boolean identical(Object src, Object dest)
	{
		return src.toString().equals(dest.toString());
	}
	
	protected void preAnalysis(Map<String, Object> features1, Map<String, Object> features2) { }
	
	protected void postAnalysis() { }
	
	public double computeSimilarityScore(int identicalNum, int similarNum, int newNum, int deletedNum)
	{
		int total = identicalNum + similarNum + newNum + deletedNum;
		
		double score1 = (double) identicalNum / (double) (total - deletedNum);
		double score2 = (double) identicalNum / (double) (total - newNum);
		
		return score1 > score2 ? score1 : score2;
	}
	
	public void output()
	{
		StringBuilder sb = new StringBuilder();
		
		double similarityScore = computeSimilarityScore(identicalFeatures.size(), similarFeatures.size(), newFeatures.size(), deletedFeatures.size());
		
		sb.append("==>" + this.getClass().getName() + "|" + aa1.appName.replace(".apk", "") + "-" + aa2.appName.replace(".apk", "") + "," + identicalFeatures.size() + "," + similarFeatures.size() + "," + newFeatures.size() + "," + deletedFeatures.size() + "," + similarityScore + "\n");
		sb.append("identical: " + identicalFeatures.size() + "\n");
		sb.append("similar: " + similarFeatures.size() + "\n");
		sb.append("new: " + newFeatures.size() + "\n");
		sb.append("deleted: " + deletedFeatures.size() + "\n");
		sb.append("simiScore: " + similarityScore);
		
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
			sb.append("new:" + "\n");
			for (String feature : newFeatures)
			{
				sb.append("  " + feature + "\n");
			}
			
			sb.append("\n");
			sb.append("deleted:" + "\n");
			for (String feature : deletedFeatures)
			{
				sb.append("  " + feature + "\n");
			}
			
			System.out.println(sb.toString());
		}
	}
}
