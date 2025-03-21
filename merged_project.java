
// File: ./src/main/java/project/community/theatre/mapper/EventMapper.java

package project.community.theatre.mapper;

import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;
import project.community.theatre.model.EventEntity;

import java.time.LocalDate;

public class EventMapper {

    /**
     * Maps an EventEntryDto object to an EventEntity object.
     *
     * @param eventEntryDto The DTO containing event data to be mapped.
     * @return An EventEntity object populated with data from the provided DTO.
     * @throws IllegalArgumentException if the eventEntryDto is null.
     */
    public static EventEntity mapDtoToEntity(EventEntryDto eventEntryDto) {
        if (eventEntryDto == null) {
            throw new IllegalArgumentException("EventEntryDto cannot be null");
        }

        // Parse startDate and endDate into LocalDate (assuming format "yyyy-MM-dd")
        LocalDate startDate = LocalDate.parse(eventEntryDto.getStartDate());
        LocalDate endDate = LocalDate.parse(eventEntryDto.getEndDate());

        return EventEntity.builder()
                .eventId(eventEntryDto.getEventId())
                .name(eventEntryDto.getName())
                .genre(eventEntryDto.getGenre())
                .startDate(startDate)
                .endDate(endDate)
                .duration(eventEntryDto.getDuration())
                .description(eventEntryDto.getDescription())
                .producer(eventEntryDto.getProducer())
                .director(eventEntryDto.getDirector())
                .build();
    }

    public static EventResponseDto mapEntityToDto(EventEntity eventEntity) {
        if (eventEntity == null) {
            throw new IllegalArgumentException("EventEntity cannot be null");
        }
        return EventResponseDto.builder()
                .eventId(eventEntity.getEventId())
                .name(eventEntity.getName())
                .genre(eventEntity.getGenre())
                .startDate(eventEntity.getStartDate())
                .endDate(eventEntity.getEndDate())
                .duration(eventEntity.getDuration())
                .description(eventEntity.getDescription())
                .producer(eventEntity.getProducer())
                .director(eventEntity.getDirector())
                .imageUrl(eventEntity.getImageUrl())
                .build();
    }
}
// File: ./src/main/java/project/community/theatre/model/UserEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId = UUID.randomUUID().toString();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mobileNo", nullable = false)
    private String mobileNo;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    public UserEntity(String userId) {
        this.userId = userId;
    }
}
// File: ./src/main/java/project/community/theatre/model/PaymentHistoryEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_history")
public class PaymentHistoryEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id = UUID.randomUUID().toString(); // Use String for ID

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "payment_time", nullable = false)
    private LocalDateTime paymentTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    public enum PaymentStatus {
        SUCCESS, FAILED
    }

    // Constructor to generate ID
    public PaymentHistoryEntity(UserEntity user, String transactionId, Double amount,
                                LocalDateTime paymentTime, PaymentStatus status) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.transactionId = transactionId;
        this.amount = amount;
        this.paymentTime = paymentTime;
        this.status = status;
    }
}
// File: ./src/main/java/project/community/theatre/model/BandEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a band entity in the community theatre system.
 * This class is used to store and retrieve band-related information from the database.
 *
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bands")
public class BandEntity {
    @Id
    @Column(name = "band_id", nullable = false, unique = true)
    @NotBlank(message = "Band ID cannot be empty or blank")
    private String bandId;

    @Column(name = "seats_per_band", nullable = false)
    private Integer seatsPerBand;

    @Column(name = "price", nullable = false)
    private Double price;

}
// File: ./src/main/java/project/community/theatre/model/TicketEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
@ToString(exclude = {"user", "event", "showTimeId"})
public class TicketEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String ticketNumber = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "show_time_id", nullable = false)
    private ShowTimeEntity showTimeId;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "seat_numbers", nullable = false)
    private String seatNumbers;

    @Column(name = "show_time", nullable = false)
    private LocalDateTime showTime;

    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus status;

    public enum TicketStatus {
        BOOKED, CANCELLED
    }

    // Constructor to generate ID
    public TicketEntity(UserEntity user, EventEntity event, String ticketNumber, Double totalPrice,
                        String seatNumbers, LocalDateTime showTime, TicketStatus status) {
        this.ticketNumber = UUID.randomUUID().toString();
        this.user = user;
        this.event = event;
        this.totalPrice = totalPrice;
        this.seatNumbers = seatNumbers;
        this.showTime = showTime;
        this.status = status;
    }
}
// File: ./src/main/java/project/community/theatre/model/EventEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "showTimes")
@Table(name = "events")
public class EventEntity {

    /**
     * Unique identifier for the event.
     * Cannot be null.
     */
    @Id
    @NotNull(message = "ID cannot be null")
    private String eventId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "start_date", columnDefinition = "DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "DATE", nullable = false)
    private LocalDate endDate;

    @Column(name = "duration")
    private String duration;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "producer")
    private String producer;

    @Column(name = "director")
    private String director;

    @Column(name = "image_url")
    private String imageUrl;

    /**
     * List of show times associated with the event.
     * This is a one-to-many relationship with ShowTimeEntity.
     * The showTimes are mapped by the "event" field in ShowTimeEntity.
     * Cascading all operations to showTimes and removing orphaned showTimes.
     */
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowTimeEntity> showTimes;

    public EventEntity(String eventId) {
        this.eventId = eventId;
    }

    // Relationship with ReviewEntity
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviewId;
}
// File: ./src/main/java/project/community/theatre/model/ShowTimeEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "event")
@Table(name = "event_show_times")
public class ShowTimeEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id = UUID.randomUUID().toString(); // Use String for ID

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Column(name = "show_time", nullable = false)
    private String showTime; // Store date-time as a string in Zulu format

    // Constructor to generate ID
    public ShowTimeEntity(EventEntity event, String showTime) {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID
        this.event = event;
        this.showTime = showTime;
    }

    public ShowTimeEntity(String showId) {
        this.id = showId;
    }
}
// File: ./src/main/java/project/community/theatre/model/ReviewEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class ReviewEntity {
    @Id
    @Column(name = "review_id", nullable = false, unique = true)
    private String reviewId = UUID.randomUUID().toString();

    @Column(name = "user_name", nullable = false)
    private String userName; // Default to "Anonymous" if empty

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "reviewed_date", nullable = false)
    private LocalDate reviewedDate;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;
}
// File: ./src/main/java/project/community/theatre/model/DiscountEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents a discount entity in the community theatre system.
 * This entity is used to store information about different types of discounts available.
 *
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discounts")
public class DiscountEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(name = "discount_type", nullable = false)
    @NotBlank(message = "Discount type cannot be blank")
    private String discountType; // e.g., "CHILD", "STUDENT", "PENSIONER"

    @Column(name = "discount_percentage", nullable = false)
    @NotNull(message = "Discount percentage cannot be null"  )
    private Double discountPercentage;

    //Add Constructor
    public DiscountEntity(String discountType, Double discountPercentage) {
        this.id = UUID.randomUUID().toString();  // Generate a unique ID  (UUID)
        this.discountType = discountType;
        this.discountPercentage = discountPercentage;
    }

}
// File: ./src/main/java/project/community/theatre/enums/ImageFormat.java

package project.community.theatre.enums;

import lombok.Getter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum ImageFormat {
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    BMP("bmp"),
    GIF("gif");

    private final String format;

    ImageFormat(String format) {
        this.format = format;
    }

    public static boolean isSupported(String format) {
        if (format == null || format.isEmpty()) {
            return false;
        }
        String lowerCaseFormat = format.toLowerCase();
        for (ImageFormat imageFormat : values()) {
            if (imageFormat.getFormat().equals(lowerCaseFormat)) {
                return true;
            }
        }
        return false;
    }

    public static String getSupportedFormatsAsString() {
        return Stream.of(values())
                .map(ImageFormat::getFormat)
                .collect(Collectors.joining(", "));
    }
}
// File: ./src/main/java/project/community/theatre/enums/SeatBand.java

package project.community.theatre.enums;

public enum SeatBand {
    A,
    B,
    C
}

// File: ./src/main/java/project/community/theatre/service/PaymentGateway.java

package project.community.theatre.service;

import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.exception.PaymentFailedException;

public interface PaymentGateway {
    /**
     * Processes a payment request and returns a response indicating the success or failure of the payment.
     *
     * @param request The PaymentRequest object containing payment details.
     * @return A PaymentResponse object containing the result of the payment processing.
     * @throws PaymentFailedException if the payment processing fails due to an error.
     */
    PaymentResponse processPayment(PaymentRequest request) throws PaymentFailedException;
}
// File: ./src/main/java/project/community/theatre/service/EventService.java

package project.community.theatre.service;


import project.community.theatre.dto.responseDto.ShowTimeResponseDto;
import project.community.theatre.dto.requestDto.AddShowTimesRequestDto;
import project.community.theatre.dto.requestDto.DeleteShowTimeRequestDto;
import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;

import java.util.List;


/**
 * This interface defines the methods for managing events and their show times.
 */
public interface EventService {

    /**
     * Adds a new event to the system.
     *
     * @param eventEntryDto The details of the event to be added.
     * @return The response containing the details of the newly added event.
     */
    EventResponseDto addEvent(EventEntryDto eventEntryDto);

    /**
     * Retrieves the details of a specific event.
     *
     * @param id The unique identifier of the event.
     * @return The response containing the details of the requested event.
     */
    EventResponseDto getEvent(String id);

    /**
     * Retrieves the details of all events in the system.
     *
     * @return A list of responses containing the details of all events.
     */
    List<EventResponseDto> getAllEvent();

    /**
     * Deletes an event from the system.
     *
     * @param eventId The unique identifier of the event to be deleted.
     */
    void deleteEvent(String eventId);

    /**
     * Adds show times for a specific event.
     *
     * @param request The request containing the details of the show times to be added.
     */
    void addShowTimes(AddShowTimesRequestDto request);

    /**
     * Retrieves the show times for a specific event.
     *
     * @param eventId The unique identifier of the event.
     * @return A list of responses containing the details of the show times for the event.
     */
    List<ShowTimeResponseDto> getShowTimesForEvent(String eventId);

    /**
     * Deletes a specific showtime from an event.
     *
     * @param request The request containing the details of the show time to be deleted.
     */
    void deleteShowTime(DeleteShowTimeRequestDto request);
}

// File: ./src/main/java/project/community/theatre/service/ReviewService.java

package project.community.theatre.service;

import project.community.theatre.dto.responseDto.ReviewResponseDto;
import project.community.theatre.model.ReviewEntity;

import java.util.List;

public interface ReviewService {

    /**
     * This method saves a review for an event. It will first check if the event
     * exists. If the event exists, it will then create a new review and save it
     * to the database.
     *
     * @param userName   the username of the user who is submitting the review
     * @param rating     the rating of the review
     * @param description the description of the review
     * @param eventId    the id of the event to which the review is being submitted
     * @return a ReviewEntity that has been saved to the database
     */
    public ReviewEntity saveReview(String userName, Integer rating, String description, String eventId);
    public List<ReviewResponseDto> getAllReviews(String eventId);
}

// File: ./src/main/java/project/community/theatre/service/ProcessTicketAsync.java

package project.community.theatre.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.model.TicketEntity;
import project.community.theatre.model.UserEntity;
import project.community.theatre.util.EmailService;
import project.community.theatre.util.PDFUtil;
import project.community.theatre.util.QRCodeUtil;

/**
 * This service class is responsible for asynchronously processing ticket delivery.
 * It creates a new thread to handle the ticket delivery process for a given ticket.
 * The process includes generating a QR code, creating a PDF, sending an email with the PDF,
 * and handling any exceptions that may occur during the process.
 */
@Service
@Slf4j
public class ProcessTicketAsync {

    /**
     * The email service used to send emails with PDF attachments.
     */
    @Autowired
    EmailService emailService;

    /**
     * Asynchronously processes the ticket delivery for the given ticket and provided email.
     *
     * @param ticket The ticket entity for which the delivery needs to be processed.
     * @param providedEmail The email address provided by the user. If null or empty,
     *                      the email address associated with the ticket's user will be used.
     */
    public void processTicketDeliveryAsync(TicketEntity ticket, String providedEmail) {
        new Thread(() -> {
            int maxAttempts = 3;
            int attempt = 1;
            long delay = 2000;

            while (attempt <= maxAttempts) {
                String ticketId = ticket.getTicketNumber();
                try {
                    log.info("Processing ticket delivery for ticketId: {}, attempt: {}", ticketId, attempt);

                    byte[] qrTicket = QRCodeUtil.generateTicketQRCode(ticketId, 200, 200);
                    byte[] pdfBytes = PDFUtil.createPDF(qrTicket, ticket);
                    String email = ObjectUtils.isNotEmpty(providedEmail)? providedEmail : ticket.getUser().getEmail();
                    emailService.sendEmailWithPDF(email, ticket.getUser().getName(), pdfBytes);

                    log.info("Successfully delivered ticket for ticketId: {}", ticketId);
                    break;

                } catch (Exception e) {
                    log.error("Failed to process ticket delivery for ticketId: {}, attempt: {}",
                            ticketId, attempt, e);

                    if (attempt == maxAttempts) {
                        log.error("All attempts failed for ticketId: {}", ticketId);
                        break;
                    }

                    try {
                        Thread.sleep(delay);
                        delay *= 2; // Exponential backoff
                    } catch (InterruptedException ie) {
                        log.error("Sleep interrupted for ticketId: {}", ticketId, ie);
                        Thread.currentThread().interrupt();
                        break;
                    }

                    attempt++;
                }
            }
        }).start();
    }
}

// File: ./src/main/java/project/community/theatre/service/DiscountService.java

package project.community.theatre.service;

import project.community.theatre.dto.requestDto.DiscountRequest;
import project.community.theatre.dto.responseDto.DiscountResponse;
import project.community.theatre.model.DiscountEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This interface provides methods for managing discounts in the community theatre system.
 */
public interface DiscountService {
    /**
     * Calculates and returns the discount amount based on the given discount request.
     *
     * @return The discount response containing the calculated discount amount.
     */
    DiscountResponse calculateDiscount(DiscountRequest request);

    /**
     * Retrieves all discount entities from the database.
     *
     * @return A list of all discount entities.
     */
    List<DiscountEntity> getAllDiscounts();

    /**
     * Retrieves a discount entity based on the given discount type.
     *
     * @param discountType The type of discount to retrieve.
     * @return The discount entity with the specified discount type, or null if not found.
     */
    DiscountEntity getDiscountByType(String discountType);

    /**
     * Creates or updates a discount entity in the database.
     *
     * @param discount The discount entity to be created or updated.
     * @return The saved discount entity.
     */
    DiscountEntity createOrUpdateDiscount(DiscountEntity discount);

    /**
     * Deletes a discount entity from the database based on the given ID.
     * This method is annotated with {@link Transactional} to ensure atomicity.
     *
     * @param id The ID of the discount entity to be deleted.
     */
    @Transactional
    void deleteDiscount(String id);
}

// File: ./src/main/java/project/community/theatre/service/SeatService.java

package project.community.theatre.service;

import java.util.List;
import java.util.Map;

/**
 * This interface provides methods for managing seat operations in a community theatre system.
 */
public interface SeatService {

    /**
     * Locks the specified seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @param seatNumbers The list of seat numbers to be locked.
     */
    void lockSeats(String eventId, String showId, List<String> seatNumbers);

    /**
     * Checks the availability of the specified seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @param seatNumbers The list of seat numbers to be checked.
     * @return A list of seat numbers that are available.
     */
    List<String> checkSeatsAvailability(String eventId, String showId, List<String> seatNumbers);

    /**
     * Processes the availability of the specified seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @param seatNumbers The list of seat numbers to be processed.
     * @return A map containing the following keys:
     *         - "availableSeats": A list of seat numbers that are available.
     *         - "lockedSeats": A list of seat numbers that are locked.
     */
    Map<String, Object> processSeatsAvailability(String eventId, String showId, List<String> seatNumbers);

    /**
     * Locks the specified booked seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @param bookedSeats The list of seat numbers to be locked as booked.
     */
    void lockBookedSeats(String eventId, String showId, List<String> bookedSeats);

    /**
     * Retrieves all booked seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @return A list of seat numbers that are booked.
     */
    List<String> getAllBookedSeats(String eventId, String showId);
}

// File: ./src/main/java/project/community/theatre/service/BandService.java

package project.community.theatre.service;

import org.springframework.transaction.annotation.Transactional;
import project.community.theatre.model.BandEntity;

import java.util.List;

public interface BandService {
    /**
     * Retrieves all band entities.
     *
     * @return a list of all band entities.
     */
    List<BandEntity> getAllBands();

    /**
     * Retrieves a band entity by its unique identifier.
     *
     * @param bandId the unique identifier of the band to be retrieved. Must not be null or empty.
     * @return the band entity if found, or null if not found.
     */
    BandEntity getBandById(String bandId);

    /**
     * Creates a band entity.
     *
     * @param band the band entity to be created. Must be valid and not null.
     * @return the saved band entity.
     */
    BandEntity createBand(BandEntity band);

    /**
     * Deletes a band entity by its unique identifier.
     *
     * @param bandId the unique identifier of the band to be deleted. Must not be null or empty.
     */
    @Transactional
    void deleteBand(String bandId);
}

// File: ./src/main/java/project/community/theatre/service/PaymentService.java

package project.community.theatre.service;

import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;

public interface PaymentService {
    /**
     * Processes a payment request.
     *
     * @param paymentRequest The request containing payment details.
     * @return A PaymentResponse indicating the success or failure of the payment processing.
     */
    PaymentResponse processPayment(PaymentRequest paymentRequest);
}
// File: ./src/main/java/project/community/theatre/service/ImageService.java

package project.community.theatre.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    /**
     * Takes a MultipartFile image and returns a URL of the image uploaded to Cloudinary
     * @param image the MultipartFile image to upload
     * @return the URL of the uploaded image
     * @throws IOException if there is an error uploading the image
     */
    String getImageUrl(MultipartFile image) throws IOException;
}

// File: ./src/main/java/project/community/theatre/service/ShowTimeService.java

package project.community.theatre.service;

import project.community.theatre.model.ShowTimeEntity;

import java.util.List;

/**
 * This interface provides methods for managing show times in a community theatre system.
 */
public interface ShowTimeService {

    /**
     * Adds a new showtime to the system.
     *
     * @param showTime The showtime entity to be added.
     * @return The added showtime entity with its unique identifier populated.
     */
    ShowTimeEntity addShowTimes(ShowTimeEntity showTime);

    /**
     * Updates an existing showtime in the system.
     *
     * @param showTime The showtime entity with updated information.
     */
    void updateShowTime(ShowTimeEntity showTime);

    /**
     * Deletes a showtime from the system.
     *
     * @param showTimeId The unique identifier of the showtime to be deleted.
     */
    void deleteShowTime(Long showTimeId);

    /**
     * Retrieves all show times for a specific event.
     *
     * @param eventId The unique identifier of the event.
     * @return A list of showtime entities associated with the given event.
     */
    List<ShowTimeEntity> getAllShowTimes(String eventId);
}


// File: ./src/main/java/project/community/theatre/service/UserService.java

package project.community.theatre.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.community.theatre.dto.requestDto.LoginRequest;
import project.community.theatre.dto.requestDto.SignupRequest;
import project.community.theatre.dto.responseDto.AuthResponse;
import project.community.theatre.model.UserEntity;

import java.util.List;

/**
 * This interface defines the contract for user-related operations.
 */
public interface UserService {

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param userId The unique identifier of the user.
     * @return The user entity corresponding to the given identifier.
     */
    UserEntity getUserById(String userId);

    /**
     * Retrieves all users from the system.
     *
     * @return A list of all user entities.
     */
    List<UserEntity> getAllUsers();

    /**
     * Retrieves a user by their email address.
     *
     * @param userEmail The email address of the user.
     * @return The user entity corresponding to the given email address.
     */
    UserEntity getUserByEmail(String userEmail);

    /**
     * Registers a new user in the system.
     *
     * @param request The signup request containing user details.
     * @return The authentication response containing the access token.
     */
    @Transactional
    AuthResponse signup(SignupRequest request);

    /**
     * Updates an existing user's information.
     *
     * @param userId The unique identifier of the user.
     * @param updatedUser The updated user entity.
     * @return The updated user entity.
     */
    @Transactional
    UserEntity updateUser(String userId, UserEntity updatedUser);

    /**
     * Authenticates a user by their credentials.
     *
     * @param request The login request containing user credentials.
     * @return The authentication response containing the access token.
     */
    AuthResponse login(LoginRequest request);

    /**
     * Loads user details by their unique identifier.
     *
     * @param userId The unique identifier of the user.
     * @return The user details corresponding to the given identifier.
     * @throws UsernameNotFoundException If the user with the given identifier is not found.
     */
    UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException;
}
// File: ./src/main/java/project/community/theatre/service/TicketService.java

package project.community.theatre.service;

import org.springframework.transaction.annotation.Transactional;
import project.community.theatre.dto.responseDto.TicketResponse;
import project.community.theatre.dto.requestDto.PaymentRequest;

public interface TicketService {

    /**
     * Generates a ticket for the given payment request and saves it to the database.
     * This method also locks the booked seats and records the payment history.
     * It initiates an asynchronous process to deliver the ticket via email.
     *
     * @param paymentRequest The details of the payment and booking.
     * @param transactionId The unique identifier for the transaction.
     * @return A TicketResponse containing the details of the generated ticket.
     */
    @Transactional
    TicketResponse generateAndSaveTicket(PaymentRequest paymentRequest, String transactionId);

    /**
     * Retrieves the ticket details for a given ticket number.
     *
     * @param ticketNumber the ticket number to fetch details for
     * @return a TicketResponse containing the ticket details. If the ticket is not found, a 404 response is returned.
     */
    TicketResponse getTicketDetails(String ticketNumber);
}
// File: ./src/main/java/project/community/theatre/service/impl/ReviewServiceImpl.java

package project.community.theatre.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.community.theatre.dto.responseDto.ReviewResponseDto;
import project.community.theatre.exception.ResourceNotFoundException;
import project.community.theatre.model.EventEntity;
import project.community.theatre.model.ReviewEntity;
import project.community.theatre.repository.EventRepository;
import project.community.theatre.repository.ReviewRepository;
import project.community.theatre.service.ReviewService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final EventRepository eventRepository;

    @Transactional
    public ReviewEntity saveReview(String userName, Integer rating, String description, String eventId) {
        log.info("Saving review for event ID: {}", eventId);

        // Fetch the event to ensure it exists
        EventEntity event = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        // Set default username if empty
        if (userName == null || userName.trim().isEmpty()) {
            userName = "Anonymous";
        }

        // Create and save the review
        ReviewEntity review = ReviewEntity.builder()
                .reviewId(UUID.randomUUID().toString())
                .userName(userName)
                .rating(rating)
                .description(description)
                .reviewedDate(LocalDate.now())
                .event(event)
                .build();

        return reviewRepository.save(review);
    }

    @Override
    public List<ReviewResponseDto> getAllReviews(String eventId) {
        log.info("Fetching reviews for event ID: {}", eventId);

        EventEntity eventEntity = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        // Extract the show times from the event's showTimes field
        return eventEntity.getReviewId().stream()
                .map(ReviewEntity -> ReviewResponseDto.builder()
                        .reviewId(ReviewEntity.getReviewId())
                        .userName(ReviewEntity.getUserName())
                        .rating(ReviewEntity.getRating())
                        .description(ReviewEntity.getDescription())
                        .reviewDate(ReviewEntity.getReviewedDate())
                        .build())
                .toList();
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/PaymentServiceImpl.java

package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.service.PaymentGateway;
import project.community.theatre.service.PaymentService;

import java.util.UUID;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentGateway paymentGateway;

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Simulate payment processing
        if (isValidPayment(paymentRequest)) {
            return paymentGateway.processPayment(paymentRequest);
        } else {
            String transactionId = UUID.randomUUID().toString();
            log.error("Invalid payment request: {} :: transactionId: {}", paymentRequest, transactionId);
            return new PaymentResponse(false, "Payment failed", transactionId);
        }
    }

    private boolean isValidPayment(PaymentRequest paymentRequest) {
        // Dummy validation logic
        if (paymentRequest.getPayableAmount() <= 0 || paymentRequest.getPaymentDetails().getCardNumber().startsWith("4000")) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/UserServiceImpl.java

package project.community.theatre.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.LoginRequest;
import project.community.theatre.dto.requestDto.SignupRequest;
import project.community.theatre.dto.responseDto.AuthResponse;
import project.community.theatre.exception.UserAlreadyExistsException;
import project.community.theatre.exception.UserNotFoundException;
import project.community.theatre.model.UserEntity;
import project.community.theatre.repository.UserRepository;
import project.community.theatre.service.UserService;
import project.community.theatre.util.JwtUtil;
import project.community.theatre.util.PasswordEncoderUtil;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoder;

    private static final List<String> VALID_ROLES = List.of("USER", "ADMIN");

    @Transactional
    @Override
    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        String hashedPassword = passwordEncoder.encodePassword(request.getPassword());

        String role = request.getRole() != null && VALID_ROLES.contains(request.getRole()) ? request.getRole() : "USER";

        UserEntity user = UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .mobileNo(request.getMobileNo())
                .email(request.getEmail())
                .password(hashedPassword)
                .role(role)
                .build();
        userRepository.save(user);
        log.info("User with email {} saved successfully",request.getEmail());

        String token = jwtUtil.generateToken(user.getUserId(), user.getRole());

        return new AuthResponse("User registered successfully", user.getUserId(), user.getRole(), token);
    }

    @Transactional
    @Override
    public UserEntity updateUser(String userId, UserEntity updatedUser) {
        return userRepository.findUserById(userId).map(existingUser -> {

            if (updatedUser.getName() != null && !updatedUser.getName().isBlank()) {
                existingUser.setName(updatedUser.getName());
            }
            
            if (updatedUser.getMobileNo() != null && !updatedUser.getMobileNo().isBlank()) {
                existingUser.setMobileNo(updatedUser.getMobileNo());
            }

            if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank()) {
                existingUser.setEmail(updatedUser.getEmail());
            }

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                existingUser.setPassword(passwordEncoder.encodePassword(updatedUser.getPassword()));
            }

            existingUser.setRole(updatedUser.getRole());

            log.info("User with ID {} updated successfully", userId);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getUserId(), user.getRole());

        return new AuthResponse("Login successful", user.getUserId(), user.getRole(), token);

    }

    @Override
    public UserEntity getUserById(String userId) {
        return userRepository.findUserById(userId).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + userId));
    }

    @Override
    public UserEntity getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail).orElseThrow(() ->
                new UserNotFoundException("User not found with email: " + userEmail));
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getPassword(),
                Collections.singletonList(user::getRole)
        );
    }

    @Override
    public List<UserEntity> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/TicketServiceImpl.java

package project.community.theatre.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.community.theatre.dto.responseDto.TicketResponse;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.exception.ResourceNotFoundException;
import project.community.theatre.model.*;
import project.community.theatre.repository.EventRepository;
import project.community.theatre.repository.PaymentHistoryRepository;
import project.community.theatre.repository.TicketRepository;
import project.community.theatre.repository.UserRepository;
import project.community.theatre.service.ProcessTicketAsync;
import project.community.theatre.service.SeatService;
import project.community.theatre.service.TicketService;
import project.community.theatre.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    @Autowired
    UserService userService;

    @Autowired
    ProcessTicketAsync processTicketAsync;

    @Autowired
    SeatService seatService;


    private final TicketRepository ticketRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public TicketResponse generateAndSaveTicket(PaymentRequest paymentRequest, String transactionId) {
        log.info("Generating and saving ticket :: transactionId: {}", transactionId);
        // Generate a unique ticket number
        String ticketId = UUID.randomUUID().toString();
    
        // Convert seat numbers to a comma-separated string
        String seatNumbersString = String.join(",", paymentRequest.getSeatNumbers());
    
        // Convert showTime from String to LocalDateTime
        LocalDateTime showTime = LocalDateTime.parse(paymentRequest.getShowTime());
    
        // Create and save the ticket
        TicketEntity ticket = TicketEntity.builder()
                .ticketNumber(ticketId)
                .user(userRepository.findUserById(paymentRequest.getUserId())
                        .orElse(new UserEntity(paymentRequest.getUserId())))
                .event(eventRepository.findEventById(paymentRequest.getEventId())
                        .orElse(new EventEntity(paymentRequest.getEventId())))
                .totalPrice(paymentRequest.getPayableAmount())
                .seatNumbers(seatNumbersString)
                .showTime(showTime)
                .bookingTime(LocalDateTime.now())
                .status(TicketEntity.TicketStatus.BOOKED)
                .showTimeId(new ShowTimeEntity(paymentRequest.getShowId()))
                .build();
        log.info("Saving ticket: {}", ticket);
        ticketRepository.save(ticket);

        // Lock booked seats
        seatService.lockBookedSeats(paymentRequest.getEventId(), paymentRequest.getShowId(), paymentRequest.getSeatNumbers());
    
        // Save payment history
        PaymentHistoryEntity paymentHistory = PaymentHistoryEntity.builder()
                .id(transactionId)
                .user(new UserEntity(paymentRequest.getUserId()))
                .transactionId(transactionId)
                .amount(paymentRequest.getPayableAmount())
                .paymentTime(LocalDateTime.now())
                .status(PaymentHistoryEntity.PaymentStatus.SUCCESS)
                .build();
        log.info("Saving payment history: {}", paymentHistory);
        paymentHistoryRepository.save(paymentHistory);

        UserEntity user = userService.getUserById(paymentRequest.getUserId());
        // Start async ticket delivery process in a new thread
        processTicketAsync.processTicketDeliveryAsync(ticket, paymentRequest.getEmail());

        // Map the ticket entity to a response DTO
        return new TicketResponse(
                ticket.getTicketNumber(),
                ticket.getTotalPrice(),
                ticket.getSeatNumbers(),
                ticket.getShowTime().toString(),
                ticket.getEvent().getName(),
                ticket.getBookingTime(),
                ticket.getStatus().name()
        );
    }


    @Override
    public TicketResponse getTicketDetails(String ticketNumber) {
        // Fetch the ticket from the database
        TicketEntity ticket = ticketRepository.findByTicketNumber(ticketNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ticket number: " + ticketNumber));

        // Map TicketEntity to TicketResponse
        return TicketResponse.builder()
                .ticketNumber(ticket.getTicketNumber())
                .totalPrice(ticket.getTotalPrice())
                .seatNumbers(ticket.getSeatNumbers())
                .showTime(ticket.getShowTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .eventName(ticket.getEvent().getName())
                .bookingTime(ticket.getBookingTime())
                .status(ticket.getStatus().toString())
                .build();
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/SeatServiceImpl.java

package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import project.community.theatre.service.EventService;
import project.community.theatre.service.SeatService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SeatServiceImpl implements SeatService {

    @Autowired
    EventService eventService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Value("${app.seat.lock.expiry}")
    private String tempLockExpiry;
    @Value("${app.redis.temp.seat.lock.key}")
    private String tempSeatLockKeyFormat;
    @Value("${app.redis.booked.seat.lock.key}")
    private String bookedSeatLockKeyFormat;

    @Override
    public void lockSeats(String eventId, String showId, List<String> seatNumbers) {
        log.info("Locking seats for show ID: {} and seats: {}", showId, seatNumbers);
        // Lock all requested seats in Redis
        for (String seat : seatNumbers) {
            String key = tempSeatLockKeyFormat
                    .replace("{eventId}", eventId)
                    .replace("{showId}", showId)
                    .replace("{seat}", seat);
            redisTemplate.opsForValue().set(key, "LOCKED", Long.parseLong(tempLockExpiry), TimeUnit.SECONDS);
        }
    }

    @Override
    public List<String> checkSeatsAvailability(String eventId, String showId, List<String> seatNumbers) {
        log.info("Checking availability of seats for show ID: {} and seats: {}", showId, seatNumbers);
        return seatNumbers.stream()
                .filter(seatNumber -> {
                    String key = tempSeatLockKeyFormat
                            .replace("{eventId}", eventId)
                            .replace("{showId}", showId)
                            .replace("{seat}", seatNumber);

                    return Boolean.TRUE.equals(redisTemplate.hasKey(key));
                })
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> processSeatsAvailability(String eventId, String showId, List<String> seatNumbers) {
        log.info("Checking and locking seats for show ID: {} and seats: {}", showId, seatNumbers);

        List<String> unavailableSeats = checkSeatsAvailability(eventId, showId, seatNumbers);

        // If any seats are unavailable, return them
        if (!unavailableSeats.isEmpty()) {
            log.warn("Unavailable seats found: {}", unavailableSeats);
            return Map.of(
                    "status", Boolean.FALSE,
                    "unavailableSeats", unavailableSeats
            );
        }
        // Lock all requested seats in Redis
        lockSeats(eventId, showId, seatNumbers);

        log.info("All seats locked successfully: {}", seatNumbers);
        return Map.of(
                "status", Boolean.TRUE
        );
    }

    @Override
    public void lockBookedSeats(String eventId, String showId, List<String> bookedSeats) {
        log.info("Locking booked seats for show ID {}: {}", showId, bookedSeats);
        String redisKey = bookedSeatLockKeyFormat
                .replace("{eventId}", eventId)
                .replace("{showId}", showId);
        Boolean keyExists = redisTemplate.hasKey(redisKey);
        if (Boolean.TRUE.equals(keyExists)) {
            log.info("Appending {} seats to the existing list for show ID {}", bookedSeats.size(), showId);
            redisTemplate.opsForList().rightPushAll(redisKey, bookedSeats);
        } else {
            log.info("Creating a new list with {} seats for show ID {}", bookedSeats.size(), showId);
            redisTemplate.opsForList().rightPushAll(redisKey, bookedSeats);
            // Set the TTL for the key (only when creating a new list)
            redisTemplate.expire(redisKey, getEventExpirationTime(eventId), TimeUnit.SECONDS);
        }
        log.info("Successfully locked {} seats for show ID {}", bookedSeats.size(), showId);
    }

    @Override
    public List<String> getAllBookedSeats(String eventId, String showId) {
        log.info("Fetching booked and locked seats for event ID: {} and show ID: {}", eventId, showId);

        // Get booked seats from the list stored under the booked key
        String bookedRedisKey = bookedSeatLockKeyFormat
                .replace("{eventId}", eventId)
                .replace("{showId}", showId);
        List<String> bookedSeats = redisTemplate.opsForList().range(bookedRedisKey, 0, -1);
        if (bookedSeats == null) {
            bookedSeats = Collections.emptyList();
            log.info("No booked seats found for event ID: {} and show ID: {}", eventId, showId);
        } else {
            log.info("Found {} booked seats for event ID: {} and show ID: {}", bookedSeats.size(), eventId, showId);
        }

        Set<String> lockKeys = getSeatLockKeys(eventId, showId);
        Set<String> lockedSeats = new HashSet<>();
        for (String key : lockKeys) {
            String seat = key.substring(key.lastIndexOf(':') + 1);
            lockedSeats.add(seat);
        }
        log.info("Found {} locked seats.", lockedSeats.size());

        Set<String> allLockedSeats = new HashSet<>(bookedSeats);
        allLockedSeats.addAll(lockedSeats);
        log.info("Total booked and locked seats: {}", allLockedSeats.size());

        return new ArrayList<>(allLockedSeats);
    }

    private long getEventExpirationTime(String eventId) {
        LocalDate endDate = eventService.getEvent(eventId).getEndDate();
        // Assume the end time is at the end of the day (23:59:59)
        long endTimeMillis = endDate.atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        // Calculate the remaining time in seconds
        long currentTimeMillis = System.currentTimeMillis();
        return ChronoUnit.SECONDS.between(
                Instant.ofEpochMilli(currentTimeMillis),
                Instant.ofEpochMilli(endTimeMillis)
        );
    }

    private Set<String> getSeatLockKeys(String eventId, String showId) {
        // Construct the pattern with the known eventId and showId
        String pattern = tempSeatLockKeyFormat
                .replace("{eventId}", eventId)
                .replace("{showId}", showId)
                .replace("{seat}", "*");

        return redisTemplate.keys(pattern);
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/DiscountServiceImpl.java

package project.community.theatre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.TicketType;
import project.community.theatre.dto.requestDto.DiscountRequest;
import project.community.theatre.dto.responseDto.DiscountResponse;
import project.community.theatre.exception.DiscountNotFoundException;
import project.community.theatre.model.BandEntity;
import project.community.theatre.model.DiscountEntity;
import project.community.theatre.repository.BandRepository;
import project.community.theatre.repository.DiscountRepository;
import project.community.theatre.service.BandService;
import project.community.theatre.service.DiscountService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private BandService bandService;
    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public DiscountResponse calculateDiscount(DiscountRequest request) {
        log.info("Calculating discount for request: {}", request);
        DiscountResponse response = new DiscountResponse();
        List<BandEntity> bands = bandService.getAllBands();
        List<DiscountEntity> discounts = getAllDiscounts();

        Map<String, Double> discountMap = createDiscountMap(discounts);
        PriceBreakdown prices = calculatePrices(request.getBands(), bands);

        if (request.isSocialClub()) {
            response.setSocialClub(calculateSocialClubDiscount(
                    prices.totalFullPrice,
                    request.getTotalTickets(),
                    discountMap
            ));
            double totalReduction = response.getSocialClub();
            response.setReduction(totalReduction);
            response.setFinalPrice(prices.totalFullPrice - totalReduction);
            log.info("Social club discount applied: {}", response.getSocialClub());
            return response;
        }

        calculateRegularDiscounts(request, prices, discountMap, response);

        double totalReduction = response.getChild() + response.getPensioner() +
                response.getLastHour() + response.getWeekday();
        response.setReduction(totalReduction);
        response.setFinalPrice(prices.totalFullPrice - totalReduction);

        log.info("Regular discounts applied: {}", response);
        return response;
    }

    // Rest of the code remains unchanged
    private Map<String, Double> createDiscountMap(List<DiscountEntity> discounts) {
        return discounts.stream()
                .collect(Collectors.toMap(
                        DiscountEntity::getDiscountType,
                        DiscountEntity::getDiscountPercentage
                ));
    }

    private PriceBreakdown calculatePrices(Map<String, TicketType> bands, List<BandEntity> bandEntities) {
        log.info("Calculating prices for bands: {}", bands);
        Map<String, Double> bandPrices = bandEntities.stream()
                .collect(Collectors.toMap(BandEntity::getBandId, BandEntity::getPrice));

        double totalChildPrice = 0;
        double totalPensionerPrice = 0;
        double totalFullPrice = 0;

        for (Map.Entry<String, TicketType> entry : bands.entrySet()) {
            String band = entry.getKey();
            TicketType tickets = entry.getValue();
            double price = bandPrices.getOrDefault(band, 0.0);

            totalChildPrice += price * tickets.getChild();
            totalPensionerPrice += price * tickets.getPensioner();
            totalFullPrice += price * (tickets.getChild() + tickets.getAdult() + tickets.getPensioner());
        }
        log.info("Total prices: Child: {}, Pensioner: {}, Full: {}", totalChildPrice, totalPensionerPrice, totalFullPrice);
        return new PriceBreakdown(totalChildPrice, totalPensionerPrice, totalFullPrice);
    }

    private double calculateSocialClubDiscount(double totalFullPrice, int totalTickets,
                                               Map<String, Double> discountMap) {
        double baseDiscount = discountMap.getOrDefault("SOCIAL_CLUB", 0.0);
        double additionalDiscount = totalTickets > 20
                ? discountMap.getOrDefault("QUANTITY", 0.0)
                : 0.0;
        return totalFullPrice * ((baseDiscount + additionalDiscount) / 100);
    }

    private void calculateRegularDiscounts(DiscountRequest request, PriceBreakdown prices,
                                           Map<String, Double> discountMap, DiscountResponse response) {
        // Child and Pensioner discounts
        double childDiscountPercent = discountMap.getOrDefault("CHILDREN", 0.0);
        double pensionerDiscountPercent = discountMap.getOrDefault("PENSIONERS", 0.0);
        response.setChild(prices.totalChildPrice * (childDiscountPercent / 100));
        response.setPensioner(prices.totalPensionerPrice * (pensionerDiscountPercent / 100));

        // Last Hour discount
        if (isLastHour(request.getShowTime())) {
            double lastHourPercent = discountMap.getOrDefault("LAST_HOUR", 0.0);
            response.setLastHour(prices.totalFullPrice * (lastHourPercent / 100));
        }

        // Weekday discount
        if (isWeekday(request.getDay())) {
            double weekdayPercent = discountMap.getOrDefault("WEEKDAY_SPECIAL", 0.0);
            response.setWeekday(prices.totalFullPrice * (weekdayPercent / 100));
        }
    }

    private boolean isLastHour(LocalDateTime showTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        long hoursUntilShow = ChronoUnit.HOURS.between(currentTime, showTime);
        return hoursUntilShow <= 1;
    }

    private boolean isWeekday(String day) {
        List<String> weekdays = Arrays.asList("monday", "tuesday", "wednesday", "thursday");
        return weekdays.contains(day.toLowerCase());
    }

    private static class PriceBreakdown {
        double totalChildPrice;
        double totalPensionerPrice;
        double totalFullPrice;

        PriceBreakdown(double totalChildPrice, double totalPensionerPrice, double totalFullPrice) {
            this.totalChildPrice = totalChildPrice;
            this.totalPensionerPrice = totalPensionerPrice;
            this.totalFullPrice = totalFullPrice;
        }
    }

    @Override
    public List<DiscountEntity> getAllDiscounts() {
        log.info("Fetching all discounts");
        return discountRepository.findAll();
    }

    @Override
    public DiscountEntity getDiscountByType(String discountType) {
        log.info("Fetching discount for type: {}", discountType);
        return discountRepository.findByDiscountType(discountType)
                .orElseThrow(() -> new DiscountNotFoundException("Discount not found for type: " + discountType));
    }
    @Override
    public DiscountEntity createOrUpdateDiscount(DiscountEntity discount) {
        log.info("Creating or updating discount: {}", discount);
        return discountRepository.save(discount);
    }

    @Override
    @Transactional
    public void deleteDiscount(String id) {
        if (!discountRepository.existsById(id)) {
            throw new DiscountNotFoundException("Discount not found for ID: " + id);
        }
        discountRepository.deleteById(id);
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/PaymentGatewayImpl.java

package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.exception.PaymentFailedException;
import project.community.theatre.service.PaymentGateway;

import java.util.UUID;

@Service
@Slf4j
public class PaymentGatewayImpl implements PaymentGateway {

    @Override
    public PaymentResponse processPayment(PaymentRequest request) throws PaymentFailedException {
        log.info("Processing payment: {}", request);
        try {
            Thread.sleep(1000); // Fake delay to mimic network call
            String transactionId = UUID.randomUUID().toString();
            double amount = request.getPayableAmount();

            return new PaymentResponse(Boolean.TRUE, "Payment processed successfully for " + amount, transactionId);
        } catch (InterruptedException e) {
            throw new PaymentFailedException("Payment simulation interrupted");
        }
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/BandServiceImpl.java

package project.community.theatre.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.community.theatre.exception.BandNotFoundException;
import project.community.theatre.model.BandEntity;
import project.community.theatre.repository.BandRepository;
import project.community.theatre.service.BandService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BandServiceImpl implements BandService {

    private final BandRepository bandRepository;

    @Override
    public List<BandEntity> getAllBands() {
        log.info("Fetching all bands");
        return bandRepository.findAll();
    }

    @Override
    public BandEntity getBandById(String bandId) {
        log.info("Fetching band for ID: {}", bandId);
        return bandRepository.findByBandId(bandId)
                .orElseThrow(() -> new BandNotFoundException("Band not found for ID: " + bandId));
    }

    @Override
    public BandEntity createBand(BandEntity band) {
        log.info("Creating bands: {}", band);
        return bandRepository.save(band);
    }

    @Override
    @Transactional
    public void deleteBand(String bandId) {
        if (!bandRepository.existsByBandId(bandId)) {
            throw new BandNotFoundException("Band not found for ID: " + bandId);
        }
        bandRepository.deleteByBandId(bandId);
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/EventServiceImpl.java

package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.AddShowTimesRequestDto;
import project.community.theatre.dto.requestDto.DeleteShowTimeRequestDto;
import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;
import project.community.theatre.exception.InvalidImageException;
import project.community.theatre.exception.ResourceNotFoundException;
import project.community.theatre.mapper.EventMapper;
import project.community.theatre.model.EventEntity;
import project.community.theatre.model.ShowTimeEntity;
import project.community.theatre.model.TicketEntity;
import project.community.theatre.repository.EventRepository;
import project.community.theatre.repository.ShowTimeRepository;
import project.community.theatre.dto.responseDto.ShowTimeResponseDto;
import project.community.theatre.repository.TicketRepository;
import project.community.theatre.service.EventService;
import project.community.theatre.service.ImageService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public EventResponseDto addEvent(EventEntryDto eventEntryDto) {
        log.info("Adding a new event :: {}", eventEntryDto);
        try {
            String imageUrl = imageService.getImageUrl(eventEntryDto.getImage());
            EventEntity eventEntity = EventMapper.mapDtoToEntity(eventEntryDto);
            eventEntity.setImageUrl(imageUrl);

            eventRepository.save(eventEntity);

            log.info("Event added successfully :: {}", eventEntity);
            return EventMapper.mapEntityToDto(eventEntity);
        } catch (InvalidImageException e) {
            log.error("Invalid image: {}", e.getMessage(), e);
            throw e; // Let GlobalExceptionHandler handle it
        } catch (Exception e) {
            log.error("Error while adding event :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to add event", e);
        }
    }

    @Override
    public EventResponseDto getEvent(String id) {
        log.info("Fetching movie by ID :: {}", id);
        try {
            return eventRepository.findEventById(id)
                    .map(EventMapper::mapEntityToDto)
                    .orElseThrow(() -> {
                        log.error("Movie not found with ID :: {}", id);
                        return new RuntimeException("Movie not found with ID: " + id);
                    });
        } catch (Exception e) {
            log.error("Error while fetching event by ID :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch event", e);
        }
    }

    @Override
    public List<EventResponseDto> getAllEvent() {
        log.info("Fetching all events");
        try {
            List<EventEntity> eventEntities = eventRepository.findAll();
            eventEntities.sort(Comparator.comparing(EventEntity::getStartDate));
            if (eventEntities.isEmpty()) {
                log.warn("No event found in the database");
            }
            return eventEntities.stream()
                    .map(EventMapper::mapEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while fetching all events :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch all events", e);
        }
    }

    @Override
    public void deleteEvent(String eventId) {
        log.info("Deleting event with ID: {}", eventId);

        // Fetch the event
        EventEntity event = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        // Delete associated tickets
        List<TicketEntity> tickets = ticketRepository.findEventById(eventId);
        ticketRepository.deleteAll(tickets);

        // Delete associated show times
        List<ShowTimeEntity> showTimes = showTimeRepository.findByEvent(event);
        showTimeRepository.deleteAll(showTimes);

        // Delete the event
        eventRepository.delete(event);

        log.info("Event and all associated data deleted successfully for ID: {}", eventId);
    }

    @Override
    public void addShowTimes(AddShowTimesRequestDto request) {
        log.info("Adding show times for event ID: {}", request.getEventId());

        EventEntity eventEntity = eventRepository.findEventById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + request.getEventId()));

        List<ShowTimeEntity> showTimeEntities = request.getShowTimes().stream()
                .map(showTime -> ShowTimeEntity.builder()
                        .event(eventEntity)
                        .showTime(showTime)
                        .build())
                .toList();

        if (eventEntity.getShowTimes() == null) {
            eventEntity.setShowTimes(showTimeEntities);
        } else {
            eventEntity.getShowTimes().addAll(showTimeEntities);
        }

        eventRepository.save(eventEntity);
        log.info("Show times added successfully for event ID: {}", request.getEventId());
    }

    @Override
    public List<ShowTimeResponseDto> getShowTimesForEvent(String eventId) {
        log.info("Fetching show times for event ID: {}", eventId);

        EventEntity eventEntity = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        // Extract the show times from the event's showTimes field
        return eventEntity.getShowTimes().stream()
                .map(showTimeEntity -> ShowTimeResponseDto.builder()
                        .id(showTimeEntity.getId())
                        .showTime(showTimeEntity.getShowTime())
                        .build())
                .toList();
    }

    @Override
    public void deleteShowTime(DeleteShowTimeRequestDto request) {
        log.info("Deleting show time {} for event ID: {}", request.getShowTime(), request.getEventId());

        EventEntity eventEntity = eventRepository.findEventById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + request.getEventId()));

        if (eventEntity.getShowTimes() != null && eventEntity.getShowTimes().remove(request.getShowTime())) {
            eventRepository.save(eventEntity);
            log.info("Show time deleted successfully: {}", request.getShowTime());
        } else {
            throw new ResourceNotFoundException("Show time not found: " + request.getShowTime());
        }
    }

}
// File: ./src/main/java/project/community/theatre/service/impl/ImageServiceImpl.java

package project.community.theatre.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.community.theatre.constant.AppConstants;
import project.community.theatre.enums.ImageFormat;
import project.community.theatre.exception.InvalidImageException;
import project.community.theatre.service.ImageService;
import project.community.theatre.util.CustomMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Autowired
    private Cloudinary cloudinary;

    /**
     * Processes the given MultipartFile and returns the URL of the processed image.
     *
     * The image is first validated to check if it's a valid image file. If not, an
     * {@link InvalidImageException} is thrown.
     *
     * The image is then resized and compressed locally using the
     * {@link Thumbnails} library.
     *
     * The processed file is then uploaded to Cloudinary using the
     * {@link Cloudinary} library.
     *
     * @param image the image to process
     * @return the URL of the processed image
     * @throws IOException if there is an error uploading the image
     */
    public String getImageUrl(MultipartFile image) throws IOException {
        return processImage(image);
    }

    /**
     * Uploads the given MultipartFile to Cloudinary and returns the URL of the uploaded image.
     *
     * @param file the image to upload
     * @return the URL of the uploaded image
     * @throws IOException if there is an error uploading the image
     */
    private String uploadImage(MultipartFile file) throws IOException {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return Optional.ofNullable(uploadResult.get("url"))
                    .map(String::valueOf)
                    .orElseThrow(() -> new IOException("Failed to retrieve image URL from Cloudinary"));
        } catch (Exception e) {
            log.error("Failed to upload image to Cloudinary: {}", e.getMessage(), e);
            throw new IOException("Failed to upload image to Cloudinary: " + e.getMessage(), e);
        }
    }

    /**
     * Process the given MultipartFile and return the URL of the processed image.
     *
     * The image is first validated to check if it's a valid image file. If not, an
     * {@link InvalidImageException} is thrown.
     *
     * The image is then resized and compressed locally using the
     * {@link Thumbnails} library.
     *
     * The processed file is then uploaded to Cloudinary using the
     * {@link #uploadImage(MultipartFile)} method.
     *
     * @param image the image to process
     * @return the URL of the processed image
     * @throws IOException if there is an error processing the image
     * @throws InvalidImageException if the image is not a valid image file
     */
    private String processImage(MultipartFile image) {
        File tempFile = null;
        try {
            validateImage(image);

            // Generate a unique filename for temporary storage
            String fileName = UUID.randomUUID() + "." + getExtension(image.getOriginalFilename());
            tempFile = createTempFile(fileName);

            // Resize and compress the image locally
            Thumbnails.of(image.getInputStream())
                    .size(AppConstants.IMAGE_WIDTH, AppConstants.IMAGE_HEIGHT)
                    .outputQuality(AppConstants.IMAGE_QUALITY)
                    .toFile(tempFile);

            // Convert the processed file back to MultipartFile

            return uploadImage(new CustomMultipartFile(tempFile, image.getOriginalFilename()));

        } catch (InvalidImageException | IllegalArgumentException e) {
            log.error("Validation failed: {}", e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            log.error("Failed to process the image", e);
            throw new RuntimeException("Failed to process the image", e);
        }finally {
            cleanupTempFile(tempFile);
        }
    }

    /**
     * Validates the given MultipartFile to ensure it is a valid image file.
     * The validation includes:
     * <ul>
     *     <li>Checking if the content type of the file is an image type</li>
     *     <li>Checking if the file size is less than the maximum allowed size (5MB)</li>
     *     <li>Checking if the file extension is one of the supported image formats</li>
     * </ul>
     * If any of the validation fails, an {@link InvalidImageException} is thrown.
     *
     * @param image the image to validate
     * @throws InvalidImageException if the image is not a valid image file
     */
    private void validateImage(MultipartFile image) {
        if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
            throw new InvalidImageException("Only image files are allowed");
        }
        if (image.getSize() > AppConstants.MAX_FILE_SIZE_BYTES) {
            throw new InvalidImageException("File size exceeds the limit of 5MB");
        }
        String extension = getExtension(image.getOriginalFilename());
        if (!ImageFormat.isSupported(extension)) {
            throw new InvalidImageException("Unsupported file format. Supported formats: " + ImageFormat.getSupportedFormatsAsString());
        }
    }


    /**
     * Creates a temporary file with the given filename.
     * The file is created in the system's default temporary directory.
     * The file name will be prefixed with "temp-" and suffixed with the given filename.
     * A log message at INFO level is written to indicate the creation of the file.
     * @param fileName the filename of the temporary file
     * @return the created temporary file
     * @throws IOException if an I/O error occurs
     */
    private File createTempFile(String fileName) throws IOException {
        File tempFile = File.createTempFile("temp-", "-" + fileName);
        log.info("Temporary file created: {}", tempFile.getAbsolutePath());
        return tempFile;
    }

    /**
     * Cleans up a temporary file.
     * <p>
     * If the given file is not null and exists, it is attempted to be deleted.
     * If the deletion is successful, a log message at INFO level is written.
     * If the deletion fails, a log message at WARN level is written.
     *
     * @param tempFile the temporary file to clean up
     */
    private void cleanupTempFile(File tempFile) {
        if (tempFile != null && tempFile.exists()) {
            try {
                Files.delete(tempFile.toPath());
                log.info("Temporary file deleted: {}", tempFile.getAbsolutePath());
            } catch (IOException e) {
                log.warn("Failed to delete temporary file: {}", tempFile.getAbsolutePath(), e);
            }
        }
    }

    /**
     * Gets the file extension of the given filename.
     * The extension is returned in lower case.
     * If the given filename does not contain a dot, an {@link InvalidImageException} is thrown.
     * @param originalFilename the filename from which to get the extension
     * @return the file extension
     * @throws InvalidImageException if the filename does not contain a dot
     */
    private String getExtension(String originalFilename) {
        return Optional.ofNullable(originalFilename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".") + 1).toLowerCase())
                .orElseThrow(() -> new InvalidImageException("Invalid file format"));
    }
}
// File: ./src/main/java/project/community/theatre/dto/TicketType.java

package project.community.theatre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketType {
    private int child;
    private int adult;
    private int pensioner;
}
// File: ./src/main/java/project/community/theatre/dto/responseDto/DiscountResponse.java

package project.community.theatre.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountResponse {
    private double child;
    private double pensioner;
    private double lastHour;
    private double socialClub;
    private double weekday;
    private double finalPrice;
    private double reduction;
}
// File: ./src/main/java/project/community/theatre/dto/responseDto/PaymentResponse.java

package project.community.theatre.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private boolean success;
    private String message;
    private String transactionId;
}
// File: ./src/main/java/project/community/theatre/dto/responseDto/ShowTimeResponseDto.java

package project.community.theatre.dto.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowTimeResponseDto {

    private String id; // Primary key of the ShowTimeEntity
    private String showTime; // Show time in Zulu format (e.g., "2025-02-20T14:30:00Z")
}
// File: ./src/main/java/project/community/theatre/dto/responseDto/ReviewResponseDto.java

package project.community.theatre.dto.responseDto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponseDto {
    private String reviewId;
    private String userName;
    private Integer rating;
    private String description;
    private LocalDate reviewDate;

}

// File: ./src/main/java/project/community/theatre/dto/responseDto/EventResponseDto.java

package project.community.theatre.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDto {
    private String eventId;
    private String name;
    private String genre;
    private LocalDate startDate;
    private LocalDate endDate;
    private String duration;
    private String description;
    private String producer;
    private String director;
    private String imageUrl;
}
// File: ./src/main/java/project/community/theatre/dto/responseDto/TicketResponse.java

package project.community.theatre.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {
    private String ticketNumber;
    private Double totalPrice;
    private String seatNumbers;
    private String showTime;
    private String eventName;
    private LocalDateTime bookingTime;
    private String status;
}

// File: ./src/main/java/project/community/theatre/dto/responseDto/AuthResponse.java

package project.community.theatre.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String message;
    private String userId;
    private String role;
    private String token;
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/LoginRequest.java

package project.community.theatre.dto.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/SignupRequest.java

package project.community.theatre.dto.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Mobile number is required")
    private String mobileNo;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String role;
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/AddShowTimesRequestDto.java

package project.community.theatre.dto.requestDto;

import lombok.Data;

import java.util.List;

@Data
public class AddShowTimesRequestDto {

    private String eventId;
    private List<String> showTimes;
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/UserEntryDto.java

package project.community.theatre.dto.requestDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEntryDto {
    String name;
    String mobNo;
}

// File: ./src/main/java/project/community/theatre/dto/requestDto/PaymentRequest.java

package project.community.theatre.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.community.theatre.dto.AddressDto;
import project.community.theatre.dto.PaymentDetailsDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private PaymentDetailsDto paymentDetails;
    private AddressDto address;
    private String name;
    private Double payableAmount;
    private String userId;
    private String email;
    private String eventId;
    private String showId;
    private String showTime;
    private List<String> seatNumbers;
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/DeleteShowTimeRequestDto.java

package project.community.theatre.dto.requestDto;

import lombok.Data;

@Data
public class DeleteShowTimeRequestDto {

    private String eventId;
    private String showTime; // Specific show time to delete
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/ReviewRequestDto.java

package project.community.theatre.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {
    private String userName;
    private Integer rating;
    private String description;
    private String eventId;
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/DiscountRequest.java

package project.community.theatre.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.AliasFor;
import project.community.theatre.dto.TicketType;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountRequest {
    private Map<String, TicketType> bands;
    @JsonProperty("isSocialClub")
    private boolean isSocialClub;
    private int totalTickets;
    private String day;
    private LocalDateTime showTime;
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/EventEntryDto.java

package project.community.theatre.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EventEntryDto {
    @NotNull(message = "ID cannot be null")
    private String eventId;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Genre cannot be null")
    private String genre;
    @NotNull(message = "Start date cannot be null")
    private String startDate;
    @NotNull(message = "End date cannot be null")
    private String endDate;
    @NotNull(message = "Duration cannot be null")
    private String duration;
    private String description;
    private String producer;
    private String director;
    private MultipartFile image;
}

// File: ./src/main/java/project/community/theatre/dto/AddressDto.java

package project.community.theatre.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
    private String street;
    private String apartment;
    private String city;
    private String country;
    private String state;
    private String zipCode;
}

// File: ./src/main/java/project/community/theatre/dto/PaymentDetailsDto.java

package project.community.theatre.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailsDto {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

}



// File: ./src/main/java/project/community/theatre/filter/JwtRequestFilter.java

package project.community.theatre.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project.community.theatre.service.UserService;
import project.community.theatre.util.JwtUtil;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    /**
     * Validates the JWT token provided in the "Authorization" header of the request, and authenticates the user if the
     * token is valid.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param chain    the filter chain
     * @throws ServletException if an error occurs during the filter chain
     * @throws IOException       if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String userId = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            userId = jwtUtil.extractUserId(jwt);
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(userId);

            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
// File: ./src/main/java/project/community/theatre/util/PasswordEncoderUtil.java

package project.community.theatre.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A utility class for encoding and matching passwords using BCrypt.
 * This class is a Spring component, which means it can be injected into other classes.
 */
@Component
public class PasswordEncoderUtil {

    /**
     * A static instance of BCryptPasswordEncoder for encoding passwords.
     */
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Encodes a raw password using BCrypt.
     *
     * @param rawPassword The raw password to be encoded.
     * @return The encoded password.
     */
    public String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    /**
     * Checks if a raw password matches an encoded password.
     *
     * @param rawPassword The raw password to be checked.
     * @param encodedPassword The encoded password to be matched.
     * @return True if the raw password matches the encoded password, false otherwise.
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
// File: ./src/main/java/project/community/theatre/util/QRCodeUtil.java

package project.community.theatre.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
/**
 * Utility class for generating QR codes for community theatre tickets.
 */
@Slf4j
public class QRCodeUtil {

    /**
     * Generates a QR code for a given ticket UUID and returns it as a byte array in PNG format.
     *
     * @param uuid    The unique identifier of the ticket.
     * @param width   The width of the generated QR code image.
     * @param height  The height of the generated QR code image.
     * @return A byte array containing the QR code image in PNG format.
     * @throws RuntimeException If an error occurs during QR code generation or writing to the output stream.
     */
    public static byte[] generateTicketQRCode(String uuid, int width, int height) {
        log.info("Generating QR code for ticket: {}", uuid);
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(uuid, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
            return baos.toByteArray();
        } catch (WriterException e) {
            // Handle QR code generation errors
            throw new RuntimeException("Failed to generate QR code: " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle I/O errors from ByteArrayOutputStream
            throw new RuntimeException("Failed to write QR code to stream: " + e.getMessage(), e);
        }
    }
}
// File: ./src/main/java/project/community/theatre/util/CustomMultipartFile.java

package project.community.theatre.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Custom implementation of Spring's MultipartFile interface to handle file uploads.
 * This class wraps a File object and provides methods to retrieve file information and content.
 */
public class CustomMultipartFile implements MultipartFile {

    private final File file;
    private final String originalFilename;

    /**
     * Constructs a new CustomMultipartFile instance.
     *
     * @param file The underlying File object representing the uploaded file.
     * @param originalFilename The original filename provided by the client.
     */
    public CustomMultipartFile(File file, String originalFilename) {
        this.file = file;
        this.originalFilename = originalFilename;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return "image/" + getExtension(originalFilename);
    }

    @Override
    public boolean isEmpty() {
        return file.length() == 0;
    }

    @Override
    public long getSize() {
        return file.length();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        Files.copy(file.toPath(), dest.toPath());
    }

    /**
     * Extracts the file extension from the given filename.
     *
     * @param filename The filename to extract the extension from.
     * @return The extracted file extension. If no extension is found, returns "jpg".
     */
    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "jpg";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
// File: ./src/main/java/project/community/theatre/util/EmailService.java

package project.community.theatre.util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

/**
 * This service is responsible for sending emails with attachments using SendGrid.
 */
@Service
@Slf4j
public class EmailService {
    private final SendGrid sendGrid;

    /**
     * Constructor to initialize the SendGrid client with the provided API key.
     *
     * @param apiKey The API key for SendGrid.
     */
    public EmailService(@Value("${sendgrid.api.key}") String apiKey) {
        this.sendGrid = new SendGrid(apiKey);
    }

    /**
     * Sends an email with a PDF attachment to the specified recipient.
     *
     * @param to The email address of the recipient.
     * @param name The name of the recipient.
     * @param pdfBytes The byte array representing the PDF content.
     */
    public void sendEmailWithPDF(String to, String name, byte[] pdfBytes) {
        log.info("Sending email with PDF attachment to {}", to);
        try {
            // Set up email details
            Email from = new Email("collegeonlineclass@gmail.com");
            String subject = "GCT Ticket booking confirmation";
            Email toEmail = new Email(to);
            Content content = new Content("text/plain", "Dear " + name + ",\n\nHere is your GCT ticket booking confirmation for the event");

            // Create mail object
            Mail mail = new Mail(from, subject, toEmail, content);

            // Add PDF attachment
            Attachments attachments = new Attachments();
            attachments.setContent(Base64.getEncoder().encodeToString(pdfBytes));
            attachments.setType("application/pdf");
            attachments.setFilename("ticket.pdf");
            attachments.setDisposition("attachment");
            mail.addAttachments(attachments);

            // Send the email
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                log.info("Email sent successfully to {}", to);
            } else {
                log.error("Failed to send email to {}. Status: {}, Body: {}", to, response.getStatusCode(), response.getBody());
                throw new RuntimeException("Email sending failed with status: " + response.getStatusCode());
            }
        } catch (IOException e) {
            log.error("IOException sending email to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Failed to send email due to I/O error: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error sending email to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Unexpected error while sending email: " + e.getMessage(), e);
        }
    }
}
// File: ./src/main/java/project/community/theatre/util/PDFUtil.java

package project.community.theatre.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import project.community.theatre.model.TicketEntity;
import project.community.theatre.repository.EventRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Utility class for creating PDF tickets for community theatre events.
 *
 */
@Slf4j
@UtilityClass
public class PDFUtil {
    private EventRepository eventRepository;

    /**
     * Creates a PDF ticket for the given ticket entity and QR code bytes.
     *
     * @param qrCodeBytes The byte array representing the QR code image.
     * @param ticket      The ticket entity containing the ticket details.
     * @return A byte array representing the generated PDF ticket.
     * @throws RuntimeException If an error occurs while creating the PDF document or handling I/O operations.
     */
    public static byte[] createPDF(byte[] qrCodeBytes, TicketEntity ticket) {
        log.info("Creating PDF for ticket: {}", ticket.getTicketNumber());
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Header
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
            Paragraph header = new Paragraph("Greenwich Community Theatre - Event Ticket", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setSpacingAfter(20f);
            document.add(header);

            // Ticket Details
            Font detailFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
            Paragraph ticketDetails = new Paragraph();
            ticketDetails.setAlignment(Element.ALIGN_LEFT);
            ticketDetails.add(new Chunk("Ticket ID: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getTicketNumber() + "\n", detailFont));
            ticketDetails.add(new Chunk("Name: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getUser().getName() + "\n", detailFont));
            ticketDetails.add(new Chunk("Event: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getEvent().getName(), detailFont));
            ticketDetails.add(new Chunk("\nDate: ", detailFont));
            ticketDetails.add(new Chunk(DateTimeConverter.getHumanReadableDate(ticket.getShowTime()), detailFont));
            ticketDetails.add(new Chunk("\nTime: ", detailFont));
            ticketDetails.add(new Chunk(DateTimeConverter.getHumanReadableTime(ticket.getShowTime()), detailFont));
            ticketDetails.add(new Chunk("\nSeat Numbers: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getSeatNumbers(), detailFont));
            ticketDetails.add(new Chunk("\nTotal Price: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getTotalPrice() + "\n", detailFont));
            ticketDetails.add(new Chunk("\nVenue: ", detailFont));
            ticketDetails.add(new Chunk("GCT Main Stage", detailFont));
            ticketDetails.setSpacingAfter(20f);
            document.add(ticketDetails);

            // Centered QR Code
            Image qrImage = Image.getInstance(qrCodeBytes);
            qrImage.scaleToFit(150, 150);
            qrImage.setAlignment(Image.ALIGN_CENTER);
            qrImage.setSpacingBefore(10f);
            qrImage.setSpacingAfter(20f);
            document.add(qrImage);

            // Footer
            Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);
            Paragraph footer = new Paragraph("Thank you for choosing GCT! Please present this ticket at the entrance. Enjoy the show!", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return baos.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to create PDF document: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("I/O error while creating PDF: " + e.getMessage(), e);
        }
    }
}
// File: ./src/main/java/project/community/theatre/util/DateTimeConverter.java

package project.community.theatre.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class provides utility methods for converting date and time from ISO 8601 format to human-readable format.
 */
public class DateTimeConverter {
    /**
     * Converts a given ISO 8601 date-time to a human-readable date format.
     *
     * @param isoDateTime The ISO 8601 date-time to be converted.
     * @return A string representing the date in the format "MMMM dd, yyyy".
     */
    public static String getHumanReadableDate(LocalDateTime isoDateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return isoDateTime.format(dateFormatter);
    }

    /**
     * Converts a given ISO 8601 date-time to a human-readable time format.
     *
     * @param isoDateTime The ISO 8601 date-time to be converted.
     * @return A string representing the time in the format "h:mm a".
     */
    // Function to return the time in human-readable format
    public static String getHumanReadableTime(LocalDateTime isoDateTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        return isoDateTime.format(timeFormatter);
    }

}

// File: ./src/main/java/project/community/theatre/util/JwtUtil.java

package project.community.theatre.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration; // Token expiration time in milliseconds

    /**
     * Generate a JWT token.
     *
     * @param userId The unique identifier of the user.
     * @param role The role of the user.
     * @return A JWT token containing the user's ID and role.
     */
    public String generateToken(String userId, String role) {
        log.info("Generating JWT token for user: {}", userId);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Extract the user ID from the JWT token.
     *
     * @param token The JWT token.
     * @return The user ID extracted from the token.
     */
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract the user role from the JWT token.
     *
     * @param token The JWT token.
     * @return The user role extracted from the token.
     */
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    /**
     * Extract the expiration date from the JWT token.
     *
     * @param token The JWT token.
     * @return The expiration date extracted from the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Check if the JWT token is expired.
     *
     * @param token The JWT token.
     * @return True if the token is expired, false otherwise.
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validate the JWT token.
     *
     * @param token The JWT token.
     * @param userId The expected user ID.
     * @return True if the token is valid and not expired, false otherwise.
     */
    public Boolean validateToken(String token, String userId) {
        final String extractedUserId = extractUserId(token);
        return (extractedUserId.equals(userId) && !isTokenExpired(token));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
// File: ./src/main/java/project/community/theatre/repository/BandRepository.java

package project.community.theatre.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.BandEntity;

import java.util.List;
import java.util.Optional;

public interface BandRepository extends JpaRepository<BandEntity, String> {
    @NotNull List<BandEntity> findAll();

    Optional<BandEntity> findByBandId(String bandId);

    void deleteByBandId(String bandId);

    boolean existsByBandId(String discountType);
}
// File: ./src/main/java/project/community/theatre/repository/ShowTimeRepository.java

package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.community.theatre.model.EventEntity;
import project.community.theatre.model.ShowTimeEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTimeEntity, Long> {
    @Query("SELECT e FROM EventEntity e WHERE e.id = :id")
    Optional<EventEntity> findEventById(@Param("id") String id);

    List<ShowTimeEntity> findByEvent(EventEntity event);
}
// File: ./src/main/java/project/community/theatre/repository/UserRepository.java

package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.community.theatre.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    Optional<UserEntity> findUserById(@Param("id") String id);
}
// File: ./src/main/java/project/community/theatre/repository/ReviewRepository.java

package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.ReviewEntity;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
    List<ReviewEntity> findByEvent_EventId(String eventId);
}
// File: ./src/main/java/project/community/theatre/repository/TicketRepository.java

package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.community.theatre.model.EventEntity;
import project.community.theatre.model.TicketEntity;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    Optional<TicketEntity> findByTicketNumber(String ticketNumber);
    @Query("SELECT e FROM EventEntity e WHERE e.id = :id")
    List<TicketEntity> findEventById(@Param("id") String id);
}
// File: ./src/main/java/project/community/theatre/repository/EventRepository.java

package project.community.theatre.repository;

import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import project.community.theatre.model.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Integer> {

    @Query("SELECT e FROM EventEntity e WHERE e.id = :id")
    Optional<EventEntity> findEventById(@Param("id") String id);
}
// File: ./src/main/java/project/community/theatre/repository/PaymentHistoryRepository.java

package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.PaymentHistoryEntity;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, Long> {
}
// File: ./src/main/java/project/community/theatre/repository/DiscountRepository.java

package project.community.theatre.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.DiscountEntity;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {
    Optional<DiscountEntity> findByDiscountType(String discountType);

    boolean existsById(@NotNull String id);
    void deleteById(@NotNull String id);
}
// File: ./src/main/java/project/community/theatre/exception/DiscountNotFoundException.java

package project.community.theatre.exception;

public class DiscountNotFoundException extends RuntimeException {
    public DiscountNotFoundException(String message) {
        super(message);
    }
}
// File: ./src/main/java/project/community/theatre/exception/UserNotFoundException.java

package project.community.theatre.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
// File: ./src/main/java/project/community/theatre/exception/ErrorResponse.java

package project.community.theatre.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final String details;

    public ErrorResponse(LocalDateTime timestamp, int status, String message, String details) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.details = details;
    }
}
// File: ./src/main/java/project/community/theatre/exception/GlobalExceptionHandler.java

package project.community.theatre.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles ResourceNotFoundException and returns a ResponseEntity with an ErrorResponse and a NOT_FOUND status.
     *
     * @param ex The ResourceNotFoundException that occurred.
     * @param request The WebRequest object.
     * @return A ResponseEntity with an ErrorResponse and a NOT_FOUND status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles InvalidImageException and returns a ResponseEntity with an ErrorResponse and a BAD_REQUEST status.
     *
     * @param ex The InvalidImageException that occurred.
     * @param request The WebRequest object.
     * @return A ResponseEntity with an ErrorResponse and a BAD_REQUEST status.
     */
    @ExceptionHandler(InvalidImageException.class)
    public ResponseEntity<ErrorResponse> handleInvalidImageException(InvalidImageException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles PaymentFailedException and returns a ResponseEntity with an ErrorResponse and a BAD_REQUEST status.
     *
     * @param ex The PaymentFailedException that occurred.
     * @param request The WebRequest object.
     * @return A ResponseEntity with an ErrorResponse and a BAD_REQUEST status.
     */
    // Handle PaymentFailedException
    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<ErrorResponse> handlePaymentFailedException(PaymentFailedException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UserAlreadyExistsException and returns a ResponseEntity with a conflict status and an error message.
     *
     * @param ex The UserAlreadyExistsException that occurred.
     * @return A ResponseEntity with a conflict status and an error message.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Handles UserNotFoundException and returns a ResponseEntity with an unauthorized status and an error message.
     *
     * @param ex The UserNotFoundException that occurred.
     * @return A ResponseEntity with an unauthorized status and an error message.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Handles DiscountNotFoundException and returns a ResponseEntity with a not found status and an error message.
     *
     * @param ex The DiscountNotFoundException that occurred.
     * @return A ResponseEntity with a not found status and an error message.
     */
    @ExceptionHandler(DiscountNotFoundException.class)
    public ResponseEntity<?> handleDiscountNotFoundException(DiscountNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles BandNotFoundException and returns a ResponseEntity with a not found status and an error message.
     *
     * @param ex The BandNotFoundException that occurred.
     * @return A ResponseEntity with a not found status and an error message.
     */
    @ExceptionHandler(BandNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBandNotFoundException(BandNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles generic exceptions and returns a ResponseEntity with an ErrorResponse and an internal server error status.
     *
     * @param ex The Exception that occurred.
     * @param request The WebRequest object.
     * @return A ResponseEntity with an ErrorResponse and an internal server error status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Overrides the handleMethodArgumentNotValid method to handle validation errors and returns a ResponseEntity with a bad request status and a map of field errors.
     *
     * @param ex The MethodArgumentNotValidException that occurred.
     * @param headers The HttpHeaders object.
     * @param status The HttpStatusCode object.
     * @param request The WebRequest object.
     * @return A ResponseEntity with a bad request status and a map of field errors.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
// File: ./src/main/java/project/community/theatre/exception/UserAlreadyExistsException.java

package project.community.theatre.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
// File: ./src/main/java/project/community/theatre/exception/BandNotFoundException.java

package project.community.theatre.exception;

public class BandNotFoundException extends RuntimeException {
    public BandNotFoundException(String message) {
        super(message);
    }
}
// File: ./src/main/java/project/community/theatre/exception/PaymentFailedException.java

package project.community.theatre.exception;

public class PaymentFailedException extends RuntimeException {

    public PaymentFailedException(String message) {
        super(message);
    }
}
// File: ./src/main/java/project/community/theatre/exception/ResourceNotFoundException.java

package project.community.theatre.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
// File: ./src/main/java/project/community/theatre/exception/InvalidImageException.java

package project.community.theatre.exception;

public class InvalidImageException extends RuntimeException {
    public InvalidImageException(String message) {
        super(message);
    }
}
// File: ./src/main/java/project/community/theatre/controller/UserController.java

package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.exception.ResourceNotFoundException;
import project.community.theatre.model.UserEntity;
import project.community.theatre.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Retrieves a user entity by its unique identifier.
     *
     * @param userId the unique identifier of the user to be retrieved. Must not be null or empty.
     * @return a ResponseEntity containing the user entity if found, or an appropriate error response if not found.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String userId) {
        log.info("Received request to fetch user by ID: {}", userId);
        try {
            UserEntity user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(404).body(null);
        }
    }

    /**
     * Retrieves all user entities from the database.
     *
     * @return a ResponseEntity containing a list of user entities, or an appropriate error response if the list is empty.
     */
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        log.info("Received request to fetch all users");
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Updates a user entity with the provided request body.
     *
     * @param userId    the unique identifier of the user to be updated. Must not be null or empty.
     * @param updatedUser the updated user entity to be saved. Must not be null.
     *
     * @return a ResponseEntity containing the updated user entity, or an appropriate error response if not found.
     */
    @PutMapping("/{userId}/update")
    public ResponseEntity<UserEntity> updateUser(@PathVariable String userId, @RequestBody UserEntity updatedUser) {
    UserEntity updated = userService.updateUser(userId, updatedUser);
    return ResponseEntity.ok(updated);
}


}
// File: ./src/main/java/project/community/theatre/controller/SeatController.java

package project.community.theatre.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.constant.AppConstants;
import project.community.theatre.service.impl.SeatServiceImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(AppConstants.BASE_URL + "/seats")
@CrossOrigin("*")
@Slf4j
public class SeatController {

    @Autowired
    private SeatServiceImpl seatService;

    /**
     * This endpoint verifies and locks the given seats for a specific event and show ID. It takes the event ID,
     * show ID and a list of seat numbers as input and returns a ResponseEntity containing the result of the
     * verification. If the seats are available, it locks the seats and returns a map containing a status flag
     * set to true. If the seats are unavailable, it returns a map containing a status flag set to false and
     * a list of unavailable seats. If an unexpected error occurs, it returns a ResponseEntity with a status of
     * INTERNAL_SERVER_ERROR and a map containing the error message.
     *
     * @param eventId the event ID
     * @param showId the show ID
     * @param seatNumbers the list of seat numbers
     * @return a ResponseEntity containing the result of the verification
     */
    @PostMapping("/verify/{eventId}/{showId}")
    public ResponseEntity<?> verifySeatStatus(@PathVariable String eventId, @PathVariable String showId,
                                              @RequestBody List<String> seatNumbers) {
        log.info("Received request to verify and lock seats for event ID: {}, show ID: {} and seats: {}", eventId, showId, seatNumbers);
        try {
            
            Map<String, Object> result = seatService.processSeatsAvailability(eventId, showId, seatNumbers);

            if (Boolean.FALSE.equals(result.get("status"))) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * This endpoint fetches the list of booked seats for a specific event and show ID. It takes the event ID and
     * show ID as input and returns a ResponseEntity containing the list of booked seats. If an unexpected error
     * occurs, it returns a ResponseEntity with a status of INTERNAL_SERVER_ERROR and a map containing the error
     * message.
     *
     * @param eventId the event ID
     * @param showId the show ID
     * @return a ResponseEntity containing the list of booked seats
     */
    @GetMapping("/booked-seats/{eventId}/{showId}")
    public ResponseEntity<List<String>> getAllBookedSeats(@PathVariable String eventId, @PathVariable String showId) {
        log.info("Fetching locked seats for event ID: {} and show ID: {}", eventId, showId);
    
        List<String> lockedSeats = seatService.getAllBookedSeats(eventId, showId);
    
        return ResponseEntity.ok(lockedSeats);
    }
}
// File: ./src/main/java/project/community/theatre/controller/BandController.java

package project.community.theatre.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.model.BandEntity;
import project.community.theatre.service.BandService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bands")
@RequiredArgsConstructor
@Slf4j
public class BandController {

    private final BandService bandService;

    /**
     * Retrieves all band entities from the database.
     *
     * @return a ResponseEntity containing a list of band entities, or an appropriate error response if the list is empty.
     */
    @GetMapping(value = "/all")
    public ResponseEntity<List<BandEntity>> getAllBands() {
        log.info("Received request to get all bands");
        List<BandEntity> bands = bandService.getAllBands();
        return ResponseEntity.ok(bands);
    }

    /**
     * Creates or updates a band entity.
     *
     * @param band the band entity to be created or updated. Must be valid and not null.
     * @return a ResponseEntity containing the saved band entity.
     */
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<BandEntity> createOrUpdateBand(@Valid @RequestBody BandEntity band) {
        log.info("Received request to create bands");
        BandEntity savedBand = bandService.createBand(band);
        return ResponseEntity.ok(savedBand);
    }

    /**
     * Retrieves a band entity by its unique identifier.
     *
     * @param bandId the unique identifier of the band to be retrieved. Must not be null or empty.
     * @return a ResponseEntity containing the band entity if found, or an appropriate error response if not found.
     */
    @GetMapping("/get/{bandId}")
    public ResponseEntity<BandEntity> getBandById(@PathVariable String bandId) {
        log.info("Fetching band for ID: {}", bandId);
        BandEntity band = bandService.getBandById(bandId);
        return ResponseEntity.ok(band);
    }

    /**
     * Deletes a band entity by its unique identifier.
     *
     * @param bandId the unique identifier of the band to be deleted. Must not be null or empty.
     * @return a ResponseEntity with an empty body indicating success, or an appropriate error response if the entity
     * could not be found.
     */
    @DeleteMapping("/{bandId}")
    public ResponseEntity<Void> deleteBand(@PathVariable String bandId) {
        log.info("Received request to delete band for ID: {}", bandId);
        bandService.deleteBand(bandId);
        return ResponseEntity.ok().build();
    }
}
// File: ./src/main/java/project/community/theatre/controller/TicketController.java

package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.responseDto.TicketResponse;
import project.community.theatre.exception.ResourceNotFoundException;
import project.community.theatre.service.TicketService;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketController {

    private final TicketService ticketService;

    /**
     * Retrieves the ticket details for a given ticket number.
     *
     * @param ticketNumber the ticket number to fetch details for
     * @return a ResponseEntity containing the ticket details. If the ticket is not found, a 404 response is returned.
     */
    @GetMapping("/{ticketNumber}")
    public ResponseEntity<TicketResponse> getTicketDetails(@PathVariable String ticketNumber) {
        log.info("Received request to fetch ticket details for ticket number: {}", ticketNumber);

        try {
            TicketResponse ticketResponse = ticketService.getTicketDetails(ticketNumber);
            return ResponseEntity.ok(ticketResponse);
        } catch (ResourceNotFoundException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(404).body(null); // Return 404 if ticket not found
        }
    }
}
// File: ./src/main/java/project/community/theatre/controller/EventController.java

package project.community.theatre.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.community.theatre.dto.responseDto.ShowTimeResponseDto;
import project.community.theatre.dto.requestDto.AddShowTimesRequestDto;
import project.community.theatre.dto.requestDto.DeleteShowTimeRequestDto;
import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;
import project.community.theatre.service.EventService;

import java.util.List;

/**
 * This class is a controller for handling event-related operations.
 * It provides RESTful endpoints for fetching, adding, and deleting events, as well as managing their show times.
 *
 */
@RestController
@RequestMapping("/event")
@CrossOrigin("*")
@Slf4j
public class EventController {

    @Autowired
    private EventService eventService;

    /**
     * This method fetches an event by its ID.
     *
     * @param id The unique identifier of the event to fetch.
     * @return A ResponseEntity containing the fetched event wrapped in an EventResponseDto object.
     */
    @GetMapping(value = "/get-event")
    public ResponseEntity<EventResponseDto> getEvent(@RequestParam("id") String id) {
        log.info("Received request to fetch event with ID: {}", id);

        EventResponseDto eventResponseDto = eventService.getEvent(id);

        log.info("Returning event: {}", eventResponseDto);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.OK);
    }

    /**
     * This method fetches all events.
     *
     * @return A ResponseEntity containing a list of all events wrapped in an EventResponseDto object.
     */
    @GetMapping(value = "/get-all-events")
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        log.info("Received request to fetch all events");

        List<EventResponseDto> events = eventService.getAllEvent();

        log.info("Returning {} events", events.size());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * This method adds a new event.
     *
     * @param eventEntryDto The details of the new event to be added.
     * @param image         The image file associated with the new event.
     * @return A ResponseEntity containing the newly added event wrapped in an EventResponseDto object.
     */
    @PostMapping(value = "/add-event", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponseDto> addEvent(
            @Valid @RequestPart("eventDetails") EventEntryDto eventEntryDto,
            @RequestPart("image") MultipartFile image) {
        log.info("Received request to add a new event: {}", eventEntryDto);
        eventEntryDto.setImage(image);

        EventResponseDto eventResponseDto = eventService.addEvent(eventEntryDto);

        log.info("Event added successfully: {}", eventResponseDto);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.CREATED);
    }

    /**
     * This method deletes an event by its ID.
     *
     * @param eventId The unique identifier of the event to be deleted.
     * @return A ResponseEntity with an empty body and an OK status code.
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String eventId) {
        log.info("Received request to delete event with ID: {}", eventId);

        eventService.deleteEvent(eventId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method adds show times for a specific event.
     *
     * @param request The details of the new show time to be added.
     * @return A ResponseEntity with an empty body and an OK status code.
     */
    @PostMapping("/add-show-times")
    public ResponseEntity<Void> addShowTimes(@RequestBody AddShowTimesRequestDto request) {
        log.info("Received request to add show times for event ID: {}", request.getEventId());

        eventService.addShowTimes(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method fetches show times for a specific event.
     *
     * @param eventId The unique identifier of the event to fetch its show times.
     * @return A ResponseEntity containing a list of show times wrapped in a ShowTimeResponseDto object.
     */
    @GetMapping("/{eventId}/get-show-times")
    public ResponseEntity<List<ShowTimeResponseDto>> getShowTimesForEvent(@PathVariable String eventId) {
        log.info("Received request to fetch show times for event ID: {}", eventId);

        List<ShowTimeResponseDto> showTimes = eventService.getShowTimesForEvent(eventId);

        return new ResponseEntity<>(showTimes, HttpStatus.OK);
    }

    /**
     * This method deletes a specific showtime for a specific event.
     *
     * @param request The details of the showtime to be deleted.
     * @return A ResponseEntity with an empty body and an OK status code.
     */
    @DeleteMapping("/delete-show-time")
    public ResponseEntity<Void> deleteShowTime(@RequestBody DeleteShowTimeRequestDto request) {
        log.info("Received request to delete show time {} for event ID: {}", request.getShowTime(), request.getEventId());

        eventService.deleteShowTime(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
// File: ./src/main/java/project/community/theatre/controller/PaymentController.java

package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.responseDto.TicketResponse;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.service.PaymentService;
import project.community.theatre.service.TicketService;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    private final TicketService ticketService;

    /**
     * Processes a payment request and generates a ticket if the payment is successful.
     *
     * @param paymentRequest The request containing payment details.
     * @return A ResponseEntity containing the generated ticket if the payment is successful, otherwise a bad request response with the payment response.
     */
    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest paymentRequest) {
        log.info("Received payment request: {}", paymentRequest);
        // Process the payment
        PaymentResponse paymentResponse = paymentService.processPayment(paymentRequest);
    
        if (!paymentResponse.isSuccess()) {
            return ResponseEntity.badRequest().body(paymentResponse);
        }
    
        // Generate and save the ticket
        TicketResponse ticketResponse = ticketService.generateAndSaveTicket(paymentRequest, paymentResponse.getTransactionId());
    
        return ResponseEntity.ok(ticketResponse);
    }
}
// File: ./src/main/java/project/community/theatre/controller/ReviewController.java

package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.requestDto.ReviewRequestDto;
import project.community.theatre.dto.responseDto.ReviewResponseDto;
import project.community.theatre.model.ReviewEntity;
import project.community.theatre.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * This method saves a new review for an event.
     *
     * @param request A ReviewRequestDto object containing the details of the review to be saved.
     * @return A ResponseEntity containing the newly saved ReviewEntity object.
     */
    @PostMapping("/save")
    public ResponseEntity<ReviewEntity> saveReview(@RequestBody ReviewRequestDto request) {
        log.info("Received request to save review for event ID: {}", request.getEventId());

        // Save the review
        ReviewEntity savedReview = reviewService.saveReview(
                request.getUserName(),
                request.getRating(),
                request.getDescription(),
                request.getEventId()
        );

        return ResponseEntity.ok(savedReview);
    }

    /**
     * This method fetches all reviews for a specific event.
     *
     * @param eventId The unique identifier of the event to fetch reviews for.
     * @return A ResponseEntity containing a list of ReviewEntity objects.
     */
    @GetMapping("/all/{eventId}")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews(@PathVariable String eventId) {
        log.info("Received request to fetch all reviews for event ID: {}", eventId);

        // Fetch all reviews for the event
        List<ReviewResponseDto> reviews = reviewService.getAllReviews(eventId);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
// File: ./src/main/java/project/community/theatre/controller/AuthController.java

package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.requestDto.LoginRequest;
import project.community.theatre.dto.requestDto.SignupRequest;
import project.community.theatre.dto.responseDto.AuthResponse;
import project.community.theatre.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    /**
     * Handles user signup requests.
     * 
     * This method processes a signup request, creates a new user account,
     * and returns an authentication response.
     *
     * @param request The SignupRequest object containing user registration details.
     * @return ResponseEntity<AuthResponse> A ResponseEntity containing the AuthResponse
     *         with details such as authentication token and user information.
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request) {
        log.info("Received request to signup with email: {}", request.getEmail());
        AuthResponse response = userService.signup(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Handles user login requests.
     * 
     * This method authenticates a user based on the provided login credentials
     * and returns an authentication response.
     *
     * @param request The LoginRequest object containing user login credentials.
     * @return ResponseEntity<AuthResponse> A ResponseEntity containing the AuthResponse
     *         with details such as authentication token and user information.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        log.info("Received request to login with email: {}", request.getEmail());
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
// File: ./src/main/java/project/community/theatre/controller/DiscountController.java

package project.community.theatre.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.requestDto.DiscountRequest;
import project.community.theatre.dto.responseDto.DiscountResponse;
import project.community.theatre.model.DiscountEntity;
import project.community.theatre.service.DiscountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
@Slf4j
public class DiscountController {

    private final DiscountService discountService;

    /**
     * Calculates the discount based on the provided request details.
     *
     * @param request the DiscountRequest object containing the necessary
     *                information to calculate the discount.
     * @return a ResponseEntity containing a DiscountResponse object with
     *         the calculated discount details, wrapped in an HTTP status of OK.
     */
    @PostMapping("/calculate")
    public ResponseEntity<DiscountResponse> calculateDiscount(@RequestBody DiscountRequest request) {
        DiscountResponse response = discountService.calculateDiscount(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a list of all discounts.
     *
     * @return a ResponseEntity containing the list of DiscountEntity objects,
     *         wrapped in an HTTP status of OK.
     */
    @GetMapping(value ="/all-discounts")
    public ResponseEntity<List<DiscountEntity>> getAllDiscounts() {
        log.info("Received request to get all discounts");
        List<DiscountEntity> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    /**
     * Creates or updates a discount entity.
     *
     * @param discount the DiscountEntity to be created or updated. Must be valid and not null.
     * @return a ResponseEntity containing the saved DiscountEntity, wrapped in an HTTP status of OK.
     */
    @PostMapping(value = "/create-discount" , consumes = "application/json"  )
    public ResponseEntity<DiscountEntity> createOrUpdateDiscount(@Valid @RequestBody DiscountEntity discount) {
        log.info("Received request to create a discount");
        DiscountEntity savedDiscount = discountService.createOrUpdateDiscount(discount);
        return ResponseEntity.ok(savedDiscount);
    }

    /**
     * Retrieves a discount entity based on the provided discount type.
     *
     * @param discountType the unique identifier of the discount type to fetch the discount for.
     * @return a ResponseEntity containing the DiscountEntity object with the discount details for the specified type, wrapped in an HTTP status of OK.
     *         If no discount is found for the given type, an appropriate error status will be returned.
     */
    @GetMapping("/type/{discountType}")
    public ResponseEntity<DiscountEntity> getDiscountByType(@PathVariable String discountType) {
        log.info("Fetching discount for type: {}", discountType);
        DiscountEntity discount = discountService.getDiscountByType(discountType);
        return ResponseEntity.ok(discount);
    }

    /**
     * Deletes a discount based on the provided discount ID.
     *
     * @param id the unique identifier of the discount to be deleted.
     *           It should be a valid string representing the discount ID.
     * @return a ResponseEntity with an HTTP status of OK if the discount
     *         was successfully deleted, or an appropriate error status otherwise.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable String id) {
        log.info("Received request to delete discount for id: {}", id);
        discountService.deleteDiscount(id);
        return ResponseEntity.ok().build();
    }
}
// File: ./src/main/java/project/community/theatre/config/CloudinaryConfig.java

package project.community.theatre.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Cloudinary integration.
 * This class sets up the Cloudinary bean with the necessary configuration.
 */
@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    /**
     * Creates and configures a Cloudinary instance.
     * 
     * This method sets up a Cloudinary object with the cloud name, API key, and API secret
     * injected from the application properties.
     *
     * @return A configured Cloudinary instance ready for use in the application.
     */
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }
}
// File: ./src/main/java/project/community/theatre/config/SecurityConfig.java

package project.community.theatre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.community.theatre.filter.JwtRequestFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * Configuration class for setting up security in the application.
 * This class is responsible for configuring web security settings,
 * including CORS, CSRF, and authentication rules.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final JwtRequestFilter jwtRequestFilter;

    /**
     * Constructs a new SecurityConfig with the specified JWT request filter.
     *
     * @param jwtRequestFilter The JWT request filter to be used for authentication.
     */
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Configures the security filter chain for the application.
     * This method sets up CSRF protection, CORS, request authorization rules,
     * session management, and adds the JWT request filter.
     *
     * @param http The HttpSecurity object to be configured.
     * @return A SecurityFilterChain object representing the configured security filters.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/signup", "/api/v1/auth/login", "/event/get-all-events").permitAll()
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configures and provides a CORS configuration source.
     * This method sets up CORS settings including allowed origins, methods, headers,
     * credentials, and max age for preflight requests.
     *
     * @return A CorsConfigurationSource object with the configured CORS settings.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true); // true if using cookies or JWT tokens
        configuration.setMaxAge(3600L); // Cache preflight for 1 hour
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
// File: ./src/main/java/project/community/theatre/constant/AppConstants.java

package project.community.theatre.constant;

/**
 * This class contains constant values used throughout the application.
 * It includes API versioning, base URL, and file upload size limits.
 */
public class AppConstants {

    // General Application Constants
    public static final String API_VERSION = "/v1";
    public static final String BASE_URL = "/api"+API_VERSION;

    // File Upload Constants
    public static final long MAX_FILE_SIZE_BYTES = 5 * 1024 * 1024; // 5MB
    public static final int IMAGE_WIDTH = 800;
    public static final int IMAGE_HEIGHT = 600;
    public static final double IMAGE_QUALITY = 1.0;

}

// File: ./src/main/java/project/community/theatre/GreenwichCommunityTheatre.java

package project.community.theatre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GreenwichCommunityTheatre {

	public static void main(String[] args) {
		SpringApplication.run(GreenwichCommunityTheatre.class, args);
	}

}

// File: ./src/test/java/project/community/theatre/GreenwichCommunityTheatreTests.java

package project.community.theatre;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GreenwichCommunityTheatreTests {

	@Test
	void contextLoads() {
	}

}

// File: ./merged_project.java


// File: ./src/main/java/project/community/theatre/GreenwichCommunityTheatre.java

package project.community.theatre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GreenwichCommunityTheatre {

	public static void main(String[] args) {
		SpringApplication.run(GreenwichCommunityTheatre.class, args);
	}

}

// File: ./src/main/java/project/community/theatre/config/CloudinaryConfig.java

package project.community.theatre.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Cloudinary integration.
 * This class sets up the Cloudinary bean with the necessary configuration.
 */
@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    /**
     * Creates and configures a Cloudinary instance.
     * 
     * This method sets up a Cloudinary object with the cloud name, API key, and API secret
     * injected from the application properties.
     *
     * @return A configured Cloudinary instance ready for use in the application.
     */
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }
}
// File: ./src/main/java/project/community/theatre/config/SecurityConfig.java

package project.community.theatre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.community.theatre.filter.JwtRequestFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * Configuration class for setting up security in the application.
 * This class is responsible for configuring web security settings,
 * including CORS, CSRF, and authentication rules.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final JwtRequestFilter jwtRequestFilter;

    /**
     * Constructs a new SecurityConfig with the specified JWT request filter.
     *
     * @param jwtRequestFilter The JWT request filter to be used for authentication.
     */
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Configures the security filter chain for the application.
     * This method sets up CSRF protection, CORS, request authorization rules,
     * session management, and adds the JWT request filter.
     *
     * @param http The HttpSecurity object to be configured.
     * @return A SecurityFilterChain object representing the configured security filters.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/signup", "/api/v1/auth/login", "/event/get-all-events").permitAll()
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configures and provides a CORS configuration source.
     * This method sets up CORS settings including allowed origins, methods, headers,
     * credentials, and max age for preflight requests.
     *
     * @return A CorsConfigurationSource object with the configured CORS settings.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true); // true if using cookies or JWT tokens
        configuration.setMaxAge(3600L); // Cache preflight for 1 hour
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
// File: ./src/main/java/project/community/theatre/constant/AppConstants.java

package project.community.theatre.constant;

/**
 * This class contains constant values used throughout the application.
 * It includes API versioning, base URL, and file upload size limits.
 */
public class AppConstants {

    // General Application Constants
    public static final String API_VERSION = "/v1";
    public static final String BASE_URL = "/api"+API_VERSION;

    // File Upload Constants
    public static final long MAX_FILE_SIZE_BYTES = 5 * 1024 * 1024; // 5MB
    public static final int IMAGE_WIDTH = 800;
    public static final int IMAGE_HEIGHT = 600;
    public static final double IMAGE_QUALITY = 1.0;

}

// File: ./src/main/java/project/community/theatre/controller/AuthController.java

package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.requestDto.LoginRequest;
import project.community.theatre.dto.requestDto.SignupRequest;
import project.community.theatre.dto.responseDto.AuthResponse;
import project.community.theatre.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    /**
     * Handles user signup requests.
     * 
     * This method processes a signup request, creates a new user account,
     * and returns an authentication response.
     *
     * @param request The SignupRequest object containing user registration details.
     * @return ResponseEntity<AuthResponse> A ResponseEntity containing the AuthResponse
     *         with details such as authentication token and user information.
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request) {
        log.info("Received request to signup with email: {}", request.getEmail());
        AuthResponse response = userService.signup(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Handles user login requests.
     * 
     * This method authenticates a user based on the provided login credentials
     * and returns an authentication response.
     *
     * @param request The LoginRequest object containing user login credentials.
     * @return ResponseEntity<AuthResponse> A ResponseEntity containing the AuthResponse
     *         with details such as authentication token and user information.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        log.info("Received request to login with email: {}", request.getEmail());
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
// File: ./src/main/java/project/community/theatre/controller/BandController.java

package project.community.theatre.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.model.BandEntity;
import project.community.theatre.service.BandService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bands")
@RequiredArgsConstructor
@Slf4j
public class BandController {

    private final BandService bandService;

    /**
     * Retrieves all band entities from the database.
     *
     * @return a ResponseEntity containing a list of band entities, or an appropriate error response if the list is empty.
     */
    @GetMapping(value = "/all")
    public ResponseEntity<List<BandEntity>> getAllBands() {
        log.info("Received request to get all bands");
        List<BandEntity> bands = bandService.getAllBands();
        return ResponseEntity.ok(bands);
    }

    /**
     * Creates or updates a band entity.
     *
     * @param band the band entity to be created or updated. Must be valid and not null.
     * @return a ResponseEntity containing the saved band entity.
     */
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<BandEntity> createOrUpdateBand(@Valid @RequestBody BandEntity band) {
        log.info("Received request to create bands");
        BandEntity savedBand = bandService.createBand(band);
        return ResponseEntity.ok(savedBand);
    }

    /**
     * Retrieves a band entity by its unique identifier.
     *
     * @param bandId the unique identifier of the band to be retrieved. Must not be null or empty.
     * @return a ResponseEntity containing the band entity if found, or an appropriate error response if not found.
     */
    @GetMapping("/get/{bandId}")
    public ResponseEntity<BandEntity> getBandById(@PathVariable String bandId) {
        log.info("Fetching band for ID: {}", bandId);
        BandEntity band = bandService.getBandById(bandId);
        return ResponseEntity.ok(band);
    }

    /**
     * Deletes a band entity by its unique identifier.
     *
     * @param bandId the unique identifier of the band to be deleted. Must not be null or empty.
     * @return a ResponseEntity with an empty body indicating success, or an appropriate error response if the entity
     * could not be found.
     */
    @DeleteMapping("/{bandId}")
    public ResponseEntity<Void> deleteBand(@PathVariable String bandId) {
        log.info("Received request to delete band for ID: {}", bandId);
        bandService.deleteBand(bandId);
        return ResponseEntity.ok().build();
    }
}
// File: ./src/main/java/project/community/theatre/controller/DiscountController.java

package project.community.theatre.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.requestDto.DiscountRequest;
import project.community.theatre.dto.responseDto.DiscountResponse;
import project.community.theatre.model.DiscountEntity;
import project.community.theatre.service.DiscountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
@Slf4j
public class DiscountController {

    private final DiscountService discountService;

    /**
     * Calculates the discount based on the provided request details.
     *
     * @param request the DiscountRequest object containing the necessary
     *                information to calculate the discount.
     * @return a ResponseEntity containing a DiscountResponse object with
     *         the calculated discount details, wrapped in an HTTP status of OK.
     */
    @PostMapping("/calculate")
    public ResponseEntity<DiscountResponse> calculateDiscount(@RequestBody DiscountRequest request) {
        DiscountResponse response = discountService.calculateDiscount(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a list of all discounts.
     *
     * @return a ResponseEntity containing the list of DiscountEntity objects,
     *         wrapped in an HTTP status of OK.
     */
    @GetMapping(value ="/all-discounts")
    public ResponseEntity<List<DiscountEntity>> getAllDiscounts() {
        log.info("Received request to get all discounts");
        List<DiscountEntity> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    /**
     * Creates or updates a discount entity.
     *
     * @param discount the DiscountEntity to be created or updated. Must be valid and not null.
     * @return a ResponseEntity containing the saved DiscountEntity, wrapped in an HTTP status of OK.
     */
    @PostMapping(value = "/create-discount" , consumes = "application/json"  )
    public ResponseEntity<DiscountEntity> createOrUpdateDiscount(@Valid @RequestBody DiscountEntity discount) {
        log.info("Received request to create a discount");
        DiscountEntity savedDiscount = discountService.createOrUpdateDiscount(discount);
        return ResponseEntity.ok(savedDiscount);
    }

    /**
     * Retrieves a discount entity based on the provided discount type.
     *
     * @param discountType the unique identifier of the discount type to fetch the discount for.
     * @return a ResponseEntity containing the DiscountEntity object with the discount details for the specified type, wrapped in an HTTP status of OK.
     *         If no discount is found for the given type, an appropriate error status will be returned.
     */
    @GetMapping("/type/{discountType}")
    public ResponseEntity<DiscountEntity> getDiscountByType(@PathVariable String discountType) {
        log.info("Fetching discount for type: {}", discountType);
        DiscountEntity discount = discountService.getDiscountByType(discountType);
        return ResponseEntity.ok(discount);
    }

    /**
     * Deletes a discount based on the provided discount ID.
     *
     * @param id the unique identifier of the discount to be deleted.
     *           It should be a valid string representing the discount ID.
     * @return a ResponseEntity with an HTTP status of OK if the discount
     *         was successfully deleted, or an appropriate error status otherwise.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable String id) {
        log.info("Received request to delete discount for id: {}", id);
        discountService.deleteDiscount(id);
        return ResponseEntity.ok().build();
    }
}
// File: ./src/main/java/project/community/theatre/controller/EventController.java

package project.community.theatre.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.community.theatre.dto.responseDto.ShowTimeResponseDto;
import project.community.theatre.dto.requestDto.AddShowTimesRequestDto;
import project.community.theatre.dto.requestDto.DeleteShowTimeRequestDto;
import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;
import project.community.theatre.service.EventService;

import java.util.List;

/**
 * This class is a controller for handling event-related operations.
 * It provides RESTful endpoints for fetching, adding, and deleting events, as well as managing their show times.
 *
 */
@RestController
@RequestMapping("/event")
@CrossOrigin("*")
@Slf4j
public class EventController {

    @Autowired
    private EventService eventService;

    /**
     * This method fetches an event by its ID.
     *
     * @param id The unique identifier of the event to fetch.
     * @return A ResponseEntity containing the fetched event wrapped in an EventResponseDto object.
     */
    @GetMapping(value = "/get-event")
    public ResponseEntity<EventResponseDto> getEvent(@RequestParam("id") String id) {
        log.info("Received request to fetch event with ID: {}", id);

        EventResponseDto eventResponseDto = eventService.getEvent(id);

        log.info("Returning event: {}", eventResponseDto);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.OK);
    }

    /**
     * This method fetches all events.
     *
     * @return A ResponseEntity containing a list of all events wrapped in an EventResponseDto object.
     */
    @GetMapping(value = "/get-all-events")
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        log.info("Received request to fetch all events");

        List<EventResponseDto> events = eventService.getAllEvent();

        log.info("Returning {} events", events.size());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * This method adds a new event.
     *
     * @param eventEntryDto The details of the new event to be added.
     * @param image         The image file associated with the new event.
     * @return A ResponseEntity containing the newly added event wrapped in an EventResponseDto object.
     */
    @PostMapping(value = "/add-event", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponseDto> addEvent(
            @Valid @RequestPart("eventDetails") EventEntryDto eventEntryDto,
            @RequestPart("image") MultipartFile image) {
        log.info("Received request to add a new event: {}", eventEntryDto);
        eventEntryDto.setImage(image);

        EventResponseDto eventResponseDto = eventService.addEvent(eventEntryDto);

        log.info("Event added successfully: {}", eventResponseDto);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.CREATED);
    }

    /**
     * This method deletes an event by its ID.
     *
     * @param eventId The unique identifier of the event to be deleted.
     * @return A ResponseEntity with an empty body and an OK status code.
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String eventId) {
        log.info("Received request to delete event with ID: {}", eventId);

        eventService.deleteEvent(eventId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method adds show times for a specific event.
     *
     * @param request The details of the new show time to be added.
     * @return A ResponseEntity with an empty body and an OK status code.
     */
    @PostMapping("/add-show-times")
    public ResponseEntity<Void> addShowTimes(@RequestBody AddShowTimesRequestDto request) {
        log.info("Received request to add show times for event ID: {}", request.getEventId());

        eventService.addShowTimes(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method fetches show times for a specific event.
     *
     * @param eventId The unique identifier of the event to fetch its show times.
     * @return A ResponseEntity containing a list of show times wrapped in a ShowTimeResponseDto object.
     */
    @GetMapping("/{eventId}/get-show-times")
    public ResponseEntity<List<ShowTimeResponseDto>> getShowTimesForEvent(@PathVariable String eventId) {
        log.info("Received request to fetch show times for event ID: {}", eventId);

        List<ShowTimeResponseDto> showTimes = eventService.getShowTimesForEvent(eventId);

        return new ResponseEntity<>(showTimes, HttpStatus.OK);
    }

    /**
     * This method deletes a specific showtime for a specific event.
     *
     * @param request The details of the showtime to be deleted.
     * @return A ResponseEntity with an empty body and an OK status code.
     */
    @DeleteMapping("/delete-show-time")
    public ResponseEntity<Void> deleteShowTime(@RequestBody DeleteShowTimeRequestDto request) {
        log.info("Received request to delete show time {} for event ID: {}", request.getShowTime(), request.getEventId());

        eventService.deleteShowTime(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
// File: ./src/main/java/project/community/theatre/controller/PaymentController.java

package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.responseDto.TicketResponse;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.service.PaymentService;
import project.community.theatre.service.TicketService;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    private final TicketService ticketService;

    /**
     * Processes a payment request and generates a ticket if the payment is successful.
     *
     * @param paymentRequest The request containing payment details.
     * @return A ResponseEntity containing the generated ticket if the payment is successful, otherwise a bad request response with the payment response.
     */
    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest paymentRequest) {
        log.info("Received payment request: {}", paymentRequest);
        // Process the payment
        PaymentResponse paymentResponse = paymentService.processPayment(paymentRequest);
    
        if (!paymentResponse.isSuccess()) {
            return ResponseEntity.badRequest().body(paymentResponse);
        }
    
        // Generate and save the ticket
        TicketResponse ticketResponse = ticketService.generateAndSaveTicket(paymentRequest, paymentResponse.getTransactionId());
    
        return ResponseEntity.ok(ticketResponse);
    }
}
// File: ./src/main/java/project/community/theatre/controller/ReviewController.java

package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.requestDto.ReviewRequestDto;
import project.community.theatre.dto.responseDto.ReviewResponseDto;
import project.community.theatre.model.ReviewEntity;
import project.community.theatre.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * This method saves a new review for an event.
     *
     * @param request A ReviewRequestDto object containing the details of the review to be saved.
     * @return A ResponseEntity containing the newly saved ReviewEntity object.
     */
    @PostMapping("/save")
    public ResponseEntity<ReviewEntity> saveReview(@RequestBody ReviewRequestDto request) {
        log.info("Received request to save review for event ID: {}", request.getEventId());

        // Save the review
        ReviewEntity savedReview = reviewService.saveReview(
                request.getUserName(),
                request.getRating(),
                request.getDescription(),
                request.getEventId()
        );

        return ResponseEntity.ok(savedReview);
    }

    /**
     * This method fetches all reviews for a specific event.
     *
     * @param eventId The unique identifier of the event to fetch reviews for.
     * @return A ResponseEntity containing a list of ReviewEntity objects.
     */
    @GetMapping("/all/{eventId}")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews(@PathVariable String eventId) {
        log.info("Received request to fetch all reviews for event ID: {}", eventId);

        // Fetch all reviews for the event
        List<ReviewResponseDto> reviews = reviewService.getAllReviews(eventId);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
// File: ./src/main/java/project/community/theatre/controller/SeatController.java

package project.community.theatre.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.constant.AppConstants;
import project.community.theatre.service.impl.SeatServiceImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(AppConstants.BASE_URL + "/seats")
@CrossOrigin("*")
@Slf4j
public class SeatController {

    @Autowired
    private SeatServiceImpl seatService;

    /**
     * This endpoint verifies and locks the given seats for a specific event and show ID. It takes the event ID,
     * show ID and a list of seat numbers as input and returns a ResponseEntity containing the result of the
     * verification. If the seats are available, it locks the seats and returns a map containing a status flag
     * set to true. If the seats are unavailable, it returns a map containing a status flag set to false and
     * a list of unavailable seats. If an unexpected error occurs, it returns a ResponseEntity with a status of
     * INTERNAL_SERVER_ERROR and a map containing the error message.
     *
     * @param eventId the event ID
     * @param showId the show ID
     * @param seatNumbers the list of seat numbers
     * @return a ResponseEntity containing the result of the verification
     */
    @PostMapping("/verify/{eventId}/{showId}")
    public ResponseEntity<?> verifySeatStatus(@PathVariable String eventId, @PathVariable String showId,
                                              @RequestBody List<String> seatNumbers) {
        log.info("Received request to verify and lock seats for event ID: {}, show ID: {} and seats: {}", eventId, showId, seatNumbers);
        try {
            
            Map<String, Object> result = seatService.processSeatsAvailability(eventId, showId, seatNumbers);

            if (Boolean.FALSE.equals(result.get("status"))) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * This endpoint fetches the list of booked seats for a specific event and show ID. It takes the event ID and
     * show ID as input and returns a ResponseEntity containing the list of booked seats. If an unexpected error
     * occurs, it returns a ResponseEntity with a status of INTERNAL_SERVER_ERROR and a map containing the error
     * message.
     *
     * @param eventId the event ID
     * @param showId the show ID
     * @return a ResponseEntity containing the list of booked seats
     */
    @GetMapping("/booked-seats/{eventId}/{showId}")
    public ResponseEntity<List<String>> getAllBookedSeats(@PathVariable String eventId, @PathVariable String showId) {
        log.info("Fetching locked seats for event ID: {} and show ID: {}", eventId, showId);
    
        List<String> lockedSeats = seatService.getAllBookedSeats(eventId, showId);
    
        return ResponseEntity.ok(lockedSeats);
    }
}
// File: ./src/main/java/project/community/theatre/controller/TicketController.java

package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.responseDto.TicketResponse;
import project.community.theatre.exception.ResourceNotFoundException;
import project.community.theatre.service.TicketService;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketController {

    private final TicketService ticketService;

    /**
     * Retrieves the ticket details for a given ticket number.
     *
     * @param ticketNumber the ticket number to fetch details for
     * @return a ResponseEntity containing the ticket details. If the ticket is not found, a 404 response is returned.
     */
    @GetMapping("/{ticketNumber}")
    public ResponseEntity<TicketResponse> getTicketDetails(@PathVariable String ticketNumber) {
        log.info("Received request to fetch ticket details for ticket number: {}", ticketNumber);

        try {
            TicketResponse ticketResponse = ticketService.getTicketDetails(ticketNumber);
            return ResponseEntity.ok(ticketResponse);
        } catch (ResourceNotFoundException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(404).body(null); // Return 404 if ticket not found
        }
    }
}
// File: ./src/main/java/project/community/theatre/controller/UserController.java

package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.exception.ResourceNotFoundException;
import project.community.theatre.model.UserEntity;
import project.community.theatre.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Retrieves a user entity by its unique identifier.
     *
     * @param userId the unique identifier of the user to be retrieved. Must not be null or empty.
     * @return a ResponseEntity containing the user entity if found, or an appropriate error response if not found.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String userId) {
        log.info("Received request to fetch user by ID: {}", userId);
        try {
            UserEntity user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(404).body(null);
        }
    }

    /**
     * Retrieves all user entities from the database.
     *
     * @return a ResponseEntity containing a list of user entities, or an appropriate error response if the list is empty.
     */
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        log.info("Received request to fetch all users");
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Updates a user entity with the provided request body.
     *
     * @param userId    the unique identifier of the user to be updated. Must not be null or empty.
     * @param updatedUser the updated user entity to be saved. Must not be null.
     *
     * @return a ResponseEntity containing the updated user entity, or an appropriate error response if not found.
     */
    @PutMapping("/{userId}/update")
    public ResponseEntity<UserEntity> updateUser(@PathVariable String userId, @RequestBody UserEntity updatedUser) {
    UserEntity updated = userService.updateUser(userId, updatedUser);
    return ResponseEntity.ok(updated);
}


}
// File: ./src/main/java/project/community/theatre/dto/AddressDto.java

package project.community.theatre.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
    private String street;
    private String apartment;
    private String city;
    private String country;
    private String state;
    private String zipCode;
}

// File: ./src/main/java/project/community/theatre/dto/PaymentDetailsDto.java

package project.community.theatre.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailsDto {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

}



// File: ./src/main/java/project/community/theatre/dto/TicketType.java

package project.community.theatre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketType {
    private int child;
    private int adult;
    private int pensioner;
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/AddShowTimesRequestDto.java

package project.community.theatre.dto.requestDto;

import lombok.Data;

import java.util.List;

@Data
public class AddShowTimesRequestDto {

    private String eventId;
    private List<String> showTimes;
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/DeleteShowTimeRequestDto.java

package project.community.theatre.dto.requestDto;

import lombok.Data;

@Data
public class DeleteShowTimeRequestDto {

    private String eventId;
    private String showTime; // Specific show time to delete
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/DiscountRequest.java

package project.community.theatre.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.AliasFor;
import project.community.theatre.dto.TicketType;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountRequest {
    private Map<String, TicketType> bands;
    @JsonProperty("isSocialClub")
    private boolean isSocialClub;
    private int totalTickets;
    private String day;
    private LocalDateTime showTime;
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/EventEntryDto.java

package project.community.theatre.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EventEntryDto {
    @NotNull(message = "ID cannot be null")
    private String eventId;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Genre cannot be null")
    private String genre;
    @NotNull(message = "Start date cannot be null")
    private String startDate;
    @NotNull(message = "End date cannot be null")
    private String endDate;
    @NotNull(message = "Duration cannot be null")
    private String duration;
    private String description;
    private String producer;
    private String director;
    private MultipartFile image;
}

// File: ./src/main/java/project/community/theatre/dto/requestDto/LoginRequest.java

package project.community.theatre.dto.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/PaymentRequest.java

package project.community.theatre.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.community.theatre.dto.AddressDto;
import project.community.theatre.dto.PaymentDetailsDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private PaymentDetailsDto paymentDetails;
    private AddressDto address;
    private String name;
    private Double payableAmount;
    private String userId;
    private String email;
    private String eventId;
    private String showId;
    private String showTime;
    private List<String> seatNumbers;
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/ReviewRequestDto.java

package project.community.theatre.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {
    private String userName;
    private Integer rating;
    private String description;
    private String eventId;
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/SignupRequest.java

package project.community.theatre.dto.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Mobile number is required")
    private String mobileNo;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String role;
}
// File: ./src/main/java/project/community/theatre/dto/requestDto/UserEntryDto.java

package project.community.theatre.dto.requestDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEntryDto {
    String name;
    String mobNo;
}

// File: ./src/main/java/project/community/theatre/dto/responseDto/AuthResponse.java

package project.community.theatre.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String message;
    private String userId;
    private String role;
    private String token;
}
// File: ./src/main/java/project/community/theatre/dto/responseDto/DiscountResponse.java

package project.community.theatre.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountResponse {
    private double child;
    private double pensioner;
    private double lastHour;
    private double socialClub;
    private double weekday;
    private double finalPrice;
    private double reduction;
}
// File: ./src/main/java/project/community/theatre/dto/responseDto/EventResponseDto.java

package project.community.theatre.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDto {
    private String eventId;
    private String name;
    private String genre;
    private LocalDate startDate;
    private LocalDate endDate;
    private String duration;
    private String description;
    private String producer;
    private String director;
    private String imageUrl;
}
// File: ./src/main/java/project/community/theatre/dto/responseDto/PaymentResponse.java

package project.community.theatre.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private boolean success;
    private String message;
    private String transactionId;
}
// File: ./src/main/java/project/community/theatre/dto/responseDto/ReviewResponseDto.java

package project.community.theatre.dto.responseDto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponseDto {
    private String reviewId;
    private String userName;
    private Integer rating;
    private String description;
    private LocalDate reviewDate;

}

// File: ./src/main/java/project/community/theatre/dto/responseDto/ShowTimeResponseDto.java

package project.community.theatre.dto.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowTimeResponseDto {

    private String id; // Primary key of the ShowTimeEntity
    private String showTime; // Show time in Zulu format (e.g., "2025-02-20T14:30:00Z")
}
// File: ./src/main/java/project/community/theatre/dto/responseDto/TicketResponse.java

package project.community.theatre.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {
    private String ticketNumber;
    private Double totalPrice;
    private String seatNumbers;
    private String showTime;
    private String eventName;
    private LocalDateTime bookingTime;
    private String status;
}

// File: ./src/main/java/project/community/theatre/enums/ImageFormat.java

package project.community.theatre.enums;

import lombok.Getter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum ImageFormat {
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    BMP("bmp"),
    GIF("gif");

    private final String format;

    ImageFormat(String format) {
        this.format = format;
    }

    public static boolean isSupported(String format) {
        if (format == null || format.isEmpty()) {
            return false;
        }
        String lowerCaseFormat = format.toLowerCase();
        for (ImageFormat imageFormat : values()) {
            if (imageFormat.getFormat().equals(lowerCaseFormat)) {
                return true;
            }
        }
        return false;
    }

    public static String getSupportedFormatsAsString() {
        return Stream.of(values())
                .map(ImageFormat::getFormat)
                .collect(Collectors.joining(", "));
    }
}
// File: ./src/main/java/project/community/theatre/enums/SeatBand.java

package project.community.theatre.enums;

public enum SeatBand {
    A,
    B,
    C
}

// File: ./src/main/java/project/community/theatre/exception/BandNotFoundException.java

package project.community.theatre.exception;

public class BandNotFoundException extends RuntimeException {
    public BandNotFoundException(String message) {
        super(message);
    }
}
// File: ./src/main/java/project/community/theatre/exception/DiscountNotFoundException.java

package project.community.theatre.exception;

public class DiscountNotFoundException extends RuntimeException {
    public DiscountNotFoundException(String message) {
        super(message);
    }
}
// File: ./src/main/java/project/community/theatre/exception/ErrorResponse.java

package project.community.theatre.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final String details;

    public ErrorResponse(LocalDateTime timestamp, int status, String message, String details) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.details = details;
    }
}
// File: ./src/main/java/project/community/theatre/exception/GlobalExceptionHandler.java

package project.community.theatre.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles ResourceNotFoundException and returns a ResponseEntity with an ErrorResponse and a NOT_FOUND status.
     *
     * @param ex The ResourceNotFoundException that occurred.
     * @param request The WebRequest object.
     * @return A ResponseEntity with an ErrorResponse and a NOT_FOUND status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles InvalidImageException and returns a ResponseEntity with an ErrorResponse and a BAD_REQUEST status.
     *
     * @param ex The InvalidImageException that occurred.
     * @param request The WebRequest object.
     * @return A ResponseEntity with an ErrorResponse and a BAD_REQUEST status.
     */
    @ExceptionHandler(InvalidImageException.class)
    public ResponseEntity<ErrorResponse> handleInvalidImageException(InvalidImageException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles PaymentFailedException and returns a ResponseEntity with an ErrorResponse and a BAD_REQUEST status.
     *
     * @param ex The PaymentFailedException that occurred.
     * @param request The WebRequest object.
     * @return A ResponseEntity with an ErrorResponse and a BAD_REQUEST status.
     */
    // Handle PaymentFailedException
    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<ErrorResponse> handlePaymentFailedException(PaymentFailedException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UserAlreadyExistsException and returns a ResponseEntity with a conflict status and an error message.
     *
     * @param ex The UserAlreadyExistsException that occurred.
     * @return A ResponseEntity with a conflict status and an error message.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Handles UserNotFoundException and returns a ResponseEntity with an unauthorized status and an error message.
     *
     * @param ex The UserNotFoundException that occurred.
     * @return A ResponseEntity with an unauthorized status and an error message.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Handles DiscountNotFoundException and returns a ResponseEntity with a not found status and an error message.
     *
     * @param ex The DiscountNotFoundException that occurred.
     * @return A ResponseEntity with a not found status and an error message.
     */
    @ExceptionHandler(DiscountNotFoundException.class)
    public ResponseEntity<?> handleDiscountNotFoundException(DiscountNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles BandNotFoundException and returns a ResponseEntity with a not found status and an error message.
     *
     * @param ex The BandNotFoundException that occurred.
     * @return A ResponseEntity with a not found status and an error message.
     */
    @ExceptionHandler(BandNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBandNotFoundException(BandNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles generic exceptions and returns a ResponseEntity with an ErrorResponse and an internal server error status.
     *
     * @param ex The Exception that occurred.
     * @param request The WebRequest object.
     * @return A ResponseEntity with an ErrorResponse and an internal server error status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Overrides the handleMethodArgumentNotValid method to handle validation errors and returns a ResponseEntity with a bad request status and a map of field errors.
     *
     * @param ex The MethodArgumentNotValidException that occurred.
     * @param headers The HttpHeaders object.
     * @param status The HttpStatusCode object.
     * @param request The WebRequest object.
     * @return A ResponseEntity with a bad request status and a map of field errors.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
// File: ./src/main/java/project/community/theatre/exception/InvalidImageException.java

package project.community.theatre.exception;

public class InvalidImageException extends RuntimeException {
    public InvalidImageException(String message) {
        super(message);
    }
}
// File: ./src/main/java/project/community/theatre/exception/PaymentFailedException.java

package project.community.theatre.exception;

public class PaymentFailedException extends RuntimeException {

    public PaymentFailedException(String message) {
        super(message);
    }
}
// File: ./src/main/java/project/community/theatre/exception/ResourceNotFoundException.java

package project.community.theatre.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
// File: ./src/main/java/project/community/theatre/exception/UserAlreadyExistsException.java

package project.community.theatre.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
// File: ./src/main/java/project/community/theatre/exception/UserNotFoundException.java

package project.community.theatre.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
// File: ./src/main/java/project/community/theatre/filter/JwtRequestFilter.java

package project.community.theatre.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project.community.theatre.service.UserService;
import project.community.theatre.util.JwtUtil;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    /**
     * Validates the JWT token provided in the "Authorization" header of the request, and authenticates the user if the
     * token is valid.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param chain    the filter chain
     * @throws ServletException if an error occurs during the filter chain
     * @throws IOException       if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String userId = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            userId = jwtUtil.extractUserId(jwt);
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(userId);

            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
// File: ./src/main/java/project/community/theatre/mapper/EventMapper.java

package project.community.theatre.mapper;

import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;
import project.community.theatre.model.EventEntity;

import java.time.LocalDate;

public class EventMapper {

    /**
     * Maps an EventEntryDto object to an EventEntity object.
     *
     * @param eventEntryDto The DTO containing event data to be mapped.
     * @return An EventEntity object populated with data from the provided DTO.
     * @throws IllegalArgumentException if the eventEntryDto is null.
     */
    public static EventEntity mapDtoToEntity(EventEntryDto eventEntryDto) {
        if (eventEntryDto == null) {
            throw new IllegalArgumentException("EventEntryDto cannot be null");
        }

        // Parse startDate and endDate into LocalDate (assuming format "yyyy-MM-dd")
        LocalDate startDate = LocalDate.parse(eventEntryDto.getStartDate());
        LocalDate endDate = LocalDate.parse(eventEntryDto.getEndDate());

        return EventEntity.builder()
                .eventId(eventEntryDto.getEventId())
                .name(eventEntryDto.getName())
                .genre(eventEntryDto.getGenre())
                .startDate(startDate)
                .endDate(endDate)
                .duration(eventEntryDto.getDuration())
                .description(eventEntryDto.getDescription())
                .producer(eventEntryDto.getProducer())
                .director(eventEntryDto.getDirector())
                .build();
    }

    public static EventResponseDto mapEntityToDto(EventEntity eventEntity) {
        if (eventEntity == null) {
            throw new IllegalArgumentException("EventEntity cannot be null");
        }
        return EventResponseDto.builder()
                .eventId(eventEntity.getEventId())
                .name(eventEntity.getName())
                .genre(eventEntity.getGenre())
                .startDate(eventEntity.getStartDate())
                .endDate(eventEntity.getEndDate())
                .duration(eventEntity.getDuration())
                .description(eventEntity.getDescription())
                .producer(eventEntity.getProducer())
                .director(eventEntity.getDirector())
                .imageUrl(eventEntity.getImageUrl())
                .build();
    }
}
// File: ./src/main/java/project/community/theatre/model/BandEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a band entity in the community theatre system.
 * This class is used to store and retrieve band-related information from the database.
 *
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bands")
public class BandEntity {
    @Id
    @Column(name = "band_id", nullable = false, unique = true)
    @NotBlank(message = "Band ID cannot be empty or blank")
    private String bandId;

    @Column(name = "seats_per_band", nullable = false)
    private Integer seatsPerBand;

    @Column(name = "price", nullable = false)
    private Double price;

}
// File: ./src/main/java/project/community/theatre/model/DiscountEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents a discount entity in the community theatre system.
 * This entity is used to store information about different types of discounts available.
 *
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discounts")
public class DiscountEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(name = "discount_type", nullable = false)
    @NotBlank(message = "Discount type cannot be blank")
    private String discountType; // e.g., "CHILD", "STUDENT", "PENSIONER"

    @Column(name = "discount_percentage", nullable = false)
    @NotNull(message = "Discount percentage cannot be null"  )
    private Double discountPercentage;

    //Add Constructor
    public DiscountEntity(String discountType, Double discountPercentage) {
        this.id = UUID.randomUUID().toString();  // Generate a unique ID  (UUID)
        this.discountType = discountType;
        this.discountPercentage = discountPercentage;
    }

}
// File: ./src/main/java/project/community/theatre/model/EventEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "showTimes")
@Table(name = "events")
public class EventEntity {

    /**
     * Unique identifier for the event.
     * Cannot be null.
     */
    @Id
    @NotNull(message = "ID cannot be null")
    private String eventId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "start_date", columnDefinition = "DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "DATE", nullable = false)
    private LocalDate endDate;

    @Column(name = "duration")
    private String duration;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "producer")
    private String producer;

    @Column(name = "director")
    private String director;

    @Column(name = "image_url")
    private String imageUrl;

    /**
     * List of show times associated with the event.
     * This is a one-to-many relationship with ShowTimeEntity.
     * The showTimes are mapped by the "event" field in ShowTimeEntity.
     * Cascading all operations to showTimes and removing orphaned showTimes.
     */
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowTimeEntity> showTimes;

    public EventEntity(String eventId) {
        this.eventId = eventId;
    }

    // Relationship with ReviewEntity
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviewId;
}
// File: ./src/main/java/project/community/theatre/model/PaymentHistoryEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_history")
public class PaymentHistoryEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id = UUID.randomUUID().toString(); // Use String for ID

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "payment_time", nullable = false)
    private LocalDateTime paymentTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    public enum PaymentStatus {
        SUCCESS, FAILED
    }

    // Constructor to generate ID
    public PaymentHistoryEntity(UserEntity user, String transactionId, Double amount,
                                LocalDateTime paymentTime, PaymentStatus status) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.transactionId = transactionId;
        this.amount = amount;
        this.paymentTime = paymentTime;
        this.status = status;
    }
}
// File: ./src/main/java/project/community/theatre/model/ReviewEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class ReviewEntity {
    @Id
    @Column(name = "review_id", nullable = false, unique = true)
    private String reviewId = UUID.randomUUID().toString();

    @Column(name = "user_name", nullable = false)
    private String userName; // Default to "Anonymous" if empty

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "reviewed_date", nullable = false)
    private LocalDate reviewedDate;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;
}
// File: ./src/main/java/project/community/theatre/model/ShowTimeEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "event")
@Table(name = "event_show_times")
public class ShowTimeEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id = UUID.randomUUID().toString(); // Use String for ID

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Column(name = "show_time", nullable = false)
    private String showTime; // Store date-time as a string in Zulu format

    // Constructor to generate ID
    public ShowTimeEntity(EventEntity event, String showTime) {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID
        this.event = event;
        this.showTime = showTime;
    }

    public ShowTimeEntity(String showId) {
        this.id = showId;
    }
}
// File: ./src/main/java/project/community/theatre/model/TicketEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
@ToString(exclude = {"user", "event", "showTimeId"})
public class TicketEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String ticketNumber = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "show_time_id", nullable = false)
    private ShowTimeEntity showTimeId;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "seat_numbers", nullable = false)
    private String seatNumbers;

    @Column(name = "show_time", nullable = false)
    private LocalDateTime showTime;

    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus status;

    public enum TicketStatus {
        BOOKED, CANCELLED
    }

    // Constructor to generate ID
    public TicketEntity(UserEntity user, EventEntity event, String ticketNumber, Double totalPrice,
                        String seatNumbers, LocalDateTime showTime, TicketStatus status) {
        this.ticketNumber = UUID.randomUUID().toString();
        this.user = user;
        this.event = event;
        this.totalPrice = totalPrice;
        this.seatNumbers = seatNumbers;
        this.showTime = showTime;
        this.status = status;
    }
}
// File: ./src/main/java/project/community/theatre/model/UserEntity.java

package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId = UUID.randomUUID().toString();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mobileNo", nullable = false)
    private String mobileNo;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    public UserEntity(String userId) {
        this.userId = userId;
    }
}
// File: ./src/main/java/project/community/theatre/repository/BandRepository.java

package project.community.theatre.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.BandEntity;

import java.util.List;
import java.util.Optional;

public interface BandRepository extends JpaRepository<BandEntity, String> {
    @NotNull List<BandEntity> findAll();

    Optional<BandEntity> findByBandId(String bandId);

    void deleteByBandId(String bandId);

    boolean existsByBandId(String discountType);
}
// File: ./src/main/java/project/community/theatre/repository/DiscountRepository.java

package project.community.theatre.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.DiscountEntity;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {
    Optional<DiscountEntity> findByDiscountType(String discountType);

    boolean existsById(@NotNull String id);
    void deleteById(@NotNull String id);
}
// File: ./src/main/java/project/community/theatre/repository/EventRepository.java

package project.community.theatre.repository;

import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import project.community.theatre.model.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Integer> {

    @Query("SELECT e FROM EventEntity e WHERE e.id = :id")
    Optional<EventEntity> findEventById(@Param("id") String id);
}
// File: ./src/main/java/project/community/theatre/repository/PaymentHistoryRepository.java

package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.PaymentHistoryEntity;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, Long> {
}
// File: ./src/main/java/project/community/theatre/repository/ReviewRepository.java

package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.ReviewEntity;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
    List<ReviewEntity> findByEvent_EventId(String eventId);
}
// File: ./src/main/java/project/community/theatre/repository/ShowTimeRepository.java

package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.community.theatre.model.EventEntity;
import project.community.theatre.model.ShowTimeEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTimeEntity, Long> {
    @Query("SELECT e FROM EventEntity e WHERE e.id = :id")
    Optional<EventEntity> findEventById(@Param("id") String id);

    List<ShowTimeEntity> findByEvent(EventEntity event);
}
// File: ./src/main/java/project/community/theatre/repository/TicketRepository.java

package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.community.theatre.model.EventEntity;
import project.community.theatre.model.TicketEntity;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    Optional<TicketEntity> findByTicketNumber(String ticketNumber);
    @Query("SELECT e FROM EventEntity e WHERE e.id = :id")
    List<TicketEntity> findEventById(@Param("id") String id);
}
// File: ./src/main/java/project/community/theatre/repository/UserRepository.java

package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.community.theatre.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    Optional<UserEntity> findUserById(@Param("id") String id);
}
// File: ./src/main/java/project/community/theatre/service/BandService.java

package project.community.theatre.service;

import org.springframework.transaction.annotation.Transactional;
import project.community.theatre.model.BandEntity;

import java.util.List;

public interface BandService {
    /**
     * Retrieves all band entities.
     *
     * @return a list of all band entities.
     */
    List<BandEntity> getAllBands();

    /**
     * Retrieves a band entity by its unique identifier.
     *
     * @param bandId the unique identifier of the band to be retrieved. Must not be null or empty.
     * @return the band entity if found, or null if not found.
     */
    BandEntity getBandById(String bandId);

    /**
     * Creates a band entity.
     *
     * @param band the band entity to be created. Must be valid and not null.
     * @return the saved band entity.
     */
    BandEntity createBand(BandEntity band);

    /**
     * Deletes a band entity by its unique identifier.
     *
     * @param bandId the unique identifier of the band to be deleted. Must not be null or empty.
     */
    @Transactional
    void deleteBand(String bandId);
}

// File: ./src/main/java/project/community/theatre/service/DiscountService.java

package project.community.theatre.service;

import project.community.theatre.dto.requestDto.DiscountRequest;
import project.community.theatre.dto.responseDto.DiscountResponse;
import project.community.theatre.model.DiscountEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This interface provides methods for managing discounts in the community theatre system.
 */
public interface DiscountService {
    /**
     * Calculates and returns the discount amount based on the given discount request.
     *
     * @return The discount response containing the calculated discount amount.
     */
    DiscountResponse calculateDiscount(DiscountRequest request);

    /**
     * Retrieves all discount entities from the database.
     *
     * @return A list of all discount entities.
     */
    List<DiscountEntity> getAllDiscounts();

    /**
     * Retrieves a discount entity based on the given discount type.
     *
     * @param discountType The type of discount to retrieve.
     * @return The discount entity with the specified discount type, or null if not found.
     */
    DiscountEntity getDiscountByType(String discountType);

    /**
     * Creates or updates a discount entity in the database.
     *
     * @param discount The discount entity to be created or updated.
     * @return The saved discount entity.
     */
    DiscountEntity createOrUpdateDiscount(DiscountEntity discount);

    /**
     * Deletes a discount entity from the database based on the given ID.
     * This method is annotated with {@link Transactional} to ensure atomicity.
     *
     * @param id The ID of the discount entity to be deleted.
     */
    @Transactional
    void deleteDiscount(String id);
}

// File: ./src/main/java/project/community/theatre/service/EventService.java

package project.community.theatre.service;


import project.community.theatre.dto.responseDto.ShowTimeResponseDto;
import project.community.theatre.dto.requestDto.AddShowTimesRequestDto;
import project.community.theatre.dto.requestDto.DeleteShowTimeRequestDto;
import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;

import java.util.List;


/**
 * This interface defines the methods for managing events and their show times.
 */
public interface EventService {

    /**
     * Adds a new event to the system.
     *
     * @param eventEntryDto The details of the event to be added.
     * @return The response containing the details of the newly added event.
     */
    EventResponseDto addEvent(EventEntryDto eventEntryDto);

    /**
     * Retrieves the details of a specific event.
     *
     * @param id The unique identifier of the event.
     * @return The response containing the details of the requested event.
     */
    EventResponseDto getEvent(String id);

    /**
     * Retrieves the details of all events in the system.
     *
     * @return A list of responses containing the details of all events.
     */
    List<EventResponseDto> getAllEvent();

    /**
     * Deletes an event from the system.
     *
     * @param eventId The unique identifier of the event to be deleted.
     */
    void deleteEvent(String eventId);

    /**
     * Adds show times for a specific event.
     *
     * @param request The request containing the details of the show times to be added.
     */
    void addShowTimes(AddShowTimesRequestDto request);

    /**
     * Retrieves the show times for a specific event.
     *
     * @param eventId The unique identifier of the event.
     * @return A list of responses containing the details of the show times for the event.
     */
    List<ShowTimeResponseDto> getShowTimesForEvent(String eventId);

    /**
     * Deletes a specific showtime from an event.
     *
     * @param request The request containing the details of the show time to be deleted.
     */
    void deleteShowTime(DeleteShowTimeRequestDto request);
}

// File: ./src/main/java/project/community/theatre/service/ImageService.java

package project.community.theatre.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    /**
     * Takes a MultipartFile image and returns a URL of the image uploaded to Cloudinary
     * @param image the MultipartFile image to upload
     * @return the URL of the uploaded image
     * @throws IOException if there is an error uploading the image
     */
    String getImageUrl(MultipartFile image) throws IOException;
}

// File: ./src/main/java/project/community/theatre/service/PaymentGateway.java

package project.community.theatre.service;

import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.exception.PaymentFailedException;

public interface PaymentGateway {
    /**
     * Processes a payment request and returns a response indicating the success or failure of the payment.
     *
     * @param request The PaymentRequest object containing payment details.
     * @return A PaymentResponse object containing the result of the payment processing.
     * @throws PaymentFailedException if the payment processing fails due to an error.
     */
    PaymentResponse processPayment(PaymentRequest request) throws PaymentFailedException;
}
// File: ./src/main/java/project/community/theatre/service/PaymentService.java

package project.community.theatre.service;

import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;

public interface PaymentService {
    /**
     * Processes a payment request.
     *
     * @param paymentRequest The request containing payment details.
     * @return A PaymentResponse indicating the success or failure of the payment processing.
     */
    PaymentResponse processPayment(PaymentRequest paymentRequest);
}
// File: ./src/main/java/project/community/theatre/service/ProcessTicketAsync.java

package project.community.theatre.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.model.TicketEntity;
import project.community.theatre.model.UserEntity;
import project.community.theatre.util.EmailService;
import project.community.theatre.util.PDFUtil;
import project.community.theatre.util.QRCodeUtil;

/**
 * This service class is responsible for asynchronously processing ticket delivery.
 * It creates a new thread to handle the ticket delivery process for a given ticket.
 * The process includes generating a QR code, creating a PDF, sending an email with the PDF,
 * and handling any exceptions that may occur during the process.
 */
@Service
@Slf4j
public class ProcessTicketAsync {

    /**
     * The email service used to send emails with PDF attachments.
     */
    @Autowired
    EmailService emailService;

    /**
     * Asynchronously processes the ticket delivery for the given ticket and provided email.
     *
     * @param ticket The ticket entity for which the delivery needs to be processed.
     * @param providedEmail The email address provided by the user. If null or empty,
     *                      the email address associated with the ticket's user will be used.
     */
    public void processTicketDeliveryAsync(TicketEntity ticket, String providedEmail) {
        new Thread(() -> {
            int maxAttempts = 3;
            int attempt = 1;
            long delay = 2000;

            while (attempt <= maxAttempts) {
                String ticketId = ticket.getTicketNumber();
                try {
                    log.info("Processing ticket delivery for ticketId: {}, attempt: {}", ticketId, attempt);

                    byte[] qrTicket = QRCodeUtil.generateTicketQRCode(ticketId, 200, 200);
                    byte[] pdfBytes = PDFUtil.createPDF(qrTicket, ticket);
                    String email = ObjectUtils.isNotEmpty(providedEmail)? providedEmail : ticket.getUser().getEmail();
                    emailService.sendEmailWithPDF(email, ticket.getUser().getName(), pdfBytes);

                    log.info("Successfully delivered ticket for ticketId: {}", ticketId);
                    break;

                } catch (Exception e) {
                    log.error("Failed to process ticket delivery for ticketId: {}, attempt: {}",
                            ticketId, attempt, e);

                    if (attempt == maxAttempts) {
                        log.error("All attempts failed for ticketId: {}", ticketId);
                        break;
                    }

                    try {
                        Thread.sleep(delay);
                        delay *= 2; // Exponential backoff
                    } catch (InterruptedException ie) {
                        log.error("Sleep interrupted for ticketId: {}", ticketId, ie);
                        Thread.currentThread().interrupt();
                        break;
                    }

                    attempt++;
                }
            }
        }).start();
    }
}

// File: ./src/main/java/project/community/theatre/service/ReviewService.java

package project.community.theatre.service;

import project.community.theatre.dto.responseDto.ReviewResponseDto;
import project.community.theatre.model.ReviewEntity;

import java.util.List;

public interface ReviewService {

    /**
     * This method saves a review for an event. It will first check if the event
     * exists. If the event exists, it will then create a new review and save it
     * to the database.
     *
     * @param userName   the username of the user who is submitting the review
     * @param rating     the rating of the review
     * @param description the description of the review
     * @param eventId    the id of the event to which the review is being submitted
     * @return a ReviewEntity that has been saved to the database
     */
    public ReviewEntity saveReview(String userName, Integer rating, String description, String eventId);
    public List<ReviewResponseDto> getAllReviews(String eventId);
}

// File: ./src/main/java/project/community/theatre/service/SeatService.java

package project.community.theatre.service;

import java.util.List;
import java.util.Map;

/**
 * This interface provides methods for managing seat operations in a community theatre system.
 */
public interface SeatService {

    /**
     * Locks the specified seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @param seatNumbers The list of seat numbers to be locked.
     */
    void lockSeats(String eventId, String showId, List<String> seatNumbers);

    /**
     * Checks the availability of the specified seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @param seatNumbers The list of seat numbers to be checked.
     * @return A list of seat numbers that are available.
     */
    List<String> checkSeatsAvailability(String eventId, String showId, List<String> seatNumbers);

    /**
     * Processes the availability of the specified seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @param seatNumbers The list of seat numbers to be processed.
     * @return A map containing the following keys:
     *         - "availableSeats": A list of seat numbers that are available.
     *         - "lockedSeats": A list of seat numbers that are locked.
     */
    Map<String, Object> processSeatsAvailability(String eventId, String showId, List<String> seatNumbers);

    /**
     * Locks the specified booked seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @param bookedSeats The list of seat numbers to be locked as booked.
     */
    void lockBookedSeats(String eventId, String showId, List<String> bookedSeats);

    /**
     * Retrieves all booked seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @return A list of seat numbers that are booked.
     */
    List<String> getAllBookedSeats(String eventId, String showId);
}

// File: ./src/main/java/project/community/theatre/service/ShowTimeService.java

package project.community.theatre.service;

import project.community.theatre.model.ShowTimeEntity;

import java.util.List;

/**
 * This interface provides methods for managing show times in a community theatre system.
 */
public interface ShowTimeService {

    /**
     * Adds a new showtime to the system.
     *
     * @param showTime The showtime entity to be added.
     * @return The added showtime entity with its unique identifier populated.
     */
    ShowTimeEntity addShowTimes(ShowTimeEntity showTime);

    /**
     * Updates an existing showtime in the system.
     *
     * @param showTime The showtime entity with updated information.
     */
    void updateShowTime(ShowTimeEntity showTime);

    /**
     * Deletes a showtime from the system.
     *
     * @param showTimeId The unique identifier of the showtime to be deleted.
     */
    void deleteShowTime(Long showTimeId);

    /**
     * Retrieves all show times for a specific event.
     *
     * @param eventId The unique identifier of the event.
     * @return A list of showtime entities associated with the given event.
     */
    List<ShowTimeEntity> getAllShowTimes(String eventId);
}


// File: ./src/main/java/project/community/theatre/service/TicketService.java

package project.community.theatre.service;

import org.springframework.transaction.annotation.Transactional;
import project.community.theatre.dto.responseDto.TicketResponse;
import project.community.theatre.dto.requestDto.PaymentRequest;

public interface TicketService {

    /**
     * Generates a ticket for the given payment request and saves it to the database.
     * This method also locks the booked seats and records the payment history.
     * It initiates an asynchronous process to deliver the ticket via email.
     *
     * @param paymentRequest The details of the payment and booking.
     * @param transactionId The unique identifier for the transaction.
     * @return A TicketResponse containing the details of the generated ticket.
     */
    @Transactional
    TicketResponse generateAndSaveTicket(PaymentRequest paymentRequest, String transactionId);

    /**
     * Retrieves the ticket details for a given ticket number.
     *
     * @param ticketNumber the ticket number to fetch details for
     * @return a TicketResponse containing the ticket details. If the ticket is not found, a 404 response is returned.
     */
    TicketResponse getTicketDetails(String ticketNumber);
}
// File: ./src/main/java/project/community/theatre/service/UserService.java

package project.community.theatre.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.community.theatre.dto.requestDto.LoginRequest;
import project.community.theatre.dto.requestDto.SignupRequest;
import project.community.theatre.dto.responseDto.AuthResponse;
import project.community.theatre.model.UserEntity;

import java.util.List;

/**
 * This interface defines the contract for user-related operations.
 */
public interface UserService {

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param userId The unique identifier of the user.
     * @return The user entity corresponding to the given identifier.
     */
    UserEntity getUserById(String userId);

    /**
     * Retrieves all users from the system.
     *
     * @return A list of all user entities.
     */
    List<UserEntity> getAllUsers();

    /**
     * Retrieves a user by their email address.
     *
     * @param userEmail The email address of the user.
     * @return The user entity corresponding to the given email address.
     */
    UserEntity getUserByEmail(String userEmail);

    /**
     * Registers a new user in the system.
     *
     * @param request The signup request containing user details.
     * @return The authentication response containing the access token.
     */
    @Transactional
    AuthResponse signup(SignupRequest request);

    /**
     * Updates an existing user's information.
     *
     * @param userId The unique identifier of the user.
     * @param updatedUser The updated user entity.
     * @return The updated user entity.
     */
    @Transactional
    UserEntity updateUser(String userId, UserEntity updatedUser);

    /**
     * Authenticates a user by their credentials.
     *
     * @param request The login request containing user credentials.
     * @return The authentication response containing the access token.
     */
    AuthResponse login(LoginRequest request);

    /**
     * Loads user details by their unique identifier.
     *
     * @param userId The unique identifier of the user.
     * @return The user details corresponding to the given identifier.
     * @throws UsernameNotFoundException If the user with the given identifier is not found.
     */
    UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException;
}
// File: ./src/main/java/project/community/theatre/service/impl/BandServiceImpl.java

package project.community.theatre.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.community.theatre.exception.BandNotFoundException;
import project.community.theatre.model.BandEntity;
import project.community.theatre.repository.BandRepository;
import project.community.theatre.service.BandService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BandServiceImpl implements BandService {

    private final BandRepository bandRepository;

    @Override
    public List<BandEntity> getAllBands() {
        log.info("Fetching all bands");
        return bandRepository.findAll();
    }

    @Override
    public BandEntity getBandById(String bandId) {
        log.info("Fetching band for ID: {}", bandId);
        return bandRepository.findByBandId(bandId)
                .orElseThrow(() -> new BandNotFoundException("Band not found for ID: " + bandId));
    }

    @Override
    public BandEntity createBand(BandEntity band) {
        log.info("Creating bands: {}", band);
        return bandRepository.save(band);
    }

    @Override
    @Transactional
    public void deleteBand(String bandId) {
        if (!bandRepository.existsByBandId(bandId)) {
            throw new BandNotFoundException("Band not found for ID: " + bandId);
        }
        bandRepository.deleteByBandId(bandId);
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/DiscountServiceImpl.java

package project.community.theatre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.TicketType;
import project.community.theatre.dto.requestDto.DiscountRequest;
import project.community.theatre.dto.responseDto.DiscountResponse;
import project.community.theatre.exception.DiscountNotFoundException;
import project.community.theatre.model.BandEntity;
import project.community.theatre.model.DiscountEntity;
import project.community.theatre.repository.BandRepository;
import project.community.theatre.repository.DiscountRepository;
import project.community.theatre.service.BandService;
import project.community.theatre.service.DiscountService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private BandService bandService;
    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public DiscountResponse calculateDiscount(DiscountRequest request) {
        log.info("Calculating discount for request: {}", request);
        DiscountResponse response = new DiscountResponse();
        List<BandEntity> bands = bandService.getAllBands();
        List<DiscountEntity> discounts = getAllDiscounts();

        Map<String, Double> discountMap = createDiscountMap(discounts);
        PriceBreakdown prices = calculatePrices(request.getBands(), bands);

        if (request.isSocialClub()) {
            response.setSocialClub(calculateSocialClubDiscount(
                    prices.totalFullPrice,
                    request.getTotalTickets(),
                    discountMap
            ));
            double totalReduction = response.getSocialClub();
            response.setReduction(totalReduction);
            response.setFinalPrice(prices.totalFullPrice - totalReduction);
            log.info("Social club discount applied: {}", response.getSocialClub());
            return response;
        }

        calculateRegularDiscounts(request, prices, discountMap, response);

        double totalReduction = response.getChild() + response.getPensioner() +
                response.getLastHour() + response.getWeekday();
        response.setReduction(totalReduction);
        response.setFinalPrice(prices.totalFullPrice - totalReduction);

        log.info("Regular discounts applied: {}", response);
        return response;
    }

    // Rest of the code remains unchanged
    private Map<String, Double> createDiscountMap(List<DiscountEntity> discounts) {
        return discounts.stream()
                .collect(Collectors.toMap(
                        DiscountEntity::getDiscountType,
                        DiscountEntity::getDiscountPercentage
                ));
    }

    private PriceBreakdown calculatePrices(Map<String, TicketType> bands, List<BandEntity> bandEntities) {
        log.info("Calculating prices for bands: {}", bands);
        Map<String, Double> bandPrices = bandEntities.stream()
                .collect(Collectors.toMap(BandEntity::getBandId, BandEntity::getPrice));

        double totalChildPrice = 0;
        double totalPensionerPrice = 0;
        double totalFullPrice = 0;

        for (Map.Entry<String, TicketType> entry : bands.entrySet()) {
            String band = entry.getKey();
            TicketType tickets = entry.getValue();
            double price = bandPrices.getOrDefault(band, 0.0);

            totalChildPrice += price * tickets.getChild();
            totalPensionerPrice += price * tickets.getPensioner();
            totalFullPrice += price * (tickets.getChild() + tickets.getAdult() + tickets.getPensioner());
        }
        log.info("Total prices: Child: {}, Pensioner: {}, Full: {}", totalChildPrice, totalPensionerPrice, totalFullPrice);
        return new PriceBreakdown(totalChildPrice, totalPensionerPrice, totalFullPrice);
    }

    private double calculateSocialClubDiscount(double totalFullPrice, int totalTickets,
                                               Map<String, Double> discountMap) {
        double baseDiscount = discountMap.getOrDefault("SOCIAL_CLUB", 0.0);
        double additionalDiscount = totalTickets > 20
                ? discountMap.getOrDefault("QUANTITY", 0.0)
                : 0.0;
        return totalFullPrice * ((baseDiscount + additionalDiscount) / 100);
    }

    private void calculateRegularDiscounts(DiscountRequest request, PriceBreakdown prices,
                                           Map<String, Double> discountMap, DiscountResponse response) {
        // Child and Pensioner discounts
        double childDiscountPercent = discountMap.getOrDefault("CHILDREN", 0.0);
        double pensionerDiscountPercent = discountMap.getOrDefault("PENSIONERS", 0.0);
        response.setChild(prices.totalChildPrice * (childDiscountPercent / 100));
        response.setPensioner(prices.totalPensionerPrice * (pensionerDiscountPercent / 100));

        // Last Hour discount
        if (isLastHour(request.getShowTime())) {
            double lastHourPercent = discountMap.getOrDefault("LAST_HOUR", 0.0);
            response.setLastHour(prices.totalFullPrice * (lastHourPercent / 100));
        }

        // Weekday discount
        if (isWeekday(request.getDay())) {
            double weekdayPercent = discountMap.getOrDefault("WEEKDAY_SPECIAL", 0.0);
            response.setWeekday(prices.totalFullPrice * (weekdayPercent / 100));
        }
    }

    private boolean isLastHour(LocalDateTime showTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        long hoursUntilShow = ChronoUnit.HOURS.between(currentTime, showTime);
        return hoursUntilShow <= 1;
    }

    private boolean isWeekday(String day) {
        List<String> weekdays = Arrays.asList("monday", "tuesday", "wednesday", "thursday");
        return weekdays.contains(day.toLowerCase());
    }

    private static class PriceBreakdown {
        double totalChildPrice;
        double totalPensionerPrice;
        double totalFullPrice;

        PriceBreakdown(double totalChildPrice, double totalPensionerPrice, double totalFullPrice) {
            this.totalChildPrice = totalChildPrice;
            this.totalPensionerPrice = totalPensionerPrice;
            this.totalFullPrice = totalFullPrice;
        }
    }

    @Override
    public List<DiscountEntity> getAllDiscounts() {
        log.info("Fetching all discounts");
        return discountRepository.findAll();
    }

    @Override
    public DiscountEntity getDiscountByType(String discountType) {
        log.info("Fetching discount for type: {}", discountType);
        return discountRepository.findByDiscountType(discountType)
                .orElseThrow(() -> new DiscountNotFoundException("Discount not found for type: " + discountType));
    }
    @Override
    public DiscountEntity createOrUpdateDiscount(DiscountEntity discount) {
        log.info("Creating or updating discount: {}", discount);
        return discountRepository.save(discount);
    }

    @Override
    @Transactional
    public void deleteDiscount(String id) {
        if (!discountRepository.existsById(id)) {
            throw new DiscountNotFoundException("Discount not found for ID: " + id);
        }
        discountRepository.deleteById(id);
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/EventServiceImpl.java

package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.AddShowTimesRequestDto;
import project.community.theatre.dto.requestDto.DeleteShowTimeRequestDto;
import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;
import project.community.theatre.exception.InvalidImageException;
import project.community.theatre.exception.ResourceNotFoundException;
import project.community.theatre.mapper.EventMapper;
import project.community.theatre.model.EventEntity;
import project.community.theatre.model.ShowTimeEntity;
import project.community.theatre.model.TicketEntity;
import project.community.theatre.repository.EventRepository;
import project.community.theatre.repository.ShowTimeRepository;
import project.community.theatre.dto.responseDto.ShowTimeResponseDto;
import project.community.theatre.repository.TicketRepository;
import project.community.theatre.service.EventService;
import project.community.theatre.service.ImageService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public EventResponseDto addEvent(EventEntryDto eventEntryDto) {
        log.info("Adding a new event :: {}", eventEntryDto);
        try {
            String imageUrl = imageService.getImageUrl(eventEntryDto.getImage());
            EventEntity eventEntity = EventMapper.mapDtoToEntity(eventEntryDto);
            eventEntity.setImageUrl(imageUrl);

            eventRepository.save(eventEntity);

            log.info("Event added successfully :: {}", eventEntity);
            return EventMapper.mapEntityToDto(eventEntity);
        } catch (InvalidImageException e) {
            log.error("Invalid image: {}", e.getMessage(), e);
            throw e; // Let GlobalExceptionHandler handle it
        } catch (Exception e) {
            log.error("Error while adding event :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to add event", e);
        }
    }

    @Override
    public EventResponseDto getEvent(String id) {
        log.info("Fetching movie by ID :: {}", id);
        try {
            return eventRepository.findEventById(id)
                    .map(EventMapper::mapEntityToDto)
                    .orElseThrow(() -> {
                        log.error("Movie not found with ID :: {}", id);
                        return new RuntimeException("Movie not found with ID: " + id);
                    });
        } catch (Exception e) {
            log.error("Error while fetching event by ID :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch event", e);
        }
    }

    @Override
    public List<EventResponseDto> getAllEvent() {
        log.info("Fetching all events");
        try {
            List<EventEntity> eventEntities = eventRepository.findAll();
            eventEntities.sort(Comparator.comparing(EventEntity::getStartDate));
            if (eventEntities.isEmpty()) {
                log.warn("No event found in the database");
            }
            return eventEntities.stream()
                    .map(EventMapper::mapEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while fetching all events :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch all events", e);
        }
    }

    @Override
    public void deleteEvent(String eventId) {
        log.info("Deleting event with ID: {}", eventId);

        // Fetch the event
        EventEntity event = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        // Delete associated tickets
        List<TicketEntity> tickets = ticketRepository.findEventById(eventId);
        ticketRepository.deleteAll(tickets);

        // Delete associated show times
        List<ShowTimeEntity> showTimes = showTimeRepository.findByEvent(event);
        showTimeRepository.deleteAll(showTimes);

        // Delete the event
        eventRepository.delete(event);

        log.info("Event and all associated data deleted successfully for ID: {}", eventId);
    }

    @Override
    public void addShowTimes(AddShowTimesRequestDto request) {
        log.info("Adding show times for event ID: {}", request.getEventId());

        EventEntity eventEntity = eventRepository.findEventById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + request.getEventId()));

        List<ShowTimeEntity> showTimeEntities = request.getShowTimes().stream()
                .map(showTime -> ShowTimeEntity.builder()
                        .event(eventEntity)
                        .showTime(showTime)
                        .build())
                .toList();

        if (eventEntity.getShowTimes() == null) {
            eventEntity.setShowTimes(showTimeEntities);
        } else {
            eventEntity.getShowTimes().addAll(showTimeEntities);
        }

        eventRepository.save(eventEntity);
        log.info("Show times added successfully for event ID: {}", request.getEventId());
    }

    @Override
    public List<ShowTimeResponseDto> getShowTimesForEvent(String eventId) {
        log.info("Fetching show times for event ID: {}", eventId);

        EventEntity eventEntity = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        // Extract the show times from the event's showTimes field
        return eventEntity.getShowTimes().stream()
                .map(showTimeEntity -> ShowTimeResponseDto.builder()
                        .id(showTimeEntity.getId())
                        .showTime(showTimeEntity.getShowTime())
                        .build())
                .toList();
    }

    @Override
    public void deleteShowTime(DeleteShowTimeRequestDto request) {
        log.info("Deleting show time {} for event ID: {}", request.getShowTime(), request.getEventId());

        EventEntity eventEntity = eventRepository.findEventById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + request.getEventId()));

        if (eventEntity.getShowTimes() != null && eventEntity.getShowTimes().remove(request.getShowTime())) {
            eventRepository.save(eventEntity);
            log.info("Show time deleted successfully: {}", request.getShowTime());
        } else {
            throw new ResourceNotFoundException("Show time not found: " + request.getShowTime());
        }
    }

}
// File: ./src/main/java/project/community/theatre/service/impl/ImageServiceImpl.java

package project.community.theatre.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.community.theatre.constant.AppConstants;
import project.community.theatre.enums.ImageFormat;
import project.community.theatre.exception.InvalidImageException;
import project.community.theatre.service.ImageService;
import project.community.theatre.util.CustomMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Autowired
    private Cloudinary cloudinary;

    /**
     * Processes the given MultipartFile and returns the URL of the processed image.
     *
     * The image is first validated to check if it's a valid image file. If not, an
     * {@link InvalidImageException} is thrown.
     *
     * The image is then resized and compressed locally using the
     * {@link Thumbnails} library.
     *
     * The processed file is then uploaded to Cloudinary using the
     * {@link Cloudinary} library.
     *
     * @param image the image to process
     * @return the URL of the processed image
     * @throws IOException if there is an error uploading the image
     */
    public String getImageUrl(MultipartFile image) throws IOException {
        return processImage(image);
    }

    /**
     * Uploads the given MultipartFile to Cloudinary and returns the URL of the uploaded image.
     *
     * @param file the image to upload
     * @return the URL of the uploaded image
     * @throws IOException if there is an error uploading the image
     */
    private String uploadImage(MultipartFile file) throws IOException {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return Optional.ofNullable(uploadResult.get("url"))
                    .map(String::valueOf)
                    .orElseThrow(() -> new IOException("Failed to retrieve image URL from Cloudinary"));
        } catch (Exception e) {
            log.error("Failed to upload image to Cloudinary: {}", e.getMessage(), e);
            throw new IOException("Failed to upload image to Cloudinary: " + e.getMessage(), e);
        }
    }

    /**
     * Process the given MultipartFile and return the URL of the processed image.
     *
     * The image is first validated to check if it's a valid image file. If not, an
     * {@link InvalidImageException} is thrown.
     *
     * The image is then resized and compressed locally using the
     * {@link Thumbnails} library.
     *
     * The processed file is then uploaded to Cloudinary using the
     * {@link #uploadImage(MultipartFile)} method.
     *
     * @param image the image to process
     * @return the URL of the processed image
     * @throws IOException if there is an error processing the image
     * @throws InvalidImageException if the image is not a valid image file
     */
    private String processImage(MultipartFile image) {
        File tempFile = null;
        try {
            validateImage(image);

            // Generate a unique filename for temporary storage
            String fileName = UUID.randomUUID() + "." + getExtension(image.getOriginalFilename());
            tempFile = createTempFile(fileName);

            // Resize and compress the image locally
            Thumbnails.of(image.getInputStream())
                    .size(AppConstants.IMAGE_WIDTH, AppConstants.IMAGE_HEIGHT)
                    .outputQuality(AppConstants.IMAGE_QUALITY)
                    .toFile(tempFile);

            // Convert the processed file back to MultipartFile

            return uploadImage(new CustomMultipartFile(tempFile, image.getOriginalFilename()));

        } catch (InvalidImageException | IllegalArgumentException e) {
            log.error("Validation failed: {}", e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            log.error("Failed to process the image", e);
            throw new RuntimeException("Failed to process the image", e);
        }finally {
            cleanupTempFile(tempFile);
        }
    }

    /**
     * Validates the given MultipartFile to ensure it is a valid image file.
     * The validation includes:
     * <ul>
     *     <li>Checking if the content type of the file is an image type</li>
     *     <li>Checking if the file size is less than the maximum allowed size (5MB)</li>
     *     <li>Checking if the file extension is one of the supported image formats</li>
     * </ul>
     * If any of the validation fails, an {@link InvalidImageException} is thrown.
     *
     * @param image the image to validate
     * @throws InvalidImageException if the image is not a valid image file
     */
    private void validateImage(MultipartFile image) {
        if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
            throw new InvalidImageException("Only image files are allowed");
        }
        if (image.getSize() > AppConstants.MAX_FILE_SIZE_BYTES) {
            throw new InvalidImageException("File size exceeds the limit of 5MB");
        }
        String extension = getExtension(image.getOriginalFilename());
        if (!ImageFormat.isSupported(extension)) {
            throw new InvalidImageException("Unsupported file format. Supported formats: " + ImageFormat.getSupportedFormatsAsString());
        }
    }


    /**
     * Creates a temporary file with the given filename.
     * The file is created in the system's default temporary directory.
     * The file name will be prefixed with "temp-" and suffixed with the given filename.
     * A log message at INFO level is written to indicate the creation of the file.
     * @param fileName the filename of the temporary file
     * @return the created temporary file
     * @throws IOException if an I/O error occurs
     */
    private File createTempFile(String fileName) throws IOException {
        File tempFile = File.createTempFile("temp-", "-" + fileName);
        log.info("Temporary file created: {}", tempFile.getAbsolutePath());
        return tempFile;
    }

    /**
     * Cleans up a temporary file.
     * <p>
     * If the given file is not null and exists, it is attempted to be deleted.
     * If the deletion is successful, a log message at INFO level is written.
     * If the deletion fails, a log message at WARN level is written.
     *
     * @param tempFile the temporary file to clean up
     */
    private void cleanupTempFile(File tempFile) {
        if (tempFile != null && tempFile.exists()) {
            try {
                Files.delete(tempFile.toPath());
                log.info("Temporary file deleted: {}", tempFile.getAbsolutePath());
            } catch (IOException e) {
                log.warn("Failed to delete temporary file: {}", tempFile.getAbsolutePath(), e);
            }
        }
    }

    /**
     * Gets the file extension of the given filename.
     * The extension is returned in lower case.
     * If the given filename does not contain a dot, an {@link InvalidImageException} is thrown.
     * @param originalFilename the filename from which to get the extension
     * @return the file extension
     * @throws InvalidImageException if the filename does not contain a dot
     */
    private String getExtension(String originalFilename) {
        return Optional.ofNullable(originalFilename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".") + 1).toLowerCase())
                .orElseThrow(() -> new InvalidImageException("Invalid file format"));
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/PaymentGatewayImpl.java

package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.exception.PaymentFailedException;
import project.community.theatre.service.PaymentGateway;

import java.util.UUID;

@Service
@Slf4j
public class PaymentGatewayImpl implements PaymentGateway {

    @Override
    public PaymentResponse processPayment(PaymentRequest request) throws PaymentFailedException {
        log.info("Processing payment: {}", request);
        try {
            Thread.sleep(1000); // Fake delay to mimic network call
            String transactionId = UUID.randomUUID().toString();
            double amount = request.getPayableAmount();

            return new PaymentResponse(Boolean.TRUE, "Payment processed successfully for " + amount, transactionId);
        } catch (InterruptedException e) {
            throw new PaymentFailedException("Payment simulation interrupted");
        }
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/PaymentServiceImpl.java

package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.dto.responseDto.PaymentResponse;
import project.community.theatre.service.PaymentGateway;
import project.community.theatre.service.PaymentService;

import java.util.UUID;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentGateway paymentGateway;

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Simulate payment processing
        if (isValidPayment(paymentRequest)) {
            return paymentGateway.processPayment(paymentRequest);
        } else {
            String transactionId = UUID.randomUUID().toString();
            log.error("Invalid payment request: {} :: transactionId: {}", paymentRequest, transactionId);
            return new PaymentResponse(false, "Payment failed", transactionId);
        }
    }

    private boolean isValidPayment(PaymentRequest paymentRequest) {
        // Dummy validation logic
        if (paymentRequest.getPayableAmount() <= 0 || paymentRequest.getPaymentDetails().getCardNumber().startsWith("4000")) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/ReviewServiceImpl.java

package project.community.theatre.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.community.theatre.dto.responseDto.ReviewResponseDto;
import project.community.theatre.exception.ResourceNotFoundException;
import project.community.theatre.model.EventEntity;
import project.community.theatre.model.ReviewEntity;
import project.community.theatre.repository.EventRepository;
import project.community.theatre.repository.ReviewRepository;
import project.community.theatre.service.ReviewService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final EventRepository eventRepository;

    @Transactional
    public ReviewEntity saveReview(String userName, Integer rating, String description, String eventId) {
        log.info("Saving review for event ID: {}", eventId);

        // Fetch the event to ensure it exists
        EventEntity event = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        // Set default username if empty
        if (userName == null || userName.trim().isEmpty()) {
            userName = "Anonymous";
        }

        // Create and save the review
        ReviewEntity review = ReviewEntity.builder()
                .reviewId(UUID.randomUUID().toString())
                .userName(userName)
                .rating(rating)
                .description(description)
                .reviewedDate(LocalDate.now())
                .event(event)
                .build();

        return reviewRepository.save(review);
    }

    @Override
    public List<ReviewResponseDto> getAllReviews(String eventId) {
        log.info("Fetching reviews for event ID: {}", eventId);

        EventEntity eventEntity = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        // Extract the show times from the event's showTimes field
        return eventEntity.getReviewId().stream()
                .map(ReviewEntity -> ReviewResponseDto.builder()
                        .reviewId(ReviewEntity.getReviewId())
                        .userName(ReviewEntity.getUserName())
                        .rating(ReviewEntity.getRating())
                        .description(ReviewEntity.getDescription())
                        .reviewDate(ReviewEntity.getReviewedDate())
                        .build())
                .toList();
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/SeatServiceImpl.java

package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import project.community.theatre.service.EventService;
import project.community.theatre.service.SeatService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SeatServiceImpl implements SeatService {

    @Autowired
    EventService eventService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Value("${app.seat.lock.expiry}")
    private String tempLockExpiry;
    @Value("${app.redis.temp.seat.lock.key}")
    private String tempSeatLockKeyFormat;
    @Value("${app.redis.booked.seat.lock.key}")
    private String bookedSeatLockKeyFormat;

    @Override
    public void lockSeats(String eventId, String showId, List<String> seatNumbers) {
        log.info("Locking seats for show ID: {} and seats: {}", showId, seatNumbers);
        // Lock all requested seats in Redis
        for (String seat : seatNumbers) {
            String key = tempSeatLockKeyFormat
                    .replace("{eventId}", eventId)
                    .replace("{showId}", showId)
                    .replace("{seat}", seat);
            redisTemplate.opsForValue().set(key, "LOCKED", Long.parseLong(tempLockExpiry), TimeUnit.SECONDS);
        }
    }

    @Override
    public List<String> checkSeatsAvailability(String eventId, String showId, List<String> seatNumbers) {
        log.info("Checking availability of seats for show ID: {} and seats: {}", showId, seatNumbers);
        return seatNumbers.stream()
                .filter(seatNumber -> {
                    String key = tempSeatLockKeyFormat
                            .replace("{eventId}", eventId)
                            .replace("{showId}", showId)
                            .replace("{seat}", seatNumber);

                    return Boolean.TRUE.equals(redisTemplate.hasKey(key));
                })
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> processSeatsAvailability(String eventId, String showId, List<String> seatNumbers) {
        log.info("Checking and locking seats for show ID: {} and seats: {}", showId, seatNumbers);

        List<String> unavailableSeats = checkSeatsAvailability(eventId, showId, seatNumbers);

        // If any seats are unavailable, return them
        if (!unavailableSeats.isEmpty()) {
            log.warn("Unavailable seats found: {}", unavailableSeats);
            return Map.of(
                    "status", Boolean.FALSE,
                    "unavailableSeats", unavailableSeats
            );
        }
        // Lock all requested seats in Redis
        lockSeats(eventId, showId, seatNumbers);

        log.info("All seats locked successfully: {}", seatNumbers);
        return Map.of(
                "status", Boolean.TRUE
        );
    }

    @Override
    public void lockBookedSeats(String eventId, String showId, List<String> bookedSeats) {
        log.info("Locking booked seats for show ID {}: {}", showId, bookedSeats);
        String redisKey = bookedSeatLockKeyFormat
                .replace("{eventId}", eventId)
                .replace("{showId}", showId);
        Boolean keyExists = redisTemplate.hasKey(redisKey);
        if (Boolean.TRUE.equals(keyExists)) {
            log.info("Appending {} seats to the existing list for show ID {}", bookedSeats.size(), showId);
            redisTemplate.opsForList().rightPushAll(redisKey, bookedSeats);
        } else {
            log.info("Creating a new list with {} seats for show ID {}", bookedSeats.size(), showId);
            redisTemplate.opsForList().rightPushAll(redisKey, bookedSeats);
            // Set the TTL for the key (only when creating a new list)
            redisTemplate.expire(redisKey, getEventExpirationTime(eventId), TimeUnit.SECONDS);
        }
        log.info("Successfully locked {} seats for show ID {}", bookedSeats.size(), showId);
    }

    @Override
    public List<String> getAllBookedSeats(String eventId, String showId) {
        log.info("Fetching booked and locked seats for event ID: {} and show ID: {}", eventId, showId);

        // Get booked seats from the list stored under the booked key
        String bookedRedisKey = bookedSeatLockKeyFormat
                .replace("{eventId}", eventId)
                .replace("{showId}", showId);
        List<String> bookedSeats = redisTemplate.opsForList().range(bookedRedisKey, 0, -1);
        if (bookedSeats == null) {
            bookedSeats = Collections.emptyList();
            log.info("No booked seats found for event ID: {} and show ID: {}", eventId, showId);
        } else {
            log.info("Found {} booked seats for event ID: {} and show ID: {}", bookedSeats.size(), eventId, showId);
        }

        Set<String> lockKeys = getSeatLockKeys(eventId, showId);
        Set<String> lockedSeats = new HashSet<>();
        for (String key : lockKeys) {
            String seat = key.substring(key.lastIndexOf(':') + 1);
            lockedSeats.add(seat);
        }
        log.info("Found {} locked seats.", lockedSeats.size());

        Set<String> allLockedSeats = new HashSet<>(bookedSeats);
        allLockedSeats.addAll(lockedSeats);
        log.info("Total booked and locked seats: {}", allLockedSeats.size());

        return new ArrayList<>(allLockedSeats);
    }

    private long getEventExpirationTime(String eventId) {
        LocalDate endDate = eventService.getEvent(eventId).getEndDate();
        // Assume the end time is at the end of the day (23:59:59)
        long endTimeMillis = endDate.atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        // Calculate the remaining time in seconds
        long currentTimeMillis = System.currentTimeMillis();
        return ChronoUnit.SECONDS.between(
                Instant.ofEpochMilli(currentTimeMillis),
                Instant.ofEpochMilli(endTimeMillis)
        );
    }

    private Set<String> getSeatLockKeys(String eventId, String showId) {
        // Construct the pattern with the known eventId and showId
        String pattern = tempSeatLockKeyFormat
                .replace("{eventId}", eventId)
                .replace("{showId}", showId)
                .replace("{seat}", "*");

        return redisTemplate.keys(pattern);
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/TicketServiceImpl.java

package project.community.theatre.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.community.theatre.dto.responseDto.TicketResponse;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.exception.ResourceNotFoundException;
import project.community.theatre.model.*;
import project.community.theatre.repository.EventRepository;
import project.community.theatre.repository.PaymentHistoryRepository;
import project.community.theatre.repository.TicketRepository;
import project.community.theatre.repository.UserRepository;
import project.community.theatre.service.ProcessTicketAsync;
import project.community.theatre.service.SeatService;
import project.community.theatre.service.TicketService;
import project.community.theatre.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    @Autowired
    UserService userService;

    @Autowired
    ProcessTicketAsync processTicketAsync;

    @Autowired
    SeatService seatService;


    private final TicketRepository ticketRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public TicketResponse generateAndSaveTicket(PaymentRequest paymentRequest, String transactionId) {
        log.info("Generating and saving ticket :: transactionId: {}", transactionId);
        // Generate a unique ticket number
        String ticketId = UUID.randomUUID().toString();
    
        // Convert seat numbers to a comma-separated string
        String seatNumbersString = String.join(",", paymentRequest.getSeatNumbers());
    
        // Convert showTime from String to LocalDateTime
        LocalDateTime showTime = LocalDateTime.parse(paymentRequest.getShowTime());
    
        // Create and save the ticket
        TicketEntity ticket = TicketEntity.builder()
                .ticketNumber(ticketId)
                .user(userRepository.findUserById(paymentRequest.getUserId())
                        .orElse(new UserEntity(paymentRequest.getUserId())))
                .event(eventRepository.findEventById(paymentRequest.getEventId())
                        .orElse(new EventEntity(paymentRequest.getEventId())))
                .totalPrice(paymentRequest.getPayableAmount())
                .seatNumbers(seatNumbersString)
                .showTime(showTime)
                .bookingTime(LocalDateTime.now())
                .status(TicketEntity.TicketStatus.BOOKED)
                .showTimeId(new ShowTimeEntity(paymentRequest.getShowId()))
                .build();
        log.info("Saving ticket: {}", ticket);
        ticketRepository.save(ticket);

        // Lock booked seats
        seatService.lockBookedSeats(paymentRequest.getEventId(), paymentRequest.getShowId(), paymentRequest.getSeatNumbers());
    
        // Save payment history
        PaymentHistoryEntity paymentHistory = PaymentHistoryEntity.builder()
                .id(transactionId)
                .user(new UserEntity(paymentRequest.getUserId()))
                .transactionId(transactionId)
                .amount(paymentRequest.getPayableAmount())
                .paymentTime(LocalDateTime.now())
                .status(PaymentHistoryEntity.PaymentStatus.SUCCESS)
                .build();
        log.info("Saving payment history: {}", paymentHistory);
        paymentHistoryRepository.save(paymentHistory);

        UserEntity user = userService.getUserById(paymentRequest.getUserId());
        // Start async ticket delivery process in a new thread
        processTicketAsync.processTicketDeliveryAsync(ticket, paymentRequest.getEmail());

        // Map the ticket entity to a response DTO
        return new TicketResponse(
                ticket.getTicketNumber(),
                ticket.getTotalPrice(),
                ticket.getSeatNumbers(),
                ticket.getShowTime().toString(),
                ticket.getEvent().getName(),
                ticket.getBookingTime(),
                ticket.getStatus().name()
        );
    }


    @Override
    public TicketResponse getTicketDetails(String ticketNumber) {
        // Fetch the ticket from the database
        TicketEntity ticket = ticketRepository.findByTicketNumber(ticketNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ticket number: " + ticketNumber));

        // Map TicketEntity to TicketResponse
        return TicketResponse.builder()
                .ticketNumber(ticket.getTicketNumber())
                .totalPrice(ticket.getTotalPrice())
                .seatNumbers(ticket.getSeatNumbers())
                .showTime(ticket.getShowTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .eventName(ticket.getEvent().getName())
                .bookingTime(ticket.getBookingTime())
                .status(ticket.getStatus().toString())
                .build();
    }
}
// File: ./src/main/java/project/community/theatre/service/impl/UserServiceImpl.java

package project.community.theatre.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.LoginRequest;
import project.community.theatre.dto.requestDto.SignupRequest;
import project.community.theatre.dto.responseDto.AuthResponse;
import project.community.theatre.exception.UserAlreadyExistsException;
import project.community.theatre.exception.UserNotFoundException;
import project.community.theatre.model.UserEntity;
import project.community.theatre.repository.UserRepository;
import project.community.theatre.service.UserService;
import project.community.theatre.util.JwtUtil;
import project.community.theatre.util.PasswordEncoderUtil;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoder;

    private static final List<String> VALID_ROLES = List.of("USER", "ADMIN");

    @Transactional
    @Override
    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        String hashedPassword = passwordEncoder.encodePassword(request.getPassword());

        String role = request.getRole() != null && VALID_ROLES.contains(request.getRole()) ? request.getRole() : "USER";

        UserEntity user = UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .mobileNo(request.getMobileNo())
                .email(request.getEmail())
                .password(hashedPassword)
                .role(role)
                .build();
        userRepository.save(user);
        log.info("User with email {} saved successfully",request.getEmail());

        String token = jwtUtil.generateToken(user.getUserId(), user.getRole());

        return new AuthResponse("User registered successfully", user.getUserId(), user.getRole(), token);
    }

    @Transactional
    @Override
    public UserEntity updateUser(String userId, UserEntity updatedUser) {
        return userRepository.findUserById(userId).map(existingUser -> {

            if (updatedUser.getName() != null && !updatedUser.getName().isBlank()) {
                existingUser.setName(updatedUser.getName());
            }
            
            if (updatedUser.getMobileNo() != null && !updatedUser.getMobileNo().isBlank()) {
                existingUser.setMobileNo(updatedUser.getMobileNo());
            }

            if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank()) {
                existingUser.setEmail(updatedUser.getEmail());
            }

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                existingUser.setPassword(passwordEncoder.encodePassword(updatedUser.getPassword()));
            }

            existingUser.setRole(updatedUser.getRole());

            log.info("User with ID {} updated successfully", userId);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getUserId(), user.getRole());

        return new AuthResponse("Login successful", user.getUserId(), user.getRole(), token);

    }

    @Override
    public UserEntity getUserById(String userId) {
        return userRepository.findUserById(userId).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + userId));
    }

    @Override
    public UserEntity getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail).orElseThrow(() ->
                new UserNotFoundException("User not found with email: " + userEmail));
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getPassword(),
                Collections.singletonList(user::getRole)
        );
    }

    @Override
    public List<UserEntity> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }
}
// File: ./src/main/java/project/community/theatre/util/CustomMultipartFile.java

package project.community.theatre.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Custom implementation of Spring's MultipartFile interface to handle file uploads.
 * This class wraps a File object and provides methods to retrieve file information and content.
 */
public class CustomMultipartFile implements MultipartFile {

    private final File file;
    private final String originalFilename;

    /**
     * Constructs a new CustomMultipartFile instance.
     *
     * @param file The underlying File object representing the uploaded file.
     * @param originalFilename The original filename provided by the client.
     */
    public CustomMultipartFile(File file, String originalFilename) {
        this.file = file;
        this.originalFilename = originalFilename;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return "image/" + getExtension(originalFilename);
    }

    @Override
    public boolean isEmpty() {
        return file.length() == 0;
    }

    @Override
    public long getSize() {
        return file.length();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        Files.copy(file.toPath(), dest.toPath());
    }

    /**
     * Extracts the file extension from the given filename.
     *
     * @param filename The filename to extract the extension from.
     * @return The extracted file extension. If no extension is found, returns "jpg".
     */
    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "jpg";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
// File: ./src/main/java/project/community/theatre/util/DateTimeConverter.java

package project.community.theatre.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class provides utility methods for converting date and time from ISO 8601 format to human-readable format.
 */
public class DateTimeConverter {
    /**
     * Converts a given ISO 8601 date-time to a human-readable date format.
     *
     * @param isoDateTime The ISO 8601 date-time to be converted.
     * @return A string representing the date in the format "MMMM dd, yyyy".
     */
    public static String getHumanReadableDate(LocalDateTime isoDateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return isoDateTime.format(dateFormatter);
    }

    /**
     * Converts a given ISO 8601 date-time to a human-readable time format.
     *
     * @param isoDateTime The ISO 8601 date-time to be converted.
     * @return A string representing the time in the format "h:mm a".
     */
    // Function to return the time in human-readable format
    public static String getHumanReadableTime(LocalDateTime isoDateTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        return isoDateTime.format(timeFormatter);
    }

}

// File: ./src/main/java/project/community/theatre/util/EmailService.java

package project.community.theatre.util;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

/**
 * This service is responsible for sending emails with attachments using SendGrid.
 */
@Service
@Slf4j
public class EmailService {
    private final SendGrid sendGrid;

    /**
     * Constructor to initialize the SendGrid client with the provided API key.
     *
     * @param apiKey The API key for SendGrid.
     */
    public EmailService(@Value("${sendgrid.api.key}") String apiKey) {
        this.sendGrid = new SendGrid(apiKey);
    }

    /**
     * Sends an email with a PDF attachment to the specified recipient.
     *
     * @param to The email address of the recipient.
     * @param name The name of the recipient.
     * @param pdfBytes The byte array representing the PDF content.
     */
    public void sendEmailWithPDF(String to, String name, byte[] pdfBytes) {
        log.info("Sending email with PDF attachment to {}", to);
        try {
            // Set up email details
            Email from = new Email("collegeonlineclass@gmail.com");
            String subject = "GCT Ticket booking confirmation";
            Email toEmail = new Email(to);
            Content content = new Content("text/plain", "Dear " + name + ",\n\nHere is your GCT ticket booking confirmation for the event");

            // Create mail object
            Mail mail = new Mail(from, subject, toEmail, content);

            // Add PDF attachment
            Attachments attachments = new Attachments();
            attachments.setContent(Base64.getEncoder().encodeToString(pdfBytes));
            attachments.setType("application/pdf");
            attachments.setFilename("ticket.pdf");
            attachments.setDisposition("attachment");
            mail.addAttachments(attachments);

            // Send the email
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                log.info("Email sent successfully to {}", to);
            } else {
                log.error("Failed to send email to {}. Status: {}, Body: {}", to, response.getStatusCode(), response.getBody());
                throw new RuntimeException("Email sending failed with status: " + response.getStatusCode());
            }
        } catch (IOException e) {
            log.error("IOException sending email to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Failed to send email due to I/O error: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error sending email to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Unexpected error while sending email: " + e.getMessage(), e);
        }
    }
}
// File: ./src/main/java/project/community/theatre/util/JwtUtil.java

package project.community.theatre.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration; // Token expiration time in milliseconds

    /**
     * Generate a JWT token.
     *
     * @param userId The unique identifier of the user.
     * @param role The role of the user.
     * @return A JWT token containing the user's ID and role.
     */
    public String generateToken(String userId, String role) {
        log.info("Generating JWT token for user: {}", userId);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Extract the user ID from the JWT token.
     *
     * @param token The JWT token.
     * @return The user ID extracted from the token.
     */
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract the user role from the JWT token.
     *
     * @param token The JWT token.
     * @return The user role extracted from the token.
     */
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    /**
     * Extract the expiration date from the JWT token.
     *
     * @param token The JWT token.
     * @return The expiration date extracted from the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Check if the JWT token is expired.
     *
     * @param token The JWT token.
     * @return True if the token is expired, false otherwise.
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validate the JWT token.
     *
     * @param token The JWT token.
     * @param userId The expected user ID.
     * @return True if the token is valid and not expired, false otherwise.
     */
    public Boolean validateToken(String token, String userId) {
        final String extractedUserId = extractUserId(token);
        return (extractedUserId.equals(userId) && !isTokenExpired(token));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
// File: ./src/main/java/project/community/theatre/util/PDFUtil.java

package project.community.theatre.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import project.community.theatre.model.TicketEntity;
import project.community.theatre.repository.EventRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Utility class for creating PDF tickets for community theatre events.
 *
 */
@Slf4j
@UtilityClass
public class PDFUtil {
    private EventRepository eventRepository;

    /**
     * Creates a PDF ticket for the given ticket entity and QR code bytes.
     *
     * @param qrCodeBytes The byte array representing the QR code image.
     * @param ticket      The ticket entity containing the ticket details.
     * @return A byte array representing the generated PDF ticket.
     * @throws RuntimeException If an error occurs while creating the PDF document or handling I/O operations.
     */
    public static byte[] createPDF(byte[] qrCodeBytes, TicketEntity ticket) {
        log.info("Creating PDF for ticket: {}", ticket.getTicketNumber());
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Header
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
            Paragraph header = new Paragraph("Greenwich Community Theatre - Event Ticket", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setSpacingAfter(20f);
            document.add(header);

            // Ticket Details
            Font detailFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
            Paragraph ticketDetails = new Paragraph();
            ticketDetails.setAlignment(Element.ALIGN_LEFT);
            ticketDetails.add(new Chunk("Ticket ID: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getTicketNumber() + "\n", detailFont));
            ticketDetails.add(new Chunk("Name: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getUser().getName() + "\n", detailFont));
            ticketDetails.add(new Chunk("Event: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getEvent().getName(), detailFont));
            ticketDetails.add(new Chunk("\nDate: ", detailFont));
            ticketDetails.add(new Chunk(DateTimeConverter.getHumanReadableDate(ticket.getShowTime()), detailFont));
            ticketDetails.add(new Chunk("\nTime: ", detailFont));
            ticketDetails.add(new Chunk(DateTimeConverter.getHumanReadableTime(ticket.getShowTime()), detailFont));
            ticketDetails.add(new Chunk("\nSeat Numbers: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getSeatNumbers(), detailFont));
            ticketDetails.add(new Chunk("\nTotal Price: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getTotalPrice() + "\n", detailFont));
            ticketDetails.add(new Chunk("\nVenue: ", detailFont));
            ticketDetails.add(new Chunk("GCT Main Stage", detailFont));
            ticketDetails.setSpacingAfter(20f);
            document.add(ticketDetails);

            // Centered QR Code
            Image qrImage = Image.getInstance(qrCodeBytes);
            qrImage.scaleToFit(150, 150);
            qrImage.setAlignment(Image.ALIGN_CENTER);
            qrImage.setSpacingBefore(10f);
            qrImage.setSpacingAfter(20f);
            document.add(qrImage);

            // Footer
            Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);
            Paragraph footer = new Paragraph("Thank you for choosing GCT! Please present this ticket at the entrance. Enjoy the show!", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return baos.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to create PDF document: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("I/O error while creating PDF: " + e.getMessage(), e);
        }
    }
}
// File: ./src/main/java/project/community/theatre/util/PasswordEncoderUtil.java

package project.community.theatre.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A utility class for encoding and matching passwords using BCrypt.
 * This class is a Spring component, which means it can be injected into other classes.
 */
@Component
public class PasswordEncoderUtil {

    /**
     * A static instance of BCryptPasswordEncoder for encoding passwords.
     */
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Encodes a raw password using BCrypt.
     *
     * @param rawPassword The raw password to be encoded.
     * @return The encoded password.
     */
    public String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    /**
     * Checks if a raw password matches an encoded password.
     *
     * @param rawPassword The raw password to be checked.
     * @param encodedPassword The encoded password to be matched.
     * @return True if the raw password matches the encoded password, false otherwise.
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
// File: ./src/main/java/project/community/theatre/util/QRCodeUtil.java

package project.community.theatre.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
/**
 * Utility class for generating QR codes for community theatre tickets.
 */
@Slf4j
public class QRCodeUtil {

    /**
     * Generates a QR code for a given ticket UUID and returns it as a byte array in PNG format.
     *
     * @param uuid    The unique identifier of the ticket.
     * @param width   The width of the generated QR code image.
     * @param height  The height of the generated QR code image.
     * @return A byte array containing the QR code image in PNG format.
     * @throws RuntimeException If an error occurs during QR code generation or writing to the output stream.
     */
    public static byte[] generateTicketQRCode(String uuid, int width, int height) {
        log.info("Generating QR code for ticket: {}", uuid);
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(uuid, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
            return baos.toByteArray();
        } catch (WriterException e) {
            // Handle QR code generation errors
            throw new RuntimeException("Failed to generate QR code: " + e.getMessage(), e);
        } catch (IOException e) {
            // Handle I/O errors from ByteArrayOutputStream
            throw new RuntimeException("Failed to write QR code to stream: " + e.getMessage(), e);
        }
    }
}
