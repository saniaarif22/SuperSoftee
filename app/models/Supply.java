package models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import uk.co.panaxiom.playjongo.PlayJongo;
import play.libs.Json;

public class Supply
{
    public static MongoCollection ordersupplys() {
        return PlayJongo.getCollection("OrderSupply");
    }

    @JsonProperty("_id")


    public ObjectId id;
    public String kidCone;
    public String smallCones;
    public String doubleCone;
    public String waffleCones;
    public String cartweels;
    public String simpleSyrup;
    public String chocolateSyrup;
    public String vanillaSyrup;
    public String strawberryShakeBase;
    public String wetWalnuts;
    public String wholeCherries;
    public String toppingStrawberry;
    public String toppingPineapple;
    public String toppingButterscotch;
    public String toppingCruchedCherry;
    public String smallDixieCups;
    public String largeDixieCups;
    public String ozCups;
    public String milkShakeCups;
    public String sundaeCups;
    public String bananaBoats;
    public String lids;
    public String spoons;
    public String straws;
    public String chocolateJimmies;
    public String rainbowJimmies;
    public String vanillaMix;
    public String fatVanillaMix;
    public String bananasBunch;
    public String napkins;
    public String milk;
    public String rwbBombPop;
    public String jollyRancherBombPop;
    public String jollyRancherSnowcone;
    public String screwballCherry;
    public String faceBratz;
    public String faceSonic;
    public String minion;

    public void insert() {
        ordersupplys().save(this);
    }

    public void remove() {
        ordersupplys().remove(this.id);
    }

    public static Supply findByOrderName(String kidCone) {
        return ordersupplys().findOne("{kidCone: #}", kidCone).as(Supply.class);
    }

    public static Supply findById(String id) {
        return ordersupplys().findOne("{id: #}", id).as(Supply.class);
    }

    public static ArrayNode findAll() {
        Iterable<Supply> ordersupplys = ordersupplys().find().as(Supply.class);

        ArrayNode res = (ArrayNode) Json.newArray();

        for (Supply orderSupply : ordersupplys)
        {
            res.add(orderSupply.toJson());
        }

        return res;
    }

    @Override
    public String toString()
    {
        return "kidCone: " + kidCone + "\n" +
                "smallCones: "  + smallCones + "\n" +
                "doubleCone: "  + doubleCone + "\n" +
                "waffleCones: "  + waffleCones + "\n"+
                "cartweels: "  + cartweels + "\n"+
                "simpleSyrup: "  + simpleSyrup + "\n" +
                "chocolateSyrup:" + chocolateSyrup + "\n" +
                "vanillaSyrup:" + vanillaSyrup + "\n" +
                "strawberryShakeBase:" + strawberryShakeBase + "\n" +
                "wetWalnuts:" + wetWalnuts + "\n" +
                "wholeCherries:" + wholeCherries + "\n" +
                "toppingStrawberry:" + toppingStrawberry + "\n" +
                "toppingPineapple:" + toppingPineapple + "\n" +
                "toppingButterscotch:" + toppingButterscotch + "\n" +
                "toppingCruchedCherry:" + toppingCruchedCherry + "\n" +
                "smallDixieCups:" + smallDixieCups + "\n" +
                "largeDixieCups:" + largeDixieCups + "\n" +
                "ozCups:" + ozCups + "\n" +
                "milkShakeCups:" + milkShakeCups + "\n" +
                "sundaeCups:" + sundaeCups + "\n" +
                "bananaBoats:" + bananaBoats+ "\n" +
                "lids:" + lids + "\n" +
                "spoons:" +spoons + "\n" +
                "straws:" + straws + "\n" +
                "chocolateJimmies:" + chocolateJimmies + "\n" +
                "rainbowJimmies:" + rainbowJimmies + "\n" +
                "vanillaMix:" + vanillaMix + "\n" +
                "fatVanillaMix:" + fatVanillaMix + "\n" +
                "bananasBunch:" + bananasBunch + "\n" +
                "napkins:" + napkins + "\n" +
                "milk:" + milk + "\n" +
                "rwbBombPop:" + rwbBombPop + "\n" +
                "jollyRancherBombPop\":" + jollyRancherBombPop + "\n" +
                "jollyRancherSnowcone:" + jollyRancherSnowcone + "\n" +
                "screwballCherry:" + screwballCherry + "\n" +
                "faceBratz:" + faceBratz + "\n" +
                "faceSonic:" + faceSonic + "\n" +
                "minion:" + minion + "\n" ;


    }

    public ObjectNode toJson()
    {
        ObjectNode obj = Json.newObject();

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

        return obj;
    }
}

