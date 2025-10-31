package project.common.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class ObjectUtils {
  private ObjectUtils() {
  }

  // Verificação rasa (atual)
  public static boolean allPropertiesEmpty(Object target) {
    if (target == null) {
      return true;
    }

    Class<?> current = target.getClass();

    while (current != null && current != Object.class) {
      Field[] fields = current.getDeclaredFields();
      for (Field field : fields) {
        if (Modifier.isStatic(field.getModifiers())) {
          continue;
        }

        field.setAccessible(true);
        Object value;
        try {
          value = field.get(target);
        } catch (IllegalAccessException e) {
          return false;
        }

        if (isValuePresentShallow(value)) {
          return false;
        }
      }

      current = current.getSuperclass();
    }

    return true;
  }

  // NOVO: verificação profunda (recursiva)
  public static boolean allPropertiesEmptyDeep(Object target) {
    return allPropertiesEmptyDeep(target, 3); // profundidade padrão
  }

  public static boolean allPropertiesEmptyDeep(Object target, int maxDepth) {
    if (maxDepth < 0) {
      maxDepth = 0;
    }
    return !isValuePresentDeep(target, maxDepth, java.util.Collections.newSetFromMap(new IdentityHashMap<>()));
  }

  // --------- Helpers ---------

  private static boolean isValuePresentShallow(Object value) {
    if (value == null) {
      return false;
    }

    if (value instanceof Optional<?> opt) {
      return opt.isPresent();
    }

    if (value instanceof CharSequence cs) {
      return cs.toString().trim().length() > 0;
    }

    if (value.getClass().isArray()) {
      return Array.getLength(value) > 0;
    }

    if (value instanceof Collection<?> col) {
      return !col.isEmpty();
    }

    if (value instanceof Map<?, ?> map) {
      return !map.isEmpty();
    }

    return true;
  }

  private static boolean isValuePresentDeep(Object value, int depth, Set<Object> visited) {
    if (value == null) {
      return false;
    }

    // Evita ciclos
    if (!isSimple(value.getClass())) {
      if (visited.contains(value)) {
        return false;
      }
      visited.add(value);
    }

    if (value instanceof Optional<?> opt) {
      return opt.isPresent() && isValuePresentDeep(opt.get(), depth - 1, visited);
    }

    if (value instanceof CharSequence cs) {
      return cs.toString().trim().length() > 0;
    }

    if (value.getClass().isArray()) {
      int len = Array.getLength(value);
      for (int i = 0; i < len; i++) {
        Object el = Array.get(value, i);
        if (isValuePresentDeep(el, depth - 1, visited)) {
          return true;
        }
      }
      return false;
    }

    if (value instanceof Collection<?> col) {
      if (col.isEmpty())
        return false;
      for (Object el : col) {
        if (isValuePresentDeep(el, depth - 1, visited)) {
          return true;
        }
      }
      return false;
    }

    if (value instanceof Map<?, ?> map) {
      if (map.isEmpty())
        return false;
      for (Map.Entry<?, ?> e : map.entrySet()) {
        if (isValuePresentDeep(e.getKey(), depth - 1, visited)
            || isValuePresentDeep(e.getValue(), depth - 1, visited)) {
          return true;
        }
      }
      return false;
    }

    // Tipos simples: qualquer não-nulo é considerado “presente”
    if (isSimple(value.getClass())) {
      return true;
    }

    // Se esgotou a profundidade, considerar qualquer objeto como presente
    if (depth <= 0) {
      return true;
    }

    // Bean: inspeciona campos recursivamente
    Class<?> current = value.getClass();
    while (current != null && current != Object.class) {
      Field[] fields = current.getDeclaredFields();
      for (Field field : fields) {
        if (Modifier.isStatic(field.getModifiers())) {
          continue;
        }
        field.setAccessible(true);
        Object fieldValue;
        try {
          fieldValue = field.get(value);
        } catch (IllegalAccessException e) {
          return true; // conservador: se não deu pra ler, considera presente
        }

        if (isValuePresentDeep(fieldValue, depth - 1, visited)) {
          return true;
        }
      }
      current = current.getSuperclass();
    }

    return false;
  }

  private static boolean isSimple(Class<?> cls) {
    return cls.isPrimitive()
        || Number.class.isAssignableFrom(cls)
        || Boolean.class.isAssignableFrom(cls)
        || Character.class.isAssignableFrom(cls)
        || Enum.class.isAssignableFrom(cls)
        || java.util.Date.class.isAssignableFrom(cls)
        || Temporal.class.isAssignableFrom(cls)
        || cls.getName().startsWith("java.time.");
  }
}