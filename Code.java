import java.util.*;

 public class Code {
	 // hash tables for each segment of dest, jump and comp
     private static Hashtable<String, String> dest_hash = new Hashtable<String, String>(8);
     private static Hashtable<String, String> jump_hash = new Hashtable<String, String>(8);
     private static Hashtable<String, String> comp_hash = new Hashtable<String, String>(28);

     
     private static void init_dest_table() { // puts in dest translations
        dest_hash.put("null", "000");
        dest_hash.put("M", "001");	// Memory (RAM)
        dest_hash.put("D", "010");	// D-register
        dest_hash.put("MD", "011");
        dest_hash.put("A", "100");	// A-register
        dest_hash.put("AM", "101");
        dest_hash.put("AD", "110");
        dest_hash.put("AMD", "111");
     } // end init_dest_table

     
     private static void init_jump_table() { // puts in jump translations
        jump_hash.put("null", "000");	// no jump
        jump_hash.put("JGT", "001");	// jump if greater than zero
        jump_hash.put("JEQ", "010");	// jump if equal to zero
        jump_hash.put("JGE", "011");	// jump is greater than or equal to zero
        jump_hash.put("JLT", "100");	// jump if less than zero
        jump_hash.put("JNE", "101");	// jump if not equal to zero
        jump_hash.put("JLE", "110");	// jump if less than or equal to zero
        jump_hash.put("JMP", "111");	// unconditional jump
     } // end init_jump_table

     
     private static void init_comp_table() { // puts in comp translations
        comp_hash.put("0", "0101010");
        comp_hash.put("1", "0111111");
        comp_hash.put("-1", "0111010");
        comp_hash.put("D", "0001100");
        comp_hash.put("A", "0110000");
        comp_hash.put("!D", "0001101");
        comp_hash.put("!A", "0110001");
        comp_hash.put("-D", "0001111");
        comp_hash.put("-A", "0110011");
        comp_hash.put("D+1", "0011111");
        comp_hash.put("A+1", "0110111");
        comp_hash.put("D-1", "0001110");
        comp_hash.put("A-1", "0110010");
        comp_hash.put("D+A", "0000010");
        comp_hash.put("D-A", "0010011");
        comp_hash.put("A-D", "0000111");
        comp_hash.put("D&A", "0000000");
        comp_hash.put("D|A", "0010101");
        comp_hash.put("M", "1110000");
        comp_hash.put("!M", "1110001");
        comp_hash.put("-M", "1110011");
        comp_hash.put("M+1", "1110111");
        comp_hash.put("M-1", "1110010");
        comp_hash.put("D+M", "1000010");
        comp_hash.put("D-M", "1010011");
        comp_hash.put("M-D", "1000111");
        comp_hash.put("D&M", "1000000");
        comp_hash.put("D|M", "1010101");
     } // end init_comp_table

     
    public static String get_comp_code(final String key) { // input key symbols
		init_comp_table(); // creates table
		return comp_hash.get(key); // returns 7-bit binary for comp
	} // end get_comp_code

  
    public static String get_dest_code(final String key) { // input key symbols
	  init_dest_table(); // creates table
	  return dest_hash.get(key); // returns 3-bit binary for dest
	} // end get_dest_code

  
    public static String get_jump_code(final String key) { // input key symbols
		init_jump_table(); // creates table
        return jump_hash.get(key); // returns 3-bit binary for jump
    }// end get_jump_code
     
 }