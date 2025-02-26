package project.community.theatre.service;

import project.community.theatre.model.ShowTimeEntity;

import java.util.List;

public interface ShowTimeService {
    ShowTimeEntity addShowTimes(ShowTimeEntity showTime);
    void updateShowTime(ShowTimeEntity showTime);
    void deleteShowTime(Long showTimeId);
    List<ShowTimeEntity> getAllShowTimes(String eventId);
}