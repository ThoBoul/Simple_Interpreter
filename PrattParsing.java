package interpreter;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The PrattParsing class implements a recursive descent parser for arithmetic expressions,
 * handling operator precedence and parenthesis via a variant of Pratt parsing.
 */
public class PrattParsing {
	
	// The root of the parsed expression tree.
	Expression ParsedLexer;
	
	/**
	 * Constructs the PrattParsing object and parses the given list of tokens (expressions).
	 * Handles parenthesis by recursively parsing sub-expressions inside them.
	 *
	 * @param ExpressionsLexer The list of tokens (as Expression objects) to parse.
	 */
	PrattParsing(ArrayList<Expression> ExpressionsLexer){
		
		// If the input is null or empty, throw error
		if (ExpressionsLexer == null || ExpressionsLexer.isEmpty()) {
		    throw new IllegalArgumentException("Empty expression list");
		}
		
		// Stack to keep track of open parenthesis positions
		Stack<Integer> ParenthesisStack = new Stack<>();
		
		int i = 0;
		// Main loop to process all tokens and handle parenthesis
		while (i < ExpressionsLexer.size()) {
		    Expression expr = ExpressionsLexer.get(i);

		    // If open parenthesis, push its index onto stack
		    if (expr.getExpressionType() == ExpressionEnum.PAREN && expr.getVal().equals("(")) {
		        ParenthesisStack.push(i);
		        i++;
		    } 
		    // If closing parenthesis, pop stack and parse the sub-expression within the parenthesis
		    else if (expr.getExpressionType() == ExpressionEnum.PAREN && expr.getVal().equals(")")) {

		        if (!ParenthesisStack.empty()) {
		            int start = ParenthesisStack.pop();

		            // Get sublist between ( and )
		            ArrayList<Expression> copySlice = new ArrayList<>(ExpressionsLexer.subList(start + 1, i));
		            // Recursively parse the inside of the parenthesis
		            Expression newExpression = Parsing(copySlice);

		            // Remove the entire parenthesis block from the list
		            for (int j = 0; j <= i - start; j++) {
		                ExpressionsLexer.remove(start); // Always remove at 'start' because list shrinks
		            }

		            // Insert the new parsed expression in place of the parenthesis block
		            ExpressionsLexer.add(start, newExpression);

		            // After collapsing, move i back to start to recheck from there
		            i = start;
		        } else {
		            throw new RuntimeException("Unmatched closing parenthesis");
		        }
		    } 
		    // If not a parenthesis, just move to next token
		    else {
		        i++;
		    }
		}

		// After parenthesis are resolved, parse the remaining expression for operator precedence
		ParsedLexer = Parsing(ExpressionsLexer);		
	}
	
	
	/**
	 * Parses the token list according to operator precedence and builds an expression tree.
	 * Handles operators in the order of precedence: ^, *, /, +, -, = (assignment is lowest)
	 *
	 * @param ExpressionsLexer The list of Expression tokens (should have no unprocessed parenthesis).
	 * @return The root Expression node for this sub-expression.
	 */
	Expression Parsing(ArrayList<Expression> ExpressionsLexer) {
	    
	    // Define operator precedence groups, from highest to lowest
	    ArrayList<String> currPrecedence = new ArrayList<String>();
	    currPrecedence.add("^");     // Exponentiation (highest)
	    currPrecedence.add("*/");    // Multiplication/Division
	    currPrecedence.add("+-");    // Addition/Subtraction
	    currPrecedence.add("=");     // Assignment (lowest)

	    // Loop through each precedence level, from highest to lowest
	    for (int counter = 0; counter < currPrecedence.size(); counter++) {
	        int i = 0;

	        // Right-associative for ^ and =, left for others
	        boolean rightAssociative = currPrecedence.get(counter).equals("^");

	        if (rightAssociative) {
	            i = ExpressionsLexer.size() - 1;
	            while (i >= 0) {
	                Expression currExp = ExpressionsLexer.get(i);
	                boolean isOp = (currExp.getExpressionType() == ExpressionEnum.OP &&
	                                currPrecedence.get(counter).indexOf(currExp.getVal()) != -1);
	                boolean isEq = (currExp.getExpressionType() == ExpressionEnum.EQUAL &&
	                                currPrecedence.get(counter).indexOf(currExp.getVal()) != -1);

	                if ((isOp || isEq) && i - 1 >= 0 && i + 1 < ExpressionsLexer.size()) {
	                    Expression Exp = new Expression(currExp.getVal(), ExpressionEnum.EXP);
	                    Exp.setLHS(ExpressionsLexer.get(i - 1));
	                    Exp.setRHS(ExpressionsLexer.get(i + 1));

	                    ExpressionsLexer.remove(i + 1);
	                    ExpressionsLexer.remove(i);
	                    ExpressionsLexer.remove(i - 1);

	                    ExpressionsLexer.add(i - 1, Exp);
	                    i = ExpressionsLexer.size() - 1; // restart from end
	                } else {
	                    i--;
	                }
	            }
	        } else {
	            i = 0;
	            while (i < ExpressionsLexer.size()) {
	                Expression currExp = ExpressionsLexer.get(i);
	                boolean isOp = (currExp.getExpressionType() == ExpressionEnum.OP &&
	                                currPrecedence.get(counter).indexOf(currExp.getVal()) != -1);
	                boolean isEq = (currExp.getExpressionType() == ExpressionEnum.EQUAL &&
	                                currPrecedence.get(counter).indexOf(currExp.getVal()) != -1);

	                if ((isOp || isEq) && i - 1 >= 0 && i + 1 < ExpressionsLexer.size()) {
	                    Expression Exp = new Expression(currExp.getVal(), ExpressionEnum.EXP);
	                    Exp.setLHS(ExpressionsLexer.get(i - 1));
	                    Exp.setRHS(ExpressionsLexer.get(i + 1));

	                    ExpressionsLexer.remove(i + 1);
	                    ExpressionsLexer.remove(i);
	                    ExpressionsLexer.remove(i - 1);

	                    ExpressionsLexer.add(i - 1, Exp);
	                    i = Math.max(0, i - 1);
	                } else {
	                    i++;
	                }
	            }
	        }
	    }
	    
	    // After all precedence groups are processed, only one expression should remain
	    return ExpressionsLexer.get(0);
	}
	
	/**
	 * Returns the root of the parsed expression tree.
	 * @return The parsed Expression tree.
	 */
	Expression getParsedLexer() {
		return ParsedLexer;
	}
}