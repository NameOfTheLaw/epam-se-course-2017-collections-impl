package ru.epam.training;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CustomMapsTest {

    private Map<Integer, String> m;

    public CustomMapsTest(Map<Integer, String> m) {
        this.m = m;
    }

    @Parameterized.Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[]{
                new CustomTreeMap(),
                new CustomHashMap()
        });
    }

    @Before
    public void init() {
        m.clear();
    }

    @Test
    public void testThatWeCanCreate() {
        assertThat(m, is(notNullValue()));
    }

    @Test
    public void testThatNewMapIsEmpty() {
        assertThat(m.isEmpty(), is(true));
    }

    @Test
    public void testThatOnNewMapContainKeyMethodReturnFalseForAnyObject() {
        assertThat(m.containsKey(1), is(false));
    }

    @Test
    public void testThatWeCanPutKeyValuePairAndCanCheckIt() {
        m.put(3, "abc");
        assertThat(m.containsKey(3), is(true));
    }

    @Test(expected = NullPointerException.class)
    public void testThatWeCantPutNullKey() {
        m.put(null, "abc");
    }

    @Test
    public void testThatWeCanPutNullValue() {
        m.put(1, null);
        assertThat(m.containsKey(1), is(true));
    }

    @Test
    public void testThatMapCanPutPairWithKeyThatAlreadyPresented() {

        String oldValue = "aaaa";
        String newValue = "bbbb";

        m.put(1, oldValue);
        m.put(1, newValue);

        assertFalse(m.containsValue(oldValue));
        assertTrue(m.containsValue(newValue));
    }

    @Test
    public void testThatIfWePutNewValueOnExistingKeyPreviousValueWillBeReturned() {
        String oldValue = "aaaa";
        String newValue = "bbbb";

        m.put(1, oldValue);
        String returnedValue = m.put(1, newValue);

        assertThat(oldValue, is(equalTo(returnedValue)));
    }

    @Test
    public void testThatWeCanPut10DifferentKeysInMap() {
        IntStream.range(1, 10).forEach(
                i -> m.put(i, String.valueOf(i))
        );

        IntStream.range(1, 10).forEach(
                i -> assertTrue(m.containsKey(i))
        );
    }

    @Test(expected = NullPointerException.class)
    public void testThatContainsKeyMethodThrowsExceptionOnNullKey() {
        m.containsKey(null);
    }

    @Test
    public void testContainsValueMethodWorksProperly() {
        String value = "aaaa";

        m.put(1, value);

        assertTrue(m.containsValue(value));
    }

    @Test
    public void testContainsValueMethodWorksProperlyOnNullInputValue() {
        String value = "aaaa";

        m.put(1, value);

        assertFalse(m.containsValue(null));
    }

    @Test
    public void testThatMapCalculateItsSizeProperly() {
    }
}
