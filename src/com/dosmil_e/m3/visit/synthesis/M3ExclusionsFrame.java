package com.dosmil_e.m3.visit.synthesis;

import com.dosmil_e.modelbase.support.*;
import com.dosmil_e.modelbase.flattrx.*;

public class M3ExclusionsFrame implements M3ExclusionsFrameIfc {


  protected M3ExclusionsFrameIfc     vPreviousFrame;
  protected java.util.Hashtable      vNotAllowedToSynthetize;


 /****************************************************************************
 *  Constructors of the M3ExclusionsFrame
 ****************************************************************************/


  public M3ExclusionsFrame( M3ExclusionsFrameIfc thePreviousFrame) {

    vPreviousFrame = thePreviousFrame;
  }



/****************************************************************************
 *  Implementation of M3ExclusionsFrameIfc
 ****************************************************************************/


  public M3ExclusionsFrameIfc  getPreviousFrame() {
    return vPreviousFrame;
  }



  public void forbidToSynthetize(
    EAIMMCtxtIfc                                  theCtxt,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc theMMElement) {

    if( vNotAllowedToSynthetize == null) {
      vNotAllowedToSynthetize = new java.util.Hashtable( 13, (float) 0.5);
    }

    vNotAllowedToSynthetize.put( theMMElement, theMMElement);
  }


  public boolean isAllowedToSynthetize(
    EAIMMCtxtIfc                                  theCtxt,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc theMMElement) {

    if( vNotAllowedToSynthetize == null) { return true;}

    Object anObject = vNotAllowedToSynthetize.get( theMMElement);
    if( anObject != null) { return false;}

    M3ExclusionsFrameIfc  aPreviousFrame = getPreviousFrame();
    if( aPreviousFrame != null) { return true;}

    return aPreviousFrame.isAllowedToSynthetize( theCtxt, theMMElement);
  }



}
