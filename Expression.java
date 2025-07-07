package interpreter;

/**
 * Expression represents a node in an expression tree.
 * Each node can be:
 *   - An ATOM (a variable or a number)
 *   - An OP (an operator like +, -, etc.)
 *   - An EXP (an operation with left and right subtrees)
 *   - Other types as defined in ExpressionEnum
 */
public class Expression {
	
	// Value of the node: 
	// - For OP, val is the operator (e.g. "+", "-", "*", etc.)
	// - For ATOM, val is the variable name or numeric value as a string
	String val;

	// The type of this expression (ATOM, OP, EXP, etc.)
	ExpressionEnum type;

	// Left and right sub-expressions (only valid for EXP type nodes)
	Expression LHS = null;
	Expression RHS = null;
	
	/**
	 * Constructs an Expression node with a value and type.
	 * @param val The value (operator, variable name, or number)
	 * @param type The type of this node
	 */
	Expression(String val, ExpressionEnum type){
		this.val = val;
		this.type = type;
	}

	/**
	 * Sets the left-hand side child for an EXP node.
	 * Will print an error if called on non-EXP nodes.
	 * @param LHS The left sub-expression to attach
	 */
	void setLHS(Expression LHS){
		if (type == ExpressionEnum.EXP) {
			this.LHS = LHS;
		} else {
			System.err.println("You cannot add LHS to atom or op");
		}
	}

	/**
	 * Sets the right-hand side child for an EXP node.
	 * Will print an error if called on non-EXP nodes.
	 * @param RHS The right sub-expression to attach
	 */
	void setRHS(Expression RHS){
		if (type == ExpressionEnum.EXP) {
			this.RHS = RHS;
		} else {
			System.err.println("You cannot add RHS to atom or op");
		}
	}

	/**
	 * Gets the left-hand side child of this expression (or null if none).
	 * @return The LHS Expression, or null
	 */
	Expression getLHS() {
		return LHS;
	}
	
	/**
	 * Gets the right-hand side child of this expression (or null if none).
	 * @return The RHS Expression, or null
	 */
	Expression getRHS() {
		return RHS;
	}
	
	/**
	 * Returns the value (operator, variable name, or number) of this node.
	 * @return The val string.
	 */
	String getVal() {
		return val;
	}
	
	/**
	 * Returns the type of this expression (ATOM, OP, EXP, etc.).
	 * @return The ExpressionEnum type.
	 */
	ExpressionEnum getExpressionType() {
		return type;
	}
}