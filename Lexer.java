package interpreter;

import java.util.ArrayList;

/**
 * The Lexer class is responsible for tokenizing an input string into a list of Expression tokens.
 * It removes whitespace, groups letters and digits into atoms (variables or numbers),
 * and recognizes operators, parenthesis, and equals signs.
 */
public class Lexer {

    // List to store the resulting tokens (as Expression objects)
    ArrayList<Expression> Expressions = new ArrayList<>();

    /**
     * Constructs the Lexer and tokenizes the input string.
     * @param input The mathematical expression string to tokenize.
     */
    public Lexer(String input) {

        // Remove all whitespace from the input for easier parsing
        String cleaned = input.replaceAll("\\s+", "");
        int i = 0;

        // Process each character in the cleaned input string
        while (i < cleaned.length()) {
            char ch = cleaned.charAt(i);

            // If the character is a letter or digit, group consecutive such characters into an ATOM
            if (Character.isLetterOrDigit(ch)) {
                String number = new String();
                while (i < cleaned.length() && Character.isLetterOrDigit(cleaned.charAt(i))) {
                    number = number + cleaned.charAt(i);
                    i++;
                }
                Expression CurrentExpression = new Expression(number, ExpressionEnum.ATOM);
                Expressions.add(CurrentExpression);
            }
            // If the character is an operator (+, -, /, *, ^), create an OP token
            else if ("+-/*^".indexOf(ch) != -1) {
                Expressions.add(new Expression(String.valueOf(ch), ExpressionEnum.OP));
                i++;
            }
            // If the character is a parenthesis, create a PAREN token
            else if ("()".indexOf(ch) != -1) {
                Expressions.add(new Expression(String.valueOf(ch), ExpressionEnum.PAREN));
                i++;
            }
            // If the character is '=', create an EQUAL token
            else if ("=".indexOf(ch) != -1) {
                Expressions.add(new Expression(String.valueOf(ch), ExpressionEnum.EQUAL));
                i++;
            }
            // Throw an error for any unknown character
            else {
                throw new RuntimeException("Unknown character");
            }
        }
    }

    /**
     * Returns the list of tokenized expressions.
     * @return An ArrayList of Expression tokens.
     */
    public ArrayList<Expression> getExpressions() {
        return Expressions;
    }
}