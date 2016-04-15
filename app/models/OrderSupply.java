package models;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;
import play.libs.Json;

public class OrderSupply
{
    public static MongoCollection ordersupplys() {
        return PlayJongo.getCollection("OrderSupply");
    }

    @JsonProperty("_id")


    public ObjectId id;
    // MondayEdits : We're storing counts, so they should be Integers
    // not strings so it's easier to perform summation on them.
    public String driverName;
    public Date orderDate;
    public Integer kidCone;
    public Integer smallCones;
    public Integer doubleCone;
    public Integer waffleCones;
    public Integer cartweels;
    public Integer simpleSyrup;
    public Integer chocolateSyrup;
    public Integer vanillaSyrup;
    public Integer strawberryShakeBase;
    public Integer wetWalnuts;
    public Integer wholeCherries;
    public Integer toppingStrawberry;
    public Integer toppingPineapple;
    public Integer toppingButterscotch;
    public Integer toppingCruchedCherry;
    public Integer smallDixieCups;
    public Integer largeDixieCups;
    public Integer ozCups;
    public Integer milkShakeCups;
    public Integer sundaeCups;
    public Integer bananaBoats;
    public Integer lids;
    public Integer spoons;
    public Integer straws;
    public Integer chocolateJimmies;
    public Integer rainbowJimmies;
    public Integer vanillaMix;
    public Integer fatVanillaMix;
    public Integer bananasBunch;
    public Integer napkins;
    public Integer milk;
    public Integer rwbBombPop;
    public Integer jollyRancherBombPop;
    public Integer jollyRancherSnowcone;
    public Integer screwballCherry;
    public Integer faceBratz;
    public Integer faceSonic;
    public Integer minion;
    public String message;

    public void insert() {
        ordersupplys().save(this);
    }

    public void remove() {
        ordersupplys().remove(this.id);
    }

    public static OrderSupply findByOrderName(String kidCone) {
        return ordersupplys().findOne("{kidCone: #}", kidCone).as(OrderSupply.class);
    }

    public static OrderSupply findById(String id) {
        return ordersupplys().findOne("{id: #}", id).as(OrderSupply.class);
    }

    public static ArrayNode findAll() {
        Iterable<OrderSupply> ordersupplys = ordersupplys().find().as(OrderSupply.class);

        ArrayNode res = (ArrayNode) Json.newArray();

        for (OrderSupply orderSupply : ordersupplys)
        {
            res.add(orderSupply.toJson());
        }

        return res;
    }

    @Override
    public String toString()
    {
        return "driverName" + driverName+ "\n"+
                "orderDate" + ((orderDate != null) ? orderDate.toString() : "") + "\n" +
                "kidCone: " + kidCone+ "\n"+
                "smallCones: "  + smallCones+ "\n"+
                "doubleCone: "  + doubleCone+ "\n"+
                "waffleCones: "  + waffleCones + "\n"+
                "cartweels: "  + cartweels + "\n"+
                "simpleSyrup: "  + simpleSyrup+ "\n"+
                "chocolateSyrup:" + chocolateSyrup+ "\n"+
                "vanillaSyrup:" + vanillaSyrup+ "\n"+
                "strawberryShakeBase:" + strawberryShakeBase+ "\n"+
                "wetWalnuts:" + wetWalnuts+ "\n"+
                "wholeCherries:" + wholeCherries+ "\n"+
                "toppingStrawberry:" + toppingStrawberry+ "\n"+
                "toppingPineapple:" + toppingPineapple+ "\n"+
                "toppingButterscotch:" + toppingButterscotch+ "\n"+
                "toppingCruchedCherry:" + toppingCruchedCherry+ "\n"+
                "smallDixieCups:" + smallDixieCups+ "\n"+
                "largeDixieCups:" + largeDixieCups+ "\n"+
                "ozCups:" + ozCups+ "\n"+
                "milkShakeCups:" + milkShakeCups+ "\n"+
                "sundaeCups:" + sundaeCups+ "\n"+
                "bananaBoats:" + bananaBoats+ "\n" +
                "lids:" + lids+ "\n"+
                "spoons:" +spoons+ "\n"+
                "straws:" + straws+ "\n"+
                "chocolateJimmies:" + chocolateJimmies+ "\n"+
                "rainbowJimmies:" + rainbowJimmies+ "\n"+
                "vanillaMix:" + vanillaMix+ "\n"+
                "fatVanillaMix:" + fatVanillaMix+ "\n"+
                "bananasBunch:" + bananasBunch+ "\n"+
                "napkins:" + napkins+ "\n"+
                "milk:" + milk+ "\n"+
                "rwbBombPop:" + rwbBombPop+ "\n"+
                "jollyRancherBombPop\":" + jollyRancherBombPop+ "\n"+
                "jollyRancherSnowcone:" + jollyRancherSnowcone+ "\n"+
                "screwballCherry:" + screwballCherry+ "\n"+
                "faceBratz:" + faceBratz+ "\n"+
                "faceSonic:" + faceSonic+ "\n"+
                "minion:" + minion + "\n" +
                "message:" + message + "\n";
    }

    public ObjectNode toJson()
    {
        ObjectNode obj = Json.newObject();

        obj.put("driverName", driverName);
        obj.put("orderDate",(orderDate != null) ? orderDate.toString() : null);
        obj.put("kidCone", kidCone);
        obj.put("smallCones", smallCones);
        obj.put("doubleCone", doubleCone);
        obj.put("waffleCones", waffleCones);
        obj.put("cartweels", cartweels);
        obj.put("simpleSyrup", simpleSyrup);
        obj.put("chocolateSyrup", chocolateSyrup );
        obj.put("vanillaSyrup", vanillaSyrup);
        obj.put("strawberryShakeBase", strawberryShakeBase);
        obj.put("wetWalnuts", wetWalnuts);
        obj.put("wholeCherries", wholeCherries);
        obj.put("toppingStrawberry", toppingStrawberry);
        obj.put("toppingPineapple", toppingPineapple);
        obj.put("toppingButterscotch", toppingButterscotch);
        obj.put("toppingCruchedCherry", toppingCruchedCherry);
        obj.put("smallDixieCups", smallDixieCups);
        obj.put("largeDixieCups", largeDixieCups);
        obj.put("ozCups", ozCups);
        obj.put("milkShakeCups", milkShakeCups);
        obj.put("sundaeCups", sundaeCups);
        obj.put("bananaBoats", bananaBoats);
        obj.put("lids",lids );
        obj.put("spoons", spoons);
        obj.put("straws", straws);
        obj.put("chocolateJimmies", chocolateJimmies);
        obj.put("rainbowJimmies", rainbowJimmies);
        obj.put("vanillaMix", vanillaMix);
        obj.put("fatVanillaMix", fatVanillaMix);
        obj.put("bananasBunch", bananasBunch);
        obj.put("napkins", napkins);
        obj.put("milk", milk);
        obj.put("rwbBombPop", rwbBombPop);
        obj.put("jollyRancherBombPop", jollyRancherBombPop);
        obj.put("jollyRancherSnowcone", jollyRancherSnowcone);
        obj.put("screwballCherry", screwballCherry);
        obj.put("faceBratz", faceBratz);
        obj.put("faceSonic", faceSonic);
        obj.put("minion", minion);
        obj.put("message", message);

        return obj;
    }
}

