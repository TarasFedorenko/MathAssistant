package com.geekforless.tfedorenko;

import com.geekforless.tfedorenko.util.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MathAssistantTest {
    Validator validator = new Validator();
    @Test
    public void validateEquationTrue() {
        Assertions.assertTrue(validator.isValid("x+2=8"));
        Assertions.assertTrue(validator.isValid("x=8"));
        Assertions.assertTrue(validator.isValid("-x=8"));
        Assertions.assertTrue(validator.isValid("12=x"));
        Assertions.assertTrue(validator.isValid("x*2+3=23"));
        Assertions.assertTrue(validator.isValid("-x-2=8+x"));
        Assertions.assertTrue(validator.isValid("(x/2)=8"));
        Assertions.assertTrue(validator.isValid("x+2.3456-(2.0+4.2)=8.2"));
        Assertions.assertTrue(validator.isValid("x+2+(x+3)+(x+3)=8"));
        Assertions.assertTrue(validator.isValid("(x+2+(x+3))=8"));
        Assertions.assertTrue(validator.isValid("-8=(x+2.4+(x+3))"));
        Assertions.assertTrue(validator.isValid("(x+2.5)=(8.2+x)"));
    }
    @Test
    public void validateEquationFalse() {
        Assertions.assertFalse(validator.isValid("2=8"));
        Assertions.assertFalse(validator.isValid("x2=8"));
        Assertions.assertFalse(validator.isValid("2x=8"));
        Assertions.assertFalse(validator.isValid("x(2+3)=8"));
        Assertions.assertFalse(validator.isValid("(2+3)x=8"));
        Assertions.assertFalse(validator.isValid("()x+2=8"));
        Assertions.assertFalse(validator.isValid("(x+2))=8"));
        Assertions.assertFalse(validator.isValid("((x+2)=8"));
        Assertions.assertFalse(validator.isValid(")x+2(=8"));
        Assertions.assertFalse(validator.isValid("x-+2=8"));
        Assertions.assertFalse(validator.isValid("x+/2=8"));
        Assertions.assertFalse(validator.isValid("+x+2=8"));
        Assertions.assertFalse(validator.isValid("=x+2+8"));
        Assertions.assertFalse(validator.isValid("x+2.=8"));
        Assertions.assertFalse(validator.isValid("(x+2)23=8"));
        Assertions.assertFalse(validator.isValid("x=2=8"));
        Assertions.assertFalse(validator.isValid("x+2=8+"));
    }
}
