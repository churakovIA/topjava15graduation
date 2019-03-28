package ru.churakov.graduation.service;

import ru.churakov.graduation.model.Vote;
import ru.churakov.graduation.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {

    Vote get(int id, int userId) throws NotFoundException;

    UpdatedVote createOrUpdate(int restaurantId, int userId);

    List<Vote> getAll(int userId);

    List<Vote> getBetweenDates(int userId, LocalDate startDate, LocalDate endDate);

    class UpdatedVote extends Vote{
        private boolean isCreated;
        private Vote vote;

        private UpdatedVote() {
        }

        private UpdatedVote(Vote vote, boolean isCreated) {
            this();
            this.isCreated = isCreated;
            this.vote = vote;
        }

        static UpdatedVote getCreated(Vote vote){
            return new UpdatedVote(vote, true);
        }

        static UpdatedVote getUpdated(Vote vote){
            return new UpdatedVote(vote, false);
        }

        public boolean isCreated() {
            return isCreated;
        }

        public Vote getVote() {
            return vote;
        }
    }
}
