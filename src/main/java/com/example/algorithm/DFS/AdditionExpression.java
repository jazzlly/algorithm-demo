package com.example.algorithm.dfs;

/**
 *  abc + def = ghi
 *  [a-i] is in [1-9]
 */
public class AdditionExpression {
    public static final int MAX_STEP = 9;
    int[] box = new int[MAX_STEP];
    boolean[] cardUsed = new boolean[MAX_STEP];

    public AdditionExpression() {
        for (int i = 0; i < MAX_STEP; i++) {
            box[i] = -1;
            cardUsed[i] = false;
        }
    }

    int contactNum(int a, int b, int c) {
        return a * 100 + b * 10 + c;
    }

    int getCardValue(int no) {
        return no + 1;
    }

    void dfs(int step) {
        // 1. boundary
        if (step >= MAX_STEP) {
            int a = contactNum(box[0], box[1], box[2]);
            int b = contactNum(box[3], box[4], box[5]);
            int c = contactNum(box[6], box[7], box[8]);

            if ((a + b) == c) {
                System.out.println(a + " + " + b + " = " + c);
            }
            return;
        }

        // 2. check every possibility
        for (int i = 0; i < MAX_STEP; i++) {
            if (!cardUsed[i]) {
                box[step] = getCardValue(i);
                cardUsed[i] = true;
                dfs(step + 1);
                cardUsed[i] = false;
            }
        }

        return;
    }

    public static void main(String[] args) {
        AdditionExpression add = new AdditionExpression();
        add.dfs(0);
    }

}
