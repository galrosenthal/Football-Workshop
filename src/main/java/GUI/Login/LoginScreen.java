package GUI.Login;

import Domain.EntityManager;
import GUI.FootballMain;
import Service.MainController;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;

/**
 * UI content when the user is not logged in yet.
 */
@Route("Login")
@PageTitle("Login")
@CssImport("./styles/shared-styles.css")
public class LoginScreen extends FlexLayout {


    public LoginScreen() {
        buildUI();
    }

    private void buildUI() {
        setSizeFull();
        setClassName("login-screen");


        // login form, centered in the available part of the screen
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(this::login);
        loginForm.addForgotPasswordListener(
                event -> Notification.show("Sorry We cannot help you"));

        //SignUp button
        Button signup = new Button("Sign Up Here");
        signup.addClickListener(event -> {
            getUI().get().navigate("Registration");
        });

        //Test Field
        Label notReg = new Label("Not a user yet?");

        VerticalLayout loginAndSignup = new VerticalLayout();
        loginAndSignup.add(loginForm);
        loginAndSignup.add(notReg);
        loginAndSignup.add(signup);
        loginAndSignup.setJustifyContentMode(JustifyContentMode.CENTER);
        loginAndSignup.setAlignItems(Alignment.CENTER);


        // layout to center login form when there is sufficient screen space
        FlexLayout centeringLayout = new FlexLayout();
        centeringLayout.setSizeFull();
//        centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
//        centeringLayout.setAlignItems(Alignment.CENTER);
        centeringLayout.add(loginAndSignup);


        // information text about logging in
        Component loginInformation = buildLoginInformation();



        add(loginInformation);
        add(centeringLayout);
    }

    private Component buildLoginInformation() {
        VerticalLayout loginInformation = new VerticalLayout();
        loginInformation.setClassName("login-information");

        H1 loginInfoHeader = new H1("Login Information");
        loginInfoHeader.setWidth("100%");
        Span loginInfoText = new Span(
                "Log in as \"admin\" to have full access. Log in with any " +
                        "other username to have read-only access. For all " +
                        "users, the password is same as the username.");
        loginInfoText.setWidth("100%");
        loginInformation.add(loginInfoHeader);
        loginInformation.add(loginInfoText);

        return loginInformation;
    }

    private void login(LoginForm.LoginEvent event) {
        if (MainController.login(event.getUsername(), event.getPassword())) {
            WrappedSession currentSession = VaadinService.getCurrentRequest().getWrappedSession();
            if(currentSession == null)
            {
                FootballMain.showNotification("Something went Wrong, Please try Again");
                return;
            }
            else{
                currentSession.setAttribute("username", event.getUsername());
                getUI().get().navigate("");
            }

        } else {
            event.getSource().setError(true);
        }
    }
}
