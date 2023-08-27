package com.geekforless.tfedorenko.dao.impl;

import com.geekforless.tfedorenko.config.JDBCService;
import com.geekforless.tfedorenko.config.impl.JDBCServiceImpl;
import com.geekforless.tfedorenko.dao.MathAssistantDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MathAssistantDAOImpl implements MathAssistantDAO {
    JDBCService jdbcService = new JDBCServiceImpl();
    public static final String CREATE_EQUATION = "INSERT INTO equation values(default,?) ";
    public static final String CREATE_ROOT = "INSERT INTO roots values (default,?,?)";
    public static final String FIND_ID_BY_VALUE = "SELECT equation_id FROM equation WHERE equation_value = ?";

    public static final String FIND_ALL_BY_ROOT = "SELECT e.equation_value from equation e join roots r on e.equation_id = r.eq_id where r.roots_value =?";
    public static final String FIND_ALL_BY_NUMBER_ROOT = "SELECT e.equation_value FROM equation e JOIN roots r ON e.equation_id = r.eq_id GROUP BY e.equation_value HAVING COUNT(r.roots_value) = ?";
    public static final String CHECK_EQUATION = "SELECT EXISTS (SELECT 1 FROM equation WHERE equation_value = ?)";

    @Override
    public boolean createEquation(String equation) {
        if (equationExists(equation)) {
            System.out.println("Equation already exist");
            return false;
        }
        try (PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement(CREATE_EQUATION)) {
            preparedStatement.setString(1, equation);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            ;
            return false;
        }
    }

    public boolean equationExists(String equation) {
        try (PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement(CHECK_EQUATION)) {
            preparedStatement.setString(1, equation);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void createRootForEquation(String equation, String root) {
        try (Connection connection = jdbcService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ROOT)) {

            Integer equationId = getIdEquationByValue(connection, equation);
            if (equationId != null) {
                preparedStatement.setInt(1, equationId);
                preparedStatement.setString(2, root);
                preparedStatement.executeUpdate();
            } else {
                System.out.println("Equation does not exist");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getAllEquationByRoot(String root) {
        List<String> equations = new ArrayList<>();
        String equation = "";
        try (PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement(FIND_ALL_BY_ROOT)) {
            preparedStatement.setString(1, root);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    equation = resultSet.getString("equation_value");
                    equations.add(equation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equations;
    }

    @Override
    public List<String> getAllEquationByNumberOfRoots(int number) {
        List<String> equations = new ArrayList<>();
        String equation = "";
        try (PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement(FIND_ALL_BY_NUMBER_ROOT)) {
            preparedStatement.setInt(1, number);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    equation = resultSet.getString("equation_value");
                    equations.add(equation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equations;
    }

    @Override
    public Integer getIdEquationByValue(Connection connection, String equation) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ID_BY_VALUE)) {
            preparedStatement.setString(1, equation);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("equation_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
