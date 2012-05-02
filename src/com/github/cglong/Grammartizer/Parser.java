package com.github.cglong.Grammartizer;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class Parser {

	public static void parse(ArrayList<Terminal> inputtokens, Nonterminal startvar, Terminal epsilon, ParseTable parsetable)
	{
		
		Stack<Symbol> stack = new Stack<Symbol>();
		stack.push(startvar);
		
		while(!stack.empty())
		{
			Symbol sym = stack.pop();
			if(!sym.equals(epsilon))
			{
				if(sym.isTerminal())
				{
					if(inputtokens.isEmpty())
						fail("Expected Token: " + sym.getName());
					else if(sym.equals(inputtokens.get(0)))
						inputtokens.remove(0);
					else
						fail("Unexpected Token: " + inputtokens.get(0).getName() + " Expected: " + sym.getName());			
				}
				else
				{
					Rule r;
					if(inputtokens.isEmpty())
						{
						r = parsetable.get((Nonterminal) sym, epsilon);
						if(r==null)
							fail("Token Expected");
						}
					else
						{	
						r = parsetable.get((Nonterminal)sym, inputtokens.get(0));
						if(r==null)
							fail("Unexpected Token: " + inputtokens.get(0).getName());
						}
					List<Symbol> rightsymbols = r.getRightSymbols();
					for(int a = rightsymbols.size()-1; a > -1; a--)
						stack.push(rightsymbols.get(a));
				}			
			}
		}//end while
		
		if(!inputtokens.isEmpty())
			fail("Unexpected Token: " + inputtokens.get(0).getName());
		System.out.println("Successful parse");
		
	}//end validate
	
	private static void fail(String s)
	{
		System.out.println(s);
		System.exit(-1);
	}
}//end class
