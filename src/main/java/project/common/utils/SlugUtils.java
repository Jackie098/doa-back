package project.common.utils;

import java.time.LocalDate;
import java.util.Random;

import project.common.exceptions.MessageErrorEnum;
import project.common.exceptions.customs.BusinessException;
import project.common.utils.enums.MonthEnum;

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

    static public String generateCampaignSlug(String slug) {
      String baseSlug = cleanSlug(slug);
      LocalDate now = LocalDate.now();
      
      String year = String.valueOf(now.getYear()).substring(2);
      String month = MonthEnum.valueOf(now.getMonth().name()).getValue();

      String templateMonthYear = "-" + month + "-" + year;
      String expectedPattern = baseSlug + templateMonthYear;
      
      if (slug.contains(templateMonthYear)) {
        Boolean hasCount = !slug.endsWith(templateMonthYear);

        if (hasCount) {
          String lastPart = slug.substring(slug.lastIndexOf("-") + 1);

          if (lastPart.matches("\\d{2}")) {
            int currentCount = Integer.parseInt(lastPart);
            int nextCount = currentCount + 1;

            return slug.substring(0, slug.lastIndexOf("-") + 1) + String.format("%02d", nextCount);
          } else {
            throw new BusinessException(MessageErrorEnum.CAMPAIGN_SLUG_INVALID_FORMAT.getMessage(), 400);
          }
        } else {
            return slug + "-01";
        }
      } else {
        return expectedPattern;
      }
  }
}
