package com.liujianjian.suanfa;

/**
 * created by liujianjian
 * on 2021/6/30 1:58 下午
 */
public class FindNoDuplicatedNumberInArray {
    public static int findNoDuplicateNumber(int array []) {
        int result = 0;

        int length = array.length;
        int subArray1 [] = new int[array.length/2 + 1];
        int subArray2 [] = new int[array.length/2 + 1];

        int length1 = 0;
        int length2 = 0;
        for (int i = 0; i < length;i++) {
            if (i%2==0) {
                subArray1[length1++] = array[i];
            } else {
                subArray2[length2++] = array[i];
            }
        }

        //第一种情况subArray1={0,1,2,3,4}
        //第一种情况subArray2={1,2,3,4}


        //第二种情况subArray1={1,2,3,4,5}
        //第二种情况subArray2={1,2,4,5}


        //第三种情况subArray1={1,2,3,4,5,6}
        //第三种情况subArray2={1,2,3,4,5}


        for (int i = 0; i < subArray1.length;i++) {
            if (subArray1[i] == subArray2[i]) {

            } else {
                return subArray1[i];
            }
        }
        return result;
    }



    public static void main(String[] args) {
        int array [] = {0,1,1,2,2,3,3,4,4};
        int array1 [] = {1,1,2,2,3,4,4,5,5};
        int array2 [] = {1,1,2,2,4,4,5,5,6};

        int first = findNoDuplicateNumber(array);
        System.out.println("不重复的数字在最前面的情况是="+first);


        int second = findNoDuplicateNumber(array1);
        System.out.println("不重复的数字在中间的情况是="+second);

        int third = findNoDuplicateNumber(array2);
        System.out.println("不重复的数字在最后面的情况是="+third);

    }

}
