package part073;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 二叉树中的伪回文路径
 */
public class P1457 {
    /**
     * 给你一棵二叉树，每个节点的值为 1 到 9 。我们称二叉树中的一条路径是 「伪回文」的，当它满足：路径经过的所有节点值的排列中，存在一个回文序列。
     * <p>
     * 请你返回从根到叶子节点的所有路径中 伪回文 路径的数目。
     * 示例 1：
     * 输入：root = [2,3,1,3,1,null,1]
     * 输出：2
     * 解释：上图为给定的二叉树。总共有 3 条从根到叶子的路径：红色路径 [2,3,3] ，绿色路径 [2,1,1] 和路径 [2,3,1] 。
     * 在这些路径中，只有红色和绿色的路径是伪回文路径，因为红色路径 [2,3,3] 存在回文排列 [3,2,3] ，绿色路径 [2,1,1] 存在回文排列 [1,2,1] 。
     * 示例 2：
     * 输入：root = [2,1,1,1,3,null,null,null,null,null,1]
     * 输出：1
     * 解释：上图为给定二叉树。总共有 3 条从根到叶子的路径：绿色路径 [2,1,1] ，路径 [2,1,3,1] 和路径 [2,1] 。
     * 这些路径中只有绿色路径是伪回文路径，因为 [2,1,1] 存在回文排列 [1,2,1] 。
     * 示例 3：
     * 输入：root = [9]
     * 输出：1
     * 提示：
     * 给定二叉树的节点数目在 1 到 10^5 之间。
     * 节点值在 1 到 9 之间。
     */
    @Test
    public void test(){

    }

    class Solution {
        int result = 0;
        /**
         * 优秀解法
         *
         * @param root
         * @return
         */
        public int pseudoPalindromicPaths (TreeNode root){
            // 1.节点值在 1 到 9 之间。
            // 2.如果是回文串, 每个元素出现的次数都为偶数或者只有一个元素出现了奇数次
            // 1 ^ 1 = 0; 0 ^ 1 = 1; 10100 & 10011 = 10000
            deal(root, 0);
            return result;
        }

        public void deal(TreeNode node, int temp){
            temp = (1 << node.val) ^ temp;
            if (node.left == null && node.right == null){
                if (temp== 0 || (temp & (temp - 1)) == 0){
                    result++;
                }
            }
            if (node.left != null){
                deal(node.left, temp);
            }
            if (node.right != null){
                deal(node.right, temp);
            }
        }
    }

    class Solution1 {
        /**
         * 第一版解法
         *
         * @param root
         * @return
         */
        public int pseudoPalindromicPaths (TreeNode root) {
            // 先找出所有的路径
            List<String> road = getRoad(root);

            // 查看路径是否满足伪回文
            return (int) road.stream().filter(s -> {
                Map<Character, Integer> map = new HashMap<>();
                for (char c : s.toCharArray()) {
                    map.put(c, map.getOrDefault(c, 0) + 1);
                }
                if (s.length() % 2 == 0){
                    for (Integer value : map.values()) {
                        if (value % 2 != 0){
                            return false;
                        }
                    }
                    return true;
                }
                int i = 0;
                for (Integer value : map.values()) {
                    if (value % 2 != 0){
                        i++;
                    }
                }
                return i == 1;
            }).count();
        }

        private List<String> getRoad(TreeNode root){
            List<String> list = new ArrayList<>();
            if (root.left == null && root.right == null){
                list.add(String.valueOf(root.val));
            }
            if (root.left != null){
                getRoad(root.left).forEach(s -> list.add(root.val + s));
            }
            if (root.right != null){
                getRoad(root.right).forEach(s -> list.add(root.val + s));
            }
            return list;
        }
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}

