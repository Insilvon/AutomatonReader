import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class NFA2 {
    public NFA2(DFA map){

    }
    private ArrayList<DFANode> epsilonClosures;

    class DFANode {
        private String name;
        private Set<String> states;
        private ArrayList<String[]> transitions = new ArrayList<String[]>();
        //Format: this node's name, transition type, end node

        public DFANode(Set<String> states){
            this.states = states;
        }

        private DFANode union(DFANode target){
            Set<String> newStates = null;
            String[] temp1 = (String[]) this.states.toArray();
            String[] temp2 = (String[]) target.states.toArray();
            newStates.addAll(Arrays.asList(temp1));
            newStates.addAll(Arrays.asList(temp2));
            return new DFANode(newStates);
        }
    }

}
