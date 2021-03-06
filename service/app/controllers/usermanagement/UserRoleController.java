package controllers.usermanagement;

import controllers.BaseController;
import controllers.usermanagement.validator.UserRoleRequestValidator;
import org.sunbird.common.models.util.ActorOperations;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.request.Request;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

public class UserRoleController extends BaseController {

  public CompletionStage<Result> getRoles(Http.Request httpRequest) {
    return handleRequest(ActorOperations.GET_ROLES.getValue(), httpRequest);
  }

  public CompletionStage<Result> assignRoles(Http.Request httpRequest) {
     final boolean isPrivate = httpRequest.path().contains(JsonKey.PRIVATE)?true:false;

    return handleRequest(
        ActorOperations.ASSIGN_ROLES.getValue(),
        httpRequest.body().asJson(),
        (request) -> {
          Request req = (Request) request;
          req.getContext().put(JsonKey.PRIVATE, isPrivate);
          new UserRoleRequestValidator().validateAssignRolesRequest(req);
          return null;
        },
            httpRequest);
  }
}
