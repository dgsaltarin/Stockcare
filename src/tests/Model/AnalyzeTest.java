package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class AnalyzeTest {

    @Test
    public void lastSixMonths() {
        Analyze analyze = new Analyze();
        System.out.println(analyze.getDateSixMonthAgo());
    }

    @Before
    public void setUp() throws Exception {
    }
}