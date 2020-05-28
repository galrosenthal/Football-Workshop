package GUI;

import Domain.EntityManager;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.WrappedSession;

@Route(value = "Welcome", layout = FootballMain.class)
@RouteAlias(value = "", layout = FootballMain.class)
@PageTitle("Welcome")
public class Welcome extends FlexLayout
{
    private VerticalLayout content;
    private Button next;

    public Welcome() {
        buildWelcomePage();
    }

    private void buildWelcomePage() {
        setSizeFull();
        setClassName("welcome-page");
        content = new VerticalLayout();
        content.setId("welcome");
        content.setJustifyContentMode(JustifyContentMode.CENTER);
        content.setAlignItems(Alignment.CENTER);
        add(content);
        String spanText = "Welcome to the Football System.\n " +
                "if you wish to go to the about page press the" +
                " button below";
        Span welcomeSpan = new Span(spanText);
        welcomeSpan.setId("welcomeSpan");
        content.add(welcomeSpan);
        next = new Button("See About");
        next.setWidth("50%");
        next.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        next.addClickListener(event -> {
            getUI().get().navigate("About");
        });
        next.setVisible(false);

        content.add(next);

    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        try{
            WrappedSession userSession = VaadinService.getCurrentRequest().getWrappedSession();


            //This is just a button disable for fun not needed!
            if(userSession.getAttribute("username") != null)
            {
                next.setVisible(true);
            }
            else
            {
                next.setVisible(false);
            }
        }
        catch (Exception e)
        {
            getUI().get().getPage().reload();
        }


    }
}
