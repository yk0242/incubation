package com.yk0242.labs.htmltag;

import java.util.ArrayList;
import java.util.List;

public class A {
	public String href;
	public String target;
	public String className;
	private List<String[]> proplist = new ArrayList<String[]>();
	
	public void setProperty(String pname, String pval, String priority){
		proplist.add(new String[]{pname,pval});
	}
	public String getPropertyValue(String pname){
		String ret = null;
		for(String[] sa : proplist){
			if(sa[0]==pname){
				ret=sa[1];
				break;
			}
		}
		return ret;
	}
	
	public void setAttribute(String pname, String pval){
		proplist.add(new String[]{pname,pval});
	}
	public String getAttribute(String pname){
		String ret = null;
		for(String[] sa : proplist){
			if(sa[0]==pname){
				ret=sa[1];
				break;
			}
		}
		return ret;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("href=");
		sb.append(href);
		sb.append("\ntarget=");
		sb.append(target);
		sb.append("\nclass name = ");
		sb.append(className);
		sb.append("\nProperties list : \n");
		for(String[] sa : proplist){
			sb.append("  ");
			sb.append(sa[0]);
			sb.append(":");
			sb.append(sa[1]);
			sb.append(" ;\n");
		}
		return sb.toString();
	}
}
