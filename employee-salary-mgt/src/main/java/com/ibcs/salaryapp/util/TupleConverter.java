package com.ibcs.salaryapp.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import org.apache.commons.text.CaseUtils;

public class TupleConverter {

    public static <T> T tupleToObject(Tuple tuple, Class<T> clazz) {
        T object = (T) new Object();
        try {
            object = clazz.newInstance();
            List<TupleElement<?>> tuples = tuple.getElements();
            if (tuples != null && tuples.size() > 0) {
                for (TupleElement<?> element : tuples) {
                    try {
                        String fieldName = CaseUtils.toCamelCase(element.getAlias(), false, '_');
                        Object value = tuple.get(element);

                        try {
                            Field field = clazz.getDeclaredField(fieldName);
                            if (object != null && value != null) {
                                field.setAccessible(true);
                                field.set(object, value);
                            }
                        } catch (NoSuchFieldException e) {

                        }

                    } catch (Exception e) {
                        Logger.getLogger(TupleConverter.class.getName()).log(Level.SEVERE, e.getMessage());
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TupleConverter.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        return object;
    }

}
