//package controllers;
//
//import com.google.inject.Inject;
//import models.MyMongoClientFactory;
//import models.Order;
//import org.bson.types.ObjectId;
//import play.Configuration;
//import play.Logger;
//import play.data.DynamicForm;
//import play.data.Form;
//import play.mvc.*;
//import securesocial.core.BasicProfile;
//import securesocial.core.RuntimeEnvironment;
//import securesocial.core.java.SecureSocial;
//import securesocial.core.java.SecuredAction;
//import securesocial.core.java.UserAwareAction;
//import service.User;
//
///**
// * Created by Sania on 12/3/15.
// */
//public class ApplicationOrder extends Controller
//{
//    public static Logger.ALogger logger = Logger.of("application.controllers.Application");
//    private RuntimeEnvironment env;
//
//    MyMongoClientFactory mongoFactory = new MyMongoClientFactory(Configuration.empty());
//
//    @Inject()
//    public ApplicationOrder(RuntimeEnvironment env)
//    {
//        this.env = env;
//    }
//
//    @SecuredAction
//    public Result authAction()
//    {
//        if (logger.isDebugEnabled())
//        {
//            logger.debug("access granted to index");
//        }
//        User user = (User) ctx().args.get(SecureSocial.USER_KEY);
//        return redirect("/assets/html/managerDashboard.html");
//    }
//
//    @UserAwareAction
//    public Result userAware()
//    {
//        User User = (User) ctx().args.get(SecureSocial.USER_KEY);
//        String userName;
//        if (User != null)
//        {
//            BasicProfile user = User.main;
//            if (user.firstName().isDefined())
//            {
//                userName = user.firstName().get();
//            } else if (user.fullName().isDefined())
//            {
//                userName = user.fullName().get();
//            } else
//            {
//                userName = "authenticated user";
//            }
//        } else
//        {
//            userName = "guest";
//        }
//        return ok("Hello " + userName + ", you are seeing a public page");
//    }
//
//    @SecuredAction
//    public Result approveOrder(String id)
//    {
//
//        // Get the id of the order we want to transfer to the "approvedOrder" table
//        ObjectId oId = new ObjectId(id);
//
//        // Get that order by its id
//        Order approvedOrder = Order.findById(oId);
//
//        if (approvedOrder == null) return ok("Error - order not in the database.");
//
//        // Insert it to the new table
//        approvedOrder.insertApprovedOrders();
//
//        // Remove it from the original table, to complete the transfer
//        approvedOrder.remove();
//
//        // Nothing to be returned to the client
//        return ok();
//    }
//
//    @SecuredAction
//    public Result getApprovedOrders()
//    {
//        // Return all the approved orders that the truck driver can see
//        return ok(Order.findAllApprovedOrders());
//    }
//
//    @SecuredAction
//    public Result finishOrder(String id)
//    {
//
//        // Get the id of the order we want to transfer to the "approvedOrder" table
//        ObjectId oId = new ObjectId(id);
//
//        // Get that order by its id
//        Order finishOrder = Order.findByIdApproved(oId);
//
//        if (finishOrder == null) return ok("Error - order not in the database.");
//
//        // Insert it to the new table
//        finishOrder.insertFinishedOrders();
//
//        // Remove it from the original table, to complete the transfer
//        finishOrder.removeApprovedOrders();
//
//        // Nothing to be returned to the client
//        return ok();
//    }
//
//
//    @SecuredAction()
//    public Result getOrders()
//    {
//        // Return the all the orders that haven't been approved or rejected yet
//        return ok(Order.findAll());
//    }
//
//    public Result getFinishedOrders()
//    {
//
//        return ok(Order.findAllFinishedOrders());
//    }
//
//    public Result enterOrder()
//    {
//        DynamicForm requestData = Form.form().bindFromRequest();
//
//        Order order = new Order();
//        order.id = new ObjectId();
//        order.oName = requestData.get("oName");
//        order.oDate = requestData.get("oDate");
//        order.oTime = requestData.get("oTime");
//        order.oAddress = requestData.get("oAddress");
//        order.oPhoneNumber = requestData.get("oPhoneNumber");
//        order.oCoupon = requestData.get("oCoupon");
//
//        order.insert();
//
//        return redirect("/assets/html/orderplaced.html");
//    }
//
//    public Result enterOrder(Order order)
//    {
//        order.insert();
//
//        return redirect("/assets/html/orderplaced.html");
//    }
//}
package controllers;

import com.google.api.client.util.DateTime;
import com.google.inject.Inject;
import com.google.api.services.calendar.model.*;
import models.MyMongoClientFactory;
import models.Order;
import org.bson.types.ObjectId;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.Configuration;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import scala.collection.mutable.StringBuilder;
import securesocial.core.BasicProfile;
import securesocial.core.RuntimeEnvironment;
import securesocial.core.java.SecureSocial;
import securesocial.core.java.SecuredAction;
import securesocial.core.java.UserAwareAction;
import service.User;

/**
 * Created by Sania on 12/3/15.
 */
public class ApplicationOrder extends Controller
{
    public static Logger.ALogger logger = Logger.of("application.controllers.Application");
    private RuntimeEnvironment env;

    MyMongoClientFactory mongoFactory = new MyMongoClientFactory(Configuration.empty());

    @Inject()
    public ApplicationOrder(RuntimeEnvironment env)
    {
        this.env = env;
    }

    @SecuredAction
    public Result loggedin()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("access granted to index");
        }
        User user = (User) ctx().args.get(SecureSocial.USER_KEY);
        return redirect("/assets/html/managerDashboard.html");
    }

    @UserAwareAction
    public Result userAware()
    {
        User User = (User) ctx().args.get(SecureSocial.USER_KEY);
        String userName;
        if (User != null)
        {
            BasicProfile user = User.main;
            if (user.firstName().isDefined())
            {
                userName = user.firstName().get();
            } else if (user.fullName().isDefined())
            {
                userName = user.fullName().get();
            } else
            {
                userName = "authenticated user";
            }
        } else
        {
            userName = "guest";
        }
        return ok("Hello " + userName + ", you are seeing a public page");
    }

    @SecuredAction
    public Result approveOrder(String id)
    {
        // Get the id of the order we want to transfer to the "approvedOrder" table
        ObjectId oId = new ObjectId(id);

        // Get that order by its id
        Order approvedOrder = Order.findById(oId);

        if (approvedOrder == null) return ok("Error - order not in the database.");

        // Insert it to the new table
        approvedOrder.insertApprovedOrders();

        // Remove it from the original table, to complete the transfer
        approvedOrder.remove();

        // Add a corresponding google calendar entry
        try {
            com.google.api.services.calendar.Calendar service = ApplicationCalendar.getCalendarService();

            if (null != service) {
                // The start date is being saved as a date/time in the database.  Grab just the date
                // portion.
                String justTheDate = 0 < approvedOrder.oDate.indexOf(" ") ? approvedOrder.oDate.substring(0, approvedOrder.oDate.indexOf(" ")) : approvedOrder.oDate;
                String theDateTime = justTheDate + " " + approvedOrder.oTime;

                // Now the date will be in the format "16/12/2015 13:01" (24 hour clock).  Create a
                // formatter to turn it into a Java date time object.
                DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yy HH:mm");

                // Create the start and end date/times.  We assume 3 hours for each event.
                org.joda.time.DateTime startDateTime = formatter.parseDateTime(theDateTime);
                org.joda.time.DateTime endDateTime = startDateTime.plusHours(3);

                // convert them into the google format
                DateTime gStartDate = new DateTime(startDateTime.getMillis());
                DateTime gEndDate = new DateTime(endDateTime.getMillis());

                EventDateTime eventStartDate = new EventDateTime();
                EventDateTime eventEndDate = new EventDateTime();

                eventStartDate.setDateTime(gStartDate);
                eventEndDate.setDateTime(gEndDate);

                // Create a new google calendar event.
                Event event = new Event();

                event.setSummary("Approved event for " + approvedOrder.oName);
                event.setStart(eventStartDate);
                event.setEnd(eventEndDate);
                event.setLocation(approvedOrder.oAddress);

                StringBuilder descriptionBuilder = new StringBuilder();

                if (null != approvedOrder.oPhoneNumber) {
                    descriptionBuilder.append("Phone : " + approvedOrder.oPhoneNumber + "\n");
                }
                if (null != approvedOrder.oCoupon) {
                    descriptionBuilder.append("Coupon : " + approvedOrder.oCoupon + "\n");
                }

                // the calendar ID
                String calendarID = "q0l7pjdncarqncmnlcp1emo71g@group.calendar.google.com";

                event = service.events().insert(calendarID, event).execute();

                System.out.println(event.getHtmlLink());
            }
        } catch (java.lang.Exception e) {
            logger.error("Unable to create Google Calendar Event.",e);
        }
        // Nothing to be returned to the client
        return ok();
    }

    @SecuredAction
    public Result getApprovedOrders()
    {
        // Return all the approved orders that the truck driver can see
        return ok(Order.findAllApprovedOrders());
    }

    @SecuredAction
    public Result finishOrder(String id)
    {

        // Get the id of the order we want to transfer to the "approvedOrder" table
        ObjectId oId = new ObjectId(id);

        // Get that order by its id
        Order finishOrder = Order.findByIdApproved(oId);

        if (finishOrder == null) return ok("Error - order not in the database.");

        // Insert it to the new table
        finishOrder.insertFinishedOrders();

        // Remove it from the original table, to complete the transfer
        finishOrder.removeApprovedOrders();

        // Nothing to be returned to the client
        return ok();
    }


    @SecuredAction()
    public Result getOrders()
    {
        // Return the all the orders that haven't been approved or rejected yet
        return ok(Order.findAll());
    }

    public Result getFinishedOrders()
    {
        return ok(Order.findAllFinishedOrders());
    }

    public Result enterOrder()
    {
        DynamicForm requestData = Form.form().bindFromRequest();

        Order order = new Order();
        order.id = new ObjectId();
        order.oName = requestData.get("oName");
        order.oDate = requestData.get("oDate");
        order.oTime = requestData.get("oTime");
        order.oAddress = requestData.get("oAddress");
        order.oPhoneNumber = requestData.get("oPhoneNumber");
        order.oCoupon = requestData.get("oCoupon");

        order.insert();

        return redirect("/assets/html/orderplaced.html");
    }

    public Result enterOrder(Order order)
    {
        order.insert();

        return redirect("/assets/html/orderplaced.html");
    }
}
