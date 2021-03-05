package dataframetest;

import dataframapackage.DataFrame;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataFrameTest {

    private DataFrame mydataFrame;

    @Before
    public void setUp() throws Exception {
        mydataFrame = new DataFrame();
    }

    @Test
    public void anEmptyDataFrameShouldBeEmpty()
    {
        assertTrue(mydataFrame.isEmpty());
    }

    @Test
    public void anNonEmptyDataFrameShouldBeNoNEmpty()
    {
        mydataFrame.addColumn("sample1");
        assertFalse(mydataFrame.isEmpty());
    }

    @Test
    public void correctColumnNameShouldBeReturned()
    {
        mydataFrame.addColumn("sample1");
        mydataFrame.addColumn("sample2");
        mydataFrame.addColumn("sample3");
        assertEquals("sample2",mydataFrame.getColumnName(1));
    }

    @Test
    public void rowCountShouldBeZeroForEmptyColumns()
    {
        mydataFrame.addColumn("sample1");
        mydataFrame.addColumn("sample2");
        mydataFrame.addColumn("sample3");
        assertEquals(0,mydataFrame.getRowCount());
    }

    @Test
    public void rowCountShouldBeNotZeroForNonEmptyColumns()
    {
        mydataFrame.addColumn("sample1");
        mydataFrame.addColumn("sample2");
        mydataFrame.addColumn("sample3");
        mydataFrame.addValue("sample1", "data1");
        mydataFrame.addValue("sample2", "data2");
        mydataFrame.addValue("sample3", "data3");
        assertEquals(1,mydataFrame.getRowCount());
    }

    @Test
    public void rowValueShouldBeChangedCorrectly()
    {
        mydataFrame.addColumn("sample1");
        mydataFrame.addColumn("sample2");
        mydataFrame.addColumn("sample3");
        mydataFrame.addValue("sample1", "data1");
        mydataFrame.addValue("sample2", "data2");
        mydataFrame.addValue("sample3", "data3");
        mydataFrame.putValue("sample3",0, "data2.2");
        assertEquals("data2.2",mydataFrame.getValue("sample3",0));
    }


    @Test
    public void rowValueShouldBeCReturnedIfCorrect()
    {
        mydataFrame.addColumn("sample1");
        mydataFrame.addColumn("sample2");
        mydataFrame.addColumn("sample3");
        mydataFrame.addValue("sample1", "data1");
        mydataFrame.addValue("sample2", "data2");
        mydataFrame.addValue("sample3", "data3");
        mydataFrame.putValue("sample3",0, "data2.2");
        assertNotEquals("data2.1", mydataFrame.getValue("sample3", 0));
    }
}