package ru.epam.training;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CustomListsTest {

    private List<String> list;

    public CustomListsTest(List<String> list, String className) {
        this.list = list;
    }

    @Parameterized.Parameters(name = "{1}")
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][] {
                {new CustomArrayList(), "ArrayList"},
                {new CustomLinkedList(), "LinkedList"}
        });
    }

    @Before
    public void init() {
        list.clear();
    }

    @Test
    public void testThatNewListIsEmpty() {
        assertTrue(list.isEmpty());
    }

    @Test
    public void testThatListNotEmptyAfterAddingElement() {
        list.add("aaaa");

        assertFalse(list.isEmpty());
    }

    @Test
    public void testThatListContainsElementThatWeAddedBefore() {
        String value = "bbb";

        list.add(value);

        assertTrue(list.contains(value));
    }

    @Test
    public void testThatListNotContainsElementThatWasntAddedToList() throws Exception {
        list.add("fff");
        assertFalse(list.contains("ccc"));
    }

    @Test
    public void testThatListContainsNullIfItWasAdded() {
        list.add(null);

        assertTrue(list.contains(null));
    }

    @Test
    public void testThatListNotContainsNullIfItWasNotAdded() {
        list.add("fff");
        assertFalse(list.contains(null));
    }

    @Test
    public void testThatListsSizeIsDynamic() throws Exception {
        int size = 50;

        fillList(size);

        assertThat(list.size(), is(size));
    }

    @Test
    public void testThatWeCanGetElementByIndex() {
        fillList(10);

        assertThat(list.get(1), is("1"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThatWeCantGetElementByIndexMoreThenSize() throws Exception {
        fillList(10);

        list.get(list.size());
    }

    @Test
    public void testThatWeCanRemoveElementByIndex() throws Exception {
        fillList(10);

        String removed = list.remove(2);

        assertFalse(list.contains("2"));
        assertThat(removed, is(equalTo("2")));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThatWeCantRemoveElementByIndexEqualsSize() throws Exception {
        int size = 5;
        fillList(size);
        list.remove(size);
    }

    @Test
    public void testThatWeCanRemoveExistedElementFromList() throws Exception {
        fillList(10);

        list.remove("4");

        assertFalse(list.contains("4"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThatWeCantRemoveElementByIndexBelowZero() throws Exception {
        list.remove(-5);
    }

    @Test
    public void testThatWeCanRemoveLastElement() throws Exception {
        fillList(10);

        int prevSize = list.size();

        list.remove(list.size() - 1);

        assertFalse(list.contains("9"));
        assertThat(list.size(), is(equalTo(prevSize - 1)));
    }

    @Test
    public void testThatWeCantRemoveNonExistentElement() throws Exception {
        fillList(10);

        assertFalse(list.remove("ccc"));
    }

    @Test
    public void testThatRemoveByValueMethodWorksProperlyOnNullInputValue() {
        assertFalse(list.remove(null));
    }

    @Test
    public void testIsEmptyWorksProperlyOnRemoving() {
        list.add("aa");
        list.remove("aa");

        assertTrue(list.isEmpty());
    }

    @Test
    public void testIndexOfWorksProperly() {
        fillList(10);

        IntStream.range(0, 10)
                .forEach((i) -> assertThat(list.indexOf(String.valueOf(i)), is(i)));
    }

    @Test
    public void testIndexOfWorksProperlyIfThereIsSeveralEqualElement() {
        list.add("0");
        list.add("1");
        list.add("aaa");
        list.add("3");
        list.add("aaa");
        list.add("5");

        assertThat(list.indexOf("aaa"), is(2));
    }

    @Test
    public void testThatIndexOfWillReturnSpecialValueIfWhereIsNoInputObjectInList() {
        assertThat(list.indexOf("aaa"), is(-1));
    }

    @Test
    public void testLastIndexOfWorksProperly() {
        fillList(10);

        IntStream.range(0, 10)
                .forEach((i) -> assertThat(list.lastIndexOf(String.valueOf(i)), is(i)));
    }

    @Test
    public void testLastIndexOfWorksProperlyIfThereIsSeveralEqualElement() {
        list.add("0");
        list.add("1");
        list.add("aaa");
        list.add("3");
        list.add("aaa");
        list.add("5");

        assertThat(list.lastIndexOf("aaa"), is(4));
    }

    @Test
    public void testThatLastIndexOfWillReturnSpecialValueIfWhereIsNoInputObjectInList() {
        assertThat(list.lastIndexOf("aaa"), is(-1));
    }

    @Test
    public void testThatAddByIndexWorksProperly() {
        list.add("1");
        list.add("3");
        list.add("4");

        list.add(1, "2");

        assertThat(list.get(0), is("1"));
        assertThat(list.get(1), is("2"));
        assertThat(list.get(2), is("3"));
        assertThat(list.get(3), is("4"));
    }

    @Test
    public void testThatAddByIndexWorksProperlyOnEmptyList() {
        list.add(0, "2");

        assertThat(list.get(0), is("2"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThatAddByIndexThrowsExceptionIfIndexIsNotAppropriate() {
        list.add(-1, "2");
    }

    @Test
    public void testThatAddByIndexWorksProperlyWithNullInputValue() {
        list.add("1");

        list.add(1, null);

        assertNull(list.get(1));
    }

    @Test
    public void testThatWeCanSetValueByIndex() {
        list.add("0");
        list.add("2");
        list.add("2");

        list.set(1, "1");

        assertThat(list.get(0), is("0"));
        assertThat(list.get(1), is("1"));
        assertThat(list.get(2), is("2"));
    }

    @Test
    public void testThatListSizeDoesntChangeIfWeSetValue() {
        int size = 3;

        list.add("0");
        list.add("2");
        list.add("2");

        assertThat(list.size(), is(size));
        list.set(1, "1");
        assertThat(list.size(), is(size));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThatWeCantSetValueForNonExistingElement() {
        list.add("0");
        list.add("1");
        list.add("2");

        list.set(list.size(), "3");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThatSetMethodThrowsExceptionIfIndexIsNotAppropriate() {
        list.set(-1, "1");
    }

    @Test
    public void testThatWeCanSetNullValue() {
        list.add("0");
        list.add("2");
        list.add("2");

        list.set(1, null);

        assertNull(list.get(1));
    }

    @Test
    public void testThatSetMethodReturnsPreviousValue() {
        list.add("0");

        assertThat(list.set(0, "1"), is("0"));
    }

    private void fillList(int size) {
        IntStream.range(0, size)
                .mapToObj(String::valueOf)
                .forEach(list::add);
    }
}
