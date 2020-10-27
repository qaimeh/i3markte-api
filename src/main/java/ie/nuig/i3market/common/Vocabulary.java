package ie.nuig.i3market.common;


import ie.nuig.i3market.common.annotations.RDF;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;

public class Vocabulary {

    private Vocabulary(){}

    public static  final  class DCAT {

        private DCAT(){}

        private static  final  String NAMESPACE= "http://dcat.org/";

        public static final  String RESOURCE_URI = NAMESPACE+"resource/" ;

        public static final String providerId= NAMESPACE +"providerId";
        public static final String providerName= NAMESPACE +"providerName";
        public static final String description= NAMESPACE +"description";
        public static final String providerOrganization= NAMESPACE +"providerOrganization";


    }

    public static final class RDFContainer{

        public static final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

        public static final String Alt = RDF_NS+"Alt";
        public static final String Bag = RDF_NS+"Bag";
        public static final String Seq = RDF_NS+"Seq";

    }

}
