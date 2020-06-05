package codewins;

public class WXS001 {
    /**
     * 常规解法
     * @param nums
     * @return
     */
    public int getLocalMaximumElemenet(int[] nums) {
        int index = 0;
        if (nums.length <= 1){
            return index;
        }
        if (nums.length == 2){
            return nums[index] >= nums[index + 1] ? index : index + 1;
        }
        int left = nums[index];
        int biggest = nums[index + 1];
        for (int i = 2; i < nums.length; i++) {
            if ((left < biggest && nums[i] < biggest)){
                break;
            }
            index ++ ;
            left = nums[index];
            biggest = nums[index + 1];
        }
        if (index == nums.length - 2){
            return nums[0] >= nums[nums.length - 1] ? 0 : index + 1;
        }
        return index + 1;
    }

    public static void main(String[] args) {
        WXS001 solution = new WXS001();
        System.out.println(solution.getLocalMaximumElemenet(new int[]{1,2,1,3,5,6,4}));
    }
}
