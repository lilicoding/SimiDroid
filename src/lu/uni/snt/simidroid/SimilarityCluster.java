package lu.uni.snt.simidroid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimilarityCluster 
{
	public double averageSimilarity(double[][] similarities)
	{
		double total = 0;
		double count = 0;
		
		for (int i = 0; i < similarities.length; i++)
		{
			for (int j = 0; j < similarities[i].length; j++)
			{
				if (similarities[i][j] > 0)
				{
					total += similarities[i][j];
					count++;
				}
			}
		}
		
		return (double) total / count;
	}
	
	public Set<Set<String>> cluster(double[][] similarities, String[] applications, double threshold)
	{
		Map<Integer, Set<String>> clusters = new HashMap<Integer, Set<String>>();
		Map<String, Integer> app2clusterSeqs = new HashMap<String, Integer>();
		int count = 1;
		
		for (int i = 0; i < similarities.length; i++)
		{
			for (int j = 0; j < similarities[i].length; j++)
			{
				if (similarities[i][j] >= threshold)
				{
					if (app2clusterSeqs.containsKey("a" + i) && app2clusterSeqs.containsKey("a" + j))
					{
						int seq1 = app2clusterSeqs.get("a" + i);
						int seq2 = app2clusterSeqs.get("a" + j);
						
						if (seq1 != seq2)
						{
							Set<String> apps1 = clusters.get(seq1);
							Set<String> apps2 = clusters.get(seq2);
							
							apps1.addAll(apps2);
							clusters.put(seq1, apps1);
							
							for (String app : apps2)
							{
								app2clusterSeqs.put(app, seq1);
							}
							
							clusters.remove(seq2);
						}
					}
					
					if (app2clusterSeqs.containsKey("a" + i) && ! app2clusterSeqs.containsKey("a" + j))
					{
						int seq = app2clusterSeqs.get("a" + i);
						
						Set<String> apps = clusters.get(seq);
						apps.add("a" + j);
						clusters.put(seq, apps);
						
						app2clusterSeqs.put("a" + j, seq);
					}
					
					if (app2clusterSeqs.containsKey("a" + j) && ! app2clusterSeqs.containsKey("a" + i))
					{
						int seq = app2clusterSeqs.get("a" + j);
						
						Set<String> apps = clusters.get(seq);
						apps.add("a" + i);
						clusters.put(seq, apps);
						
						app2clusterSeqs.put("a" + i, seq);
					}
					
					if (! app2clusterSeqs.containsKey("a" + i) && ! app2clusterSeqs.containsKey("a" + j))
					{
						Set<String> apps = new HashSet<String>();
						apps.add("a" + i);
						apps.add("a" + j);
						
						count = 1 + count;
						
						clusters.put(count, apps);
						
						app2clusterSeqs.put("a" + i, count);
						app2clusterSeqs.put("a" + j, count);
					}
				}
			}
		}
		
		Set<Set<String>> set = new HashSet<Set<String>>();
		for (int key : clusters.keySet())
		{
			Set<String> appIDs = clusters.get(key);
			Set<String> apps = new HashSet<String>();
			for (String id : appIDs)
			{
				id = id.replace("a", "").trim();
				String app = applications[Integer.parseInt(id)];
				apps.add(app);
			}
			
			set.add(apps);
		}
		
		return set;
	}
}
