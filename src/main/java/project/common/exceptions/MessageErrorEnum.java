package project.common.exceptions;

public enum MessageErrorEnum {
  INTERNAL_ERROR("Erro inesperado no servidor."),
  USER_ALREADY_EXISTS("Um usuário já existe com estes dados."),
  LEGAL_RESPONSIBLE_ALREADY_EXISTS("Um responsável legal já existe com estes dados."),
  AGENT_ALREADY_EXISTS("Um agente de caridade já existe com estes dados.");

  public String message;

  private MessageErrorEnum(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
}
