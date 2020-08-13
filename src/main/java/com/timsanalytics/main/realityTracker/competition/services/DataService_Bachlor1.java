package com.timsanalytics.main.realityTracker.competition.services;

import com.timsanalytics.main.realityTracker.competition.beans.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService_Bachlor1 {

    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team("key_team1", "Team 1"));
        return teams;
    }

    public List<Player> getUsers() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("key_user1", "Tim"));
        players.add(new Player("key_user2", "Jess"));
        players.add(new Player("key_user3", "Internet"));
        return players;
    }

    public List<Round> getRounds() {
        List<Round> rounds = new ArrayList<>();
        rounds.add(new Round(1, 30, 0));
        rounds.add(new Round(2, 18, 2));
        rounds.add(new Round(3, 15, 3));
        rounds.add(new Round(4, 12, 4));
        rounds.add(new Round(5, 9, 5));
        rounds.add(new Round(6, 6, 6));
        rounds.add(new Round(7, 4, 10));
        rounds.add(new Round(8, 3, 15));
        rounds.add(new Round(9, 2, 20));
        rounds.add(new Round(10, 1, 30));
        return rounds;
    }

    public List<Pick> getPicks() {
        List<Pick> picks = new ArrayList<>();

        picks.add(new Pick("key_group1", "key_user1", 1, "key_Madison"));
        picks.add(new Pick("key_group1", "key_user1", 2, "key_Hannah Anne"));
        picks.add(new Pick("key_group1", "key_user1", 3, "key_Savannah"));
        picks.add(new Pick("key_group1", "key_user1", 4, "key_Victoria P."));
        picks.add(new Pick("key_group1", "key_user1", 5, "key_Kelsey"));
        picks.add(new Pick("key_group1", "key_user1", 6, "key_Mykenna"));
        picks.add(new Pick("key_group1", "key_user1", 7, "key_Lexi"));
        picks.add(new Pick("key_group1", "key_user1", 8, "key_Kelley"));
        picks.add(new Pick("key_group1", "key_user1", 9, "key_Natasha"));
        picks.add(new Pick("key_group1", "key_user1", 10, "key_Payton"));
        picks.add(new Pick("key_group1", "key_user1", 11, "key_Sydney"));
        picks.add(new Pick("key_group1", "key_user1", 12, "key_Alayah"));
        picks.add(new Pick("key_group1", "key_user1", 13, "key_Sarah"));
        picks.add(new Pick("key_group1", "key_user1", 14, "key_Courtney"));
        picks.add(new Pick("key_group1", "key_user1", 15, "key_Tammy"));
        picks.add(new Pick("key_group1", "key_user1", 16, "key_Victoria F."));
        picks.add(new Pick("key_group1", "key_user1", 17, "key_Alexa"));
        picks.add(new Pick("key_group1", "key_user1", 18, "key_Jasmine"));
        picks.add(new Pick("key_group1", "key_user1", 19, "key_Lauren"));
        picks.add(new Pick("key_group1", "key_user1", 20, "key_Deandra"));
        picks.add(new Pick("key_group1", "key_user1", 21, "key_Kiarra"));
        picks.add(new Pick("key_group1", "key_user1", 22, "key_Shiann"));

        picks.add(new Pick("key_group1", "key_user2", 1, "key_Savannah"));
        picks.add(new Pick("key_group1", "key_user2", 2, "key_Hannah Anne"));
        picks.add(new Pick("key_group1", "key_user2", 3, "key_Sydney"));
        picks.add(new Pick("key_group1", "key_user2", 4, "key_Victoria P."));
        picks.add(new Pick("key_group1", "key_user2", 5, "key_Kelsey"));
        picks.add(new Pick("key_group1", "key_user2", 6, "key_Mykenna"));
        picks.add(new Pick("key_group1", "key_user2", 7, "key_Alayah"));
        picks.add(new Pick("key_group1", "key_user2", 8, "key_Alexa"));
        picks.add(new Pick("key_group1", "key_user2", 9, "key_Victoria F."));
        picks.add(new Pick("key_group1", "key_user2", 10, "key_Kelley"));
        picks.add(new Pick("key_group1", "key_user2", 11, "key_Lexi"));
        picks.add(new Pick("key_group1", "key_user2", 12, "key_Natasha"));
        picks.add(new Pick("key_group1", "key_user2", 13, "key_Courtney"));
        picks.add(new Pick("key_group1", "key_user2", 14, "key_Lauren"));
        picks.add(new Pick("key_group1", "key_user2", 15, "key_Jasmine"));
        picks.add(new Pick("key_group1", "key_user2", 16, "key_Deandra"));
        picks.add(new Pick("key_group1", "key_user2", 17, "key_Madison"));
        picks.add(new Pick("key_group1", "key_user2", 18, "key_Payton"));
        picks.add(new Pick("key_group1", "key_user2", 19, "key_Sarah"));
        picks.add(new Pick("key_group1", "key_user2", 20, "key_Tammy"));
        picks.add(new Pick("key_group1", "key_user2", 21, "key_Kiarra"));
        picks.add(new Pick("key_group1", "key_user2", 22, "key_Shiann"));

        picks.add(new Pick("key_group1", "key_user3", 1, "key_Hannah Anne"));
        picks.add(new Pick("key_group1", "key_user3", 2, "key_Madison"));
        picks.add(new Pick("key_group1", "key_user3", 3, "key_Kelley"));
        picks.add(new Pick("key_group1", "key_user3", 4, "key_Victoria P."));
        picks.add(new Pick("key_group1", "key_user3", 5, "key_Alayah"));
        picks.add(new Pick("key_group1", "key_user3", 6, "key_Mykenna"));
        picks.add(new Pick("key_group1", "key_user3", 7, "key_Tammy"));
        picks.add(new Pick("key_group1", "key_user3", 8, "key_Lexi"));
        picks.add(new Pick("key_group1", "key_user3", 9, "key_Sydney"));
        picks.add(new Pick("key_group1", "key_user3", 10, "key_Alexa"));
        picks.add(new Pick("key_group1", "key_user3", 11, "key_Kiarra"));
        picks.add(new Pick("key_group1", "key_user3", 12, "key_Natasha"));
        picks.add(new Pick("key_group1", "key_user3", 13, "key_Deandra"));
        picks.add(new Pick("key_group1", "key_user3", 14, "key_Jasmine"));
        picks.add(new Pick("key_group1", "key_user3", 15, "key_Courtney"));
        picks.add(new Pick("key_group1", "key_user3", 16, "key_Lauren"));
        picks.add(new Pick("key_group1", "key_user3", 17, "key_Sarah"));
        picks.add(new Pick("key_group1", "key_user3", 18, "key_Kelsey"));
        picks.add(new Pick("key_group1", "key_user3", 19, "key_Savannah"));
        picks.add(new Pick("key_group1", "key_user3", 20, "key_Shiann"));
        picks.add(new Pick("key_group1", "key_user3", 21, "key_Victoria F."));
        picks.add(new Pick("key_group1", "key_user3", 22, "key_Payton"));

        return picks;
    }

    public List<CompetitionContestant> getContestants() {
        List<CompetitionContestant> competitionContestant = new ArrayList<>();
        competitionContestant.add(new CompetitionContestant("key_Alayah", "Alayah"));
        competitionContestant.add(new CompetitionContestant("key_Alexa", "Alexa"));
        competitionContestant.add(new CompetitionContestant("key_Avonlea", "Avonlea"));
        competitionContestant.add(new CompetitionContestant("key_Courtney", "Courtney"));
        competitionContestant.add(new CompetitionContestant("key_Deandra", "Deandra"));
        competitionContestant.add(new CompetitionContestant("key_Eunice", "Eunice"));
        competitionContestant.add(new CompetitionContestant("key_Hannah Anne", "Hannah Anne"));
        competitionContestant.add(new CompetitionContestant("key_Jade", "Jade"));
        competitionContestant.add(new CompetitionContestant("key_Jasmine", "Jasmine"));
        competitionContestant.add(new CompetitionContestant("key_Jenna", "Jenna"));
        competitionContestant.add(new CompetitionContestant("key_Katrina", "Katrina"));
        competitionContestant.add(new CompetitionContestant("key_Kelley", "Kelley"));
        competitionContestant.add(new CompetitionContestant("key_Kelsey", "Kelsey"));
        competitionContestant.add(new CompetitionContestant("key_Kiarra", "Kiarra"));
        competitionContestant.add(new CompetitionContestant("key_Kylie", "Kylie"));
        competitionContestant.add(new CompetitionContestant("key_Lauren", "Lauren"));
        competitionContestant.add(new CompetitionContestant("key_Lexi", "Lexi"));
        competitionContestant.add(new CompetitionContestant("key_Madison", "Madison"));
        competitionContestant.add(new CompetitionContestant("key_Maurissa", "Maurissa"));
        competitionContestant.add(new CompetitionContestant("key_Megan", "Megan"));
        competitionContestant.add(new CompetitionContestant("key_Mykenna", "Mykenna"));
        competitionContestant.add(new CompetitionContestant("key_Natasha", "Natasha"));
        competitionContestant.add(new CompetitionContestant("key_Payton", "Payton"));
        competitionContestant.add(new CompetitionContestant("key_Sarah", "Sarah"));
        competitionContestant.add(new CompetitionContestant("key_Savannah", "Savannah"));
        competitionContestant.add(new CompetitionContestant("key_Shiann", "Shiann"));
        competitionContestant.add(new CompetitionContestant("key_Sydney", "Sydney"));
        competitionContestant.add(new CompetitionContestant("key_Tammy", "Tammy"));
        competitionContestant.add(new CompetitionContestant("key_Victoria F.", "Victoria F."));
        competitionContestant.add(new CompetitionContestant("key_Victoria P.", "Victoria P."));
        return competitionContestant;
    }

    public List<Result> getResults() {
        List<Result> results = new ArrayList<>();

        // Round 1
        results.add(new Result(1, "key_Hannah Anne", 1));
        results.add(new Result(1, "key_Victoria P.", 2));
        results.add(new Result(1, "key_Madison", 3));
        results.add(new Result(1, "key_Kelley", 4));
        results.add(new Result(1, "key_Lexi", 5));
        results.add(new Result(1, "key_Savannah", 6));
        results.add(new Result(1, "key_Lauren", 7));
        results.add(new Result(1, "key_Tammy", 8));
        results.add(new Result(1, "key_Alayah", 9));
        results.add(new Result(1, "key_Jasmine", 10));
        results.add(new Result(1, "key_Sydney", 11));
        results.add(new Result(1, "key_Natasha", 12));
        results.add(new Result(1, "key_Mykenna", 13));
        results.add(new Result(1, "key_Deandra", 14));
        results.add(new Result(1, "key_Sarah", 15));
        results.add(new Result(1, "key_Alexa", 16));
        results.add(new Result(1, "key_Kelsey", 17));
        results.add(new Result(1, "key_Payton", 18));
        results.add(new Result(1, "key_Kiarra", 19));
        results.add(new Result(1, "key_Courtney", 20));
        results.add(new Result(1, "key_Shiann", 21));
        results.add(new Result(1, "key_Victoria F.", 22));

        // Round 2
        results.add(new Result(2, "key_Kelley", 1));
        results.add(new Result(2, "key_Madison", 2));
        results.add(new Result(2, "key_Sydney", 3));
        results.add(new Result(2, "key_Mykenna", 4));
        results.add(new Result(2, "key_Victoria P.", 5));
        results.add(new Result(2, "key_Natasha", 6));
        results.add(new Result(2, "key_Jasmine", 7));
        results.add(new Result(2, "key_Sarah", 8));
        results.add(new Result(2, "key_Lexi", 9));
        results.add(new Result(2, "key_Hannah Anne", 10));
        results.add(new Result(2, "key_Alexa", 11));
        results.add(new Result(2, "key_Tammy", 12));
        results.add(new Result(2, "key_Alayah", 13));
        results.add(new Result(2, "key_Deandra", 14));
        results.add(new Result(2, "key_Victoria F.", 15));
        results.add(new Result(2, "key_Shiann", 16));
        results.add(new Result(2, "key_Kiarra", 17));
        results.add(new Result(2, "key_Savannah", 18));
        results.add(new Result(2, "key_Kelsey", 19));

        // Round 3
        results.add(new Result(3, "key_Victoria F.", 1));
        results.add(new Result(3, "key_Victoria P.", 2));
        results.add(new Result(3, "key_Sydney", 3));
        results.add(new Result(3, "key_Kelsey", 4));
        results.add(new Result(3, "key_Hannah Anne", 5));
        results.add(new Result(3, "key_Natasha", 6));
        results.add(new Result(3, "key_Lexi", 7));
        results.add(new Result(3, "key_Madison", 8));
        results.add(new Result(3, "key_Shiann", 9));
        results.add(new Result(3, "key_Kelley", 10));
        results.add(new Result(3, "key_Kiarra", 11));
        results.add(new Result(3, "key_Tammy", 12));
        results.add(new Result(3, "key_Savannah", 13));
        results.add(new Result(3, "key_Deandra", 14));
        results.add(new Result(3, "key_Mykenna", 15));

        // Round 4
        results.add(new Result(4, "key_Victoria F.", 1));
        results.add(new Result(4, "key_Kelsey", 2));
        results.add(new Result(4, "key_Madison", 3));
        results.add(new Result(4, "key_Sydney", 4));
        results.add(new Result(4, "key_Natasha", 5));
        results.add(new Result(4, "key_Lexi", 6));
        results.add(new Result(4, "key_Hannah Anne", 7));
        results.add(new Result(4, "key_Shiann", 8));
        results.add(new Result(4, "key_Mykenna", 9));
        results.add(new Result(4, "key_Victoria P.", 10));
        results.add(new Result(4, "key_Kelley", 11));
        results.add(new Result(4, "key_Tammy", 12));

//        // Round 5
//        results.add(new Result(5, "key_Sydney", 1));
//        results.add(new Result(5, "key_Hannah Anne", 2));
//        results.add(new Result(5, "key_Kelley", 3));
//        results.add(new Result(5, "key_Kelsey", 4));
//        results.add(new Result(5, "key_Victoria F.", 5));
//        results.add(new Result(5, "key_Madison", 6));
//        results.add(new Result(5, "key_Natasha", 7));
//        results.add(new Result(5, "key_Victoria P.", 8));
//        results.add(new Result(5, "key_Mykenna", 9));
//        results.add(new Result(5, "key_Tammy", 10));
//
//        // Round 6
//        results.add(new Result(6, "key_Hannah Anne", 1));
//        results.add(new Result(6, "key_Madison", 2));
//        results.add(new Result(6, "key_Victoria F.", 3));
//        results.add(new Result(6, "key_Kelsey", 4));
//        results.add(new Result(6, "key_Natasha", 5));
//        results.add(new Result(6, "key_Kelley", 6));
//
//        // Round 7
//        results.add(new Result(7, "key_Madison", 1));
//        results.add(new Result(7, "key_Kelsey", 2));
//        results.add(new Result(7, "key_Victoria F.", 3));
//        results.add(new Result(7, "key_Hannah Anne", 4));
//
//        // Round 8
//        results.add(new Result(8, "key_Hannah Anne", 1));
//        results.add(new Result(8, "key_Madison", 2));
//        results.add(new Result(8, "key_Victoria F.", 3));

        return results;
    }


}
