package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/* Holds a rule of the form <nonterminal> : a1a2...an, where all ai are Symbols.
 * Rules containing the OR symbol '|' must be split into multiple Rule objects.
 */
public class Rule {
	// The Nonterminal that appears on the left of a replacement rule
	private Nonterminal leftSymbol;
	
	// The sequence of symbols that leftSymbol can be replaced with
	private List<Symbol> rightSymbols;
	
	/* Creates a new Rule with the specified leftSymbol and a Collection of rightSymbols.
	 * leftSymbol - The Nonterminal on the left side of the replacement rule
	 * rightSymbols - A Collection of the symbols on the right side of the replacement rule.
	 */
	public Rule(Nonterminal leftSymbol, Collection<Symbol> rightSymbols) {
		this.leftSymbol = leftSymbol;
		this.rightSymbols = new ArrayList<Symbol>(rightSymbols);
	}
	
	/* Appends the specified Symbol to the end of the right side.
	 * symbol - The next Symbol to append
	 */
	public void add(Symbol symbol) {
		this.rightSymbols.add(symbol);
	}
	
	/* Getter for this rule's leftmost result symbol.
	 */
	public Symbol getLeft(){
		return this.rightSymbols.get(0);
	}
	
	/* Getter for this rule's nonterminal.
	 */
	public Nonterminal getLeftSide() {
		return leftSymbol;
	}
	
	/* Getter for the list of right hand symbols.
	 */
	public List<Symbol> getRightSymbols() {
		return this.rightSymbols;
	}
	
	/* Returns true if this Rule has immediate left recursion, false otherwise.
	 */
	public boolean hasLeftRecursion() {
		return this.leftSymbol.equals(rightSymbols.get(0));
	}
	
	@Override
	/* Defines the printing behavior as <nonterminal> -> <symbol1><symbol2>...<symboln>
	 */
	public String toString() {
		String str = this.leftSymbol + " -> ";
		for (Symbol symbol : this.rightSymbols)
			str += symbol;
		return str;
	}
}
