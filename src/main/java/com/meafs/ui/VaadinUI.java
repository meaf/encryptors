package com.meafs.ui;

/**
 * Created by meaf on 25.12.16.
 */

import com.meafs.ui.Labs.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

@SpringUI
@Title("Encryption methods")
@Theme("valo")
public class VaadinUI extends UI {


    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);
        navigator.addView("Home", new HomePage());

        navigator.navigateTo("Home");
    }
}