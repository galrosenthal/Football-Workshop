package GUI;


import GUI.About.AboutView;
import GUI.RoleRelatedViews.AssociationRepresentative.ARControls;
import GUI.RoleRelatedViews.Referee.RefereeControls;
import GUI.RoleRelatedViews.TeamOwner.TOControls;
import Service.MainController;
import Service.UIController;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
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
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.Collection;
import java.util.List;


/**
 * The main layout. Contains the navigation menu.
 */
@Theme(value = Lumo.class)
@Push
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
        top.setWidth("100%");
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

        HorizontalLayout left = new HorizontalLayout();
        left.setWidth("80%");
        left.add(image,title);



        final HorizontalLayout buttons = new HorizontalLayout();
//        buttons.setWidth("100%");
        buttons.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        buttons.setAlignItems(Alignment.END);
        buttons.add(loginBtn,signupBtn);
//        buttons.setAlignSelf(Alignment.STRETCH);

        HorizontalLayout right = new HorizontalLayout();
        right.setAlignItems(Alignment.END);
        right.add(buttons);

        top.addAndExpand(left,right);

        addToNavbar(top);




//        addToDrawer(createMenuLink(RegistrationView.class, RegistrationView.VIEW_NAME,VaadinIcon.USER.create()));

        // Create logout button but don't add it yet; admin view might be added
        // in between (see #onAttach())
        logoutButton = createMenuButton("Logout", VaadinIcon.SIGN_OUT.create());
        logoutButton.addClickListener(e -> logout());
        logoutButton.getElement().setAttribute("title", "Logout (Ctrl+L)");
    }

    public static void showAlert(String message,UI seUI) {
        seUI.access(() -> {
            Dialog alertWindow = new Dialog();
            VerticalLayout alertView = new VerticalLayout();
            Label msg = new Label(message);
            Button close = new Button("Close");
            close.addClickListener(e -> {
                alertWindow.close();
            });
            alertView.add(msg,close);

            alertWindow.add(alertView);
            alertWindow.setCloseOnOutsideClick(false);
//            UI.getCurrent().access(alertWindow::open);
            alertWindow.open();
            seUI.push();
        });
    }


    private void createNavItems() {
        VaadinSession userSession = VaadinSession.getCurrent();
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
        VaadinSession userSession = VaadinSession.getCurrent();
        MainController.logout((String)userSession.getAttribute(USERNAME_ATTRIBUTE_NAME));
        userSession.setAttribute(USERNAME_ATTRIBUTE_NAME, null);
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

        VaadinSession userSession = VaadinSession.getCurrent();
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
    public static void showDialog(UI lastUI, String msg, String receiveType,StringBuilder returnedValue, Thread callingThread ,Collection<String>... displayValues)
    {

        lastUI.access(() -> {
            Dialog newWindow = new Dialog();
            newWindow.setCloseOnOutsideClick(false);
            newWindow.setCloseOnEsc(false);
            newWindow.setVisible(true);
            VerticalLayout vl = new VerticalLayout();
            newWindow.setCloseOnEsc(false);
            int numOfInputs = msg.split(UIController.STRING_DELIMETER).length;
            TextField[] textFieldsArray = new TextField[numOfInputs];
            ComboBox<String>[] multiInputsFromList = new ComboBox[numOfInputs];

            ComboBox<String> valuesForInt = new ComboBox<>();
            valuesForInt.setWidth("100%");
            MultiSelectListBox<String> valuesForString = new MultiSelectListBox<>();

            DatePicker picker = new DatePicker();


            Button submit = new Button("Submit");
            Button cancel = new Button("Cancel");

            cancel.addClickListener(e -> {
                newWindow.close();
                returnedValue.append(UIController.CANCEL_TASK_VALUE);
                callingThread.interrupt();
            });

            submit.setEnabled(false);
            submit.addClickListener(e -> {
                if(receiveType.equals(UIController.SEND_TYPE_FOR_GUI_STRING))
                {
                    if(displayValues.length <= 0)
                    {
                        returnedValue.append(textFieldsArray[0].getValue());

                    }
                    else
                    {
                        String resultDelimeter = UIController.STRING_DELIMETER;
                        StringBuilder valuesSelected = new StringBuilder();
                        for (String value :
                                valuesForString.getSelectedItems()) {
                            valuesSelected.append(value).append(resultDelimeter);
                        }
                        // Remove the last ';' from the string value
                        valuesSelected.setLength(valuesSelected.length()-1);
                        returnedValue.append(valuesSelected);
                    }
                }
                else if(receiveType.equals(UIController.SEND_TYPE_FOR_GUI_INT))
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
                else if(receiveType.equals(UIController.SEND_TYPE_FOR_GUI_MULTIPLE_STRINGS))
                {
                    for (int i = 0; i < textFieldsArray.length; i++) {
                        returnedValue.append(textFieldsArray[i].getValue()).append(UIController.STRING_DELIMETER);
                    }
                    returnedValue.setLength(returnedValue.length()-1);
                }
                else if(receiveType.equals(UIController.SEND_TYPE_FOR_GUI_DATE))
                {
                    returnedValue.append(picker.getValue().toString());
                }
                else if(receiveType.equals(UIController.SEND_TYPE_FOR_GUI_MULTIPLE_INPUTS))
                {
                    apendValuesToReturnValue(returnedValue, multiInputsFromList);
                }
                newWindow.close();
                callingThread.interrupt();
//            UI.setCurrent(lastUI);
            });


            if(receiveType.equals(UIController.SEND_TYPE_FOR_GUI_STRING))
            {
                String[] messages = msg.split(UIController.STRING_DELIMETER);
                if(displayValues.length <= 0) {
                    createMultiStringInputs(vl, numOfInputs, textFieldsArray, submit, messages);
                }
                else
                {
                    vl.add(valuesForString);
                    valuesForString.setItems(displayValues[0]);
                    valuesForString.addValueChangeListener(e -> {
                       if(valuesForString.getSelectedItems().size() > 0)
                       {
                           submit.setEnabled(true);
                       }
                       else
                       {
                           submit.setEnabled(false);
                       }
                    });
                }

            }
            else if(receiveType.equals(UIController.SEND_TYPE_FOR_GUI_INT))
            {
                String[] messeages = msg.split(UIController.STRING_DELIMETER);
                for (String message : messeages){
                    Label lbl = new Label(message);
                    lbl.setWidth("100%");
                    vl.add(lbl);
                }

                if(displayValues.length > 0) {
                    valuesForInt.setItems(displayValues[0]);
                    valuesForInt.setClearButtonVisible(true);
                    vl.add(valuesForInt);
                    valuesForInt.addValueChangeListener(e -> {
                        if(!e.getValue().isEmpty())
                        {
                            submit.setEnabled(true);
                        }
                        else
                        {
                            submit.setEnabled(false);
                        }
                    });

                }

            }
            else if(receiveType.equals(UIController.SEND_TYPE_FOR_GUI_MULTIPLE_STRINGS))
            {
                String[] messages = msg.split(UIController.STRING_DELIMETER);
                createMultiStringInputs(vl,numOfInputs,textFieldsArray,submit, messages);
            }
            else if(receiveType.equals(UIController.SEND_TYPE_FOR_GUI_DATE))
            {
                vl.add(picker);
                picker.setLabel(msg);
                picker.addValueChangeListener(e -> {
                   if(picker.getValue() != null)
                   {
                       submit.setEnabled(true);
                   }
                   else
                   {
                       submit.setEnabled(false);
                   }
                });
            }
            else if(receiveType.equals(UIController.SEND_TYPE_FOR_GUI_MULTIPLE_INPUTS))
            {
                String[] messages = msg.split(UIController.STRING_DELIMETER);
                createMultiListInputs(vl, numOfInputs,multiInputsFromList,submit, messages, displayValues);
            }


            HorizontalLayout buttons = new HorizontalLayout();
            buttons.add(submit,cancel);
            vl.add(buttons);
            newWindow.add(vl);
            newWindow.open();
            lastUI.push();
        });


    }

    private static void apendValuesToReturnValue(StringBuilder returnedValue, ComboBox<String>[] multiInputsFromList) {
        for (ComboBox<String> singleComboBox:
             multiInputsFromList) {
            returnedValue.append(singleComboBox.getValue()).append(UIController.STRING_DELIMETER);
        }
        returnedValue.setLength(returnedValue.length()-1);
    }

    private static void createMultiListInputs(VerticalLayout verticalLayout, int numOfInputs, ComboBox<String>[] multiInputsFromList, Button close, String[] messagesToDisplay, Collection<String>... displayValues) {
        for (int i = 0; i < numOfInputs; i++) {
            multiInputsFromList[i] = new ComboBox<>();
            verticalLayout.add(new Label(messagesToDisplay[i]));
            multiInputsFromList[i].setClearButtonVisible(true);
            multiInputsFromList[i].setItems(displayValues[i]);
            multiInputsFromList[i].addValueChangeListener(e -> {
                    checkValidToSubmit(close,multiInputsFromList);
            });
            verticalLayout.add(multiInputsFromList[i]);
        }
    }

    private static void checkValidToSubmit(Button close, AbstractField[] fieldArray) {
        for (AbstractField field:
                fieldArray) {
            if(field.getValue() == null)
            {
                close.setEnabled(false);
                return;
            }
        }
        close.setEnabled(true);
    }


    /**
     * This function is adding {@code numOfInputs} text field to the Dialog window
     * each has a Label from the {@code messagesToDisplay} in the correct order
     * @param verticalLayout the layout to add the Text Fields into
     * @param numOfInputs the number of inputs to create
     * @param textFieldsArray the array of Text Fields which is empty
     * @param close button of submit, needs to be removed,
     * @param messagesToDisplay the array of messages splitted from the received message
     */
    private static void createMultiStringInputs(VerticalLayout verticalLayout, int numOfInputs, TextField[] textFieldsArray, Button close, String[] messagesToDisplay) {
        for (int i = 0; i < numOfInputs; i++) {
            textFieldsArray[i] = new TextField();
            //textFieldsArray[i].setLabel(messagesToDisplay[i]);
            verticalLayout.add(new Label(messagesToDisplay[i]));
            textFieldsArray[i].setValueChangeMode(ValueChangeMode.EAGER);
            textFieldsArray[i].addValueChangeListener(e -> {
                checkValidToSubmit(close,textFieldsArray);
            });
            verticalLayout.add(textFieldsArray[i]);
        }
    }

    public static void showModal(Collection<String>... displayValues){
        Dialog newWindow = new Dialog();
        newWindow.setCloseOnOutsideClick(false);
        newWindow.setCloseOnEsc(false);
        newWindow.setVisible(true);
        VerticalLayout vl = new VerticalLayout();
        newWindow.setCloseOnEsc(false);

        for (String game : displayValues[0]){

            Label ta = new Label(game);
                ta.setSizeFull();
                ta.setMaxHeight("100px");
            vl.add(ta);
        }

        Button ok = new Button("Ok");
        ok.addClickListener(e -> { newWindow.close(); });
        vl.add(ok);
        newWindow.add(vl);
        newWindow.open();
    }
}





