import java.util.ArrayList;
/**
 * GeneNode of GeneSearch. Helps keep track of genes and their associated info
 * @author Muyao
 * Version 2.3
 */
public class GeneNode {
	public String geneName;
	public ArrayList<String> type;
	public ArrayList<String> diseases;
	public ArrayList<String> tabs;
	public GeneNode(String name)
	{
		geneName=name;
		diseases = new ArrayList<String>();
		type = new ArrayList<String>();

	}
	public void addDistease(String d)
	{
		for(int k = 0;k<diseases.size();k++)
			if(diseases.get(k).equalsIgnoreCase(d))
				return;
		diseases.add(d);
	}
	public void addType(String t)
	{
		if(!type.contains(t))
			type.add(t);
	}

}

