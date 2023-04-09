package domain.position;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private final List<Position> positions = new ArrayList<>();

    public Path(Position start, Position end) {
        makePathTo(start, end);
    }

    public void makePathTo(Position start, Position end) {
        if (Direction.of(start, end) == Direction.OTHER) {
            positions.add(end);
            return;
        }
        calculatePath(start, end);
    }

    private void calculatePath(Position start, Position end) {
        /*
        path를 계산하는 로직이 복잡해서 직관적으로 이해가 안되실 것 같아 주석을 덧붙입니다.
        start end 두 포지션을 받아서 각 row, column 간 차이를 구합니다.
        그 다음 row와 column 중 더 큰 차이가 있는 곳의 좌표 값을 1씩 증감시킨 위치를 path에 추가합니다.
        만약 정대각선의 경우 row, column 값을 동시에 1씩 증감하면서 위치를 path에 추가합니다.
        모든 케이스 별로 메서드를 나누면 복잡한 수식은 리팩토링할 수 있겠지만 시간 상 문제로 생략했습니다. 감안 부탁드립니다. :)
         */

        int rowGap = start.calculateRowGap(end);
        int columnGap = start.calculateColumnGap(end);

        int unit = Math.max(Math.abs(rowGap), Math.abs(columnGap));
        int rowCoefficient = rowGap / unit;
        int columnCoefficient = columnGap / unit;
        for (int i = 1; i <= unit; i++) {
            positions.add(start.moveByGap(rowCoefficient * i, columnCoefficient * i));
        }
    }

    public int size() {
        return positions.size();
    }

    public List<Position> subListFirstTo(int exclusiveIndex) {
        return positions.subList(0, exclusiveIndex);
    }

    public Position getFirstPosition() {
        return positions.get(0);
    }

    public Position getEndPosition() {
        return positions.get(positions.size() - 1);
    }

    public List<Position> getPositions() {
        return positions;
    }
}
