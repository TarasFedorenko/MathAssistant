package com.geekforless.tfedorenko.util;

import org.nfunk.jep.JEP;
public class EvaluationExpression {
    public double evaluate(String expression, String root) {
        String exp = expression.replaceAll("x", Double.toString(Double.parseDouble(root)));
        JEP jep = new JEP();
        jep.addStandardFunctions();
        jep.addStandardConstants();
        jep.parseExpression(exp);
        return jep.getValue();
    }
}

