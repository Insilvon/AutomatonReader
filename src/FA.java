import java.util.ArrayList;

public class FA {
    private String[] states;
    private String[] alphabet;
    private String startState;
    private String[] finalStates;
    private ArrayList<String[]> transFunctions;

    public FA(ArrayList<String[]> transFunctions, String[] states, String[] alphabet, String startState, String[] finalStates){
        this.transFunctions = transFunctions;
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
        this.finalStates = finalStates;
    }

    /**
     * Returns the transition functions/relations associated with this FA
     * @return ArrayList<String[]></String[]>
     */
    public ArrayList<String[]> getTransFunctions(){
        return this.transFunctions;
    }

    /**
     * Returns the states associated with this FA
     * @return String[]
     */
    public String[] getStates(){
        return this.states;
    }
    /**
     * Returns the alphabet associated with this FA
     * @return String[] (Symbols can be multiple icons)
     */
    public String[] getAlphabet(){
        return this.alphabet;
    }
    /**
     * Returns the initial starting state for the FA
     * @return String
     */
    public String getStartState(){
        return this.startState;
    }
    /**
     * Returns the array of final states for this FA
     * @return String[]
     */
    public String[] getFinalStates(){
        return this.finalStates;
    }
    /**
     * Returns the number of symbols in the alphabet.
     * @return integer, # of symbols
     */
    public int getCardinality(){
        return this.alphabet.length;
    }


}
