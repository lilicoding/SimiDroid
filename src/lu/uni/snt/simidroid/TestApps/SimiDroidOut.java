package lu.uni.snt.simidroid.TestApps;

import java.io.OutputStream;
import java.io.PrintStream;

public class SimiDroidOut extends PrintStream
{
	private String prefix = "";
	
	public SimiDroidOut(OutputStream out) 
	{
		super(out);
	}
	
	public SimiDroidOut(OutputStream out, String prefix)
	{
		super(out);
		this.prefix = prefix;
	}

	@Override
	public void print(String s) 
	{
		super.print(prefix + ": ");
		super.print(s);
	}
	
	@Override
	public void println(String s) 
	{
		super.print(prefix + ": ");
		super.println(s);
	}
}
