package controllers.tenantmigration;

import controllers.BaseController;
import controllers.usermanagement.validator.ShadowUserMigrateReqValidator;
import java.util.concurrent.CompletionStage;
import org.sunbird.common.models.util.ActorOperations;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.request.Request;
import play.mvc.Http;
import play.mvc.Result;

/**
 * @author Amit Kumar This controller will handle all the request related for user migration.
 * @author anmolgupta
 */
public class TenantMigrationController extends BaseController {

  /**
   * Method to migrate user from one tenant to another.
   *
   * @return Result
   */
  public CompletionStage<Result> userTenantMigrate(Http.Request httpRequest) {
    return handleRequest(
        ActorOperations.USER_TENANT_MIGRATE.getValue(),
        httpRequest.body().asJson(),
        null,
        null,
        null,
        true,
        httpRequest);
  }

  public CompletionStage<Result> shadowUserMigrate(Http.Request httpRequest) {
    String callerId = httpRequest.flash().get(JsonKey.USER_ID);
    return handleRequest(
        ActorOperations.MIGRATE_USER.getValue(),
        request().body().asJson(),
        req -> {
          Request request = (Request) req;
          ShadowUserMigrateReqValidator.getInstance(request, callerId).validate();
          return null;
        },
        null,
        null,
        true,
        httpRequest);
  }
}
