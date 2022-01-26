/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author beryl
 */
public class ArcTest {
    
    public ArcTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getSrc method, of class Arc.
     */
    @Test
    public void testGetSrc() {
        System.out.println("getSrc");
        Arc instance = null;
        int expResult = 0;
        int result = instance.getSrc();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPoids method, of class Arc.
     */
    @Test
    public void testGetPoids() {
        System.out.println("getPoids");
        Arc instance = null;
        int expResult = 0;
        int result = instance.getPoids();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPoids method, of class Arc.
     */
    @Test
    public void testSetPoids() {
        System.out.println("setPoids");
        int poids = 0;
        Arc instance = null;
        instance.setPoids(poids);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDest method, of class Arc.
     */
    @Test
    public void testGetDest() {
        System.out.println("getDest");
        Arc instance = null;
        int expResult = 0;
        int result = instance.getDest();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDest method, of class Arc.
     */
    @Test
    public void testSetDest() {
        System.out.println("setDest");
        int dest = 0;
        Arc instance = null;
        instance.setDest(dest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSrc method, of class Arc.
     */
    @Test
    public void testSetSrc() {
        System.out.println("setSrc");
        int src = 0;
        Arc instance = null;
        instance.setSrc(src);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class Arc.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        Arc compareEdge = null;
        Arc instance = null;
        int expResult = 0;
        int result = instance.compareTo(compareEdge);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
