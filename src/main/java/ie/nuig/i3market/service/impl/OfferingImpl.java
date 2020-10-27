package ie.nuig.i3market.service.impl;

import ie.nuig.i3market.domain.DataProviderTemplate;
import ie.nuig.i3market.repository.RegistrationOfferingRepository;

import ie.nuig.i3market.service.OfferingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OfferingImpl implements OfferingService{

    private RegistrationOfferingRepository registrationOfferingRepository;

    public OfferingImpl(RegistrationOfferingRepository registrationOfferingRepository) {
        this.registrationOfferingRepository = registrationOfferingRepository;
    }

    @Override
    public void save(DataProviderTemplate dataProviderTemplate) {
        registrationOfferingRepository.saveTemplate(dataProviderTemplate);
    }
}
