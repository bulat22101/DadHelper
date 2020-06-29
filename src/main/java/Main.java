import config.MainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.ui.UserInterface;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        UserInterface ui = context.getBean("graphicalUserInterface", UserInterface.class);
        ui.launch();
    }
}
