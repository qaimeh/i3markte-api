package ie.nuig.i3market.domain;

import ie.nuig.i3market.common.Vocabulary;
import ie.nuig.i3market.common.annotations.RDF;
import ie.nuig.i3market.common.annotations.RDFSubject;

import javax.validation.constraints.NotNull;

public class DataProviderTemplate {

    @RDFSubject (prefix = Vocabulary.DCAT.RESOURCE_URI)
    @NotNull
    private String providerId;
    @RDF(Vocabulary.DCAT.providerName)
    @NotNull
    private String name;

    @RDF(Vocabulary.DCAT.description)
    private String description;
    @RDF(Vocabulary.DCAT.providerOrganization)
    private String providerOrganization;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProviderOrganization() {
        return providerOrganization;
    }

    public void setProviderOrganization(String providerOrganization) {
        this.providerOrganization = providerOrganization;
    }

    @Override
    public String toString() {
        return "DataProviderTemplate{" +
            "providerId='" + providerId + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", providerOrganization='" + providerOrganization + '\'' +
            '}';
    }
}
