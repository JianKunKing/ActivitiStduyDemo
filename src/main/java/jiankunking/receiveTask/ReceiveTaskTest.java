package jiankunking.receiveTask;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ReceiveTaskTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**�������̶��壨��inputStream��*/
	@Test
	public void deploymentProcessDefinition_inputStream(){
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("receiveTask.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("receiveTask.png");
		Deployment deployment = processEngine.getRepositoryService()//�����̶���Ͳ��������ص�Service
						.createDeployment()//����һ���������
						.name("���ջ����")//��Ӳ��������
						.addInputStream("receiveTask.bpmn", inputStreamBpmn)//
						.addInputStream("receiveTask.png", inputStreamPng)//
						.deploy();//��ɲ���
		System.out.println("����ID��"+deployment.getId());//
		System.out.println("�������ƣ�"+deployment.getName());//
	}
	
	/**��������ʵ��+�������̱���+��ȡ���̱���+���ִ��һ��*/
	@Test
	public void startProcessInstance(){
		//���̶����key
		String processDefinitionKey = "receiveTask";
		ProcessInstance pi = processEngine.getRuntimeService()//������ִ�е�����ʵ����ִ�ж�����ص�Service
						.startProcessInstanceByKey(processDefinitionKey);//ʹ�����̶����key��������ʵ����key��Ӧhelloworld.bpmn�ļ���id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
		System.out.println("����ʵ��ID:"+pi.getId());//����ʵ��ID    101
		System.out.println("���̶���ID:"+pi.getProcessDefinitionId());//���̶���ID   helloworld:1:4
		
		/**��ѯִ�ж���ID*/
		Execution execution1 = processEngine.getRuntimeService()//
						.createExecutionQuery()//����ִ�ж����ѯ
						.processInstanceId(pi.getId())//ʹ������ʵ��ID��ѯ
						.activityId("receivetask1")//��ǰ���id����ӦreceiveTask.bpmn�ļ��еĻ�ڵ�id������ֵ
						.singleResult();
		
		/**ʹ�����̱������õ������۶��������ҵ�����*/
		processEngine.getRuntimeService()//
						.setVariable(execution1.getId(), "���ܵ������۶�", 21000);
		
		/**���ִ��һ����������̴��ڵȴ�״̬��ʹ�����̼���ִ��*/
		processEngine.getRuntimeService()
						.signal(execution1.getId());
		
		/**��ѯִ�ж���ID*/
		Execution execution2 = processEngine.getRuntimeService()//
						.createExecutionQuery()//����ִ�ж����ѯ
						.processInstanceId(pi.getId())//ʹ������ʵ��ID��ѯ
						.activityId("receivetask2")//��ǰ���id����ӦreceiveTask.bpmn�ļ��еĻ�ڵ�id������ֵ
						.singleResult();
		
		/**�����̱����л�ȡ���ܵ������۶��ֵ*/
		Integer value = (Integer)processEngine.getRuntimeService()//
						.getVariable(execution2.getId(), "���ܵ������۶�");
		System.out.println("���ϰ巢�Ͷ��ţ�����ǣ�"+value);
		/**���ִ��һ����������̴��ڵȴ�״̬��ʹ�����̼���ִ��*/
		processEngine.getRuntimeService()
						.signal(execution2.getId());
		
	}
	
	
}

