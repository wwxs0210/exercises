package arithmetic;

import java.util.HashMap;

/**
 * @Date 2020/6/5 11:16
 * Created by Wangxuehuo
 * https://leetcode-cn.com/problems/longest-consecutive-sequence/
 *
 * 最长连续序列
 * 给定一个未排序的整数数组，找出最长连续序列的长度。
 *
 * 要求算法的时间复杂度为 O(n)。
 *
 * 示例:
 *
 * 输入: [100, 4, 200, 1, 3, 2]
 * 输出: 4
 * 解释: 最长连续序列是 [1, 2, 3, 4]。它的长度为 4。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-consecutive-sequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LongestConsecutiveSequence {
    public static int longestConsecutive(int[] nums) {
        HashMap<Integer, Integer> stack = new HashMap<Integer, Integer>();
        if (nums == null || nums.length == 0){
            return 0;
        }
        int longgest = 0;
        for (int i = 0; i < nums.length; i++){
            if (!stack.containsKey(nums[i])){
                stack.put(nums[i], 1);
                int pre = stack.get(nums[i] - 1) == null ? 0 : stack.get(nums[i] - 1);
                int pos = stack.get(nums[i] + 1) == null ? 0 : stack.get(nums[i] + 1);
                int all = pre + pos + 1;
                stack.put(nums[i] - pre, all);
                stack.put(nums[i] + pos, all);
                longgest = Math.max(longgest, all);
            }
        }
        return longgest;
    }

    public static void main(String[] args) {
        System.out.println(longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
    }
}
