package ie.nuig.i3market.common;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class CommonUtil {

    public static Object getFieldValue(Object object, Field annotatedField){

        Class<?> clazz = object.getClass();
        String fieldName = annotatedField.getName();
        PropertyDescriptor pd;

        try{
            pd= new PropertyDescriptor(fieldName,clazz);
            Method method= pd.getReadMethod();

            return method.invoke(object);

        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isCollection(Field field) {
        return List.class.isAssignableFrom(field.getType()) || Set.class.isAssignableFrom(field.getType());
    }
}
