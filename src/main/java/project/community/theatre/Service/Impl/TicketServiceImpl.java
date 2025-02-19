package project.community.theatre.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.community.theatre.Converter.TicketConverter;
import project.community.theatre.Dto.BookTicketRequestDto;
import project.community.theatre.Dto.TicketDto;
import project.community.theatre.Model.ShowEntity;
import project.community.theatre.Model.ShowSeatsEntity;
import project.community.theatre.Model.TicketEntity;
import project.community.theatre.Model.UserEntity;
import project.community.theatre.Repository.ShowRepository;
import project.community.theatre.Repository.TicketRepository;
import project.community.theatre.Repository.UserRepository;
import project.community.theatre.Service.TicketService;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Component
public class TicketServiceImpl implements TicketService {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShowRepository showRepository;
    
    @Override
    public TicketEntity getTicket(int id) {
        TicketEntity ticketEntity = ticketRepository.findById(id).get(); 
        return ticketEntity;
    }

    @Override
    public TicketDto bookTicket(BookTicketRequestDto bookTicketRequestDto) {
        // STEP 1 : finding user & show by ticketRequestDto
        UserEntity userEntity = userRepository.findById(bookTicketRequestDto.getUserId()).get();
        ShowEntity showEntity = showRepository.findById(bookTicketRequestDto.getShowId()).get();

        Set<String> requestedSeat = bookTicketRequestDto.getRequestedSeat();
        List<ShowSeatsEntity> showSeatsList = showEntity.getShowSeatList();

        // ## Option 1 to filter all seat based on some filter condition
        List<ShowSeatsEntity> bookedSeats = showSeatsList
                .stream()
                .filter( seat-> requestedSeat.contains(seat.getSeatNumber()) && !seat.isBooked() &&
                seat.getSeatType().equals(bookTicketRequestDto.getSeatType()) )
                .collect(Collectors.toList());
                
        
        // ## Option 2 :
        // List<ShowSeatsEntity> bookedSeats = new ArrayList<>();
        // for(ShowSeatsEntity seat : showSeatsList) {
        //     if(requestedSeat.contains(seat.getSeatNumber()) && !seat.isBooked()
        //     seat.getSeatType().equals(bookTicketRequestDto.getSeatType())) {
        //         bookedSeats.add(seat);
        //     }
        // }
        

        if(bookedSeats.size() < requestedSeat.size()) {
            // This means All the seats are not Available;
            throw new Error("All Seats Are Not Availble");
        }

        // STEP 2 : Buildin A ticketEntity
        
        TicketEntity ticketEntity = TicketEntity.builder()
                        .user(userEntity)
                        .show(showEntity)
                        .seatList(bookedSeats)
                        .build();


        // STEP 3 : 
        double amount = 0;
        String allotedSeat = "";

        for(ShowSeatsEntity seat : bookedSeats) {
            seat.setBooked(true);
            seat.setBookedAt(new Date());
            seat.setTicket(ticketEntity);

            allotedSeat += seat.getSeatNumber()+" ";
            amount += seat.getRate();
        }
        
        ticketEntity.setAllotedSeats(allotedSeat);
        ticketEntity.setAmount(amount);

        //Connect in the Show and the user
        showEntity.getTicketList().add(ticketEntity);


        //Add the ticket in the tickets list of the user Entity
        userEntity.getTicketList().add(ticketEntity);

        //saving this ticket
        ticketRepository.save(ticketEntity);

        return TicketConverter.convertEntityToDto(ticketEntity);
    }
    
}
