package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.inject.Inject;
import models.Feedback;
import models.MyMongoClientFactory;
import org.bson.types.ObjectId;
import play.Configuration;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import securesocial.core.BasicProfile;
import securesocial.core.RuntimeEnvironment;
import securesocial.core.java.SecureSocial;
import securesocial.core.java.SecuredAction;
import securesocial.core.java.UserAwareAction;
import service.User;

public class ApplicationFeedback extends Controller {
    public static Logger.ALogger logger = Logger.of("application.controllers.Application");
    private RuntimeEnvironment env;

    MyMongoClientFactory mongoFactory = new MyMongoClientFactory(Configuration.empty());

    @Inject()
    public ApplicationFeedback(RuntimeEnvironment env) {
        this.env = env;
    }

    @SecuredAction
    public Result loggedin() {
        if (logger.isDebugEnabled()) {
            logger.debug("access granted to index");
        }
        User user = (User) ctx().args.get(SecureSocial.USER_KEY);
        return redirect("/assets/html/managerDashboard.html");
    }


    @SecuredAction()
    public Result getFeedbacks()
    {

        return ok(Feedback.findAll());
    }

    public Result enterFeedback()
    {
        DynamicForm requestData = Form.form().bindFromRequest();

        Feedback feedback = new Feedback();
        feedback.id = new ObjectId();
        feedback.customerName = requestData.get("customerName");
        feedback.customerEmail = requestData.get("customerEmail");
        feedback.phoneNumber = requestData.get("phoneNumber");
        feedback.message = requestData.get("message");

        feedback.insert();

        return redirect("/assets/html/contacted.html");
    }

    public Result enterFeedback(Feedback feedback)
    {

        feedback.insert();

        return redirect("/assets/html/contacted.html");
    }

}