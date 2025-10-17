package project.common.utils;

import java.util.Random;

public class SlugUtils {
    private static final Random random = new Random();
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    
    static public String cleanSlug(String slug) {
        return slug.toLowerCase().replace(" ", "-");
    }
    
    static public String generateAgentSlug(String slug) {
        StringBuilder slugMaker = new StringBuilder();
        
        for (int i = 0; i < 2; i++) {
            slugMaker.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        }
        
        for (int i = 0; i < 2; i++) {
            slugMaker.append(random.nextInt(10));
        }
        
        return slug.toLowerCase() + "-" + slugMaker.toString();
    }
}
