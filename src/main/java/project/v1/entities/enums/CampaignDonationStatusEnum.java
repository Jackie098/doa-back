package project.v1.entities.enums;

public enum CampaignDonationStatusEnum {
  PENDING, // Pagamento não confirmado pelo voluntário
  RECEIVED, // Pagamento confirmado pelo voluntário (pix e comprovante)
  SENT, // Comprovante pix enviado pelo voluntário no wpp do agente
  VALIDATED, // Agente confirmou recebimento do valor
  REFUSED; // Recusado por algum motivo
}
