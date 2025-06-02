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
  AGENT_ALREADY_EXISTS("Um agente de caridade já existe com estes dados.");

  public String message;

  private MessageErrorEnum(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}
