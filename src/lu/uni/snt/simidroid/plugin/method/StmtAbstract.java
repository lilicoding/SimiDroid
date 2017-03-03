package lu.uni.snt.simidroid.plugin.method;

import soot.Value;
import soot.ValueBox;
import soot.jimple.AssignStmt;
import soot.jimple.BreakpointStmt;
import soot.jimple.DefinitionStmt;
import soot.jimple.EnterMonitorStmt;
import soot.jimple.ExitMonitorStmt;
import soot.jimple.GotoStmt;
import soot.jimple.IdentityStmt;
import soot.jimple.IfStmt;
import soot.jimple.InvokeStmt;
import soot.jimple.LookupSwitchStmt;
import soot.jimple.MonitorStmt;
import soot.jimple.NopStmt;
import soot.jimple.NumericConstant;
import soot.jimple.RetStmt;
import soot.jimple.ReturnStmt;
import soot.jimple.ReturnVoidStmt;
import soot.jimple.Stmt;
import soot.jimple.StringConstant;
import soot.jimple.SwitchStmt;
import soot.jimple.TableSwitchStmt;
import soot.jimple.ThrowStmt;

/**
 * 
AssignStmt-->51157
InvokeStmt-->17523
DefinitionStmt-->11036
IfStmt-->8542
ReturnVoidStmt-->5189
ReturnStmt-->4040
GotoStmt-->2765
ThrowStmt-->419
ExitMonitorStmt-->387
EnterMonitorStmt-->164
LookupSwitchStmt-->117
NopStmt-->1
 * 

AssignStmt.class, 
InvokeStmt.class, 
DefinitionStmt.class,
IfStmt.class, 
ReturnVoidStmt.class, 
ReturnStmt.class,
GotoStmt.class,
ThrowStmt.class
ExitMonitorStmt.class,
EnterMonitorStmt.class, 
LookupSwitchStmt.class,
NopStmt.class,

IdentityStmt.class, 
MonitorStmt.class, 
RetStmt.class, 
SwitchStmt.class, 
TableSwitchStmt.class, 
BreakpointStmt.class, 

 * @author li.li
 *
 */
public class StmtAbstract 
{
	public String stmt = "";
	
	public String type = "";
	
	public String methodSignature;
	public String fieldSignature;
	public String arrayRefBase;
	public String constantString;
	public String constantNumber;
	
	public boolean containsInvokeExpr;
	public boolean containsFieldRef;
	public boolean containsArrayRef;
	public boolean containsConstantString;
	public boolean containsConstantNumber;
	
	public StmtAbstract(Stmt stmt)
	{
		this.stmt = stmt.toString();
		setType(stmt);
		
		if (stmt.containsInvokeExpr())
		{
			containsInvokeExpr = true;
			this.methodSignature = stmt.getInvokeExpr().getMethod().getSignature();
		}
		else if (stmt.containsFieldRef())
		{
			containsFieldRef = true;
			this.fieldSignature = stmt.getFieldRef().getField().getSignature();
		}
		else if (stmt.containsArrayRef())
		{
			containsArrayRef = true;
			this.arrayRefBase = stmt.getArrayRef().getBase().toString();
		}
		
		for (ValueBox vb : stmt.getUseAndDefBoxes())
		{
			Value v = vb.getValue();
			
			if (v instanceof StringConstant)
			{
				containsConstantString = true;
				constantString += v.toString();
			}
			else if (v instanceof NumericConstant)
			{
				containsConstantNumber = true;
				constantNumber += v.toString();
			}
		}
	}
	
	public boolean equalsTo(StmtAbstract sa)
	{
		if (! this.type.equals(sa.type))
		{
			if (sa.containsInvokeExpr)
			{
				System.out.println("[Explanation](New Method Call) " + this.stmt + " -> " + sa.stmt);
			}
			else if (sa.containsFieldRef)
			{
				System.out.println("[Explanation](New Field Access) " + this.stmt + " -> " + sa.stmt);
			}
			
			return false;
		}
		
		if (this.containsInvokeExpr && sa.containsInvokeExpr)
		{
			if (! this.methodSignature.equals(sa.methodSignature))
			{
				System.out.println("[Explanation](Method Mismatch) " + this.stmt + " -> " + sa.stmt);
				
				return false;
			}
		}
		else if (this.containsFieldRef && sa.containsFieldRef)
		{
			if (! this.fieldSignature.equals(sa.fieldSignature))
			{
				System.out.println("[Explanation](Field Mismatch) " + this.stmt + " -> " + sa.stmt);
				
				return false;
			}
		}
		else if (this.containsArrayRef && sa.containsArrayRef)
		{
			if (! this.arrayRefBase.equals(sa.arrayRefBase))
			{
				System.out.println("[Explanation](ArrayRef Mismatch) " + this.stmt + " -> " + sa.stmt);
				
				return false;
			}
		}
		
		if (this.containsConstantString && sa.containsConstantString)
		{
			if (! this.constantString.equals(sa.constantString))
			{
				System.out.println("[Explanation](ConstantString Mismatch) " + this.stmt + " -> " + sa.stmt);
				
				return false;
			}
		}
		
		if (this.containsConstantNumber && sa.containsConstantNumber)
		{
			if (! this.constantNumber.equals(sa.constantNumber))
			{
				System.out.println("[Explanation](ConstantNumber Mismatch) " + this.stmt + " -> " + sa.stmt);
				
				return false;
			}
		}
		
		return true;
	}
	
	public void setType(Stmt stmt)
	{
		if (stmt instanceof AssignStmt)
		{
			this.type = "AssignStmt";
		}
		else if (stmt instanceof BreakpointStmt)
		{
			this.type = "BreakpointStmt";
		}
		else if (stmt instanceof DefinitionStmt)
		{
			this.type = "DefinitionStmt";
		}
		else if (stmt instanceof EnterMonitorStmt)
		{
			this.type = "EnterMonitorStmt";
		}
		else if (stmt instanceof ExitMonitorStmt)
		{
			this.type = "ExitMonitorStmt";
		}
		else if (stmt instanceof GotoStmt)
		{
			this.type = "GotoStmt";
		}
		else if (stmt instanceof IdentityStmt)
		{
			this.type = "IdentityStmt";
		}
		else if (stmt instanceof IfStmt)
		{
			this.type = "IfStmt";
		}
		else if (stmt instanceof InvokeStmt)
		{
			this.type = "InvokeStmt";
		}
		else if (stmt instanceof LookupSwitchStmt)
		{
			this.type = "LookupSwitchStmt";
		}
		else if (stmt instanceof MonitorStmt)
		{
			this.type = "MonitorStmt";
		}
		else if (stmt instanceof NopStmt)
		{
			this.type = "NopStmt";
		}
		else if (stmt instanceof RetStmt)
		{
			this.type = "RetStmt";
		}
		else if (stmt instanceof ReturnStmt)
		{
			this.type = "ReturnStmt";
		}
		else if (stmt instanceof ReturnVoidStmt)
		{
			this.type = "ReturnVoidStmt";
		}
		else if (stmt instanceof SwitchStmt)
		{
			this.type = "SwitchStmt";
		}
		else if (stmt instanceof TableSwitchStmt)
		{
			this.type = "TableSwitchStmt";
		}
		else if (stmt instanceof ThrowStmt)
		{
			this.type = "ThrowStmt";
		}
		else 
		{
			this.type = "Other";
		}
	}
}
