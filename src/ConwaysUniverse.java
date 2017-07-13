/**
 * This class is responsible for maintaing the state of the Game of Life and moving from one state to the next
 * State is determined via a lookup table for efficieny. On object creation all 2^9 (512) possible states for a 3x3
 * cell determined and stored to a lookup table. The Universe where the state is stored is padded with an extra
 * rows and columns of dead cells to make logic for updating state easier to understand.
 *
 * Created by Cameron Bauer on 11/30/16.
 */
public class ConwaysUniverse {

    private boolean[][] currentUniverse;
    private boolean[][] prevUniverse;
    private int height;
    private int width;

    //Generation could be made static if I want to use elsewhere easily
    private boolean[] lookupTable;


    public ConwaysUniverse(boolean[][] universe) {
        this.height = universe.length;
        this.width  = universe[0].length;
        this.currentUniverse = padUniverse(universe);
        this.lookupTable = createLookupTable();
    }

    /**
     * Adds two rows and columns of dead cells to an existing Universe
     * @param universe - Game of Life state to pad
     * @return padded Game of Life state
     */
    private boolean[][] padUniverse(boolean[][] universe) {
        boolean[][] newUniverse = new boolean[height + 2][width + 2];
        newUniverse[0] = (new boolean[width + 2]);
        for (int i = 0; i < height; i++) {
            boolean[] paddedCopy = new boolean[width + 2];
            System.arraycopy(universe[i], 0, paddedCopy, 1, universe[i].length);
            newUniverse[i + 1] = (paddedCopy);
        }
        newUniverse[height + 1] = (new boolean[width + 2]);
        return newUniverse;
    }

    /**
     * Creates lookup table for all 2^9 (512) possible Game of Life states of a 3x3 cell
     * If the center cell is ALIVE next cycle, lookup table value will be TRUE, FALSE otherwise
     * @return Generated lookup table
     */
    private boolean[] createLookupTable() {
        boolean[] nextCycleLookupTable = new boolean[512];
        for(int i = 0; i < 512; i++) {
            nextCycleLookupTable[i] = aliveNextCycle(i);
        }
        return nextCycleLookupTable;
    }

    /**
     * Determines if the center of a 3x3 cell will be ALIVE next cycle. Cell combinations are indexed by binary
     * representation and the 3x3 cell is divided by the following manner:
     * [ 1 x 2^8] [1 x 2^5] [1 x 2^2]
     * [ 1 x 2^7] [1 x 2^4] [1 x 2^1]
     * [ 1 x 2^6] [1 x 2^3] [1 x 2^0]
     * This way a cell is determined to be ALIVE if the BIT value is 1, DEAD if 0
     * @param i Current 3x3 cell index to be considered
     * @return TRUE if center cell is alive next cycle, FALSE otherwise
     */
    private boolean aliveNextCycle(int i) {

        // Generate a count of living cells in the area surrounding center cell
        int count = (i >> 8 & 0x1) + (i >> 5 & 0x1) + (i >> 2 & 0x1)
                + (i >> 7 & 0x1) + 0 + (i >> 1 & 0x1)
                + (i >> 6 & 0x1) + (i >> 3 & 0x1) + (i >> 0 & 0x1);

        // Use the Rules of Conway's Game of Life
        if ((i >> 4 & 0x1) == 1) {
            // If an ALIVE cell has...

            // ... less than 2 ALIVE neighbors, then it dies
            if (count < 2) return false;

            // ... more than 3 ALIVE neighbors, then it dies
            if (count > 3) return false;

            // ... exactly 2 or 3 ALIVE neighbors, then it lives
            return true;
        } else {
            // If a DEAD cell has...

            // ... exactly 3 ALIVE neighbors, then it becomes alive
            if (count == 3) return true;

            // ... otherwise it remains dead
            return false;
        }
    }

    /**
     * Prints Game of Life state to the console w/o padding
     */
    public void printCurrentUniverse() {
        for (int i = 1; i <= height; i++) {
            for (int j = 1; j <= width; j++) {
                System.out.print(((currentUniverse[i][j]) ? "#" : "_") + " ");
            }
            System.out.println();
        }
    }
    /**
     * Prints previous Game of Life state to the console w/o padding
     */
    public void printPrevUniverse() {
        for (int i = 1; i <= height; i++) {
            for (int j = 1; j <= width; j++) {
                System.out.print(((prevUniverse[i][j]) ? "#" : "_") + " ");
            }
            System.out.println();
        }
    }

    /**
     * Uses lookup table to determine the state of the next Universe.
     * The lookup table uses binary representation of a 3x3 cell, so the current universe is read
     * in 3x3 segments and is converted to the binary count. Since indexing places the most significant bits
     * on the left side of the 3x3 cell, lookup values need to be only partially calculated for an entire row
     *
     * The new Game of Life state is saved as the current Universe and the old version is preserved
     */
    public void generateNextUniverse() {
        boolean[][] newUniverse = new boolean[height+2][width+2];

        // Due to padding, Universe values are actually 1 indexed and inclusive.
        for(int i = 1; i <= height; i++) {
            int lookupValue = (currentUniverse[i-1][0] ? 32 : 0) + (currentUniverse[i-1][1] ? 4 : 0)
                            + (currentUniverse[i][0] ? 16 : 0) + (currentUniverse[i][1] ? 2 : 0)
                            + (currentUniverse[i+1][0] ? 8 : 0) + (currentUniverse[i+1][1] ? 1 : 0);


            for(int j = 1; j <= width; j++) {
                lookupValue = ((lookupValue % 64) * 8) +
                            + (currentUniverse[i-1][j+1] ? 4 : 0)
                            + (currentUniverse[i][j+1] ? 2 : 0)
                            + (currentUniverse[i+1][j+1] ? 1 : 0);
                newUniverse[i][j] = lookupTable[lookupValue];
            }
        }

        this.prevUniverse = currentUniverse;
        this.currentUniverse = newUniverse;
    }


    // GETTERS
    public boolean[][] getCurrentUniverse() {
        return currentUniverse;
    }

    public boolean[] getLookupTable() {
        return lookupTable;
    }

}
