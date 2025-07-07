package interpreter;

import java.util.HashMap;

/**
 * The Calculate class is responsible for evaluating and assigning values to variables
 * based on a parsed expression tree.
 */
public class Calculate {

    // A map to store variable names and their corresponding double values.
    HashMap<String, Double> variablesMap;

    // Stores the result of the most recent evaluation (if not an assignment).
    double returnVal;
    boolean flag = false;

    public Calculate() {
        // Initialize the variable map.
        variablesMap = new HashMap<>();
    }

    /**
     * Evaluates an assignment expression or just evaluates and stores the result.
     * If the expression is an assignment (e.g., x = 2 + 3), it will update the variable map.
     * Otherwise, it will evaluate the expression and store the result in returnVal.
     *
     * @param exp The expression to evaluate or assign.
     */
    public void EvaluateAssign(Expression exp) {
        flag = false;
        // Check if this is an assignment expression (root is an EXP node with "=" operator)

        if (exp.getExpressionType() == ExpressionEnum.EXP && exp.getVal().equals("=")) {
            Expression lhs = exp.getLHS();
            Expression rhs = exp.getRHS();

            // LHS cannot be a complex expression
            if (lhs.getExpressionType() == ExpressionEnum.EXP) {
                throw new IllegalArgumentException("When assigning value to variable, LHS cannot be an expression");
            // LHS cannot be a numeric literal (e.g., 3 = 5 is invalid)
            } else if (lhs.getExpressionType() == ExpressionEnum.ATOM && isNumeric(lhs.getVal())) {
                throw new IllegalArgumentException("When assigning value to variable, LHS cannot be a number");
            // LHS is a valid variable name
            } else if (lhs.getExpressionType() == ExpressionEnum.ATOM && !isNumeric(lhs.getVal())) {
                try {
                    // Evaluate the RHS expression and assign it to the variable name on the LHS
                    double assignmentValue = EvaluateCalculate(rhs);
                    variablesMap.put(lhs.getVal(), assignmentValue);
                } catch (Exception e) {
                    // If evaluation fails, rethrow as IllegalArgumentException
                    throw new IllegalArgumentException(e);
                }
            } else {
                // LHS is not a valid assignment target
                throw new IllegalArgumentException("Illegal symbol in assignment");
            }
        }
        else {
            // Not an assignment, just evaluate and store result in returnVal
            try {
                returnVal = EvaluateCalculate(exp);
                flag = true;
            } catch (Exception e) {
                throw new IllegalArgumentException("Cannot evaluate expression: " + e.getMessage());
            }
        }
    }

    public boolean getFlag(){
        return flag;
    }

    public double getVal() {
        return returnVal;
    }

    /**
     * Recursively evaluates an expression tree and returns the result as a double.
     * Handles arithmetic operations and variable lookup.
     *
     * @param exp The expression to evaluate.
     * @return The result of the evaluation.
     */
    public double EvaluateCalculate(Expression exp) {
        // If this node is a literal or variable
        if (exp.getExpressionType() == ExpressionEnum.ATOM) {
            String value = exp.getVal();
            // If it's a numeric literal, parse and return
            if (isNumeric(value)) {
                return Double.parseDouble(value);
            // If it's a variable name, look up its value
            } else if (variablesMap.containsKey(value)) {
                return variablesMap.get(value);
            } else {
                // Unknown variable or invalid atom
                throw new IllegalArgumentException("Unknown variable or invalid atom: " + value);
            }
        }
        // If this node is an operator expression (e.g., +, -, *, /, ^)
        else if (exp.getExpressionType() == ExpressionEnum.EXP) {
            Expression lhs = exp.getLHS();
            Expression rhs = exp.getRHS();
            String opType = exp.getVal();

            // Recursively evaluate left and right subtrees and apply the operator
            if (opType.equals("+")) {
                return EvaluateCalculate(lhs) + EvaluateCalculate(rhs);
            }
            else if (opType.equals("-")) {
                return EvaluateCalculate(lhs) - EvaluateCalculate(rhs);
            }
            else if (opType.equals("*")) {
                return EvaluateCalculate(lhs) * EvaluateCalculate(rhs);
            }
            else if (opType.equals("/")) {
                return EvaluateCalculate(lhs) / EvaluateCalculate(rhs);
            }
            else if (opType.equals("^")) {
                return Math.pow(EvaluateCalculate(lhs), EvaluateCalculate(rhs));
            } else {
                // Operator not recognized
                throw new IllegalArgumentException("Unknown operator: " + opType);
            }
        }
        // If the node is (incorrectly) an EQUAL node, throw an error
        else if (exp.getExpressionType() == ExpressionEnum.EQUAL && exp.getVal().equals("=")) {
            throw new IllegalArgumentException("Cannot evaluate or assign expression");
        }
        else {
            // Any other node type is not valid for evaluation
            throw new IllegalArgumentException("Cannot evaluate expression");
        }
    }

    /**
     * Utility function to check if a string is a valid numeric value.
     *
     * @param str The string to check.
     * @return true if the string represents a number, false otherwise.
     */
    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  // match a number with optional '-' and decimal.
    }
}
