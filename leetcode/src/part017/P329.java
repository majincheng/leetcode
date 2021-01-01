package part017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

/**
 * 给定一个整数矩阵，找出最长递增路径的长度。
 * <p>
 * 对于每个单元格，你可以往上，下，左，右四个方向移动。 你不能在对角线方向上移动或移动到边界外（即不允许环绕）。
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums =
 * [
 * [9,9,4],
 * [6,6,8],
 * [2,1,1]
 * ]
 * 输出: 4
 * 解释: 最长递增路径为 [1, 2, 6, 9]。
 * 示例 2:
 * <p>
 * 输入: nums =
 * [
 * [3,4,5],
 * [3,2,6],
 * [2,2,1]
 * ]
 * 输出: 4
 * 解释: 最长递增路径是 [3, 4, 5, 6]。注意不允许在对角线方向上移动。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-increasing-path-in-a-matrix
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class P329 {

    public static void main(String[] args) {
        System.out.println(new Solution1().longestIncreasingPath(new int[][]{{9,9,4},{6,6,8},{2,1,1}}));
        System.out.println(new Solution1().longestIncreasingPath(new int[][]{{3,4,5},{3,2,6},{2,2,1}}));
        System.out.println(new Solution1().longestIncreasingPath(new int[][]{{1,2}}));
    }
}

class Solution{
    public int longestIncreasingPath(int[][] matrix) {
        return 0;
    }
}

/**
 * 我自己尝试的解法
 * 简单点 深度优先
 * 广度优先也能写出来, 不过深度已经超时间了, 就先不写
 */
class Solution0 {
    static int iLength;
    static int jLength;
    public int longestIncreasingPath(int[][] matrix) {
        int max = 0;
        iLength = matrix.length;
        if (iLength == 0){
            return max;
        }
        jLength = matrix[0].length;
        Point[][] points = toPoints(matrix);
        for (int i = 0; i < iLength; i++) {
            for (int j = 0; j < jLength; j++) {
                max = Math.max(max, getMax(points, points[i][j]));
            }
        }
        return max;
    }

    private int getMax(Point[][] points, Point point) {
        // 深度优先
        int step = 1;
        int maxStep = 1;
        Stack<Point> stack = new Stack<>();
        stack.add(new Point(point));
        while (!stack.empty()){
            Point now = stack.peek();
            Point next = getNext(points, now);
            if (next == null){
                maxStep = Math.max(step, maxStep);
                stack.pop();
                step--;
                continue;
            }
            step++;
            stack.push(new Point(next));
        }
        return maxStep;
    }

    private static Point getNext(Point[][] points, Point point){
        for (int i = -1; i < 2; i += 2) {
            if (!point.iNot.contains(i)){
                point.iNot.add(i);
                return points[point.i + i][point.j];
            }
            if (!point.jNot.contains(i)){
                point.jNot.add(i);
                return points[point.i][point.j + i];
            }
        }
        return null;
    }

    private static Point[][] toPoints(int[][] matrix) {
        Point[][] points = new Point[iLength][jLength];
        for (int i = 0; i < iLength; i++) {
            for (int j = 0; j < jLength; j++) {
                Point point = new Point(i, j);
                buildNot(point, matrix);
                points[i][j] = point;
            }
        }
        return points;
    }

    private static void buildNot(Point point, int[][] matrix) {
        for (int i = -1; i < 2; i += 2) {
            if (point.i + i < 0 || point.i + i == iLength || matrix[point.i + i][point.j] <= matrix[point.i][point.j]) {
                point.iNot.add(i);
            }
            if (point.j + i < 0 || point.j + i == jLength || matrix[point.i][point.j + i] <= matrix[point.i][point.j]) {
                point.jNot.add(i);
            }
        }
    }

    private static class Point {
        int i, j;
        Set<Integer> iNot;
        Set<Integer> jNot;

        public Point(int i, int j) {
            this.i = i;
            this.j = j;
            this.iNot = new HashSet<>(2);
            this.jNot = new HashSet<>(2);
        }

        public Point(Point point) {
            this.i = point.i;
            this.j = point.j;
            this.iNot = new HashSet<>(2);
            this.jNot = new HashSet<>(2);
            this.iNot.addAll(point.iNot);
            this.jNot.addAll(point.jNot);
        }
    }
}

/**
 * 还是我尝试的解法
 * 这次采用动态规划来做, 先找出路径1的, 再找出路径2的, 然后2拼1拼出3, 3拼2拼出5, 3拼1拼出4, 拼到不能拼为止
 */
class Solution1{
    public int longestIncreasingPath(int[][] matrix) {
        int length = matrix.length;
        if (length == 0){
            return 0;
        }
        int width = matrix[0].length;
        // 各级路径点
        Map<Integer, Map<Point, Point>> roads = new HashMap<>();
        Map<Point, Point> first = new HashMap<>();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                Point point = new Point(i, j);
                first.put(point, point);
            }
        }
        roads.put(1, first);

        // 从最后一级路径往之前匹配
        int max = 1;
        while (true){
            boolean notMore = true;
            int current = max;
            for (int i = current; i > 0; i--) {
                Map<Point, Point> maxRoad = roads.get(current);
                Map<Point, Point> willRoad = roads.get(i);
                if (willRoad == null){
                    continue;
                }
                Map<Point, Point> next = new HashMap<>();
                // 匹配开始
                for (Map.Entry<Point, Point> entry : maxRoad.entrySet()) {
                    qq:for (int j = -1; j < 1; j+=2) {
                        for (int k = -1; k < 1; k+=2) {
                            int x = entry.getValue().x + j;
                            int y = entry.getValue().y + k;
                            Point point = new Point(x, y);
                            if (willRoad.containsKey(point) && matrix[entry.getValue().x][entry.getValue().y] < matrix[x][y]){
                                next.put(entry.getKey(), willRoad.get(point));
                                break qq;
                            }
                        }
                    }
                }
                if (!next.isEmpty()){
                    notMore = false;
                    roads.put(current + i, next);
                    max = Math.max(max, current + i);
                }
                if (i == 1 && !notMore){
                    current ++;
                }
                if (notMore && i == 1){
                    return max;
                }
            }
            if (notMore){
                return max;
            }
        }
    }

    private static class Point{
        int x;
        int y;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}