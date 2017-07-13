import java.io.File;

import static java.lang.System.exit;

/**
 * This class is responsible for actually running the simulation.
 * It accepts TWO (2) command line arguments:
 * arg 0 - filename, the file to be read that contains and appropraite Game of Life state
 * arg 1- iterations, the number of times the simulation should be run, if none default = 5
 *
 * Created by Cameron Bauer on 12/5/16.
 */
public class ConwaysGameOfLife {

    public ConwaysGameOfLife(String file, int iterations) {

        ConwaysUniverse universe = ConwaysUniverseReader.readUniverseStateFromFile(file);

        for(int i = 0; i < iterations; i++) {
            universe.generateNextUniverse();
            System.out.println("\nIteration " + i);
            universe.printCurrentUniverse();
        }

    }

    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println("arg = " + arg);
        }


        //Verify arguments
        if(args.length < 1 || args[0] == null) {
            System.out.println("ERROR: No file name provided");
            System.out.println("Proper usage: arg 0 - filename, arg 1 - iterations (optional, default = 5)");
            exit(0);
        }

        if(!(new File(args[0]).exists())) {
            System.out.println("ERROR: " +args[0] + " does not exist. Please provide a valid file name");
            exit(0);
        }
        int iterations = 5;
        if(args.length > 1 && args[1] != null) {
            try {iterations = Integer.parseInt(args[1]);}
            catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid iteration value provided. Defaulting to 5");
            }
        }


        // Run Game of Life code
        new ConwaysGameOfLife(args[0],iterations);

    }

}
