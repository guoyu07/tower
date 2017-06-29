package com.tower.service.dao.util;

import java.util.concurrent.atomic.AtomicBoolean;

import com.alibaba.druid.wall.WallCheckResult;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallProvider;
import com.alibaba.druid.wall.spi.MySqlWallProvider;
import com.tower.service.config.DynamicConfig;
import com.tower.service.dao.ibatis.IBatisDAOException;
import com.tower.service.exception.DataAccessException;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

public class SqlFireWall {

	private WallConfig wallConfig = null;
	private static WallProvider provider;
	private static Boolean check = true;

	public static String WALL_CODE = "BlackWall";

	private static AtomicBoolean wallCheck = new AtomicBoolean(true);

	private static Logger logger = LoggerFactory.getLogger(SqlFireWall.class);
	private static final Logger minitorLogger = LoggerFactory
			.getLogger(SqlFireWall.class);
	private static SqlFireWall instance;

	public static synchronized SqlFireWall getInstance() {
		if (instance == null) {
			instance = new SqlFireWall();
		}
		return instance;
	}

	public void init(DynamicConfig accConfig) {
		wallConfig = new WallConfig();
		wallConfig.setDeleteWhereNoneCheck(true);
		// wallConfig.setDeleteWhereAlwayTrueCheck(true);
		wallConfig.setUpdateWhereNoneCheck(true);
		// wallConfig.setUpdateWhereAlayTrueCheck(true);
		wallConfig.setCommentAllow(true);
		wallConfig.setDeleteAllow(false);
		wallConfig.setCallAllow(false);

		wallConfig.setCreateTableAllow(false);
		wallConfig.setAlterTableAllow(false);
		wallConfig.setRenameTableAllow(false);
		wallConfig.setLockTableAllow(false);
		wallConfig.setTruncateAllow(false);

		wallConfig.setSelectAllColumnAllow(false);

		wallConfig.setDeleteWhereAlwayTrueCheck(false);
		wallConfig.setUpdateWhereAlayTrueCheck(false);
		wallConfig.setConditionAndAlwayTrueAllow(true);
		wallConfig.setMultiStatementAllow(true);

		if (check) {
			provider = new MySqlWallProvider(wallConfig);
			provider.setBlackListEnable(true);
		}
	}

	public boolean check(String sql) {
		if (check) {
			WallCheckResult result = provider.check(sql);
			if (!result.getViolations().isEmpty()) {
				String msString = result.getViolations().get(0).getMessage();
				minitorLogger.info(msString, sql);
				throw new DataAccessException(IBatisDAOException.MSG_1_0007,
						sql);
			}
		}
		return true;
	}
}
