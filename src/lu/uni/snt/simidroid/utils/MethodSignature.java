package lu.uni.snt.simidroid.utils;

public class MethodSignature 
{
	private String signature = "";
	
	private String pkg;
	private String cls;
	private String methodName;
	private String returnType;
	
	private int parameterNumber;
	private String[] parameterTypes;
	
	public MethodSignature(String signature)
	{
		this.signature = signature;
		parse(signature);
	}
	
	public boolean validateSignature(String signature)
	{
		String regex = "<\\w: *\\w *<*\\w>*(.*)>";
		return signature.matches(regex);
	}
	
	public void parse(String signature)
	{
		int pos = signature.indexOf(':');
		cls = signature.substring(1, pos);

		
		ClassSignature cs = new ClassSignature(cls);
		pkg = cs.getPackageName();
		
		String[] strs = signature.split(" ");
		pos = strs[2].indexOf('(');
		methodName = strs[2].substring(0, pos);
		
		returnType = strs[1];
		
		pos = signature.indexOf('(');
		int endPos = signature.lastIndexOf(')');
		String parameters = signature.substring(pos+1, endPos);
		
		if (parameters.isEmpty())
		{
			parameterNumber = 0;
			parameterTypes = null;
		}
		else
		{
			if (parameters.contains(","))
			{
				String params[] = parameters.split(",");
				parameterNumber = params.length;
				parameterTypes = new String[parameterNumber];
				
				for (int i = 0; i < parameterNumber; i++)
				{
					parameterTypes[i] = params[i].trim();
				}
			}
			else
			{
				parameterNumber = 1;
				parameterTypes = new String[parameterNumber];
				parameterTypes[0] = parameters.trim();
			}
		}
	}

	public String getCompactSignature()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(cls);
		sb.append("." + methodName);
		for (String paramType : parameterTypes)
		{
			sb.append("_" + paramType);
		}
		
		return sb.toString();
	}
	
	public String getSignature() {
		return signature;
	}

	public String getPkg() {
		return pkg;
	}

	public String getCls() {
		return cls;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getReturnType() {
		return returnType;
	}

	public int getParameterNumber() {
		return parameterNumber;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}
}
