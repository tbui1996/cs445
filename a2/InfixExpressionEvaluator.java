package cs445.a2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import static java.lang.Math.pow;



public class InfixExpressionEvaluator {
    // Tokenizer to break up our input into tokens
    StreamTokenizer tokenizer;

    // Stacks for operators (for converting to postfix) and operands (for
    // evaluating)
    StackInterface<Character> operatorStack;
    StackInterface<Double> operandStack;
	
	//counts brackets
	int Bracket1=0, Bracket2=0, count = 0;

    /**
     * Initializes the evaluator to read an infix expression from an input
     * stream.
     * @param input the input stream from which to read the expression
     */
    public InfixExpressionEvaluator(InputStream input) {
        // Initialize the tokenizer to read from the given InputStream
        tokenizer = new StreamTokenizer(new BufferedReader(
                        new InputStreamReader(input)));

        // StreamTokenizer likes to consider - and / to have special meaning.
        // Tell it that these are regular characters, so that they can be parsed
        // as operators
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');

        // Allow the tokenizer to recognize end-of-line, which marks the end of
        // the expression
        tokenizer.eolIsSignificant(true);

        // Initialize the stacks
        operatorStack = new ArrayStack<Character>();
        operandStack = new ArrayStack<Double>();
    }

    /**
     * Parses and evaluates the expression read from the provided input stream,
     * then returns the resulting value
     * @return the value of the infix expression that was parsed
     */
    public Double evaluate() throws ExpressionError {
        // Get the first token. If an IO exception occurs, replace it with a
        // runtime exception, causing an immediate crash.
        try {
            tokenizer.nextToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean preNum = false, preOp = false;
        boolean preOpenbracket = false, preClosebracket = false;
        int Bracket1 = 0, Bracket2 = 0, count = 0;

        // Continue processing tokens until we find end-of-line
        while (tokenizer.ttype != StreamTokenizer.TT_EOL) {
            // Consider possible token type
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    // Expression error handling
                    if ((preNum == true) && (count > 0)) {
                        throw new ExpressionError("Two operands in a row");
                    }
                    if ((preClosebracket == true) && (count > 0)) {
                        throw new ExpressionError("A close bracket cannot be followed by a number");
                    }

                    // If the token is a number, process it as a double-valued
                    // operand
                    processOperand((double) tokenizer.nval);
                    preOp = false;
                    preNum = true;
                    preOpenbracket = false;
                    preClosebracket = false;
                   break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                    // check for errror in input
                    if (count == 0) {
                        throw new ExpressionError("Leading off with operator is illegal");
                    }
                    if ((preOp == true) && (count > 0)) {
                        throw new ExpressionError("Two operators in a row");
                    }
                    if ((preOpenbracket == true) && (count > 0)) {
                        throw new ExpressionError("An open bracket cannot be followed by an operator");
                    }

                    // If the token is any of the above characters, process it
                    // is an operator
                    processOperator((char) tokenizer.ttype);
                    preOp = true;
                    preNum = false;
                    preOpenbracket = false;
                    preClosebracket = false;
                    break;
                case '(':
                case '[':
                    // Expression error handling
                    if ((preNum == true) && (count > 0)) {
                        throw new ExpressionError("An open bracket cannot be preceded by a number");
                    }
                    // If the token is open bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    processOpenBracket((char) tokenizer.ttype);

                    preOp = false;
                    preNum = false;
                    preOpenbracket = true;
                    preClosebracket = false;
                    Bracket1++;
                    break;
                case ')':
                case ']':
                     // Expression error handling
                    if ((preOp == true) && (count > 0)) {
                        throw new ExpressionError("A close bracket cannot be preceded by a operator");
                    }

                    // If the token is close bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    processCloseBracket((char) tokenizer.ttype);

                    preOp = false;
                    preNum = false;
                    preOpenbracket = false;
                    preClosebracket = true;
                    Bracket2++;
                    break;
                case StreamTokenizer.TT_WORD:
                    // If the token is a "word", throw an expression error
                    throw new ExpressionError("Unrecognized token: "
                            + tokenizer.sval);
                default:
                    // If the token is any other type or value, throw an
                    // expression error
                    throw new ExpressionError("Unrecognized token: "
                            + String.valueOf((char) tokenizer.ttype));
            }
            count++;

            // Read the next token, again converting any potential IO exception
            try {
                tokenizer.nextToken();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Almost done now, but we may have to process remaining operators in
        // the operators stack
        processRemainingOperators();

        if (Bracket1 != Bracket2) {
            throw new ExpressionError("\nNot the same number of bracket");
        }

        // Return the result of the evaluation - 
        return operandStack.pop();
    }


    void processOperand(double operand) {
    //push operand
		operandStack.push(operand);
    }

  
    void processOperator(char operator) {
		// Precedence order: ^*/ level 2, +- level 1, ( and [ has lowest level 0
		while (!operatorStack.isEmpty() && hasPrecedence(operator, operatorStack.peek())) {
		    
		    // perform step 1a,1b,1c,1d
		    operandStack.push(applyOp(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
		}
		// Step 2: thisOp has more precedence than one on top of operatorStack, push it
		operatorStack.push(operator);
    }

    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2)
    {
        if (op2 == '(' || op2 == ')' || op2 == '[' || op2 == ']')
            return false;
        if ((op1 == '*' || op1 == '/' || op1 == '^') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }
  
    void processOpenBracket(char openBracket) {
		operatorStack.push(openBracket);
    }

   
    void processCloseBracket(char closeBracket) {
        //Declare variables
		char operator='0';
		double a=0, b=0, c=0;
		boolean correct = true;
		
		//check for error before loop
		if (operatorStack.isEmpty()) {
			throw new ExpressionError("Brackets/parenthasis are uneven");
		} 
		if (operatorStack.peek()== '(' || operatorStack.peek()=='[') {
			throw new ExpressionError("There was an empty set of brackets or unneeded use of brackets");
		}
		if (operandStack.isEmpty()) {
			throw new ExpressionError("Too many operators");
		}
		
		//loop through stacks and pop off operators and operands
		while (correct) {
			operator = operatorStack.pop();
			b = operandStack.pop();
			a = operandStack.pop();
			
			//check  to see if next one is bracket
			if (operatorStack.peek() == '(' || operatorStack.peek() == '[') { 
				correct = false;
			}
		} 
			
		//check for errors
		if (closeBracket == ')' && !operatorStack.isEmpty() && operatorStack.peek() != '(') {
			throw new ExpressionError("Parenthesis do not match");
		}
		if (closeBracket == ']' && !operatorStack.isEmpty() && operatorStack.peek() != '[') {
			throw new ExpressionError("Brackets do not match");
		}

		//calculate result and push it onto the stack, pop off bracket
		c = applyOp(operator, b, a);
		operandStack.push(c);
		operatorStack.pop();
    }
    
    public static double applyOp(char op, double b, double a)
    {
        switch (op) {
			case '+':
				return a + b;
			case '-':
				return a - b;
			case '*':
				return a * b;
			case '^':
				return pow(a,b);
			case '/':
			//check for division by zero
            if (b == 0)
				throw new ExpressionError("No division by zero");
            return a / b;
        }
        return 0;
    }
 
    /**
     * This method is called when the evaluator encounters the end of an
     * expression. It manipulates operatorStack and/or operandStack to process
     * the operators that remain on the stack, according to the Infix-to-Postfix
     * and Postfix-evaluation algorithms.
     */
    void processRemainingOperators() {
		//error check
		double a;
		if (!operatorStack.isEmpty()) {
			if (operatorStack.peek()=='(' || operatorStack.peek()=='[') {
				throw new ExpressionError("Uneven number of parenthesis");
			}
			
			//check for expression ending with operator
			a=operandStack.pop();
			if (operandStack.isEmpty()) {
				throw new ExpressionError("You can not end with an operator");
			}
			operandStack.push(a);
				
		}
		
        //process the remaining operators and answer is placed in the stack alone
        while (!operatorStack.isEmpty()) {
            operandStack.push(applyOp(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
		}
    }


    /**
     * Creates an InfixExpressionEvaluator object to read from System.in, then
     * evaluates its input and prints the result.
     * @param args not used
     */
    public static void main(String[] args) {
        System.out.println("\nInfix expression:");
        InfixExpressionEvaluator evaluator =
                        new InfixExpressionEvaluator(System.in);
        Double value = null;
        try {
            value = evaluator.evaluate();
        } catch (ExpressionError e) {
            System.out.println("ExpressionError: " + e.getMessage());
        }
        if (value != null) {
            System.out.println(value);
        } else {
            System.out.println("Evaluator returned null");
        }
    }

}

