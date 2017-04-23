package com.meafs.ui.Labs;

import com.meafs.Back.Charset;
import com.meafs.Back.CharsetProvider;
import com.meafs.Back.FileUtil;
import com.meafs.Back.l3.Gamma;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.*;

/**
 * Created by meaf on 21.12.16.
 */

public class L3 extends VerticalLayout implements View {
    private Gamma method;
    private VerticalLayout page, morphChain, options;
    private HorizontalLayout controls, upperOptions, ppOption;
    private Button btnEncrypt, btnDecrypt, btnReadFromFile, btnClear, btnGenerateKey;
    private Label lblHeader;
    private TextArea taInput, taProcessed, taOutput;
    private TextField tfFileName, tfPassPhrase;
    private ComboBox<Charset> comboCharset;
    private LinkedList<Charset> charsetList;
    private LinkedList<Integer> randKeyList;


    public L3() {
        randKeyList = new LinkedList<>();
        lblHeader = new Label("Gamma cipher");
        lblHeader.addStyleName("h1");
        lblHeader.addStyleName("colored");

        charsetList = CharsetProvider.getInstance().getAll();
        method = new Gamma(charsetList.getLast());

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
        taInput.setHeight(30, Unit.PERCENTAGE);
        taInput.setPlaceholder("Enter message to decrypt");

        taProcessed = new TextArea();
        taProcessed.setWidth(100, Unit.PERCENTAGE);
        taProcessed.setHeight(30, Unit.PERCENTAGE);
        taProcessed.setPlaceholder("Encrypted message");

        taOutput = new TextArea();
        taOutput.setWidth(100, Unit.PERCENTAGE);
        taOutput.setHeight(30, Unit.PERCENTAGE);
        taOutput.setPlaceholder("Decrypted message");
        taOutput.setReadOnly(true);

        tfFileName = new TextField();
        tfFileName.setPlaceholder("File name");
        tfFileName.setWidth(100, Unit.PERCENTAGE);

        tfPassPhrase = new TextField();
        tfPassPhrase.setPlaceholder("Secret key");
        tfPassPhrase.setWidth(100, Unit.PERCENTAGE);
        tfPassPhrase.setReadOnly(true);
        tfPassPhrase.addContextClickListener(e -> Notification.show("asd"));

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
        btnEncrypt.addClickListener(e -> {
            int count = 0;
            while (!encrypt() & count < 100000) {
//                System.out.println(tfPassPhrase.getValue());
                setRandomKey();
                System.out.println("retrying x" + (++count));
            }
            if(count >= 50000){
                System.out.println("unfortunate...");
                Notification.show("Try using shorter string for faster valid key generation");
            }
        });

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
        options = new VerticalLayout(upperOptions, btnReadFromFile, tfFileName, btnClear);
        options.setSpacing(false);
        options.setSizeFull();

        morphChain = new VerticalLayout(options, taInput, btnEncrypt, taProcessed, btnDecrypt, taOutput);
        morphChain.setSizeFull();
        morphChain.setMargin(false);
        morphChain.setSpacing(false);

        controls = new HorizontalLayout(morphChain);
//        controls.setComponentAlignment(morphChain, Alignment.MIDDLE_LEFT);
//        controls.setExpandRatio(morphChain, 1);
        controls.setSizeFull();
        controls.setMargin(false);
        controls.setSpacing(false);

        ppOption = new HorizontalLayout(tfPassPhrase);
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

    private void changeCharset() {
        try {
            Charset charset = comboCharset.getValue();
            method.setCharset(charset);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private boolean encrypt() {
        try {
            String result;
//            setRandomKey();
            if(randKeyList.toArray().length < taInput.getValue().length()
                    || tfFileName.getValue().equals("testing")) // for testing purpose
                setRandomKey();
            result = method.encrypt(taInput.getValue(),
                    randKeyList.stream().mapToInt(Integer::intValue).toArray());
            taProcessed.setValue(result);
        } catch (IllegalArgumentException e) {
            Notification error = new Notification("Invalid input", e.getLocalizedMessage(), Notification.Type.HUMANIZED_MESSAGE);
            error.show(Page.getCurrent());
        }catch (StringIndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    private boolean decrypt() {
        try{
        String result;
        result = method.decrypt(taProcessed.getValue(),
                randKeyList.stream().mapToInt(Integer::intValue).toArray());
        taOutput.setValue(result);
        }catch (IllegalArgumentException e) {
            Notification error = new Notification("Invalid input", e.getLocalizedMessage(), Notification.Type.HUMANIZED_MESSAGE);
            error.show(Page.getCurrent());
        }catch (StringIndexOutOfBoundsException e){
            return false;
        }
        return true;
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
    }

    private void setRandomKey(){
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        randKeyList.clear();
        for (int i=0, randNum; i < getLongestTextArea(); i++){
            randNum = rand.nextInt(method.getCharset().length()-1);
            randKeyList.add(randNum);
            sb.append(randNum).append(" ");
        }
        tfPassPhrase.setValue(sb.toString());
    }

    private int getLongestTextArea(){
        return (taInput.getValue().length() > taProcessed.getValue().length() ?
                taInput.getValue().length() :
                taProcessed.getValue().length());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

}//TODO: DD84 quant cryps
