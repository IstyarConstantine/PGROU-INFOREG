/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Stack;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author beryl
 */
public class GrapheTest {
    
    public GrapheTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getModif method, of class Graphe.
     */
    @Test
    public void testGetModif() {
        System.out.println("getModif");
        Graphe instance = new GrapheImpl();
        Stack<Graphe> expResult = null;
        Stack<Graphe> result = instance.getModif();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setModif method, of class Graphe.
     */
    @Test
    public void testSetModif() {
        System.out.println("setModif");
        Stack<Graphe> modif = null;
        Graphe instance = new GrapheImpl();
        instance.setModif(modif);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAdj method, of class Graphe.
     */
    @Test
    public void testGetAdj() {
        System.out.println("getAdj");
        Graphe instance = new GrapheImpl();
        int[][] expResult = null;
        int[][] result = instance.getAdj();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAdj method, of class Graphe.
     */
    @Test
    public void testSetAdj() {
        System.out.println("setAdj");
        int[][] adj = null;
        Graphe instance = new GrapheImpl();
        instance.setAdj(adj);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNbsommets method, of class Graphe.
     */
    @Test
    public void testGetNbsommets() {
        System.out.println("getNbsommets");
        Graphe instance = new GrapheImpl();
        int expResult = 0;
        int result = instance.getNbsommets();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNbsommets method, of class Graphe.
     */
    @Test
    public void testSetNbsommets() {
        System.out.println("setNbsommets");
        int nbsommets = 0;
        Graphe instance = new GrapheImpl();
        instance.setNbsommets(nbsommets);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of afficher method, of class Graphe.
     */
    @Test
    public void testAfficher() {
        System.out.println("afficher");
        Graphe instance = new GrapheImpl();
        instance.afficher();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copie method, of class Graphe.
     */
    @Test
    public void testCopie() {
        System.out.println("copie");
        Graphe instance = new GrapheImpl();
        Graphe expResult = null;
        Graphe result = instance.copie();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSommet method, of class Graphe.
     */
    @Test
    public void testAddSommet() {
        System.out.println("addSommet");
        Graphe instance = new GrapheImpl();
        instance.addSommet();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of suppSommet method, of class Graphe.
     */
    @Test
    public void testSuppSommet() {
        System.out.println("suppSommet");
        Graphe instance = new GrapheImpl();
        instance.suppSommet();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of estPresent method, of class Graphe.
     */
    @Test
    public void testEstPresent() {
        System.out.println("estPresent");
        Arc a = null;
        Graphe instance = new GrapheImpl();
        boolean expResult = false;
        boolean result = instance.estPresent(a);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addArc method, of class Graphe.
     */
    @Test
    public void testAddArc_Arc() {
        System.out.println("addArc");
        Arc a = null;
        Graphe instance = new GrapheImpl();
        instance.addArc(a);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addArc method, of class Graphe.
     */
    @Test
    public void testAddArc_3args() {
        System.out.println("addArc");
        int s = 0;
        int d = 0;
        int p = 0;
        Graphe instance = new GrapheImpl();
        instance.addArc(s, d, p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of suppArc method, of class Graphe.
     */
    @Test
    public void testSuppArc_Arc() {
        System.out.println("suppArc");
        Arc a = null;
        Graphe instance = new GrapheImpl();
        instance.suppArc(a);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of suppArc method, of class Graphe.
     */
    @Test
    public void testSuppArc_3args() {
        System.out.println("suppArc");
        int s = 0;
        int d = 0;
        int p = 0;
        Graphe instance = new GrapheImpl();
        instance.suppArc(s, d, p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modifArc method, of class Graphe.
     */
    @Test
    public void testModifArc_Arc_int() {
        System.out.println("modifArc");
        Arc a = null;
        int p = 0;
        Graphe instance = new GrapheImpl();
        instance.modifArc(a, p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modifArc method, of class Graphe.
     */
    @Test
    public void testModifArc_4args() {
        System.out.println("modifArc");
        int s = 0;
        int d = 0;
        int p1 = 0;
        int p2 = 0;
        Graphe instance = new GrapheImpl();
        instance.modifArc(s, d, p1, p2);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of retourEnArriere method, of class Graphe.
     */
    @Test
    public void testRetourEnArriere() {
        System.out.println("retourEnArriere");
        Graphe instance = new GrapheImpl();
        instance.retourEnArriere();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class GrapheImpl extends Graphe {

        public Graphe copie() {
            return null;
        }

        public void addArc(Arc a) {
        }

        public void suppArc(Arc a) {
        }

        public void modifArc(Arc a, int p) {
        }
    }
    
}
