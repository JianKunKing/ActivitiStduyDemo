package jiankunking.processVariables;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ProcessVariablesTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/** �������̶��壨��InputStream�� */
	@Test
	public void deploymentProcessDefinition_inputStream() {
		InputStream inputStreambpmn = this.getClass().getResourceAsStream("/diagrams/processVariables.bpmn");
		InputStream inputStreampng = this.getClass().getResourceAsStream("/diagrams/processVariables.png");
		Deployment deployment = processEngine.getRepositoryService()// �����̶���Ͳ��������ص�Service
				.createDeployment()// ����һ���������
				.name("���̶���")// ��Ӳ��������
				.addInputStream("processVariables.bpmn", inputStreambpmn)// ʹ����Դ�ļ������ƣ�Ҫ������Դ�ļ�������Ҫһ�£�������������ɲ���
				.addInputStream("processVariables.png", inputStreampng)// ʹ����Դ�ļ������ƣ�Ҫ������Դ�ļ�������Ҫһ�£�������������ɲ���
				.deploy();// ��ɲ���
		System.out.println("����ID��" + deployment.getId());//
		System.out.println("�������ƣ�" + deployment.getName());//
	}

	/** ��������ʵ�� */
	@Test
	public void startProcessInstance() {
		// ���̶����key
		String processDefinitionKey = "processVariables";
		ProcessInstance pi = processEngine.getRuntimeService()// ������ִ�е�����ʵ����ִ�ж�����ص�Service
				.startProcessInstanceByKey(processDefinitionKey);// ʹ�����̶����key��������ʵ����key��Ӧhelloworld.bpmn�ļ���id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
		System.out.println("����ʵ��ID:" + pi.getId());// ����ʵ��ID
		System.out.println("���̶���ID:" + pi.getProcessDefinitionId());// ���̶���ID
	}

	/** �������̱��� */
	@Test
	public void setVariables() {
		/** ����������ִ�У� */
		TaskService taskService = processEngine.getTaskService();
		// ����ID
		String taskId = "2104";
		/** һ���������̱�����ʹ�û����������� */
		// taskService.setVariableLocal(taskId, "�������", 5);//������ID��
		// taskService.setVariable(taskId, "�������", new Date());
		// taskService.setVariable(taskId, "���ԭ��", "�ؼ�̽�ף�һ��Ը���");
		/** �����������̱�����ʹ��javabean���� */
		/**
		 * ��һ��javabean��ʵ�����кţ����õ����̱����У�Ҫ��javabean�����Բ����ٷ����仯 * ��������仯���ٻ�ȡ��ʱ���׳��쳣
		 * 
		 * �����������Person��������ӣ� private static final long serialVersionUID =
		 * 6757393795687480331L; ͬʱʵ��Serializable
		 */
		Person p = new Person();
		p.setId(20);
		p.setName("�仨");
		taskService.setVariable(taskId, "��Ա��Ϣ(��ӹ̶��汾)", p);

		System.out.println("�������̱����ɹ���");
	}

	/** ��ȡ���̱��� */
	@Test
	public void getVariables() {
		/** ����������ִ�У� */
		TaskService taskService = processEngine.getTaskService();
		// ����ID
		String taskId = "2104";
		/** һ����ȡ���̱�����ʹ�û����������� */
		// Integer days = (Integer) taskService.getVariable(taskId, "�������");
		// Date date = (Date) taskService.getVariable(taskId, "�������");
		// String resean = (String) taskService.getVariable(taskId, "���ԭ��");
		// System.out.println("���������"+days);
		// System.out.println("������ڣ�"+date);
		// System.out.println("���ԭ��"+resean);
		/** ������ȡ���̱�����ʹ��javabean���� */
		Person p = (Person) taskService.getVariable(taskId, "��Ա��Ϣ(��ӹ̶��汾)");
		System.out.println(p.getId() + "        " + p.getName());
	}

	/** ģ�����úͻ�ȡ���̱����ĳ��� */
	public void setAndGetVariables() {
		/** ������ʵ����ִ�ж�������ִ�У� */
		RuntimeService runtimeService = processEngine.getRuntimeService();
		/** ����������ִ�У� */
		TaskService taskService = processEngine.getTaskService();

		/** �������̱��� */
		// runtimeService.setVariable(executionId, variableName,
		// value)//��ʾʹ��ִ�ж���ID�������̱��������ƣ��������̱�����ֵ��һ��ֻ������һ��ֵ��
		// runtimeService.setVariables(executionId,
		// variables)//��ʾʹ��ִ�ж���ID����Map�����������̱�����map���ϵ�key�������̱��������ƣ�map���ϵ�value�������̱�����ֵ��һ�����ö��ֵ��

		// taskService.setVariable(taskId, variableName,
		// value)//��ʾʹ������ID�������̱��������ƣ��������̱�����ֵ��һ��ֻ������һ��ֵ��
		// taskService.setVariables(taskId,
		// variables)//��ʾʹ������ID����Map�����������̱�����map���ϵ�key�������̱��������ƣ�map���ϵ�value�������̱�����ֵ��һ�����ö��ֵ��

		// runtimeService.startProcessInstanceByKey(processDefinitionKey,
		// variables);//��������ʵ����ͬʱ�������������̱�������Map����
		// taskService.complete(taskId, variables)//��������ͬʱ���������̱�������Map����

		/** ��ȡ���̱��� */
		// runtimeService.getVariable(executionId,
		// variableName);//ʹ��ִ�ж���ID�����̱��������ƣ���ȡ���̱�����ֵ
		// runtimeService.getVariables(executionId);//ʹ��ִ�ж���ID����ȡ���е����̱����������̱������õ�Map�����У�map���ϵ�key�������̱��������ƣ�map���ϵ�value�������̱�����ֵ
		// runtimeService.getVariables(executionId,
		// variableNames);//ʹ��ִ�ж���ID����ȡ���̱�����ֵ��ͨ���������̱��������ƴ�ŵ������У���ȡָ�����̱������Ƶ����̱�����ֵ��ֵ��ŵ�Map������

		// taskService.getVariable(taskId,
		// variableName);//ʹ������ID�����̱��������ƣ���ȡ���̱�����ֵ
		// taskService.getVariables(taskId);//ʹ������ID����ȡ���е����̱����������̱������õ�Map�����У�map���ϵ�key�������̱��������ƣ�map���ϵ�value�������̱�����ֵ
		// taskService.getVariables(taskId,
		// variableNames);//ʹ������ID����ȡ���̱�����ֵ��ͨ���������̱��������ƴ�ŵ������У���ȡָ�����̱������Ƶ����̱�����ֵ��ֵ��ŵ�Map������

	}

	/** ����ҵ����� */
	@Test
	public void completeMyPersonalTask() {
		// ����ID
		String taskId = "2402";
		processEngine.getTaskService()// ������ִ�е����������ص�Service
				.complete(taskId);
		System.out.println("�����������ID��" + taskId);
	}

	/** ��ѯ���̱�������ʷ�� */
	@Test
	public void findHistoryProcessVariables() {
		List<HistoricVariableInstance> list = processEngine.getHistoryService()//
				.createHistoricVariableInstanceQuery()// ����һ����ʷ�����̱�����ѯ����
				.variableName("�������").list();
		if (list != null && list.size() > 0) {
			for (HistoricVariableInstance hvi : list) {
				System.out.println(hvi.getId() + "   " + hvi.getProcessInstanceId() + "   " + hvi.getVariableName()
						+ "   " + hvi.getVariableTypeName() + "    " + hvi.getValue());
				System.out.println("###############################################");
			}
		}
	}
}
