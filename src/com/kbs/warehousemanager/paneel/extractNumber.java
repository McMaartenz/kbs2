package com.kbs.warehousemanager.paneel;

public class extractNumber {
    public static int extract(final String str) {

        if(str == null || str.isEmpty()) return 0;

        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for(char c : str.toCharArray()){
            if(Character.isDigit(c)){
                sb.append(c);
                found = true;
            } else if(found){
                // If we already found a digit before and this char is not a digit, stop looping
                break;
            }
        }

        return Integer.parseInt(sb.toString());
    }
}
