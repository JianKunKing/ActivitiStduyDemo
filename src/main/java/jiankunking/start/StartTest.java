package jiankunking.start;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class StartTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**�������̶��壨��inputStream��*/
	@Test
	public void deploymentProcessDefinition_inputStream(){
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("start.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("start.png");
		Deployment deployment = processEngine.getRepositoryService()//�����̶���Ͳ��������ص�Service
						.createDeployment()//����һ���������
						.name("��ʼ�")//��Ӳ��������
						.addInputStream("start.bpmn", inputStreamBpmn)//
						.addInputStream("start.png", inputStreamPng)//
						.deploy();//��ɲ���
		System.out.println("����ID��"+deployment.getId());//
		System.out.println("�������ƣ�"+deployment.getName());//
	}
	
	/**��������ʵ��+�ж������Ƿ����+��ѯ��ʷ*/
	@Test
	public void startProcessInstance(){
		//���̶����key
		String processDefinitionKey = "start";
		ProcessInstance pi = processEngine.getRuntimeService()//������ִ�е�����ʵ����ִ�ж�����ص�Service
						.startProcessInstanceByKey(processDefinitionKey);//ʹ�����̶����key��������ʵ����key��Ӧhelloworld.bpmn�ļ���id������ֵ��ʹ��keyֵ������Ĭ���ǰ������°汾�����̶�������
		System.out.println("����ʵ��ID:"+pi.getId());//����ʵ��ID    101
		System.out.println("���̶���ID:"+pi.getProcessDefinitionId());//���̶���ID   helloworld:1:4
		
		/**�ж������Ƿ��������ѯ����ִ�е�ִ�ж����*/
		ProcessInstance rpi = processEngine.getRuntimeService()//
						.createProcessInstanceQuery()//��������ʵ����ѯ����
						.processInstanceId(pi.getId())
						.singleResult();
		//˵������ʵ��������
		if(rpi==null){
			/**��ѯ��ʷ����ȡ���̵������Ϣ*/
			HistoricProcessInstance hpi = processEngine.getHistoryService()//
						.createHistoricProcessInstanceQuery()//
						.processInstanceId(pi.getId())//ʹ������ʵ��ID��ѯ
						.singleResult();
			System.out.println(hpi.getId()+"    "+hpi.getStartTime()+"   "+hpi.getEndTime()+"   "+hpi.getDurationInMillis());
		}
	}
	
	
}