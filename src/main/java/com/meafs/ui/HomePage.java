package com.meafs.ui;

import com.meafs.ui.Labs.L1;
import com.meafs.ui.Labs.L2;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 * Created by meaf on 22.02.17.
 */
public class HomePage extends VerticalLayout implements View{

    private HorizontalSplitPanel lowerSection = new HorizontalSplitPanel();
    private VerticalLayout menuLayout = new VerticalLayout();
    private HorizontalLayout menuTitle = new HorizontalLayout();
    private VerticalLayout contentLayout = new VerticalLayout();
    private Label lblMenu;

    HomePage() {

        //UI Components
        lblMenu = new Label("Select encryption");
        lblMenu.addStyleName("colored");
        lblMenu.addStyleName("h2");

        //menu section
        menuTitle.addComponent(lblMenu);
        menuLayout.addComponent(menuTitle);
        menuLayout.setWidth(100, Unit.PERCENTAGE);
        menuLayout.setComponentAlignment(menuTitle, Alignment.MIDDLE_CENTER);

        lowerSection.addComponent(menuLayout);
        lowerSection.addComponent(contentLayout);
        contentLayout.setSizeFull();
        contentLayout.setSpacing(false);
        lowerSection.setSizeFull();
        lowerSection.setSplitPosition(300, Unit.PIXELS);

        addComponent(lowerSection);
        setSizeFull();
        setMargin(false);


    }

    private void setMenuTitle() {
        //set the menu title
        menuTitle.addComponent(lblMenu);
        menuLayout.addComponent(menuTitle);
        menuLayout.setWidth("100%");
        menuLayout.setComponentAlignment(menuTitle, Alignment.MIDDLE_CENTER);

    }


    private Component getComponent(String componentName) {
        switch (componentName){
            case "L1":
                return new L1();
            default:
                return new L2();
        }
    }

    private void addMenuOption(String caption, String componentName) {
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

