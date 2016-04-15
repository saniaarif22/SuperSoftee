package models;

import com.mongodb.*;
import play.Configuration;
import uk.co.panaxiom.playjongo.*;

import java.util.Arrays;

public class MyMongoClientFactory extends MongoClientFactory {
    public MongoClient mongoClient;

    private Configuration config = this.config;
    public MyMongoClientFactory(Configuration config) {
        super(config);
        this.config = config;
        try {
            mongoClient = this.createClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MongoClient createClient() throws Exception {
        MongoClientOptions options = MongoClientOptions.builder()
                .connectionsPerHost(100)
                .maxConnectionIdleTime(60000)
                .build();

        return new MongoClient(Arrays.asList(
                new ServerAddress("localhost", 27017)),
                options);
    }

    public String getDBName() {
        return config.getString("myappconfig.dbname");
    }

}
