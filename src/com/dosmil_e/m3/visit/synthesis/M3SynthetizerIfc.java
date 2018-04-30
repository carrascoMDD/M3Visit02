package com.dosmil_e.m3.visit.synthesis;

import com.dosmil_e.modelbase.support.*;
import com.dosmil_e.modelbase.flattrx.*;


import java.util.Vector;
import java.util.Hashtable;

public interface M3SynthetizerIfc  {


  public com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc synthetize(
    EAIMMCtxtIfc                                          theCtxt,
    com.dosmil_e.m3.projection.ifc.M3ProjectionConfigIfc  theSynthesisConfig,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc         theSourceMMElement) throws EAIException ;


}





