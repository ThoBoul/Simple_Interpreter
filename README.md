# Simple_Interpreter

This project is a simple arithmetic expression interpreter written in Java.  
It was created for fun and as a learning exercise to prepare for Computer Science exams.

## What is this?

This is a command-line tool that can parse and evaluate arithmetic expressions with variables and binary operators.  
It supports standard mathematical operations (addition `+`, subtraction `-`, multiplication `*`, division `/`, exponentiation `^`), assignment to variables, and parentheses for grouping.
I have not yet implemented unary operators.

## What does this interpreter do?

- **Parses** arithmetic expressions such as `a = 2 + 3 * (4 - 1)`  
- **Evaluates** the result of expressions and assignments  
- **Stores** variables and allows their use in later expressions  
- **Handles** operator precedence and parentheses correctly

## Why did I make this?

This project was made to:
- Practice and understand Java syntax and object-oriented programming
- Learn about parsing (specifically, operator precedence parsing)
- Get hands-on experience with data structures and algorithms used in interpreters
- Have fun building a working calculator/interpreter from scratch

## How to use

1. **Compile the project** using your preferred Java build method (e.g. `javac *.java`).
2. **Run the main program**.
3. **Type an arithmetic expression** at the prompt, for example:
   ```
   a = 2 + 3 * (4 - 1)
   b = a ^ 2
   b / 2
   ```
4. **The interpreter** will output the result, and you can use previously assigned variables in new expressions.


---

**Note:**  
This interpreter is intentionally simple and is not meant for production use.  
