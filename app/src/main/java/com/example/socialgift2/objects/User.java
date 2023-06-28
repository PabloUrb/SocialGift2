package com.example.socialgift2.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private  int id;
    private String name;
    private String last_name;
    private String password;
    private String image;
    private String email;

    public User() { }

    public User(int id, String name, String last_name, String password, String image, String email) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.password = password;
        this.image = image;
        this.email = email;
    }

    public User(int id, String name, String last_name, String image, String email) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.image = image;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String toJSON() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("name", getName());
            jsonObject.put("last_name", getLast_name());
            jsonObject.put("email", getEmail());
            jsonObject.put("password", getPassword());
            jsonObject.put("image", getImage());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
