package com.fpstudios.switchbuttondemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;

public class DrawableTextHelper
{
	public static BitmapDrawable writeOnDrawable( Context context, int drawableId, String text, int color, int textSize )
	{
		if( context == null ) return null;

		Bitmap bitmap = BitmapFactory.decodeResource( context.getResources( ), drawableId );
		if( bitmap == null ) return null;

		Bitmap copy = bitmap.copy( Bitmap.Config.ARGB_8888, true );
		if( copy == null ) return null;

		Paint paint = new Paint( );
		paint.setColor( color );
		paint.setAntiAlias(true);
		paint.setTextSize( textSize );
		paint.setStyle( Style.FILL );
		paint.setTextAlign( Align.CENTER );

		Canvas canvas = new Canvas( copy );
		int x = (canvas.getWidth() / 2);
		int y = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ; 
		//((textPaint.descent() + textPaint.ascent()) / 2) is the distance from the baseline to the center.
		canvas.drawText( text, x, y, paint );

		return new BitmapDrawable( context.getResources( ), copy );
	}
}
