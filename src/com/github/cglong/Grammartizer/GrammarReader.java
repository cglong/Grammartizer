package com.github.cglong.Grammartizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GrammarReader {

	private Scanner scan;
	
	public GrammarReader(String s){
		File f = new File(s);
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("No file");
			System.exit(-1);			
		}	
	}//end constructor
	
	public Grammar readGrammar()
	{
		Grammar g = new Grammar();
		String[] words;
		ArrayList<Terminal> terminals = new ArrayList<Terminal>();
		ArrayList<Nonterminal> nonterminals = new ArrayList<Nonterminal>();
		ArrayList<Symbol> allsymbols = new ArrayList<Symbol>();
		
		try{
			String nextline = scan.nextLine();
			words = nextline.split(" ");
			if(!words[0].equals("%Tokens"))
				fail("%Tokens expected");
			for(int a = 1; a < words.length; a++)
			{
				Terminal t = new Terminal(notationSwitch(words[a]));
				terminals.add(t);
				allsymbols.add(t);
			}
			g.setTerminals(terminals);
			
			nextline = scan.nextLine();
			words = nextline.split(" ");
			if(!words[0].equals("%Non-terminals"))
				fail("%Non-terminals expected");
			nextline = scan.nextLine();
			String[] words2 = nextline.split(" ");
			if(!words2[0].equals("%Start"))
				fail("%Start expected");
			String startvar = words2[1];
			for(int a = 1; a < words.length; a++)
			{
				Nonterminal n = new Nonterminal(words[a]);
				nonterminals.add(n);
				allsymbols.add(n);
				if(words[a].equals(startvar))
					g.setStartvariable(n);
			}
			g.setNonterminals(nonterminals);
			
			nextline = scan.nextLine();
			if(!nextline.equals("%Rules"))
				fail("%Rules expected");
			
			while(scan.hasNextLine()){
				nextline = scan.nextLine();
				words = nextline.split(" : ", 2);
				Nonterminal ls = new Nonterminal("placeholder");
				for(Nonterminal nt : nonterminals){
					if(nt.getName().equals(words[0]))
						{ls = nt;
						break;}
				}

				words2 = words[1].split(" [|] ");
				for(String word : words2){
					ArrayList<Symbol> rs = new ArrayList<Symbol>();
					String[] words3 = word.split(" ");
					for(String word2 : words3){
					for(Symbol sym: allsymbols){
						if(sym.getName().equals(notationSwitch(word2)))
							{rs.add(sym);
							break;}
						}
					}
					Rule rule = new Rule(ls, rs);
					g.add(ls, rule);
				}

			}//end while
			
			
			
		} 
		catch(Exception e){
			fail();
		}
		return g;
	}//end read grammar
	
	private void fail()
	{
		System.out.println("Error reading file");
		System.exit(-1);
	}//end fail
	
	private void fail(String s)
	{
		System.out.println(s);
		System.exit(-1);
	}
	
	private String notationSwitch(String s)
	{
		if(s.equals("LEFTPAR"))
			return "(";
		else if(s.equals("RIGHTPAR"))
			return ")";
		else if(s.equals("ASSIGN"))
			return ":=";
		else if(s.equals("COMMA"))
			return ",";
		else if(s.equals("SEMICOLON"))
			return ";";
		else if(s.equals("PLUS"))
			return "+";
		else if(s.equals("MINUS"))
			return "-";
		else if(s.equals("MULTIPLY"))
			return "*";
		else if(s.equals("MODULO"))
			return "%";
		else
			return s;
	}
}//end class
