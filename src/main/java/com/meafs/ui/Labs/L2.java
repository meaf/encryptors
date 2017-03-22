package com.meafs.ui.Labs;

import com.meafs.Back.Charset;
import com.meafs.Back.CharsetProvider;
import com.meafs.Back.FileUtil;
import com.meafs.Back.l2.Trithemius;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.*;

/**
 * Created by meaf on 21.12.16.
 */

public class L2 extends VerticalLayout implements View {
    private Trithemius method;
    private VerticalLayout page, morphChain, sliders, options, ASliderAl, BSliderAl, CSliderAl;
    private HorizontalLayout controls, upperOptions, ppOption;
    private Button btnEncrypt, btnDecrypt, btnReadFromFile, btnClear;
    private Label lblHeader, lblFunction;
    private TextArea taInput, taProcessed, taOutput;
    private TextField tfFileName, tfPassPhrase, Akey, Bkey, Ckey;
    private ComboBox<Charset> comboCharset;
    private LinkedList<Charset> charsetList;
    private CheckBox cbA, cbB, cbC, cbPP;
    private Integer temp;

    public L2() {
        lblHeader = new Label("Trithemius cipher");
        lblHeader.addStyleName("h1");
        lblHeader.addStyleName("colored");

        lblFunction = new Label();
        lblFunction.addStyleName("colored");

        charsetList = CharsetProvider.getInstance().getAll();
        method = new Trithemius(charsetList.getLast());

        comboCharset = new ComboBox<>("", charsetList);
        comboCharset.setItemCaptionGenerator(Charset::getName);
        comboCharset.setPlaceholder("Select charset");
        comboCharset.setTextInputAllowed(false);
        comboCharset.setValue(charsetList.getLast());
        comboCharset.addValueChangeListener(e -> changeCharset());



        taInput = new TextArea();
        taInput.setWidth(100, Unit.PERCENTAGE);
        taInput.setHeight(25, Unit.PERCENTAGE);
        taInput.setPlaceholder("Enter message to decrypt");

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

        btnReadFromFile = new Button("Select file:");
        btnReadFromFile.setWidth(100, Unit.PERCENTAGE);
        btnReadFromFile.addClickListener(e ->
            read()
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

        Akey = new TextField();
        Akey.setValue("0");
        Akey.setMaxLength(6);
        Akey.addValueChangeListener(e->
            setFunction()
        );
        cbA = new CheckBox();
        cbA.setValue(true);
        cbA.addValueChangeListener(e->{
            Akey.setEnabled(cbA.getValue());
            setFunction();
        });

        Bkey = new TextField();
        Bkey.setValue("0");
        Bkey.setMaxLength(6);
        Bkey.addListener(e->
            setFunction()
        );
        cbB = new CheckBox();
        cbB.setValue(true);
        cbB.addValueChangeListener(e->{
            Bkey.setEnabled(cbB.getValue());
            setFunction();
        });

        Ckey = new TextField();
        Ckey.setValue("0");
        Ckey.setMaxLength(6);
        Ckey.addValueChangeListener(e->
            setFunction()
        );
        cbC = new CheckBox();
        cbC.setValue(true);
        cbC.addValueChangeListener(e->{
            Ckey.setEnabled(cbC.getValue());
            setFunction();
        });

        tfPassPhrase = new TextField();
        tfPassPhrase.setPlaceholder("enter passphrase");
        tfPassPhrase.setWidth(100, Unit.PERCENTAGE);

        cbPP = new CheckBox();
        cbPP.addValueChangeListener(e->
            setPhraseMode(!cbPP.getValue())
        );

        setFunction();

                ///       alignment setup

        upperOptions = new HorizontalLayout(comboCharset, lblFunction);
        upperOptions.setExpandRatio(lblFunction, 1);
        upperOptions.setSizeFull();
        upperOptions.setComponentAlignment(lblFunction, Alignment.BOTTOM_LEFT);

        options = new VerticalLayout(upperOptions, btnReadFromFile, tfFileName, btnClear);
        options.setSpacing(false);
        options.setSizeFull();

        ASliderAl = new VerticalLayout(cbA, Akey);
        ASliderAl.setComponentAlignment(cbA, Alignment.MIDDLE_CENTER);
        ASliderAl.setComponentAlignment(Akey, Alignment.MIDDLE_LEFT);
        ASliderAl.setSizeFull();
        ASliderAl.setSpacing(false);
        ASliderAl.setMargin(false);

        BSliderAl = new VerticalLayout(cbB, Bkey);
        BSliderAl.setComponentAlignment(cbB, Alignment.MIDDLE_CENTER);
        BSliderAl.setComponentAlignment(Bkey, Alignment.MIDDLE_LEFT);
        BSliderAl.setSizeFull();
        BSliderAl.setSpacing(false);
        BSliderAl.setMargin(false);

        CSliderAl = new VerticalLayout(cbC, Ckey);
        CSliderAl.setComponentAlignment(cbC, Alignment.MIDDLE_CENTER);
        CSliderAl.setComponentAlignment(Ckey, Alignment.MIDDLE_LEFT);
        CSliderAl.setSizeFull();
        CSliderAl.setSpacing(false);
        CSliderAl.setMargin(false);


        sliders = new VerticalLayout(ASliderAl, BSliderAl, CSliderAl);
        sliders.setSpacing(false);

        morphChain = new VerticalLayout(options, taInput, btnEncrypt, taProcessed, btnDecrypt, taOutput);
        morphChain.setSizeFull();
        morphChain.setSpacing(false);

        controls = new HorizontalLayout(sliders, morphChain);
        controls.setComponentAlignment(morphChain, Alignment.MIDDLE_LEFT);
        controls.setComponentAlignment(sliders, Alignment.MIDDLE_RIGHT);
        controls.setExpandRatio(morphChain, 1);
        controls.setExpandRatio(sliders, 0.21F);
        controls.setSizeFull();

        ppOption = new HorizontalLayout(cbPP, tfPassPhrase);
        ppOption.setExpandRatio(tfPassPhrase, 1);
        ppOption.setWidth(100, Unit.PERCENTAGE);

        page = new VerticalLayout(lblHeader, controls, ppOption);
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

    private void setFunction() {
        StringBuilder sb = new StringBuilder();

        if(Akey.isEnabled()) sb.append(getKey(Akey)).append("t"+(char)178);
        if(Bkey.isEnabled()) {
            if (getKey(Bkey) >= 0 && Akey.isEnabled()) {
                sb.append('+');
            }
            sb.append(getKey(Bkey)).append("t");
        }
        if(Ckey.isEnabled()) {
            if (getKey(Ckey) >= 0 && (Akey.isEnabled() || Bkey.isEnabled())) {
                sb.append('+');
            }
            sb.append(getKey(Ckey));
        }
        lblFunction.setValue(sb.toString());
    }

    private void changeCharset() {
        try {
            String charset = comboCharset.getValue().getCharset();
            method.setCharset(charset);
            clearSliders();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void encrypt() {
        try{
            String result;
            if (cbPP.getValue())
                result = method.encrypt(taInput.getValue(), tfPassPhrase.getValue());
            else
                result = method.encrypt(taInput.getValue(), getSliderVals());
                taProcessed.setValue(result);
        }catch (IllegalArgumentException e) {
            Notification error = new Notification("Invalid input", e.getLocalizedMessage(), Notification.Type.HUMANIZED_MESSAGE);
            error.show(Page.getCurrent());
        }
    }

    private void decrypt() {
        try{
            String result;
            if (cbPP.getValue())
                result = method.decrypt(taProcessed.getValue(), tfPassPhrase.getValue());
            else
                result = method.decrypt(taProcessed.getValue(),getSliderVals());
            taOutput.setValue(result);
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
        tfFileName.clear();
        tfPassPhrase.clear();
        cbA.setValue(true);
        cbB.setValue(true);
        cbC.setValue(true);
        cbPP.setValue(false);
        clearSliders();
    }

    private void clearSliders(){
        Akey.setValue("0");
        Bkey.setValue("0");
        Ckey.setValue("0");
    }

    private void setPhraseMode(Boolean phraseMode) {
        cbA.setValue(phraseMode);
        cbB.setValue(phraseMode);
        cbC.setValue(phraseMode);
    }

    private int[] getSliderVals(){
        int[] val = new int[3];
        try {
            if (cbA.getValue()) val[0] = getKey(Akey);
            if (cbB.getValue()) val[1] = getKey(Bkey);
            if (cbC.getValue()) val[2] = getKey(Ckey);
        }catch (NumberFormatException e){
            e.printStackTrace();
            Notification.show("Enter valid keys");
        }
        return val;
    }


    private int getKey(TextField key){
        temp = 0;
        try {
            temp = Integer.parseInt(key.getValue());
        }   catch (NumberFormatException e){
            Notification.show("Illegal key");
        }
        return temp;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

}