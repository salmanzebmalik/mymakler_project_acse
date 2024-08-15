package de.unims.acse2024.mymakler.svc.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import de.unims.acse2024.mymakler.svc.api.data.model.Appointment;
import de.unims.acse2024.mymakler.svc.api.data.model.Attribute;
import de.unims.acse2024.mymakler.svc.api.data.model.MaklerUser;
import de.unims.acse2024.mymakler.svc.api.data.model.Role;
import de.unims.acse2024.mymakler.svc.api.data.model.ViewingOffer;
import de.unims.acse2024.mymakler.svc.api.data.model.ViewingRequest;
import de.unims.acse2024.mymakler.svc.api.data.repo.AppointmentRepository;
import de.unims.acse2024.mymakler.svc.api.data.repo.AttributeRepository;
import de.unims.acse2024.mymakler.svc.api.data.repo.ViewingOfferRepository;
import de.unims.acse2024.mymakler.svc.api.data.repo.ViewingRequestRepository;
import de.unims.acse2024.mymakler.svc.api.service.UserService;
import de.unims.acse2024.mymakler.svc.api.web.controller.dto.UserDto;
import de.unims.acse2024.mymakler.svc.api.web.controller.dto.ViewingOfferDto;

@EnableJms
@SpringBootApplication
public class Application {
  @Autowired
  AttributeRepository attributeRepository;

  @Autowired
  UserService userService;

  @Autowired
  ViewingOfferRepository viewingOfferRepository;

  @Autowired
  ViewingRequestRepository viewingRequestRepository;

  @Autowired
  AppointmentRepository appointmentRepository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    converter.setTypeIdMappings(Map.of(
        "ViewingOfferDto", ViewingOfferDto.class,
        "UserDto", UserDto.class
    ));
    return converter;
  }

  // We will create some dummy data after the application has become ready. Removing @EventListener skips this step
  @EventListener
  public void afterStartup(ApplicationReadyEvent event) throws Exception {
    // check if data was already created
    if (attributeRepository.existsById("student")) {
      return;
    }

    // Attributes
    var attrSmoker = new Attribute("smoker");
    var attrNonsmoker = new Attribute("nonsmoker");
    var attrOutgoing = new Attribute("outgoing");
    var attrStudent = new Attribute("student");
    attributeRepository.save(attrSmoker);
    attributeRepository.save(attrNonsmoker);
    attributeRepository.save(attrOutgoing);
    attributeRepository.save(attrStudent);

    // Users
    var l1 = new MaklerUser();
    l1.setUsername("l1");
    l1.setEmail("l1@example.com");
    l1.setPassword("$2a$10$2.HVaRoOSgHrjbiVEzbjqu2AeqXJvg52laME56IIeOJzRDie5mRXW"); // 1234
    l1.setRole(Role.LANDLORD);
    l1.setCityOfOrigin("Münster");
    l1.setAttributes(Set.of(attrNonsmoker, attrOutgoing, attrStudent));
    userService.create(l1);

    var l2 = new MaklerUser();
    l2.setUsername("l2");
    l2.setEmail("l2@example.com");
    l2.setPassword("$2a$10$2.HVaRoOSgHrjbiVEzbjqu2AeqXJvg52laME56IIeOJzRDie5mRXW"); // 1234
    l2.setRole(Role.LANDLORD);
    l2.setCityOfOrigin("Münster");
    l2.setAttributes(Set.of(attrSmoker));
    userService.create(l2);

    var t1 = new MaklerUser();
    t1.setUsername("t1");
    t1.setEmail("t1@example.com");
    t1.setPassword("$2a$10$2.HVaRoOSgHrjbiVEzbjqu2AeqXJvg52laME56IIeOJzRDie5mRXW"); // 1234
    t1.setRole(Role.TENANT);
    t1.setCityOfOrigin("Münster");
    t1.setAttributes(Set.of(attrNonsmoker, attrOutgoing, attrStudent));
    userService.create(t1);

    var t2 = new MaklerUser();
    t2.setUsername("t2");
    t2.setEmail("t2@example.com");
    t2.setPassword("$2a$10$2.HVaRoOSgHrjbiVEzbjqu2AeqXJvg52laME56IIeOJzRDie5mRXW"); // 1234
    t2.setRole(Role.TENANT);
    t2.setCityOfOrigin("Münster");
    t2.setAttributes(Set.of(attrNonsmoker));
    userService.create(t2);

    var t3 = new MaklerUser();
    t3.setUsername("t3");
    t3.setEmail("t3@example.com");
    t3.setPassword("$2a$10$2.HVaRoOSgHrjbiVEzbjqu2AeqXJvg52laME56IIeOJzRDie5mRXW"); // 1234
    t3.setRole(Role.TENANT);
    t3.setCityOfOrigin("Münster");
    t3.setAttributes(Set.of(attrNonsmoker));
    userService.create(t3);

    var t4 = new MaklerUser();
    t4.setUsername("t4");
    t4.setEmail("t4@example.com");
    t4.setPassword("$2a$10$2.HVaRoOSgHrjbiVEzbjqu2AeqXJvg52laME56IIeOJzRDie5mRXW"); // 1234
    t4.setRole(Role.TENANT);
    t4.setCityOfOrigin("Münster");
    t4.setAttributes(Set.of(attrSmoker));
    userService.create(t4);

    // ViewingOffers
    var vo1 = new ViewingOffer();
    vo1.setTitle("Cozy and deserted room in office building");
    vo1.setDescription("Dozens of people used to work here");
    vo1.setOfferingUser(l1);
    vo1.setCity("Muenster");
    vo1.setStreet("Leonardo-Campus 3");
    vo1.setPostalCode("48147");
    vo1.setSqMeters(52);
    vo1.setOnlineSince(LocalDateTime.now());
    vo1.setViewingDate(LocalDateTime.now().plusDays(10));
    viewingOfferRepository.save(vo1);

    var vo2 = new ViewingOffer();
    vo2.setTitle("A large broom closet. Will do for one semester");
    vo2.setDescription("What you see is pretty much what you get");
    vo2.setOfferingUser(l1);
    vo2.setCity("Muenster");
    vo2.setStreet("Leonardo-Campus 3");
    vo2.setPostalCode("48147");
    vo2.setSqMeters(8);
    vo2.setOnlineSince(LocalDateTime.now());
    vo2.setViewingDate(LocalDateTime.now().plusDays(10));
    viewingOfferRepository.save(vo2);

    var vo3 = new ViewingOffer();
    vo3.setTitle("Offering a couch 1");
    vo3.setDescription("Access to a bath is included.");
    vo3.setOfferingUser(l1);
    vo3.setCity("Muenster");
    vo3.setStreet("Leonardo-Campus 3");
    vo3.setPostalCode("48147");
    vo3.setSqMeters(8);
    vo3.setOnlineSince(LocalDateTime.now());
    vo3.setViewingDate(LocalDateTime.now().plusDays(10));
    viewingOfferRepository.save(vo3);

    var vo4 = new ViewingOffer();
    vo4.setTitle("Offering a couch 2");
    vo4.setDescription("Access to a bath is included.");
    vo4.setOfferingUser(l1);
    vo4.setCity("Muenster");
    vo4.setStreet("Leonardo-Campus 3");
    vo4.setPostalCode("48147");
    vo4.setSqMeters(8);
    vo4.setOnlineSince(LocalDateTime.now());
    vo4.setViewingDate(LocalDateTime.now().plusDays(10));
    viewingOfferRepository.save(vo4);

    var vo5 = new ViewingOffer();
    vo5.setTitle("Offering a couch 3");
    vo5.setDescription("Access to a bath is included.");
    vo5.setOfferingUser(l1);
    vo5.setCity("Muenster");
    vo5.setStreet("Leonardo-Campus 3");
    vo5.setPostalCode("48147");
    vo5.setSqMeters(8);
    vo5.setOnlineSince(LocalDateTime.now());
    vo5.setViewingDate(LocalDateTime.now().plusDays(10));
    viewingOfferRepository.save(vo5);

    var vo6 = new ViewingOffer();
    vo6.setTitle("Offering a couch 4");
    vo6.setDescription("Access to a bath is included.");
    vo6.setOfferingUser(l1);
    vo6.setCity("Muenster");
    vo6.setStreet("Leonardo-Campus 3");
    vo6.setPostalCode("48147");
    vo6.setSqMeters(8);
    vo6.setOnlineSince(LocalDateTime.now());
    vo6.setViewingDate(LocalDateTime.now().plusDays(10));
    viewingOfferRepository.save(vo6);

    var vo7 = new ViewingOffer();
    vo7.setTitle("Offering a couch 5");
    vo7.setDescription("Access to a bath is included.");
    vo7.setOfferingUser(l1);
    vo7.setCity("Muenster");
    vo7.setStreet("Leonardo-Campus 3");
    vo7.setPostalCode("48147");
    vo7.setSqMeters(8);
    vo7.setOnlineSince(LocalDateTime.now());
    vo7.setViewingDate(LocalDateTime.now().plusDays(10));
    viewingOfferRepository.save(vo7);

    var vo8 = new ViewingOffer();
    vo8.setTitle("Offering a couch 6");
    vo8.setDescription("Access to a bath is included.");
    vo8.setOfferingUser(l1);
    vo8.setCity("Muenster");
    vo8.setStreet("Leonardo-Campus 3");
    vo8.setPostalCode("48147");
    vo8.setSqMeters(8);
    vo8.setOnlineSince(LocalDateTime.now());
    vo8.setViewingDate(LocalDateTime.now().plusDays(10));
    viewingOfferRepository.save(vo8);

    var vo9 = new ViewingOffer();
    vo9.setTitle("Offering a couch 7");
    vo9.setDescription("'Access to a bath is included.");
    vo9.setOfferingUser(l2);
    vo9.setCity("Muenster");
    vo9.setStreet("Leonardo-Campus 3");
    vo9.setPostalCode("48147");
    vo9.setSqMeters(8);
    vo9.setOnlineSince(LocalDateTime.now());
    vo9.setViewingDate(LocalDateTime.now().plusDays(10));
    viewingOfferRepository.save(vo9);

    // ViewingRequests
    var vr1 = new ViewingRequest();
    vr1.setUserDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    vr1.setWasDeclined(true);
    vr1.setRequestedOffer(vo1);
    vr1.setRequestingUser(t1);
    viewingRequestRepository.save(vr1);

    var vr2 = new ViewingRequest();
    vr2.setUserDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    vr2.setWasDeclined(true);
    vr2.setRequestedOffer(vo2);
    vr2.setRequestingUser(t1);
    viewingRequestRepository.save(vr2);

    var vr3 = new ViewingRequest();
    vr3.setUserDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    vr3.setWasDeclined(true);
    vr3.setRequestedOffer(vo3);
    vr3.setRequestingUser(t1);
    viewingRequestRepository.save(vr3);

    var vr4 = new ViewingRequest();
    vr4.setUserDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    vr4.setWasDeclined(false);
    vr4.setRequestedOffer(vo4);
    vr4.setRequestingUser(t1);
    viewingRequestRepository.save(vr4);

    var vr5 = new ViewingRequest();
    vr5.setUserDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    vr5.setWasDeclined(false);
    vr5.setRequestedOffer(vo5);
    vr5.setRequestingUser(t1);
    viewingRequestRepository.save(vr5);

    var vr6 = new ViewingRequest();
    vr6.setUserDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    vr6.setWasDeclined(false);
    vr6.setRequestedOffer(vo6);
    vr6.setRequestingUser(t1);
    viewingRequestRepository.save(vr6);

    var vr7 = new ViewingRequest();
    vr7.setUserDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    vr7.setWasDeclined(false);
    vr7.setRequestedOffer(vo7);
    vr7.setRequestingUser(t1);
    viewingRequestRepository.save(vr7);

    var vr8 = new ViewingRequest();
    vr8.setUserDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    vr8.setWasDeclined(false);
    vr8.setRequestedOffer(vo5);
    vr8.setRequestingUser(t2);
    viewingRequestRepository.save(vr8);

    var vr9 = new ViewingRequest();
    vr9.setUserDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    vr9.setWasDeclined(true);
    vr9.setRequestedOffer(vo2);
    vr9.setRequestingUser(t2);
    viewingRequestRepository.save(vr9);

    var vr10 = new ViewingRequest();
    vr10.setUserDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    vr10.setWasDeclined(false);
    vr10.setRequestedOffer(vo8);
    vr10.setRequestingUser(t2);
    viewingRequestRepository.save(vr10);

    // Appointments
    var a1 = new Appointment();
    a1.setRequest(vr5);
    a1.setOfferingUserRemarks("Bring drinks");
    appointmentRepository.save(a1);

    var a2 = new Appointment();
    a2.setRequest(vr6);
    a2.setOfferingUserRemarks("OK");
    appointmentRepository.save(a2);

    var a3 = new Appointment();
    a3.setRequest(vr7);
    a3.setOfferingUserRemarks("");
    appointmentRepository.save(a3);
  }
}
