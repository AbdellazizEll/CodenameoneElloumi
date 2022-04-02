/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.pheonixui;

import com.mycompany.services.serviceUtilisateur;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.codename1.uikit.pheonixui.SignInForm;

public class UValidationUser extends com.codename1.ui.Form {

    public UValidationUser() {
        this(com.codename1.ui.util.Resources.getGlobalResources());
    }

    public UValidationUser(com.codename1.ui.util.Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        getTitleArea().setUIID("Container");
        getToolbar().setUIID("Container");
        getToolbar().getTitleComponent().setUIID("SigninTitle");
        FontImage mat = FontImage.createMaterial(FontImage.MATERIAL_CLOSE, "SigninTitle", 3.5f);
        getToolbar().addCommandToLeftBar("", mat, e -> new WalkthruForm().show());
        getContentPane().setUIID("SignInForm");
                FontImage login = FontImage.createMaterial(FontImage.MATERIAL_LOGIN, "SigninTitle", 3.5f);
        getToolbar().addCommandToRightBar("", login, e -> new SignInForm().show());
    }

    protected com.codename1.ui.Container gui_Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
    protected com.codename1.ui.Label gui_Label_1 = new com.codename1.ui.Label();
    protected com.codename1.ui.Container gui_Component_Group_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
    protected TextField textfieldToken = new TextField("", "Token");
    protected com.codename1.ui.Button btnConnecter = new com.codename1.ui.Button("Valider");

    private void initGuiBuilderComponents(Resources resourceObjectInstance) {

        setLayout(new com.codename1.ui.layouts.BorderLayout());

        setTitle("Validation");
        setName("SignInForm");
        gui_Container_1.setScrollableY(true);

        gui_Container_1.setName("Container_1");

        addComponent(com.codename1.ui.layouts.BorderLayout.CENTER, gui_Container_1);
        gui_Label_1.setUIID("CenterLabel");

        gui_Label_1.setName("Label_1");
        gui_Label_1.setIcon(resourceObjectInstance.getImage("profile_image.png"));

        textfieldToken.setUIID("TextField");

      

        gui_Container_1.addComponent(gui_Label_1);
        gui_Container_1.addComponent(gui_Component_Group_1);

        gui_Component_Group_1.addComponent(textfieldToken);
        gui_Container_1.addComponent(btnConnecter);
        btnConnecter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                serviceUtilisateur dao=serviceUtilisateur.getInstance();
                if(dao.Valider(textfieldToken.getText())!=null){
                    Dialog.show("Validation", "Félicitation votre compte a été bien validé, vous pouvez vous connecter", new Command("OK"));
                    new SignInForm().show();
                }else{
                      Dialog.show("Validation", "Code non valider", new Command("OK"));
                }
            }
        });

    }
}
