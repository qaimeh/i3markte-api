package ie.nuig.i3market.common;

import ie.nuig.i3market.common.annotations.RDF;
import ie.nuig.i3market.common.annotations.RDFSubject;
import ie.nuig.i3market.domain.DataProviderTemplate;
import ie.nuig.i3market.exceptions.BindingException;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.graph.BlankNodeId;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.XSD;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class RDFBinding {

    Model model;

    public RDFBinding(Model model){
        this.model=model;
    }

    public Resource marshal(Object object) throws BindingException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class<?> clazz= object.getClass();
        Field [] declaredFields= clazz.getDeclaredFields();

        Resource subject = createSubject(object,declaredFields);
        if(model.contains(subject,null,(RDFNode) null))
            return subject;

        if(object instanceof DataProviderTemplate){
            marshalIntoRDF(object, subject, declaredFields);
        }

        //model.commit();
        return subject;
    }

    private void marshalIntoRDF(Object object, Resource subject, Field[] declaredFields) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        addStatements(object,subject,declaredFields,null);
    }


    private Resource createSubject(Object object, Field[] declaredFields) throws BindingException {

        Field annotatedField= getAnnotatedField(declaredFields, RDFSubject.class);
        if (annotatedField == null)
            throw new NullPointerException();

        Object objectValue= CommonUtil.getFieldValue (object, annotatedField);

        if (objectValue == null )
            throw  new BindingException("subject field (" + annotatedField.getName() +") is not assigned a value");

        RDFSubject rdfSubject = annotatedField.getAnnotation(RDFSubject.class);

        Resource rs = model.createResource(rdfSubject.prefix()+objectValue.toString());

        return rs;
    }

    private  Field getAnnotatedField(Field [] declaredFields, Class<? extends Annotation> annotationClass){
        for (Field field: declaredFields){
            if (field.isAnnotationPresent(annotationClass)){
                return field;
            }
        }
        return null;
    }

    private void addStatements(Object o, Resource subject, Field[] fields, Class<? extends Annotation> annotationClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {

        for (Field field : fields) {
            if (field.isAnnotationPresent(RDF.class)) {
                Property predicate = createProperty(field);
                RDFNode object = extractNodeFromField(o, field);
                if(subject!=null && predicate!=null && object!=null)
                    model.add(subject, predicate, object);
            }
            /*else if ((!field.isAnnotationPresent(annotationClass) && CommonUtil.isCollection(field))) {
                addPropertyStatements(o, subject, field);
            }*/
        }
    }

    private RDFNode marshalObject(Object value) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (value == null) {
            return null;
        }
        Resource dtUri = getDatatypeURI(value);
        //RDFDatatype datatype = org.apache.jena.vocabulary.RDF;
        if (dtUri != null) {
            // Literal
            String s = value.toString();
            if (value instanceof Date) {
                s = DateUtils.getISO8601DateFormat().format(((Date) value).toInstant());
            }

            return model.createTypedLiteral(s, dtUri.toString());
        }
        if (Set.class.isAssignableFrom(value.getClass()) || List.class.isAssignableFrom(value.getClass())) {
            // RDF Container
            String type = Set.class.isAssignableFrom(value.getClass()) ? Vocabulary.RDFContainer.Bag : Vocabulary.RDFContainer.Seq;
            BlankNodeId collection = BlankNodeId.create();
            RDFNode collectionRDFNode= model.createResource(collection.toString());

            model.add(collectionRDFNode.asResource(), org.apache.jena.vocabulary.RDF.type,type);
            int i = 0;
            for (Object o : (Collection) value) {
                model.add(collectionRDFNode.asResource(), org.apache.jena.vocabulary.RDF.li(i),marshalObject(o));
                i++;
            }
            return  collectionRDFNode;
        }

        return null;
    }

    private RDFNode extractNodeFromField(Object object, Field field) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object value = CommonUtil.getFieldValue(object, field);
        return marshalObject(value);
    }

    private Property createProperty(Field field) {
        return model.createProperty(field.getAnnotation(RDF.class).value());
    }


    private static Resource getDatatypeURI(Object o) {
        Class <?> cls = o.getClass();
        if (String.class.isAssignableFrom(cls)) {
            return XSD.xstring;
        } else if (Boolean.class.isAssignableFrom(cls)) {
            return XSD.xboolean;
        } else if (Byte.class.isAssignableFrom(cls)) {
            return XSD.xbyte;
        } else if (Long.class.isAssignableFrom(cls)) {
            return XSD.xlong;
        } else if (Short.class.isAssignableFrom(cls)) {
            return XSD.xshort;
        } else if (Integer.class.isAssignableFrom(cls)) {
            return XSD.xint;
        } else if (Float.class.isAssignableFrom(cls)) {
            return XSD.xfloat;
        } else if (Double.class.isAssignableFrom(cls)) {
            return XSD.xdouble;
        } else if (Date.class.isAssignableFrom(cls)) {
            return XSD.dateTime;
        } else if (java.net.URI.class.isAssignableFrom(cls)) {
            return XSD.anyURI;
        }
        return null;
    }

    private static Object getDatatypeValue(Literal l) {
        Literal dt = l;
        String s = dt.getValue().toString();
        if (dt.equals(XSD.xstring)) {
            return s;
        } else if (dt.equals(XSD.xboolean)) {
            return Boolean.valueOf(s);
        } else if (dt.equals(XSD.xint)) {
            return Integer.valueOf(s);
        } else if (dt.equals(XSD.xbyte)) {
            return Byte.valueOf(s);
        } else if (dt.equals(XSD.xlong)) {
            return Long.valueOf(s);
        } else if (dt.equals(XSD.xlong)) {
            return Short.valueOf(s);
        } else if (dt.equals(XSD.xfloat)) {
            return Float.valueOf(s);
        } else if (dt.equals(XSD.xdouble)) {
            return Double.valueOf(s);
        } else if (dt.equals(XSD.dateTime)) {
            return DateUtils.getISO8601DateFormat().parse(s);
        } else if (dt.equals(XSD.anyURI)) {
            return java.net.URI.create(s);
        }
        return s;
    }

}
