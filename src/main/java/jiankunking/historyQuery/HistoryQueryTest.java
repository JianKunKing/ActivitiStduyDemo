package jiankunking.historyQuery;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

public class HistoryQueryTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/** ��ѯ��ʷ����ʵ�� */
	@Test
	public void findHistoryProcessInstance() {
		String processInstanceId = "2101";
		HistoricProcessInstance hpi = processEngine.getHistoryService()// ����ʷ���ݣ���ʷ����ص�Service
				.createHistoricProcessInstanceQuery()// ������ʷ����ʵ����ѯ
				.processInstanceId(processInstanceId)// ʹ������ʵ��ID��ѯ
				.orderByProcessInstanceStartTime().asc().singleResult();
		System.out.println(hpi.getId() + "    " + hpi.getProcessDefinitionId() + "    " + hpi.getStartTime() + "    "
				+ hpi.getEndTime() + "     " + hpi.getDurationInMillis());
	}

	/** ��ѯ��ʷ� */
	@Test
	public void findHistoryActiviti() {
		String processInstanceId = "2101";
		List<HistoricActivityInstance> list = processEngine.getHistoryService()//
				.createHistoricActivityInstanceQuery()// ������ʷ�ʵ���Ĳ�ѯ
				.processInstanceId(processInstanceId)//
				.orderByHistoricActivityInstanceStartTime().asc()//
				.list();
		if (list != null && list.size() > 0) {
			for (HistoricActivityInstance hai : list) {
				System.out.println(hai.getId() + "   " + hai.getProcessInstanceId() + "   " + hai.getActivityType()
						+ "  " + hai.getStartTime() + "   " + hai.getEndTime() + "   " + hai.getDurationInMillis());
				System.out.println("#####################");
			}
		}
	}

	/** ��ѯ��ʷ���� */
	@Test
	public void findHistoryTask() {
		String processInstanceId = "2101";
		List<HistoricTaskInstance> list = processEngine.getHistoryService()// ����ʷ���ݣ���ʷ����ص�Service
				.createHistoricTaskInstanceQuery()// ������ʷ����ʵ����ѯ
				.processInstanceId(processInstanceId)//
				.orderByHistoricTaskInstanceStartTime().asc().list();
		if (list != null && list.size() > 0) {
			for (HistoricTaskInstance hti : list) {
				System.out.println(hti.getId() + "    " + hti.getName() + "    " + hti.getProcessInstanceId() + "   "
						+ hti.getStartTime() + "   " + hti.getEndTime() + "   " + hti.getDurationInMillis());
				System.out.println("################################");
			}
		}
	}

	/** ��ѯ��ʷ���̱��� */
	@Test
	public void findHistoryProcessVariables() {
		String processInstanceId = "2101";
		List<HistoricVariableInstance> list = processEngine.getHistoryService()//
				.createHistoricVariableInstanceQuery()// ����һ����ʷ�����̱�����ѯ����
				.processInstanceId(processInstanceId)//
				.list();
		if (list != null && list.size() > 0) {
			for (HistoricVariableInstance hvi : list) {
				System.out.println(hvi.getId() + "   " + hvi.getProcessInstanceId() + "   " + hvi.getVariableName()
						+ "   " + hvi.getVariableTypeName() + "    " + hvi.getValue());
				System.out.println("###############################################");
			}
		}
	}
}
