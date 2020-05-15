package GUI;


import GUI.About.AboutView;
import GUI.RoleRelatedViews.AssociationRepresentative.ARControls;
import GUI.RoleRelatedViews.Referee.RefereeControls;
import GUI.RoleRelatedViews.TeamOwner.TOControls;
import Service.MainController;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.ELState;

import java.util.Collection;
import java.util.List;


/**
 * The main layout. Contains the navigation menu.
 */
@Theme(value = Lumo.class)
@PWA(name = "Football", shortName = "Football", enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/menu-buttons.css", themeFor = "vaadin-button")
public class FootballMain extends AppLayout implements RouterLayout{

    private static boolean waitingForUI = false;
    private final Button logoutButton;
    private final Button loginBtn;
    private final Button signupBtn;

    private final static String USERNAME_ATTRIBUTE_NAME = "username";



    public FootballMain() {
// Header of the menu (the navbar)

        // menu toggle
        final DrawerToggle drawerToggle = new DrawerToggle();
        drawerToggle.addClassName("menu-toggle");
        addToNavbar(drawerToggle);

        // image, logo
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        top.setClassName("menu-header");

        // Note! Image resource url is resolved here as it is dependent on the
        // execution mode (development or production) and browser ES level
        // support
        final String resolvedImage = VaadinService.getCurrent().resolveResource("img/table-logo.png", VaadinSession.getCurrent().getBrowser());

        final Image image = new Image(resolvedImage, "");
        final Label title = new Label("Football Workshop");
        loginBtn = new Button("Login");
        loginBtn.addClickListener(e -> {
            getUI().get().navigate("Login");
        });
        signupBtn = new Button("Sign Up");
        signupBtn.addClickListener(e -> {
            getUI().get().navigate("Registration");
        });

        final HorizontalLayout buttons = new HorizontalLayout();
        buttons.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        buttons.setAlignItems(Alignment.END);
        buttons.add(loginBtn,signupBtn);
        buttons.setAlignSelf(Alignment.STRETCH);

        top.add(image, title);
        top.add(title);
        top.addAndExpand(buttons);

        addToNavbar(top);




//        addToDrawer(createMenuLink(RegistrationView.class, RegistrationView.VIEW_NAME,VaadinIcon.USER.create()));

        // Create logout button but don't add it yet; admin view might be added
        // in between (see #onAttach())
        logoutButton = createMenuButton("Logout", VaadinIcon.SIGN_OUT.create());
        logoutButton.addClickListener(e -> logout());
        logoutButton.getElement().setAttribute("title", "Logout (Ctrl+L)");
    }

    private void createNavItems() {
        WrappedSession userSession = VaadinService.getCurrentRequest().getWrappedSession();
        if(userSession == null || userSession.getAttribute(USERNAME_ATTRIBUTE_NAME) == null  )
        {
            System.out.println("No Session acquired");
            return;
        }

        List<String> userRoles = MainController.getUserRoles(userSession.getAttribute(USERNAME_ATTRIBUTE_NAME).toString());
        addToDrawer(createMenuLink(AboutView.class, AboutView.VIEW_NAME,
                VaadinIcon.INFO_CIRCLE.create()));

        if(userRoles == null || userRoles.isEmpty())
        {
            return;
        }

        if(userRoles.contains("SYSTEM_ADMIN"))
        {
            addToDrawer(createMenuLink(ModifyUsers.class, ModifyUsers.VIEW_NAME,
                VaadinIcon.EDIT.create()));

        }

        if(userRoles.contains("ASSOCIATION_REPRESENTATIVE"))
        {
            addToDrawer(createMenuLink(ARControls.class, ARControls.VIEW_NAME,
                    VaadinIcon.USER.create()));
        }

        if(userRoles.contains("TEAM_OWNER"))
        {
            addToDrawer(createMenuLink(TOControls.class, TOControls.VIEW_NAME,
                    VaadinIcon.USER.create()));
        }

        if(userRoles.contains("REFEREE")){
            addToDrawer(createMenuLink(RefereeControls.class, RefereeControls.VIEW_NAME,
                    VaadinIcon.USER.create()));
        }

    }

    private void logout() {
        WrappedSession userSession = VaadinService.getCurrentRequest().getWrappedSession();
        userSession.removeAttribute(USERNAME_ATTRIBUTE_NAME);
        getUI().get().navigate("");
        getUI().get().getPage().reload();

    }

    private RouterLink createMenuLink(Class<? extends Component> viewClass,
                                      String caption, Icon icon) {
        final RouterLink routerLink = new RouterLink(null, viewClass);
        routerLink.setClassName("menu-link");
        routerLink.add(icon);
        routerLink.add(new Span(caption));
        icon.setSize("24px");
        return routerLink;
    }

    private Button createMenuButton(String caption, Icon icon) {
        final Button routerButton = new Button(caption);
        routerButton.setClassName("menu-button");
        routerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        routerButton.setIcon(icon);
        icon.setSize("24px");
        return routerButton;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        WrappedSession userSession = VaadinService.getCurrentRequest().getWrappedSession();
        if(userSession.getAttribute(USERNAME_ATTRIBUTE_NAME) != null  )
        {
            loginBtn.setVisible(false);
            signupBtn.setVisible(false);
        }

        // Navigation items
        createNavItems();

        // User can quickly activate logout with Ctrl+L
        attachEvent.getUI().addShortcutListener(this::logout, Key.KEY_L,
                KeyModifier.CONTROL);


        // Finally, add logout button for all users
        addToDrawer(logoutButton);
    }

    /**
     * Popup notification with a message msg in the top center of the screen
     * @param msg the message to display
     */
    public static void showNotification(String msg)
    {
        if(msg != null)
        {
            Notification notification = new Notification(msg,2500, Notification.Position.TOP_CENTER);
            if(msg.toLowerCase().contains("success"))
            {
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }
            else if(msg.toLowerCase().contains("fail") ||
                    msg.toLowerCase().contains("error") ||
                    msg.toLowerCase().contains("wrong") ||
                    msg.toLowerCase().contains("incorrect") ||
                    msg.toLowerCase().contains("could not") ||
                    msg.toLowerCase().contains("already exists") ||
                    msg.toLowerCase().contains("there is no") ||
                    msg.toLowerCase().contains("there was no") ||
                    msg.toLowerCase().contains("there are no"))
            {
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            notification.open();
        }
    }


    /**
     * This function is poping up a dialog window to the user,
     * asking is to do something in a <i>msg</i> and setting the user response
     * into the <i>returnedValue</i> variable.
     * The function also receives a type to use with (string/int) and shows a textField or ComboBox respectivly
     * Also the function should receive the Thread that started it <i>calledThread</i>
     * and if there are any values that the user should choose from (in case of type int) this function can
     * get a Collection<String> displayValues
     * @param msg the message to show the user
     * @param receiveType the type of the calling function (string/int)
     * @param returnedValue the StringBuilder object to fill in the response from the user
     * @param callingThread the Thread that called this function
     * @param displayValues the Values to show the user, used as an Array of collection.
     */
    public static void showDialog(String msg, String receiveType,StringBuilder returnedValue, Thread callingThread ,Collection<String>... displayValues)
    {


        Dialog newWindow = new Dialog();
        newWindow.setCloseOnOutsideClick(false);
        newWindow.setCloseOnEsc(false);
        newWindow.setVisible(true);
        VerticalLayout vl = new VerticalLayout();
        newWindow.setCloseOnEsc(false);

        TextField tf = new TextField();
        ComboBox<String> valuesForInt = new ComboBox<>();
        MultiSelectListBox<String> valuesForString = new MultiSelectListBox<>();

        Button close = new Button("Submit");
        close.setEnabled(false);
        close.addClickListener(e -> {
            waitingForUI = false;
            if(receiveType.equals("string"))
            {
                if(displayValues.length <= 0)
                {
                    returnedValue.append(tf.getValue());

                }
                else
                {
                    String resultDelimeter = ";";
                    StringBuilder valuesSelected = new StringBuilder();
                    for (String value :
                            valuesForString.getSelectedItems()) {
                        valuesSelected.append(resultDelimeter).append(value);
                    }
                    returnedValue.append(valuesSelected);
                }
            }
            else if(receiveType.equals("int"))
            {
                int value = 0, index = 0;
                for (String listValue :
                        displayValues[0]) {
                    if(valuesForInt.getValue().equals(listValue))
                    {
                        value = index;
                    }
                    index++;
                }
                returnedValue.append(value);
            }
            newWindow.close();
            callingThread.interrupt();
//            UI.setCurrent(lastUI);
        });

        Label lbl = new Label(msg);
        vl.add(lbl);
        if(receiveType.equals("string"))
        {
            if(displayValues.length <= 0) {
                vl.add(tf);
                tf.setValueChangeMode(ValueChangeMode.EAGER);
                tf.addValueChangeListener(e -> {
                    if (!e.getValue().isEmpty()) {
                        close.setEnabled(true);
                    } else {
                        close.setEnabled(false);
                    }
                });
            }
            else
            {
                vl.add(valuesForString);
                valuesForString.setItems(displayValues[0]);


            }

        }
        else if(receiveType.equals("int"))
        {
            if(displayValues.length > 0) {
                valuesForInt.setItems(displayValues[0]);
                valuesForInt.setClearButtonVisible(true);
                vl.add(valuesForInt);
                valuesForInt.addValueChangeListener(e -> {
                    if(!e.getValue().isEmpty())
                    {
                        close.setEnabled(true);
                    }
                    else
                    {
                        close.setEnabled(false);
                    }
                });

            }

        }



        vl.add(close);
        newWindow.add(vl);
        newWindow.open();

    }

}





