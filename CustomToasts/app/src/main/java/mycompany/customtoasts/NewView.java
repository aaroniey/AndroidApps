package mycompany.customtoasts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by Asilcott on 1/23/2015.
 */
public class NewView extends View {
    Bitmap mImageBitMap = null;
    public NewView(Context context, Bitmap variable){
        super(context);
        mImageBitMap = variable;
    }

    @Override
    protected void onDraw(Canvas cans){
        super.onDraw(cans);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        Rect destshape = new Rect(0,0,mImageBitMap.getWidth(), mImageBitMap.getHeight());
        cans.drawBitmap(mImageBitMap, null, destshape, paint);
    }
}
