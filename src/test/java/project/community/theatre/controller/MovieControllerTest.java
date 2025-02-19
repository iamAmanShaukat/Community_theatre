package project.community.theatre.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import project.community.theatre.Controller.MovieController;
//import project.community.theatre.Dto.EntryRequestDto.MovieEntryDto;
//import project.community.theatre.Dto.ResponseDto.MovieResponseDto;
//import project.community.theatre.Service.MovieService;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(MovieController.class)
//public class MovieControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock // Use Mockito's @Mock annotation
//    private MovieService movieService;
//
//    @InjectMocks // Inject the mocked service into the controller
//    private MovieController movieController;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private MovieResponseDto mockMovieResponseDto;
//    private MovieEntryDto mockMovieEntryDto;
//
//    @BeforeEach
//    void setUp() {
//        // Create a mock MovieResponseDto
//        mockMovieResponseDto = new MovieResponseDto();
//        mockMovieResponseDto.setId(1);
//        mockMovieResponseDto.setName("Inception");
//
//        // Create a mock MovieEntryDto
//        mockMovieEntryDto = new MovieEntryDto();
//        mockMovieEntryDto.setName("Inception");
//    }
//
//    @Test
//    void testGetMovie_Success() throws Exception {
//        // Mock the service behavior
//        when(movieService.getMovie(1)).thenReturn(mockMovieResponseDto);
//
//        // Perform GET request and validate the response
//        mockMvc.perform(get("/movie/get-movie")
//                        .param("id", "1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("Inception"))
//                .andExpect(jsonPath("$.releaseDate").value("2010-07-16"));
//    }
//
//    @Test
//    void testGetMovie_NotFound() throws Exception {
//        // Mock the service behavior to throw an exception
//        when(movieService.getMovie(999)).thenThrow(new RuntimeException("Movie not found with ID: 999"));
//
//        // Perform GET request and validate the error response
//        mockMvc.perform(get("/movie/get-movie")
//                        .param("id", "999"))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Movie not found with ID: 999"));
//    }
//
//    @Test
//    void testGetAllMovies_Success() throws Exception {
//        // Mock the service behavior
//        List<MovieResponseDto> mockMovieList = Arrays.asList(mockMovieResponseDto);
//        when(movieService.getAllMovies()).thenReturn(mockMovieList);
//
//        // Perform GET request and validate the response
//        mockMvc.perform(get("/movie/get-all-movies"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].name").value("Inception"))
//                .andExpect(jsonPath("$[0].releaseDate").value("2010-07-16"));
//    }
//
//    @Test
//    void testAddMovie_Success() throws Exception {
//        // Mock the service behavior
//        when(movieService.addMovie(any(MovieEntryDto.class))).thenReturn(mockMovieResponseDto);
//
//        // Perform POST request and validate the response
//        mockMvc.perform(post("/movie/add-movie")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(mockMovieEntryDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("Inception"))
//                .andExpect(jsonPath("$.releaseDate").value("2010-07-16"));
//    }
//
//    @Test
//    void testAddMovie_InvalidInput() throws Exception {
//        // Perform POST request with invalid JSON and validate the response
//        mockMvc.perform(post("/movie/add-movie")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{}")) // Empty JSON
//                .andExpect(status().isBadRequest());
//    }
//}