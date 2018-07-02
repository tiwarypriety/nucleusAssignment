package com.nucleus.execution;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;

import com.nucleus.dao.CustomerDAO;
import com.nucleus.dao.CustomerDAOI;
import com.nucleus.pojo.Customer;
import com.nucleus.validation.Validation;
import com.nucleus.validation.ValidationI;

public class Rejection 
{
	static int number;
	HashSet<String> set=new HashSet<String>();
	FileWriter out = new FileWriter("d:/newErrorLog.txt",true);
	Rejection(BufferedReader br,char ch) throws NumberFormatException, IOException, SQLException
	{
		ValidationI v = new Validation();
		Customer cus = new Customer();
		CustomerDAOI dao = new CustomerDAO();
		String c;
		
		while((c = br.readLine())!=null)							
		{		
		String s[] = c.split("~",-1);
		cus.setCode(s[0]);
		cus.setName(s[1]);
		cus.setAddress1(s[2]);
		cus.setAddress2(s[3]);
		cus.setPin_code(Integer.parseInt(s[4]));
		cus.setEmail(s[5]);
		cus.setContact_number(Long.parseLong(s[6]));
		cus.setPrimary_contact_person(s[7]);
		cus.setRecordStatus(s[8]);
		cus.setFlag(s[9]);
		cus.setCreateDate(s[10]);
		cus.setCreatedBy(s[11]);
		cus.setModifiedDate(s[12]);
		cus.setModifiedBy(s[13]);
		cus.setAuthorizedDate(s[14]);
		cus.setAuthorizedBy(s[15]);	
		
try
{					
if(ch=='R')
if(v.checkNull(s))
{
	if(v.checkName(cus.getName()))
	{
		if(v.checkPinCode(cus.getPin_code()))
		{
				if(v.checkEmailFormat(cus.getEmail()))
				{
						if(v.checkRecordStatus(cus.getRecordStatus()))
						{
								if(v.checkFlag(cus.getFlag()))
								{
										if(v.checkContact(cus.getContact_number()))
										{
										if(v.code(cus,set))	
										{								
				
										int num= dao.insertRecord(cus);
										set.add(cus.getCode());
										System.out.println("record inserted "+num);
										}
										else
										{		
											FileWriter out = new FileWriter("d:/newErrorLog.txt",true);
											for(int i = 0;i<s.length;i++)
											{						 
												out.write(s[i]);
												out.write(" ");
											}
											out.append(System.lineSeparator()); 
	
											out.flush();
										}	
										}
										else
										{
											out.write("Error in contact");
										}
								}
								else
								{
									out.write("Error in flag");
								}
						}
						else
						{
							out.write("Error in Reord status");
						}
				}
				else
				{
					out.write("Error in email");
				}
		}
		else
		{
			out.write("Error in pin code");
		}
	}
	else
	{
		out.write("Error in name");
	}
}
else
{
	out.write("null value found in record");
}
if(ch=='F')
{
	if(v.checkNull(s)&&v.checkName(cus.getName())&&v.checkPinCode(cus.getPin_code())&&v.checkEmailFormat(cus.getEmail())&&v.checkRecordStatus(cus.getRecordStatus())&&v.checkFlag(cus.getFlag())&&v.checkContact(cus.getContact_number())&&v.code(cus,set))	
	{
		
		number= dao.insertRecord(cus);
		set.add(cus.getCode());
		number++;
	 FileWriter out1 = new FileWriter("d:/newErrorLog1.txt",true);
	 for(int i = 0;i<s.length;i++)
	 	{						 
		 		out1.write(s[i]);
		 		out1.write(" ");		 
		 	}
		 out1.append(System.lineSeparator()); 
			out1.flush();
	 	}
	}
	else
	{
		break;
	}	
	
}		
catch(Exception e)
	{
		e.printStackTrace();
	}

		}
		
			if(ch=='F')
			{
				dao.rollBack();
			}
			if(ch=='R')
			{
				dao.committ();
			}

	
	}
}
