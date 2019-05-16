package arithmetic;

import com.alibaba.fastjson.JSON;

/**
 * @Date 2019/5/16 10:09
 * Created by Wangxuehuo
 * 快速排序
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arrayOld = new int[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48};
        quickSort(arrayOld);
        System.out.println(JSON.toJSONString(arrayOld));
    }

    private static int[] quickSort(int[] arrayOld) {
        if (arrayOld.length <= 1){
            return arrayOld;
        }
        qSort(arrayOld,0,arrayOld.length - 1);
        return arrayOld;
    }

    private static void qSort(int[] L, int low, int high) {
        //分割序列的位置
        int pivot;
        if (low < high){
            //将L[low,high]一分为二,算出枢轴值pivot,该值得位置固定,不用再变化
            pivot = partition(L,low,high);
            //对两边的数组分别排序
            qSort(L,low,pivot - 1);
            qSort(L,pivot + 1,high);
        }
    }

    private static int partition(int[] L, int low, int high) {
        int pivotkey;
        pivotkey = L[low];
        //顺序很重要，要先从右边找
        while (low < high){
            while (low < high && L[high] >= pivotkey){
                //从后往前找到比key小的放到前面去
                high --;
            }
            //交换位置
            swap(L,low,high);
            while (low < high && L[low] <= pivotkey){
                //从前往后找到比key大的 放到后面去
                low ++;
            }
            //交换位置
            swap(L,low,high);
        }
        //遍历所有记录  low的位置即为 key所在位置, 且固定,不用再改变
        return low;
    }

    private static void swap(int[] L, int i, int j) {
        int temp=L[i];
        L[i]=L[j];
        L[j]=temp;
    }


}
