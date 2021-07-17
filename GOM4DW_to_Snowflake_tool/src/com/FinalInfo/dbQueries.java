package com.FinalInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

//import com.mysql.jdbc.PreparedStatement;

//import sun.print.PSPrinterJob.EPSPrinter;

public class dbQueries {

	private Connection con;
	private String p_id="";
	private boolean inserted=true;
	boolean duplicateInfoName=false;
	public dbQueries(Connection con)
	{
		this.con=con;
	}

	public boolean  InsertDataIntoDatabase(RequirementsClass rc)
	{
		java.sql.PreparedStatement pst;

		// insert in p_info table
		String sql = "insert into p_info(p_info,history_freq,history_durationNum,history_duration) values(?,?,?,?)";
		try {
			pst= con.prepareStatement(sql);
			pst.setString(1, rc.getp_info());
			if(rc.getFrequency()!=null && rc.getFrequency().length()>0)
				pst.setString(2, rc.getFrequency());
			else
				pst.setNull(2, java.sql.Types.NULL);

			if(rc.getDurationNum()!=-1)
				pst.setInt(3, rc.getDurationNum());
			else
				pst.setNull(3, java.sql.Types.NULL);

			if(rc.getDurationFreq()!=null && rc.getDurationFreq().length()>0)
				pst.setString(4, rc.getDurationFreq());
			else
				pst.setNull(4, java.sql.Types.NULL);
			pst.execute();
			duplicateInfoName=true;

		} 
		catch(SQLIntegrityConstraintViolationException e1)
		{
			e1.printStackTrace();
			 inserted =false;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			inserted= false;
		}


		// insert into category_subcategory
		
		try
		{

			ArrayList<String> categories =rc.getCategories();

			ArrayList<String> changeType=rc.getchangeType();

			if(categories!=null && categories.size()>0)
			{
				for(int i=0;i<categories.size();i++)
				{
					sql = "insert into category_subcategory(p_info,category_name,category_id) values(?,?,?)";
					pst= con.prepareStatement(sql);
					pst.setString(1, rc.getp_info());
					if(checkforValue(categories.get(i).trim()))
					{
						pst.setString(2, categories.get(i).trim());
						pst.setString(3, rc.getp_info()+"_"+categories.get(i).trim());
					}
					pst.execute();

					sql = "insert into category_changeType(p_info,category_name,change_type,category_id) values(?,?,?,?)";
					pst= con.prepareStatement(sql);
					pst.setString(1, rc.getp_info());
					if(checkforValue(categories.get(i).trim())) {
						pst.setString(2, categories.get(i).trim());
						pst.setString(3, changeType.get(i).trim());
						pst.setString(4, rc.getp_info()+"_"+categories.get(i).trim());
					}
					System.out.println(changeType);
					//Timestamp time_stamp=new Timestamp(System.currentTimeMillis());
					//pst.setString(3,CURRENT_TIMESTAMP);
					pst.execute();

				}
			}


		}catch(SQLException e)
		{
			e.printStackTrace();
			inserted= false;
		}
		
		
		try
		{
			HashMap<String,ArrayList<String>> cat_subCat=rc.getCategory_subcategory();
			if(cat_subCat!=null && cat_subCat.size()>0)
			{
				for(String cat:cat_subCat.keySet())
				{
					ArrayList<String> subcat_list=cat_subCat.get(cat);
					if(subcat_list!=null && subcat_list.size()>0)
					{
						for(int i=0;i<subcat_list.size();i++)
						{
							sql = "update category_subcategory set  subcategory_of=?,subcategory_of_id=? where category_id=?";
							pst= con.prepareStatement(sql);
							pst.setString(1,cat.trim());
							pst.setString(2, rc.getp_info()+"_"+cat.trim());
							pst.setString(3, rc.getp_info()+"_"+subcat_list.get(i).trim());	
							pst.executeUpdate();
						}
					}
					
				}
			}
			
		}catch(SQLException e)
		{
			e.printStackTrace();
			inserted=false;
		}
//		try {
//			String ctg="";
//			ArrayList<String> categories =rc.getCategories();
//			HashMap<String,ArrayList<String>> cat_subCat=rc.getCategory_subcategory();
//			if(categories!=null && categories.size()>0)
//			{
//				for(int i=0;i<categories.size();i++)
//				{
//					ctg=categories.get(i);
//					if(cat_subCat.containsKey(ctg))
//					{
//						ArrayList<String> subCat_list=cat_subCat.get(ctg);
//						if(subCat_list!=null && subCat_list.size()>0)
//						{
//							for(int j=0;j<cat_subCat.size();j++)
//							{
//								sql = "insert into category_subcategory(p_info,category_name,subcategory_of,category_id,subcategory_of_id) values(?,?,?,?,?)";
//								pst= con.prepareStatement(sql);
//								pst.setString(1, rc.getp_info());
//								if(checkforValue(subCat_list.get(j)))
//								{
//									pst.setString(2, subCat_list.get(j));
//									pst.setString(4, rc.getp_info()+"_"+subCat_list.get(j));
//								}									
////								else
////									pst.setNull(2, java.sql.Types.NULL); 
//
//								if(checkforValue(ctg))
//								{
//									pst.setString(3,ctg );
//									pst.setString(5, rc.getp_info()+"_"+ctg);
//								}									
//								else {
//									pst.setNull(3, java.sql.Types.NULL);
//									pst.setNull(5, java.sql.Types.NULL);
//								}
//								
//								
//								pst.execute();
//							}
//
//						}
//					}
//					else
//					{
//						sql = "insert into category_subcategory(p_info,category_name,subcategory_of,category_id,subcategory_of_id) values(?,?,?,?,?)";
//						pst= con.prepareStatement(sql);
//						pst.setString(1, rc.getp_info());
//						if(checkforValue(ctg))
//						{
//							pst.setString(2, ctg);
//							pst.setString(4, rc.getp_info()+"_"+ctg);
//						}							
////						else
////							pst.setNull(2, java.sql.Types.NULL);
//
//						pst.setNull(3, java.sql.Types.NULL);
//						pst.setNull(5, java.sql.Types.NULL);
//						pst.execute();
//					}
//
//
//				}
//			}
			//			HashMap<String,ArrayList<String>> cat_subCat=rc.getCategory_subcategory();
			//			if(cat_subCat!=null && cat_subCat.size()>0)
			//			{
			//				for(String cat: cat_subCat.keySet())
			//				{
			//					ArrayList<String> subCat_list=cat_subCat.get(cat);
			//					if(subCat_list!=null && subCat_list.size()>0)
			//					{
			//						for(int i=0;i<cat_subCat.size();i++)
			//						{
			//							sql = "insert into category_subcategory(p_info,category_name,subcategory_of) values(?,?,?)";
			//							pst= con.prepareStatement(sql);
			//							pst.setString(1, rc.getp_info());
			//							if(checkforValue(cat))
			//								pst.setString(2, cat);
			//							else
			//								pst.setNull(2, java.sql.Types.NULL);
			//							
			//							if(checkforValue(subCat_list.get(i)))
			//							   pst.setString(3,subCat_list.get(i) );
			//							else
			//								pst.setNull(3, java.sql.Types.NULL);
			//							pst.execute();
			//						}
			//
			//					}
			//				}
			//			}
//		} 

//		catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			inserted= false;
//			//return false;
//		}



		// insert into infoAttribute

		try
		{

			HashMap<String,String> attributes=rc.getAttributes();
			if(attributes!=null && attributes.size()>0)
			{
				for(String attr:attributes.keySet())
				{
					sql = "insert into infoAttribute(p_info,attribute,dataType) values(?,?,?)";
					pst= con.prepareStatement(sql);
					pst.setString(1, rc.getp_info());
					pst.setString(2, attr);
					pst.setString(3, attributes.get(attr));
					pst.execute();
				}
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}


		//category_attributes
		try
		{
			HashMap<String, HashMap<String, String>>category_attribute=	rc.getCategory_attribute();
			if(category_attribute!=null && category_attribute.size()>0)
			{
				for(String cat :category_attribute.keySet())
				{
					HashMap<String, String> innerHM=category_attribute.get(cat);
					if(innerHM!=null && innerHM.size()>0)
					{
						for(String attr : innerHM.keySet())
						{
							sql = "insert into category_attributes(p_info,category_name,attribute_name,dataType,category_id) values(?,?,?,?,?)";
							pst= con.prepareStatement(sql);
							pst.setString(1, rc.getp_info());

							if(checkforValue(cat))
							{
								pst.setString(2, cat);
								pst.setString(5, rc.getp_info()+"_"+cat);
							}
								
							else
								pst.setNull(2, java.sql.Types.NULL);

							if(checkforValue(attr))
								pst.setString(3, attr);
							else
								pst.setNull(3, java.sql.Types.NULL);

							if(checkforValue(innerHM.get(attr)))
								pst.setString(4, innerHM.get(attr));
							else
								pst.setNull(4, java.sql.Types.NULL);
							pst.execute();
						}

					}
				}

			}

		}catch(Exception e)
		{
			e.printStackTrace();
			inserted= false;
		}


		//aggregate
		try
		{
			ArrayList<ArrayList<String>> aggr=rc.getAggregateInfo();
			int id=1;
			int computed_from_id=1;
			int category_id=1;
			if(aggr!=null && aggr.size()>0)
			{
				for(int i=0;i<aggr.size();i++)
				{

					sql = "insert into aggregate(p_info,aggregate_id,aggregate_name,history_freq,history_durationNum,history_duration) values(?,?,?,?,?,?)";
					pst= con.prepareStatement(sql);
					pst.setString(1, rc.getp_info());
					pst.setInt(2, id);
					pst.setString(3, aggr.get(i).get(0));

					if(checkforValue(aggr.get(i).get(3)))
						pst.setString(4, aggr.get(i).get(3));
					else
						pst.setNull(4,java.sql.Types.NULL);

					if(checkforValue(aggr.get(i).get(4)))
						pst.setInt(5,Integer.parseInt(aggr.get(i).get(4)));
					else
						pst.setNull(5,java.sql.Types.NULL);

					if(checkforValue(aggr.get(i).get(5)))
						pst.setString(6, aggr.get(i).get(5));
					else
						pst.setNull(6,java.sql.Types.NULL);

					pst.execute();


					// computed_from table
					if(checkforValue(aggr.get(i).get(1)))
					{
						String computed_from[];
						if(aggr.get(i).get(1).contains(","))
						{
							computed_from=aggr.get(i).get(1).split(",");
						}
						else
						{
							computed_from =new String[1];
							computed_from[0]= aggr.get(i).get(1);
						}
						for(int k=0;k<computed_from.length;k++)
						{
							sql= "insert into aggregate_computedFrom(p_info,aggregate_id,attribute_name) values(?,?,?)";
							pst= con.prepareStatement(sql);
							//pst.setInt(1, computed_from_id++);
							pst.setString(1, rc.getp_info());
							pst.setInt(2, id);
							pst.setString(3, computed_from[k]);

							pst.execute();
						}

					}


					//aggregate_over_category table
					if(checkforValue(aggr.get(i).get(1)))
					{
						String category[];
						if(aggr.get(i).get(2).contains(","))
						{
							category=aggr.get(i).get(2).split(",");
						}
						else
						{
							category =new String[1];
							category[0]= aggr.get(i).get(2);
						}

						for(int k=0;k<category.length;k++)
						{
							sql= "insert into aggregate_over_category(p_info,aggregate_id,category,category_id) values(?,?,?,?)";
							pst= con.prepareStatement(sql);
							//pst.setInt(1, category_id++);
							pst.setString(1, rc.getp_info());
							pst.setInt(2, id);
							//System.out.println(category[k]);
							if(checkforValue(category[k].trim().toLowerCase()))
							{
								pst.setString(3, category[k].trim().toLowerCase());
								pst.setString(4, rc.getp_info()+"_"+category[k].trim().toLowerCase());
							}
								
							else
								pst.setNull(3,java.sql.Types.NULL);

							pst.execute();
						}

					}


					id++;

				}
			}

		}catch(Exception e)
		{
			e.printStackTrace();
			inserted= false;
		}



		return inserted;

	}

	public boolean checkforValue(String s)
	{
		if(s==null || s.equals(" ") || s.length()==0 ||s.equals(""))
			return false;

		return true;
	}


	public String [] fetch_pInfo_FromDataBase(String projectID)
	{
		java.sql.PreparedStatement pst;
		Statement stmt;
		ArrayList<String> pInfo_list=new ArrayList<String>();
		String [] p_info_list = null;
		String sql = "select p_info from p_info";


		try {
			pst= con.prepareStatement(sql);
			stmt = con.createStatement();
			ResultSet  resultset = stmt.executeQuery(sql);
			if(resultset!=null)
			{
				while (resultset.next()) {
					String result=resultset.getString("p_info");
					pInfo_list.add(result);
				}
			}

			p_info_list=new String[pInfo_list.size()];
			for(int i=0;i<pInfo_list.size();i++)
			{
				p_info_list[i]=pInfo_list.get(i);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return p_info_list;
	}


	public RequirementsClass  getInfo(String info) throws SQLException
	{
		System.out.println(" GETTING INFO FROM DB.....");
		RequirementsClass rc= new RequirementsClass(p_id, info);
		java.sql.PreparedStatement pst;

		// from p_info Table
	//	System.out.println("INfo table");
		String sql="SELECT * from p_info where p_info=? ";

		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, info);
			ResultSet rs = pst.executeQuery();

			while (rs.next())
			{
				//String p_info = rs.getString("p_Info");
				String history_freq= rs.getString("history_freq");
				int history_durationNum= rs.getInt("history_durationNum");
				String history_duration =rs.getString("history_duration");

				if(checkNullForString(history_freq))
				{
					rc.setFrequency(history_freq);
				}

				if(checkNullForInt(history_durationNum))
				{
					rc.setDurationNumber(history_durationNum);
				}

				if(checkNullForString(history_duration))
				{
					rc.setDurationFreq(history_duration);
				}

			}

			pst.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}

		// get Info attributes
		//System.out.println("infoAttributes table");
		try
		{
			HashMap<String ,String> attributes=new HashMap<String,String>();
			sql="SELECT attribute,dataType from infoattribute where p_info=?";
			pst = con.prepareStatement(sql);
			pst.setString(1, info);
			ResultSet rs = pst.executeQuery();

			while (rs.next())
			{

				String attribute= rs.getString("attribute");
				String dataType=rs.getString("dataType");
				if(checkNullForString(attribute) && checkNullForString(dataType))
				{
					if(!attributes.containsKey(attribute))
					{
						attributes.put(attribute, dataType);
					}
				}

			}
			rc.setAttribute(attributes);
			pst.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		// category and subcategory
		//System.out.println("category and subcategory table");
		try
		{
			HashMap<String,ArrayList<String>> cat_subCat= new HashMap<String,ArrayList<String>> ();
			ArrayList<String> categories=new ArrayList<String>(); 
			sql="SELECT category_name,subcategory_of from category_subcategory where p_info=?";
			pst = con.prepareStatement(sql);
			pst.setString(1, info);
			ResultSet rs = pst.executeQuery();

			while (rs.next())
			{
				String cat= rs.getString("category_name");
				String subCat=rs.getString("subcategory_of");

				if(checkNullForString(cat))
				{
					categories.add(cat);

					if(checkNullForString(subCat))
					{
						if(cat_subCat.containsKey(subCat))
						{
							ArrayList<String> al=cat_subCat.get(subCat);
							al.add(cat);
							cat_subCat.put(subCat, al);
						}
						else
						{
							ArrayList<String> al=new ArrayList<String>();
							al.add(cat);
							cat_subCat.put(subCat, al);
						}
					}
				}
			}
			rc.setcategory_subcategory(cat_subCat);
			rc.setCategories(categories);
			pst.close();

		}catch(Exception e)
		{
			e.printStackTrace();
		}


		// category attributes
		//System.out.println("category attributes table");
		try
		{
			HashMap<String, HashMap<String, String>> cat_attr =new HashMap<String, HashMap<String, String>>();
			sql="SELECT * from category_attributes where p_info=?";
			pst = con.prepareStatement(sql);
			pst.setString(1, info);
			ResultSet rs = pst.executeQuery();

			while (rs.next())
			{
				String cat= rs.getString("category_name");
				String cat_att=rs.getString("attribute_name");
				String dataType=rs.getString("dataType");

				if(checkNullForString(cat) && checkNullForString(cat_att) && checkNullForString(dataType))
				{
					if(cat_attr.containsKey(cat))
					{
						HashMap<String, String> inner=cat_attr.get(cat);
						if(!inner.containsKey(cat_att))
						{
							inner.put(cat_att, dataType);
						}
						cat_attr.put(cat, inner);
					}
					else
					{
						HashMap<String, String> inner =new HashMap<String, String>();
						if(!inner.containsKey(cat_att))
						{
							inner.put(cat_att, dataType);
						}
						cat_attr.put(cat, inner);
					}
				}


			}

			rc.set_categoryAttribute(cat_attr);
			pst.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}


		// get Aggregate Info
		//System.out.println("Aggregate table");
		ArrayList<Integer> aggr_ids=new ArrayList<Integer>();
		ArrayList<ArrayList<String>> aggr_info= new ArrayList<ArrayList<String>>();
		try
		{

			ArrayList<String> inner = null  ;

			sql="SELECT * from aggregate where p_info=?";
			pst = con.prepareStatement(sql);
			pst.setString(1, info);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				int aggr_id=rs.getInt("aggregate_id");
				String aggr_name =rs.getString("aggregate_name");
				String hist_freq =rs.getString("history_freq");
				int history_durNum=rs.getInt("history_durationNum");
				String history_dur=rs.getString("history_duration");

				if(checkforValue(aggr_name))
				{
					inner = new ArrayList<String>(Collections.nCopies(7, " "));
					inner.set(1, aggr_name);

				}

				if(checkNullForInt(aggr_id))
				{
					inner.set(0, String.valueOf(aggr_id));
					aggr_ids.add(aggr_id);
				}
				if(checkNullForString(hist_freq) && inner!=null)
				{
					inner.set(4, hist_freq);
				}

				if(checkNullForString(history_dur) && inner!=null)
				{
					inner.set(6, history_dur);
				}

				if(checkNullForInt(history_durNum) && inner!=null)
				{
					inner.set(5, String.valueOf(history_durNum));
				}

				aggr_info.add(inner);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		// aggregate computed from
		//		


		//System.out.println("aggregate computed from table");
		//		CharSequence[] agg_id_arr = new CharSequence[aggr_ids.size()]; 
		//		agg_id_arr= (CharSequence[])aggr_ids.toArray(agg_id_arr);
		StringBuffer ids = null;
		for(int i=0;i<aggr_ids.size();i++)
		{
			if(ids==null)
			{
				ids=new StringBuffer("?");
			}
			else
			{
				ids=ids.append(",?");
			}
		}

		//		char[] markers = new char[aggr_ids.size() * 2 - 1];
		//		for (int i = 0; i < markers.length; i++)
		//		    markers[i] = ((i %2)==0 ? '?' : ',');
		//Array array = con.createArrayOf("INT", aggr_ids.toArray());
		HashMap<Integer,StringBuffer> computedFrom=new   HashMap<Integer,StringBuffer>();
		try
		{

			//sql="SELECT aggregate_id,attribute_name from aggregate_computedfrom where p_info=? AND aggregate_id in ("+String.join(",",agg_id_arr)+")";
			sql="SELECT aggregate_id,attribute_name from aggregate_computedfrom where p_info=? AND aggregate_id in ( "+ids+")";
			pst = con.prepareStatement(sql);
			pst.setString(1, info);
			//pst.setInt(2, aggr_ids.get(0));
			//pst.setInt(3, aggr_ids.get(1));
			int idx = 2;
			for (int value : aggr_ids)
				pst.setInt(idx++, value);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				int aggr_id =rs.getInt("aggregate_id");
				String attr =rs.getString("attribute_name");
				if(checkNullForInt(aggr_id))
				{
					if(checkNullForString(attr))
					{
						StringBuffer c = null;
						if(computedFrom.containsKey(aggr_id))
						{
							c=computedFrom.get(aggr_id);
							c=c.append(","+attr);


						}
						else
						{

							c=new StringBuffer(attr);
						}
						computedFrom.put(aggr_id, c);
					}
				}

			}
			pst.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}


		// aggregate category
		//System.out.println("aggregate over cat table");
		HashMap<Integer,StringBuffer> overcategory=new   HashMap<Integer,StringBuffer>();
		try
		{

			//sql="SELECT aggregate_id,attribute_name from aggregate_computedfrom where p_info=? AND aggregate_id in ("+String.join(",",agg_id_arr)+")";
			sql="SELECT aggregate_id,category from aggregate_over_category where p_info=? AND aggregate_id in ("+ids+")";
			pst = con.prepareStatement(sql);
			pst.setString(1, info);
			int idx = 2;
			for (int value : aggr_ids)
				pst.setInt(idx++, value);
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				int aggr_id =rs.getInt("aggregate_id");
				String cat =rs.getString("category");
				if(checkNullForInt(aggr_id))
				{
					if(checkNullForString(cat))
					{
						StringBuffer c = null;
						if(overcategory.containsKey(aggr_id))
						{
							c=overcategory.get(aggr_id);
							c=c.append(","+cat);


						}
						else
						{
							c=new StringBuffer(cat);
						}
						overcategory.put(aggr_id, c);
					}
				}

			}
			pst.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}


		//create aggregate info arraylist
		ArrayList<String> inner = null  ;
		String id_str="";
		int id=-1;
		//String computed_from="";
		//String overcat="";
		for(int i=0;i<aggr_info.size();i++)
		{
			inner=aggr_info.get(i);
			id_str=inner.get(0);
			if(checkNullForString(id_str))
			{
				id=Integer.parseInt(id_str);
			}
			if(computedFrom.containsKey(id))
				inner.set(2, computedFrom.get(id).toString());
			if(overcategory.containsKey(id))
				inner.set(3, overcategory.get(id).toString());

			inner.remove(0);

		}
		//System.out.println("_-----------__"+aggr_info.get(0).size());
		rc.setAggregateInfo(aggr_info);

		return rc;

	}


	public void deleteFromdatabase(String p_info)
	{
		System.out.println("Delete from Database Method");
		java.sql.PreparedStatement pst;
		try
		{

			deletefromTable(p_info,"p_info");

			deletefromTable(p_info, "infoattribute");

			deletefromTable(p_info, "aggregate");

			deletefromTable(p_info, "aggregate_computedfrom");

			deletefromTable(p_info, "aggregate_over_category");

			deletefromTable(p_info, "category_attributes");

			deletefromTable(p_info, "category_subcategory");

		}catch(Exception e)
		{
                        System.out.println("deleted from table error");
			e.printStackTrace();
		}

	}

	public void deletefromTable(String p_info,String table_name)
	{
		
		java.sql.PreparedStatement pst;

		try {
                       System.out.println(p_info);
			String sql ="DELETE from "+ table_name +" where p_info=?";
			pst = con.prepareStatement(sql);
			pst.setString(1, p_info);
			pst.execute();
                        System.out.println("deleted from "+ table_name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("null")
	public boolean checkNullForString(String inp)
	{
		//		if(inp==null)
		//		{
		//			return false;
		//		}
		//if(inp!=null && !inp.equals(null) && inp.length()>0 && !inp.isBlank() )
		if(inp!=null && !inp.trim().isEmpty())
		{
			return true;
		}


		return false;
	}

	public boolean checkNullForInt(int inp)
	{
		if(inp>0)
		{
			return true;
		}

		return false;
	}


}
