package utils;

public class InputValidator {

    public static boolean isInteger(String text) {
        
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return false;
        }
        
        return true;
    }
    
}
