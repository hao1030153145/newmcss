package com.transing.mcss4dpm.JobEvent;

import com.jeeframework.jeetask.event.rdb.impl.JobEventCommonStorageProcessor;
import com.jeeframework.jeetask.event.type.JobExecutionEvent;
import com.jeeframework.util.format.DateFormat;
import com.transing.mcss4dpm.JobEvent.Bo.McssTask;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.text.ParseException;

public class McssJobEventCommonStorage extends JobEventCommonStorageProcessor {
    private static final Logger log = LoggerFactory.getLogger(McssJobEventCommonStorage.class);

    public McssJobEventCommonStorage(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void createTaskTable(Connection conn) throws SQLException {
        String dbSchema = "CREATE TABLE `"+tableName+"` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `job_name` varchar(100),\n" +
                "  `state` varchar(20) NOT NULL,\n" +
                "  `progress` int(11) NOT NULL,\n" +
                "  `ip` varchar(20) NOT NULL,\n" +
                "  `param` text, \n" +
                "  `message` varchar(500) NOT NULL,\n" +
                "  `create_time` timestamp NULL DEFAULT NULL,\n" +
                "  `start_time` timestamp NULL DEFAULT NULL,\n" +
                "  `complete_time` timestamp NULL DEFAULT NULL,\n" +
                "  `failure_cause` varchar(4000) DEFAULT NULL,\n" +
                "  `sub_task_id` int(11),\n" +
                "  `task_id` int(11),\n" +
                "  `deal_class` varchar(50) DEFAULT NULL,\n" +
                "  `data_type_id` varchar(50) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `idx_task_id_state` (`id`,`state`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(dbSchema)) {
            preparedStatement.execute();
        }
    }

    @Override
    protected boolean insertJobExecutionEvent(JobExecutionEvent jobExecutionEvent) {
        boolean result = false;
        String sql = "INSERT INTO `"+tableName+"` (\n" +
                "\t`job_name`,\n" +
                "\t`state`,\n" +
                "\t`progress`,\n" +
                "\t`ip`,\n" +
                "\t`param`,\n" +
                "\t`message`,\n" +
                "\t`create_time`,\n" +
                "\t`start_time`,\n" +
                "\t`complete_time`,\n" +
                "\t`sub_task_id`,\n" +
                "\t`task_id`,\n" +
                "\t`deal_class`,\n" +
                "\t`data_type_id`\n" +
                ")\n" +
                "VALUES\n" +
                "\t(\n" +
                "\t\t?,\n" +
                "\t\t?,\n" +
                "\t\t?,\n" +
                "\t\t?,\n" +
                "\t\t?,\n" +
                "\t\t?,\n" +
                "\t\t?,\n" +
                "\t\t?,\n" +
                "\t\t?,\n" +
                "\t\t?,\n" +
                "\t\t?,\n" +
                "\t\t?,\n" +
                "\t\t?\n" +
                "\t);\n" +
                "\n";
        try (
        Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            McssTask mcssTask = (McssTask) jobExecutionEvent.getTask();
            preparedStatement.setString(1, mcssTask.getName());
            preparedStatement.setString(2, jobExecutionEvent.getState().toString());
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, jobExecutionEvent.getIp());
            preparedStatement.setString(5, mcssTask.getParam());
            preparedStatement.setString(6, "");
            preparedStatement.setTimestamp(7, new Timestamp(jobExecutionEvent.getCreateTime().getTime()));
            try {
                preparedStatement.setTimestamp(8, new Timestamp(DateFormat.parseDate("1971-01-01 00:00:00", DateFormat
                        .DT_YYYY_MM_DD_HHMMSS).getTime()));
            } catch (ParseException e) {
            }
            try {
                preparedStatement.setTimestamp(9, new Timestamp(DateFormat.parseDate("1971-01-01 00:00:00", DateFormat
                        .DT_YYYY_MM_DD_HHMMSS).getTime()));
            } catch (ParseException e) {
            }
//            preparedStatement.setString(10,mcssTask.getFailureCause());
            preparedStatement.setString(10,mcssTask.getSubTaskId());
            preparedStatement.setString(11,mcssTask.getTaskId());
            preparedStatement.setString(12,mcssTask.getDealClass());
            preparedStatement.setString(13,mcssTask.getDataTypeId());

            preparedStatement.execute();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            Object retId = null;
            if (rs.next())
                retId = rs.getObject(1);
            else
                throw new SQLException("insert or generate keys failed..");

            jobExecutionEvent.setTaskId(Long.valueOf(retId + ""));
            result = true;
        } catch (final SQLException ex) {
            if (!isDuplicateRecord(ex)) {
                // TODO 记录失败直接输出日志,未来可考虑配置化
                log.error(ex.getMessage());
            }
        }
        return result;
    }
}