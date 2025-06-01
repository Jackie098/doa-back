package project.common.exceptions;

import java.util.List;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import project.common.exceptions.customs.BusinessException;
import project.common.requests.ResponseModel;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

  @Override
  public Response toResponse(Throwable exception) {
    exception.printStackTrace();

    ResponseModel<Object> response;

    if (exception instanceof ConstraintViolationException validationEx) {
      List<String> messages = validationEx.getConstraintViolations()
          .stream()
          .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
          .toList();

      response = ResponseModel.error(Response.Status.BAD_REQUEST.getStatusCode(), messages);
      return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }

    if (exception instanceof BusinessException be) {
      response = ResponseModel.error(be.getStatusCode(), be.getMessage());
      return Response.status(be.getStatusCode()).entity(response).build();
    }

    response = ResponseModel.error(MessageErrorEnum.INTERNAL_ERROR.getMessage());

    // TODO: Delete this in production
    response.setStackTrace(List.of(exception.getMessage()));

    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
  }
}
