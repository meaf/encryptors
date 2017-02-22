package com.meafs.ui;

import com.meafs.ui.Labs.L1;
import com.meafs.ui.Labs.L2;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.shared.Registration;
import com.vaadin.shared.communication.SharedState;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.declarative.DesignContext;
import elemental.json.JsonObject;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created by meaf on 22.02.17.
 */
public class HomePage extends VerticalLayout implements View{

    HorizontalLayout upperSection = new HorizontalLayout();
    HorizontalLayout innerUpperSection = new HorizontalLayout();
    HorizontalSplitPanel lowerSection = new HorizontalSplitPanel();
    VerticalLayout menuLayout = new VerticalLayout();
    HorizontalLayout menuTitle = new HorizontalLayout();
    VerticalLayout contentLayout = new VerticalLayout();
    Label lblHeader;
    Label lblMenu;

    public HomePage() {

        //UI Components

        lblHeader = new Label("Labs");
        lblHeader.addStyleName("colored");
        lblHeader.addStyleName("h2");
        lblHeader.setSizeUndefined();

        lblMenu = new Label("Select encryption");
        lblMenu.addStyleName("colored");
        lblMenu.addStyleName("h2");

        //Sections
        innerUpperSection.addComponent(lblHeader);
        innerUpperSection.setSpacing(true);

        upperSection.setSizeFull();
        upperSection.addComponent(innerUpperSection);

        upperSection.setMargin(new MarginInfo(false, true, false, false));
        upperSection.setComponentAlignment(innerUpperSection, Alignment.TOP_RIGHT);
        upperSection.addStyleName("borderBottom");
        upperSection.setHeight(4, UNITS_EM);

        //menu section
        menuTitle.addComponent(lblMenu);
        menuLayout.addComponent(menuTitle);
        menuLayout.setWidth(100, Unit.PERCENTAGE);
        menuLayout.setComponentAlignment(menuTitle, Alignment.MIDDLE_CENTER);

        lowerSection.addComponent(menuLayout);
        lowerSection.addComponent(contentLayout);
        contentLayout.setSizeFull();
        lowerSection.setSizeFull();
        lowerSection.setSplitPosition(300, Unit.PIXELS);

        addComponent(upperSection);
        addComponent(lowerSection);

        setSizeFull();

        setExpandRatio(lowerSection,1);


    }

    public void setMenuTitle() {
        //set the menu title
        menuTitle.addComponent(lblMenu);
        menuLayout.addComponent(menuTitle);
        menuLayout.setWidth("100%");
        menuLayout.setComponentAlignment(menuTitle, Alignment.MIDDLE_CENTER);

    }


    public Component getComponent(String componentName) {
        switch (componentName){
            case "L1":
                return new L1();
            default:
                return new L2();
        }
    }

    public void addMenuOption(String caption, String componentName) {
        Button button = new Button(caption);
        button.setWidth("100%");
        button.setStyleName("borderless");
        menuLayout.addComponent(button);
        button.addClickListener(e->
        {
            contentLayout.removeAllComponents();
            contentLayout.addComponent(getComponent(componentName));
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        menuLayout.removeAllComponents();
        contentLayout.removeAllComponents();

        setMenuTitle();
        {
            this.addMenuOption("Caesar cipher (lab 1)", "L1");
            this.addMenuOption("Trithemius cipher (lab 2)", "L2");
        }
    }
}

