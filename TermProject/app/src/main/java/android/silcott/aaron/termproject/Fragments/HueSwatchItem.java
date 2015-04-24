package android.silcott.aaron.termproject.Fragments;

public class HueSwatchItem {

    public final float START_HUE;
    public final float END_HUE;
    public final float SATURATION_PERCENT;
    public final float VALUE_PERCENT;

    HueSwatchItem(float s, float e){
        START_HUE = s;
        END_HUE = e;
        SATURATION_PERCENT = 1;
        VALUE_PERCENT = 1;
    }
    HueSwatchItem(float s, float e, float sat){
        START_HUE = s;
        END_HUE = e;
        SATURATION_PERCENT = sat;
        VALUE_PERCENT = 1;
    }
    HueSwatchItem(float s, float e, float sat, float val){
        START_HUE = s;
        END_HUE = e;
        SATURATION_PERCENT = sat;
        VALUE_PERCENT = val;
    }
}
