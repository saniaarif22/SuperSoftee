package controllers;

import java.util.Date;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import org.bson.types.ObjectId;
import play.*;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.*;
import com.google.inject.Inject;
import securesocial.core.RuntimeEnvironment;
import securesocial.core.java.SecureSocial;
import securesocial.core.java.SecuredAction;
import securesocial.core.java.UserAwareAction;
import service.User;
import play.data.Form;


//import static groovy.util.GroovyTestCase.assertEquals;

public class Application extends Controller
{
    public static Logger.ALogger logger = Logger.of("application.controllers.Application");
    private RuntimeEnvironment env;

    MyMongoClientFactory mongoFactory = new MyMongoClientFactory(Configuration.empty());


    @Inject()
    public Application(RuntimeEnvironment env)
    {
        this.env = env;
    }


    // function called when a user logs in - its credentials will be identified as driver credentials or manager credentials
    @SecuredAction
    public Result authAction()
    {
        User user = (User) ctx().args.get(SecureSocial.USER_KEY);

        String userType = getUserType(user.main.email().get());

        if (userType == Credential.managerType)
        {
            return redirect("/assets/html/managerDashboard.html");
        }
        if (userType == Credential.driverType)
        {
            return redirect("/assets/html/truckDriverDashboard.html");
        }

        return redirect("/auth/logout");
//        return redirect("/assets/html/managerDashboard.html");
    }


    // returns the type of the user as a String (manager, truckDriver or not authenticated)
    private String getUserType(String userEmail)
    {
        if ( Credential.findByManagerEmail(userEmail) != null )
        {
            return Credential.managerType;
        }

        if ( Credential.findByDriverEmail(userEmail) != null )
        {
            return Credential.driverType;
        }

        return Credential.notAuthType;
    }


    // Return relevant information about the user that is logged in
    @UserAwareAction
    public Result getUserInfo()
    {
        User user = (User) ctx().args.get(SecureSocial.USER_KEY);

        String userType = getUserType(user.main.email().get());

        if ( userType.equals(Credential.notAuthType) )
        {
            return ok("");
        }

        String userName = user.main.firstName().get();

        String userAvatarUrl = user.main.avatarUrl().get();

        // Create the JSON response
        ObjectNode res = (ObjectNode) Json.newObject();
        res.put("type", userType);
        res.put("name", userName);
        res.put("avatarUrl", userAvatarUrl);

        return ok(res);
    }


    @UserAwareAction
    public Result insertManagerEmail()
    {
        DynamicForm requestData = Form.form().bindFromRequest();

        Credential cred = new Credential();

        cred.email = requestData.get("managerEmail");

        cred.insertManagerEmail();

        return redirect("/assets/html/managerDashboard.html");
    }

    public Result insertDriverEmail()
    {
        DynamicForm requestData = Form.form().bindFromRequest();

        Credential cred = new Credential();

        cred.email = requestData.get("driverEmail");

        cred.insertDriverEmail();

        return redirect("/assets/html/managerDashboard.html");
    }

    public Result getDriverEmails()
    {
        return ok(Credential.findAllByDriverEmail());
    }

    public Result getManagerEmails()
    {
        return ok(Credential.findAllByManagerEmail());
    }

    public Result removeDriverEmail(String email)
    {
        Credential cred = Credential.findByDriverEmail(email);

        cred.removeDriverEmail();

        return ok("");
    }

    public Result removeManagerEmail(String email)
    {
        Credential cred = Credential.findByManagerEmail(email);

        cred.removeManagerEmail();

        return ok("");
    }

    @SecuredAction()
    public Result getOrderSupplies()
    {
        // Return all the supplies that the truck driver can order
        return ok(OrderSupply.findAll());
    }


    private Integer safeParseInt(String stringValue)
    {
        if (null == stringValue || stringValue.length() == 0)
        {
            return 0;
        }

        Integer result = 0;

        try
        {
            result = Integer.parseInt(stringValue, 10);
        } catch (NumberFormatException nfe)
        {
            // In this case we were expecting their might be an exception and we
            // can silently ignore it
            result = 0;
        }

        return result;
    }


    @SecuredAction
    public Result enterOrderSupply()
    {

        DynamicForm requestData = Form.form().bindFromRequest();

        // Get the current user so we can record who placed the order
        User user = (User) ctx().args.get(SecureSocial.USER_KEY);

        String userName = user.main.firstName().get();

        // Create a new order supply object to hold the order.
        OrderSupply orderSupply = new OrderSupply();

        orderSupply.id = new ObjectId();
        orderSupply.orderDate = new Date();
        orderSupply.driverName = userName;
        orderSupply.kidCone = safeParseInt(requestData.get("kidCone"));
        orderSupply.smallCones = safeParseInt(requestData.get("smallCones"));
        orderSupply.doubleCone = safeParseInt(requestData.get("doubleCone"));
        orderSupply.waffleCones = safeParseInt(requestData.get("waffleCone"));
        orderSupply.cartweels = safeParseInt(requestData.get("cartwheels"));
        orderSupply.simpleSyrup = safeParseInt(requestData.get("simpleSyrup"));
        orderSupply.chocolateSyrup = safeParseInt(requestData.get("cholateSyrup"));
        orderSupply.vanillaSyrup = safeParseInt(requestData.get("vanillaSyrup"));
        orderSupply.strawberryShakeBase = safeParseInt(requestData.get("strawberryShakeBase"));
        orderSupply.wetWalnuts = safeParseInt(requestData.get("wetWalnuts"));
        orderSupply.wholeCherries = safeParseInt(requestData.get("wholeCherries"));
        orderSupply.toppingStrawberry = safeParseInt(requestData.get("toppingStrawberry"));
        orderSupply.toppingPineapple = safeParseInt(requestData.get("toppingPineapple"));
        orderSupply.toppingButterscotch = safeParseInt(requestData.get("toppingButterscotch"));
        orderSupply.toppingCruchedCherry = safeParseInt(requestData.get("toppingCruchedCherry"));
        orderSupply.smallDixieCups = safeParseInt(requestData.get("smallDixieCups"));
        orderSupply.largeDixieCups = safeParseInt(requestData.get("largeDixieCups"));
        orderSupply.ozCups = safeParseInt(requestData.get("ozCups"));
        orderSupply.milkShakeCups = safeParseInt(requestData.get("milkShakeCups"));
        orderSupply.sundaeCups = safeParseInt(requestData.get("sundaeCups"));
        orderSupply.bananaBoats = safeParseInt(requestData.get("bananaBoats"));
        orderSupply.lids = safeParseInt(requestData.get("lids"));
        orderSupply.spoons = safeParseInt(requestData.get("spoons"));
        orderSupply.straws = safeParseInt(requestData.get("straws"));
        orderSupply.chocolateJimmies = safeParseInt(requestData.get("chocolateJimmies"));
        orderSupply.rainbowJimmies = safeParseInt(requestData.get("rainbowJimmies"));
        orderSupply.vanillaMix = safeParseInt(requestData.get("vanillaMix"));
        orderSupply.bananasBunch = safeParseInt(requestData.get("bananasBunch"));
        orderSupply.napkins = safeParseInt(requestData.get("napkins"));
        orderSupply.milk = safeParseInt(requestData.get("milk"));
        orderSupply.rwbBombPop = safeParseInt(requestData.get("rwbBombPop"));
        orderSupply.jollyRancherBombPop = safeParseInt(requestData.get("jollyRancherBombPop"));
        orderSupply.jollyRancherSnowcone = safeParseInt(requestData.get("jollyRancherSnowcone"));
        orderSupply.screwballCherry = safeParseInt(requestData.get("screwballCherry"));
        orderSupply.faceBratz = safeParseInt(requestData.get("faceBratz"));
        orderSupply.faceSonic = safeParseInt(requestData.get("faceSonic"));
        orderSupply.minion = safeParseInt(requestData.get("minion"));
        orderSupply.message = requestData.get("message");
        orderSupply.insert();


        return redirect("/assets/html/ordersupplyplaced.html");
    }

}

