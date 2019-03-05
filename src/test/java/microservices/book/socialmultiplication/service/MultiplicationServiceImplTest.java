package microservices.book.socialmultiplication.service;

import microservices.book.socialmultiplication.domain.Multiplication;
import microservices.book.socialmultiplication.domain.MultiplicationResultAttempt;
import microservices.book.socialmultiplication.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class MultiplicationServiceImplTest {

    @Mock
    private RandomGeneratorService randomGeneratorService;

    private MultiplicationServiceImpl multiplicationServiceImpl;

    private MultiplicationResultAttempt multiplicationAttempt;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService);
    }

    @Test
    public void createRandomMultiplicationTest() {
        //given (the random generator will return first 50 then 30)
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        //when
        Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

        //then
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
    }

    @Test
    public void checkCorrectAttemptTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 30);
        User user = new User("Jhon");
        multiplicationAttempt = new MultiplicationResultAttempt(user, multiplication, 1500, false);

        //when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(multiplicationAttempt);

        //then
        assertThat(attemptResult).isTrue();
    }

    @Test
    public void checkWrongAttemptTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 30);
        User user = new User("Jhon");
        multiplicationAttempt = new MultiplicationResultAttempt(user, multiplication, 1505, false);

        //when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(multiplicationAttempt);

        //then
        assertThat(attemptResult).isFalse();
    }
}
