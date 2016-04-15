package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import play.libs.Json;
import uk.co.panaxiom.playjongo.PlayJongo;

public class Credential
{

    public static MongoCollection driverEmails() {
        return PlayJongo.getCollection("DriverEmail");
    }
    public static MongoCollection managerEmails() {
        return PlayJongo.getCollection("ManagerEmail");
    }

    @JsonProperty("_id")
    public ObjectId id;
    public String email;

    public static String managerType = "manager";
    public static String driverType = "driver";
    public static String notAuthType = "notAuth";

    public void insertDriverEmail() {driverEmails().save(this);}
    public void insertManagerEmail() {
        managerEmails().save(this);
    }

    public void removeDriverEmail() {
        driverEmails().remove(this.id);
    }
    public void removeManagerEmail() {
        managerEmails().remove(this.id);
    }

    public static Credential findByDriverEmail(String driverEmail) {
        return driverEmails().findOne("{email: #}", driverEmail).as(Credential.class);
    }
    public static Credential findByManagerEmail(String managerEmail) {
        return managerEmails().findOne("{email: #}", managerEmail).as(Credential.class);
    }

    public static ArrayNode findAllByDriverEmail()
    {
        Iterable<Credential> creds = driverEmails().find().as(Credential.class);

        ArrayNode res = (ArrayNode) Json.newArray();

        for (Credential cred : creds)
        {
            ObjectNode obj = Json.newObject();

            obj.put("email", cred.email);

            res.add(obj);
        }

        return res;
    }

    public static ArrayNode findAllByManagerEmail()
    {
        Iterable<Credential> creds = managerEmails().find().as(Credential.class);

        ArrayNode res = (ArrayNode) Json.newArray();

        for (Credential cred : creds)
        {
            ObjectNode obj = Json.newObject();

            obj.put("email", cred.email);

            res.add(obj);
        }

        return res;
    }
}