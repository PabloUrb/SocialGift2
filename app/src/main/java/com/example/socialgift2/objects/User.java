package com.example.socialgift2.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private  int id;
    private String name;
    private String lastName;
    private String password;
    private String image;
    private String email;

    public User() { }

    public User(int id, String name, String last_name, String password, String image, String email) {
        this.id = id;
        this.name = name;
        this.lastName = last_name;
        this.password = password;
        this.image = image;
        this.email = email;
    }

    public User( String name, String last_name, String password, String email, String image) {
        this.name = name;
        this.lastName = last_name;
        this.password = password;
        this.email = email;
        this.image = image;
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
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
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
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
