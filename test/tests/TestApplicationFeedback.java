package tests;

import controllers.ApplicationFeedback;
import controllers.ApplicationOrder;
import models.Feedback;
import org.bson.types.ObjectId;
import org.junit.Test;
import play.mvc.Call;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import service.MyEnvironment;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static play.test.Helpers.fakeRequest;


public class TestApplicationFeedback extends WithApplication
{
    @Test
    public void testGetFeedback()
    {
        Result result = new ApplicationFeedback(new MyEnvironment()).getFeedbacks();
        assertEquals(OK, result.status());
        assertEquals("text/plain", result.contentType().toString());
        assertTrue(contentAsString(result).contains("Error - No feedbacks exist in the database."));
        System.out.println("test");
    }

    @Test
    public void testCallGetFinishedOrder()
    {
        Result result = route(
                controllers.routes.ApplicationFeedback.getFeedbacks()
        );
        assertEquals(OK, result.status());
    }

    //Testing if Test Order data is input into the database (Unit test to see where the application breaks in case
    // the testEnterOrderWithForm test fails (i.e. knowing if there is an issue with DB or with Form Request)
    @Test
    public void testEnterFeedback()
    {
        Feedback feedback = new Feedback();
        feedback.id = new ObjectId();
        feedback.customerName = "TestCustomerName";
        feedback.customerEmail = "TestCustomerEmail";
        feedback.phoneNumber = "TestCustomerPhoneNumber";
        feedback.message = "TestMessage";

        Result result = new ApplicationFeedback(new MyEnvironment()).enterFeedback(feedback);
        assertEquals(OK, result.status());
        assertEquals("text/plain", result.contentType().toString());
        assertTrue(contentAsString(result).contains("Error - No appropriate database for Feedback exists."));
    }

    @Test
    public void testCallEnterFeedback()
    {
        Result result = route(
                controllers.routes.ApplicationFeedback.enterFeedback()
        );
        assertEquals(OK, result.status());
    }

    @Test
    public void testEnterFeedbackWithForm()
    {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Map<String, String> formData = new HashMap<String, String>();
                formData.put("customerName", "TestEnterCName");
                formData.put("customerEmail", "TestEnterCEmail");
                formData.put("phoneNumber", "TestEnterCPhoneNumber");
                formData.put("message", "TestEnterCMessage");

                Call call = controllers.routes.ApplicationFeedback.enterFeedback();
                Http.RequestBuilder request = fakeRequest(call).bodyForm(formData);
                Result result = route(request);
                assertEquals(OK, result.status());

            }
        });
    }

    @Test
    public void testCallEnterFeedbackWithForm()
    {
        Result result = route(
                controllers.routes.ApplicationFeedback.enterFeedback()
        );
        assertEquals(OK, result.status());
    }
}
