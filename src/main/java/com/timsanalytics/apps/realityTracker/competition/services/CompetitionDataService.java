package com.timsanalytics.apps.realityTracker.competition.services;

import com.timsanalytics.apps.realityTracker.competition.beans.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompetitionDataService {

    public List<CompetitionTeam> getTeams() {
        List<CompetitionTeam> teams = new ArrayList<>();
        teams.add(new CompetitionTeam("key_team1", "Team 1"));
        return teams;
    }

    public List<CompetitionUser> getUsers() {
        List<CompetitionUser> competitionUserList = new ArrayList<>();
        competitionUserList.add(new CompetitionUser("key_user1", "Tim"));
        competitionUserList.add(new CompetitionUser("key_user2", "Jess"));
        competitionUserList.add(new CompetitionUser("key_user3", "Internet"));
        competitionUserList.add(new CompetitionUser("key_user4", "Laura"));
        return competitionUserList;
    }

    public List<CompetitionRound> getRounds() {
        List<CompetitionRound> rounds = new ArrayList<>();
        rounds.add(new CompetitionRound(1, 15, 1));
        rounds.add(new CompetitionRound(2, 14, 2));
        rounds.add(new CompetitionRound(3, 13, 3));
        rounds.add(new CompetitionRound(4, 12, 4));
        rounds.add(new CompetitionRound(5, 11, 5));
        rounds.add(new CompetitionRound(6, 10, 6));
        rounds.add(new CompetitionRound(7, 9, 7));
        rounds.add(new CompetitionRound(8, 8, 8));
        rounds.add(new CompetitionRound(9, 7, 9));
        rounds.add(new CompetitionRound(10, 6, 10));
        rounds.add(new CompetitionRound(11, 5, 11));
        rounds.add(new CompetitionRound(12, 4, 12));
        rounds.add(new CompetitionRound(13, 3, 13));
        rounds.add(new CompetitionRound(14, 2, 14));
        rounds.add(new CompetitionRound(15, 1, 15));

        return rounds;
    }

    public List<CompetitionPick> getPicks() {
        List<CompetitionPick> picks = new ArrayList<>();

        picks.add(new CompetitionPick("key_team1", "key_user1", 1, "key_David"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 2, "key_NicoleF"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 3, "key_Tyler"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 4, "key_NicoleA"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 5, "key_Cody"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 6, "key_Christmas"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 7, "key_Ian"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 8, "key_Dani"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 9, "key_Memphis"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 10, "key_Kevin"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 11, "key_Enzo"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 12, "key_Kaysar"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 13, "key_Bayleigh"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 14, "key_Keesha"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 15, "key_DaVonne"));
        picks.add(new CompetitionPick("key_team1", "key_user1", 16, "key_Janelle"));

        picks.add(new CompetitionPick("key_team1", "key_user2", 1, "key_Tyler"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 2, "key_Christmas"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 3, "key_David"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 4, "key_Kevin"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 5, "key_Memphis"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 6, "key_NicoleF"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 7, "key_Cody"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 8, "key_Dani"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 9, "key_NicoleA"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 10, "key_DaVonne"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 11, "key_Keesha"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 12, "key_Enzo"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 13, "key_Kaysar"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 14, "key_Janelle"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 15, "key_Ian"));
        picks.add(new CompetitionPick("key_team1", "key_user2", 16, "key_Bayleigh"));

        picks.add(new CompetitionPick("key_team1", "key_user3", 1, "key_Tyler"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 2, "key_Christmas"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 3, "key_David"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 4, "key_Kevin"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 5, "key_Memphis"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 6, "key_NicoleF"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 7, "key_Cody"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 8, "key_Dani"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 9, "key_NicoleA"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 10, "key_DaVonne"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 11, "key_Keesha"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 12, "key_Enzo"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 13, "key_Kaysar"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 14, "key_Janelle"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 15, "key_Ian"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 16, "key_Bayleigh"));

        picks.add(new CompetitionPick("key_team1", "key_user3", 1, "key_Cody"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 2, "key_Memphis"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 3, "key_Christmas"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 4, "key_Enzo"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 5, "key_Janelle"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 6, "key_Bayleigh"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 7, "key_Tyler"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 8, "key_Dani"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 9, "key_NicoleF"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 10, "key_Ian"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 11, "key_DaVonne"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 12, "key_Kaysar"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 13, "key_NicoleA"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 14, "key_Kevin"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 15, "key_David"));
        picks.add(new CompetitionPick("key_team1", "key_user3", 16, "key_Keesha"));

        return picks;
    }

    public List<CompetitionContestant> getContestants() {
        List<CompetitionContestant> competitionContestant = new ArrayList<>();
        competitionContestant.add(new CompetitionContestant("key_Tyler", "Tyler"));
        competitionContestant.add(new CompetitionContestant("key_Christmas", "Christmas"));
        competitionContestant.add(new CompetitionContestant("key_David", "David"));
        competitionContestant.add(new CompetitionContestant("key_Kevin", "Kevin"));
        competitionContestant.add(new CompetitionContestant("key_Memphis", "Memphis"));
        competitionContestant.add(new CompetitionContestant("key_NicoleF", "Nicole F."));
        competitionContestant.add(new CompetitionContestant("key_Cody", "Cody"));
        competitionContestant.add(new CompetitionContestant("key_Dani", "Dani"));
        competitionContestant.add(new CompetitionContestant("key_NicoleA", "Nicole A."));
        competitionContestant.add(new CompetitionContestant("key_DaVonne", "Da'Vonne"));
        competitionContestant.add(new CompetitionContestant("key_Keesha", "Keesha"));
        competitionContestant.add(new CompetitionContestant("key_Enzo", "Enzo"));
        competitionContestant.add(new CompetitionContestant("key_Kaysar", "Kaysar"));
        competitionContestant.add(new CompetitionContestant("key_Janelle", "Janelle"));
        competitionContestant.add(new CompetitionContestant("key_Ian", "Ian"));
        competitionContestant.add(new CompetitionContestant("key_Bayleigh", "Bayleigh"));
        return competitionContestant;
    }

    public List<CompetitionResult> getResults() {
        List<CompetitionResult> results = new ArrayList<>();

        // Round 1
        results.add(new CompetitionResult(1, "key_Tyler", 1));
        results.add(new CompetitionResult(1, "key_Christmas", 2));
        results.add(new CompetitionResult(1, "key_David", 3));
        results.add(new CompetitionResult(1, "key_Kevin", 4));
        results.add(new CompetitionResult(1, "key_Memphis", 5));
        results.add(new CompetitionResult(1, "key_NicoleF", 6));
        results.add(new CompetitionResult(1, "key_Cody", 7));
        results.add(new CompetitionResult(1, "key_Dani", 8));
        results.add(new CompetitionResult(1, "key_NicoleA", 9));
        results.add(new CompetitionResult(1, "key_DaVonne", 10));
        results.add(new CompetitionResult(1, "key_Enzo", 11));
        results.add(new CompetitionResult(1, "key_Kaysar", 12));
        results.add(new CompetitionResult(1, "key_Ian", 13));
        results.add(new CompetitionResult(1, "key_Bayleigh", 14));

        // Round 2
        results.add(new CompetitionResult(2, "key_Tyler", 1));
        results.add(new CompetitionResult(2, "key_Christmas", 2));
        results.add(new CompetitionResult(2, "key_David", 3));
        results.add(new CompetitionResult(2, "key_Kevin", 4));
        results.add(new CompetitionResult(2, "key_Memphis", 5));
        results.add(new CompetitionResult(2, "key_NicoleF", 6));
        results.add(new CompetitionResult(2, "key_Cody", 7));
        results.add(new CompetitionResult(2, "key_Dani", 8));
        results.add(new CompetitionResult(2, "key_DaVonne", 9));
        results.add(new CompetitionResult(2, "key_Enzo", 10));
        results.add(new CompetitionResult(2, "key_Kaysar", 11));
        results.add(new CompetitionResult(2, "key_Ian", 12));
        results.add(new CompetitionResult(2, "key_Bayleigh", 13));

        return results;
    }


}
