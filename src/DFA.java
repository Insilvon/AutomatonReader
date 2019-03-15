import java.util.ArrayList;

public class DFA extends FA {

    class DFAState {
        //name of this node/state
        String name;
        //all the arrows that point off of this node (needs to equal the cardinality of the alphabet)
        ArrayList<Transition> transitions = new ArrayList<Transition>();

        public DFAState(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void addTransition(Transition var){
            transitions.add(var);
        }
        public boolean hasTransition(String name){
            for(int i = 0; i<transitions.size(); i++){
                if (transitions.get(i).getSymbol().equals(name)) return true;
            }
            return false;
        }
        public DFAState getNextState(char symbol){
            for (int i = 0; i<transitions.size(); i++){
                Transition currentTransition = transitions.get(i);
                if (currentTransition.getSymbol().equals(symbol+"")){
                    return findState(currentTransition.getState());
                }
            }
            return null;
        }
    }

    private ArrayList<DFAState> nodes = new ArrayList<DFAState>();
    private ArrayList<DFAState> endStates = new ArrayList<DFAState>();

    public DFA(ArrayList<String[]> transFunctions, String[] states, String[] alphabet, String startState, String[] finalStates){
        super(transFunctions, states, alphabet, startState, finalStates);
        createStates(states);
        createEndStates(finalStates);
        addTransitions(transFunctions);
        purify();
    }
    private void purify(){
        //loop through all states. If a transition is missing, add one to a sink state.
        //If a state is an end state, make the transition point to itself
        for (int i = 0; i<nodes.size(); i++){
            DFAState currentState = nodes.get(i);
            //check if it is an end state (should loop?)
            if (purifyHelper(currentState.getName())){
                //it is an end state, so add self loops?
                if(currentState.transitions.size()!=this.getCardinality()){
                    //Okay, so the number of transitions DOES NOT EQUAL the number of symbols in the alphabet, so time to find the missing link.
                    for(int j = 0; j<this.getAlphabet().length; j++){
                        if (!currentState.hasTransition(this.getAlphabet()[j])){
                            //if our current State DOES NOT have a transition for this symbol, we add one.
                            String[] temp = {currentState.getName(), this.getAlphabet()[j], currentState.getName()};
                            Transition tempT = new Transition(temp);
                            currentState.addTransition(tempT);
                        }
                    }
                }
            }
            else { // this is not an end state, so add sink state transitions
                for(int j = 0; j<this.getAlphabet().length; j++){
                    if (!currentState.hasTransition(this.getAlphabet()[j])){
                        //if our current State DOES NOT have a transition for this symbol, we add one.
                        String[] temp = {currentState.getName(), this.getAlphabet()[j], "sink"};
                        Transition tempT = new Transition(temp);
                        currentState.addTransition(tempT);
                    }
                }
            }
        }
    }
    private boolean purifyHelper(String name){
        for(int i = 0; i<getFinalStates().length; i++){
            if (getFinalStates()[i].equals(name)) return true;
        }
        return false;
    }
    /**
     * THIS NEEDS TO PULL FROM THE NODES INSTEAD OF FROM STATES. FIND THE STATES IN NODES, THEN ADD THEM HERE.
     * @param states
     */
    private void createEndStates(String[] states){
        for (int i = 0; i<states.length; i++){
            String item = states[i];
            DFAState state = new DFAState(item);
            endStates.add(state);
        }
    }
    private void createStates(String[] states){
        for (int i = 0; i<states.length; i++){
            String item = states[i];
            DFAState state = new DFAState(item);
            nodes.add(state);
        }
        DFAState sinkState = new DFAState("sink");
        sinkState.addTransition(new Transition(new String[] {"sink","a","sink"}));
        sinkState.addTransition(new Transition(new String[] {"sink","b","sink"}));
        nodes.add(sinkState);
    }
    private void addTransitions(ArrayList<String[]> transitions) {
        for (int i = 0; i<transitions.size(); i++){
            String[] values = transitions.get(i);
            String name = values[0];
            Transition var = new Transition(values);
            if(findState(name)!=null) findState(name).addTransition(var);
            }
        }

    private DFAState findState(String name){
        for (int i = 0; i<nodes.size(); i++){
            if (nodes.get(i).getName().equals(name)){
                return nodes.get(i);
            }
        }
        return null;
    }

    private DFAState getStartingState(){
        return findState(this.getStartState());
    }

    public boolean verify(String input){
        DFAState currentState = this.getStartingState();
        for(int i = 0; i<input.length(); i++){
            char symbol = input.charAt(i);
            currentState = currentState.getNextState(symbol);
        }
        return verifyHelper(currentState);
    }
    private boolean verifyHelper(DFAState currentState){
        for(int i = 0; i<endStates.size();i++){
            if (endStates.get(i).getName().equals(currentState.getName())) return true;
        }
        return false;
    }


}
