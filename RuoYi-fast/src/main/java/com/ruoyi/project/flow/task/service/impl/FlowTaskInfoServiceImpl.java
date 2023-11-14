package com.ruoyi.project.flow.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.JSONUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.flow.chart.area.domain.FlowTaskArea;
import com.ruoyi.project.flow.chart.area.service.IFlowTaskAreaService;
import com.ruoyi.project.flow.chart.line.domain.FlowTaskLine;
import com.ruoyi.project.flow.chart.line.service.IFlowTaskLineService;
import com.ruoyi.project.flow.chart.node.domain.FlowTaskNode;
import com.ruoyi.project.flow.chart.node.service.IFlowTaskNodeService;
import com.ruoyi.project.flow.info.domain.FlowInfo;
import com.ruoyi.project.flow.info.service.IFlowInfoService;
import com.ruoyi.project.flow.task.domain.FlowTaskInfo;
import com.ruoyi.project.flow.task.mapper.FlowTaskInfoMapper;
import com.ruoyi.project.flow.task.service.IFlowTaskInfoService;
import com.ruoyi.project.flow.task.util.FlowUtil;
import com.ruoyi.project.flow.task.util.po.FlowTaskBean;
import com.ruoyi.project.flow.task.util.po.FlowTaskNodeView;
import com.ruoyi.project.flow.task.util.vo.FlowTaskView;

/**
 * 【流程任务】Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
@Service
public class FlowTaskInfoServiceImpl implements IFlowTaskInfoService 
{
    @Autowired
    private FlowTaskInfoMapper flowTaskInfoMapper;
    @Autowired
    private IFlowTaskAreaService flowTaskAreaService;
    @Autowired
    private IFlowTaskLineService flowTaskLineService;
    @Autowired
    private IFlowTaskNodeService flowTaskNodeService;
    @Autowired
    private IFlowInfoService flowInfoService;
    
    /**
     * 查询【流程任务】
     * @param flowId 【流程任务】ID
     * @return 【流程任务】
     */
    @Override
	public FlowTaskInfo selectFlowTaskInfoById(String flowId) {
    	
		FlowTaskView ftv = getFlowTaskView(flowId);
		String flowJson = JSONUtils.beanToJson(ftv);
		
		FlowTaskInfo flowTaskInfo = new FlowTaskInfo();
		flowTaskInfo.setFlowId(flowId);
		flowTaskInfo.setFlowJson(flowJson);
		return flowTaskInfo;
	}

    /**
     * 根据FlowId加载FlowTaskView
     * @param flowId
     * @return
     */
    public FlowTaskView getFlowTaskView(String flowId) {
    	FlowInfo flowInfo = flowInfoService.selectFlowInfoById(flowId);
		/**
    	 * 1.先把线段,节点, 区域信息查询出来
    	 */
    	FlowTaskArea areaS = new FlowTaskArea();
    	areaS.setFlowid(flowId);
    	List<FlowTaskArea> flowTaskAreaList = flowTaskAreaService.selectFlowTaskAreaList(areaS);
    	
//    	List<FlowTaskArea> flowTaskAreaListVo = new ArrayList<FlowTaskArea>();
//    	for (FlowTaskArea flowTaskArea : flowTaskAreaList) {
//    		FlowTaskArea ftav = new FlowTaskArea();
//    	    ftav.setFlowid(flowTaskArea.getFlowid());
//    	    ftav.setId(flowTaskArea.getId());
//    	    ftav.setName(flowTaskArea.getName());
//    	    ftav.setLeft(flowTaskArea.getLeft());
//    	    ftav.setTop(flowTaskArea.getTop());
//    	    ftav.setColor(flowTaskArea.getColor());
//    	    ftav.setWidth(flowTaskArea.getWidth());
//    	    ftav.setHeight(flowTaskArea.getHeight());
//    	    flowTaskAreaListVo.add(ftav);
//		}
//    	
    	FlowTaskLine lineS = new FlowTaskLine();
    	lineS.setFlowid(flowId);
		List<FlowTaskLine> flowTaskLineList = flowTaskLineService.selectFlowTaskLineList(lineS);
//		List<FlowTaskLine> flowTaskLineListVo = new ArrayList<FlowTaskLine>();
//		for (FlowTaskLine flowTaskLine : flowTaskLineList) {
//			FlowTaskLine ftlv = new FlowTaskLine();
//		    ftlv.setFlowid(flowTaskLine.getFlowid());
//		    ftlv.setId(flowTaskLine.getId());
//		    ftlv.setName(flowTaskLine.getName());
//		    ftlv.setType(flowTaskLine.getType());
//		    ftlv.setFrom(flowTaskLine.getFrom());
//		    ftlv.setTo(flowTaskLine.getTo());
//			flowTaskLineListVo.add(ftlv);
//		}
//		
//		
    	FlowTaskNode nodeS = new FlowTaskNode();
    	nodeS.setFlowid(flowId);
		List<FlowTaskNode> flowTaskNodeList = flowTaskNodeService.selectFlowTaskNodeList(nodeS);
		
		
//		List<FlowTaskNode> flowTaskNodeListVo = new ArrayList<FlowTaskNode>();
//		for (FlowTaskNode flowTaskNode : flowTaskNodeList) {
//			FlowTaskNode ftnv = new FlowTaskNode();
//			ftnv.setFlowid(flowTaskNode.getFlowid());
//			ftnv.setId(flowTaskNode.getId());
//			ftnv.setName(flowTaskNode.getName());
//			ftnv.setLeft(flowTaskNode.getLeft());
//			ftnv.setTop(flowTaskNode.getTop());
//			ftnv.setType(flowTaskNode.getType());
//			ftnv.setWidth(flowTaskNode.getWidth());
//			ftnv.setHeight(flowTaskNode.getHeight());
//			ftnv.setAlt(flowTaskNode.getAlt());
//			flowTaskNodeListVo.add(ftnv);
//		}
    	/**
    	 * 2.设置实体类, 准备JSON
    	 */
    	FlowTaskView ftv = new FlowTaskView(); 
    	ftv.setTitle(flowInfo.getFlowName());
    	/**
    	 * 设置Area
    	 */
    	Map<String, FlowTaskArea> areasMap = new HashMap<String, FlowTaskArea>();
    	for (FlowTaskArea flowTaskArea : flowTaskAreaList) {
    		areasMap.put(flowTaskArea.getId(), flowTaskArea);
		}
		ftv.setAreas(areasMap);
		/**
		 * 设置Line
		 */
		Map<String, FlowTaskLine> linesMap = new HashMap<String, FlowTaskLine>();
		for (FlowTaskLine flowTaskLine : flowTaskLineList) {
			linesMap.put(flowTaskLine.getId(), flowTaskLine);
		}
		ftv.setLines(linesMap);
		/**
		 * 设置Node
		 */
		Map<String, FlowTaskNode> nodesMap = new HashMap<String, FlowTaskNode>();
		for (FlowTaskNode flowTasknodes : flowTaskNodeList) {
			nodesMap.put(flowTasknodes.getId(), flowTasknodes);
		}
		ftv.setNodes(nodesMap);

    	FlowTaskInfo selectFlowTaskInfoById = flowTaskInfoMapper.selectFlowTaskInfoById(flowId);
    	if(selectFlowTaskInfoById == null){
    		ftv.setInitNum(0);
    	}else{
    		ftv.setInitNum(selectFlowTaskInfoById.getInitNum());
    	}
    	testName(flowId);
		return ftv;
	}
    Map<String, Object> fff= JSONUtils.jsonToMap("{\"flow_id_1adfccef9cde48c3809a0653a671602b_line_9\":{\"createBy\":\"\",\"createTime\":1578279194761,\"flowid\":\"1adfccef9cde48c3809a0653a671602b\",\"from\":\"flow_id_1adfccef9cde48c3809a0653a671602b_node_4\",\"id\":\"flow_id_1adfccef9cde48c3809a0653a671602b_line_9\",\"name\":\"\",\"params\":{},\"to\":\"flow_id_1adfccef9cde48c3809a0653a671602b_node_5\",\"type\":\"sl\"},\"flow_id_1adfccef9cde48c3809a0653a671602b_line_8\":{\"createBy\":\"\",\"createTime\":1578279194757,\"flowid\":\"1adfccef9cde48c3809a0653a671602b\",\"from\":\"flow_id_1adfccef9cde48c3809a0653a671602b_node_1\",\"id\":\"flow_id_1adfccef9cde48c3809a0653a671602b_line_8\",\"name\":\"\",\"params\":{},\"to\":\"flow_id_1adfccef9cde48c3809a0653a671602b_node_4\",\"type\":\"sl\"},\"flow_id_1adfccef9cde48c3809a0653a671602b_line_10\":{\"createBy\":\"\",\"createTime\":1578279194764,\"flowid\":\"1adfccef9cde48c3809a0653a671602b\",\"from\":\"flow_id_1adfccef9cde48c3809a0653a671602b_node_5\",\"id\":\"flow_id_1adfccef9cde48c3809a0653a671602b_line_10\",\"name\":\"\",\"params\":{},\"to\":\"flow_id_1adfccef9cde48c3809a0653a671602b_node_6\",\"type\":\"sl\"},\"flow_id_1adfccef9cde48c3809a0653a671602b_line_12\":{\"createBy\":\"\",\"createTime\":1578279194770,\"flowid\":\"1adfccef9cde48c3809a0653a671602b\",\"from\":\"flow_id_1adfccef9cde48c3809a0653a671602b_node_7\",\"id\":\"flow_id_1adfccef9cde48c3809a0653a671602b_line_12\",\"name\":\"\",\"params\":{},\"to\":\"flow_id_1adfccef9cde48c3809a0653a671602b_node_2\",\"type\":\"sl\"},\"flow_id_1adfccef9cde48c3809a0653a671602b_line_11\":{\"createBy\":\"\",\"createTime\":1578279194766,\"flowid\":\"1adfccef9cde48c3809a0653a671602b\",\"from\":\"flow_id_1adfccef9cde48c3809a0653a671602b_node_6\",\"id\":\"flow_id_1adfccef9cde48c3809a0653a671602b_line_11\",\"name\":\"\",\"params\":{},\"to\":\"flow_id_1adfccef9cde48c3809a0653a671602b_node_7\",\"type\":\"sl\"}}");
    public void testName(String flowId){
    	
    	
	}
    
    /**
     * 查询【流程任务】列表
     * 
     * @param flowTaskInfo 【流程任务】
     * @return 【流程任务】
     */
    @Override
    public List<FlowTaskInfo> selectFlowTaskInfoList(FlowTaskInfo flowTaskInfo)
    {
        return flowTaskInfoMapper.selectFlowTaskInfoList(flowTaskInfo);
    }

    /**
     * 新增【流程任务】
     * 
     * @param flowTaskInfo 【流程任务】
     * @return 结果
     */
    @Override
    public int insertFlowTaskInfo(FlowTaskInfo flowTaskInfo){
    	
    	String flowId = flowTaskInfo.getFlowId();
    	
    	//先把存在的删了再插入
    	deleteFlowTaskInfoById(flowId);
    	flowTaskInfo = FlowUtil.setFlowJsonIndex(flowTaskInfo);
        flowTaskInfo.setCreateTime(DateUtils.getNowDate());
        
        /**
         * 1.解析JSON
         */
        FlowTaskBean flowBean = FlowUtil.setFlowBean(flowTaskInfo);
        flowTaskInfo.setInitNum(flowBean.getInitNum());
        /**
         * 2.处理area
         */
        flowTaskAreaService.deleteFlowTaskAreaById(flowId);
        List<FlowTaskArea> areasList = flowBean.getAreasList();
        for (FlowTaskArea flowTaskArea : areasList) {
        	flowTaskAreaService.insertFlowTaskArea(flowTaskArea);
		}
        /**
         * 3.处理line
         */
        flowTaskLineService.deleteFlowTaskLineById(flowId);
        List<FlowTaskLine> linesList = flowBean.getLinesList();
        for (FlowTaskLine flowTaskLine : linesList) {
        	flowTaskLineService.insertFlowTaskLine(flowTaskLine);
        }
        /**
         * 4.处理node
         */
        flowTaskNodeService.deleteFlowTaskNodeById(flowId);
        List<FlowTaskNodeView> nodesList = flowBean.getNodesList();
        for (FlowTaskNodeView flowTaskNode : nodesList) {
        	flowTaskNodeService.insertFlowTaskNode(flowTaskNode);
        }
        return flowTaskInfoMapper.insertFlowTaskInfo(flowTaskInfo);
    }

    /**
     * 修改【流程任务】
     * 
     * @param flowTaskInfo 【流程任务】
     * @return 结果
     */
    @Override
    public int updateFlowTaskInfo(FlowTaskInfo flowTaskInfo)
    {
        flowTaskInfo.setUpdateTime(DateUtils.getNowDate());
        return flowTaskInfoMapper.updateFlowTaskInfo(flowTaskInfo);
    }

    /**
     * 删除【流程任务】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFlowTaskInfoByIds(String ids)
    {
        return flowTaskInfoMapper.deleteFlowTaskInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【流程任务】信息
     * 
     * @param flowId 【流程任务】ID
     * @return 结果
     */
    public int deleteFlowTaskInfoById(String flowId)
    {
        return flowTaskInfoMapper.deleteFlowTaskInfoById(flowId);
    }
}
