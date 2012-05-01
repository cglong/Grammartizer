package com.github.cglong.Grammartizer;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

/* Represents a nonterminal in a Context-Free Grammar - an intermediate in the derivation of a program from a set of grammar rules.
 * Contains an initially empty First set and Follow set, both of which need to be updated later.
 */
public class Nonterminal extends Symbol {
	// This Nonterminal's follow set - the set of characters that can come immediately after this Nonterminal.
	// Nonterminals are the only Symbols with a defined follow set.
	private Set<Terminal> followSet;
	
	/* Initializes this Nonterminal with the specified name and an initially empty first and follow set.
	 * name - The name used to reference this nonTerminal.  Should be encapsulated in < > symbols.
	 */
	public Nonterminal(String name) {
		super(name);
		this.followSet = new HashSet<Terminal>();
	}
	
	@Override
	/* Checks if this Symbol is a Terminal.
	 * Returns false, since this is not a Terminal.
	 */
	public boolean isTerminal() {
		return false;
	}
	
	@Override
	/* Getter for this Nonterminal's follow set.
	 */
	public Set<Terminal> getFollowSet() throws UnsupportedOperationException {
		return this.followSet;
	}
	
	@Override
	/* Given a Rule, updates the first set of this Nonterminal.
	 * Multiple iterations may be needed within a Grammar.
	 * rule - The rule to update the firstSet with.
	 */
	public boolean updateFirstSet(Rule rule) {
		Symbol first = rule.getRightSymbols().get(0);
		return this.getFirstSet().addAll(first.getFirstSet());
	}
	
	@Override
	/* Given a Rule, updates the follow set of this Nonterminal.
	 * Multiple iterations may be needed within a Grammar.
	 * rule - The rule to update this firstSet with.
	 */
	public boolean updateFollowSet(Rule rule, int index) throws UnsupportedOperationException {
		List<Symbol> symbols = rule.getRightSymbols();
		List<Symbol> followSymbols = symbols.subList(index, symbols.size());
		
		boolean changes = false;
		for (Symbol symbol : followSymbols)
			changes = this.getFollowSet().addAll(symbol.getFirstSet()) || changes;
		
		boolean hadEmpty = followSymbols.isEmpty() || this.getFollowSet().remove(new Nonterminal(""));
		if (hadEmpty)
			changes = this.getFollowSet().addAll(rule.getLeftSide().getFollowSet()) || changes;
		
		return changes;
	}
}
