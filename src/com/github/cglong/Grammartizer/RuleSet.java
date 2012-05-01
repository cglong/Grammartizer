package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.ArrayList;
import java.util.ListIterator;

/* Represents a set of rules for a single nonterminal.
 * A symbol with multiple derivations separated by | symbols will have one RuleSet, containing a rule for each of the possible derivations.
 */
public class RuleSet {
	// The Nonterminal that these rules contain derivations for.
	private Nonterminal symbol;

	// The set of derivation rules for the above Nonterminal.
	private Collection<Rule> rules;
	
	/* Creates a new RuleSet with a specified Nonterminal and an initially empty rule list.
	 * symbol - The symbol to create a RuleSet for.
	 */
	public RuleSet(Nonterminal symbol) {
		this.rules = new ArrayList<Rule>();
		this.symbol = symbol;
	}
	
	/* Adds the specified rule to this RuleSet.
	 * rule - The next rule to add.
	 */
	public void add(Rule rule) {
		this.rules.add(rule);
	}
	
	/* Gets the Nonterminal that this RuleSet uses.
	 */
	public Nonterminal getSymbol() {
		return this.symbol;
	}
	
	/* Getter for the collection of rules.
	 */
	public Collection<Rule> getRules() {
		return this.rules;
	}

	/* Returns true if this RuleSet has left recursion, false otherwise.
	 */
	public boolean hasLeftRecursion() {
		// Scan all rules in the RuleSet and check if left recursion exists.
		for (Rule rule : this.rules)
			if (rule.hasLeftRecursion())
				return true;
		return false;
	}
	
	/* Updates this RuleSet so that all follow sets contain accurate values.
	 * PRECONDITION: All rules have been added to this RuleSet
	 * PRECONDITION: This RuleSet cannot contain any left recursion.
	 * POSTCONDITION: All rules have their firstSet member correctly filled in.
	 */
	public boolean updateFirstSets() {
		boolean changes = false;
		for (Rule rule : this.rules)
			changes = this.symbol.updateFirstSet(rule) || changes;
		return changes;
	}
	
	/* Updates this RuleSet so that all follow sets contain accurate values.
	 * PRECONDITION: All rules have been added to this RuleSet.
	 * PRECONDITION: This RuleSet cannot contain any left recursion.
	 * POSTCONDITION: All rules have their followSet member correctly filled in.
	 */
	public boolean updateFollowSets() {
		boolean changes = false;
		for (Rule rule : this.rules) {
			ListIterator<Symbol> iter = rule.getRightSymbols().listIterator();
			while (iter.hasNext()) {
				Symbol symbol = iter.next();
				if (!symbol.isTerminal())
					changes = symbol.updateFollowSet(rule, iter.nextIndex()) || changes;
			}
		}
		return changes;
	}
	
	@Override
	/* Defines the printing behavior for this String to print out the first and follow sets for the Nonterminal.
	 */
	public String toString() {
		String str = "First(" + this.symbol + "): " + this.symbol.getFirstSet();
		str += "\nFollow(" + this.symbol + "): " + this.symbol.getFollowSet();
		return str;
	}
}
