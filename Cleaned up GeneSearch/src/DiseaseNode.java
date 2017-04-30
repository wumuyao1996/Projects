import java.util.ArrayList;
/**
 * DiseaseNode of GeneSearch. Helps keep track of Diseases and their associated info
 * @author Muyao
 * Version 2.3
 */
public class DiseaseNode {
	public final String diseaseName;
	public ArrayList<GeneNode> genes;
	public ArrayList<String> tabs;
	public DiseaseNode(String name)
	{
		diseaseName=name;
		genes = new ArrayList<GeneNode>();
	}
	public void addGene(GeneNode d)
	{
		for(int k = 0;k<genes.size();k++)
		if(genes.contains(d))
			return;
		genes.add(d);
	}
	
	public String toString(){
		return diseaseName;
		
	}
	public String getGenes()
	{
		String ret = "";
		for(int k = 0; k<genes.size();k++)
		{
			ret=ret+genes.get(k).geneName +"("+ genes.get(k).type+")" + ", ";
		}
		return ret;
	}

	

}

