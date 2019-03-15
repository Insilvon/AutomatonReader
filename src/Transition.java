public class Transition {
    private String lastState;
    private String symbol;
    private String state;
    public Transition(String[] vars){
        this.lastState = vars[0];
        this.symbol = vars[1];
        this.state = vars[2];
    }
    public String getState() {
        return state;
    }
    public String getSymbol() {
        return symbol;
    }
    public String getLastState(){return lastState;}
}
