package com.coen448.robot;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        RobotEngine engine = new RobotEngine();
        Scanner sc = new Scanner(System.in);

        System.out.println("Robot Motion CLI");
        System.out.println("Commands: I n, U, D, L, R, M s, P, C, H, Q");

        while (true) {
            System.out.print("> ");
            String line = sc.nextLine().trim();
            if (line.isEmpty())
                continue;

            if (line.equalsIgnoreCase("Q")) {
                System.out.println("Bye!");
                break;
            }

            try {
                String output = engine.executeCommand(line);
                if (output != null && !output.isBlank())
                    System.out.println(output);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }
}
