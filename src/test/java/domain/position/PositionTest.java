package domain.position;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@DisplayName("Position은 ")
class PositionTest {

    @Test
    @DisplayName("캐싱된다.")
    void getCachedPositionTest() {
        // given
        Position position1 = Position.of(2, 2);
        Position position2 = Position.of(2, 2);

        // expect
        assertThat(position1).isSameAs(position2);
    }

    @Test
    @DisplayName("캐싱된 Position의 개수는 64개이다.")
    void checkNumberOfPositionTest() {
        // given
        List<Integer> rows = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> columns = List.of(1, 2, 3, 4, 5, 6, 7, 8);

        List<Position> expectedPositions = rows.stream()
                .flatMap(row -> columns.stream()
                        .map(column -> Position.of(row, column)))
                .collect(toList());

        List<List<Position>> setPositions = Position.getAllPositions();
        List<Position> flattenSetPositions = new ArrayList<>();
        setPositions.forEach(flattenSetPositions::addAll);

        // then
        assertThat(flattenSetPositions)
                .containsAll(expectedPositions)
                .hasSize(64);
    }

    @ParameterizedTest
    @MethodSource("getPathToTestCase")
    @DisplayName("목적지가 8방향 직선 상에 있을 경우 목적지 Position까지의 경로를 반환할 수 있다.")
    void getPathToTest_EightDirections(Position start, Position end, List<Position> expected) {
        // given
        Path path = new Path(start, end);

        // expect
        assertThat(path.getPositions()).containsAll(expected);
    }

    @Test
    @DisplayName("목적지가 8방향 직선 상에 없을 경우 목적지 Position만 경로에 넣어준다.")
    void getPathToTest_NonEightDirections() {
        // given
        Position start = Position.of(3, 3);
        Position end = Position.of(6, 8);

        // when
        Path path = new Path(start, end);

        // then
        assertThat(path.getPositions()).containsExactly(end);
    }


    @Test
    @DisplayName("black pawn의 초기 위치이면 true를 반환한다.")
    void isBlackPawnInitialRowTest_True() {
        // given
        Position start = Position.of(7, 5);

        // when
        boolean result = start.isBlackPawnInitialRow();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("black pawn의 초기 위치가 아니면 false를 반환한다.")
    void isBlackPawnInitialRowTest_False() {
        // given
        Position start = Position.of(3, 5);

        // when
        boolean result = start.isBlackPawnInitialRow();

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("white pawn의 초기 위치이면 true를 반환한다.")
    void isWhitePawnInitialRowTest_True() {
        // given
        Position start = Position.of(2, 5);

        // when
        boolean result = start.isWhitePawnInitialRow();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("white pawn의 초기 위치가 아니면 false를 반환한다.")
    void isWhitePawnInitialRowTest_False() {
        // given
        Position start = Position.of(3, 5);

        // when
        boolean result = start.isWhitePawnInitialRow();

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("다른 Position과의 행 좌표값 차이를 구할 수 있다.")
    void calculateRowGapTest() {
        // given
        Position start = Position.of(2, 2);
        Position end = Position.of(5, 7);

        // when
        int rowGap = start.calculateRowGap(end);

        // then
        assertThat(rowGap).isEqualTo(3);
    }

    @Test
    @DisplayName("다른 Position과의 열 좌표값 차이를 구할 수 있다.")
    void calculateColumnGapTest() {
        // given
        Position start = Position.of(2, 2);
        Position end = Position.of(5, 7);

        // when
        int columnGap = start.calculateColumnGap(end);

        // then
        assertThat(columnGap).isEqualTo(5);
    }

    static Stream<Arguments> getPathToTestCase() {
        return Stream.of(
                Arguments.arguments(Position.of(3, 3), Position.of(5, 3), List.of(Position.of(4, 3), Position.of(5, 3))),
                Arguments.arguments(Position.of(3, 3), Position.of(5, 5), List.of(Position.of(4, 4), Position.of(5, 5))),
                Arguments.arguments(Position.of(3, 3), Position.of(3, 5), List.of(Position.of(3, 4), Position.of(3, 5))),
                Arguments.arguments(Position.of(3, 3), Position.of(1, 5), List.of(Position.of(2, 4), Position.of(1, 5))),
                Arguments.arguments(Position.of(3, 3), Position.of(1, 3), List.of(Position.of(2, 3), Position.of(1, 3))),
                Arguments.arguments(Position.of(3, 3), Position.of(1, 1), List.of(Position.of(2, 2), Position.of(1, 1))),
                Arguments.arguments(Position.of(3, 3), Position.of(3, 1), List.of(Position.of(3, 2), Position.of(3, 1))),
                Arguments.arguments(Position.of(3, 3), Position.of(5, 1), List.of(Position.of(4, 2), Position.of(5, 1)))
        );
    }
}
