package junit;

import org.junit.Test;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

public class TestActiviti {

	// ʹ�ô��봴��activiti��Ҫ��23����
	@Test
	public void creteTable() {
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration();
		// �������ݿ������
		// �������ݿ�����:��Ӧ��ͬ���ݿ����͵�����
		processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
		// �������ݿ��JDBC URL
		processEngineConfiguration
				.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?useUnicode=true&characterEncoding=utf8");
		// �����������ݿ���û���
		processEngineConfiguration.setJdbcUsername("root");
		// �����������ݿ������
		processEngineConfiguration.setJdbcPassword("aaaaaa");
		/**
		 * public static final String DB_SCHEMA_UPDATE_FALSE =
		 * "false";�����Զ���������Ҫ����� public static final String
		 * DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop";��ɾ�����ٴ����� public static
		 * final String DB_SCHEMA_UPDATE_TRUE = "true";��������ڣ��Զ�������
		 */
		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		// �������ĺ��Ķ���ProcessEnginee����
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println("processEngine:" + processEngine);
	}

	// ͨ��xml�����ļ������������������
	@Test
	public void creteTableWithXml() {
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println("processEngine:" + processEngine);
	}
}
