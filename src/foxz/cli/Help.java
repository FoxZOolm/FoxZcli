package foxz.cli;

public class Help {
	public Help(String name,String h,Boolean r,String d){
		help=h;
		require=r;
		bydefault=d;
		this.name=name;
	}
	public String help;
	public Boolean require;
	public String bydefault;
	public String name;
	public String toString(AbsHelp h){
		return h.Help(name, help, bydefault, require);
	}

}
