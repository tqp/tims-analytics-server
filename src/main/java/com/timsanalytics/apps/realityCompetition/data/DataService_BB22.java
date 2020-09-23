package com.timsanalytics.apps.realityCompetition.data;

import com.timsanalytics.apps.realityCompetition.beans.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataService_BB22 {

    public List<Team> defineTeams() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team("key_team1", "Team Tim and Jess"));
        teams.add(new Team("key_team2", "Team Everyone"));

        return teams.stream()
                .sorted(Comparator
                        .comparing(Team::getTeamKey)
                )
                .collect(Collectors.toList());
    }

    public List<User> defineUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("key_user1", "Tim"));
        users.add(new User("key_user2", "Jess"));
        users.add(new User("key_user3", "Laura"));
        users.add(new User("key_user4", "Internet"));

        return users.stream()
                .sorted(Comparator
                        .comparing(User::getUserKey)
                )
                .collect(Collectors.toList());

    }

    public List<Round> defineRounds() {
        List<Round> rounds = new ArrayList<>();
        rounds.add(new Round(1, 15, 100.0 / 15));
        rounds.add(new Round(2, 14, 100.0 / 14));
        rounds.add(new Round(3, 13, 100.0 / 13));
        rounds.add(new Round(4, 12, 100.0 / 12));
        rounds.add(new Round(5, 11, 100.0 / 11));
        rounds.add(new Round(6, 10, 100.0 / 10));
        rounds.add(new Round(7, 9, 100.0 / 9));
        rounds.add(new Round(8, 8, 100.0 / 8));
        rounds.add(new Round(9, 7, 100.0 / 7));
        rounds.add(new Round(10, 6, 100.0 / 6));
        rounds.add(new Round(11, 5, 100.0 / 5));
        rounds.add(new Round(12, 4, 100.0 / 4));
        rounds.add(new Round(13, 3, 100.0 / 3));
        rounds.add(new Round(14, 2, 100.0 / 2));
        rounds.add(new Round(15, 1, 100.0 / 1));

        return rounds.stream()
                .sorted(Comparator
                        .comparing(Round::getRoundNumber)
                )
                .collect(Collectors.toList());
    }

    public List<Contestant> defineContestants() {
        List<Contestant> contestant = new ArrayList<>();
        contestant.add(new Contestant("key_Bayleigh", "Bayleigh"));
        contestant.add(new Contestant("key_Christmas", "Christmas"));
        contestant.add(new Contestant("key_Cody", "Cody"));
        contestant.add(new Contestant("key_Dani", "Dani"));
        contestant.add(new Contestant("key_David", "David"));
        contestant.add(new Contestant("key_DaVonne", "Da'Vonne"));
        contestant.add(new Contestant("key_Enzo", "Enzo"));
        contestant.add(new Contestant("key_Ian", "Ian"));
        contestant.add(new Contestant("key_Janelle", "Janelle"));
        contestant.add(new Contestant("key_Kaysar", "Kaysar"));
        contestant.add(new Contestant("key_Keesha", "Keesha"));
        contestant.add(new Contestant("key_Kevin", "Kevin"));
        contestant.add(new Contestant("key_Memphis", "Memphis"));
        contestant.add(new Contestant("key_NicoleA", "Nicole A."));
        contestant.add(new Contestant("key_NicoleF", "Nicole F."));
        contestant.add(new Contestant("key_Tyler", "Tyler"));

        return contestant.stream()
                .sorted(Comparator
                        .comparing(Contestant::getContestantName)
                )
                .collect(Collectors.toList());
    }

    public List<Pick> definePicks() {
        List<Pick> picks = new ArrayList<>();

        // TEAM 1: TIM AND JESS

        picks.add(new Pick("key_team1", "key_user1", 1, "key_David"));
        picks.add(new Pick("key_team1", "key_user1", 2, "key_NicoleF"));
        picks.add(new Pick("key_team1", "key_user1", 3, "key_Tyler"));
        picks.add(new Pick("key_team1", "key_user1", 4, "key_NicoleA"));
        picks.add(new Pick("key_team1", "key_user1", 5, "key_Cody"));
        picks.add(new Pick("key_team1", "key_user1", 6, "key_Christmas"));
        picks.add(new Pick("key_team1", "key_user1", 7, "key_Ian"));
        picks.add(new Pick("key_team1", "key_user1", 8, "key_Dani"));
        picks.add(new Pick("key_team1", "key_user1", 9, "key_Memphis"));
        picks.add(new Pick("key_team1", "key_user1", 10, "key_Kevin"));
        picks.add(new Pick("key_team1", "key_user1", 11, "key_Enzo"));
        picks.add(new Pick("key_team1", "key_user1", 12, "key_Kaysar"));
        picks.add(new Pick("key_team1", "key_user1", 13, "key_Bayleigh"));
        picks.add(new Pick("key_team1", "key_user1", 14, "key_Keesha"));
        picks.add(new Pick("key_team1", "key_user1", 15, "key_DaVonne"));
        picks.add(new Pick("key_team1", "key_user1", 16, "key_Janelle"));

        picks.add(new Pick("key_team1", "key_user2", 1, "key_Tyler"));
        picks.add(new Pick("key_team1", "key_user2", 2, "key_Christmas"));
        picks.add(new Pick("key_team1", "key_user2", 3, "key_David"));
        picks.add(new Pick("key_team1", "key_user2", 4, "key_Kevin"));
        picks.add(new Pick("key_team1", "key_user2", 5, "key_Memphis"));
        picks.add(new Pick("key_team1", "key_user2", 6, "key_NicoleF"));
        picks.add(new Pick("key_team1", "key_user2", 7, "key_Cody"));
        picks.add(new Pick("key_team1", "key_user2", 8, "key_Dani"));
        picks.add(new Pick("key_team1", "key_user2", 9, "key_NicoleA"));
        picks.add(new Pick("key_team1", "key_user2", 10, "key_DaVonne"));
        picks.add(new Pick("key_team1", "key_user2", 11, "key_Keesha"));
        picks.add(new Pick("key_team1", "key_user2", 12, "key_Enzo"));
        picks.add(new Pick("key_team1", "key_user2", 13, "key_Kaysar"));
        picks.add(new Pick("key_team1", "key_user2", 14, "key_Janelle"));
        picks.add(new Pick("key_team1", "key_user2", 15, "key_Ian"));
        picks.add(new Pick("key_team1", "key_user2", 16, "key_Bayleigh"));

        // TEAM 2: EVERYONE

        picks.add(new Pick("key_team2", "key_user1", 1, "key_David"));
        picks.add(new Pick("key_team2", "key_user1", 2, "key_NicoleF"));
        picks.add(new Pick("key_team2", "key_user1", 3, "key_Tyler"));
        picks.add(new Pick("key_team2", "key_user1", 4, "key_NicoleA"));
        picks.add(new Pick("key_team2", "key_user1", 5, "key_Cody"));
        picks.add(new Pick("key_team2", "key_user1", 6, "key_Christmas"));
        picks.add(new Pick("key_team2", "key_user1", 7, "key_Ian"));
        picks.add(new Pick("key_team2", "key_user1", 8, "key_Dani"));
        picks.add(new Pick("key_team2", "key_user1", 9, "key_Memphis"));
        picks.add(new Pick("key_team2", "key_user1", 10, "key_Kevin"));
        picks.add(new Pick("key_team2", "key_user1", 11, "key_Enzo"));
        picks.add(new Pick("key_team2", "key_user1", 12, "key_Kaysar"));
        picks.add(new Pick("key_team2", "key_user1", 13, "key_Bayleigh"));
        picks.add(new Pick("key_team2", "key_user1", 14, "key_Keesha"));
        picks.add(new Pick("key_team2", "key_user1", 15, "key_DaVonne"));
        picks.add(new Pick("key_team2", "key_user1", 16, "key_Janelle"));

        picks.add(new Pick("key_team2", "key_user2", 1, "key_Tyler"));
        picks.add(new Pick("key_team2", "key_user2", 2, "key_Christmas"));
        picks.add(new Pick("key_team2", "key_user2", 3, "key_David"));
        picks.add(new Pick("key_team2", "key_user2", 4, "key_Kevin"));
        picks.add(new Pick("key_team2", "key_user2", 5, "key_Memphis"));
        picks.add(new Pick("key_team2", "key_user2", 6, "key_NicoleF"));
        picks.add(new Pick("key_team2", "key_user2", 7, "key_Cody"));
        picks.add(new Pick("key_team2", "key_user2", 8, "key_Dani"));
        picks.add(new Pick("key_team2", "key_user2", 9, "key_NicoleA"));
        picks.add(new Pick("key_team2", "key_user2", 10, "key_DaVonne"));
        picks.add(new Pick("key_team2", "key_user2", 11, "key_Keesha"));
        picks.add(new Pick("key_team2", "key_user2", 12, "key_Enzo"));
        picks.add(new Pick("key_team2", "key_user2", 13, "key_Kaysar"));
        picks.add(new Pick("key_team2", "key_user2", 14, "key_Janelle"));
        picks.add(new Pick("key_team2", "key_user2", 15, "key_Ian"));
        picks.add(new Pick("key_team2", "key_user2", 16, "key_Bayleigh"));

        picks.add(new Pick("key_team2", "key_user3", 1, "key_Cody"));
        picks.add(new Pick("key_team2", "key_user3", 2, "key_Memphis"));
        picks.add(new Pick("key_team2", "key_user3", 3, "key_Christmas"));
        picks.add(new Pick("key_team2", "key_user3", 4, "key_Enzo"));
        picks.add(new Pick("key_team2", "key_user3", 5, "key_Janelle"));
        picks.add(new Pick("key_team2", "key_user3", 6, "key_Bayleigh"));
        picks.add(new Pick("key_team2", "key_user3", 7, "key_Tyler"));
        picks.add(new Pick("key_team2", "key_user3", 8, "key_Dani"));
        picks.add(new Pick("key_team2", "key_user3", 9, "key_NicoleF"));
        picks.add(new Pick("key_team2", "key_user3", 10, "key_Ian"));
        picks.add(new Pick("key_team2", "key_user3", 11, "key_DaVonne"));
        picks.add(new Pick("key_team2", "key_user3", 12, "key_Kaysar"));
        picks.add(new Pick("key_team2", "key_user3", 13, "key_NicoleA"));
        picks.add(new Pick("key_team2", "key_user3", 14, "key_Kevin"));
        picks.add(new Pick("key_team2", "key_user3", 15, "key_David"));
        picks.add(new Pick("key_team2", "key_user3", 16, "key_Keesha"));

        picks.add(new Pick("key_team2", "key_user4", 1, "key_Cody"));
        picks.add(new Pick("key_team2", "key_user4", 2, "key_Tyler"));
        picks.add(new Pick("key_team2", "key_user4", 3, "key_Dani"));
        picks.add(new Pick("key_team2", "key_user4", 4, "key_Enzo"));
        picks.add(new Pick("key_team2", "key_user4", 5, "key_Ian"));
        picks.add(new Pick("key_team2", "key_user4", 6, "key_Janelle"));
        picks.add(new Pick("key_team2", "key_user4", 7, "key_Memphis"));
        picks.add(new Pick("key_team2", "key_user4", 8, "key_NicoleF"));
        picks.add(new Pick("key_team2", "key_user4", 9, "key_Kaysar"));
        picks.add(new Pick("key_team2", "key_user4", 10, "key_DaVonne"));
        picks.add(new Pick("key_team2", "key_user4", 11, "key_Christmas"));
        picks.add(new Pick("key_team2", "key_user4", 12, "key_David"));
        picks.add(new Pick("key_team2", "key_user4", 13, "key_Kevin"));
        picks.add(new Pick("key_team2", "key_user4", 14, "key_NicoleA"));
        picks.add(new Pick("key_team2", "key_user4", 15, "key_Bayleigh"));
        picks.add(new Pick("key_team2", "key_user4", 16, "key_Keesha"));

        return picks.stream()
                .sorted(Comparator
                        .comparing(Pick::getTeamKey)
                        .thenComparing(Pick::getUserKey)
                        .thenComparing(Pick::getContestantKey)
                )
                .collect(Collectors.toList());
    }

    public List<Result> defineResults() {
        List<Result> results = new ArrayList<>();

        // Round 1: Keesha
        results.add(new Result(1, 1, "key_Bayleigh"));
        results.add(new Result(1, 2, "key_Christmas"));
        results.add(new Result(1, 3, "key_Cody"));
        results.add(new Result(1, 4, "key_Dani"));
        results.add(new Result(1, 5, "key_David"));
        results.add(new Result(1, 6, "key_DaVonne"));
        results.add(new Result(1, 7, "key_Enzo"));
        results.add(new Result(1, 8, "key_Ian"));
        results.add(new Result(1, 9, "key_Janelle"));
        results.add(new Result(1, 10, "key_Kaysar"));
        results.add(new Result(1, 11, "key_Kevin"));
        results.add(new Result(1, 12, "key_Memphis"));
        results.add(new Result(1, 13, "key_NicoleA"));
        results.add(new Result(1, 14, "key_NicoleF"));
        results.add(new Result(1, 15, "key_Tyler"));

        // Round 2: Nicole A.
        results.add(new Result(2, 1, "key_Bayleigh"));
        results.add(new Result(2, 2, "key_Christmas"));
        results.add(new Result(2, 3, "key_Cody"));
        results.add(new Result(2, 4, "key_Dani"));
        results.add(new Result(2, 5, "key_David"));
        results.add(new Result(2, 6, "key_DaVonne"));
        results.add(new Result(2, 7, "key_Enzo"));
        results.add(new Result(2, 8, "key_Ian"));
        results.add(new Result(2, 9, "key_Janelle"));
        results.add(new Result(2, 10, "key_Kaysar"));
        results.add(new Result(2, 11, "key_Kevin"));
        results.add(new Result(2, 12, "key_Memphis"));
        results.add(new Result(2, 13, "key_NicoleF"));
        results.add(new Result(2, 14, "key_Tyler"));

        // Round 3: Janelle
        results.add(new Result(3, 1, "key_Bayleigh"));
        results.add(new Result(3, 2, "key_Christmas"));
        results.add(new Result(3, 3, "key_Cody"));
        results.add(new Result(3, 4, "key_Dani"));
        results.add(new Result(3, 5, "key_David"));
        results.add(new Result(3, 6, "key_DaVonne"));
        results.add(new Result(3, 7, "key_Enzo"));
        results.add(new Result(3, 8, "key_Ian"));
        results.add(new Result(3, 9, "key_Kaysar"));
        results.add(new Result(3, 10, "key_Kevin"));
        results.add(new Result(3, 11, "key_Memphis"));
        results.add(new Result(3, 12, "key_NicoleF"));
        results.add(new Result(3, 13, "key_Tyler"));

        // Round 4: Kaysar
        results.add(new Result(4, 1, "key_Bayleigh"));
        results.add(new Result(4, 2, "key_Christmas"));
        results.add(new Result(4, 3, "key_Cody"));
        results.add(new Result(4, 4, "key_Dani"));
        results.add(new Result(4, 5, "key_David"));
        results.add(new Result(4, 6, "key_DaVonne"));
        results.add(new Result(4, 7, "key_Enzo"));
        results.add(new Result(4, 8, "key_Ian"));
        results.add(new Result(4, 9, "key_Kevin"));
        results.add(new Result(4, 10, "key_Memphis"));
        results.add(new Result(4, 11, "key_NicoleF"));
        results.add(new Result(4, 12, "key_Tyler"));

        // Round 5: Bayleigh
        results.add(new Result(5, 1, "key_Christmas"));
        results.add(new Result(5, 2, "key_Cody"));
        results.add(new Result(5, 3, "key_Dani"));
        results.add(new Result(5, 4, "key_David"));
        results.add(new Result(5, 5, "key_DaVonne"));
        results.add(new Result(5, 6, "key_Enzo"));
        results.add(new Result(5, 7, "key_Ian"));
        results.add(new Result(5, 8, "key_Kevin"));
        results.add(new Result(5, 9, "key_Memphis"));
        results.add(new Result(5, 10, "key_NicoleF"));
        results.add(new Result(5, 11, "key_Tyler"));

        // Round 6: Ian
        results.add(new Result(6, 1, "key_Christmas"));
        results.add(new Result(6, 2, "key_Cody"));
        results.add(new Result(6, 3, "key_Dani"));
        results.add(new Result(6, 4, "key_David"));
        results.add(new Result(6, 5, "key_DaVonne"));
        results.add(new Result(6, 6, "key_Enzo"));
        results.add(new Result(6, 7, "key_Kevin"));
        results.add(new Result(6, 8, "key_Memphis"));
        results.add(new Result(6, 9, "key_NicoleF"));
        results.add(new Result(6, 10, "key_Tyler"));

        return results.stream()
                .sorted(Comparator
                        .comparing(Result::getRoundNumber)
                        .thenComparing(Result::getCallOutOrder)
                )
                .collect(Collectors.toList());
    }

}
