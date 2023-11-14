package com.ruoyi.project.flow.task.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.druid.support.json.JSONUtils;
import com.ruoyi.common.utils.DateUtil;
import com.ruoyi.project.flow.chart.area.domain.FlowTaskArea;
import com.ruoyi.project.flow.chart.line.domain.FlowTaskLine;
import com.ruoyi.project.flow.chart.node.domain.FlowTaskNode;
import com.ruoyi.project.flow.exeFlowTask.mapper.ExeBean;
import com.ruoyi.project.flow.subTask.domain.FlowTaskSubInfo;
import com.ruoyi.project.flow.task.domain.FlowTaskInfo;
import com.ruoyi.project.flow.task.util.po.FlowTaskBean;
import com.ruoyi.project.flow.task.util.po.FlowTaskNodeView;
import com.ruoyi.project.flow.task.util.vo.FlowTaskView;

public class FlowUtil {
	/**
	 * 	统一替换变量	
	 *  //id 实例ID
		//flowID 流程ID
		//sysdate 系统时间 yyyyMMddhhmmss
		//sysdateYmd 系统时间 yyyyMMdd
		//sysdateY_m_d 系统时间 yyyy-MM-dd
		//sysdateHis 系统时间  HH:mm:ss
		//
	 * @param str
	 * @return
	 */
	public static String repParamStr(String str,ExeBean exeBean){
		if(StringUtils.isNoneBlank(str)){
			return str.replace("${id}", exeBean.getSl_id())
					 .replace("${flowId}", exeBean.getFlowTaskBean().getFlowId())
					 .replace("${sysdate}", DateUtil.getSysTime())
					 .replace("${sysdateYmd}", DateUtil.getDate())
					 .replace("${sysdateY_m_d}", DateUtil.getDateFormat())
					 .replace("${sysdateHis}", DateUtil.getTime());
		}
		return str;
	}
	
	/**
	 * 根据传入的FlowJson,解析并装载对应的Bean
	 * @param flowTaskInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static FlowTaskBean setFlowBean(FlowTaskInfo fti) {
		LinkedHashMap<String,Object> lh = (LinkedHashMap<String,Object>) JSONUtils.parse(fti.getFlowJson());
		FlowTaskBean ftb = new FlowTaskBean();
		ftb.setTitle(String.valueOf(lh.get("title")));
		ftb.setInitNum(Integer.valueOf(String.valueOf(lh.get("initNum"))));
		ftb.setFlowId(fti.getFlowId());
		ftb.setFlowJson(fti.getFlowJson());
		LinkedHashMap<String, LinkedHashMap<String,String>> nodesMap = (LinkedHashMap<String, LinkedHashMap<String,String>>) lh.get("nodes");
		LinkedHashMap<String, LinkedHashMap<String,String>> linesMap = (LinkedHashMap<String, LinkedHashMap<String,String>>) lh.get("lines");
		LinkedHashMap<String, LinkedHashMap<String,String>> areasMap = (LinkedHashMap<String, LinkedHashMap<String,String>>) lh.get("areas");

		Set<Entry<String, LinkedHashMap<String,String>>> nodesMapEntrySet = nodesMap.entrySet();
		Set<Entry<String, LinkedHashMap<String,String>>> linesMapEntrySet = linesMap.entrySet();
		Set<Entry<String, LinkedHashMap<String,String>>> areasMapEntrySet = areasMap.entrySet();
		
		List<FlowTaskNodeView> nodesList = new ArrayList<FlowTaskNodeView>();
		List<FlowTaskLine> linesList = new ArrayList<FlowTaskLine>();
		List<FlowTaskArea> areasList = new ArrayList<FlowTaskArea>();
		
		LinkedHashMap<String, FlowTaskNodeView> nodesMapBean = new LinkedHashMap<String, FlowTaskNodeView>();
		LinkedHashMap<String, FlowTaskLine> linesMapBean = new LinkedHashMap<String, FlowTaskLine>();
		LinkedHashMap<String, FlowTaskArea> areasMapBean = new LinkedHashMap<String, FlowTaskArea>();
		
		for (Entry<String, LinkedHashMap<String,String>> nodesMapEntry : nodesMapEntrySet) {
			LinkedHashMap<String,String> nodesValue = nodesMapEntry.getValue();
			FlowTaskNodeView ftn = new FlowTaskNodeView();
			ftn.setFlowid(fti.getFlowId());
			ftn.setId(String.valueOf(nodesMapEntry.getKey()));
			ftn.setName(String.valueOf(nodesValue.get("name")));
			ftn.setLeft(Integer.valueOf(String.valueOf(nodesValue.get("left"))));
			ftn.setTop(Integer.valueOf(String.valueOf(nodesValue.get("top"))));
			ftn.setType(String.valueOf(nodesValue.get("type")));
			ftn.setWidth(Integer.valueOf(String.valueOf(nodesValue.get("width"))));
			ftn.setHeight(Integer.valueOf(String.valueOf(nodesValue.get("height"))));
			ftn.setAlt(String.valueOf(nodesValue.get("alt")));
			nodesMapBean.put(nodesMapEntry.getKey(), ftn);
			nodesList.add(ftn);
		}
		for (Entry<String, LinkedHashMap<String,String>> linesMapEntry : linesMapEntrySet) {
			LinkedHashMap<String,String> lineValue = linesMapEntry.getValue();
			FlowTaskLine ftl = new FlowTaskLine();
			ftl.setFlowid(fti.getFlowId());
			ftl.setId(String.valueOf(linesMapEntry.getKey()));
			ftl.setName(String.valueOf(lineValue.get("name")));
			ftl.setType(String.valueOf(lineValue.get("type")));
			ftl.setFrom(String.valueOf(lineValue.get("from")));
			ftl.setTo(String.valueOf(lineValue.get("to")));
			linesMapBean.put(linesMapEntry.getKey(), ftl);
			linesList.add(ftl);
		}

		for (Entry<String, LinkedHashMap<String,String>> areasMapEntry : areasMapEntrySet) {
			LinkedHashMap<String,String> areaValue = areasMapEntry.getValue();
			FlowTaskArea fta = new FlowTaskArea();
			fta.setFlowid(fti.getFlowId());
			fta.setId(String.valueOf(areasMapEntry.getKey()));
			fta.setName(String.valueOf(areaValue.get("name")));
			fta.setLeft(String.valueOf(areaValue.get("left")));
			fta.setTop(String.valueOf(areaValue.get("top")));
			fta.setColor(String.valueOf(areaValue.get("color")));
			fta.setWidth(String.valueOf(areaValue.get("width")));
			fta.setHeight(String.valueOf(areaValue.get("height")));
			fta.setAlt(String.valueOf(areaValue.get("alt")));
			areasMapBean.put(areasMapEntry.getKey(), fta);
			areasList.add(fta);
		}
		ftb.setNodesList(nodesList);
		ftb.setLinesList(linesList);
		ftb.setAreasList(areasList);
		ftb.setNodes(nodesMapBean);
		ftb.setLines(linesMapBean);
		ftb.setAreas(areasMapBean);
		//最后一步设置各个节点依赖关系,并set回去
		ftb.setNodesList(setFlowDependence(ftb));
		return ftb;
	}
	
	/**
	 * 设置各个节点的依赖关系,从哪来到哪去
	 * @param fti
	 * @return
	 */
	public static List<FlowTaskNodeView> setFlowDependence(FlowTaskBean flowBean) {
		List<FlowTaskNodeView> nodesListNew = new ArrayList<FlowTaskNodeView>();
		List<FlowTaskNodeView> nodesList = flowBean.getNodesList();
		//1.遍历所有节点
		for (FlowTaskNodeView flowTaskNode : nodesList) {
			//2.根据节点ID 去line里面找依赖关系
			String id = flowTaskNode.getId();
			List<FlowTaskLine> linesList = flowBean.getLinesList();
			List<String> toList = new ArrayList<String>();
			List<String> fromList = new ArrayList<String>();
			for (FlowTaskLine flowTaskLine : linesList) {
				String to = flowTaskLine.getTo();
				String from = flowTaskLine.getFrom();
				//3.满足条件的add进集合
				if(to.equals(id)){
					fromList.add(from);
				}
				if(from.equals(id)){
					toList.add(to);
				}
			}
			//4.set回去
			flowTaskNode.setToNodeId(toList);
			flowTaskNode.setFromNodeId(fromList);
			nodesListNew.add(flowTaskNode);
		}
		return nodesListNew;
	}
	
	public static void main(String[] args) {
		FlowTaskInfo t = new FlowTaskInfo();
		t.setFlowId("12345678");
		String flowJson = "{\"title\":\"3321\",\"nodes\":{\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_48\":{\"name\":\"node_48\",\"left\":594,\"top\":30,\"type\":\"start round\",\"width\":24,\"height\":24,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_49\":{\"name\":\"node_49\",\"left\":262,\"top\":104,\"type\":\"node\",\"width\":100,\"height\":24,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_50\":{\"name\":\"node_50\",\"left\":558,\"top\":107,\"type\":\"node\",\"width\":100,\"height\":24,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_51\":{\"name\":\"node_51\",\"left\":862,\"top\":113,\"type\":\"node\",\"width\":100,\"height\":24,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_52\":{\"name\":\"node_52\",\"left\":298,\"top\":178,\"type\":\"node\",\"width\":100,\"height\":24,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_53\":{\"name\":\"node_53\",\"left\":493,\"top\":177,\"type\":\"node\",\"width\":100,\"height\":24,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_54\":{\"name\":\"node_54\",\"left\":653,\"top\":179,\"type\":\"node\",\"width\":100,\"height\":24,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_55\":{\"name\":\"node_55\",\"left\":805,\"top\":181,\"type\":\"node\",\"width\":100,\"height\":24,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_56\":{\"name\":\"node_56\",\"left\":932,\"top\":181,\"type\":\"node\",\"width\":100,\"height\":24,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_57\":{\"name\":\"node_57\",\"left\":179,\"top\":181,\"type\":\"node\",\"width\":100,\"height\":24,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_58\":{\"name\":\"node_58\",\"left\":268,\"top\":250,\"type\":\"node\",\"width\":100,\"height\":24,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_60\":{\"name\":\"node_60\",\"left\":870,\"top\":247,\"type\":\"node\",\"width\":100,\"height\":24,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_61\":{\"name\":\"node_61\",\"left\":574,\"top\":336,\"type\":\"node\",\"width\":100,\"height\":24,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_62\":{\"name\":\"node_62\",\"left\":609,\"top\":442,\"type\":\"end round\",\"width\":24,\"height\":24,\"alt\":true}},\"lines\":{\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_63\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_48\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_49\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_64\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_48\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_50\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_65\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_48\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_51\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_66\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_49\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_57\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_67\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_49\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_52\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_68\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_50\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_53\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_69\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_50\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_54\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_70\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_51\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_55\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_71\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_51\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_56\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_72\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_57\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_58\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_73\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_52\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_58\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_76\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_55\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_60\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_77\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_56\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_60\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_81\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_61\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_62\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_82\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_58\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_61\",\"name\":\"enh\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_83\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_53\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_61\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_84\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_54\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_61\",\"name\":\"\"},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_line_85\":{\"type\":\"sl\",\"from\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_60\",\"to\":\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_node_61\",\"name\":\"\"}},\"areas\":{\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_area_86\":{\"name\":\"LEO\",\"left\":233,\"top\":71,\"color\":\"blue\",\"width\":766,\"height\":96,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_area_87\":{\"name\":\"VIRGO\",\"left\":109,\"top\":175,\"color\":\"green\",\"width\":1044,\"height\":121,\"alt\":true},\"flow_id_aeec6daca72f4e8aadc62bc77fa290ad_area_88\":{\"name\":\"ERMSERMS\",\"left\":522,\"top\":320,\"color\":\"blue\",\"width\":200,\"height\":100,\"alt\":true}},\"initNum\":89}";
		t.setFlowJson(flowJson);
		FlowTaskBean setFlowBean = setFlowBean(t);
		FlowTaskView ftv = setViewBean(setFlowBean);
		String beanToJson = com.ruoyi.common.utils.JSONUtils.beanToJson(ftv);
		System.out.println(beanToJson);
	}

	/**
	 * 装载前端显示内容Bean
	 * @param setFlowBean
	 * @return
	 */
	public static FlowTaskView setViewBean(FlowTaskBean setFlowBean) {
		FlowTaskView ftv = new FlowTaskView();
		Map<String, FlowTaskArea> areas = setFlowBean.getAreas();
//		Map<String, FlowTaskAreaVo> areasVo = new HashMap<String, FlowTaskAreaVo>();
//		Set<Entry<String, FlowTaskArea>> areasEntrySet = areas.entrySet();
//		for (Entry<String, FlowTaskArea> areasEntry : areasEntrySet) {
//			FlowTaskAreaVo ftav = new FlowTaskAreaVo();
//			FlowTaskArea areasvalue = areasEntry.getValue();
//		    ftav.setFlowid(areasvalue.getFlowid());
//		    ftav.setId(areasvalue.getId());
//		    ftav.setName(areasvalue.getName());
//		    ftav.setLeft(areasvalue.getLeft());
//		    ftav.setTop(areasvalue.getTop());
//		    ftav.setColor(areasvalue.getColor());
//		    ftav.setWidth(areasvalue.getWidth());
//		    ftav.setHeight(areasvalue.getHeight());
//			areasVo.put(areasEntry.getKey(), ftav);
//		}
		
		Map<String, FlowTaskLine> lines = setFlowBean.getLines();
//		Map<String, FlowTaskLineVo> linesVo = new HashMap<String, FlowTaskLineVo>();
//		Set<Entry<String, FlowTaskLine>> linesEntrySet = lines.entrySet();
//		for (Entry<String, FlowTaskLine> linesEntry : linesEntrySet) {
//			FlowTaskLineVo ftlv = new FlowTaskLineVo();
//			FlowTaskLine linesvalue = linesEntry.getValue();
//   		    ftlv.setFlowid(linesvalue.getFlowid());
//   		    ftlv.setId(linesvalue.getId());
//   		    ftlv.setName(linesvalue.getName());
//   		    ftlv.setType(linesvalue.getType());
//   		    ftlv.setFrom(linesvalue.getFrom());
//   		    ftlv.setTo(linesvalue.getTo());
//			linesVo.put(linesEntry.getKey(), ftlv);
//		}
		ftv.setAreas(areas);
		ftv.setInitNum(setFlowBean.getInitNum());
		ftv.setLines(lines);
		ftv.setTitle(setFlowBean.getTitle());
		
		Map<String, FlowTaskNodeView> nodes = setFlowBean.getNodes();
		Map<String, FlowTaskNode> nodesVo = new HashMap<String, FlowTaskNode>();
		Set<Entry<String, FlowTaskNodeView>> nodesEntrySet = nodes.entrySet();
		for (Entry<String, FlowTaskNodeView> nodesEntry : nodesEntrySet) {
			FlowTaskNode ftn = nodesEntry.getValue();
			nodesVo.put(nodesEntry.getKey(), ftn);
		}
		ftv.setNodes(nodesVo);
		return ftv;
	}
	
	

	/**
	 * 设置存储流程Json,根据3000字符分隔
	 * @param flowTaskInfo
	 * @return
	 */
	public static FlowTaskInfo setFlowJsonIndex(FlowTaskInfo flowTaskInfo) {
		String flowJson = flowTaskInfo.getFlowJson();
		int flowJsonLength = flowJson.length();
    	if(flowJsonLength<3000){
    		flowTaskInfo.setFlowJson0(flowJson);
    	}else if(flowJsonLength>3000&& flowJsonLength<6000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000));
    	}else if(flowJsonLength>6000&& flowJsonLength<9000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000));
    	}else if(flowJsonLength>9000&& flowJsonLength<12000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000));
    	}else if(flowJsonLength>12000&& flowJsonLength<15000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000));
    	}else if(flowJsonLength>15000&& flowJsonLength<18000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000));
    	}else if(flowJsonLength>18000&& flowJsonLength<21000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000));
    	}else if(flowJsonLength>21000&& flowJsonLength<24000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000));
    	}else if(flowJsonLength>24000&& flowJsonLength<27000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000));
    	}else if(flowJsonLength>27000&& flowJsonLength<30000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000));
    	}else if(flowJsonLength>30000&& flowJsonLength<33000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000));
    	}else if(flowJsonLength>33000&& flowJsonLength<36000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000));
    	}else if(flowJsonLength>36000&& flowJsonLength<39000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000));
    	}else if(flowJsonLength>39000&& flowJsonLength<42000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000));
    	}else if(flowJsonLength>42000&& flowJsonLength<45000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000));
    	}else if(flowJsonLength>45000&& flowJsonLength<48000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000));
    	}else if(flowJsonLength>48000&& flowJsonLength<51000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000,48000));
    		flowTaskInfo.setFlowJson16(flowJson.substring(48000));
    	}else if(flowJsonLength>51000&& flowJsonLength<54000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000,48000));
    		flowTaskInfo.setFlowJson16(flowJson.substring(48000,51000));
    		flowTaskInfo.setFlowJson17(flowJson.substring(51000));
    	}else if(flowJsonLength>54000&& flowJsonLength<57000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000,48000));
    		flowTaskInfo.setFlowJson16(flowJson.substring(48000,51000));
    		flowTaskInfo.setFlowJson17(flowJson.substring(51000,54000));
    		flowTaskInfo.setFlowJson18(flowJson.substring(54000));
    	}else if(flowJsonLength>57000&& flowJsonLength<60000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000,48000));
    		flowTaskInfo.setFlowJson16(flowJson.substring(48000,51000));
    		flowTaskInfo.setFlowJson17(flowJson.substring(51000,54000));
    		flowTaskInfo.setFlowJson18(flowJson.substring(54000,57000));
    		flowTaskInfo.setFlowJson19(flowJson.substring(57000));
    	}else if(flowJsonLength>60000&& flowJsonLength<63000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000,48000));
    		flowTaskInfo.setFlowJson16(flowJson.substring(48000,51000));
    		flowTaskInfo.setFlowJson17(flowJson.substring(51000,54000));
    		flowTaskInfo.setFlowJson18(flowJson.substring(54000,57000));
    		flowTaskInfo.setFlowJson19(flowJson.substring(57000,60000));
    		flowTaskInfo.setFlowJson20(flowJson.substring(60000));
    	}else if(flowJsonLength>63000&& flowJsonLength<66000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000,48000));
    		flowTaskInfo.setFlowJson16(flowJson.substring(48000,51000));
    		flowTaskInfo.setFlowJson17(flowJson.substring(51000,54000));
    		flowTaskInfo.setFlowJson18(flowJson.substring(54000,57000));
    		flowTaskInfo.setFlowJson19(flowJson.substring(57000,60000));
    		flowTaskInfo.setFlowJson20(flowJson.substring(60000,63000));
    		flowTaskInfo.setFlowJson21(flowJson.substring(63000));
    	}else if(flowJsonLength>66000&& flowJsonLength<69000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000,48000));
    		flowTaskInfo.setFlowJson16(flowJson.substring(48000,51000));
    		flowTaskInfo.setFlowJson17(flowJson.substring(51000,54000));
    		flowTaskInfo.setFlowJson18(flowJson.substring(54000,57000));
    		flowTaskInfo.setFlowJson19(flowJson.substring(57000,60000));
    		flowTaskInfo.setFlowJson20(flowJson.substring(60000,63000));
    		flowTaskInfo.setFlowJson21(flowJson.substring(63000,66000));
    		flowTaskInfo.setFlowJson22(flowJson.substring(66000));
    	}else if(flowJsonLength>69000&& flowJsonLength<72000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000,48000));
    		flowTaskInfo.setFlowJson16(flowJson.substring(48000,51000));
    		flowTaskInfo.setFlowJson17(flowJson.substring(51000,54000));
    		flowTaskInfo.setFlowJson18(flowJson.substring(54000,57000));
    		flowTaskInfo.setFlowJson19(flowJson.substring(57000,60000));
    		flowTaskInfo.setFlowJson20(flowJson.substring(60000,63000));
    		flowTaskInfo.setFlowJson21(flowJson.substring(63000,66000));
    		flowTaskInfo.setFlowJson22(flowJson.substring(66000,69000));
    		flowTaskInfo.setFlowJson23(flowJson.substring(69000));
    	}else if(flowJsonLength>72000&& flowJsonLength<75000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000,48000));
    		flowTaskInfo.setFlowJson16(flowJson.substring(48000,51000));
    		flowTaskInfo.setFlowJson17(flowJson.substring(51000,54000));
    		flowTaskInfo.setFlowJson18(flowJson.substring(54000,57000));
    		flowTaskInfo.setFlowJson19(flowJson.substring(57000,60000));
    		flowTaskInfo.setFlowJson20(flowJson.substring(60000,63000));
    		flowTaskInfo.setFlowJson21(flowJson.substring(63000,66000));
    		flowTaskInfo.setFlowJson22(flowJson.substring(66000,69000));
    		flowTaskInfo.setFlowJson23(flowJson.substring(69000,72000));
    		flowTaskInfo.setFlowJson24(flowJson.substring(72000));
    	}else if(flowJsonLength>75000&& flowJsonLength<78000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000,48000));
    		flowTaskInfo.setFlowJson16(flowJson.substring(48000,51000));
    		flowTaskInfo.setFlowJson17(flowJson.substring(51000,54000));
    		flowTaskInfo.setFlowJson18(flowJson.substring(54000,57000));
    		flowTaskInfo.setFlowJson19(flowJson.substring(57000,60000));
    		flowTaskInfo.setFlowJson20(flowJson.substring(60000,63000));
    		flowTaskInfo.setFlowJson21(flowJson.substring(63000,66000));
    		flowTaskInfo.setFlowJson22(flowJson.substring(66000,69000));
    		flowTaskInfo.setFlowJson23(flowJson.substring(69000,72000));
    		flowTaskInfo.setFlowJson24(flowJson.substring(72000,75000));
    		flowTaskInfo.setFlowJson25(flowJson.substring(75000));
    	}else if(flowJsonLength>78000&& flowJsonLength<81000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000,48000));
    		flowTaskInfo.setFlowJson16(flowJson.substring(48000,51000));
    		flowTaskInfo.setFlowJson17(flowJson.substring(51000,54000));
    		flowTaskInfo.setFlowJson18(flowJson.substring(54000,57000));
    		flowTaskInfo.setFlowJson19(flowJson.substring(57000,60000));
    		flowTaskInfo.setFlowJson20(flowJson.substring(60000,63000));
    		flowTaskInfo.setFlowJson21(flowJson.substring(63000,66000));
    		flowTaskInfo.setFlowJson22(flowJson.substring(66000,69000));
    		flowTaskInfo.setFlowJson23(flowJson.substring(69000,72000));
    		flowTaskInfo.setFlowJson24(flowJson.substring(72000,75000));
    		flowTaskInfo.setFlowJson25(flowJson.substring(75000,78000));
    		flowTaskInfo.setFlowJson26(flowJson.substring(78000));
    	}else if(flowJsonLength>81000&& flowJsonLength<84000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000,48000));
    		flowTaskInfo.setFlowJson16(flowJson.substring(48000,51000));
    		flowTaskInfo.setFlowJson17(flowJson.substring(51000,54000));
    		flowTaskInfo.setFlowJson18(flowJson.substring(54000,57000));
    		flowTaskInfo.setFlowJson19(flowJson.substring(57000,60000));
    		flowTaskInfo.setFlowJson20(flowJson.substring(60000,63000));
    		flowTaskInfo.setFlowJson21(flowJson.substring(63000,66000));
    		flowTaskInfo.setFlowJson22(flowJson.substring(66000,69000));
    		flowTaskInfo.setFlowJson23(flowJson.substring(69000,72000));
    		flowTaskInfo.setFlowJson24(flowJson.substring(72000,75000));
    		flowTaskInfo.setFlowJson25(flowJson.substring(75000,78000));
    		flowTaskInfo.setFlowJson26(flowJson.substring(78000,81000));
    		flowTaskInfo.setFlowJson27(flowJson.substring(81000));
    	}else if(flowJsonLength>84000&& flowJsonLength<87000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000,48000));
    		flowTaskInfo.setFlowJson16(flowJson.substring(48000,51000));
    		flowTaskInfo.setFlowJson17(flowJson.substring(51000,54000));
    		flowTaskInfo.setFlowJson18(flowJson.substring(54000,57000));
    		flowTaskInfo.setFlowJson19(flowJson.substring(57000,60000));
    		flowTaskInfo.setFlowJson20(flowJson.substring(60000,63000));
    		flowTaskInfo.setFlowJson21(flowJson.substring(63000,66000));
    		flowTaskInfo.setFlowJson22(flowJson.substring(66000,69000));
    		flowTaskInfo.setFlowJson23(flowJson.substring(69000,72000));
    		flowTaskInfo.setFlowJson24(flowJson.substring(72000,75000));
    		flowTaskInfo.setFlowJson25(flowJson.substring(75000,78000));
    		flowTaskInfo.setFlowJson26(flowJson.substring(78000,81000));
    		flowTaskInfo.setFlowJson27(flowJson.substring(81000,84000));
    		flowTaskInfo.setFlowJson28(flowJson.substring(84000));
    	}else if(flowJsonLength>87000&& flowJsonLength<90000){
    		flowTaskInfo.setFlowJson0(flowJson.substring(0,3000));
    		flowTaskInfo.setFlowJson1(flowJson.substring(3000,6000));
    		flowTaskInfo.setFlowJson2(flowJson.substring(6000,9000));
    		flowTaskInfo.setFlowJson3(flowJson.substring(9000,12000));
    		flowTaskInfo.setFlowJson4(flowJson.substring(12000,15000));
    		flowTaskInfo.setFlowJson5(flowJson.substring(15000,18000));
    		flowTaskInfo.setFlowJson6(flowJson.substring(18000,21000));
    		flowTaskInfo.setFlowJson7(flowJson.substring(21000,24000));
    		flowTaskInfo.setFlowJson8(flowJson.substring(24000,27000));
    		flowTaskInfo.setFlowJson9(flowJson.substring(27000,30000));
    		flowTaskInfo.setFlowJson10(flowJson.substring(30000,33000));
    		flowTaskInfo.setFlowJson11(flowJson.substring(33000,36000));
    		flowTaskInfo.setFlowJson12(flowJson.substring(36000,39000));
    		flowTaskInfo.setFlowJson13(flowJson.substring(39000,42000));
    		flowTaskInfo.setFlowJson14(flowJson.substring(42000,45000));
    		flowTaskInfo.setFlowJson15(flowJson.substring(45000,48000));
    		flowTaskInfo.setFlowJson16(flowJson.substring(48000,51000));
    		flowTaskInfo.setFlowJson17(flowJson.substring(51000,54000));
    		flowTaskInfo.setFlowJson18(flowJson.substring(54000,57000));
    		flowTaskInfo.setFlowJson19(flowJson.substring(57000,60000));
    		flowTaskInfo.setFlowJson20(flowJson.substring(60000,63000));
    		flowTaskInfo.setFlowJson21(flowJson.substring(63000,66000));
    		flowTaskInfo.setFlowJson22(flowJson.substring(66000,69000));
    		flowTaskInfo.setFlowJson23(flowJson.substring(69000,72000));
    		flowTaskInfo.setFlowJson24(flowJson.substring(72000,75000));
    		flowTaskInfo.setFlowJson25(flowJson.substring(75000,78000));
    		flowTaskInfo.setFlowJson26(flowJson.substring(78000,81000));
    		flowTaskInfo.setFlowJson27(flowJson.substring(81000,84000));
    		flowTaskInfo.setFlowJson28(flowJson.substring(84000,87000));
    		flowTaskInfo.setFlowJson29(flowJson.substring(87000));
    	}
    	return flowTaskInfo;
	}
	
	/**
	 * 设置存储流程任务Src,根据3000字符分隔
	 * @param flowTaskInfo
	 * @return
	 */
	public static FlowTaskSubInfo setFlowTaskSubSrcIndex(FlowTaskSubInfo flowTaskSubInfo) {
		String exeStr = flowTaskSubInfo.getExeStr();
		int exeStrLength = exeStr.length();
		if(exeStrLength<3000){
			flowTaskSubInfo.setExeStr0(exeStr);
		}else if(exeStrLength>3000&& exeStrLength<6000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000));
		}else if(exeStrLength>6000&& exeStrLength<9000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000));
		}else if(exeStrLength>9000&& exeStrLength<12000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000));
		}else if(exeStrLength>12000&& exeStrLength<15000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000));
		}else if(exeStrLength>15000&& exeStrLength<18000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000));
		}else if(exeStrLength>18000&& exeStrLength<21000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000,18000));
			flowTaskSubInfo.setExeStr6(exeStr.substring(18000));
		}else if(exeStrLength>21000&& exeStrLength<24000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000,18000));
			flowTaskSubInfo.setExeStr6(exeStr.substring(18000,21000));
			flowTaskSubInfo.setExeStr7(exeStr.substring(21000));
		}else if(exeStrLength>24000&& exeStrLength<27000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000,18000));
			flowTaskSubInfo.setExeStr6(exeStr.substring(18000,21000));
			flowTaskSubInfo.setExeStr7(exeStr.substring(21000,24000));
			flowTaskSubInfo.setExeStr8(exeStr.substring(24000));
		}else if(exeStrLength>27000&& exeStrLength<30000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000,18000));
			flowTaskSubInfo.setExeStr6(exeStr.substring(18000,21000));
			flowTaskSubInfo.setExeStr7(exeStr.substring(21000,24000));
			flowTaskSubInfo.setExeStr8(exeStr.substring(24000,27000));
			flowTaskSubInfo.setExeStr9(exeStr.substring(27000));
		}else if(exeStrLength>30000&& exeStrLength<33000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000,18000));
			flowTaskSubInfo.setExeStr6(exeStr.substring(18000,21000));
			flowTaskSubInfo.setExeStr7(exeStr.substring(21000,24000));
			flowTaskSubInfo.setExeStr8(exeStr.substring(24000,27000));
			flowTaskSubInfo.setExeStr9(exeStr.substring(27000,30000));
			flowTaskSubInfo.setExeStr10(exeStr.substring(30000));
		}else if(exeStrLength>33000&& exeStrLength<36000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000,18000));
			flowTaskSubInfo.setExeStr6(exeStr.substring(18000,21000));
			flowTaskSubInfo.setExeStr7(exeStr.substring(21000,24000));
			flowTaskSubInfo.setExeStr8(exeStr.substring(24000,27000));
			flowTaskSubInfo.setExeStr9(exeStr.substring(27000,30000));
			flowTaskSubInfo.setExeStr10(exeStr.substring(30000,33000));
			flowTaskSubInfo.setExeStr11(exeStr.substring(33000));
		}else if(exeStrLength>36000&& exeStrLength<39000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000,18000));
			flowTaskSubInfo.setExeStr6(exeStr.substring(18000,21000));
			flowTaskSubInfo.setExeStr7(exeStr.substring(21000,24000));
			flowTaskSubInfo.setExeStr8(exeStr.substring(24000,27000));
			flowTaskSubInfo.setExeStr9(exeStr.substring(27000,30000));
			flowTaskSubInfo.setExeStr10(exeStr.substring(30000,33000));
			flowTaskSubInfo.setExeStr11(exeStr.substring(33000,36000));
			flowTaskSubInfo.setExeStr12(exeStr.substring(36000));
		}else if(exeStrLength>39000&& exeStrLength<42000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000,18000));
			flowTaskSubInfo.setExeStr6(exeStr.substring(18000,21000));
			flowTaskSubInfo.setExeStr7(exeStr.substring(21000,24000));
			flowTaskSubInfo.setExeStr8(exeStr.substring(24000,27000));
			flowTaskSubInfo.setExeStr9(exeStr.substring(27000,30000));
			flowTaskSubInfo.setExeStr10(exeStr.substring(30000,33000));
			flowTaskSubInfo.setExeStr11(exeStr.substring(33000,36000));
			flowTaskSubInfo.setExeStr12(exeStr.substring(36000,39000));
			flowTaskSubInfo.setExeStr13(exeStr.substring(39000));
		}else if(exeStrLength>42000&& exeStrLength<45000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000,18000));
			flowTaskSubInfo.setExeStr6(exeStr.substring(18000,21000));
			flowTaskSubInfo.setExeStr7(exeStr.substring(21000,24000));
			flowTaskSubInfo.setExeStr8(exeStr.substring(24000,27000));
			flowTaskSubInfo.setExeStr9(exeStr.substring(27000,30000));
			flowTaskSubInfo.setExeStr10(exeStr.substring(30000,33000));
			flowTaskSubInfo.setExeStr11(exeStr.substring(33000,36000));
			flowTaskSubInfo.setExeStr12(exeStr.substring(36000,39000));
			flowTaskSubInfo.setExeStr13(exeStr.substring(39000,42000));
			flowTaskSubInfo.setExeStr14(exeStr.substring(42000));
		}else if(exeStrLength>45000&& exeStrLength<48000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000,18000));
			flowTaskSubInfo.setExeStr6(exeStr.substring(18000,21000));
			flowTaskSubInfo.setExeStr7(exeStr.substring(21000,24000));
			flowTaskSubInfo.setExeStr8(exeStr.substring(24000,27000));
			flowTaskSubInfo.setExeStr9(exeStr.substring(27000,30000));
			flowTaskSubInfo.setExeStr10(exeStr.substring(30000,33000));
			flowTaskSubInfo.setExeStr11(exeStr.substring(33000,36000));
			flowTaskSubInfo.setExeStr12(exeStr.substring(36000,39000));
			flowTaskSubInfo.setExeStr13(exeStr.substring(39000,42000));
			flowTaskSubInfo.setExeStr14(exeStr.substring(42000,45000));
			flowTaskSubInfo.setExeStr15(exeStr.substring(45000));
		}else if(exeStrLength>48000&& exeStrLength<51000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000,18000));
			flowTaskSubInfo.setExeStr6(exeStr.substring(18000,21000));
			flowTaskSubInfo.setExeStr7(exeStr.substring(21000,24000));
			flowTaskSubInfo.setExeStr8(exeStr.substring(24000,27000));
			flowTaskSubInfo.setExeStr9(exeStr.substring(27000,30000));
			flowTaskSubInfo.setExeStr10(exeStr.substring(30000,33000));
			flowTaskSubInfo.setExeStr11(exeStr.substring(33000,36000));
			flowTaskSubInfo.setExeStr12(exeStr.substring(36000,39000));
			flowTaskSubInfo.setExeStr13(exeStr.substring(39000,42000));
			flowTaskSubInfo.setExeStr14(exeStr.substring(42000,45000));
			flowTaskSubInfo.setExeStr15(exeStr.substring(45000,48000));
			flowTaskSubInfo.setExeStr16(exeStr.substring(48000));
		}else if(exeStrLength>51000&& exeStrLength<54000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000,18000));
			flowTaskSubInfo.setExeStr6(exeStr.substring(18000,21000));
			flowTaskSubInfo.setExeStr7(exeStr.substring(21000,24000));
			flowTaskSubInfo.setExeStr8(exeStr.substring(24000,27000));
			flowTaskSubInfo.setExeStr9(exeStr.substring(27000,30000));
			flowTaskSubInfo.setExeStr10(exeStr.substring(30000,33000));
			flowTaskSubInfo.setExeStr11(exeStr.substring(33000,36000));
			flowTaskSubInfo.setExeStr12(exeStr.substring(36000,39000));
			flowTaskSubInfo.setExeStr13(exeStr.substring(39000,42000));
			flowTaskSubInfo.setExeStr14(exeStr.substring(42000,45000));
			flowTaskSubInfo.setExeStr15(exeStr.substring(45000,48000));
			flowTaskSubInfo.setExeStr16(exeStr.substring(48000,51000));
			flowTaskSubInfo.setExeStr17(exeStr.substring(51000));
		}else if(exeStrLength>54000&& exeStrLength<57000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000,18000));
			flowTaskSubInfo.setExeStr6(exeStr.substring(18000,21000));
			flowTaskSubInfo.setExeStr7(exeStr.substring(21000,24000));
			flowTaskSubInfo.setExeStr8(exeStr.substring(24000,27000));
			flowTaskSubInfo.setExeStr9(exeStr.substring(27000,30000));
			flowTaskSubInfo.setExeStr10(exeStr.substring(30000,33000));
			flowTaskSubInfo.setExeStr11(exeStr.substring(33000,36000));
			flowTaskSubInfo.setExeStr12(exeStr.substring(36000,39000));
			flowTaskSubInfo.setExeStr13(exeStr.substring(39000,42000));
			flowTaskSubInfo.setExeStr14(exeStr.substring(42000,45000));
			flowTaskSubInfo.setExeStr15(exeStr.substring(45000,48000));
			flowTaskSubInfo.setExeStr16(exeStr.substring(48000,51000));
			flowTaskSubInfo.setExeStr17(exeStr.substring(51000,54000));
			flowTaskSubInfo.setExeStr18(exeStr.substring(54000));
		}else if(exeStrLength>57000&& exeStrLength<60000){
			flowTaskSubInfo.setExeStr0(exeStr.substring(0,3000));
			flowTaskSubInfo.setExeStr1(exeStr.substring(3000,6000));
			flowTaskSubInfo.setExeStr2(exeStr.substring(6000,9000));
			flowTaskSubInfo.setExeStr3(exeStr.substring(9000,12000));
			flowTaskSubInfo.setExeStr4(exeStr.substring(12000,15000));
			flowTaskSubInfo.setExeStr5(exeStr.substring(15000,18000));
			flowTaskSubInfo.setExeStr6(exeStr.substring(18000,21000));
			flowTaskSubInfo.setExeStr7(exeStr.substring(21000,24000));
			flowTaskSubInfo.setExeStr8(exeStr.substring(24000,27000));
			flowTaskSubInfo.setExeStr9(exeStr.substring(27000,30000));
			flowTaskSubInfo.setExeStr10(exeStr.substring(30000,33000));
			flowTaskSubInfo.setExeStr11(exeStr.substring(33000,36000));
			flowTaskSubInfo.setExeStr12(exeStr.substring(36000,39000));
			flowTaskSubInfo.setExeStr13(exeStr.substring(39000,42000));
			flowTaskSubInfo.setExeStr14(exeStr.substring(42000,45000));
			flowTaskSubInfo.setExeStr15(exeStr.substring(45000,48000));
			flowTaskSubInfo.setExeStr16(exeStr.substring(48000,51000));
			flowTaskSubInfo.setExeStr17(exeStr.substring(51000,54000));
			flowTaskSubInfo.setExeStr18(exeStr.substring(54000,57000));
			flowTaskSubInfo.setExeStr19(exeStr.substring(57000));
		}
		return flowTaskSubInfo;
	}
	
}
