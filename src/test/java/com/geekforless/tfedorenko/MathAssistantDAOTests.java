package com.geekforless.tfedorenko;
import com.geekforless.tfedorenko.config.JDBCService;
import com.geekforless.tfedorenko.config.impl.JDBCServiceImpl;
import com.geekforless.tfedorenko.dao.MathAssistantDAO;
import com.geekforless.tfedorenko.dao.impl.MathAssistantDAOImpl;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MathAssistantDAOTests {
        JDBCService jdbcService= new JDBCServiceImpl();
        private MathAssistantDAO mathAssistantDAO;

        @BeforeEach
        void setUp() {
            mathAssistantDAO = new MathAssistantDAOImpl();
        }

        @Test
        @Order(1)
        void testCreateEquation() {
            assertTrue(mathAssistantDAO.createEquation("2*x+3=5"));
        }
        @Order(2)
        @Test
        void testCreateExistingEquation() {
            assertFalse(mathAssistantDAO.createEquation("2*x+3=5"));
        }

        @Order(3)
        @Test
        void testCreateRootForEquation() {
            mathAssistantDAO.createEquation("2*x+3=5");
            mathAssistantDAO.createRootForEquation("2*x+3=5", "x=2");

        }

        @Test
        void testGetAllEquationByRoot() {
            List<String> equations = mathAssistantDAO.getAllEquationByRoot("x=2");

        }

        @Test
        void testGetAllEquationByNumberOfRoots() {
            List<String> equations = mathAssistantDAO.getAllEquationByNumberOfRoots(1);

        }


    }

