
import play.GlobalSettings;
import securesocial.core.RuntimeEnvironment;
import service.MyEnvironment;

public class Global extends GlobalSettings {
    private RuntimeEnvironment env = new MyEnvironment();

}
