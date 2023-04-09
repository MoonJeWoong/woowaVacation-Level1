package domain.position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public final class Position {

    private static final Map<Integer, Position> cache = new HashMap<>();
    private static final Map<Character, Integer> ColumnMapper = new HashMap<>();
    private static final Map<Character, Integer> RowMapper = new HashMap<>();
    public static final List<Integer> PADDING_ROWS = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    public static final List<Integer> PADDING_COLUMNS = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    public static final List<Integer> ORDERED_ROWS = List.of(8, 7, 6, 5, 4, 3, 2, 1);
    public static final List<Integer> ORDERED_COLUMNS = List.of(1, 2, 3, 4, 5, 6, 7, 8);
    public static final List<Character> ORDERED_COLUMNS_ALPHABETS = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');
    public static final int DECIMAL_SYSTEM_RADIX = 10;
    public static final int COLUMN_INDEX = 0;
    public static final int ROW_INDEX = 1;

    static {
        for (int row : PADDING_ROWS) {
            setUpColumnsByRow(row);
        }
        for (int row : ORDERED_ROWS) {
            RowMapper.put(Character.forDigit(row, DECIMAL_SYSTEM_RADIX), row);
        }
        IntStream.range(0, ORDERED_COLUMNS.size())
                .forEach(index -> ColumnMapper.put(ORDERED_COLUMNS_ALPHABETS.get(index), ORDERED_COLUMNS.get(index)));
    }

    private final int row;
    private final int column;

    private Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(String inputPoint) {
        validateInputPointSize(inputPoint);
        try {
            int row = RowMapper.get(inputPoint.charAt(ROW_INDEX));
            int column = ColumnMapper.get(inputPoint.charAt(COLUMN_INDEX));
            return Position.of(row, column);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("[ERROR] 적절하지 않은 좌표 값 형식을 입력하셨습니다.");
        }
    }

    private static void validateInputPointSize(String inputPoint) {
        if (inputPoint.length() != 2) {
            throw new IllegalArgumentException("[ERROR] 입력된 좌표값 길이가 적절하지 않습니다.");
        }
    }

    public static Position of(int row, int column) {
        return cache.get(Objects.hash(row, column));
    }

    private static void setUpColumnsByRow(int row) {
        for (int column : PADDING_COLUMNS) {
            Position position = new Position(row, column);
            cache.put(position.hashCode(), position);
        }
    }

    public Position moveByGap(int rowGap, int columnGap) {
        return cache.get(Objects.hash(row + rowGap, column + columnGap));
    }

    public static List<List<Position>> getAllPositions() {
        List<List<Position>> allPositions = new ArrayList<>();
        for (int row : ORDERED_ROWS) {
            allPositions.add(makeRowPositions(row));
        }
        return allPositions;
    }

    private static List<Position> makeRowPositions(int row) {
        List<Position> rowPositions = new ArrayList<>();
        for (int column : ORDERED_COLUMNS) {
            rowPositions.add(Position.of(row, column));
        }
        return rowPositions;
    }

    public int calculateRowGap(Position other) {
        return other.row - this.row;
    }

    public int calculateColumnGap(Position other) {
        return other.column - this.column;
    }

    public boolean isBlackPawnInitialRow() {
        return this.row == 7;
    }

    public boolean isWhitePawnInitialRow() {
        return this.row == 2;
    }

    public boolean isSameColumn(int column) {
        return this.column == column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(column, position.column) && Objects.equals(row, position.row);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
