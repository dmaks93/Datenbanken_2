package de.hda.fbi.db2.stud.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQueries({
    @NamedQuery (name = "findPlayerByName", query = "SELECT p from Player p where p.username = :name") })
public class Player {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_id_generator")
  @SequenceGenerator(name = "player_id_generator", sequenceName = "player_id")
  private int playerId;
  @Column(name = "username")
  private String username;

  public Player() {}

  public Player(String name) {
    this.username = name;
  }

  public int getPlayerId() {
    return playerId;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Player player = (Player) o;
    return playerId == player.playerId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerId);
  }
}
