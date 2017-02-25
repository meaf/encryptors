package com.meafs.ui.Labs;

import com.meafs.Back.Charsets;
import com.meafs.Back.FileUtil;
import com.meafs.Back.l2.Trithemius;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.*;

import java.util.*;

/**
 * Created by meaf on 21.12.16.
 */

public class L2 extends VerticalLayout implements View {
    private Trithemius method = new Trithemius(Charsets.ENG.getCharset());
    private VerticalLayout page, morphChain, options, ASliderAl, BSliderAl, CSliderAl;
    private HorizontalLayout controls, sliders, upperOptions, ppOption;
    private Button btnEncrypt, btnDecrypt, btnReadFromFile, btnClear;
    private Set<Charsets> charsets;
    private Label lblHeader, lblFunction;
    private TextArea taInput, taProcessed, taOutput;
    private TextField tfFileName, tfPassPhrase;
    private Slider ASlider, BSlider, CSlider;
    private ComboBox<Charsets> comboCharset;
    private CheckBox cbA, cbB, cbC, cbPP;

    public L2() {
        int interval;
        interval = method.getCharset().length();

        lblHeader = new Label("Trithemius cipher");
        lblHeader.addStyleName("h1");
        lblHeader.addStyleName("colored");

        lblFunction = new Label();
        lblFunction.addStyleName("colored");

        charsets = EnumSet.allOf(Charsets.class);

        comboCharset = new ComboBox<>("", charsets);
        comboCharset.setItemCaptionGenerator(Charsets::getName);
        comboCharset.setPlaceholder("Select charset");
        comboCharset.setTextInputAllowed(false);
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
        btnReadFromFile.addClickListener(e -> {
            read();
        });



        btnClear = new Button("Clear All");
        btnClear.setWidth(100, Unit.PERCENTAGE);
        btnClear.addClickListener(e -> {
            clear();
        });

        btnEncrypt = new Button("Encrypt");
        btnEncrypt.setWidth(100, Unit.PERCENTAGE);
        btnEncrypt.addStyleName("tiny");
        btnEncrypt.addStyleName("borderless");
        btnEncrypt.addClickListener(e -> {
            encrypt();
        });

        btnDecrypt = new Button("Decrypt");
        btnDecrypt.setWidth(100, Unit.PERCENTAGE);
        btnDecrypt.addStyleName("tiny");
        btnDecrypt.addStyleName("borderless");
        btnDecrypt.addClickListener(e -> {
            decrypt(taProcessed.getValue());
        });
        btnDecrypt.setWidth(100, Unit.PERCENTAGE);

        ASlider = new Slider(-interval, interval);
        ASlider.setOrientation(SliderOrientation.VERTICAL);
        ASlider.setHeight(500, Unit.PIXELS);
        ASlider.addValueChangeListener(e->{
            setFunction();
        });
        cbA = new CheckBox();
        cbA.setValue(true);
        cbA.addValueChangeListener(e->{
            ASlider.setEnabled(cbA.getValue());
            setFunction();
        });

        BSlider = new Slider(-interval, interval);
        BSlider.setOrientation(SliderOrientation.VERTICAL);
        BSlider.setHeight(500, Unit.PIXELS);
        BSlider.addListener(e->{
            setFunction();
        });
        cbB = new CheckBox();
        cbB.setValue(true);
        cbB.addValueChangeListener(e->{
            BSlider.setEnabled(cbB.getValue());
            setFunction();
        });

        CSlider = new Slider(-interval, interval);
        CSlider.setOrientation(SliderOrientation.VERTICAL);
        CSlider.setHeight(500, Unit.PIXELS);
        CSlider.addValueChangeListener(e->{
            setFunction();
        });
        cbC = new CheckBox();
        cbC.setValue(true);
        cbC.addValueChangeListener(e->{
            CSlider.setEnabled(cbC.getValue());
            setFunction();
        });

        tfPassPhrase = new TextField();
        tfPassPhrase.setPlaceholder("enter passphrase");
        tfPassPhrase.setWidth(100, Unit.PERCENTAGE);

        cbPP = new CheckBox();
        cbPP.addValueChangeListener(e->{
            setPhraseMode(!cbPP.getValue());
        });

        setFunction();

                ///       alignment setup

        upperOptions = new HorizontalLayout(comboCharset, lblFunction);
        upperOptions.setExpandRatio(lblFunction, 1);
        upperOptions.setSizeFull();
        upperOptions.setComponentAlignment(lblFunction, Alignment.BOTTOM_LEFT);

        options = new VerticalLayout(upperOptions, btnReadFromFile, tfFileName, btnClear);
        options.setSpacing(false);
        options.setSizeFull();

        ASliderAl = new VerticalLayout(cbA, ASlider);
        ASliderAl.setComponentAlignment(cbA, Alignment.MIDDLE_CENTER);
        ASliderAl.setComponentAlignment(ASlider, Alignment.MIDDLE_LEFT);
        ASliderAl.setSizeFull();
        ASliderAl.setSpacing(false);
        ASliderAl.setMargin(false);

        BSliderAl = new VerticalLayout(cbB, BSlider);
        BSliderAl.setComponentAlignment(cbB, Alignment.MIDDLE_CENTER);
        BSliderAl.setComponentAlignment(BSlider, Alignment.MIDDLE_LEFT);
        BSliderAl.setSizeFull();
        BSliderAl.setSpacing(false);
        BSliderAl.setMargin(false);

        CSliderAl = new VerticalLayout(cbC, CSlider);
        CSliderAl.setComponentAlignment(cbC, Alignment.MIDDLE_CENTER);
        CSliderAl.setComponentAlignment(CSlider, Alignment.MIDDLE_LEFT);
        CSliderAl.setSizeFull();
        CSliderAl.setSpacing(false);
        CSliderAl.setMargin(false);


        sliders = new HorizontalLayout(ASliderAl, BSliderAl, CSliderAl);
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

        if(ASlider.isEnabled()) sb.append(ASlider.getValue()).append("t"+(char)178);
        if(BSlider.isEnabled()) {
            if (BSlider.getValue() >= 0 && ASlider.isEnabled()) {
                sb.append('+');
            }
            sb.append(BSlider.getValue()).append("t");
        }
        if(CSlider.isEnabled()) {
            if (CSlider.getValue() >= 0 && (ASlider.isEnabled() || BSlider.isEnabled())) {
                sb.append('+');
            }
            sb.append(CSlider.getValue());
        }
        lblFunction.setValue(sb.toString());
    }

    private void changeCharset() {
        try {
            String charset = comboCharset.getValue().getCharset();
            method.setCharset(charset); // TODO: merge enum class
            CSlider.setMax(method.getCharset().length());
            CSlider.setMin(-method.getCharset().length());
            clearSliders();
        } catch (NullPointerException e){
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

    private void decrypt(String caption) {
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
        ASlider.setValue(0D);
        BSlider.setValue(0D);
        CSlider.setValue(0D);
    }

    private void setPhraseMode(Boolean phraseMode) {
        cbA.setValue(phraseMode);
        cbB.setValue(phraseMode);
        cbC.setValue(phraseMode);
    }

    private int[] getSliderVals(){
        int[] val = new int[3];
        if(cbA.getValue()) val[0]=ASlider.getValue().intValue();
        if(cbB.getValue()) val[1]=BSlider.getValue().intValue();
        if(cbC.getValue()) val[2]=CSlider.getValue().intValue();
        return val;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

}