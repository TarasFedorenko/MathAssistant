package com.geekforless.tfedorenko.dao;

import java.sql.Connection;
import java.util.List;

public interface MathAssistantDAO {

    boolean createEquation(String equation);

    void createRootForEquation(String equation, String root);

    List<String> getAllEquationByRoot(String root);

    List<String> getAllEquationByNumberOfRoots(int number);

    Integer getIdEquationByValue(Connection connection,String equation);
}
