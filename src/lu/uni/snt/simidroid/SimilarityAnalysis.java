package lu.uni.snt.simidroid;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

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
					System.out.println("[CHANGE] " + feature + ":" + src.toString() + "-->" + dest.toString());
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
	
	public double computeSimilarityScore()
	{
		return computeSimilarityScore(identicalFeatures.size(), similarFeatures.size(), newFeatures.size(), deletedFeatures.size());
	}
	
	public double computeSimilarityScore(int identicalNum, int similarNum, int newNum, int deletedNum)
	{
		int total = identicalNum + similarNum + newNum + deletedNum;
		
		double score1 = (double) identicalNum / (double) (total - deletedNum);
		double score2 = (double) identicalNum / (double) (total - newNum);
		
		return score1 > score2 ? score1 : score2;
	}

	protected JSONObject makeResult(){
		JSONObject r = new JSONObject();
		JSONObject c = new JSONObject();
		JSONObject v = new JSONObject();
		
		double similarityScore = computeSimilarityScore(identicalFeatures.size(), similarFeatures.size(), newFeatures.size(), deletedFeatures.size());
		
		c.put("identical", String.valueOf(identicalFeatures.size()));
		c.put("similar", String.valueOf(similarFeatures.size()));
		c.put("new", String.valueOf(newFeatures.size()));
		c.put("deleted", String.valueOf(deletedFeatures.size()));
		c.put("simiScore", String.valueOf(similarityScore));
		r.put("conclusion", c);

		JSONArray identical = new JSONArray();
		for(String feature : identicalFeatures){
			identical.put(feature);
		}
		v.put("identical", identical);
		JSONArray similar = new JSONArray();
		for(String feature : similarFeatures){
			similar.put(feature);
		}
		v.put("similar", similar);
		JSONArray newFt = new JSONArray();
		for(String feature : newFeatures){
			newFt.put(feature);
		}
		v.put("new", newFt);
		JSONArray deleted = new JSONArray();
		for(String feature : deletedFeatures){
			deleted.put(feature);
		}
		v.put("deleted", deleted);
		r.put("verbose", v);
		return r;
	}
	
	public void output(boolean verbose){
		JSONObject r = this.makeResult();
		JSONObject c = r.getJSONObject("conclusion");
		StringBuilder sb = new StringBuilder();
		
		sb.append("==>" + this.getClass().getName() + "|" + aa1.appName.replace(".apk", "") + "-" + aa2.appName.replace(".apk", ""));
		System.out.println(sb.toString());
		for(String key : c.keySet()){
			System.out.println(key + ": " + c.get(key));
		}
		if(verbose){
			JSONObject v = r.getJSONObject("verbose");
			for(String key : v.keySet()){
				System.out.println("------------------------------------------------------------------------");
				System.out.println(key + ":");
				JSONArray a = v.getJSONArray(key);
				for(int i=0;i<a.length();i++){
					System.out.println("    " + a.get(i));
				}
			}
		}
	}
	
	public void writeResultAsJSON(String path){
		Path p = Paths.get(path);
		try(BufferedWriter writer = Files.newBufferedWriter(p)){
			writer.write(this.makeResult().toString(4));
			System.out.println("result write at: " + p.toAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
