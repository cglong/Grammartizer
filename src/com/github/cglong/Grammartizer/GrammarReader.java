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
				Terminal t = new Terminal(words[a]);
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
			g.setAllSymbols(allsymbols);
			
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
				words2 = words[1].split(" ");
				ArrayList<Symbol> rs = new ArrayList<Symbol>();
				for(String word : words2){
					for(Symbol sym: allsymbols){
						if(sym.getName().equals(word))
							{rs.add(sym);
							break;}
					}
				}
				Expression exp = new Expression(rs);
				g.addRule(ls, exp);
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
}//end class
