package jiankunking.sequenceFlow;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
public class SequenceFlowTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/** �������̶��壨��inputStream�� */
	@Test
	public void deploymentProcessDefinition_inputStream() {
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("sequenceFlow.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("sequenceFlow.png");
		Deployment deployment = processEngine.getRepositoryService()// �����̶���Ͳ��������ص�Service
				.createDeployment()// ����һ���������
				.name("����")// ��Ӳ��������
				.addInputStream("sequenceFlow.bpmn", inputStreamBpmn)//
				.addInputStream("sequenceFlow.png", inputStreamPng)//
				.deploy();// ��ɲ���
		System.out.println("����ID��" + deployment.getId());//
		System.out.println("�������ƣ�" + deployment.getName());//
	}

	/** ��������ʵ�� */
	@Test
	public void startProcessInstance() {
		// ���̶����key
		String processDefinitionKey = "sequenceFlow";
		ProcessInstance pi = processEngine.getRuntimeService()// ������ִ�е�����ʵ����ִ�ж�����ص�Service
				.startProcessInstanceByKey(processDefinitionKey);// ʹ�����̶����key��������ʵ����key��Ӧhelloworld.bpmn�ļ���id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
		System.out.println("����ʵ��ID:" + pi.getId());// ����ʵ��ID 101
		System.out.println("���̶���ID:" + pi.getProcessDefinitionId());// ���̶���ID
																	// helloworld:1:4
	}

	/** ��ѯ��ǰ�˵ĸ������� */
	@Test
	public void findMyPersonalTask() {
		String assignee = "����";
		List<Task> list = processEngine.getTaskService()// ������ִ�е����������ص�Service
				.createTaskQuery()// ���������ѯ����
				/** ��ѯ������where���֣� */
				.taskAssignee(assignee)// ָ�����������ѯ��ָ��������
				// .taskCandidateUser(candidateUser)//������İ����˲�ѯ
				// .processDefinitionId(processDefinitionId)//ʹ�����̶���ID��ѯ
				// .processInstanceId(processInstanceId)//ʹ������ʵ��ID��ѯ
				// .executionId(executionId)//ʹ��ִ�ж���ID��ѯ
				/** ���� */
				.orderByTaskCreateTime().asc()// ʹ�ô���ʱ�����������
				/** ���ؽ���� */
				// .singleResult()//����Ωһ�����
				// .count()//���ؽ����������
				// .listPage(firstResult, maxResults);//��ҳ��ѯ
				.list();// �����б�
		if (list != null && list.size() > 0) {
			for (Task task : list) {
				System.out.println("����ID:" + task.getId());
				System.out.println("��������:" + task.getName());
				System.out.println("����Ĵ���ʱ��:" + task.getCreateTime());
				System.out.println("����İ�����:" + task.getAssignee());
				System.out.println("����ʵ��ID��" + task.getProcessInstanceId());
				System.out.println("ִ�ж���ID:" + task.getExecutionId());
				System.out.println("���̶���ID:" + task.getProcessDefinitionId());
				System.out.println("########################################################");
			}
		}
	}

	/** ����ҵ����� */
	@Test
	public void completeMyPersonalTask() {
		// ����ID
		String taskId = "67504";
		// ��������ͬʱ���������̱�����ʹ�����̱�������ָ������������һ�����ߣ���ӦsequenceFlow.bpmn�ļ���${message=='����Ҫ'}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("message", "��Ҫ");
		processEngine.getTaskService()// ������ִ�е����������ص�Service
				.complete(taskId, variables);
		System.out.println("�����������ID��" + taskId);
	}
}
