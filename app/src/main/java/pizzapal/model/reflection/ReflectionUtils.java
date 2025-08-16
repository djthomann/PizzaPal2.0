package pizzapal.model.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import pizzapal.model.domain.entities.Editable;

public class ReflectionUtils {

    public static List<Field> getEditableFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();

        while (type != null && type != Object.class) {
            for (Field f : type.getDeclaredFields()) {

                if (f.isAnnotationPresent(Editable.class)) {
                    f.setAccessible(true);
                    fields.add(f);
                }
            }
            type = type.getSuperclass();
        }
        return fields;
    }

    public static Field getFieldFromSuperClass(Class<?> type, String fieldName) {
        Field field = null;

        while (type != null && type != Object.class) {
            for (Field f : type.getDeclaredFields()) {
                if (f.getName().equals(fieldName)) {
                    field = f;
                }
            }
            type = type.getSuperclass();
        }
        return field;
    }
}