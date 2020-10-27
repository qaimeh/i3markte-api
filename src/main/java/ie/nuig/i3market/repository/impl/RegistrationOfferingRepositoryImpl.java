package ie.nuig.i3market.repository.impl;

import ie.nuig.i3market.common.RDFBinding;
import ie.nuig.i3market.config.databases.VirtuosoConfiguration;
import ie.nuig.i3market.domain.DataProviderTemplate;
import ie.nuig.i3market.repository.RegistrationOfferingRepository;
import ie.nuig.i3market.web.rest.errors.BadRequestAlertException;
import org.apache.http.protocol.HttpContext;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationOfferingRepositoryImpl implements RegistrationOfferingRepository {

    private VirtuosoConfiguration virtuosoConfiguration;


    public RegistrationOfferingRepositoryImpl(VirtuosoConfiguration virtuosoConfiguration) {
        this.virtuosoConfiguration = virtuosoConfiguration;
    }

    @Override
    public void saveTemplate(DataProviderTemplate dataProviderTemplate) {

        HttpContext context = virtuosoConfiguration.getVirtuosoContext();
        Model model = ModelFactory.createDefaultModel();
        RDFBinding rdfBinding= new RDFBinding(model);
        try {
            rdfBinding.marshal(dataProviderTemplate);

            StmtIterator stmtIter = model.listStatements();

            for (StmtIterator it = stmtIter; it.hasNext(); ) {
                Statement statement = it.next();

                System.out.println(statement);
            }

        }catch (Exception e){
            throw new BadRequestAlertException(e.getMessage(),"Registration Offering", "invalid-input");
        }

    }
}
