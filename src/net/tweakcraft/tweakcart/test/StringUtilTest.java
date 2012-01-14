package net.tweakcraft.tweakcart.test;

import static org.junit.Assert.*;

import net.tweakcraft.tweakcart.util.StringUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * In the ideal situation, all the Util classes
 * Should be tested and proven not to be broken
 * @author lennart
 *
 */
public class StringUtilTest {

    @Test
    public void testStringUtilNormal(){
        String actual = "[collect items]";
        String expected = "collect items";
        assertEquals(expected, StringUtil.stripBrackets(actual));
    }
    
    @Test
    public void testStringSingleChar(){
        String actual1 = "[";
        String expected = "";
        String actual2 = "]";
        
        assertEquals(expected, StringUtil.stripBrackets(actual1));
        assertEquals(expected, StringUtil.stripBrackets(actual2));
    }

}
