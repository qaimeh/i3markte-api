package ie.nuig.i3market.web.rest;

import ie.nuig.i3market.domain.DataProviderTemplate;
import ie.nuig.i3market.service.OfferingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/registration")
public class  RegistrationOffering {

    private OfferingService offeringService;

    public RegistrationOffering(OfferingService offeringService) {
        this.offeringService = offeringService;
    }


    @PostMapping
    public ResponseEntity<Void> saveTemplete(@RequestBody @Valid DataProviderTemplate dataProviderTemplate){

        offeringService.save(dataProviderTemplate);

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
