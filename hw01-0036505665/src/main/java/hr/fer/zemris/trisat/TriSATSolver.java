package hr.fer.zemris.trisat;

import hr.fer.zemris.trisat.algorithms.*;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * {@link TriSATSolver} is a program for demonstrating algorithms used to solve the 3-SAT problem.
 *
 * The index of the algorithm to execute and the path to a SAT formula config file should be given as
 * command-line arguments.
 *
 * @author Bruna Dujmović
 *
 */
public class TriSATSolver {

    /**
     * The main method. Creates a {@link SATFormula} object from the given config file and executes
     * the specified algorithm.
     *
     * @param args the command-line arguments, 2 expected - algorithm index and config file path
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Expected 2 arguments, got " + args.length);
            System.exit(1);
        }

        SATFormula formula = null;
        try {
            formula = SATFormulaBuilder.fromFile(Paths.get(args[1]));

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);

        } catch (IOException e) {
            System.out.println("I/O error occurred when opening the file!");
            System.exit(1);
        }

        BitVector solution = null;
        switch (args[0]) {
            case "1":
                solution = new Algorithm1(formula).execute(); break;
            case "2":
                solution = new Algorithm2(formula).execute(); break;
            case "3":
                solution = new Algorithm3(formula).execute(); break;
            case "4":
                solution = new Algorithm4(formula).execute(); break;
            case "5":
                solution = new Algorithm5(formula).execute(); break;
            case "6":
                solution = new Algorithm6(formula).execute(); break;
            default:
                System.out.println("Invalid algorithm index " + args[0] + ".");
                System.exit(1);
        }

        if (solution == null) {
            System.out.println("Neuspjeh!");
        } else {
            System.out.println("Zadovoljivo: " + solution);
        }
    }
}
