package com.fpstudios.switchbuttondemo.widgets;

import com.fpstudios.switchbuttondemo.R;
import com.fpstudios.switchbuttondemo.DrawableTextHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import android.view.View;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.widget.CompoundButton;

public class SwitchButton extends CompoundButton implements OnTouchListener
{
	private String				onState				= null;
	private String				offState			= null;
	private Drawable			switchOnDrawable	= null;
	private Drawable			switchOffDrawable	= null;

	private int					rightMinX;
	private int					leftMaxX;
	private boolean				pressedRight		= false;
	private boolean				pressedLeft			= false;

	private CheckStateListener	listener			= null;

	public interface CheckStateListener
	{
		void checkedStateChanged( SwitchButton switchButton, boolean checked );
	}

	/**
	 * This is used when creating the view in XML
	 */
	public SwitchButton( final Context context, final AttributeSet attrSet )
	{
		super( context, attrSet );
		initialize( );
	}

	/**
	 * This is used when creating the view programatically.
	 */
	public SwitchButton( final Context context )
	{
		super( context );
		initialize( );
	}

	private void initialize( )
	{
		setText( "ON", "OFF" );
		setGravity( Gravity.CENTER );
		setOnDrawable( R.drawable.switch_thumb, "ON" );
		setOffDrawable( R.drawable.switch_thumb, "OFF" );

		setChecked( true );		
		super.setOnTouchListener( this );
	}

	public void setText( String on, String off )
	{
		onState = on;
		offState = off;
	}

	public void setCheckStateListener( CheckStateListener listener )
	{
		this.listener = listener;
	}

	public void setOnDrawable( int drawableResId, String text )
	{
		switchOnDrawable = null;
		if( text != null && text.length( ) > 0 ) switchOnDrawable = DrawableTextHelper.writeOnDrawable( getContext( ), drawableResId, text, Color.WHITE, 20 );
		if( switchOnDrawable == null ) switchOnDrawable = getResources( ).getDrawable( drawableResId );

		switchOnDrawable.setBounds( 0, 0, switchOnDrawable.getIntrinsicWidth( ), switchOnDrawable.getIntrinsicHeight( ) );
	}

	public void setOffDrawable( int drawableResId, String text )
	{
		switchOffDrawable = null;
		if( text != null && text.length( ) > 0 ) switchOffDrawable = DrawableTextHelper.writeOnDrawable( getContext( ), drawableResId, text, Color.WHITE, 20 );
		if( switchOffDrawable == null ) switchOffDrawable = getResources( ).getDrawable( drawableResId );

		switchOffDrawable.setBounds( 0, 0, switchOffDrawable.getIntrinsicWidth( ), switchOffDrawable.getIntrinsicHeight( ) );
	}

	@Override
	public boolean onTouch( View view, MotionEvent event )
	{	
		if( event.getAction( ) == MotionEvent.ACTION_DOWN )
		{
			if( getCompoundDrawables( )[ 0 ] != null )
			{	
				pressedRight = false;
				leftMaxX = ( getWidth( ) * 3 ) / 4;
				pressedLeft = event.getX( ) < leftMaxX;
			}
			else if( getCompoundDrawables( )[ 2 ] != null )
			{
				pressedLeft = false;
				rightMinX = getWidth( ) / 4;
				pressedRight = event.getX( ) > rightMinX;
			}
		}
		else if( event.getAction( ) == MotionEvent.ACTION_MOVE || event.getAction( ) == MotionEvent.ACTION_UP )
		{
			if( pressedLeft && event.getX( ) > rightMinX )
			{
				setChecked( false );
			}
			else if( pressedRight && event.getX( ) < leftMaxX )
			{
				setChecked( true );
			}
		}

		return true;
	}

	@Override
	public void setChecked( boolean checked )
	{
		if( isChecked( ) == checked ) return;
		super.setChecked( checked );

		if( checked )
		{
			setText( offState );
			setCompoundDrawables( switchOnDrawable, getCompoundDrawables( )[ 1 ], null, getCompoundDrawables( )[ 3 ] );
		}
		else
		{
			setText( onState );
			setCompoundDrawables( null, getCompoundDrawables( )[ 1 ], switchOffDrawable, getCompoundDrawables( )[ 3 ] );
		}

		if( this.listener != null )
		{
			listener.checkedStateChanged( this, checked );
		}
	}
}
