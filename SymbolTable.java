import java.util.Hashtable;

public class SymbolTable {
    private int current_register;   // stores register address (in decimal) to which the next variable is assigned
    private final Hashtable<String, Integer> sym_hash; // stores the symbol table

    // constructor
    public SymbolTable() {
        current_register = 16;
        sym_hash = new Hashtable<String, Integer>(25);

        // initialize pre-defined variables
        for (int i = 0; i <= 15; i++) {
            final String key = "R" + i;
            sym_hash.put(key, i);
        }

        sym_hash.put("SCREEN", 16384); // puts into symbol table
        sym_hash.put("KBD", 24576);
        sym_hash.put("SP", 0);
        sym_hash.put("LCL", 1);
        sym_hash.put("ARG", 2);
        sym_hash.put("THIS", 3);
        sym_hash.put("THAT", 4);
    } // end constructor

    
    public boolean addVariable(final String symbol) { // input symbol
        if (!sym_hash.containsKey(symbol)) {
            sym_hash.put(symbol, current_register); // puts symbol and current register
            current_register++; // increment
            return true; // return true for success
        }

        return false;
    } // end addVariable

    
    public void put(final String symbol, final int value) {// input symbol and value
        sym_hash.put(symbol, value); // puts new hash pair
    } // end put

    
    public boolean contains(final String symbol) { // input symbol to be checked
        return sym_hash.containsKey(symbol); // returns if it exists or not
    } // end contains

    
    public int get(final String symbol) { // input symbol to be read
        return sym_hash.get(symbol); // returns value of symbol if it exists
    } // end get
}