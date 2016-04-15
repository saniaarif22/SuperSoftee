//package controllers;
//
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.client.util.DateTime;
//
//// Stuff that we know is already in our project ( refer to Order.java ):
////import com.fasterxml.jackson.annotation.JsonProperty;
////import com.fasterxml.jackson.databind.node.ArrayNode;
////import com.fasterxml.jackson.databind.node.ObjectNode;
////import controllers.Application;
////import org.bson.types.ObjectId;
////import org.jongo.MongoCollection;
////import uk.co.panaxiom.playjongo.PlayJongo;
////import play.libs.Json;
//
//import com.google.api.services.calendar.CalendarScopes;
//import com.google.api.services.calendar.model.*;
//import com.google.api.services.calendar.Calendar;
//import models.Order;
//import play.Play;
//import play.mvc.Controller;
//import play.mvc.Result;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.Arrays;
//import java.util.List;
//
//public class ApplicationCalendar extends Controller
//{
//    /**
//     * Application name.
//     */
//    private static final String APPLICATION_NAME =
//            "SuperSoftee Calendar";
//
//    /**
//     * Directory to store user credentials for this application.
//     */
//    private static final java.io.File DATA_STORE_DIR = new java.io.File(
//            Play.application().path() + "/credentials/");
//
//    /**
//     * Global instance of the {@link FileDataStoreFactory}.
//     */
//    private static FileDataStoreFactory DATA_STORE_FACTORY;
//
//    /**
//     * Global instance of the JSON factory.
//     */
//    private static final JsonFactory JSON_FACTORY =
//            JacksonFactory.getDefaultInstance();
//
//    /**
//     * Global instance of the HTTP transport.
//     */
//    private static HttpTransport HTTP_TRANSPORT;
//
//    /**
//     * Global instance of the scopes required by this quickstart.
//     */
//    private static final List<String> SCOPES =
//            Arrays.asList(CalendarScopes.CALENDAR_READONLY);
//
//    static
//    {
//        try
//        {
//            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
//        } catch (Throwable t)
//        {
//            t.printStackTrace();
//            System.exit(1);
//        }
//    }
//
//    /**
//     * Creates an authorized Credential object.
//     *
//     * @return an authorized Credential object.
//     * @throws IOException
//     *
//     *Here is your client ID
//    515553871519-p5bs02otr3k1feh9c7khsf8h6bho53md.apps.googleusercontent.com
//
//    Here is your client secret
//    G88F04ZgRamKUChqmhd2nXpS
//     */
//    public static Credential authorize()  throws IOException
//    {
//        // Load client secrets.
//        InputStream in =
//                new FileInputStream(DATA_STORE_DIR.getAbsolutePath() + "/client_secret.json");
//        GoogleClientSecrets clientSecrets =
//                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//
//        // Build flow and trigger user authorization request.
//        GoogleAuthorizationCodeFlow flow =
//                new GoogleAuthorizationCodeFlow.Builder(
//                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                        .setDataStoreFactory(DATA_STORE_FACTORY)
//                        .setAccessType("offline")
//                        .build();
//        Credential credential = new AuthorizationCodeInstalledApp(
//                flow, new LocalServerReceiver()).authorize("user");
//        System.out.println(
//                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
//        return credential;
//    }
//
//    /**
//     * Build and return an authorized Calendar client service.
//     *
//     * @return an authorized Calendar client service
//     * @throws IOException
//     */
//    public static com.google.api.services.calendar.Calendar
//    getCalendarService() throws IOException
//    {
//        Credential credential = authorize();
//        return (com.google.api.services.calendar.Calendar)(new com.google.api.services.calendar.Calendar.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build());
//    }
//
//    public static void insertEvent() throws IOException
//    {
//        // Build a new authorized API client service.
//        // Note: Do not confuse this class with the
//        //   com.google.api.services.calendar.model.Calendar class.
//        com.google.api.services.calendar.Calendar service =
//                getCalendarService();
//
////        // List the next 10 events from the primary calendar.
////        DateTime now = new DateTime(System.currentTimeMillis());
////        Events events = service.events().list("primary")
////                .setMaxResults(10)
////                .setTimeMin(now)
////                .setOrderBy("startTime")
////                .setSingleEvents(true)
////                .execute();
////        List<Event> items = events.getItems();
////        if (items.size() == 0)
////        {
////            System.out.println("No upcoming events found.");
////        } else
////        {
////            System.out.println("Upcoming events");
////            for (Event event : items)
////            {
////                DateTime start = event.getStart().getDateTime();
////                if (start == null)
////                {
////                    start = event.getStart().getDate();
////                }
////                System.out.printf("%s (%s)\n", event.getSummary(), start);
////            }
////
////        }
//        Order test = Order.findByApprovedOrderName("Nik");
//        //Add an event
//        DateTime dt = new DateTime(test.oDate);
//        EventDateTime edt = new EventDateTime()
//                .setDateTime(dt)
//                .setTimeZone("America/New_York");
//
//        Event event = new Event()
//                .setSummary("Ice Cream Event")
//                .setLocation(test.oAddress)
//                .setStart(edt);
//
//        String calendarId = "m71tm57gfdi89l33hq5443a7o0@group.calendar.google.com";
//        event = service.events().insert(calendarId, event).execute();
//        System.out.print("Inserted event BOOYAH");
//        System.out.printf("Event created: %s\n", event.getHtmlLink());
//
//    }
//}
package controllers;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.DateTime;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayInputStream;

// Stuff that we know is already in our project ( refer to Order.java ):
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import controllers.Application;
//import org.bson.types.ObjectId;
//import org.jongo.MongoCollection;
//import uk.co.panaxiom.playjongo.PlayJongo;
//import play.libs.Json;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ApplicationCalendar {
    /** Application name. */
    private static final String APPLICATION_NAME =
            "Google Calendar API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart. */
    private static final List<String> SCOPES =
            Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        //InputStream in = ApplicationCalendar.class.getResourceAsStream("/client_secret.json");
        String googleSecretString = "{\"installed\":{\"client_id\":\"515553871519-p5bs02otr3k1feh9c7khsf8h6bho53md.apps.googleusercontent.com\",\"project_id\":\"helical-loop-116101\",\"auth_uri\":\"https://accounts.google.com/o/oauth2/auth\",\"token_uri\":\"https://accounts.google.com/o/oauth2/token\",\"auth_provider_x509_cert_url\":\"https://www.googleapis.com/oauth2/v1/certs\",\"client_secret\":\"G88F04ZgRamKUChqmhd2nXpS\",\"redirect_uris\":[\"urn:ietf:wg:oauth:2.0:oob\",\"http://localhost\"]}}";
        InputStream            in = new ByteArrayInputStream(googleSecretString.getBytes(StandardCharsets.UTF_8));

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static Calendar
    getCalendarService() throws IOException {
        Credential credential = authorize();

        System.err.println("credential " + credential );

        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
                getCalendarService();

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
    }

}