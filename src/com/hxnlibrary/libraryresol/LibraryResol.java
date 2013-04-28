package com.hxnlibrary.libraryresol;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

public class LibraryResol {
	private static final String LogTag ="LibraryResol";
	
	public static class ResolKnight{
		private Resources mRes;
		//private Resources mRes;
		private DisplayMetrics mMetric;
		private float mCurScale;
		
		public final float mWidthInch;
		public final float mHeightInch;
		public final float mSizeInch;
		private float mExactDPI;
		private float mSizeRatio;
		private float mWidR; // Width Ratio
		private float mHeiR; // Height Ratio
		
		// Designer design device size
		private float mDesignSizeInch = 4.62f; // design target device diagonal inch
		private float mDesignWidthInch = 2.28346f;
		private float mDesignHeightInch = 4.01457f;
		private int mDesignDPI = 317;
		private float mDesignScale = 1.98f;
		
		public ResolKnight(Resources res){
			mRes = res;
			mMetric = mRes.getDisplayMetrics();
			
			// Calculate the exact DPI;
			// Here we don't use the DPI Android automatically calculate -> mMetric.densityDPI.  Because the value is sometimes not accurate.
			// Please try S2 you'll find out the reason.
			mExactDPI = (float) ( Math.sqrt( Math.pow(mMetric.xdpi, 2) + Math.pow(mMetric.ydpi, 2) ) / ( 1.414 ) ); // Math.sqrt(2) = 1.414
			mCurScale = mExactDPI / 160f ; // e.g: 320/160 = 2.0,  240dpi / 160 = 1.5.  (160dpi is the base DPI.)
			
			Log.i(LogTag,"Resol: " + mMetric.heightPixels + "x" + mMetric.widthPixels + ". DPI: " + mExactDPI + ". Current Scale/160dpi = "+ mCurScale);
			Log.i(LogTag,"( Xdpi = " + mMetric.xdpi + ", Ydpi = " + mMetric.ydpi + ")");
			
			mWidthInch = mMetric.widthPixels / mMetric.xdpi; //current device width in inch
			mHeightInch = mMetric.heightPixels / mMetric.ydpi; //current device height in inch
			mSizeInch = (float) Math.sqrt( Math.pow(mWidthInch, 2) + Math.pow(mHeightInch, 2));
			Log.i(LogTag, "Current width/height in inch = " + mWidthInch + "/" + mHeightInch + ". Diagonal =" + mSizeInch + "inch");
			
			calculateRatio();
			
		}
		
		public void calculateRatio(){
			
			mSizeRatio = (float)(mSizeInch / mDesignSizeInch);
			mWidR = mWidthInch / mDesignWidthInch;
			mHeiR = mHeightInch / mDesignHeightInch;
			
			Log.i(LogTag, "Ratio: Width Ratio = " + mWidR + ", Height Ratio = "+ mHeiR + ". Size Ratio = " + mSizeRatio);
		}
		
		public void resetDesignDeviceValue(float DPI, float widthInch, float heightInch){
			
			mDesignScale = DPI/160;
			mDesignWidthInch = widthInch;
			mDesignHeightInch = heightInch;
			mDesignSizeInch = (float) Math.sqrt(Math.pow(widthInch, 2) + Math.pow(heightInch, 2));
			
			calculateRatio();
		}
		
		
		
		public float getSizeRatio(){
			return mSizeRatio;
		}
		
		public float getWidR(){
			return mWidR;
		}
		
		public float getHeiR(){
			return mHeiR;
		}
		
		
		// Screen Size ratio this variable is implemented here.
		// We need to recalculate the px or dp.
		public int szPDtoPXaxis(int designPx){
			int curPx = dpiPxToPxCur(designPx);
			return (int)(curPx * mWidR);
		}
		
		public int szPDtoPYaxis(int designPx){
			int curPx = dpiPxToPxCur(designPx);
			return (int)(curPx * mHeiR);
		}
		
		public int szPDtoPC(int designPx){
			int curPx = dpiPxToPxCur(designPx);
			return (int)(curPx * mSizeRatio);
		}
		
		public int szDDtoDC(int designDP){
			return (int)(designDP*mSizeRatio);
		}
		
		public int szDDtoDC(float designDP){
			return (int)(designDP*mSizeRatio);
		}
		
		public int szPDtoSPC(int designPt){
			float curSP = dgnPxToDp((float)designPt);
			return szDDtoDC(curSP);
		}
		// End
		
		
		// DPI variable implement. 
		public int dpiPxToPxCur(int px){
			int dp = dgnPxToDp(px);
			float result = dp * mCurScale; //TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mMetric);
			//Log.i("Resol","Final output px = " + result);
			return (int)result;
			
		}
		
		public int dpiPxToPxCur(float px){
			int dp = dgnPxToDp((int)px);
			float result = dp* mCurScale;//TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mMetric);
			//Log.i("Resol","Final output px = " + result);
			return (int)result;
			
		}
		// End
		
		
		// Design DP
		public int dgnPxToDp(int px){
			int result = (int)( px / mDesignScale + 0.5f );
			//Log.i("Resol","Input px = "+px+", Output dp="+ result);
			return result; 
		}
		
		public int dgnPxToDp(float px){
			int result = (int)( px / mDesignScale + 0.5f );
			//Log.i("Resol","Input px = "+px+", Output dp="+ result);
			return result; 
		}
		
		
	}
	
	public static class ScrSizeKnight{
		
	}

}
