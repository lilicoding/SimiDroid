package lu.uni.snt.simidroid.plugin.method;

import lu.uni.snt.simidroid.FeatureExtraction;
import lu.uni.snt.simidroid.TestApps.Config;
import soot.G;
import soot.PackManager;
import soot.Transform;
import soot.options.Options;

public class MethodFeatureExtraction extends FeatureExtraction
{	
	public MethodFeatureExtraction(String appPath) 
	{
		super(appPath);
	}

	public void extract()
	{
		String[] args =
        {
            "-process-dir", this.appPath,
            "-android-jars", Config.androidJars,
            "-ire",
            "-pp",
            "-allow-phantom-refs",
            "-w",
			"-p", "cg", "enabled:false"
        };
		
		G.reset();
		
		MethodFeatureTransformer transformer = new MethodFeatureTransformer();
		
		Options.v().set_src_prec(Options.src_prec_apk);
		Options.v().set_output_format(Options.output_format_none);
        PackManager.v().getPack("wjtp").add(new Transform("wjtp.MethodFeatureTransformer", transformer));
		
        soot.Main.main(args);
        
        this.appAbstract.features.putAll(transformer.getMethodFeatures());
	}
}
