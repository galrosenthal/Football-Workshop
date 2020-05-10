package GUI;


import GUI.About.AboutView;
import GUI.RoleRelatedViews.AssociationRepresentative.ARControls;
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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

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
        attachEvent.getUI().addShortcutListener(() -> logout(), Key.KEY_L,
                KeyModifier.CONTROL);

//        // add the admin view menu item if user has admin role
//        final AccessControl accessControl = AccessControlFactory.getInstance()
//                .createAccessControl();
//        if (accessControl.isUserInRole(AccessControl.ADMIN_ROLE_NAME)) {
//
//            // Create extra navigation target for admins
//            registerAdminViewIfApplicable(accessControl);
//
//            // The link can only be created now, because the RouterLink checks
//            // that the target is valid.
//            addToDrawer(createMenuLink(AdminView.class, AdminView.VIEW_NAME,
//                    VaadinIcon.DOCTOR.create()));
//        }

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
                    msg.toLowerCase().contains("incorrect"))
            {
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            notification.open();
        }
    }

    public static void popupWindow(String msg, String receiveType,StringBuilder returnedValue ,Collection<String>... displayValues)
    {

        Dialog newWindow = new Dialog();
        VerticalLayout vl = new VerticalLayout();
        newWindow.setCloseOnOutsideClick(false);
        newWindow.setCloseOnEsc(false);

        ComboBox<String> values = new ComboBox<>();
        Button close = new Button("Submit");
        close.addClickListener(e -> {

            newWindow.close();
        });

        Label lbl = new Label(msg);
        vl.add(lbl);
        if(receiveType.equals("string"))
        {
            TextField tf = new TextField();
            vl.add(tf);
            tf.addValueChangeListener(e->{
                setReturnedValue(returnedValue, e.getValue());
            });

        }
        else if(receiveType.equals("int"))
        {
            if(displayValues.length > 0) {
                values.setItems(displayValues[0]);
                values.setClearButtonVisible(true);
                vl.add(values);
                values.addValueChangeListener(e-> {
                    setReturnedValue(returnedValue, e.getValue());
                });
            }
        }



        vl.add(close);
        newWindow.add(vl);
        newWindow.open();



    }

    private static void setReturnedValue(StringBuilder returnedValue, String valueToSet) {
        returnedValue = new StringBuilder(valueToSet);

    }
}





