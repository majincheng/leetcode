package part011;

/**
 * 数字范围按位与
 */
public class P201 {
    /**
     * 给定范围 [m, n]，其中 0 <= m <= n <= 2147483647，返回此范围内所有数字的按位与（包含 m, n 两端点）。
     *
     * 示例 1: 
     * 输入: [5,7]
     * 输出: 4
     * 示例 2:
     * 输入: [0,1]
     * 输出: 0
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(new Solution().rangeBitwiseAnd(0, 1));
    }


}

/**
 * 解法 找出公共前缀, 后面补0
 */
class Solution {
    public int rangeBitwiseAnd(int m, int n) {
        int offset = 0;
        while (n > m){
            n >>= 1;
            m >>= 1;
            offset ++;
        }
        return m << offset;
    }
}