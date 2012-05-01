package com.github.cglong.Grammartizer;

import java.util.Set;

/* Represents a Terminal - a character or symbol representing the endpoint of a derivation path.
 * The first set of a Terminal contains only itself, and a Terminal has no defined follow set.
 */
public class Terminal extends Symbol {

	/* Creates a new Terminal with the specified name and sets the first set to contain only itself.
	 * name - A regular expression representing the class of symbols this terminal should accept.
	 */
	public Terminal(String name) {
		super(name);
		this.getFirstSet().add(this);
	}
	
	@Override
	/* Checks if this is a Terminal.
	 * Returns true, since this is a Terminal.
	 */
	public boolean isTerminal() {
		return true;
	}
	
	@Override
	/* Throws an exception, since Terminals do not have defined follow sets.
	 */
	public Set<Terminal> getFollowSet() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	/* Does nothing, since the first set of a Terminal is simply itself.
	 */
	public boolean updateFirstSet(Rule rule) {
		return false;
	}
	
	@Override
	/* Throws an exception, since Terminals do not have defined follow sets.
	 */
	public boolean updateFollowSet(Rule rule, int index) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}
