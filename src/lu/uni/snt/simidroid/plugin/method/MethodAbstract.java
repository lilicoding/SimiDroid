package lu.uni.snt.simidroid.plugin.method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MethodAbstract 
{
	public String methodSignature = "";
	
	public List<StmtAbstract> stmts = new ArrayList<StmtAbstract>();
	
	public Set<String> callEdges = new HashSet<String>();
	
	public boolean equalsTo(MethodAbstract ma)
	{
		if (stmts.size() != ma.stmts.size())
		{
			System.out.println(methodSignature);
			System.out.println("SIZE: " + stmts.size() + "---->" + ma.stmts.size());
		}
		
		for (int i = 0; i < stmts.size(); i++)
		{
			try
			{
				StmtAbstract src = stmts.get(i);
				StmtAbstract dest = ma.stmts.get(i);
				
				if (! src.equalsTo(dest))
				{
					System.out.println("STMT: " + src.stmt + "---->" + dest.stmt);
					
					return false;
				}
			}
			catch (IndexOutOfBoundsException ex)
			{
				return false;
			}
			
		}
		
		return true;
	}
}
