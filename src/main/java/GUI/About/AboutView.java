package GUI.About;

import GUI.FootballMain;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Version;

@Route(value = "About", layout = FootballMain.class)
@PageTitle("About")
public class AboutView extends VerticalLayout {

    public static final String VIEW_NAME = "About";
    private final Button back;

    public AboutView() {
        add(VaadinIcon.INFO_CIRCLE.create());
        add(new Span(" This is our Football System Created by Team 17! "
                + Version.getFullVersion() + "."));
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        back = new Button("Go Back to the Welcome page");
        back.setWidth("50%");
        back.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        back.addClickListener(event -> {
            getUI().get().navigate("");
        });
        add(back);
    }
}