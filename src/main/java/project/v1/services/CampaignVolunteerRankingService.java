package project.v1.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.v1.entities.CampaignVolunteerRanking;
import project.v1.repositories.CampaignVolunteerRankingRepository;

@ApplicationScoped
public class CampaignVolunteerRankingService {
  @Inject
  private CampaignVolunteerRankingRepository repository;

  public List<CampaignVolunteerRanking> getRanking(Long campaignId) {
    return repository.getRanking(campaignId);
  }

  public String shareRanking(Long campaignId) {
    List<CampaignVolunteerRanking> ranking = getRanking(campaignId);
    String formattedRowsRanking = "";

    if (ranking.isEmpty()) {
      return "⌛ Calma... Ainda não há doações para esta campanha!";
    }

    for (int i = 0; i < ranking.size(); i++) {
      Integer position = i + 1;
      String formattedPosition = formatPosition(position);
      String formattedName = formatName(position, ranking.get(i).getName());

      formattedRowsRanking += formattedPosition + " " + formattedName + " " + ranking.get(i).getTicketsCollected();

      if (position % 10 == 0) {
        formattedRowsRanking += addNewLine(2);
      } else {
        formattedRowsRanking += addNewLine(1);
      }
    }

    String formattedRanking = addTextHeader("Arrecadação da Campanha") + addNewLine(1) + formattedRowsRanking;

    return formattedRanking;
  }

  private String getEmojiByPosition(int position) {
    switch (position) {
      case 1:
        return "🥇";
      case 2:
        return "🥈";
      case 3:
        return "🥉";
      default:
        return "";
    }
  }

  private String formatPosition(int position) {
    String emoji = getEmojiByPosition(position);

    if (emoji.isBlank()) {
      return position + "º";
    }

    return emoji;
  }

  private String formatName(int position, String name) {
    if (position <= 3) {
      return "*" + name + "*";
    }

    return name;
  }

  private String addTextHeader(String title) {
    String formatedTitle = "🎁 " + "*" + title + "*" + " 🎉" + addNewLine(1);
    String subtitle = "_RANKING DOS VOLUNTÁRIOS_" + addNewLine(2);

    return formatedTitle + subtitle;
  }

  private String addNewLine(Integer quantity) {
    return System.lineSeparator().repeat(quantity);
  }
}
