package project.common.requests;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class ResponseModel<T> {
  public static final String SUCESS = "OK";
  public static final String ERROR = "ERROR";

  private String status;
  private Integer statusCode;
  private String message;
  private List<String> messages;

  private T data;

  private Long totalElements;
  private Integer totalPages;

  @JsonInclude(Include.NON_NULL)
  private List<T> list;

  private List<String> stackTrace;

  public static <T> ResponseModel<T> success() {
    ResponseModel<T> ret = new ResponseModel<T>();
    ret.status = ResponseModel.SUCESS;
    ret.setStatusCode(200);

    return ret;
  }

  public static <T> ResponseModel<T> success(Integer statusCode) {
    ResponseModel<T> ret = new ResponseModel<T>();
    ret.setStatus(ResponseModel.SUCESS);
    ret.setStatusCode(statusCode);
    return ret;
  }

  public static <T> ResponseModel<T> success(Integer statusCode, T data) {
    ResponseModel<T> ret = new ResponseModel<T>();
    ret.setStatus(ResponseModel.SUCESS);
    ret.setStatusCode(statusCode);
    ret.setData(data);
    return ret;
  }

  public static <T> ResponseModel<T> success(Integer statusCode, List<T> data) {
    ResponseModel<T> ret = new ResponseModel<T>();
    ret.status = ResponseModel.SUCESS;
    ret.setStatusCode(statusCode);
    ret.setList(data);

    return ret;
  }

  public static <T> ResponseModel<T> error(String message) {
    ResponseModel<T> ret = new ResponseModel<T>();
    ret.setStatus(ResponseModel.ERROR);
    ret.setMessage(message);
    ret.setStatusCode(500);

    return ret;
  }

  public static <T> ResponseModel<T> error(List<String> messages) {
    ResponseModel<T> ret = new ResponseModel<T>();
    ret.setStatus(ResponseModel.ERROR);
    ret.setMessages(messages);
    ret.setStatusCode(500);

    return ret;
  }

  public static <T> ResponseModel<T> error(Integer statusCode, String message) {
    ResponseModel<T> ret = new ResponseModel<T>();
    ret.setStatus(ResponseModel.ERROR);
    ret.setMessage(message);
    ret.setStatusCode(statusCode);

    return ret;
  }

  public static <T> ResponseModel<T> error(Integer statusCode, List<String> messages) {
    ResponseModel<T> ret = new ResponseModel<T>();
    ret.setStatus(ResponseModel.ERROR);
    ret.setMessages(messages);
    ret.setStatusCode(statusCode);

    return ret;
  }
}
