package Helpers;

/**
 * Created by sanczo on 2016-04-21.
 */
public class PermutationConverter {

    private static PermutationConverter instance;

    private PermutationConverter(){

    }

    public static PermutationConverter getInstance(){

        if (instance == null)
            instance = new PermutationConverter();
        return instance;
    }



    public int[] covertToInverse(int[] perm)
    {
        int[] inv = new int[perm.length];

        for(int i = 1; i < perm.length; i++){
            inv[i-1] = 0;
            int m = 1;
            while(perm[m-1] != i)
            {
                if(perm[m-1] > i)
                    inv[i-1] = inv[i-1] + 1;
                m++;
            }
        }
        return inv;
    }

    public int[] covertToPerm(int[] inv)
    {
        int[] perm = new int[inv.length];
        int[] pos = new int[inv.length];
        int N = inv.length;

        for(int i = N; i > 0; i--){
            for(int m = i+1; m <= N; m++){
                if(pos[m - 1] >= inv[i - 1] +1){
                    pos[m -1] = pos[m -1] + 1;
                }
            }
            pos[i - 1] = inv[i - 1] + 1;
        }

        for (int i = 1; i <=N; i++)
        {
            perm[pos[i - 1] - 1] = i;
        }
        return perm;
    }
}
