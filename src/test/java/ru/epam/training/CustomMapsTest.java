package ru.epam.training;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CustomMapsTest {

    private Map<Integer, String> m;
    private Map<Integer, String> secondMap;

    public CustomMapsTest(Map<Integer, String> m, Map<Integer, String> m2) {
        this.m = m;
        this.secondMap = m2;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{new CustomTreeMap(), new CustomTreeMap()},
                new Object[]{new CustomHashMap(), new CustomHashMap()});
    }

    @Before
    public void init() {
        m.clear();
        secondMap.clear();
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
        fillMapToSize(10);

        IntStream.range(0, 10).forEach(
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
    public void testContainsValueMethodWorksProperlyOn50Elements() {
        fillMapToSize(50);

        IntStream.range(0, 50).forEach(
                i -> assertTrue(m.containsValue(String.valueOf(i)))
        );
    }

    @Test
    public void testContainsValueMethodWorksProperlyOnNullValue() {
        m.put(5, null);

        assertTrue(m.containsValue(null));
    }

    @Test
    public void testRemoveMethodWorksProperly() {
        int key = 4;

        m.put(key, "aa");
        m.remove(key);

        assertFalse(m.containsKey(key));
    }

    @Test(expected = NullPointerException.class)
    public void testThatWeCantRemoveByNullkey() {
        m.remove(null);
    }

    @Test
    public void testThatIfWeRemoveExistingKeyPreviousValueWillBeReturned() {
        int key = 4;
        String value = "aa";

        m.put(key, value);

        assertThat(m.remove(key), is(value));
    }

    @Test
    public void testThatRemoveMethodWorksProperlyIfWeTryToRemoveNonPresentedKey() {
        m.remove(77);
    }

    @Test
    public void testThatIfWeTryToRemoveNonPresentedKeyNullWillBeReturned() {
        assertNull(m.remove(77));
    }

    @Test
    public void testClearMethodWorksProperly() {
        fillMapToSize(50);

        m.clear();

        assertThat(m.size(), is(0));
    }

    @Test
    public void testClearMethodWorksProperlyOnEmptyMap() {
        m.clear();

        assertThat(m.size(), is(0));
    }

    @Test
    public void testThatMapCalculateItsSizeProperly() {
        assertThat(m.size(), is(0));
        fillMapToSize(50);
        assertThat(m.size(), is(50));
    }

    @Test
    public void testThatMapCalculatesItsSizeProperlyOnRemoving() {
        fillMapToSize(20);

        assertThat(m.size(), is(20));

        IntStream.range(0, 20)
                .forEach((i) -> m.remove(i));

        assertThat(m.size(), is(0));
    }

    @Test
    public void testEmptyMethodWorksProperly() {
        m.put(2, "a");

        assertFalse(m.isEmpty());
    }

    @Test
    public void testEmptyMethodWorksProperlyAfterRemovingAllKeyValuePairs() {
        m.put(2, "a");
        m.remove(2);

        assertTrue(m.isEmpty());
    }

    @Test
    public void testThatGetMethodReturnsValueProperly() {
        int key = 10;
        String value = "aaa";

        m.put(key, value);

        assertThat(m.get(key), is(value));
    }

    @Test(expected = NullPointerException.class)
    public void testThatGetMethodThrowsNullPointerExceptionOnNullInputKey() {
        m.get(null);
    }

    @Test
    public void testThatGetMethodReturnsNullOnNonExistingKey() {
        assertNull(m.get(5));
    }

    @Test
    public void testThatGetMethodWorksProperlyOn50Values() {
        fillMapToSize(50);

        IntStream.range(0, 50)
                .forEach(i -> {
                    assertThat(m.get(i), is(String.valueOf(i)));
                });
    }

    private void fillMapToSize(Map map, int size) {
        IntStream.range(0, size)
                .forEach((i) -> map.put(i, String.valueOf(i)));
    }

    private void fillMapToSize(int size) {
        fillMapToSize(m, size);
    }

}
