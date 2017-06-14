package jiankunking.groupUser;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class TaskTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/** �������̶��壨��inputStream�� */
	@Test
	public void deploymentProcessDefinition_inputStream() {
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("task.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("task.png");
		Deployment deployment = processEngine.getRepositoryService()// �����̶���Ͳ��������ص�Service
				.createDeployment()// ����һ���������
				.name("����")// ��Ӳ��������
				.addInputStream("task.bpmn", inputStreamBpmn)//
				.addInputStream("task.png", inputStreamPng)//
				.deploy();// ��ɲ���
		System.out.println("����ID��" + deployment.getId());//
		System.out.println("�������ƣ�" + deployment.getName());//
		/** ����û���ɫ�� */
		IdentityService identityService = processEngine.getIdentityService();//
		// ������ɫ
		identityService.saveGroup(new GroupEntity("�ܾ���"));
		identityService.saveGroup(new GroupEntity("���ž���"));
		// �����û�
		identityService.saveUser(new UserEntity("����"));
		identityService.saveUser(new UserEntity("����"));
		identityService.saveUser(new UserEntity("����"));
		// �����û��ͽ�ɫ�Ĺ�����ϵ
		identityService.createMembership("����", "���ž���");
		identityService.createMembership("����", "���ž���");
		identityService.createMembership("����", "�ܾ���");
		System.out.println("�����֯�����ɹ�");
	}

	/** ��������ʵ�� */
	@Test
	public void startProcessInstance() {
		// ���̶����key
		String processDefinitionKey = "task";
		ProcessInstance pi = processEngine.getRuntimeService()// ������ִ�е�����ʵ����ִ�ж�����ص�Service
				.startProcessInstanceByKey(processDefinitionKey);// ʹ�����̶����key��������ʵ����key��Ӧhelloworld.bpmn�ļ���id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
		System.out.println("����ʵ��ID:" + pi.getId());// ����ʵ��ID
		System.out.println("���̶���ID:" + pi.getProcessDefinitionId());// ���̶���ID
	}

	/** ��ѯ��ǰ�˵ĸ������� */
	@Test
	public void findMyPersonalTask() {
		String assignee = "СA";
		List<Task> list = processEngine.getTaskService()// ������ִ�е����������ص�Service
				.createTaskQuery()// ���������ѯ����
				/** ��ѯ������where���֣� */
				.taskAssignee(assignee)// ָ�����������ѯ��ָ��������
				// .taskCandidateUser(candidateUser)//������İ����˲�ѯ
				/** ���� */
				.orderByTaskCreateTime().asc()// ʹ�ô���ʱ�����������
				/** ���ؽ���� */
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

	/** ��ѯ��ǰ�˵������� */
	@Test
	public void findMyGroupTask() {
		String candidateUser = "����1";
		List<Task> list = processEngine.getTaskService()// ������ִ�е����������ص�Service
				.createTaskQuery()// ���������ѯ����
				/** ��ѯ������where���֣� */
				.taskCandidateUser(candidateUser)// ������İ����˲�ѯ
				/** ���� */
				.orderByTaskCreateTime().asc()// ʹ�ô���ʱ�����������
				/** ���ؽ���� */
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
		String taskId = "7504";
		processEngine.getTaskService()// ������ִ�е����������ص�Service
				.complete(taskId);
		System.out.println("�����������ID��" + taskId);
	}

	/** ��ѯ����ִ�е���������˱� */
	@Test
	public void findRunPersonTask() {
		// ����ID
		String taskId = "6204";
		List<IdentityLink> list = processEngine.getTaskService()//
				.getIdentityLinksForTask(taskId);
		if (list != null && list.size() > 0) {
			for (IdentityLink identityLink : list) {
				System.out.println(identityLink.getTaskId() + "   " + identityLink.getType() + "   "
						+ identityLink.getProcessInstanceId() + "   " + identityLink.getUserId());
			}
		}
	}

	/** ��ѯ��ʷ����İ����˱� */
	@Test
	public void findHistoryPersonTask() {
		// ����ʵ��ID
		String processInstanceId = "6201";
		List<HistoricIdentityLink> list = processEngine.getHistoryService()//
				.getHistoricIdentityLinksForProcessInstance(processInstanceId);
		if (list != null && list.size() > 0) {
			for (HistoricIdentityLink identityLink : list) {
				System.out.println(identityLink.getTaskId() + "   " + identityLink.getType() + "   "
						+ identityLink.getProcessInstanceId() + "   " + identityLink.getUserId());
			}
		}
	}

	/** ʰȡ���񣬽�������ָ���������ָ������İ������ֶ� */
	@Test
	public void claim() {
		// ��������������������
		// ����ID
		String taskId = "7504";
		// ����ĸ������񣨿������������еĳ�Ա��Ҳ�����Ƿ�������ĳ�Ա��
		String userId = "����";
		processEngine.getTaskService()//
				.claim(taskId, userId);
	}

}