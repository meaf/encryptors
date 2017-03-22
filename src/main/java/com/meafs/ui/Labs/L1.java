package com.meafs.ui.Labs;

import com.meafs.Back.*;
import com.meafs.Back.l1.Caesar;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.*;

import java.util.*;

/**
 * Created by meaf on 21.12.16.
 */

public class L1 extends VerticalLayout implements View {
    private Caesar method;
    private VerticalLayout page, morphChain, options;
    private HorizontalLayout controls, decryptOpt;
    private Label lblHeader;
    private Button btnEncrypt, btnDecrypt, btnBrute, btnReadFromFile, btnClear;
    private TextArea taInput, taProcessed, taOutput;
    private TextField tfFileName;
    private Slider keySlider;
    private ComboBox<Charset> comboCharset;
    private LinkedList<Charset> charsetList;
    private int interval;

    public L1() {


        lblHeader = new Label("Caesar cipher");
        lblHeader.addStyleName("h1");
        lblHeader.addStyleName("colored");
        lblHeader.setSizeFull();

        charsetList = CharsetProvider.getInstance().getAll();
        method = new Caesar(charsetList.getLast());
        interval = method.getCharset().length();

        comboCharset = new ComboBox<>("", charsetList);
        comboCharset.setItemCaptionGenerator(Charset::getName);
        comboCharset.setPlaceholder("Select charset");
        comboCharset.setTextInputAllowed(false);
        comboCharset.setValue(charsetList.getLast());
        comboCharset.addValueChangeListener(e -> changeCharset());

        taInput = new TextArea();
        taInput.setWidth(100, Unit.PERCENTAGE);
        taInput.setHeight(25, Unit.PERCENTAGE);
        taInput.setPlaceholder("Input message to decrypt");

        taProcessed = new TextArea();
        taProcessed.setWidth(100, Unit.PERCENTAGE);
        taProcessed.setHeight(25, Unit.PERCENTAGE);
        taProcessed.setPlaceholder("Encrypted message");

        taOutput = new TextArea();
        taOutput.setWidth(100, Unit.PERCENTAGE);
        taOutput.setHeight(25, Unit.PERCENTAGE);
        taOutput.setPlaceholder("Decrypted message");
        taOutput.setReadOnly(true);

        tfFileName = new TextField();
        tfFileName.setPlaceholder("File name");
        tfFileName.setWidth(100, Unit.PERCENTAGE);

        btnReadFromFile = new Button("Select");
        btnReadFromFile.setWidth(100, Unit.PERCENTAGE);
        btnReadFromFile.addClickListener(e -> {
            read();
        });



        btnClear = new Button("Clear");
        btnClear.setWidth(100, Unit.PERCENTAGE);
        btnClear.addClickListener(e -> {
            clear();
        });

        btnEncrypt = new Button("Encrypt");
        btnEncrypt.setWidth(100, Unit.PERCENTAGE);
        btnEncrypt.addStyleName("tiny");
        btnEncrypt.addStyleName("borderless");
        btnEncrypt.addClickListener(e -> {
            encrypt(taInput.getValue());
        });

        btnDecrypt = new Button("Decrypt");
        btnDecrypt.setWidth(100, Unit.PERCENTAGE);
        btnDecrypt.addStyleName("tiny");
        btnDecrypt.addStyleName("borderless");
        btnDecrypt.addClickListener(e -> {
            decrypt(taProcessed.getValue());
        });


        btnBrute = new Button("Brute");
        btnBrute.setWidth(100, Unit.PERCENTAGE);
        btnBrute.addStyleName("tiny");
        btnBrute.addStyleName("borderless");
        btnBrute.addClickListener(e -> {
            brute(taProcessed.getValue());
        });

        keySlider = new Slider(-interval, interval);
        keySlider.setOrientation(SliderOrientation.VERTICAL);

        keySlider.setHeight(400, Unit.PIXELS);




        options = new VerticalLayout(comboCharset, btnReadFromFile, tfFileName, btnClear);
        options.setSpacing(false);
        options.setSizeFull();

        decryptOpt = new HorizontalLayout(btnDecrypt, btnBrute);
        decryptOpt.setComponentAlignment(btnDecrypt, Alignment.MIDDLE_CENTER);
        decryptOpt.setComponentAlignment(btnBrute, Alignment.MIDDLE_CENTER);
        decryptOpt.setWidth(100, Unit.PERCENTAGE);

        morphChain = new VerticalLayout(options, taInput, btnEncrypt, taProcessed, decryptOpt, taOutput);
        morphChain.setSizeFull();
        morphChain.setSpacing(false);

        controls = new HorizontalLayout(keySlider, morphChain);
        controls.setComponentAlignment(morphChain, Alignment.MIDDLE_LEFT);
        controls.setComponentAlignment(keySlider, Alignment.MIDDLE_RIGHT);
        controls.setExpandRatio(morphChain, 1);
        controls.setExpandRatio(keySlider, 0.05F);
        controls.setSizeFull();
        controls.setSpacing(false);


        page = new VerticalLayout(lblHeader, controls);
        page.setComponentAlignment(lblHeader, Alignment.TOP_CENTER);
        page.setComponentAlignment(controls, Alignment.TOP_LEFT);
        page.setExpandRatio(controls, 1);
        page.setSpacing(false);
        page.setMargin(false);
        addComponent(page);
        setSizeFull();
        setMargin(false);
        setSpacing(false);
    }

    private void changeCharset() {
        try {
            interval = method.getCharset().length();
            String charset = comboCharset.getValue().getCharset();
            method.setCharset(charset);
            keySlider.setMax(interval);
            keySlider.setMin(-interval);
            clear();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void encrypt(String caption) {
        Integer key = keySlider.getValue().intValue();
        try{
            String result = method.encrypt(caption, key);
            taProcessed.setValue(result);
        }catch (IllegalArgumentException e) {
            Notification error = new Notification("Invalid input", e.getLocalizedMessage(), Notification.Type.HUMANIZED_MESSAGE);
            error.show(Page.getCurrent());
        }

    }

    private void decrypt(String caption) {
        Integer key = keySlider.getValue().intValue();
        try{
            taOutput.setValue(method.decrypt(caption, key));
        }catch (IllegalArgumentException e) {
            Notification error = new Notification("Invalid input", e.getLocalizedMessage(), Notification.Type.HUMANIZED_MESSAGE);
            error.show(Page.getCurrent());
        }
    }

    private void brute(String caption) {
        try{
            taOutput.setValue(method.brute(caption));
        }catch (IllegalArgumentException e) {
            Notification error = new Notification("Invalid input", e.getLocalizedMessage(), Notification.Type.HUMANIZED_MESSAGE);
            error.show(Page.getCurrent());
        }
    }

    private void read(){
        try {
            String str = FileUtil.readFromFile(tfFileName.getValue());
            taInput.setValue(str);
        }catch (Exception e){
            Notification.show(e.getLocalizedMessage());
        }
    }

    private void clear(){
        taInput.clear();
        taOutput.clear();
        taProcessed.clear();
        keySlider.setValue(0D);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}