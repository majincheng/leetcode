package part080;

import com.sun.jndi.cosnaming.CNCtx;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 1593. 拆分字符串使唯一子字符串的数目最大
 */
public class P1593 {

    /**
     * 给你一个字符串 s ，请你拆分该字符串，并返回拆分后唯一子字符串的最大数目。
     *
     * 字符串 s 拆分后可以得到若干 非空子字符串 ，这些子字符串连接后应当能够还原为原字符串。但是拆分出来的每个子字符串都必须是 唯一的 。
     *
     * 注意：子字符串 是字符串中的一个连续字符序列。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：s = "ababccc"
     * 输出：5
     * 解释：一种最大拆分方法为 ['a', 'b', 'ab', 'c', 'cc'] 。像 ['a', 'b', 'a', 'b', 'c', 'cc'] 这样拆分不满足题目要求，因为其中的 'a' 和 'b' 都出现了不止一次。
     * 示例 2：
     *
     * 输入：s = "aba"
     * 输出：2
     * 解释：一种最大拆分方法为 ['a', 'ba'] 。
     * 示例 3：
     *
     * 输入：s = "aa"
     * 输出：1
     * 解释：无法进一步拆分字符串。
     *  
     *
     * 提示：
     *
     * 1 <= s.length <= 16
     *
     * s 仅包含小写英文字母
     */
    @Test
    public void test(){
        Assert.assertEquals(1, new Solution().maxUniqueSplit("aa"));
        Assert.assertEquals(5, new Solution().maxUniqueSplit("ababccc"));
        Assert.assertEquals(2, new Solution().maxUniqueSplit("aba"));
        Assert.assertEquals(4, new Solution().maxUniqueSplit("degbb"));
    }

    class Solution {
        int res = 0;
        Set<String> set = new HashSet<>();

        public int maxUniqueSplit(String s) {
            deal(s, 0);
            return res;
        }

        private void deal(String s, int begin) {
            // 剪枝：若当前已拆分结果加上剩余字符数小于之前得到的答案，则没有必要再进行下去
            if (set.size() + s.length() - begin <= res) {
                return;
            }
            // 到达字符串尾部，说明拆分完毕
            if (begin == s.length()) {
                if (set.size() > res) {
                    res = set.size();
                }
                return;
            }
            // 回溯体
            for (int i = begin; i < s.length(); ++i) {
                String substr = s.substring(begin, i + 1);
                if (!set.contains(substr)) {
                    set.add(substr);
                    deal(s, i + 1);
                    set.remove(substr);
                }
            }
        }
    }
}
