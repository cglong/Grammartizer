package com.github.cglong.Grammartizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProgramReader {

	String input;
	ArrayList<Terminal> inputtokens = new ArrayList<Terminal>();
	ArrayList<Terminal> terminals;
	Terminal INTNUM;
	Terminal ID;
 	
	public ProgramReader(String filename, ArrayList<Terminal>t)
	{
		this.terminals = t;
		this.INTNUM = new Terminal("INTNUM");
		this.ID = new Terminal("ID");
		File f = new File(filename);
		try {
			Scanner scan = new Scanner(f);
			this.input = scan.nextLine();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(-1);
		}
		inputCrunch(input);
	}//end constructor

	private void inputCrunch(String s)
	{
		Boolean found = false;
		for(int a = s.length()-1; a > -1; a--)
		{
			String w1 = s.substring(0, a+1);
			for(Terminal t : terminals)
			{
				if(t.getName().equals(w1))
				{
					inputtokens.add(t);
					found = true;
					break;
				}
			}
			
			if(!found)
				{
				if(w1.matches("[0-9]*"))
					{inputtokens.add(INTNUM);
					found = true;}
				else if(w1.matches("([a-z]|[A-Z])*"))
					{inputtokens.add(ID);
					found = true;}
				}
			
			if(found)
			{
				if(a < s.length()-1)
				{
					String w2 = s.substring(a+1).trim();
					if(w2.length()>0)
						inputCrunch(w2);
				}
				return;
			}
		
		}
		System.out.println("Unable to recognize token: " + s);
		System.exit(-1);
	}//end input crunch
	
	public ArrayList<Terminal> getInputTokens()
	{
		return inputtokens;
	}
}//end class
