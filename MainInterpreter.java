package interpreter;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * MainInterpreter serves as an entry point for testing the expression parsing and tree printing.
 */
public class MainInterpreter {

    public static void main(String[] args) {
                
        Calculate interpretorCalculator = new Calculate();
        
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                
                String input = scanner.nextLine();
                
                Lexer lexer = new Lexer(input);
                ArrayList<Expression> tokens = lexer.getExpressions();

                // Parse tokens into an expression tree
                PrattParsing parser = new PrattParsing(tokens);
                Expression root = parser.getParsedLexer();
                
                interpretorCalculator.EvaluateAssign(root);
                
                if (interpretorCalculator.getFlag()) {
                    double expressionValue = interpretorCalculator.getVal();
                    System.out.println(expressionValue);
                }
                
            }
        }
    }
}
