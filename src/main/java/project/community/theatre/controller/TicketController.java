package project.community.theatre.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import project.community.theatre.Dto.BookTicketRequestDto;
//import project.community.theatre.Dto.TicketDto;
//import project.community.theatre.Model.TicketEntity;
//import project.community.theatre.Service.Impl.TicketServiceImpl;
//
//@RestController
//@RequestMapping("/ticket")
//public class TicketController {
//    @Autowired
//    TicketServiceImpl ticketService;
//
//    @GetMapping("/get-ticket")
//    public ResponseEntity<TicketEntity> getTicketById(@RequestParam("id") int id) {
//        return new ResponseEntity<>(ticketService.getTicket(id), HttpStatus.FOUND);
//    }
//
//    @PutMapping("/ticket-booking")
//    public ResponseEntity<TicketDto> bookATicket(@RequestBody BookTicketRequestDto bookTicketRequestDto) {
//        return new ResponseEntity<>(ticketService.bookTicket(bookTicketRequestDto), HttpStatus.CREATED);
//    }
//}
