package com.FinalInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class RequirementsClass {
	
	private String projectId="";
	private String p_info="";
	private String Frequency="";
	private int durationNum=-1;
	private String durationFreq="";
	private HashMap<String,ArrayList<String>> category_subcategoriesHM;
	private  HashMap<String,String>Attributes;
	private ArrayList<ArrayList<String>> aggregateInfo;
	private HashMap<String, HashMap<String, String>> category_attribute;
	private ArrayList<String> Categories;
	private ArrayList<String> changeType;
	
	public RequirementsClass(String projectid,String infoName)
	{
		this.projectId=projectid;
		this.p_info=infoName;
		
		category_subcategoriesHM =new HashMap<String,ArrayList<String>>();
		Attributes = new HashMap<String,String>();
		Categories= new ArrayList<String>();
		changeType= new ArrayList<String>();
		aggregateInfo =new ArrayList<ArrayList<String>>();
		category_attribute = new HashMap<String, HashMap<String, String>>();
	}
	
	public void setFrequency(String freq)
	{
		this.Frequency=freq;
	}

	public void setDurationNumber(int durationNum)
	{
		this.durationNum=durationNum;
	}
	
	public void setDurationFreq(String durationFreq)
	{
		this.durationFreq=durationFreq;
	}
	
	public void setcategory_subcategory(HashMap<String,ArrayList<String>> category_subcategoriesHM)
	{
		this.category_subcategoriesHM.putAll(category_subcategoriesHM);
	}
	
	public void setAttribute(HashMap<String,String> Attributes)
	{
		this.Attributes.putAll(Attributes);
	}

	public void setAggregateInfo(ArrayList<ArrayList<String>>aggregateInfo)
	{
		this.aggregateInfo=aggregateInfo;
	}
	
	public void set_categoryAttribute(HashMap<String, HashMap<String, String>> category_attribute)
	{
		this.category_attribute=category_attribute;
	}

	public void setchangeType(ArrayList<String> changeType){this.changeType=changeType;}

	public ArrayList<String> getchangeType(){return this.changeType;}
	
	public void setCategories(ArrayList<String> categories)
	{
		this.Categories=categories;
	}
	
	public String getp_info()
	{
		return this.p_info;
	}

	public String getProjectId()
	{
		return projectId;
	}

	

	public String getFrequency()
	{
		return Frequency;
	}

	
	
	public int getDurationNum()
	{
		return this.durationNum;
	}
	
	public String getDurationFreq()
	{
		return this.durationFreq;
	}
	
	public HashMap<String,ArrayList<String>> getCategory_subcategory()
	{
		return category_subcategoriesHM;
		
	}
	
	public  HashMap<String,String> getAttributes()
	{
		return Attributes;
	}
	
	public ArrayList<ArrayList<String>> getAggregateInfo()
	{
		return aggregateInfo;
	}
	
	public HashMap<String, HashMap<String, String>> getCategory_attribute()
	{
		return this.category_attribute;
	}
	
	public ArrayList<String> getCategories()
	{
		return this.Categories;
	}
 
}
