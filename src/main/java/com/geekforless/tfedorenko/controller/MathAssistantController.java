package com.geekforless.tfedorenko.controller;

import com.geekforless.tfedorenko.dao.MathAssistantDAO;
import com.geekforless.tfedorenko.dao.impl.MathAssistantDAOImpl;
import com.geekforless.tfedorenko.util.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class MathAssistantController {

    MathAssistantDAO mathAssistantDAO = new MathAssistantDAOImpl();
    Validator validator = new Validator();


    public void getStart() {
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("\n***Greetings in  Mathematician Assistant Application***");
            String line;
            menu();
            while ((line = bf.readLine()) != null) {
                action(bf, line);
            }
        } catch (IOException e) {
            System.out.println("Input/Output problem");
        }
    }

    private void menu() {
        System.out.println("\n*** MENU ***\n");
        System.out.println("1. If you want to add some mathematical equations press - 1");
        System.out.println("2. If you want to find mathematical equations by root value press -2");
        System.out.println("3. If you want to find mathematical equations by root quantity - 3");
        System.out.println("4. If you want to quit press - q");
    }

    private void action(BufferedReader bf, String line) throws IOException {
        switch (line) {
            case "1" -> addEquation(bf);
            case "2" -> findAllEquationByRootValue(bf);
            case "3" -> findAllEquationByRootQuantity(bf);
            case "q" -> exitApp();
            default -> System.out.println("Wrong input. Try again");
        }
    }

    private void addEquation(BufferedReader bf) throws IOException {
        String equation = "";
        String root = "";
        System.out.println(" \nEnter equation here: ");
        equation = bf.readLine();
        String normalizeEquation = changeToStandard(equation);
        if (validator.isValid(normalizeEquation)) {
            if (mathAssistantDAO.createEquation(normalizeEquation)) {
                System.out.println("Thank you!!! equation is valid and add to database\n ");
            }
            System.out.println("Do you want to enter root for this equation\n Yes - y, No - n");
            String solution = bf.readLine();
            if (Objects.equals(solution, "y")) {
                System.out.println("Enter the root: ");
                root = bf.readLine();
                if (validator.isSolvable(normalizeEquation, root)) {
                    System.out.println("Your root fits");
                    mathAssistantDAO.createRootForEquation(normalizeEquation, root);
                    exitApp();
                } else {
                    System.out.println("Sorry your root wrong");
                    menu();
                }
            } else if (Objects.equals(solution, "n")) {
                System.out.println("Back to main menu");
                menu();

            } else {
                System.out.println("Wrong input try again");
                menu();
            }
        } else {
            System.out.println("You have a mistake in your equation!!!\n Try again");
            menu();
        }

    }

    private void findAllEquationByRootValue(BufferedReader bf) throws IOException {
        String root = "";
        List<String> eqList;
        System.out.println("\nEnter root for equation search here: ");
        root = bf.readLine();
        if (validator.isCorrect(root)) {
            System.out.println("Here equations that you need");
            eqList = mathAssistantDAO.getAllEquationByRoot(root);
            for (String eq : eqList) {
                System.out.println(eq);
            }
            System.out.println("Thank you!");
            exitApp();
        } else {
            System.out.println("Your root incorrect!!! \n Choose another");
        }

    }

    private void findAllEquationByRootQuantity(BufferedReader bf) throws IOException {
        String quantity = "";
        List<String> eqList;
        System.out.println("\nEnter quantity of roots in equation for search here: ");
        quantity = bf.readLine();
        if (validator.isCorrectQuantity(quantity)) {
            System.out.println("Here equations that you need");
            eqList = mathAssistantDAO.getAllEquationByNumberOfRoots(Integer.parseInt(quantity));
            for (String eq : eqList) {
                System.out.println(eq);
            }
            System.out.println("Thank you!");
            exitApp();
        } else {
            System.out.println("Your input incorrect!!! \n Choose another");
        }
    }

    private void exitApp() {
        System.out.println("Good bye!!!");
        System.exit(0);
    }

    private String changeToStandard(String equation) {
        return equation.toLowerCase().replace(" ", "");
    }

}
