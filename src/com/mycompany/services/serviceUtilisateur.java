/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.mycompany.entities.User;

import com.mycompany.utils.Statics;
import com.codename1.ui.events.ActionListener;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class serviceUtilisateur {

    public User user = null;
    public List<User> us;
    public String result = "";
    public static serviceUtilisateur instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private serviceUtilisateur() {
        req = new ConnectionRequest();
    }

    public static serviceUtilisateur getInstance() {
        if (instance == null) {
            instance = new serviceUtilisateur();
        }
        return instance;
    }
    boolean retour = false;

    

    public User ConnexionMobile(String username, String password) {
        String url = Statics.BASE_URL+"/login_mobile/" + username + "/"+password;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                user = parseLogin(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return user;
    }

   


    public boolean Inscrire(User u) {
       

        String url = Statics.BASE_URL + "/inscrireMobile?email=" + u.getEmail()+ "&firstName=" + u.getFirstName()+ "&password=" + u.getPassword()+"&lastName=" + u.getLastName()+"&picture=" + u.getPicture(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.*/
               

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public boolean Update(User u) {
       

        String url = Statics.BASE_URL + "/updateUser?email=" + u.getEmail()+ "&firstName=" + u.getFirstName()+ "&password=" + u.getPassword()+"&LastName=" + u.getLastName()+"&picture=" + u.getPicture(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                */

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public User Valider(String token) {
       

        String url = Statics.BASE_URL + "/valider/"+token;
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                user = parseLogin(new String(req.getResponseData()));
                req.removeResponseListener(this);
            
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                */

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return user;
    }
    
    
    

    public boolean SendPdf(String mail) {
       

        String url = Statics.BASE_URL + "/sendpdf/" + mail;
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public boolean Remove(String mail) {
       

        String url = Statics.BASE_URL + "/removeUser/" + mail; //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    
        public List<User> AllUsers() {
        String url = Statics.BASE_URL + "/getAllUsers";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                us = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return us;
    }
   
    
    public User GetUserByEmail(String email) {
        String url = Statics.BASE_URL + "/getUserByEmail/" +email;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                user = parseLogin(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return user;
    }
    
    
     public User parseLogin(String json) {
        

        try {
            JSONParser j = new JSONParser();
            Map<String, Object> usermap = j.parseJSON(new CharArrayReader(json.toCharArray()));
           

            if (usermap==null) {
                return null;
            }
            float id = Float.parseFloat(usermap.get("id").toString());
              
                    User user=new User();
                    user.setId((int) id);
                   
                    user.setFirstName(usermap.get("firstName").toString());
                     user.setLastName(usermap.get("lastName").toString());
                    if(usermap.get("role").toString().contains("MANAGER"))
                        user.setRoles("manager");
                    else
                        user.setRoles("user");
                    user.setEmail(usermap.get("email").toString());
                    user.setPicture(Statics.uploads+"images/products/"+ usermap.get("image").toString());

                return user;   
                
            
        } catch (Exception ex) {
           return null; 
        }
       

    }
     
     public List<User> parseUsers(String json) {
        ArrayList<User> listUser = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

            if (list.size() <= 0) {
                return listUser;
            }
            for (Map<String, Object> obj : list) {
               
                    User user = new User();

                    float id = Float.parseFloat(obj.get("id").toString());
                    System.out.println("id " + id);
                    user.setId((int) id);
                    
                    
                     user.setFirstName(obj.get("firstName").toString());
                    if(obj.get("role").toString().contains("MANAGER"))
                        user.setRoles("manager");
                    else
                        user.setRoles("user");
                    user.setEmail(obj.get("email").toString());
                    
                    
                    listUser.add(user);
               
            }
        } catch (Exception ex) {
            
        }
        return listUser;

    }
}
