package com.dosmil_e.m3.visit.synthesis;

import com.dosmil_e.modelbase.support.*;
import com.dosmil_e.modelbase.flattrx.*;

/****************************************************************************
 *  M3SynthesisExclusionsFrameIfc
 ****************************************************************************/

 public interface M3ExclusionsFrameIfc {


  public M3ExclusionsFrameIfc  getPreviousFrame();
  
  public boolean              isAllowedToSynthetize( EAIMMCtxtIfc theCtxt, com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc theMMElement);
  public void                 forbidToSynthetize(    EAIMMCtxtIfc theCtxt, com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc theMMElement);



}
