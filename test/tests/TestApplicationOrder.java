package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import controllers.Application;

import controllers.ApplicationOrder;
import controllers.*;
import models.Order;
import org.bson.types.ObjectId;
import org.junit.Test;

import play.data.Form;
import play.mvc.Call;
import play.mvc.Http;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import play.test.WithApplication;
import play.twirl.api.Content;
import service.MyEnvironment;
import service.User;

public class TestApplicationOrder extends WithApplication
{
//    @Override
//    protected Application provideApplication() {
//        Application application = new GuiceApplicationBuilder()
//                .configure("db.default.driver", "org.h2.Driver")
//                .configure("db.default.url", "jdbc:h2:mem:play")
//                .configure("play.http.router", "my.test.Routes")
//                .build();
//        return application;
//    }
//    @Override
//    protected FakeApplication provideFakeApplication()
//    {
//        return new FakeApplication(new java.io.File("."), Helpers.class.getClassLoader(),
//                ImmutableMap.of("play.http.router", "play.tests.Routes"), new ArrayList<String>(), null);
//    }

    @Test
    public void testApproveOrder() {
        ObjectId testOId = new ObjectId();
        Result result = new ApplicationOrder(new MyEnvironment()).approveOrder(testOId.toString());
        assertEquals(OK, result.status());
        assertEquals("text/plain", result.contentType().toString());
        assertTrue(contentAsString(result).contains("Error - order not in the database."));
    }

    //Testing the approve order function in addition to the routing functionality i.e. the function approveorder in addition to the call to appr
    @Test
    public void testCallApproveOrder()
    {
        ObjectId testOId = new ObjectId();
        Result result = route(
                controllers.routes.ApplicationOrder.approveOrder(testOId.toString())
        );
        assertEquals(OK, result.status());
    }

    @Test
    public void testFinishOrder()
    {
        // Testing the failure cases
        ObjectId testOId = new ObjectId();
        Result result1 = new ApplicationOrder(new MyEnvironment()).finishOrder(testOId.toString());
        assertEquals(OK, result1.status());
        assertEquals("text/plain", result1.contentType().toString());
        assertTrue(contentAsString(result1).contains("Error - order not in the database."));

        // testing the successful cases
        Order finishOrder = Order.orders().findOne().as(Order.class);
        testOId = finishOrder.id;

        Result result2 = new ApplicationOrder(new MyEnvironment()).finishOrder(testOId.toString());
        assertEquals(OK, result2.status());
        assertEquals("text/plain", result2.contentType().toString());
        assertTrue(contentAsString(result2).equals(""));
    }


    @Test
    public void testGetOrder()
    {
        Result result = new ApplicationOrder(new MyEnvironment()).getOrders();
        assertEquals(OK, result.status());
        assertEquals("text/plain", result.contentType().toString());
        assertTrue(contentAsString(result).contains("Error - No orders exist in the database."));
    }

    @Test
    public void testCallGetOrder()
    {
        Result result = route(
                controllers.routes.ApplicationOrder.getOrders()
        );
        assertEquals(OK, result.status());
    }

    @Test
    public void testGetFinishedOrder()
    {
        Result result = new ApplicationOrder(new MyEnvironment()).getFinishedOrders();
        assertEquals(OK, result.status());
        assertEquals("text/plain", result.contentType().toString());
        assertTrue(contentAsString(result).contains("Error - No finished orders exist in the database."));
    }

    @Test
    public void testCallGetFinishedOrder()
    {
        Result result = route(
                controllers.routes.ApplicationOrder.getFinishedOrders()
        );
        assertEquals(OK, result.status());
    }

    //Testing if Test Order data is input into the database (Unit test to see where the application breaks in case
    // the testEnterOrderWithForm test fails (i.e. knowing if there is an issue with DB or with Form Request)
    @Test
    public void testEnterOrder()
    {
        Order order = new Order();
        order.id = new ObjectId();
        order.oName = "TestName";
        order.oDate = "TestDate";
        order.oTime = "TestTime";
        order.oAddress = "TestAddress";
        order.oPhoneNumber = "TestPhoneNumber";
        order.oCoupon = "TestCoupon";

        Result result = new ApplicationOrder(new MyEnvironment()).enterOrder(order);
        assertEquals(OK, result.status());
        assertEquals("text/plain", result.contentType().toString());
        assertTrue(contentAsString(result).contains("Error - No appropriate database exists."));
    }

    @Test
    public void testCallEnterOrder()
    {
        Result result = route(
                controllers.routes.ApplicationOrder.enterOrder()
        );
        assertEquals(OK, result.status());
    }

    @Test
    public void testEnterOrderWithForm()
    {
        running(fakeApplication(), new Runnable() {
            public void run() {
                int originalSize = Order.findAll().size();

                Map<String, String> formData = new HashMap<String, String>();
                formData.put("oName", "TestEnterName");
                formData.put("oDate", "TestEnterdate");
                formData.put("oTime", "TestEnterTime");
                formData.put("oAddress", "TestEnterAddress");
                formData.put("oPhoneNumber", "84903502");
                formData.put("oCoupon", "TestEnterCoupon");

                Call call = controllers.routes.ApplicationOrder.enterOrder();
                Http.RequestBuilder request = fakeRequest(call).bodyForm(formData);
                Result result = route(request);

                int newSize = Order.findAll().size();

                assertEquals(originalSize + 1, newSize);
            }
        });
    }

    @Test
    public void testCallEnterOrderWithForm()
    {
        Result result = route(
                controllers.routes.ApplicationOrder.enterOrder()
        );
        assertEquals(OK, result.status());
    }

}