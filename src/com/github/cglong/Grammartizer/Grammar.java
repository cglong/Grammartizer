package com.github.cglong.Grammartizer;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* Represents a full Context-Free Grammar specification.
 * Holds a set of rules, nonterminals, terminals, and a start variable.
 */
public class Grammar {
	// A list of RuleSet objects, indexed by the Nonterminal they are specified by.
	private Map<Nonterminal, RuleSet> ruleSets;
	
	// An array of all Nonterminals used in this Grammar
	private ArrayList<Nonterminal> nonterminals;
	
	// An array of all Terminals used in this Grammar
	private ArrayList<Terminal> terminals;
	
	// The starting Nonterminal
	private Nonterminal startvariable;
	
	// A single reference for the special epsilon Terminal.  It is a terminal with "" as its name.
	private Terminal epsilon;
	
	/* Initializes this Grammar object with a created epsilon variable.
	 * The list of terminals includes only epsilon.
	 * All other instance data are initially empty.
	 */
	public Grammar() {
		this.ruleSets = new ConcurrentHashMap<Nonterminal, RuleSet>();
		this.terminals = new ArrayList<Terminal>();
		this.nonterminals = new ArrayList<Nonterminal>();
		this.epsilon = new Terminal("");
		this.terminals.add(epsilon);
	}
	
	/* Prints all of the instance data, for use in debugging.
	 */
	public void debugDump() {
		System.out.println("Terminals: ");
		for (Terminal t : terminals)
			System.out.print(t.getName() + " ");
		System.out.println();
		System.out.println("Nonterminals : ");
		for (Nonterminal t: nonterminals)
			System.out.print(t.getName() + " ");
		System.out.println();
		System.out.println("Rules: ");
		for (RuleSet r : ruleSets.values()) {
			System.out.print(r.getSymbol().getName() + " : ");
			for (Rule e : r.getRules())
				for (Symbol s : e.getRightSymbols())
					System.out.print(s.getName() + " ");
			System.out.println();
		}
	}
	
	/* Inserts a new rule into the grammar.
	 * Automatically merges it with an existing RuleSet, if one exists.
	 * symbol - The Nonterminal on the left side of the rule.
	 * rule - The rule to add to this Grammar
	 */
	public void add(Nonterminal symbol, Rule rule) {
		if (!this.ruleSets.containsKey(symbol))
			this.ruleSets.put(symbol, new RuleSet(symbol));
		RuleSet ruleSet = this.ruleSets.get(symbol);
		ruleSet.add(rule);
	}
	
	/* Fills the first sets with their correct values.
	 * PRECONDITION: All rules have been added to this Grammar
	 * PRECONDITION: Immediate left recursion and common prefixes have been removed.
	 * POSTCONDITION: All Terminals and Nonterminals have their first sets correctly set
	 */
	public void updateFirstSets() {
		boolean changes;
		do {
			changes = false;
			for (RuleSet ruleSet : this.ruleSets.values())
				changes = ruleSet.updateFirstSets() || changes;
		} while (changes);
	}

	/* Fills the follow sets with their correct values.
	 * PRECONDITION: All rules have been added to this Grammar
	 * PRECONDITION: Immediate left recursion and common prefixes have been removed.
	 * POSTCONDITION: All Terminals and Nonterminals have their follow sets correctly set
	 */
	public void updateFollowSets() {
		boolean changes;
		do {
			changes = false;
			for (RuleSet ruleSet : this.ruleSets.values())
				changes = ruleSet.updateFollowSets() || changes;
		} while (changes);
	}
	
	/* Eliminates all left recursion on this grammar.  Should be called once, after all rules have
	 * been added to this grammar.
	 * POSTCONDITION: The grammar will recognize the same language and create the same syntax trees, but
	 * will not be immediately left-recursive.
	 */
	public void eliminateLeftRecursion() {
		for (RuleSet ruleSet : this.ruleSets.values())
			if (ruleSet.hasLeftRecursion())
				eliminateLeftRecursionHelper(ruleSet);
	}

	/* Helper function.  Eliminates all immediate left recursion in a single recursive RuleSet.
	 * recursiveSet - A RuleSet that contains at least one rule with immediate left recursion.
	 * PRECONDITION: recursiveSet.hasLeftRecursion() returns true
	 * POSTCONDITION: recursiveSet no longer has immediate left recursion, but recognizes the same language as before.
	 */
	private void eliminateLeftRecursionHelper(RuleSet recursiveSet) {
		// Split the RuleSet into two lists - Rules with immediate left recursion and rules without.
		ArrayList<Rule> recursiveRules = new ArrayList<Rule>();
		ArrayList<Rule> nonRecursiveRules = new ArrayList<Rule>();

		for (Rule rule : recursiveSet.getRules())
			if (rule.hasLeftRecursion())
				recursiveRules.add(rule);
			else
				nonRecursiveRules.add(rule);

		// The nonterminal we are removing left recursion from
		Nonterminal oldSymbol = recursiveSet.getSymbol();
		// The new 'tail' nonterminal being added to the grammar
		Nonterminal tail = new Nonterminal(oldSymbol.getName() + "-tail"); // Since all grammar rules are named with <>s, <symbol>-tail is guaranteed to be unique.
		// The new RuleSet based off of oldSymbol that will reference tail and no longer contain immediate left recursion.  This will replace oldBase.
		RuleSet newBase = new RuleSet(oldSymbol);
		// The new 'tail' RuleSet
		RuleSet tailSet = new RuleSet(tail);

		// Add the new nonterminal to the list.
		nonterminals.add(tail);
		
		// For each non recursive rule B, add a rule B<a-tail> to newBase
		for (Rule rule : nonRecursiveRules) {
			ArrayList<Symbol> newSymbolList = new ArrayList<Symbol>(rule.getRightSymbols());
			newSymbolList.add(tail);
			newBase.add(new Rule(oldSymbol, newSymbolList));
		}

		// For each recursive rule A, add rule A<a-tail> to tailSet
		for (Rule rule : recursiveRules) {
			ArrayList<Symbol> newSymbolList = new ArrayList<Symbol>(rule.getRightSymbols());
			newSymbolList.remove(0); // Remove the immediate left recursive symbol
			newSymbolList.add(tail); // Add the new tail symbol to the end of the set.
			tailSet.add(new Rule(tail, newSymbolList));
		}

		// Insert a rule into tailSet for epsilon
		Terminal epsilon = new Terminal("");
		ArrayList<Symbol> EpsilonList = new ArrayList<Symbol>();
		EpsilonList.add(epsilon);
		tailSet.add(new Rule(tail, EpsilonList));

		// Remove the old Nonterminal RuleSet from this Grammar and add in newBase and tailSet
		this.ruleSets.remove(oldSymbol);
		this.ruleSets.put(oldSymbol, newBase);
		this.ruleSets.put(tail, tailSet);
	}
	
	/* Eliminates all common prefixes in the grammar.
	 * Changes all rules of type <A> : A B | A C | ... | A N | M | O | ... | Z to
	 * <A> : | A <A>A | M | O | ... | Z
	 * <A>A : B | C | ... | N
	 * PRECONDITION: All rules have been added to this Grammar
	 * PRECONDITION: Left recursion has been removed from this Grammar
	 * POSTCONDITION: The grammar will recognize the same language but will have all common prefixes removed.
	 */
	public void eliminateCommonPrefixes() {
		boolean changes;
		
		do {
			changes = false;
			for (RuleSet ruleset : this.ruleSets.values()) {
				// This list maps the leftmost symbol in a rule to a list of rules with that leftmost symbol
				ConcurrentHashMap<Symbol, ArrayList<Rule>> ruleMap = new ConcurrentHashMap<Symbol, ArrayList<Rule>>(); 
				
				// Scan all Rules and add them to the map
				for (Rule rule : ruleset.getRules()) {
					if (!ruleMap.containsKey(rule.getLeft()))
						ruleMap.put(rule.getLeft(), new ArrayList<Rule>());
					
					ruleMap.get(rule.getLeft()).add(rule);
				}
				
				// Scan all lists based on common prefixes.
				// If a list has more than two elements with a common prefix, factor it out.
				for (ArrayList<Rule> ruleList :  ruleMap.values()) {
					if (ruleList.size() >= 2) {
						changes = true;
						Symbol common = ruleList.get(0).getLeft();
						Nonterminal oldSymbol = ruleList.get(0).getLeftSide();
						
						// First step:  Remove these rules from ruleset
						for (Rule rule : ruleList)
							ruleset.remove(rule);
						
						// Second step: Create a new derivative nonterminal
						Nonterminal newSymbol = new Nonterminal(oldSymbol.getName() + common.getName());
						
						// Third step:  Create a new rule with the common prefix and nonterminal
						ArrayList<Symbol> newRuleList = new ArrayList<Symbol>();
						newRuleList.add(common);
						newRuleList.add(newSymbol);
						Rule newRule = new Rule(oldSymbol, newRuleList);
						
						
						// Fourth step:  Add this new rule to ruleset
						ruleset.add(newRule);
						
						// Fifth step: Remove the leftmost symbol from each of these rules
						for (Rule rule : ruleList)
							rule.getRightSymbols().remove(0);
						
						// Sixth step: Change the nonterminal in each of these rules to the new one
						for (Rule rule : ruleList)
							rule.setLeftSite(newSymbol);
						
						// Seventh step:  Add these rules to the Grammar
						for (Rule rule : ruleList)
							this.add(newSymbol, rule);
						
						// Eighth step:  Add the new Nonterminal to the list of nonterminals.
						nonterminals.add(newSymbol);
					}
					
				}
			}
		} while (changes == true);
	}
	
	@Override
	/* Sets the printing behavior for a Grammar to print all rule sets.
	 */
	public String toString() {
		String str = "";
		for (RuleSet ruleSet : this.ruleSets.values())
			str += ruleSet.toString() + "\n";
		return str;
	}
	
	/* Getter for the list of RuleSets
	 */
	public Collection<RuleSet> getRuleSets() {
		return this.ruleSets.values();
	}
	
	/* Setter for this Grammar's start variable.
	 * s - This Grammar's start variable.
	 */
	public void setStartvariable(Nonterminal s) {
		this.startvariable = s;
		this.startvariable.getFollowSet().add(new Terminal("$"));
	}//end setter

	/* Getter for this Grammar's start variable.
	 */
	public Nonterminal getStartvariable() {
		return startvariable;
	}//end getter
	
	/* Getter for the epsilon Terminal.
	 */
	public Terminal getEpsilon(){
		return epsilon;
	}
	
	/* Getter for the list of Terminals.
	 */
	public ArrayList<Terminal> getTerminals(){
		return terminals;
	}//end getter
	
	/* Getter for the list of Nonterminals.
	 */
	public ArrayList<Nonterminal> getNonterminals(){
		return nonterminals;
	}//end getter
	
	/* Setter for the list of Terminals.
	 */
	public void setTerminals(ArrayList<Terminal> t) {
		this.terminals = t;
	}//end setter
	
	/* Setter for the list of Nonterminals.
	 */
	public void setNonterminals(ArrayList<Nonterminal> n) {
		this.nonterminals = n;
	}// end setter
	
}
