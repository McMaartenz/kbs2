package com.kbs.warehousemanager.algoritmes;


public abstract class FirstFitAlgoritme {
    static int[] ruimteInDoos = new int[] {10, 10, 10 ,10};

    public static void ruimteVrijmakenInDozen() {
        ruimteInDoos = new int[]{10, 10, 10, 10};
    }

    public static int bepaalDoos(int gewichtProduct) {

        int doosnummer;
        if (gewichtProduct <= ruimteInDoos[0]) {
            doosnummer = 1;
            ruimteInDoos[0] -= gewichtProduct;
        } else if (gewichtProduct <= ruimteInDoos[1]) {
            doosnummer = 2;
            ruimteInDoos[1] -= gewichtProduct;
        } else if (gewichtProduct <= ruimteInDoos[2]) {
            doosnummer = 3;
            ruimteInDoos[2] -= gewichtProduct;
        } else {
            doosnummer = 4;
            ruimteInDoos[3] -= gewichtProduct;
        }
        return doosnummer;
    }
}
