package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_id_generator")
  @SequenceGenerator(name = "game_id_generator", sequenceName = "db2.game_id")
  private int gameId;
  @Temporal(TIMESTAMP)
  private Date startTime;
  @Temporal(TIMESTAMP)
  private Date endTime;
  @ManyToOne
  private Player player;
  @OneToMany(mappedBy = "game")
  private List<GivenAnswer> gameQuestions;

  public Game() {};

  public Game(Player player, List<Question> questions) {
    this.player = player;
    this.gameQuestions = new ArrayList<GivenAnswer>();
    for (Question q: questions) {
      this.gameQuestions.add(new GivenAnswer(this, q));
    }
  }

  public List<GivenAnswer> getQuestionList() {
    return gameQuestions;
  }

  public Player getPlayer() {
    return player;
  }

  @Override
  public int hashCode() {
    return gameId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Game game = (Game) o;
    return gameId == game.gameId && Objects.equals(startTime, game.startTime)
        && Objects.equals(endTime, game.endTime) && Objects.equals(player,
        game.player) && Objects.equals(gameQuestions, game.gameQuestions);
  }
}
