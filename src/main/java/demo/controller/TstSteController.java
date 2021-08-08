package demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.model.TstSte;
import demo.service.TstSteService;


@RestController
@RequestMapping(value = "tstSte")
public class TstSteController {
  @Autowired TstSteService tstSteService;

  @PostMapping
  public ResponseEntity<?> persist(@RequestBody TstSte tstSte){
//    System.out.println(String.format("(persist) starting [tstSte => %s]", tstSte));
    tstSteService.persist(tstSte);
//    System.out.println(tstSte.toString());
    return new ResponseEntity<>(tstSte, HttpStatus.OK);
  }
  
  @GetMapping
  public ResponseEntity<?> tstStes(){
    return new ResponseEntity<>(tstSteService.tstStes(new TstSte()), HttpStatus.OK);
  }
  
  /**
   * do not use a body in a get request as it seems to be a somewhat debatable
   * subject see https://datatracker.ietf.org/doc/html/rfc7231#section-4.3.1
   * 
   * A payload within a GET request message has no defined semantics; sending
   * a payload body on a GET request might cause some existing implementations
   * to reject the request.
   * 
   * + bunch of messages on http://stackoverflow.com
   * 
   * with queries it can be useful to use an Optional Object if the parameter
   * is not required
   *  
   * @param tstSte
   * @return
   */
  @GetMapping("tstSte")
  public ResponseEntity<?> tstSte(
      @RequestParam(value = "tsId") Optional<Integer> tsId,
      @RequestParam(value = "name") Optional<String> name){
    System.out.println(String.format("(tstSte) starting [tsId => %s]", tsId));
    if (tsId.isPresent()) { // only one return should be used when PK or Natural key is used
      TstSte tstSte = new TstSte.Builder()
          .tsId(tsId.orElse(null))
          .name(name.orElse(null)) // it is possible although unlikely more than one value supplied with the key
          .build(); 
      return new ResponseEntity<>(tstSteService.tstSteSingleton(tstSte), HttpStatus.OK);
    } else {
      TstSte tstSte = new TstSte.Builder()
          .name(name.orElse(null))
          .build(); 
      return new ResponseEntity<>(tstSteService.tstStes(tstSte), HttpStatus.OK);      
    }
  }
  
  @GetMapping("find")
  public ResponseEntity<?> find(@RequestBody TstSte tstSte){
    return new ResponseEntity<>(tstSteService.find(tstSte), HttpStatus.OK);
  }
}
