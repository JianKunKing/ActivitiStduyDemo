package junit;

import org.junit.Test;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

public class TestActiviti {

	// 使用代码创建activiti需要的23个表
	@Test
	public void creteTable() {
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration();
		// 连接数据库的配置
		// 配置数据库驱动:对应不同数据库类型的驱动
		processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
		// 配置数据库的JDBC URL
		processEngineConfiguration
				.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?useUnicode=true&characterEncoding=utf8");
		// 配置连接数据库的用户名
		processEngineConfiguration.setJdbcUsername("root");
		// 配置连接数据库的密码
		processEngineConfiguration.setJdbcPassword("aaaaaa");
		/**
		 * public static final String DB_SCHEMA_UPDATE_FALSE =
		 * "false";不能自动创建表，需要表存在 public static final String
		 * DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop";先删除表再创建表 public static
		 * final String DB_SCHEMA_UPDATE_TRUE = "true";如果表不存在，自动创建表
		 */
		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		// 工作流的核心对象，ProcessEnginee对象
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println("processEngine:" + processEngine);
	}

	// 通过xml配置文件创建流程流程引擎表
	@Test
	public void creteTableWithXml() {
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println("processEngine:" + processEngine);
	}
}
