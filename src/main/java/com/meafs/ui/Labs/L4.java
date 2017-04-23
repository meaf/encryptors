package com.meafs.ui.Labs;

import com.meafs.Back.*;
import com.meafs.Back.l4.Stirl;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.*;

/**
 * Created by meaf on 21.12.16.
 */

public class L4 extends VerticalLayout implements View {
    private Stirl method;
    private VerticalLayout page, options, encryption;
    private HorizontalLayout upperOptions, selectOptions, operations;
    private Button btnEncrypt, btnDecrypt, btnReadFromFile, btnReadText, btnClear, btnGenerateKey;
    private Label lblHeader;
    private TextArea taInput;
    private TextField tfFileName, tfPassPhrase;
    private ComboBox<Charset> comboCharset;
    private LinkedList<Charset> charsetList;
    private int seed;


    public L4() {
        lblHeader = new Label("Gamma cipher");
        lblHeader.addStyleName("h1");
        lblHeader.addStyleName("colored");

        charsetList = CharsetProvider.getInstance().getAll();
        method = new Stirl(charsetList.getLast());

        comboCharset = new ComboBox<>("", charsetList);
        comboCharset.setWidth(100, Unit.PERCENTAGE);
        comboCharset.setItemCaptionGenerator(Charset::getName);
        comboCharset.setPlaceholder("Select charset");
        comboCharset.setTextInputAllowed(false);
        comboCharset.addValueChangeListener(e -> changeCharset());
        comboCharset.setValue(charsetList.getLast());



        btnGenerateKey = new Button("Generate Key");
        btnGenerateKey.setWidth(100, Unit.PERCENTAGE);
        btnGenerateKey.addClickListener(e ->
                setRandomKey()
        );

        taInput = new TextArea();
        taInput.setWidth(100, Unit.PERCENTAGE);
        taInput.setHeight(80, Unit.PERCENTAGE);
        taInput.setPlaceholder("Enter message to decrypt");

        tfFileName = new TextField();
        tfFileName.setPlaceholder("File name");
        tfFileName.setWidth(100, Unit.PERCENTAGE);

        tfPassPhrase = new TextField();
        tfPassPhrase.setPlaceholder("Secret keys");
        tfPassPhrase.setWidth(100, Unit.PERCENTAGE);
        tfPassPhrase.setReadOnly(true);
        tfPassPhrase.addContextClickListener(e -> Notification.show("asd"));

        btnReadFromFile = new Button("Select message:");
        btnReadFromFile.setWidth(100, Unit.PERCENTAGE);
        btnReadFromFile.addClickListener(e ->
                readMessage()
        );

        btnReadText = new Button("Select text:");
        btnReadText.setWidth(100, Unit.PERCENTAGE);
        btnReadText.addClickListener(e ->
                readText()
        );



        btnClear = new Button("Clear All");
        btnClear.setWidth(100, Unit.PERCENTAGE);
        btnClear.addClickListener(e ->
                clear()
        );

        btnEncrypt = new Button("Encrypt");
        btnEncrypt.setWidth(100, Unit.PERCENTAGE);
        btnEncrypt.addStyleName("tiny");
        btnEncrypt.addStyleName("borderless");
        btnEncrypt.addClickListener(e ->
            encrypt()
        );

        btnDecrypt = new Button("Decrypt");
        btnDecrypt.setWidth(100, Unit.PERCENTAGE);
        btnDecrypt.addStyleName("tiny");
        btnDecrypt.addStyleName("borderless");
        btnDecrypt.addClickListener(e ->
                decrypt()
        );
        btnDecrypt.setWidth(100, Unit.PERCENTAGE);

        ///       alignment setup

        upperOptions = new HorizontalLayout(comboCharset, btnGenerateKey);
        upperOptions.setComponentAlignment(btnGenerateKey, Alignment.BOTTOM_RIGHT);
        upperOptions.setSizeFull();
        upperOptions.setSpacing(false);

        selectOptions = new HorizontalLayout(btnReadFromFile, btnReadText);
        selectOptions.setSizeFull();
        selectOptions.setSpacing(false);

        options = new VerticalLayout(upperOptions, selectOptions, tfFileName, btnClear);
        options.setSpacing(false);
        options.setSizeFull();

        operations = new HorizontalLayout(btnEncrypt, btnDecrypt);

        encryption = new VerticalLayout(taInput, operations, tfPassPhrase);

        page = new VerticalLayout(lblHeader, options, encryption);
        page.setComponentAlignment(lblHeader, Alignment.TOP_CENTER);
        page.setSpacing(false);
        page.setMargin(false);
        addComponent(page);
        setSizeFull();
        setMargin(false);
        setSpacing(false);
    }

    private void encrypt() {
        try {
            String result;

            result = method.encrypt(taInput.getValue(), seed);
            taInput.setValue(result);
        } catch (IllegalArgumentException e) {
            Notification error = new Notification("Invalid input", e.getLocalizedMessage(), Notification.Type.HUMANIZED_MESSAGE);
            error.show(Page.getCurrent());
        }
    }

    private boolean decrypt() {
        try{
            String result;
            result = method.decrypt(taInput.getValue(), seed);
            taInput.setValue(result);
        }catch (IllegalArgumentException e) {
            Notification error = new Notification("Invalid input", e.getLocalizedMessage(), Notification.Type.HUMANIZED_MESSAGE);
            error.show(Page.getCurrent());
        }catch (StringIndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    private void changeCharset() {
        try {
            Charset charset = comboCharset.getValue();
            method.setCharset(charset);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void readMessage(){
        try {
            String str = FileUtil.readFromFile(tfFileName.getValue());
            taInput.setValue(str);
        }catch (Exception e){
            Notification.show(e.getLocalizedMessage());
        }
    }

    private void readText(){
        try {
            String textStr = FileUtil.readFromFile(tfFileName.getValue());
            method.setText(textStr);
        }catch (Exception e){
            Notification.show(e.getLocalizedMessage());
        }
    }

    private void clear(){
        taInput.clear();
        tfFileName.clear();
        tfPassPhrase.clear();
    }

    private void setRandomKey(){
        Random random = new Random();
        this.seed = random.nextInt();
        tfPassPhrase.setValue("your seed = " + seed);
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

}//TODO: DD84 quant cryps
