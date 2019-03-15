import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class NFA extends FA{
    private String[][] table;
    private int row;
    private int col;
    public NFA(ArrayList<String[]> transFunctions, String[] states, String[] alphabet, String startState, String[] finalStates){
        super(transFunctions, states, alphabet, startState, finalStates);
        int cols = this.getCardinality();
        int rows = this.getStates().length+1; //number of states, +1 for possible sink state
        this.col = cols;
        this.row = rows;
        table = new String[rows][cols];
        setUp();
    }
    public void setUp(){
        String[] states = this.getStates(); //Represents the rows (Last Row is for added sink state)
        ArrayList<String> stateIndecies = new ArrayList<String>(Arrays.asList(states));

        ArrayList<String[]> transFunctions = this.getTransFunctions();

        String[] alpha = this.getAlphabet(); //Represents the columns
        ArrayList<String> alphabet = new ArrayList<String>(Arrays.asList(alpha));
        //loop
        for (int i = 0; i<transFunctions.size();i++){
            String[] transition = transFunctions.get(i);
            int rowIndex = stateIndecies.indexOf(transition[0]);
            int colIndex = alphabet.indexOf(transition[1]); //does this need to be cast to string?
            String value = transition[2];
            this.table[rowIndex][colIndex] = value;
        }
        //loop through the table, looking for gaps in the transition table. If a gap is found,
        //add a transition to the sink state. Loop to row-1 to avoid sink state.
        for(int i = 0; i<row-1; i++){
            for (int j = 0; j<col; j++){
                if (this.table[i][j] == null){
                    String[] sinkTrans = {stateIndecies.get(i), alphabet.get(j), "sink"};
                    transFunctions.add(sinkTrans);
                }
            }
        }
        // add transitions from sink state to sink state
        for (int j = 0; j<col; j++){
            String[] sinkTrans = {"sink", alphabet.get(j),"sink"};
            transFunctions.add(sinkTrans);
        }
        //set up transitions for epsilon cases

    }
}
