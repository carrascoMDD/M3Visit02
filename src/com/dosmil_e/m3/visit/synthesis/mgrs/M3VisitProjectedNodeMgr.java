package com.dosmil_e.m3.visit.synthesis.mgrs;

import com.dosmil_e.modelbase.support.*;
import com.dosmil_e.modelbase.flattrx.*;

public class M3VisitProjectedNodeMgr  extends com.dosmil_e.m3.traversal.cust.M3NodeMgrCust {

  public M3VisitProjectedNodeMgr() {
    super();
  }


  public M3VisitProjectedNodeMgr( EAIMMCtxtIfc theCtxt) {
    super( theCtxt);
  }

  public M3VisitProjectedNodeMgr( EAIMMCtxtIfc theCtxt, EAIMMNameIfc theName) {
    super( theCtxt, theName);
  }



/****************************************************************************
 *  Implementation of derived relationships of M3NodeMgr
 ****************************************************************************/


/* Override START */

/****************************************************************************
 *  Implementation of visiting operations of M3NodeMgr
 ****************************************************************************/

 public static final boolean sDumpVisitBoundaries = true;


  public boolean beginVisit(
    EAIMMCtxtIfc                                        theCtxt,
    com.dosmil_e.m3.visit.M3TraversalCtxtIfc            theTraversalCtxt,
    com.dosmil_e.m3.visit.M3TraversalVisitorIfc         theVisitor,
    com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc  theTraversalCfg,
    com.dosmil_e.m3.traversal.ifc.M3NodeConfigIfc       theNodeCfg,
    com.dosmil_e.m3.core.ifc.M3TypeIfc                  theType,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theSourceMMElement) throws EAIException {

    if( theCtxt == null)          { return false;}
    if( theTraversalCtxt == null) { return false;}
    if( theNodeCfg == null)       { return false;}
    if( theType == null)          { return false;}

    boolean aIsTerminal = theNodeCfg.getIsTerminal( theCtxt);
    String aTerminalString = aIsTerminal ? "Term" : "";

    if( sDumpVisitBoundaries) {
      int aNumLevels = theTraversalCtxt.getLevels();
      indent( aNumLevels + 1);
      try { System.out.print(">N" + aTerminalString + " " + theSourceMMElement.getName( theCtxt).getString() +
        " a " + theType.getName( theCtxt).getString());} catch( Exception anEx) {}
      System.out.println();
    }

    if( aIsTerminal) { return false;}

    theTraversalCtxt.push();

    synthetize( theCtxt, theTraversalCtxt, theSourceMMElement);

    return true;
  }


  public static final String gIndentString = "  ";


  static public void indent( int theIndentLevels) {
    if( sDumpVisitBoundaries) {
      for( int anIndex = 0; anIndex < theIndentLevels; anIndex++) {
        System.out.print( gIndentString);
      }
    }
  }


  public void endVisit(
    EAIMMCtxtIfc                                        theCtxt,
    com.dosmil_e.m3.visit.M3TraversalCtxtIfc            theTraversalCtxt,
    com.dosmil_e.m3.visit.M3TraversalVisitorIfc         theVisitor,
    com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc  theTraversalCfg,
    com.dosmil_e.m3.traversal.ifc.M3NodeConfigIfc       theNodeCfg,
    com.dosmil_e.m3.core.ifc.M3TypeIfc                  theType,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theSourceMMElement) throws EAIException {

    if( theCtxt == null)          { return;}
    if( theTraversalCtxt == null) { return;}
    if( theNodeCfg == null)       { return;}
    if( theType == null)          { return;}

    boolean aIsTerminal = theNodeCfg.getIsTerminal( theCtxt);
    String aTerminalString = aIsTerminal ? "Term" : "";

    if( sDumpVisitBoundaries) {
      int aNumLevels = theTraversalCtxt.getLevels();
      indent( aNumLevels);
      try { System.out.print("<N" + aTerminalString + " " + theSourceMMElement.getName( theCtxt).getString() +
        " a " + theType.getName( theCtxt).getString());} catch( Exception anEx) {}
      System.out.println();
    }

    if( aIsTerminal) { return;}

    theTraversalCtxt.pop();
  }



  public boolean beginVisit(
    EAIMMCtxtIfc                                        theCtxt,
    com.dosmil_e.m3.visit.M3TraversalCtxtIfc            theTraversalCtxt,
    com.dosmil_e.m3.visit.M3TraversalVisitorIfc         theVisitor,
    com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc  theTraversalCfg,
    com.dosmil_e.m3.traversal.ifc.M3BranchConfigIfc     theBranchCfg,
    com.dosmil_e.m3.traversal.ifc.M3BranchMgrIfc        theBranchMgr,
    com.dosmil_e.m3.core.ifc.M3RelationshipIfc          theRelationship,
    com.dosmil_e.m3.traversal.ifc.M3NodeConfigIfc       theNodeCfg,
    com.dosmil_e.m3.core.ifc.M3TypeIfc                  theType,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theSourceMMElement,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theRelatedElement) throws EAIException {

    if( theCtxt == null)          { return false;}
    if( theTraversalCtxt == null) { return false;}
    if( theNodeCfg == null)       { return false;}
    if( theType == null)          { return false;}

    boolean aIsTerminal = theNodeCfg.getIsTerminal( theCtxt);
    String aTerminalString = aIsTerminal ? "Term" : "";

    if( sDumpVisitBoundaries) {
      int aNumLevels = theTraversalCtxt.getLevels();
      indent( aNumLevels + 1);
      try { System.out.print(">rN" + aTerminalString + " " + theRelatedElement.getName( theCtxt).getString() +
        " a " + theType.getName( theCtxt).getString());} catch( Exception anEx) {}
      System.out.println();
    }

    if( aIsTerminal) { return false;}

    theTraversalCtxt.push();

    synthetize( theCtxt, theTraversalCtxt, theRelatedElement);


    return true;
  }



  public void endVisit(
    EAIMMCtxtIfc                                        theCtxt,
    com.dosmil_e.m3.visit.M3TraversalCtxtIfc            theTraversalCtxt,
    com.dosmil_e.m3.visit.M3TraversalVisitorIfc         theVisitor,
    com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc  theTraversalCfg,
    com.dosmil_e.m3.traversal.ifc.M3BranchConfigIfc     theBranchCfg,
    com.dosmil_e.m3.traversal.ifc.M3BranchMgrIfc        theBranchMgr,
    com.dosmil_e.m3.core.ifc.M3RelationshipIfc          theRelationship,
    com.dosmil_e.m3.traversal.ifc.M3NodeConfigIfc       theNodeCfg,
    com.dosmil_e.m3.core.ifc.M3TypeIfc                  theType,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theSourceMMElement,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theRelatedElement) throws EAIException {

    if( theCtxt == null)          { return;}
    if( theTraversalCtxt == null) { return;}
    if( theNodeCfg == null)       { return;}
    if( theType == null)          { return;}

    boolean aIsTerminal = theNodeCfg.getIsTerminal( theCtxt);
    String aTerminalString = aIsTerminal ? "Term" : "";

    if( sDumpVisitBoundaries) {
      int aNumLevels = theTraversalCtxt.getLevels();
      indent( aNumLevels);
      try { System.out.print("<rN" + aTerminalString + " " + theRelatedElement.getName( theCtxt).getString() +
        " a " + theType.getName( theCtxt).getString());} catch( Exception anEx) {}
      System.out.println();
    }

    if( aIsTerminal) { return;}

    theTraversalCtxt.pop();
  }




  protected void synthetize(
    EAIMMCtxtIfc                                        theCtxt,
    com.dosmil_e.m3.visit.M3TraversalCtxtIfc            theTraversalCtxt,
    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc       theProjectedMMElement) throws EAIException {

    if( theCtxt == null)                { return;}
    if( theTraversalCtxt == null)       { return;}
    if( theProjectedMMElement == null)  { return;}


    com.dosmil_e.m3.visit.synthesis.M3SynthesisCtxtIfc aM3SynthesisCtxt = null;
    try { aM3SynthesisCtxt = (com.dosmil_e.m3.visit.synthesis.M3SynthesisCtxtIfc) theTraversalCtxt;} catch( ClassCastException anEx) {}
    if( aM3SynthesisCtxt == null) { return;}

    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc[] someSourceElements =
      aM3SynthesisCtxt.getSourceByReplicated( theCtxt, theProjectedMMElement);
    if( someSourceElements == null)        { return;}
    if( someSourceElements.length < 1)     { return;}

    com.dosmil_e.m3.withm3.ifc.MMElementWithM3Ifc aSourceOfReplicatedElement = someSourceElements[ 0];
    if( aSourceOfReplicatedElement == null)        { return;}

    com.dosmil_e.m3.core.ifc.M3ModelIfc anM3Model = aSourceOfReplicatedElement.getM3Type( theCtxt).getM3Model( theCtxt);

    com.dosmil_e.m3.visit.M3TraversalVisitor aVisitor = new com.dosmil_e.m3.visit.M3TraversalVisitor( theCtxt);

    com.dosmil_e.m3.traversal.ifc.M3TraversalConfigIfc aReplicationCfg = aM3SynthesisCtxt.getSynthesisReplicationConfig(
      theCtxt, anM3Model);

    aVisitor.visit( theCtxt, aM3SynthesisCtxt, aReplicationCfg, aSourceOfReplicatedElement);
  }




/* Override END */


/****************************************************************************
 *  Serialization support
 ****************************************************************************/

  private static final long serialVersionUID = -1234123456300000007L;



}
