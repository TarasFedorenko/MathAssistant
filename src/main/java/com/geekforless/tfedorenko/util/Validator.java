package com.geekforless.tfedorenko.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    EvaluationExpression evex = new EvaluationExpression();
    private final char[] operation = {'+', '-', '/', '*'};
    public boolean isValid(String equation) {
        String regex = "^[0-9x+\\-*/().= ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(equation);
        if (equation.isEmpty()) {
            return false;
        } else if (matcher.matches()) {
            if (bracketsCheck(equation)) {
                if (checkForSigns(equation)) {
                    if (checkDotValid(equation)) {
                        if (checkXValid(equation)) {
                            if (hasOnlyOneEquals(equation)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isCorrect(String root) {
        if (root.matches("-?\\d+(\\.\\d+)?")) {
            return true;
        }
        return false;
    }

    private boolean bracketsCheck(String formula) {
        int openBracketCount = 0;

        for (int i = 0; i < formula.length(); i++) {
            char currentChar = formula.charAt(i);

            if (currentChar == '(') {
                openBracketCount++;

                if (i > 0) {
                    char charBeforeOpeningBracket = formula.charAt(i - 1);
                    if (!isValidCharBeforeOpeningBracket(charBeforeOpeningBracket)) {
                        return false;
                    }
                }

                if (i < formula.length() - 1) {
                    char charAfterOpeningBracket = formula.charAt(i + 1);
                    if (isValidCharAfterOpeningBracket(charAfterOpeningBracket)) {
                        return false;
                    }
                }
            } else if (currentChar == ')') {
                openBracketCount--;

                if (i > 0) {
                    char charBeforeClosingBracket = formula.charAt(i - 1);
                    if (isValidCharBeforeClosingBracket(charBeforeClosingBracket)) {
                        return false;
                    }
                }

                if (i < formula.length() - 1) {
                    char charAfterClosingBracket = formula.charAt(i + 1);
                    if (!isValidCharAfterClosingBracket(charAfterClosingBracket)) {
                        return false;
                    }
                }
            }
        }
        return openBracketCount == 0;
    }

    private static boolean isValidCharBeforeOpeningBracket(char charBeforeOpeningBracket) {
        return "+-*/=(".indexOf(charBeforeOpeningBracket) != -1;
    }

    private static boolean isValidCharAfterOpeningBracket(char charAfterOpeningBracket) {
        return "+-*/=)".indexOf(charAfterOpeningBracket) != -1;
    }

    private static boolean isValidCharBeforeClosingBracket(char charBeforeClosingBracket) {
        return "+-*/=(".indexOf(charBeforeClosingBracket) != -1;
    }

    private static boolean isValidCharAfterClosingBracket(char charAfterClosingBracket) {
        return "+-*/=)".indexOf(charAfterClosingBracket) != -1;
    }

    private boolean checkForSigns(String equation) {
        boolean isPreviousSign = false;
        char firstChar = equation.charAt(0);
        char lastChar = equation.charAt(equation.length() - 1);
        if (!equation.contains("x")) {
            return false;
        }
        for (int i = 0; i < equation.length(); i++) {
            if (isSign(firstChar, operation) && firstChar != '-' || firstChar == '=') {
                return false;
            } else if (isSign(lastChar, operation) || lastChar == '=') {
                return false;
            } else if (firstChar == '.' || lastChar == '.') {
                return false;
            }
            if (isPreviousSign && equation.charAt(i) == '-') {
                isPreviousSign = false;
            } else if (isPreviousSign && isSign(equation.charAt(i), operation)) {
                return false;
            }
            if (equation.charAt(i) == '=' && isSign(equation.charAt(i - 1), operation)) {
                return false;
            } else if (equation.charAt(i) == '=' && isSign(equation.charAt(i + 1), operation) && equation.charAt(i + 1) != '-') {
                return false;
            }
            isPreviousSign = isSign(equation.charAt(i), operation);
        }

        return true;
    }

    private boolean isSign(char sign, char[] signs) {
        for (char c : signs) {
            if (c == sign) {
                return true;
            }
        }
        return false;
    }


    public boolean isSolvable(String equation, String root) {
        String result = equation.replace("x", root);
        String[] parts = result.split("=");
        String leftPart = "";
        String rightPart = "";
        if (parts.length == 2) {
            leftPart = parts[0].trim();
            rightPart = parts[1].trim();
        } else {
            System.out.println("Incorrect equation");
        }
        double left = evex.evaluate(leftPart, root);
        double right = evex.evaluate(rightPart, root);
        return Math.abs(left - right) < 1e-9;
    }

    public boolean isCorrectQuantity(String quantity) {
        if (!Objects.equals(quantity, "")) {
            if (quantity.matches("\\b\\d+\\b")) {
                int number = Integer.parseInt(quantity);
                if (number > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDotValid(String equation) {
        for (int i = 0; i < equation.length(); i++) {
            if (equation.charAt(i) == '.') {
                if (isSign(equation.charAt(i + 1), operation) ||
                        equation.charAt(i + 1) == '=' ||
                        equation.charAt(i + 1) == '.' ||
                        equation.charAt(i + 1) == ')' ||
                        equation.charAt(i + 1) == '(') {
                    return false;
                } else if (isSign(equation.charAt(i - 1), operation) ||
                        equation.charAt(i - 1) == '=' ||
                        equation.charAt(i - 1) == '.' ||
                        equation.charAt(i - 1) == ')' ||
                        equation.charAt(i - 1) == '(') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkXValid(String equation) {
        for (int xIndex = 0; xIndex < equation.length(); xIndex++) {
            char currentChar = equation.charAt(xIndex);
            if (currentChar == 'x') {
                boolean condition = checkSingleXCondition(equation, xIndex);
                if (!condition) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkSingleXCondition(String equation, int xIndex) {
        if (xIndex == 0) {
            char charAfterX = equation.charAt(xIndex + 1);
            return "+-*/=".indexOf(charAfterX) != -1;
        }

        if (xIndex == equation.length() - 1) {
            char charBeforeX = equation.charAt(xIndex - 1);
            return "+-*/=".indexOf(charBeforeX) != -1;
        }

        char charBeforeX = equation.charAt(xIndex - 1);
        char charAfterX = equation.charAt(xIndex + 1);
        return "+-*/=(".indexOf(charBeforeX) != -1 && "+-*/=)".indexOf(charAfterX) != -1;
    }

    public static boolean hasOnlyOneEquals(String formula) {
        int equalsCount = 0;

        for (int i = 0; i < formula.length(); i++) {
            char currentChar = formula.charAt(i);
            if (currentChar == '=') {
                equalsCount++;
            }
        }
        return equalsCount == 1;
    }
}
