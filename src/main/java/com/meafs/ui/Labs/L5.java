package com.meafs.ui.Labs;

import com.meafs.Back.*;
import com.meafs.Back.l5.DES;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.validation.constraints.Null;
import java.util.*;

/**
 * Created by meaf on 21.12.16.
 */

public class L5 extends VerticalLayout implements View {
    private DES method;
    private VerticalLayout page, options, encryption;
    private HorizontalLayout operations, keyField;
    private Button btnEncrypt, btnDecrypt, btnReadFromFile, btnClear, btnGenerateKey;
    private Label lblHeader;
    private TextArea taInput;
    private TextField tfFileName, tfSecretKey;
    private KeyGenerator keygen;
    private SecretKey desKey;

    public L5() {
        lblHeader = new Label("DES encryption");
        lblHeader.addStyleName("h1");
        lblHeader.addStyleName("colored");
        try {
            keygen = KeyGenerator.getInstance("DES");
            desKey = keygen.generateKey();
            method = new DES(desKey);
        }catch (Exception e){e.printStackTrace();
            return;}


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

        tfSecretKey = new TextField();
        tfSecretKey.setPlaceholder("Secret keys");
        tfSecretKey.setWidth(100, Unit.PERCENTAGE);
        tfSecretKey.setReadOnly(true);
        tfSecretKey.setValue(Arrays.toString(desKey.getEncoded()));

        btnReadFromFile = new Button("Select message:");
        btnReadFromFile.setWidth(100, Unit.PERCENTAGE);
        btnReadFromFile.addClickListener(e ->
                readMessage()
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
        options = new VerticalLayout(btnReadFromFile, tfFileName, btnClear);
        options.setSpacing(false);
        options.setSizeFull();

        operations = new HorizontalLayout(btnEncrypt, btnDecrypt);

        encryption = new VerticalLayout(taInput, operations, tfSecretKey);

        keyField = new HorizontalLayout(btnGenerateKey, tfSecretKey);
        keyField.setExpandRatio(btnGenerateKey, 1);
        keyField.setExpandRatio(tfSecretKey, 2);
        keyField.setSizeFull();
        keyField.setMargin(false);

        page = new VerticalLayout(lblHeader, options, encryption, keyField);
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
            result = method.encrypt(taInput.getValue(), null);
            taInput.setValue(result);
        } catch (IllegalArgumentException e) {e.printStackTrace();}
    }

    private void decrypt() {
        try{
            String result;
            result = method.decrypt(taInput.getValue(), null);
            taInput.setValue(result);
        }
//        catch (IllegalArgumentException e) {e.printStackTrace();}
        catch (Exception e){
            Notification.show("Error decrypting the message", "either message or key is invalid", Notification.Type.HUMANIZED_MESSAGE);}
    }

    private void readMessage(){
        try {
            String str = FileUtil.readFromFile(tfFileName.getValue());
            taInput.setValue(str);
        }catch (Exception e){
            Notification.show(e.getLocalizedMessage());
        }
    }


    private void clear(){
        taInput.clear();
        tfFileName.clear();
        tfSecretKey.clear();
    }

    private void setRandomKey(){
        desKey = keygen.generateKey();
        method.setKey(desKey);
        tfSecretKey.setValue(Arrays.toString(desKey.getEncoded()));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

}
