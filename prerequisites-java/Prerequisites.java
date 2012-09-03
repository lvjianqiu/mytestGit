import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public class Prerequisites {

	
	public static void main(){
		
	}
	public String[] orderClasses(String[] param0) {
		class Node{
			public ArrayList<Node> pres;
			public int depends;
			public String depName;
			public String depNum;
			public String[] testString;
			
			public Node(String name){
				pres = new ArrayList<Node>();
				depends = 0;
				depName = "";
				depNum = "";
				testString = new String[200];
				for(int i=0; i<name.length(); i++){
					if(!Character.isDigit(name.charAt(i))){
						depName+=name.charAt(i);
					}else{
						depNum+=name.charAt(i);
					}
				}
			}
		}
		
		class NodeComparator implements Comparator<Node>{

			public int compare(Node o1, Node o2) {
				
				if(Integer.parseInt(o1.depNum) > Integer.parseInt(o2.depNum)){
					return 1;
				}else if(Integer.parseInt(o1.depNum) < Integer.parseInt(o2.depNum)){
					return -1;
				}
				
				if(o1.depName.compareTo(o2.depName)>0){
					return 1;
				}else if(o1.depName.compareTo(o2.depName)<0){
					return -1;
				}else{
					return 0;
				}
			}
		}
		
		TreeSet<Node> activeSet = new TreeSet<Node>(new NodeComparator());
		HashMap<String,Node> wholeNode = new HashMap<String,Node>();
		
		for(int i =0; i<param0.length; i++){
			String[] pres = param0[i].split(":| ");
			boolean hasDepends = false;
			Node self = null;
			for(int j=0; j< pres.length; j++){
				if(j>0){
					if(pres[j].trim().length() > 0){
						pres[0] = pres[j];
						hasDepends = true;
						Node other = null;
						if(wholeNode.containsKey(pres[j].trim())){
							other = wholeNode.get(pres[j].trim());
							
						}else{
							other = new Node(pres[j].trim());
							pres[0] = other.depName;
							pres[1] = other.depNum;
							pres[2] = pres[j];
							return pres;
//							wholeNode.put(pres[j].trim(), other);
						}
						self.depends++;
						other.pres.add(self);
					}
				}else{
					if(! wholeNode.containsKey(pres[j].trim()) ){
						self = new Node(pres[j].trim());
						wholeNode.put(pres[j].trim(), self);
					}else{
						self = wholeNode.get(pres[j].trim()) ;
					}
				}
			}
			if(!hasDepends){
				activeSet.add(self);
			}
		}
		Node current = null;
		String[] finalResult = new String[20];
		int i = 0;
		while((current = activeSet.first())!=null){
			activeSet.remove(current);
			finalResult[i] = current.depName+current.depNum;
			i++;
			Iterator<Node> it = current.pres.iterator();
			while(it.hasNext()){
				Node dep = it.next();
				dep.depends--;
				if(dep.depends==0){
					activeSet.add(dep);
				}
			}
		}
		
		if(i<wholeNode.size()){
			return new String[0];
		}else{
			return finalResult;
		}
	}

}
