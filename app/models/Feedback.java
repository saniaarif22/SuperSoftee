package models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.Application;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;
import play.libs.Json;

public class Feedback {

    public static MongoCollection feedbacks() {
        return PlayJongo.getCollection("Feedback");
    }

    @JsonProperty("_id")
    public ObjectId id;
    public String customerName;
    public String customerEmail;
    public String phoneNumber;
    public String message;

    public void insert() {
        feedbacks().save(this);
    }

    public void remove() {
        feedbacks().remove(this.id);
    }

    public static Feedback findByCustomerName(String customerName) {
        return feedbacks().findOne("{customerName: #}", customerName).as(Feedback.class);
    }

    public static ArrayNode findAll() {
        Iterable<Feedback> feedbacks = feedbacks().find().as(Feedback.class);

        ArrayNode res = (ArrayNode) Json.newArray();

        for (Feedback feedback : feedbacks)
        {
            res.add(feedback.toJson());
        }

        return res;
    }

    @Override
    public String toString()
    {
        return "customerName: " + customerName + "\n" +
                "customerEmail: "  + customerEmail + "\n" +
                "phoneNumber: "  + phoneNumber + "\n" +
                "message: "  + message + "\n";
    }

    public ObjectNode toJson()
    {
        ObjectNode obj = Json.newObject();

        obj.put("customerName",customerName);
        obj.put("customerEmail", customerEmail);
        obj.put("phoneNumber", phoneNumber);
        obj.put("message", message);

        return obj;
    }

}