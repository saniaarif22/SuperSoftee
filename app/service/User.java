package service;

import securesocial.core.BasicProfile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    public User(BasicProfile user) {
        this.main = user;
        identities = new ArrayList<BasicProfile>();
        identities.add(user);
    }

    public BasicProfile main;
    public List<BasicProfile> identities;
}