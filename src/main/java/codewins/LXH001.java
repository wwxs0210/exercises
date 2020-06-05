package codewins;

public class LXH001 {
    public int getLocalMaximumElemenet(int[] nums) {
        if(nums.length < 1){
            return -1;
        }

        int head = 0;
        int tail = nums.length-1;

        while (head < tail) {
            int middle = (head + tail)/2;
            if(nums[middle] > nums[middle+1]){
                tail = middle;
            } else {
                head = middle +1;
            }
        }

        return head;
    }

    public static void main(String[] args) {
        LXH001 lxh001 = new LXH001();
        System.out.println(lxh001.getLocalMaximumElemenet(new int[]{1,2,1,3,4,5,6}));
    }
}