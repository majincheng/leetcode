package part012;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * @Author : MJC
 * @Date : 2021/1/2 14:31
 */
public class P239 {

    /**
     * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
     *
     * 返回滑动窗口中的最大值。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
     * 输出：[3,3,5,5,6,7]
     * 解释：
     * 滑动窗口的位置                最大值
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       3
     *  1 [3  -1  -3] 5  3  6  7       3
     *  1  3 [-1  -3  5] 3  6  7       5
     *  1  3  -1 [-3  5  3] 6  7       5
     *  1  3  -1  -3 [5  3  6] 7       6
     *  1  3  -1  -3  5 [3  6  7]      7
     * 示例 2：
     *
     * 输入：nums = [1], k = 1
     * 输出：[1]
     * 示例 3：
     *
     * 输入：nums = [1,-1], k = 1
     * 输出：[1,-1]
     * 示例 4：
     *
     * 输入：nums = [9,11], k = 2
     * 输出：[11]
     * 示例 5：
     *
     * 输入：nums = [4,-2], k = 2
     * 输出：[4]
     *  
     * 输入[1,3,1,2,0,5] 3
     * 输出[3,3,2,5]
     *
     * 提示：
     *
     * 1 <= nums.length <= 105
     * -104 <= nums[i] <= 104
     * 1 <= k <= nums.length
     *
     */
    @Test
    public void test(){
        long l = System.nanoTime();
        Assert.assertArrayEquals(new int[]{3,3,5,5,6,7}, new Solution().maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3));
        Assert.assertArrayEquals(new int[]{1}, new Solution().maxSlidingWindow(new int[]{1}, 1));
        Assert.assertArrayEquals(new int[]{1,-1}, new Solution().maxSlidingWindow(new int[]{1,-1}, 1));
        Assert.assertArrayEquals(new int[]{11}, new Solution().maxSlidingWindow(new int[]{9,11}, 2));
        Assert.assertArrayEquals(new int[]{4}, new Solution().maxSlidingWindow(new int[]{4,-2}, 2));
        Assert.assertArrayEquals(new int[]{3,3,2,5}, new Solution().maxSlidingWindow(new int[]{1,3,1,2,0,5}, 3));
        System.out.println(System.nanoTime() - l);
    }


    @Test
    public void test3(){
        long l = System.nanoTime();
        Assert.assertArrayEquals(new int[]{3,3,5,5,6,7}, new Solution3().maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3));
        Assert.assertArrayEquals(new int[]{1}, new Solution3().maxSlidingWindow(new int[]{1}, 1));
        Assert.assertArrayEquals(new int[]{1,-1}, new Solution3().maxSlidingWindow(new int[]{1,-1}, 1));
        Assert.assertArrayEquals(new int[]{11}, new Solution3().maxSlidingWindow(new int[]{9,11}, 2));
        Assert.assertArrayEquals(new int[]{4}, new Solution3().maxSlidingWindow(new int[]{4,-2}, 2));
        Assert.assertArrayEquals(new int[]{3,3,2,5}, new Solution3().maxSlidingWindow(new int[]{1,3,1,2,0,5}, 3));
        System.out.println(System.nanoTime() - l);
    }

    @Test
    public void test2(){
        long l = System.nanoTime();
        Assert.assertArrayEquals(new int[]{3,3,5,5,6,7}, new Solution2().maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3));
        Assert.assertArrayEquals(new int[]{1}, new Solution2().maxSlidingWindow(new int[]{1}, 1));
        Assert.assertArrayEquals(new int[]{1,-1}, new Solution2().maxSlidingWindow(new int[]{1,-1}, 1));
        Assert.assertArrayEquals(new int[]{11}, new Solution2().maxSlidingWindow(new int[]{9,11}, 2));
        Assert.assertArrayEquals(new int[]{4}, new Solution2().maxSlidingWindow(new int[]{4,-2}, 2));
        Assert.assertArrayEquals(new int[]{3,3,2,5}, new Solution2().maxSlidingWindow(new int[]{1,3,1,2,0,5}, 3));
        System.out.println(System.nanoTime() - l);
    }

    @Test
    public void test1(){
        long l = System.nanoTime();
        Assert.assertArrayEquals(new int[]{3,3,5,5,6,7}, new Solution1().maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3));
        Assert.assertArrayEquals(new int[]{1}, new Solution1().maxSlidingWindow(new int[]{1}, 1));
        Assert.assertArrayEquals(new int[]{1,-1}, new Solution1().maxSlidingWindow(new int[]{1,-1}, 1));
        Assert.assertArrayEquals(new int[]{11}, new Solution1().maxSlidingWindow(new int[]{9,11}, 2));
        Assert.assertArrayEquals(new int[]{4}, new Solution1().maxSlidingWindow(new int[]{4,-2}, 2));
        Assert.assertArrayEquals(new int[]{3,3,2,5}, new Solution1().maxSlidingWindow(new int[]{1,3,1,2,0,5}, 3));
        System.out.println(System.nanoTime() - l);
    }


    /**
     * 我的解法四 使用最大堆
     *
     * 116 ms	59 MB
     */
    class Solution{
        public int[] maxSlidingWindow(int[] nums, int k) {
            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> ((int[])o)[0]).reversed());
            int[] result = new int[nums.length - k + 1];
            for (int i = 0; i < k; i++) {
                priorityQueue.offer(new int[]{nums[i], i});
            }
            result[0] = priorityQueue.peek()[0];
            for (int i = k; i < nums.length; i++) {
                priorityQueue.offer(new int[]{nums[i], i});
                while (priorityQueue.peek()[1] <= i - k){
                    priorityQueue.poll();
                }
                result[i - k + 1] = priorityQueue.peek()[0];
            }
            return result;
        }
    }

    /**
     * 我的解法三 使用有序Map
     *
     * 507 ms	54.8 MB
     */
    class Solution3 {
        public int[] maxSlidingWindow(int[] nums, int k) {
            TreeMap<Integer, Integer> map = new TreeMap<>();
            int[] result = new int[nums.length - k + 1];
            int index = 0;
            for (int i = 0; i < k; i++) {
                map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            }
            result[index] = map.lastKey();
            for (int i = k; i < nums.length; i++) {
                Integer count = map.get(nums[index]);
                if (count == 1){
                    map.remove(nums[index]);
                } else {
                    map.put(nums[index], count - 1);
                }
                map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
                result[++index] = map.lastKey();
            }
            return result;
        }
    }

    /**
     * 我的解法2-记录窗口里最大数的位置, 勉强过关
     *
     * 1036 ms	59.8 MB
     */
    class Solution2 {
        public int[] maxSlidingWindow(int[] nums, int k) {
            int maxIndex = 0;
            int index = 0;
            int[] result = new int[nums.length - k + 1];
            for (int i = 0; i < k; i++) {
                if (nums[i] >= nums[maxIndex]){
                    maxIndex = i;
                }
            }
            for (int i = k; i < nums.length; i++) {
                result[index] = nums[maxIndex];
                if (nums[i] >= nums[maxIndex]){
                    maxIndex = i;
                } else if (maxIndex == index){
                    maxIndex = index + 1;
                    for (int j = index + 1; j <= i; j++) {
                        if (nums[j] >= nums[maxIndex]){
                            maxIndex = j;
                        }
                    }
                }
                index++;
            }
            result[index] = nums[maxIndex];
            return result;
        }
    }

    /**
     * 我的解法, 有序链表, 超出时间限制
     */
    class Solution1 {
        Node head = new Node();
        public int[] maxSlidingWindow(int[] nums, int k) {
            int index = 0;
            int[] result = new int[nums.length - k + 1];
            for (int i = 0; i < k; i++) {
                add(nums[i]);
            }
            for (int i = k; i < nums.length; i++) {
                result[index] = top();
                delete(nums[index]);
                add(nums[i]);
                index++;
            }
            result[index] = top();
            return result;
        }

        public void add(int i){
            Node p = head;
            Node last = p;
            while ((p = p.next) != null){
                if (p.data < i){
                    break;
                }
                last = p;
            }
            Node node = new Node();
            node.data = i;
            last.next = node;
            node.next = p;
        }

        public void delete(int i){
            Node p = head;
            Node last = p;
            while ((p = p.next) != null){
                if (p.data == i){
                    break;
                }
                last = p;
            }
            last.next = p.next;
        }

        public int top(){
            return head.next.data;
        }
    }

    class Node{
        int data;
        Node next;
    }
}
