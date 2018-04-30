package com.dosmil_e.m3.visit.synthesis;

import com.dosmil_e.modelbase.support.*;
import com.dosmil_e.modelbase.flattrx.*;

public interface M3SynthesisCtxtIfc extends com.dosmil_e.m3.visit.M3TraversalCtxtIfc {

  public void registerReplicated(
    EAIMMCtxtIfc                                  theCtxt,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc theSourceMMElement,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc theReplicatedMMElement);

  public com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc[] getReplicatedBySource(
    EAIMMCtxtIfc                                  theCtxt,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc theSourceMMElement);

  public com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc[] getSourceByReplicated(
    EAIMMCtxtIfc                                  theCtxt,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc theReplicatedMMElement);

  public com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc getSynthesisReplicationConfig(
    EAIMMCtxtIfc                        theCtxt,
    com.dosmil_e.m3.core.ifc.M3ModelIfc theM3Model);
    
    
}



