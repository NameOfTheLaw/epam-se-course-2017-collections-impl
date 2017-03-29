package ru.epam.training;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CustomHashMapTest {

    @Test
    public void testThatMapCanContainsKeysWithSameHashCode() {
        Map<ClassWithConstHashCode, String> m = new CustomHashMap<>();

        ClassWithConstHashCode key1 = new ClassWithConstHashCode(5);
        String value1 = "aaa";
        m.put(key1, value1);

        ClassWithConstHashCode key2 = new ClassWithConstHashCode(10);
        String value2 = "bbb";
        m.put(key2, value2);

        assertThat(m.get(key1), is(value1));
        assertThat(m.get(key2), is(value2));
    }

    private class ClassWithConstHashCode {

        private final Integer field;

        public ClassWithConstHashCode(Integer field) {
            this.field = field;
        }

        @Override
        public int hashCode() {
            return 5;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ClassWithConstHashCode that = (ClassWithConstHashCode) o;

            return field != null ? field.equals(that.field) : that.field == null;
        }
    }
}
