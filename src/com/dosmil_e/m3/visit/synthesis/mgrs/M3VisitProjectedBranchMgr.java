package com.dosmil_e.m3.visit.synthesis.mgrs;

import com.dosmil_e.modelbase.support.*;
import com.dosmil_e.modelbase.flattrx.*;

public class M3VisitProjectedBranchMgr extends com.dosmil_e.m3.traversal.cust.M3BranchMgrCust{

  public M3VisitProjectedBranchMgr() {
    super();
  }


  public M3VisitProjectedBranchMgr( EAIMMCtxtIfc theCtxt) {
    super( theCtxt);
  }

  public M3VisitProjectedBranchMgr( EAIMMCtxtIfc theCtxt, EAIMMNameIfc theName) {
    super( theCtxt, theName);
  }



/****************************************************************************
 *  Implementation of derived relationships of M3BranchMgr
 ****************************************************************************/

/* Override START */

/****************************************************************************
 *  Implementation of visiting operations of M3BranchMgr
 ****************************************************************************/


  public boolean beginVisit(
    EAIMMCtxtIfc                                          theCtxt,
    com.dosmil_e.m3.visit.M3TraversalCtxtIfc              theTraversalCtxt,
    com.dosmil_e.m3.visit.M3TraversalVisitorIfc           theVisitor,
    com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc    theTraversalCfg,
    com.dosmil_e.m3.traversal.ifc.M3BranchConfigIfc       theBranchCfg,
    com.dosmil_e.m3.core.ifc.M3RelationshipIfc            theRelationship,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc         theSourceMMElement,
    com.dosmil_e.modelbase.ifc.EAIMMElementIfc[]  theRelatedMMElements) throws EAIException {

    if( theTraversalCtxt == null) { return false;}
    if( theRelationship == null)  { return false;}

    if( M3VisitProjectedNodeMgr.sDumpVisitBoundaries) {
      int aNumLevels = theTraversalCtxt.getLevels();
      M3VisitProjectedNodeMgr.indent( aNumLevels + 1);
      try { System.out.print(">B " + theRelationship.getName( theCtxt).getString());} catch( Exception anEx) {}
      System.out.println();
    }

    theTraversalCtxt.push();

    return true;
  }

  public void endVisit(
    EAIMMCtxtIfc                                         theCtxt,
    com.dosmil_e.m3.visit.M3TraversalCtxtIfc             theTraversalCtxt,
    com.dosmil_e.m3.visit.M3TraversalVisitorIfc          theVisitor,
    com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc   theTraversalCfg,
    com.dosmil_e.m3.traversal.ifc.M3BranchConfigIfc      theBranchCfg,
    com.dosmil_e.m3.core.ifc.M3RelationshipIfc           theRelationship,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc        theSourceMMElement,
    com.dosmil_e.modelbase.ifc.EAIMMElementIfc[] theRelatedMMElements) throws EAIException {

    if( theTraversalCtxt == null) { return;}
    if( theRelationship == null) { return;}

    if( M3VisitProjectedNodeMgr.sDumpVisitBoundaries) {
      int aNumLevels = theTraversalCtxt.getLevels();
      M3VisitProjectedNodeMgr.indent( aNumLevels);
      try { System.out.print("<B " + theRelationship.getName( theCtxt).getString());} catch( Exception anEx) {}
      System.out.println();
    }

    theTraversalCtxt.pop();

  }

/* Override END */


/****************************************************************************
 *  Serialization support
 ****************************************************************************/

  private static final long serialVersionUID = -1234123456300000007L;



}
