package project.common.exceptions;

public enum MessageErrorEnum {
  INTERNAL_ERROR("Erro inesperado no servidor."),
  ACCOUNT_INVALID_TO_ACTION("Sua conta ainda não está ativa para efetuar esta ação"),
  USER_NOT_FOUND("Usuário não encontrado."),
  USER_PASS_NOT_MATCH("Dados de login estão errados!"),
  USER_TYPE_ENUM_INVALID("Tipo de usuário inválido."),
  USER_ALREADY_EXISTS("Um usuário já existe com estes dados."),
  LEGAL_RESPONSIBLE_ALREADY_EXISTS("Um responsável legal já existe com estes dados."),
  AGENT_NOT_FOUND("Agente não encontrado!"),
  AGENT_ALREADY_EXISTS("Um agente de caridade já existe com estes dados."),
  SCHEDULED_CAMPAIGN_WITHOUT_START_DATE(
      "A data de início é obrigatória quando você não deseja iniciar a campanha agora."),
  SCHEDULED_CAMPAIGN_INVALID_START_DATE("A data de início não pode ser anterior a data atual"),
  SCHEDULED_CAMPAIGN_INVALID_DUE_DATE("A data de encerramento não pode ser anterior a data de início da campanha"),
  SLUG_ALREADY_EXISTS("O código utilizado já está vinculado a outra campanha.");

  public String message;

  private MessageErrorEnum(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}
