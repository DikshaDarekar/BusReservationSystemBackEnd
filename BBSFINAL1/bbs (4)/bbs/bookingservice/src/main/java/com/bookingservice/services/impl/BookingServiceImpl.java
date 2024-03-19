package com.bookingservice.services.impl;

import com.bookingservice.dto.BookingDetailsDTO;
import com.bookingservice.entities.BookingDetails;
import com.bookingservice.entities.BusSeats;
import com.bookingservice.exceptions.CustomException;
import com.bookingservice.repositories.BookingDetailsRepository;
import com.bookingservice.services.BookingService;
import com.bookingservice.services.BusSeatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BusSeatsService busSeatsService;

    @Autowired
    private BookingDetailsRepository bookingDetailsRepository;

    public long save(BookingDetails bookingDetails) throws CustomException {
        CustomException customException=this.validate(bookingDetails);
        Map<String,Object> busDetailMap=this.findBusById(bookingDetails.getBusId());
        int totalSeats= (int) busDetailMap.get("seats");
        BusSeats busSeats=this.busSeatsService.findByBusId(bookingDetails.getBusId());
        if(busSeats!=null) {
            int remainingSeats = totalSeats - busSeats.getTotalSeatsBooked();
            if (bookingDetails.getSeatsToBook() > remainingSeats) {
                customException.addException("seatsToBook", remainingSeats + " seats available");
            }

            if (!customException.getExceptions().isEmpty()) {
                throw customException;
            }
            busSeats.setTotalSeatsBooked((short) (busSeats.getTotalSeatsBooked()+bookingDetails.getSeatsToBook()));
        }else {
            log.info(totalSeats+","+bookingDetails.getSeatsToBook());
            if(bookingDetails.getSeatsToBook()>totalSeats) {
                customException.addException("seatsToBook", totalSeats+" seats available");
            }
            if (!customException.getExceptions().isEmpty()) {
                throw customException;
            }

            busSeats=new BusSeats();
            busSeats.setBusId(bookingDetails.getBusId());
            busSeats.setTotalSeatsBooked(bookingDetails.getSeatsToBook());
        }
        BigDecimal fare= BigDecimal.valueOf((Double) busDetailMap.get("fare"));
        bookingDetails.setTotalAmount(BigDecimal.valueOf(bookingDetails.getSeatsToBook()).multiply(fare));
        bookingDetails=this.bookingDetailsRepository.save(bookingDetails);
        this.busSeatsService.save(busSeats);
        return bookingDetails.getId();
    }

    @Override
    public BookingDetailsDTO findById(long id) {

        Optional<BookingDetails> bookingDetails=this.bookingDetailsRepository.findById(id);
        if(bookingDetails.isPresent()) {
            return convertToBookingDetailsDTO(bookingDetails.get());
        }
        return null;
    }

    @Override
    public List<BookingDetailsDTO> findAllByUserId(long userId) {
        List<BookingDetails> bookingDetailsList= this.bookingDetailsRepository.findAllByBookedBy(userId);
        List<BookingDetailsDTO> bookingDetailsDTOS=new ArrayList<>();
        for(BookingDetails bookingDetails:bookingDetailsList) {
            bookingDetailsDTOS.add(this.convertToBookingDetailsDTO(bookingDetails));
        }
        return bookingDetailsDTOS;
    }

    @Override
    public Page<BookingDetailsDTO> findAll(Pageable pageable) {
        Page<BookingDetails> bookingDetailsPage=this.bookingDetailsRepository.findAll(pageable);
        List<BookingDetails> bookingDetailsList=bookingDetailsPage.getContent();
        List<BookingDetailsDTO> bookingDetailsDTOS=new ArrayList<>();
        for(BookingDetails bookingDetails:bookingDetailsList) {
            bookingDetailsDTOS.add(this.convertToBookingDetailsDTO(bookingDetails));
        }
        return new PageImpl<>(bookingDetailsDTOS,pageable,bookingDetailsDTOS.size());
    }

    private BookingDetailsDTO convertToBookingDetailsDTO(BookingDetails bookingDetails) {
        Map<String,Object> userDetails=restTemplate.getForObject("http://localhost:8081/api/users/findById/"+bookingDetails.getBookedBy(),Map.class);
        Map<String,Object> busDetails=restTemplate.getForObject("http://localhost:8082/api/busservice/bus/findbyid/"+bookingDetails.getBusId(),Map.class);
        BookingDetailsDTO bookingDetailsDTO=new BookingDetailsDTO();
        bookingDetailsDTO.setId(bookingDetails.getId());
        bookingDetailsDTO.setSeatsToBook(bookingDetails.getSeatsToBook());
        bookingDetailsDTO.setBookedBy(userDetails);
        bookingDetailsDTO.setBusDetails(busDetails);
        bookingDetailsDTO.setTotalAmount(bookingDetails.getTotalAmount());
        bookingDetailsDTO.setBookedAt(bookingDetails.getBookedAt());
        return bookingDetailsDTO;
    }

    @Override
    public void delete(long id) {
        Optional<BookingDetails> bookingDetailsOptional=this.bookingDetailsRepository.findById(id);
        if (bookingDetailsOptional.isPresent()) {
            BookingDetails bookingDetails=bookingDetailsOptional.get();
            int bookedSeats=bookingDetails.getSeatsToBook();
            BusSeats busSeats=this.busSeatsService.findByBusId(bookingDetails.getBusId());
            busSeats.setTotalSeatsBooked(busSeats.getTotalSeatsBooked()-bookedSeats);
            this.busSeatsService.save(busSeats);
            this.bookingDetailsRepository.delete(bookingDetails);
        }
    }

    private CustomException validate(BookingDetails bookingDetails) {
        log.info("Validate booking");
        CustomException customException=new CustomException();
        if(bookingDetails.getSeatsToBook()<=0) {
            customException.addException("seatsToBook","Seats to book must be greater than 0");
        }
        return customException;
    }

    private Map<String,Object> findBusById(long busId) {
        Map<String,Object> responseData=restTemplate.getForObject("http://localhost:8082/api/busservice/bus/findbyid/"+busId, Map.class);
        return responseData;
    }
}
