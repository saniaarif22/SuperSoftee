package models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.Application;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;
import play.libs.Json;

import java.util.ArrayList;

public class Order
{
    public static MongoCollection orders() { return PlayJongo.getCollection("Order");}
    public static MongoCollection approvedOrders() { return PlayJongo.getCollection("ApprovedOrder");}
    //for truck driver
    public static MongoCollection finishedOrders() { return PlayJongo.getCollection("FinishedOrder");}

    //yet to implement
    public static MongoCollection rejectedOrders() { return PlayJongo.getCollection("RejectedOrder");}

    @JsonProperty("_id")
    public ObjectId id;
    public String oName;
    public String oDate;
    public String oTime;
    public String oAddress;
    public String oPhoneNumber;
    public String oCoupon;

    public void insert() {
        orders().save(this);
    }

    public void insertToApproved() {
        orders().save(this);
    }

    public void insertToRejected() {
        approvedOrders().save(this);
    }

    public void insertApprovedOrders() {
        approvedOrders().save(this);
    }

    public void insertFinishedOrders() {
        finishedOrders().save(this);
    }

    public void remove() {
        orders().remove(this.id);
    }

    public void removeApprovedOrders() {
        approvedOrders().remove(this.id);
    }

    public void removeFinishedOrders() {
        finishedOrders().remove(this.id);
    }


    static public void remove(ObjectId id) {
        orders().remove(id);
    }

    static public void removeApprovedOrders(ObjectId id) {
        approvedOrders().remove(id);
    }

    static public void removeFinishedOrders(ObjectId id) {
        finishedOrders().remove(id);
    }


    public static Order findByOrderName(String oName) {
        return orders().findOne("{oName: #}", oName).as(Order.class);
    }

    public static Order findById(ObjectId oId) {
        return orders().findOne(oId).as(Order.class);
    }

    public static Order findByIdApproved(ObjectId oId) {
        return approvedOrders().findOne(oId).as(Order.class);
    }


    public static Order findByApprovedOrderName(String oName) {
        return approvedOrders().findOne("{oName: #}", oName).as(Order.class);
    }

    public static Order findByFinishedOrderName(String oName) {
        return finishedOrders().findOne("{oName: #}", oName).as(Order.class);
    }

    public static ArrayNode findAll() {
        Iterable<Order> orders = orders().find().as(Order.class);

        ArrayNode res = (ArrayNode) Json.newArray();

        for (Order order : orders)
        {
            res.add(order.toJson());
        }

        return res;
    }

    public static ArrayNode findAllApprovedOrders() {
        Iterable<Order> orders = approvedOrders().find().as(Order.class);

        ArrayNode res = (ArrayNode) Json.newArray();

        for (Order order : orders)
        {
            res.add(order.toJson());
        }

        return res;
    }

    public static ArrayNode findAllFinishedOrders() {
        Iterable<Order> orders = finishedOrders().find().as(Order.class);

        ArrayNode res = (ArrayNode) Json.newArray();

        for (Order order : orders)
        {
            res.add(order.toJson());
        }

        return res;
    }

    @Override
    public String toString()
    {
        return "oName: " + oName + "\n" +
                "oDate: "  + oDate + "\n" +
                "oTime: "  + oTime + "\n" +
                "oAddress: "  + oAddress + "\n"+
                "oPhoneNumber: "  + oPhoneNumber + "\n"+
                "oCoupon: "  + oCoupon + "\n";
    }

    public ObjectNode toJson()
    {
        ObjectNode obj = Json.newObject();

        obj.put("oId", id.toString());
        obj.put("oName", oName);
        obj.put("oDate", oDate);
        obj.put("oTime", oTime);
        obj.put("oAddress", oAddress);
        obj.put("oPhoneNumber", oPhoneNumber);
        obj.put("oCoupon", oCoupon);

        return obj;
    }
}