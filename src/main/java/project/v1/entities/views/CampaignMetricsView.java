package project.v1.entities.views;

public class CampaignMetricsView {
    public static final String QUERY = """
        WITH donations_by_campaign AS (
        SELECT cd.campaign_id, SUM(cd.ticket_quantity) AS tickets_donation
        FROM campaign_donations cd
        WHERE cd.is_donation = TRUE AND cd.status != 'REFUSED'
        GROUP BY cd.campaign_id
    ),
    tickets_sold_by_campaign AS (
    	SELECT cd.campaign_id, SUM(cd.ticket_quantity) AS tickets_sold
    	FROM campaign_donations cd
    	WHERE cd.status != 'REFUSED'
    	GROUP BY cd.campaign_id
    ),
    tickets_available_by_campaign AS (
    	SELECT c.id AS campaign_id, c.total_tickets - COALESCE(ts.tickets_sold, 0) + COALESCE(dc.tickets_donation, 0) AS tickets_available
    	FROM campaigns c --ON c.id = cd.campaign_id
    	LEFT JOIN tickets_sold_by_campaign ts ON ts.campaign_id = c.id
    	LEFT JOIN donations_by_campaign dc ON dc.campaign_id = c.id
    )
    SELECT
    	c.id AS campaign_id,
    	c.ticket_price,
    	c.total_tickets,
        COALESCE(tc.tickets_sold, 0) AS tickets_sold,
    	COALESCE(tac.tickets_available, 0) AS tickets_available,
    	COALESCE(dc.tickets_donation, 0) AS tickets_donation,
    	SUM(CASE WHEN cd.status = 'PENDING' THEN cd.ticket_quantity ELSE 0 END) AS tickets_pending,
    	SUM(CASE WHEN cd.status = 'RECEIVED' THEN cd.ticket_quantity ELSE 0 END) AS tickets_received,
    	SUM(CASE WHEN cd.status = 'SENT' THEN cd.ticket_quantity ELSE 0 END) AS tickets_sent,
    	SUM(CASE WHEN cd.status = 'VALIDATED' THEN cd.ticket_quantity ELSE 0 END) AS tickets_validated,
    	SUM(CASE WHEN cd.status = 'REFUSED' THEN cd.ticket_quantity ELSE 0 END) AS tickets_refused
    	FROM  campaign_donations cd
    JOIN campaigns c ON c.id = cd.campaign_id
    LEFT JOIN donations_by_campaign AS dc ON dc.campaign_id = cd.campaign_id
    LEFT JOIN tickets_sold_by_campaign AS tc ON tc.campaign_id = cd.campaign_id
    LEFT JOIN tickets_available_by_campaign AS tac ON tac.campaign_id = cd.campaign_id
    GROUP BY c.id, c."name", c.ticket_price, c.total_tickets, tc.tickets_sold, tac.tickets_available, dc.tickets_donation
    ORDER BY campaign_id;
        """;
}
