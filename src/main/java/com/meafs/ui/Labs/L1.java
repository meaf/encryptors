package com.meafs.ui.Labs;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 * Created by meaf on 21.12.16.
 */

public class L1 extends VerticalLayout implements View {
    Label lblHeader = new Label("Caesar cipher");

    public L1() {
        lblHeader.setStyleName("h2");
        addComponent(lblHeader);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}