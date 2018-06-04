package com.gwghk.ims.datacleaning.dao.entity;

import java.util.Date;

/**
 * 
 * @ClassName: ActivitySyncDataUpdate
 * @Description: 表同步时间记录
 * @author lawrence
 * @date 2017年5月19日
 *
 */
public class ActSyncDataUpdate extends BaseEntity {

  private int id;
  /**
   * 同步数据类型
   */
  private String syncType;
  /**
   * 最新同步的数据的最后更新时间
   */
  private Date lastUpdateTime;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSyncType() {
    return syncType;
  }

  public void setSyncType(String syncType) {
    this.syncType = syncType;
  }

  public Date getLastUpdateTime() {
    return lastUpdateTime;
  }

  public void setLastUpdateTime(Date lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }

}
