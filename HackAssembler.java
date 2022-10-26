import java.io.*;
import java.util.*;

public class HackAssembler { // declaring variables
    private final SymbolTable symbols; // stores symbols and labels
    private int current_line;          // keeps track of the current line
    private Parser parser;             // parses Assembly commands into individual segments

    // constructor
    public HackAssembler() {
        symbols = new SymbolTable(); // initializing current line and symbol storage
        current_line = 0;
    } // end constructor


    private void first_pass(final String filename) { // scans file for labels & saves them in symbolTable, input filename
        try {
            final BufferedReader input = new BufferedReader(new FileReader(filename));
            boolean parse_success; // parse error flag
            String line;

            while ((line = input.readLine()) != null) { // while line isn't null
                parser = new Parser();
                parse_success = parser.parse_command(line); // parse the line

                if (parse_success) {
                    if (line.trim().charAt(0) == '(') { // scan for labels [ eg. (LABEL) ]
                        // extract the label's symbol & trim it
                        final String symbol = line.trim().substring(line.indexOf("(") + 1, line.lastIndexOf(")"));

                        // add label to SymbolTable if it is not already present
                        if (!symbols.contains(symbol))
                            symbols.put(symbol, current_line);

                        current_line--; // label declarations do not count as a line count
                    }
                    current_line++; // successfully parsed command counts as one line, assuming it is not decremented by above
                }
            }
            input.close();
        } catch (final IOException ioe) { // io exception
            System.out.println(ioe);
            return;
        }
    } // end first pass

    /**
     * Translates a Hack Assembly file (.asm) into machine code (.hack file)
     * according to the Hack Machine Language specifications, after the first pass.
     * 
     * @param filename The assembly file to translate into machine code
     */
    private void translate(final String filename) { // translate the file after labels are processed
        try {
            final String output_filename = filename.substring(0, filename.indexOf(".")) + ".hack"; // change file extension from .asm to .hack
            final BufferedReader input = new BufferedReader(new FileReader(filename));
            final PrintWriter output = new PrintWriter(output_filename);

            current_line = 0; // reset counter for current line
            boolean parse_success; // flag for parsing error
            String line;

            while ((line = input.readLine()) != null) { // while line isn't null
                parser = new Parser();
                parse_success = parser.parse_command(line); // parse line

                if (parse_success && line.trim().charAt(0) != '(') { // label declarations don't count, so skips over them
                    if (parser.addr() == null) { // parsing a C-instruction
                        final String comp = Code.get_comp_code(parser.comp()); // reads comp from parser
                        final String dest = Code.get_dest_code(parser.dest()); // reads dest from parser
                        final String jump = Code.get_jump_code(parser.jump()); // reads jump from parser
                        output.printf("111%s%s%s\n", comp, dest, jump); // writes to .hack file
                    } else { // parsing an A-instruction
                        final String var = parser.addr();

                        final Scanner sc = new Scanner(var);
                        if (sc.hasNextInt()) { // check if var is an integer
                            final String addr_binary = Integer.toBinaryString(Integer.parseInt(var)); // convert to binary
                            output.println(pad_binary(addr_binary)); // write 16-bit binary to output
                        } else {
                            symbols.addVariable(var);
                            final String addr_binary = Integer.toBinaryString(symbols.get(var));
                            output.println(pad_binary(addr_binary));
                        }
                        sc.close();
                    }
                    current_line++; // increment current line
                }
            }
            input.close(); // closes reader
            output.close(); // closes writer
        } catch (final IOException ioe) {
            System.out.println(ioe);
            return;
        }
    } // end translate


    private String pad_binary(final String unpadded_binary) { // adds leading zeros necessary to make a value 16-bits
        String padded_binary = "";
        final int num_zeros = 16 - unpadded_binary.length();

        for (int i = 0; i < num_zeros; i++) {
            padded_binary += "0";
        }

        return padded_binary + unpadded_binary; // returns padded binary
    } // end pad_binary

    
    public static void main(final String[] args) { // runs assembler using "$ java HackAssembler filename"
        final String filename = args[0]; // only uses 1st arg (args[0])
        final HackAssembler assembly = new HackAssembler(); // initialize object
        assembly.first_pass(filename); // read label
        assembly.translate(filename); // translate rest of file
    } // end main
}