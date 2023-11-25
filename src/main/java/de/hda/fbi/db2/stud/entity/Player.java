package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_id_generator")
    @SequenceGenerator(name = "player_id_generator", sequenceName = "db2.player_id")
    private int playerId;
    @Column(name = "username", unique = true)
    private String username;

    Player () {};

    Player (String name) {
        this.username = name;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
