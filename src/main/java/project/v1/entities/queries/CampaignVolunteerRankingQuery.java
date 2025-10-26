package project.v1.entities.queries;

public class CampaignVolunteerRankingQuery {
	public static final String FIND_BY_CAMPAIGN_ID = """
			SELECT
				cv.id AS volunteer_id,
				u.name AS name,
				SUM(cd.ticket_quantity) AS tickets_collected,
				COALESCE(SUM(CASE WHEN cd.is_donation = true THEN cd.ticket_quantity ELSE 0 END), 0) AS tickets_donated
			FROM campaign_donations cd
			JOIN campaign_volunteers cv ON cv.id = cd.campaign_volunteer_id
			JOIN users u ON u.id = cv.user_id
			WHERE cd.campaign_id = :campaignId
				AND cd.status != 'REFUSED'
			GROUP BY cv.id, u.name
			ORDER BY tickets_collected DESC
			""";
}
