public class Transition {
    private String state;
    private String symbol;
    public Transition(String[] vars){
        this.symbol = vars[0];
        this.state = vars[1];
    }
    public String getState() {
        return state;
    }
    public String getSymbol() {
        return symbol;
    }
}
