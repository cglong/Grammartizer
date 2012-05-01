package com.github.cglong.Grammartizer;

import java.util.Set;
import java.util.HashSet;

/* Represents an abstract symbol in a context free grammar.
 * Both Terminal and Nonterminal are child classes.
 */
public abstract class Symbol {
	// The name of this symbol, as a String. See child classes for specific name syntax rules.
	private String name;
	// This Symbol's first set - the set of characters that can be the first character in this set.
	private Set<Terminal> firstSet;
	
	/* Initializes a Symbol with a name and an empty firstSet.
	 * name - The name of the Symbol.
	 */
	public Symbol(String name) {
		this.name = name;
		this.firstSet = new HashSet<Terminal>();
	}
	
	/* Returns this Symbol's name as a String. 
	 */
	public String getName() {
		return this.name;
	}
	
	/* Returns this Symbol's firstSet as a Set.
	 */
	public Set<Terminal> getFirstSet() {
		return this.firstSet;
	}
	
	@Override
	/* Defines equality of Symbols as two symbols that have the same name.
	 */
	public boolean equals(Object o) {
		Symbol other = (Symbol) o;
		return this.name.equals(other.getName());
	}
	
	@Override
	/* Sets a Symbol's printed form to be its name.
	 */
	public String toString() {
		return this.getName();
	}
	
	// Should return true if this Symbol is a Terminal, false if not.
	abstract public boolean isTerminal();
	
	// Returns the Symbol's follow set, or throws an UnsupportedOperationException if this Symbol is a Terminal.
	abstract public Set<Terminal> getFollowSet() throws UnsupportedOperationException;

	// Given a Rule, runs an iteration that updates this Symbol's first set.
	abstract public boolean updateFirstSet(Rule rule);
	
	// Given a Rule, runs an iteration that updates this Symbol's follow set.
	abstract public boolean updateFollowSet(Rule rule, int index) throws UnsupportedOperationException;
}
