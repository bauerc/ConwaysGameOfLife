import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * This class is responsible for holding an accessible method for reading Game of Life states from a file
 * The file uses '#' to denote a 'live' cell, and nearly anything to denote 'dead'.
 * Cells should be delimited by spaces and new lines for a row. Please see input.txt for an example
 *
 * Created by Cameron Bauer on 12/5/16.
 */
public class ConwaysUniverseReader {


    /**
     *
     * @param file
     * @return
     */
    public static ConwaysUniverse readUniverseStateFromFile(String file) {

        try {
            ArrayList<boolean[]> readUniverse = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(file)));
            String line;
            while( (line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(" ");
                boolean[] newRow = new boolean[splitLine.length];
                for(int i = 0; i < splitLine.length; i++) {
                    newRow[i] = splitLine[i].equals("#");
                }
                readUniverse.add(newRow);
            }
            boolean[][] newUniverse = new boolean[readUniverse.size()][readUniverse.get(0).length];
            for (int i = 0; i < newUniverse.length; i++) {
                newUniverse[i] = readUniverse.get(i);
            }
            return new ConwaysUniverse(newUniverse);
        } catch (Exception e) {
            //TODO Better exception handling
            System.out.println("ERROR: IO Exception. Aborting");
            System.out.println(e.getMessage());
        }

        return null;

    }
}
