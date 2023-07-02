package com.example.socialgift2.objects;

import java.util.Date;
import java.util.List;

public class Wishlist {
    private int id;
    private Date creation_date;
    private Date end_date;
    private int id_user;
    private String name;
    private String description;
    private List<Gift> gifts;

    public Wishlist() {
    }

    public Wishlist(int id, Date creation_date, Date end_date, int id_user, String name, String description, List<Gift> gifts) {
        this.id = id;
        this.creation_date = creation_date;
        this.end_date = end_date;
        this.id_user = id_user;
        this.name = name;
        this.description = description;
        this.gifts = gifts;
    }
    public Wishlist(int id, String name, String description,  int id_user, Date created_at) {
        this.id = id;
        this.creation_date = created_at;
        this.id_user = id_user;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id=" + id +
                ", creation_date=" + creation_date +
                ", end_date=" + end_date +
                ", id_user=" + id_user +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", gifts=" + gifts +
                '}';
    }
}
