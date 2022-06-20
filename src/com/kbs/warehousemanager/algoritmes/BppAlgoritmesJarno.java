package com.kbs.warehousemanager.algoritmes;


import java.util.*;

public class BppAlgoritmesJarno {
        static public int[] product;
        static public int inhoudContainer;
        static public int alleContainers;

        public BppAlgoritmesJarno(int[] product, int inhoudContainer, int alleContainers) {
            this.product = product;
            this.inhoudContainer = inhoudContainer;
            this.alleContainers = alleContainers;
        }

        public static int firstFit(int[] productLijst) {
            int[] productlijst = productLijst;
            // Initialize result (Count of bins)
            int result = 0;
            int[] bin_rem = new int[alleContainers];

            // Place items one by one
            for (int i = 0; i < alleContainers; i++) {
                int j;
                for (j = 0; j < result; j++) {
                    if (bin_rem[j] >= productlijst[i]) {
                        bin_rem[j] = bin_rem[j] - productlijst[i];
                        break;
                    }
                }

                if (j == result) {
                    bin_rem[result] = inhoudContainer - productlijst[i];
                    result++;
                }
            }
            return result;
        }

        public static int nextFit(int[] productlijst) {

            // Initialize result (Count of bins) and remaining
            // capacity in current bin.
            int res = 0, bin_rem = inhoudContainer;

            // Place items one by one
            for (int i = 0; i < alleContainers; i++) {
                // If this item can't fit in current bin
                if (productlijst[i] > bin_rem) {
                    res++; // Use a new bin
                    bin_rem = inhoudContainer - productlijst[i];
                } else
                    bin_rem -= productlijst[i];
            }
            return res;
        }

        public static int bestFit(int[] productlijst) {
            int res = 0;
            int[] bin_rem = new int[alleContainers];

            for (int i = 0; i < alleContainers; i++) {
                int j;
                int min = inhoudContainer + 1, bi = 0;
                for (j = 0; j < res; j++) {
                    if (bin_rem[j] >= productlijst[i] &&
                            bin_rem[j] - productlijst[i] < min) {
                        bi = j;
                        min = bin_rem[j] - productlijst[i];
                    }
                }
                if (min == inhoudContainer + 1) {
                    bin_rem[res] = inhoudContainer - productlijst[i];
                    res++;
                } else
                    bin_rem[bi] -= productlijst[i];
            }
            return res;
        }

        public static int worstFit() {

            // Initialize result (Count of bins)
            int res = 0;

            // Create an array to store remaining space in bins
            // there can be at most n bins
            int bin_rem[] = new int[alleContainers];

            // Place items one by one
            for (int i = 0; i < alleContainers; i++) {

                // Find the best bin that ca\n accommodate
                // weight[i]
                int j;

                // Initialize maximum space left and index
                // of worst bin
                int mx = -1, wi = 0;

                for (j = 0; j < res; j++) {
                    if (bin_rem[j] >= product[i] && bin_rem[j] - product[i] > mx) {
                        wi = j;
                        mx = bin_rem[j] - product[i];
                    }
                }

                // If no bin could accommodate weight[i],
                // create a new bin
                if (mx == -1) {
                    bin_rem[res] = inhoudContainer - product[i];
                    res++;
                } else // Assign the item to best bin
                    bin_rem[wi] -= product[i];
            }
            return res;
        }

        Comparator<Integer> comparator = new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        };

        public static int firstFitDec() {
            List<Integer> list = new ArrayList<>();
            for (int i : product) {
                list.add(i);
            }
            Integer[] array = list.toArray(new Integer[0]);
            // First sort all weights in decreasing order
            Arrays.sort(array, Collections.reverseOrder());
            // Now call first fit for sorted items
            int i;
            int[] sortProduct = new int[array.length];
            for (i = 0; i < array.length; i++) {
                sortProduct[i] = array[i];
            }
            return firstFit(sortProduct);
        }

        public static int nextFitDec() {
            List<Integer> list = new ArrayList<>();
            for (int i : product) {
                list.add(i);
            }
            Integer[] array = list.toArray(new Integer[0]);
            // First sort all weights in decreasing order
            Arrays.sort(array, Collections.reverseOrder());
            // Now call first fit for sorted items
            int i;
            int[] sortProduct = new int[array.length];
            for (i = 0; i < array.length; i++) {
                sortProduct[i] = array[i];
            }
            return nextFit(sortProduct);
        }

        public static int bestFitDec() {
            List<Integer> list = new ArrayList<>();
            for (int i : product) {
                list.add(i);
            }
            Integer[] array = list.toArray(new Integer[0]);
            // First sort all weights in decreasing order
            Arrays.sort(array, Collections.reverseOrder());
            // Now call first fit for sorted items
            int i;
            int[] sortProduct = new int[array.length];
            for (i = 0; i < array.length; i++) {
                sortProduct[i] = array[i];
            }
            return bestFit(sortProduct);
        }

        public String toString() {
            return "First fit :" + firstFit(product) + "\nnext fit :" + nextFit(product) + "\nbest fit :" + bestFit(product) + "\nworst fit :" + worstFit() + "\nfirst fit descending :" + firstFitDec() + "\nnext fit descending :" + nextFitDec() + "\nbest fit descending :" + bestFitDec();
        }
    }

//    public static void main(String[] args) {
//        int aantal = 999;
//        int[] product = new int[aantal];
//        for(int i=aantal - 1;i >= 0; i--){
//            Random rand = new Random();
//            int getal = rand.nextInt(11);
//            product[i] = getal;
//        }
//        int inhoudContainer = 10;
//        int alleContainers = product.length;
//        BinPack test = new BinPack(product, inhoudContainer, alleContainers);
//        System.out.println(test);
//    }
//}
