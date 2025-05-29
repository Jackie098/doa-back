package project.common.utils;

public class MaskUtils {
  public static String removeCpfMask(String cpf) {
    return cpf.replaceAll("[^\\d]", "");
  }

  public static String removeCnpjMask(String cnpj) {
    return cnpj.replaceAll("[^\\d]", "");
  }
}