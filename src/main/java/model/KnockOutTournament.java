package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/*
* KnockOutTournament: All matches are win or die. There are no groups, qualifications or rounds here.
* The scheduling algorithm is specialized:
* */
public class KnockOutTournament extends Tournament {

    public KnockOutTournament(String title){
        super(title);
    }

    public int scheduleMatch(String d, String t, String matchType) {
        Match match = new Match();
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(d, dateformatter);
        DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time= LocalTime.parse(t, timeformatter);
        match.setDate(date);
        match.setTime(time);
        match.setMatchType(matchType);
        matches.add(match);
        return match.getId();
    }





}
