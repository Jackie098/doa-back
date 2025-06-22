package project.common.exceptions;

public enum MessageErrorEnum {
  INTERNAL_ERROR("Erro inesperado no servidor."),
  ACCOUNT_INVALID_TO_ACTION("Sua conta ainda não está ativa para efetuar esta ação"),
  USER_NOT_FOUND("Usuário não encontrado."),
  USER_PASS_NOT_MATCH("Dados de login estão errados!"),
  USER_INVALID_TYPE_ENUM("Tipo de usuário inválido."),
  USER_ALREADY_EXISTS("Um usuário já existe com estes dados."),
  USER_LINKED_NON_EXISTENT_AGENT("Nenhum agente encontrado para este usuário"),
  LEGAL_RESPONSIBLE_ALREADY_EXISTS("Um responsável legal já existe com estes dados."),

  AGENT_NOT_FOUND("Agente não encontrado!"),
  AGENT_ALREADY_EXISTS("Um agente de caridade já existe com estes dados."),

  SCHEDULED_CAMPAIGN_WITHOUT_START_DATE(
      "A data de início é obrigatória quando você não deseja iniciar a campanha agora."),
  SCHEDULED_CAMPAIGN_INVALID_START_DATE("A data de início não pode ser anterior a data atual"),

  CAMPAIGN_DUE_DATE_BEFORE_START_DATE("A data de encerramento não pode ser anterior a data de início da campanha"),
  CAMPAIGN_SLUG_ALREADY_EXISTS("O código utilizado já está vinculado a outra campanha."),
  CAMPAIGN_FINISH_ONLY_STATUS_ACTIVE("É possível finalizar somente uma campanha ativa."),
  CAMPAIGN_PASSED_DEADLINE("O prazo de encerramento desta campanha venceu."),
  CAMPAIGN_ALREADY_FINISHED("Campanha já finalizada!"),

  CAMPAIGN_PAUSE_ONLY_STATUS_ACTVE_OR_AWAIT("É possível pausar somente uma campanha agendada ou ativa"),
  CAMPAIGN_NOT_FOUND("Campanha não encontrada!"),
  CAMPAIGN_ALREADY_CANCELED("Esta campanha já está cancelada."),
  CAMPAIGN_ALREADY_ACTIVE("Campanha já está ativa!"),
  CAMPAIGN_CANCELED_CANNOT_REACTIVATE("Campanhas canceladas não podem ser reativadas."),
  CAMPAIGN_DONT_BELONG_USER("O usuário não tem acesso a esta campanha!"),

  CAMPAIGN_UPDATE_WITHOUT_DATA("Não é possível atualizar uma campanha com nenhum valor novo."),
  CAMPAIGN_UPDATE_DUE_DATE_TO_REACTIVATE(
      "Você precisa atualizar a data de vencimento da campanha para poder reativá-la."),
  CAMPAIGN_UPDATE_TICKET_PRICE_BEFORE_START("Somente é possível alterar o valor do ticket antes da campanha começar."),
  CAMPAIGN_UPDATE_TOTAL_TICKETS_STATUS_INACTIVE(
      "Não é possível alterar o total de tickets pois a campanha está cancelada ou finalizada."),
  SCHEDULED_CAMPAIGN_UPDATE_START_DATE(
      "Só é possível alterar a data de início se a campanha estiver agendada (SCHEDULED)."),
  CAMPAIGN_START_DATE_AFTER_DUE_DATE("A data de início não pode ser posterior à data de término."),
  CAMPAIGN_DUE_DATE_STATUS_CANCELED("Não é possível alterar a data de término de uma campanha cancelada."),
  CAMPAIGN_NOT_RECEIVE_NEW_VOLUNTEER("Campanha não pode receber novos voluntários porque está inativa."),
  CAMPAIGN_VOLUNTEER_BIND_ALREADY_EXISTS("O voluntário já está vinculado nesta campanha!"),
  CAMPAIGN_VOLUNTEER_BIND_NOT_EXISTS("O voluntário não está vinculado nesta campanha!"),

  CAMPAIGN_NOT_ACTIVE_DOESNT_RECEIVE_DONATIONS(
      "A campanha não pode receber novas doações pois não está ativa no momento."),

  VOLUNTEERS_DONT_BELONGS_CAMPAIGN("Usuários não encontrados, não vinculados à esta campanha ou já foram aceitos."),
  VOLUNTEER_NO_ACCEPTED_CANT_ADD_NEW_DONATION(
      "O voluntário não pode inserir uma nova doação pois ainda não foi aceito na campanha.");

  public String message;

  private MessageErrorEnum(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}
