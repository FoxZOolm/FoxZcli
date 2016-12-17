package foxz.cli;

import java.util.TreeMap;

public class Helps extends TreeMap<String, Help> {

	/**
	 * 
	 */
	public String help(AbsHelp ah){
		StringBuilder sb=new StringBuilder();
		for(Help h:this.values()){
			sb.append(h.toString(ah));
		}
		return sb.toString();
	}
	private static final long serialVersionUID = -3978719538639144008L;

}
