
public class Parser {
	private String dest; // holds the destination instruction
    private String comp; // holds the computation instruction
    private String jump; // holds the jump instruction
    private String addr; // holds a 16-bit address
    
    public Parser() { //null constructor
        dest = "null";
        jump = "null";
    } // constructor end
    
    
    public boolean parse_command(String line) {
        line = line.trim(); // trim whitespace

        if (!line.isEmpty()) {
            if (line.charAt(0) != '/') {
                if (line.contains("@")) { // A-instruction
                    addr = line.split("@")[1].trim(); // could be a label, variable or number representing an address
                }
                else { // C-instructions
                    if (line.contains("=")) { // contains dest and comp fields, maybe a jump field
                        final String[] fields = line.split("=");
                        dest = fields[0];
                        if (fields[1].contains(";")) { // checking for jump field
                            split_jump(fields[1]);
                        } else {
                            comp = fields[1].split("/")[0].trim(); // trim & remove comments
                        }
                    } else if (line.contains("+") || line.contains("-")) { // contains comp field, maybe a jump field
                        if (line.contains(";")) { // check for jump
                            split_jump(line);
                        } else {
                            comp = line.split("/")[0].trim(); // trim & remove comments
                        }
                    } else if (line.contains(";")) { // contains jump field, maybe comp field
                        split_jump(line);
                    } else {
                        jump = line.split("/")[0].trim(); // trim & remove comments
                    }
                }
                return true; // successfully parsed to some result
            }
        }

        return false; // parsing failed
    } // end parse_command
    
    private void split_jump(final String str) { // cuts out the jump
        final String[] parts = str.split(";");
        comp = parts[0].trim();
        jump =  parts[1].split("/")[0].trim();
    } // end split_jump
    
    public String dest() { // getter for dest
        return dest;
    } // end dest 
    
    public String comp() { // getter for comp
        return comp;
    } // end comp
    
    public String jump() { // getter for jump
        return jump;
    } // end jump
    
    public String addr() { // getter for addr
        return addr;
    } // end addr
}
