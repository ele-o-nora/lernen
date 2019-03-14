package console;

import java.util.Scanner;

public class ConsoleCalculator {
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        String s;
        while (true) {
            System.out.println("Enter your expression to calculate. " +
                    "Separate operands from operators by space, no space needed between operands and brackets. " +
                    "Enter 'Q' to exit: ");
            s = scanner.nextLine();
            if (s.equalsIgnoreCase("q")) {
                break;
            }
            try {
                System.out.println(s + " = " + calculate(s));
            } catch (RuntimeException e) {
                System.out.println("Invalid input: " + e.getMessage() + " Please try again.");
            }
        }
        scanner.close();
    }

    private static double calculate(String s) {
        String toCalc = openBrackets(s);
        String[] tokens = toCalc.split(" ");
        return calc(tokens);
    }

    private static String openBrackets(String s) {
        if (!s.contains("(") && !s.contains(")")) return s;
        StringBuilder sb = new StringBuilder();
        int bracketCount = 0;
        int firstInsideBrackets = 0;
        int firstOutsideBrackets = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                if (bracketCount == 0) {
                    firstInsideBrackets = i + 1;
                    sb.append(s.substring(firstOutsideBrackets, i));
                }
                bracketCount++;
            } else if (s.charAt(i) == ')') {
                if (bracketCount == 0) {
                    throw new RuntimeException("Closing bracket without an opening one.");
                }
                bracketCount--;
                if (bracketCount == 0) {
                    sb.append(String.valueOf(calculate(s.substring(firstInsideBrackets, i))));
                    firstOutsideBrackets = i + 1;
                }
            }
        }
        if (bracketCount != 0) {
            throw new RuntimeException("More opening brackets than closing ones.");
        }
        sb.append(s.substring(firstOutsideBrackets));
        return sb.toString();
    }

    private static double calc(String[] tokens) {
        if (tokens.length == 3) {
            switch (tokens[1]) {
                case "+":
                    return Double.parseDouble(tokens[0]) + Double.parseDouble(tokens[2]);
                case "-":
                    return Double.parseDouble(tokens[0]) - Double.parseDouble(tokens[2]);
                case "*":
                    return Double.parseDouble(tokens[0]) * Double.parseDouble(tokens[2]);
                case "/":
                    return Double.parseDouble(tokens[0]) / Double.parseDouble(tokens[2]);
                default:
                    throw new RuntimeException("No operator found where operator was expected.");
            }
        }
        String[] rest = new String[tokens.length - 2];
        System.arraycopy(tokens, 2, rest, 0, rest.length);
        switch (tokens[1]) {
            case "+":
                return Double.parseDouble(tokens[0]) + calc(rest);
            case "-":
                return Double.parseDouble(tokens[0]) - calc(rest);
            case "*":
                rest[0] = String.valueOf(Double.parseDouble(tokens[0]) * Double.parseDouble(tokens[2]));
                return calc(rest);
            case "/":
                rest[0] = String.valueOf(Double.parseDouble(tokens[0]) / Double.parseDouble(tokens[2]));
                return calc(rest);
            default:
                throw new RuntimeException("No operator found where operator was expected.");
        }
    }
}
