package com.kbs.warehousemanager.algoritmes;

public abstract class NextFitAlgoritme {
        static int[] ruimteInDoos = new int[] {10, 10, 10 ,10} ;

        static int j = 0;

        public static int bepaalDoos(int gewichtProduct) {

            int doosnummer = 0;
                if (j == 0 && gewichtProduct <= ruimteInDoos[0]) {
                    doosnummer = 1;
                    ruimteInDoos[0] -= gewichtProduct;
                    if(gewichtProduct > ruimteInDoos[0]) j++;
                } else if (j == 1 && gewichtProduct <= ruimteInDoos[1]) {
                    doosnummer = 2;
                    ruimteInDoos[1] -= gewichtProduct;
                    if(gewichtProduct > ruimteInDoos[1]) j++;
                } else if (j == 2 && gewichtProduct <= ruimteInDoos[2]) {
                    doosnummer = 3;
                    ruimteInDoos[2] -= gewichtProduct;
                    if(gewichtProduct > ruimteInDoos[2]) j++;
                } else if(j == 3){
                    doosnummer = 4;
                    ruimteInDoos[3] -= gewichtProduct;
                    if(gewichtProduct > ruimteInDoos[3]) j++;
                }
            return doosnummer;
        }
}