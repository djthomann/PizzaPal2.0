package pizzapal.utils;

import java.lang.reflect.Field;

import pizzapal.model.domain.entities.Entity;

public class EntityField {

    private Class<? extends Entity> clazz;
    private Field field;

    public EntityField(Class<? extends Entity> clazz, Field field) {
        this.clazz = clazz;
        this.field = field;
    }

    public Class<? extends Entity> getClazz() {
        return clazz;
    }

    public Field getField() {
        return field;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EntityField other = (EntityField) obj;
        if (clazz == null) {
            if (other.clazz != null)
                return false;
        } else if (!clazz.equals(other.clazz))
            return false;
        if (field == null) {
            if (other.field != null)
                return false;
        } else if (!field.equals(other.field))
            return false;
        return true;
    }

}
