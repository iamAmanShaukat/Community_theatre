package project.community.theatre.Service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.community.theatre.Converter.TheaterConverter;
import project.community.theatre.Dto.EntryRequestDto.TheaterEntryDto;
import project.community.theatre.Dto.ResponseDto.TheaterResponseDto;
import project.community.theatre.Model.TheaterEntity;
import project.community.theatre.Model.TheaterSeatEntity;
import project.community.theatre.Repository.TheaterRepository;
import project.community.theatre.Repository.TheaterSeatRepository;
import project.community.theatre.Service.TheaterService;
import project.community.theatre.enums.SeatType;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class TheaterServiceImpl implements TheaterService {
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    TheaterSeatRepository theaterSeatRepository;

    @Override
    public TheaterResponseDto addTheater(TheaterEntryDto theaterEntryDto) {
        // we need a thater entity
        TheaterEntity theaterEntity = TheaterConverter.convertDtoToEntity(theaterEntryDto);
        
        // create theater Seats
        List<TheaterSeatEntity> seats = createTheaterSeats();
        for(TheaterSeatEntity theaterSeatEntity : seats) {
            theaterSeatEntity.setTheater(theaterEntity);
        }

        theaterRepository.save(theaterEntity);

        return TheaterConverter.convertEntityToDto(theaterEntity);
    }

    List<TheaterSeatEntity> createTheaterSeats() {
        List<TheaterSeatEntity> seats = new ArrayList<>();
        
        seats.add(getTheaterSeat("1A", 100, SeatType.CLASSIC));
        seats.add(getTheaterSeat("1B", 100, SeatType.CLASSIC));
        seats.add(getTheaterSeat("1C", 100, SeatType.CLASSIC));
        seats.add(getTheaterSeat("1D", 100, SeatType.CLASSIC));
        seats.add(getTheaterSeat("1E", 100, SeatType.CLASSIC));
        
        seats.add(getTheaterSeat("2A", 100, SeatType.PREMIUM));
        seats.add(getTheaterSeat("2B", 100, SeatType.PREMIUM));
        seats.add(getTheaterSeat("2C", 100, SeatType.PREMIUM));
        seats.add(getTheaterSeat("2D", 100, SeatType.PREMIUM));
        seats.add(getTheaterSeat("2E", 100, SeatType.PREMIUM));

        theaterSeatRepository.saveAll(seats);

        return seats;
    }

    TheaterSeatEntity getTheaterSeat(String seatNumber, int rate, SeatType seatType) {
        return TheaterSeatEntity.builder().seatNumber(seatNumber).rate(rate).seatType(seatType).build();
    }

    @Override
    public TheaterResponseDto getTheater(int id) {
        TheaterEntity theaterEntity = theaterRepository.findById(id).get();
        TheaterResponseDto theaterResponseDto = TheaterConverter.convertEntityToDto(theaterEntity);
        return theaterResponseDto;
    }
    
}
