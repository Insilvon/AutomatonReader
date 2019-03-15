import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    static boolean isDFA = true;

    public static void main(String[] args) throws FileNotFoundException {
        File fa = new File("./COSC485_P1_DFA.txt");
        FA temp = parseFa(fa);

        if (isDFA) {
            DFA var = (DFA) temp;
        } else {
            NFA var = (NFA) temp;
        }
    }

    /**
     * Converts a line like States =, Alphabet =, Final States =
     * to a usable String[] for our nodes later.
     * @param line current line to read
     * @param trans whether or not this is a transition parse (True = yes, False = no)
     * @return String[]
     */
    private static String[] getData(String line, boolean trans){
        StringBuilder temp = new StringBuilder(line);
        int leftIndex = 0;
        int rightIndex = 0;
        if(trans){
            leftIndex = temp.indexOf("(")+1;
            rightIndex = temp.indexOf(")");
        } else {
            leftIndex = temp.indexOf("{")+1;
            rightIndex = temp.indexOf("}");
        }
        String splice = temp.substring(leftIndex, rightIndex);
        //clean up the line, remove everything that isn't the names
        temp = new StringBuilder(splice);
        while(temp.toString().contains(",")){
            temp.deleteCharAt(temp.indexOf(","));
        }
        if (temp.charAt(0)==' ') temp.deleteCharAt(0);
        String[] values = temp.toString().split(" ");
        return values;
    }

    /**
     * Fetches an individual token from the stuff in this line
     * @param line line to read
     * @return String
     */
    private static String getStartData(String line){
        String value = "";
        int i = 0;
        for (i = 0; i<line.length();i++){
            if (line.charAt(i)=='=') break;
        }
        i++;
        while(i<line.length()&&line.charAt(i)!=','){
            if(line.charAt(i)!=' ') value+=line.charAt(i);
            i++;
        }
        return value;
    }

    /**
     * Reads the FA file and sifts out the data it needs
     * @param file file to read
     */
    private static FA parseFa(File file){
        String[] states = new String[0];
        String[] alphabet = new String[0];
        String startState = "";
        String[] finalStates = new String[0];
        ArrayList<String[]> transitions = new ArrayList<>();
        boolean dfa = true;

        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNext()){
                String line = reader.nextLine();
                while (line.length()==0) line = reader.nextLine();

                //Is this the header line? Are we dealing with a DFA or NFA?
                if (line.contains("M")) {
                    if (line.contains("Relation")) dfa = false;
                }

                // This is not the header line
                else {
                    // Is this the States line?
                    if(line.contains("States")) {
                        if (line.contains("Final")) finalStates = getData(line, false);
                        else states = getData(line, false);
                    }
                    // Is this the Alphabet Line?
                    if (line.contains("Alphabet")) alphabet = getData(line, false);
                    // Is this the Starting State line?
                    if (line.contains("Starting State")) startState = getStartData(line);
                    // Is this the Transition section?
                    if (line.contains("Transition")){
                        if(reader.hasNextLine()) line = reader.nextLine();
                        while(!line.contains("}")&&reader.hasNextLine()){
                            String[] temp = getData(line, true);
                            transitions.add(temp);
                            line = reader.nextLine();
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Rest of code here.
        if (dfa) {
            isDFA = true;
            DFA var = new DFA(transitions, states, alphabet, startState, finalStates);
            return var;
        } else {
            isDFA = false;
            NFA var = new NFA(transitions, states, alphabet, startState, finalStates);
            return var;
        }

    }
}
