package com.example.ton.furnitureapplication;

/**
 * Created by Optimize on 17/12/2560.
 */

public class ActionClass {
    public boolean onSwap = false;
    public boolean onRemove = false;

    public void setOnSwap(boolean swap){
        onSwap = swap;
    }

    public boolean getOnSwap(){
        return onSwap;
    }

    public void setOnRemove(boolean remove) { onRemove = remove;}

    public boolean getOnRemove(){return onRemove; }


}
