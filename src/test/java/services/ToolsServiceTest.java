package services;

import dto.Tool;
import org.junit.Assert;
import org.junit.Test;
import services.factory.ToolsServiceFactory;

import java.util.List;

public class ToolsServiceTest {

    @Test
    public void fetchRentalOfferTest() {
        ToolsService service = ToolsServiceFactory.getToolsService();
        Tool tool;

        tool = service.fetchTool("LADW");
        Assert.assertEquals("Ladder", tool.getToolType());
        Assert.assertEquals("Werner", tool.getBrand());
        Assert.assertEquals("LADW", tool.getToolCode());

        tool = service.fetchTool("CHNS");
        Assert.assertEquals("Chainsaw", tool.getToolType());
        Assert.assertEquals("Stihl", tool.getBrand());
        Assert.assertEquals("CHNS", tool.getToolCode());

        tool = service.fetchTool("JAKR");
        Assert.assertEquals("Jackhammer", tool.getToolType());
        Assert.assertEquals("Ridgid", tool.getBrand());
        Assert.assertEquals("JAKR", tool.getToolCode());

        tool = service.fetchTool("JAKD");
        Assert.assertEquals("Jackhammer", tool.getToolType());
        Assert.assertEquals("DeWalt", tool.getBrand());
        Assert.assertEquals("JAKD", tool.getToolCode());
    }

    @Test
    public void fetchAllRentalOffersTest() {
        ToolsService service = ToolsServiceFactory.getToolsService();
        List<Tool> tools = service.fetchAllTools();
        int expectedNumberOfTools = 4;

        Assert.assertEquals(expectedNumberOfTools, tools.size());
    }
}
