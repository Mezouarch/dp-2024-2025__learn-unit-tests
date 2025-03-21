package fr.anthonyquere.fizzbuzz;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class FizzBuzzTest {

    @Test
    void should_return_empty_list_when_count_is_0() {
        var count = 0;

        var result = FizzBuzz.startFizzBuzz(count);

        assertThat(result).isEmpty();
    }

    @Test
    void should_return_n_elements_when_count_is_n() {
        var count = 10;

        var result = FizzBuzz.startFizzBuzz(count);

        assertThat(result).hasSize(count);
    }

    @Test
    void should_contain_fizz_for_elements_with_index_multiple_of_3() {
        var count = 20;

        var result = FizzBuzz.startFizzBuzz(count);

        assertThat(result).isNotNull();
        assertThat(result.get(2)).contains("Fizz");
        assertThat(result.get(5)).contains("Fizz");
        assertThat(result.get(8)).contains("Fizz");
        assertThat(result.get(11)).contains("Fizz");
        assertThat(result.get(14)).contains("Fizz");
        assertThat(result.get(17)).contains("Fizz");
    }

    @Test
    void should_not_contain_fizz_for_elements_with_index_not_multiple_of_3() {
        var count = 10;

        var result = FizzBuzz.startFizzBuzz(count);

        assertThat(result).isNotNull();
        assertThat(result.get(0)).doesNotContain("Fizz");
        assertThat(result.get(1)).doesNotContain("Fizz");
        assertThat(result.get(3)).doesNotContain("Fizz");
        assertThat(result.get(4)).doesNotContain("Fizz");
        assertThat(result.get(6)).doesNotContain("Fizz");
        assertThat(result.get(7)).doesNotContain("Fizz");
        assertThat(result.get(9)).doesNotContain("Fizz");
    }

    @Test
    void should_contain_buzz_for_elements_with_index_multiple_of_5() {
        var count = 20;

        var result = FizzBuzz.startFizzBuzz(count);

        assertThat(result).isNotNull();
        assertThat(result.get(4)).contains("Buzz");
        assertThat(result.get(9)).contains("Buzz");
        assertThat(result.get(14)).contains("Buzz");
        assertThat(result.get(19)).contains("Buzz");
    }

    @Test
    void should_not_contain_buzz_for_elements_with_index_not_multiple_of_5() {
        var count = 10;

        var result = FizzBuzz.startFizzBuzz(count);

        assertThat(result).isNotNull();
        assertThat(result.get(0)).doesNotContain("Buzz");
        assertThat(result.get(1)).doesNotContain("Buzz");
        assertThat(result.get(2)).doesNotContain("Buzz");
        assertThat(result.get(3)).doesNotContain("Buzz");
        assertThat(result.get(5)).doesNotContain("Buzz");
        assertThat(result.get(6)).doesNotContain("Buzz");
        assertThat(result.get(7)).doesNotContain("Buzz");
        assertThat(result.get(8)).doesNotContain("Buzz");
    }

    @Test
    void should_contain_fizzbuzz_for_elements_with_index_multiple_of_3_and_5() {
        var count = 20;

        var result = FizzBuzz.startFizzBuzz(count);

        assertThat(result).isNotNull();
        assertThat(result.get(14)).isEqualTo("FizzBuzz");
    }
}
