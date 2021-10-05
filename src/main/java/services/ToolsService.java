package services;

import dto.Tool;

import java.util.List;

public interface ToolsService {

    Tool fetchTool(String toolCode);
    List<Tool> fetchAllTools();
}
