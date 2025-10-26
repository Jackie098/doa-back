package project.v1.entities;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Immutable
public class CampaignVolunteerRanking {
	@Id
	private Long volunteerId;

	@Column(name = "name")
	private String name;

	@Column(name = "tickets_collected")
	private Long ticketsCollected;

	@Column(name = "tickets_donated")
	private Long ticketsDonated;
}
