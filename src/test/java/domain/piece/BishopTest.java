package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Path;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("Bishop은 ")
class BishopTest {

    @Test
    @DisplayName("검은색일 때 대문자 이름을 반환한다.")
    void getBlackNameTest() {
        // given
        Bishop bishop = new Bishop(Color.BLACK);

        // when
        String name = bishop.getName();

        // then
        assertThat(name).isEqualTo("B");
    }

    @Test
    @DisplayName("흰색일 때 소문자 이름을 반환한다.")
    void getWhiteNameTest() {
        // given
        Bishop bishop = new Bishop(Color.WHITE);

        // when
        String name = bishop.getName();

        // then
        assertThat(name).isEqualTo("b");
    }

    @ParameterizedTest
    @MethodSource("isMovablePathTest_SuccessCase")
    @DisplayName("대각선에 위치한 모든 칸으로 이동할 수 있다.")
    void isMovablePathTest_Success(Path path) {
        // given
        Bishop bishop = new Bishop(Color.WHITE);

        // when
        boolean result = bishop.isMovablePath(Position.of(3, 3), path);

        // then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @MethodSource("isMovablePathTest_FailCase")
    @DisplayName("상하좌우에 위치한 모든 칸으로 이동할 수 없다.")
    void isMovablePathTest_Fail(Path path) {
        // given
        Bishop bishop = new Bishop(Color.WHITE);

        // when
        boolean result = bishop.isMovablePath(Position.of(3, 3), path);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("기본점수가 3점이다.")
    void getScoreTest() {
        // given
        Bishop bishop = new Bishop(Color.WHITE);

        // when
        double score = bishop.getScore();

        // then
        assertThat(score).isEqualTo(3);
    }

    static Stream<Arguments> isMovablePathTest_SuccessCase() {
        return Stream.of(
                Arguments.of(new Path(Position.of(3, 3), Position.of(5,5))),
                Arguments.of(new Path(Position.of(3, 3), Position.of(1,5))),
                Arguments.of(new Path(Position.of(3, 3), Position.of(1,1))),
                Arguments.of(new Path(Position.of(3, 3), Position.of(5,1)))
        );
    }

    static Stream<Arguments> isMovablePathTest_FailCase() {
        return Stream.of(
                Arguments.of(new Path(Position.of(3, 3), Position.of(5,3))),
                Arguments.of(new Path(Position.of(3, 3), Position.of(3,5))),
                Arguments.of(new Path(Position.of(3, 3), Position.of(1,3))),
                Arguments.of(new Path(Position.of(3, 3), Position.of(3,1)))
        );
    }
}
